package com.pyryze.binance.connector.client.enums;

public final class DefaultUrls {
    public static final String PROD_URL = "https://api.binance.com";
    public static final String WS_URL = "wss://stream.binance.com:9443";
    public static final String WS_API_URL = "wss://ws-api.binance.com:443/ws-api/v3";
    public static final String TESTNET_WS_API_URL = "wss://testnet.binance.vision/ws-api/v3";
    public static final String TESTNET_URL = "https://testnet.binance.vision";
    public static final String TESTNET_WSS_URL = "wss://testnet.binance.vision";
    
    public static final String USDM_TESTNET_URL = "https://testnet.binancefuture.com";
    public static final String CM_TESTNET_WSS_URL = "wss://stream.binancefuture.com";
    //USD-M Futures
    public static final String USDM_PROD_URL = "https://fapi.binance.com";
    public static final String USDM_WS_URL = "wss://fstream.binance.com";
    //COIN-M Futures
    public static final String COINM_PROD_URL = "https://dapi.binance.com";
    public static final String COINM_WS_URL = "wss://dstream.binance.com";
    
    private DefaultUrls() {
    }
}
