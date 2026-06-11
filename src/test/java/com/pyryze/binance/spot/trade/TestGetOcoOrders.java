package com.pyryze.binance.spot.trade;

import com.pyryze.binance.connector.client.enums.HttpMethod;
import com.pyryze.binance.connector.client.impl.SpotClientImpl;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.pyryze.binance.MockData;
import com.pyryze.binance.MockWebServerDispatcher;
import java.util.LinkedHashMap;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class TestGetOcoOrders {
    private MockWebServer mockWebServer;
    private String baseUrl;

    private final long startTime = System.currentTimeMillis();
    private final long endTime = startTime + 1000;

    private final int fromId = 123;
    private final int limit = 10;

    @BeforeEach
    public void init() {
        this.mockWebServer = new MockWebServer();
        this.baseUrl = mockWebServer.url(MockData.PREFIX).toString();
    }

    @Test
    public void testGetOcoOrdersWithoutParams() {
        String path = "/api/v3/allOrderList";
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();

        Dispatcher dispatcher = MockWebServerDispatcher.getDispatcher(MockData.PREFIX, path, MockData.MOCK_RESPONSE, HttpMethod.GET, MockData.HTTP_STATUS_OK);
        mockWebServer.setDispatcher(dispatcher);

        SpotClientImpl client = new SpotClientImpl(MockData.API_KEY, MockData.SECRET_KEY, baseUrl);
        String result = client.createTrade().getOCOOrders(parameters).block();
        assertEquals(MockData.MOCK_RESPONSE, result);
    }

    @Test
    public void testGetOcoOrdersWithParams() {
        String path = String.format("/api/v3/allOrderList?fromId=123&startTime=%s&endTime=%s&limit=10", startTime, endTime);
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("fromId", fromId);
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);
        parameters.put("limit", limit);

        Dispatcher dispatcher = MockWebServerDispatcher.getDispatcher(MockData.PREFIX, path, MockData.MOCK_RESPONSE, HttpMethod.GET, MockData.HTTP_STATUS_OK);
        mockWebServer.setDispatcher(dispatcher);

        SpotClientImpl client = new SpotClientImpl(MockData.API_KEY, MockData.SECRET_KEY, baseUrl);
        String result = client.createTrade().getOCOOrders(parameters).block();
        assertEquals(MockData.MOCK_RESPONSE, result);
    }
}
