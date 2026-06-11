package com.pyryze.binance.connector.client.impl.futures;

import com.pyryze.binance.connector.client.enums.HttpMethod;
import com.pyryze.binance.connector.client.utils.ProxyAuth;
import com.pyryze.binance.connector.client.utils.AsyncRequestHandler;
import com.pyryze.binance.connector.client.utils.ParameterChecker;
import com.pyryze.binance.connector.client.utils.SignatureGenerator;
import java.util.LinkedHashMap;

import reactor.core.publisher.Mono;

/**
 * <h2>Portfolio Margin Endpoints</h2>
 * Response will be returned in <i>String format</i>.
 */
public abstract class PortfolioMargin {
    private String productUrl;
    private AsyncRequestHandler asyncRequestHandler;
    private boolean showLimitUsage;

    public PortfolioMargin(String productUrl, String apiKey, SignatureGenerator signatureGenerator, boolean showLimitUsage, ProxyAuth proxy) {
        this.productUrl = productUrl;
        this.asyncRequestHandler = new AsyncRequestHandler(apiKey, signatureGenerator, proxy);
        this.showLimitUsage = showLimitUsage;
    }

    public String getProductUrl() {
        return this.productUrl;
    }

    public AsyncRequestHandler getAsyncRequestHandler() {
        return this.asyncRequestHandler;
    }

    public boolean getShowLimitUsage() {
        return this.showLimitUsage;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public void setAsyncRequestHandler(String apiKey, SignatureGenerator signatureGenerator, ProxyAuth proxy) {
        this.asyncRequestHandler = new AsyncRequestHandler(apiKey, signatureGenerator, proxy);
    }

    public void setShowLimitUsage(boolean showLimitUsage) {
        this.showLimitUsage = showLimitUsage;
    }

    private final String PORTFOLIO_MARGIN_EXCHANGE_INFO = "/v1/pmExchangeInfo";
    public Mono<String> portfolioMarginExchangeInfo(LinkedHashMap<String, Object> parameters) {
        return asyncRequestHandler.sendSignedRequest(productUrl, PORTFOLIO_MARGIN_EXCHANGE_INFO, parameters, HttpMethod.GET, showLimitUsage);
    }

    private final String PORTFOLIO_MARGIN_ACCOUNT_INFO = "/v1/pmAccountInfo";
    /**
     * Get Portfolio Margin current account information.
     * GET /v1/pmAccountInfo
     * <br>
     * @param
     * parameters LinkedHashedMap of String,Object pair
     *            where String is the name of the parameter and Object is the value of the parameter
     * <br><br>
     * asset -- mandatory/string <br>
     * recvWindow -- optional/long <br>
     * @return String
     * @see <a href="https://binance-docs.github.io/apidocs/futures/en/#portfolio-margin-account-information-user_data">
     *     https://binance-docs.github.io/apidocs/futures/en/#portfolio-margin-account-information-user_data</a>
     */
    public Mono<String> portfolioMarginAccountInfo(LinkedHashMap<String, Object> parameters) {
        ParameterChecker.checkParameter(parameters, "asset", String.class);
        return asyncRequestHandler.sendSignedRequest(productUrl, PORTFOLIO_MARGIN_ACCOUNT_INFO, parameters, HttpMethod.GET, showLimitUsage);
    }
}