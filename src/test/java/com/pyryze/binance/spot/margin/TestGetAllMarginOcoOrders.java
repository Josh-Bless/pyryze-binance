package com.pyryze.binance.spot.margin;

import com.pyryze.binance.connector.client.enums.HttpMethod;
import com.pyryze.binance.connector.client.impl.SpotClientImpl;
import java.util.LinkedHashMap;
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
public class TestGetAllMarginOcoOrders {
    private MockWebServer mockWebServer;
    private String baseUrl;

    private final int startTime = 12345678;
    private final int endTime = 12345679;
    private final int fromId = 1;


    @BeforeEach
    public void init() {
        this.mockWebServer = new MockWebServer();
        this.baseUrl = mockWebServer.url(MockData.PREFIX).toString();
    }


    @Test
    public void testGetAllMarginOcoOrders() {
        String path = "/sapi/v1/margin/allOrderList?symbol=BNBUSDT&fromId=1&startTime=12345678&endTime=12345679";
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", "BNBUSDT");
        parameters.put("fromId", fromId);
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);

        Dispatcher dispatcher = MockWebServerDispatcher.getDispatcher(MockData.PREFIX, path, MockData.MOCK_RESPONSE, HttpMethod.GET, MockData.HTTP_STATUS_OK);
        mockWebServer.setDispatcher(dispatcher);

        SpotClientImpl client = new SpotClientImpl(MockData.API_KEY, MockData.SECRET_KEY, baseUrl);
        String result = client.createMargin().getAllOcoOrders(parameters).block();
        assertEquals(MockData.MOCK_RESPONSE, result);
    }
}
