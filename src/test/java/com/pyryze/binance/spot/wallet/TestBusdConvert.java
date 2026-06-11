package com.pyryze.binance.spot.wallet;

import com.pyryze.binance.connector.client.enums.HttpMethod;
import com.pyryze.binance.connector.client.exceptions.BinanceConnectorException;
import com.pyryze.binance.connector.client.impl.SpotClientImpl;
import java.math.BigDecimal;
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
public class TestBusdConvert {
    private MockWebServer mockWebServer;
    private String baseUrl;

    private final BigDecimal amount = new BigDecimal(1);
    private final double wrongAmountType = 0.01;

    @BeforeEach
    public void init() {
        this.mockWebServer = new MockWebServer();
        this.baseUrl = mockWebServer.url(MockData.PREFIX).toString();
    }

    @Test
    public void testBusdConverWithWrongParamType() {
        String path = "/sapi/v1/asset/convert-transfer";
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("clientTranId", "118263407119");
        parameters.put("asset", "BUSD");
        parameters.put("amount", wrongAmountType);
        parameters.put("targetAsset", "USDC");
        parameters.put("accountType", "MAIN");

        Dispatcher dispatcher = MockWebServerDispatcher.getDispatcher(MockData.PREFIX, path, MockData.MOCK_RESPONSE, HttpMethod.POST, MockData.HTTP_STATUS_OK);
        mockWebServer.setDispatcher(dispatcher);

        SpotClientImpl client = new SpotClientImpl(MockData.API_KEY, MockData.SECRET_KEY, baseUrl);
        assertThrows(BinanceConnectorException.class, () -> client.createWallet().busdConvert(parameters).block());
    }

    @Test
    public void testBusdConverWithoutMandatoryParam() {
        String path = "/sapi/v1/asset/convert-transfer";
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("clientTranId", "118263407119");
        parameters.put("amount", amount);
        parameters.put("targetAsset", "USDC");
        parameters.put("accountType", "MAIN");

        Dispatcher dispatcher = MockWebServerDispatcher.getDispatcher(MockData.PREFIX, path, MockData.MOCK_RESPONSE, HttpMethod.POST, MockData.HTTP_STATUS_OK);
        mockWebServer.setDispatcher(dispatcher);

        SpotClientImpl client = new SpotClientImpl(MockData.API_KEY, MockData.SECRET_KEY, baseUrl);
        assertThrows(BinanceConnectorException.class, () -> client.createWallet().busdConvert(parameters).block());
    }

    @Test
    public void testBusdConvert() {
        String path = "/sapi/v1/asset/convert-transfer";
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("clientTranId", "118263407119");
        parameters.put("asset", "BUSD");
        parameters.put("amount", amount);
        parameters.put("targetAsset", "USDC");
        parameters.put("accountType", "MAIN");

        Dispatcher dispatcher = MockWebServerDispatcher.getDispatcher(MockData.PREFIX, path, MockData.MOCK_RESPONSE, HttpMethod.POST, MockData.HTTP_STATUS_OK);
        mockWebServer.setDispatcher(dispatcher);

        SpotClientImpl client = new SpotClientImpl(MockData.API_KEY, MockData.SECRET_KEY, baseUrl);
        String result = client.createWallet().busdConvert(parameters).block();
        assertEquals(MockData.MOCK_RESPONSE, result);
    }
}
