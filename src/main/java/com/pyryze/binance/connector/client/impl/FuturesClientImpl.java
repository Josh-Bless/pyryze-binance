package com.pyryze.binance.connector.client.impl;

import com.pyryze.binance.connector.client.FuturesClient;
import com.pyryze.binance.connector.client.utils.ProxyAuth;
import com.pyryze.binance.connector.client.utils.HmacSignatureGenerator;
import com.pyryze.binance.connector.client.utils.SignatureGenerator;

public abstract class FuturesClientImpl implements FuturesClient {
    private final SignatureGenerator signatureGenerator;
    private final String apiKey;
    //private final String secretKey;
    private final String baseUrl;
    private final String productUrl;
    private boolean showLimitUsage;
    private ProxyAuth proxy = null;

    public FuturesClientImpl(String baseUrl, String product) {
        this(null, (SignatureGenerator)null, baseUrl, product);
    }

    public FuturesClientImpl(String baseUrl, String product, boolean showLimitUsage) {
        this(null, (SignatureGenerator)null, baseUrl, product);
        this.showLimitUsage = showLimitUsage;
    }

    public FuturesClientImpl(String apiKey, String secretKey, String baseUrl, String product) {
        this(apiKey, secretKey, baseUrl, product, false);
    }

    public FuturesClientImpl(String apiKey, String secretKey, String baseUrl, String product, boolean showLimitUsage) {
        this(apiKey,new HmacSignatureGenerator(secretKey),baseUrl,product);
        this.showLimitUsage = showLimitUsage;
    }
    
    public FuturesClientImpl(String apiKey, SignatureGenerator signatureGenerator, String baseUrl, String product) {
        this.apiKey = apiKey;
        this.signatureGenerator = signatureGenerator;
        this.baseUrl = baseUrl;
        this.productUrl = baseUrl + product;
    }
    
    public String getApiKey() {
        return this.apiKey;
    }

    /*public String getSecretKey() {
        return this.secretKey;
    }*/

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public String getProductUrl() {
        return this.productUrl;
    }
    
    public SignatureGenerator getSignatureGenerator() {
        return this.signatureGenerator;
    }
    
    public boolean getShowLimitUsage() {
        return this.showLimitUsage;
    }

    public void setShowLimitUsage(boolean showLimitUsage) {
        this.showLimitUsage = showLimitUsage;
    }

    public void setProxy(ProxyAuth proxy) {
        this.proxy = proxy;
    }

    public ProxyAuth getProxy() {
        return proxy;
    }

    public void unsetProxy() {
        this.proxy = null;
    }

}
