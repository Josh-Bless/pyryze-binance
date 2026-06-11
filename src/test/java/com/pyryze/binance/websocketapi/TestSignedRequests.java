package com.pyryze.binance.websocketapi;

import com.pyryze.binance.connector.client.exceptions.BinanceConnectorException;
import com.pyryze.binance.connector.client.impl.WebsocketApiClientImpl;
import com.pyryze.binance.connector.client.utils.HmacSignatureGenerator;
import com.pyryze.binance.connector.client.utils.RsaSignatureGenerator;
import com.pyryze.binance.MockData;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class TestSignedRequests {

    @Test
    public void testHmacSignatureGeneratorWithoutSecretKey() {
        assertThrows(BinanceConnectorException.class, () ->  new HmacSignatureGenerator(""));
        assertThrows(BinanceConnectorException.class, () ->  new HmacSignatureGenerator(null));
    }


    @Test
    public void testRsaSignatureGeneratorWithoutPrivateKey() {
        assertThrows(BinanceConnectorException.class, () ->  new RsaSignatureGenerator(""));
        assertThrows(BinanceConnectorException.class, () ->  new RsaSignatureGenerator(null));
    }

    @Test
    public void testSignedRequestWithoutConnectingFirst() {
        HmacSignatureGenerator signatureGenerator = new HmacSignatureGenerator(MockData.SECRET_KEY);
        WebsocketApiClientImpl client = new WebsocketApiClientImpl(MockData.API_KEY, signatureGenerator, MockData.WS_BASE_URL);
        assertThrows(BinanceConnectorException.class, () -> client.general().ping(null));
    }
    
    @Test
    public void testSignedRequestWithoutSignatureGenerator() {
        WebsocketApiClientImpl client = new WebsocketApiClientImpl();
        client.connect(((event) -> {
            System.out.println(event);
        }));
        assertThrows(BinanceConnectorException.class, () -> client.account().accountStatus(null));
    }

    @Test
    public void testSignedRequestWithoutApiKey() {
        HmacSignatureGenerator signatureGenerator = new HmacSignatureGenerator(MockData.SECRET_KEY);
        WebsocketApiClientImpl client = new WebsocketApiClientImpl("", signatureGenerator, MockData.WS_BASE_URL);
        client.connect(((event) -> {
            System.out.println(event);
        }));
        assertThrows(BinanceConnectorException.class, () -> client.account().accountStatus(null));
    }
}
