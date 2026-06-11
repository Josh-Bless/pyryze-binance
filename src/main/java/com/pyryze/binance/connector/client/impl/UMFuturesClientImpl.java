package com.pyryze.binance.connector.client.impl;

import com.pyryze.binance.connector.client.enums.DefaultUrls;
import com.pyryze.binance.connector.client.impl.um_futures.UMAccount;
import com.pyryze.binance.connector.client.impl.um_futures.UMMarket;
import com.pyryze.binance.connector.client.impl.um_futures.UMPortfolioMargin;
import com.pyryze.binance.connector.client.impl.um_futures.UMUserData;
import com.pyryze.binance.connector.client.utils.SignatureGenerator;

public class UMFuturesClientImpl extends FuturesClientImpl {
    private static String defaultBaseUrl = DefaultUrls.USDM_PROD_URL;
    private static String umProduct = "/fapi";

    public UMFuturesClientImpl() {
        super(defaultBaseUrl, umProduct);
    }

    public UMFuturesClientImpl(String baseUrl) {
        super(baseUrl, umProduct);
    }

    public UMFuturesClientImpl(String apiKey, String secretKey) {
        super(apiKey, secretKey, defaultBaseUrl, umProduct);
    }

    public UMFuturesClientImpl(String baseUrl, boolean showLimitUsage) {
        super(baseUrl, umProduct, showLimitUsage);
    }

    public UMFuturesClientImpl(String apiKey, String secretKey, boolean showLimitUsage) {
        super(apiKey, secretKey, defaultBaseUrl, umProduct, showLimitUsage);
    }

    public UMFuturesClientImpl(String apiKey, String secretKey, String baseUrl) {
        super(apiKey, secretKey, baseUrl, umProduct);
    }
    
    public UMFuturesClientImpl(String apiKey, SignatureGenerator signatureGenerator, String baseUrl){
        super(apiKey,signatureGenerator,baseUrl,umProduct);
    }
    
    @Override
    public UMMarket market() {
        return new UMMarket(getProductUrl(), getBaseUrl(), getApiKey(), getShowLimitUsage(), getProxy());
    }

    @Override
    public UMAccount account() {
        return new UMAccount(getProductUrl(), getApiKey(), getSignatureGenerator(), getShowLimitUsage(), getProxy());
    }

    @Override
    public UMUserData userData() {
        return new UMUserData(getProductUrl(), getApiKey(), getShowLimitUsage(), getProxy());
    }

    @Override
    public UMPortfolioMargin portfolioMargin() {
        return new UMPortfolioMargin(getProductUrl(), getApiKey(), getSignatureGenerator(), getShowLimitUsage(), getProxy());
    }
}
