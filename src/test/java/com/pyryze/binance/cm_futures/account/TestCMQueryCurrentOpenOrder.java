package com.pyryze.binance.cm_futures.account;

import com.pyryze.binance.connector.client.enums.HttpMethod;
import com.pyryze.binance.connector.client.exceptions.BinanceConnectorException;
import com.pyryze.binance.connector.client.impl.CMFuturesClientImpl;
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
public class TestCMQueryCurrentOpenOrder {
    private MockWebServer mockWebServer;
    private String baseUrl;


    @BeforeEach
    public void init() {
        this.mockWebServer = new MockWebServer();
        this.baseUrl = mockWebServer.url(MockData.PREFIX).toString();
    }

    @Test
    public void testQueryCurrentOpenOrderWithoutSymbol() {
        String path = "dapi/v1/openOrder";
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();

        Dispatcher dispatcher = MockWebServerDispatcher.getDispatcher(MockData.PREFIX, path, MockData.MOCK_RESPONSE, HttpMethod.GET, MockData.HTTP_STATUS_OK);
        mockWebServer.setDispatcher(dispatcher);

        CMFuturesClientImpl client = new CMFuturesClientImpl(MockData.API_KEY, MockData.SECRET_KEY, baseUrl);
        assertThrows(BinanceConnectorException.class, () -> client.account().queryCurrentOpenOrder(parameters).block());
    }

    @Test
    public void testQueryCurrentOpenOrder() {
        String path = "dapi/v1/openOrder?symbol=BNBUSDT";
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", "BNBUSDT");

        Dispatcher dispatcher = MockWebServerDispatcher.getDispatcher(MockData.PREFIX, path, MockData.MOCK_RESPONSE, HttpMethod.GET, MockData.HTTP_STATUS_OK);
        mockWebServer.setDispatcher(dispatcher);

        CMFuturesClientImpl client = new CMFuturesClientImpl(MockData.API_KEY, MockData.SECRET_KEY, baseUrl);
        assertThrows(BinanceConnectorException.class, () -> client.account().queryCurrentOpenOrder(parameters).block());
    }

    @Test
    public void testQueryCurrentOpenOrderWithOrderId() {
        String path = "dapi/v1/openOrder?symbol=BNBUSDT&orderId=123";
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", "BNBUSDT");
        parameters.put("orderId", "123");

        Dispatcher dispatcher = MockWebServerDispatcher.getDispatcher(MockData.PREFIX, path, MockData.MOCK_RESPONSE, HttpMethod.GET, MockData.HTTP_STATUS_OK);
        mockWebServer.setDispatcher(dispatcher);

        CMFuturesClientImpl client = new CMFuturesClientImpl(MockData.API_KEY, MockData.SECRET_KEY, baseUrl);
        String result = client.account().queryCurrentOpenOrder(parameters).block();
        assertEquals(MockData.MOCK_RESPONSE, result);
    }

    @Test
    public void testQueryCurrentOpenOrderWithOrigClientOrderId() {
        String path = "dapi/v1/openOrder?symbol=BNBUSDT&origClientOrderId=123";
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", "BNBUSDT");
        parameters.put("origClientOrderId", "123");

        Dispatcher dispatcher = MockWebServerDispatcher.getDispatcher(MockData.PREFIX, path, MockData.MOCK_RESPONSE, HttpMethod.GET, MockData.HTTP_STATUS_OK);
        mockWebServer.setDispatcher(dispatcher);

        CMFuturesClientImpl client = new CMFuturesClientImpl(MockData.API_KEY, MockData.SECRET_KEY, baseUrl);
        String result = client.account().queryCurrentOpenOrder(parameters).block();
        assertEquals(MockData.MOCK_RESPONSE, result);
    }
}
