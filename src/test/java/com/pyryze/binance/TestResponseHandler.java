package com.pyryze.binance;

import com.pyryze.binance.connector.client.exceptions.BinanceClientException;
import com.pyryze.binance.connector.client.exceptions.BinanceServerException;
import com.pyryze.binance.connector.client.utils.ResponseHandler;
import okhttp3.Request;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;
import java.util.function.*;

@ExtendWith(MockitoExtension.class)
public class TestResponseHandler {
    private MockWebServer mockWebServer;
    private Request request;
    private final String VALID_RESPONSE = "VALID";
    
    @BeforeEach
    void init() {
        this.mockWebServer = new MockWebServer();
        this.request = new Request.Builder()
                .url(mockWebServer.url("/").toString())
                .addHeader("Accept", "application/json")
                .build();
    }
    
    @AfterEach
    void teardown() throws IOException {
        mockWebServer.shutdown();
    }
    
    static Stream<Arguments>responseHandleCases(){
        return Stream.of(
            Arguments.of(sync()),
            Arguments.of(async())
        );
    }
    static Function<Request,String> sync(){
        return request -> ResponseHandler.handleResponse(request,false,null,null);
    }
    static Function<Request,String> async(){
        return request -> ResponseHandler.handleAsyncResponse(request, false, null,null).block();
    }
    
    @MethodSource("responseHandleCases")
    @ParameterizedTest
    public void testHandleResponse(Function<Request,String>responseHandle) {
        mockWebServer.enqueue(new MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .setResponseCode(MockData.HTTP_STATUS_OK)
                .setBody(VALID_RESPONSE));
        String result = responseHandle.apply(request);
        assertEquals(VALID_RESPONSE, result);
        
    }
    
    @MethodSource("responseHandleCases")
    @ParameterizedTest
    public void testWithin400JSONErrorMsg(Function<Request,String>responseHandle){
        String mockErrorMsg = "{\"code\":-1000, \"msg\":\"error\"}";
        Long retryAfterSec = 56l;
        mockWebServer.enqueue(new MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Retry-After","56")
                .setResponseCode(MockData.HTTP_STATUS_CLIENT_ERROR)
                .setBody(mockErrorMsg));
        
        BinanceClientException thrown = assertThrows(BinanceClientException.class, () -> responseHandle.apply(request));
        assertTrue(thrown.getMessage().contains(mockErrorMsg));
        assertEquals(thrown.getRetryAfterSec(),retryAfterSec);
    }
    
    @MethodSource("responseHandleCases")
    @ParameterizedTest
    public void testWithin400ErrorMsg(Function<Request,String>responseHandle){
        String mockErrorMsg = "Error Message";
        Long retryAfterSec = 56l;
        mockWebServer.enqueue(new MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Retry-After","56")
                .setResponseCode(MockData.HTTP_STATUS_CLIENT_ERROR+96)
                .setBody(mockErrorMsg));
        
        BinanceClientException thrown = assertThrows(BinanceClientException.class, () -> responseHandle.apply(request));
        assertTrue(thrown.getMessage().contains(mockErrorMsg));
        assertEquals(thrown.getRetryAfterSec(),retryAfterSec);
    }

    @MethodSource("responseHandleCases")
    @ParameterizedTest
    public void testWith500ErrorMsg(Function<Request,String>responseHandle){
        String mockErrorMsg = "Error Message";
        mockWebServer.enqueue(new MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .setResponseCode(MockData.HTTP_STATUS_SERVER_ERROR)
                .setBody(mockErrorMsg));
        
        BinanceServerException thrown = assertThrows(BinanceServerException.class, () -> responseHandle.apply(request));
        assertTrue(thrown.getMessage().contains(mockErrorMsg));
    }
}
