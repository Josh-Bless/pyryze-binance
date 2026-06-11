package com.pyryze.binance.connector.client;

import com.pyryze.binance.connector.client.impl.websocketapi.WebSocketApiAccount;
import com.pyryze.binance.connector.client.impl.websocketapi.WebSocketApiGeneral;
import com.pyryze.binance.connector.client.impl.websocketapi.WebSocketApiMarket;
import com.pyryze.binance.connector.client.impl.websocketapi.WebSocketApiTrade;
import com.pyryze.binance.connector.client.impl.websocketapi.WebSocketApiUserDataStream;
import com.pyryze.binance.connector.client.utils.WebSocketCallback;

public interface WebsocketApiClient {
    void connect(WebSocketCallback onMessageCallback);
    void connect(WebSocketCallback onOpenCallback, WebSocketCallback onMessageCallback, WebSocketCallback onClosingCallback, WebSocketCallback onFailureCallback);
    void close();
    WebSocketApiGeneral general();
    WebSocketApiMarket market();
    WebSocketApiTrade trade();
    WebSocketApiAccount account();
    WebSocketApiUserDataStream userDataStream();
}