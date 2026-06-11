package com.pyryze.binance.connector.client.impl;

import com.pyryze.binance.connector.client.SpotClient;
import com.pyryze.binance.connector.client.enums.DefaultUrls;
import com.pyryze.binance.connector.client.impl.spot.Futures;
import com.pyryze.binance.connector.client.impl.spot.Margin;
import com.pyryze.binance.connector.client.impl.spot.PortfolioMargin;
import com.pyryze.binance.connector.client.impl.spot.SubAccount;
import com.pyryze.binance.connector.client.impl.spot.Trade;
import com.pyryze.binance.connector.client.impl.spot.UserData;
import com.pyryze.binance.connector.client.impl.spot.Wallet;
import com.pyryze.binance.connector.client.utils.HmacSignatureGenerator;
import com.pyryze.binance.connector.client.utils.ProxyAuth;
import com.pyryze.binance.connector.client.utils.SignatureGenerator;

public class SpotClientImpl implements SpotClient {
    private final String apiKey;
    private final SignatureGenerator signatureGenerator;
    private final String baseUrl;
    private boolean showLimitUsage = false;
    private ProxyAuth proxy = null;

    public SpotClientImpl() {
        this(DefaultUrls.PROD_URL);
    }

    public SpotClientImpl(String baseUrl) {
        this("", (SignatureGenerator) null, baseUrl);
    }

    public SpotClientImpl(String baseUrl, boolean showLimitUsage) {
        this(baseUrl);
        this.showLimitUsage = showLimitUsage;
    }

    public SpotClientImpl(String apiKey, String secretKey) {
        this(apiKey, secretKey, DefaultUrls.PROD_URL);
    }

    public SpotClientImpl(String apiKey, String secretKey, String baseUrl) {
        this(apiKey, new HmacSignatureGenerator(secretKey), baseUrl);
    }

    public SpotClientImpl(String apiKey, SignatureGenerator signatureGenerator, String baseUrl) {
        this.apiKey = apiKey;
        this.signatureGenerator = signatureGenerator;
        this.baseUrl = baseUrl;
    }

    public void setShowLimitUsage(boolean showLimitUsage) {
        this.showLimitUsage = showLimitUsage;
    }

    public void setProxy(ProxyAuth proxy) {
        this.proxy = proxy;
    }

    public void unsetProxy() {
        this.proxy = null;
    }

    @Override
    public Futures createFutures() {
        return new Futures(baseUrl, apiKey, signatureGenerator, showLimitUsage, proxy);
    }

    @Override
    public Margin createMargin() {
        return new Margin(baseUrl, apiKey, signatureGenerator, showLimitUsage, proxy);
    }

    @Override
    public PortfolioMargin createPortfolioMargin() {
        return new PortfolioMargin(baseUrl, apiKey, signatureGenerator, showLimitUsage, proxy);
    }

    @Override
    public SubAccount createSubAccount() {
        return new SubAccount(baseUrl, apiKey, signatureGenerator, showLimitUsage, proxy);
    }

    @Override
    public Trade createTrade() {
        return new Trade(baseUrl, apiKey, signatureGenerator, showLimitUsage, proxy);
    }

    @Override
    public UserData createUserData() {
        return new UserData(baseUrl, apiKey, showLimitUsage, proxy);
    }

    @Override
    public Wallet createWallet() {
        return new Wallet(baseUrl, apiKey, signatureGenerator, showLimitUsage, proxy);
    }
}
