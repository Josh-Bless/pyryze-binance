package com.pyryze.binance.connector.client.impl;

import com.pyryze.binance.connector.client.enums.DefaultUrls;
import com.pyryze.binance.connector.client.impl.cm_futures.CMAccount;
import com.pyryze.binance.connector.client.impl.futures.*;
import com.pyryze.binance.connector.client.utils.SignatureGenerator;

public class CMFuturesClientImpl extends FuturesClientImpl {
    private static String defaultBaseUrl = DefaultUrls.COINM_PROD_URL;
    private static String cmProduct = "/dapi";

    public CMFuturesClientImpl() {
        super(defaultBaseUrl, cmProduct);
    }

    public CMFuturesClientImpl(String baseUrl) {
        super(baseUrl, cmProduct);
    }

    public CMFuturesClientImpl(String apiKey, String secretKey) {
        super(apiKey, secretKey, defaultBaseUrl, cmProduct);
    }

    public CMFuturesClientImpl(String baseUrl, boolean showLimitUsage) {
        super(baseUrl, cmProduct, showLimitUsage);
    }

    public CMFuturesClientImpl(String apiKey, String secretKey, boolean showLimitUsage) {
        super(apiKey, secretKey, defaultBaseUrl, cmProduct, showLimitUsage);
    }

    public CMFuturesClientImpl(String apiKey, String secretKey, String baseUrl) {
        super(apiKey, secretKey, baseUrl, cmProduct);
    }
    
    public CMFuturesClientImpl(String apiKey, SignatureGenerator signatureGenerator, String baseUrl){
        super(apiKey,signatureGenerator,baseUrl,cmProduct);
    }
    
    @Override
    public CMAccount account() {
        return new CMAccount(getProductUrl(), getApiKey(), getSignatureGenerator(), getShowLimitUsage(), getProxy());
    }
    
    @Override
    public Market market(){
        return null;
    }
    
    @Override
    public UserData userData(){
        return null;
    }
    
    @Override
    public PortfolioMargin portfolioMargin(){
        return null;
    }
}
