package com.pyryze.binance.websocketapi;

import com.pyryze.binance.connector.client.exceptions.BinanceConnectorException;
import com.pyryze.binance.connector.client.utils.websocketapi.WebSocketApiRequestHandler;
import com.pyryze.binance.MockData;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class TestWebSocketApiRequestHandler {

    private JSONObject params;
    private static final double invalidReqId = 1.2;
    @BeforeEach
    public void init() {
        this.params = new JSONObject();
    }

    @Test
    public void testRequestWithoutWebSocketConnection() {
        assertThrows(BinanceConnectorException.class, () -> new WebSocketApiRequestHandler(null, MockData.API_KEY, MockData.HMAC_SIGNATURE_GENERATOR));
    }

    @Test
    public void testPublicRequestWithoutMandatoryParams() {
        WebSocketApiRequestHandler handler = new WebSocketApiRequestHandler(MockData.WS_CONNECTION, MockData.API_KEY, MockData.HMAC_SIGNATURE_GENERATOR);
        assertThrows(BinanceConnectorException.class, () -> handler.publicRequest("", params));
    }

    @Test
    public void testApiRequestWithoutMandatoryParams() {
        WebSocketApiRequestHandler handler = new WebSocketApiRequestHandler(MockData.WS_CONNECTION, MockData.API_KEY, MockData.HMAC_SIGNATURE_GENERATOR);
        assertThrows(BinanceConnectorException.class, () -> handler.apiRequest("", params));

        WebSocketApiRequestHandler handlerWithoutApiKey = new WebSocketApiRequestHandler(MockData.WS_CONNECTION, null, MockData.HMAC_SIGNATURE_GENERATOR);
        assertThrows(BinanceConnectorException.class, () -> handlerWithoutApiKey.apiRequest("trades.historical", params));
    }


    @Test
    public void testSignedRequestWithoutMandatoryParams() {
        WebSocketApiRequestHandler handler = new WebSocketApiRequestHandler(MockData.WS_CONNECTION, MockData.API_KEY, MockData.HMAC_SIGNATURE_GENERATOR);
        assertThrows(BinanceConnectorException.class, () -> handler.signedRequest("", params));

        WebSocketApiRequestHandler handlerWithoutApiKey = new WebSocketApiRequestHandler(MockData.WS_CONNECTION, null, MockData.HMAC_SIGNATURE_GENERATOR);
        assertThrows(BinanceConnectorException.class, () -> handlerWithoutApiKey.signedRequest("order.test", params));

        WebSocketApiRequestHandler handlerWithoutsignGenerator = new WebSocketApiRequestHandler(MockData.WS_CONNECTION, MockData.API_KEY, null);
        assertThrows(BinanceConnectorException.class, () -> handlerWithoutsignGenerator.signedRequest("order.test", params));
    }


    @Test
    public void testRequestWithInvalidRequestId() {
        WebSocketApiRequestHandler handler = new WebSocketApiRequestHandler(MockData.WS_CONNECTION, MockData.API_KEY, MockData.HMAC_SIGNATURE_GENERATOR);
        params.put("requestId", invalidReqId);
        assertThrows(BinanceConnectorException.class, () -> handler.publicRequest("ping", params));
    }

}

