package com.pyryze.binance.connector.client.impl.spot;

import com.pyryze.binance.connector.client.enums.HttpMethod;
import com.pyryze.binance.connector.client.utils.HmacSignatureGenerator;
import com.pyryze.binance.connector.client.utils.ProxyAuth;
import com.pyryze.binance.connector.client.utils.AsyncRequestHandler;
import com.pyryze.binance.connector.client.utils.SignatureGenerator;
import java.util.LinkedHashMap;

import reactor.core.publisher.Mono;

public class PortfolioMargin {
    /**
     * <h2>Portfolio Margin Endpoints</h2>
     * All endpoints under the
     * <a href="https://binance-docs.github.io/apidocs/spot/en/#portfolio-margin-endpoints">PortfolioMargin Endpoint</a>
     * section of the API documentation will be implemented in this class.
     * <br>
     * Response will be returned in <i>String format</i>.
     */
    private final String baseUrl;
    private final AsyncRequestHandler asyncRequestHandler;
    private final boolean showLimitUsage;

    public PortfolioMargin(String baseUrl, String apiKey, String secretKey, boolean showLimitUsage, ProxyAuth proxy) {
        this.baseUrl = baseUrl;
        this.asyncRequestHandler = new AsyncRequestHandler(apiKey, new HmacSignatureGenerator(secretKey), proxy);
        this.showLimitUsage = showLimitUsage;
    }

    public PortfolioMargin(String baseUrl, String apiKey, SignatureGenerator signatureGenerator, boolean showLimitUsage, ProxyAuth proxy) {
        this.baseUrl = baseUrl;
        this.asyncRequestHandler = new AsyncRequestHandler(apiKey, signatureGenerator, proxy);
        this.showLimitUsage = showLimitUsage;
    }

    private final String ACCOUNT = "/sapi/v1/portfolio/account";
    /**
     * GET /sapi/v1/portfolio/account
     * <br>
     * @param
     * parameters LinkedHashedMap of String,Object pair
     *            where String is the name of the parameter and Object is the value of the parameter
     * <br><br>
     * recvWindow -- optional/long <br>
     * @return String
     * @see <a href="https://binance-docs.github.io/apidocs/spot/en/#get-portfolio-margin-account-info-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-portfolio-margin-account-info-user_data</a>
     */
    public Mono<String> getAccount(LinkedHashMap<String, Object> parameters) {
        return asyncRequestHandler.sendSignedRequest(baseUrl, ACCOUNT, parameters, HttpMethod.GET, showLimitUsage);
    }

    private final String COLLATERAL_RATE = "/sapi/v1/portfolio/collateralRate";
    /**
     * Portfolio Margin Collateral Rate
     * <br><br>
     * GET /sapi/v1/portfolio/collateralRate
     * <br>
     * @param
     * parameters LinkedHashedMap of String,Object pair
     *            where String is the name of the parameter and Object is the value of the parameter
     * <br><br>
     * recvWindow -- optional/long <br>
     * @return String
     * @see <a href="https://binance-docs.github.io/apidocs/spot/en/#portfolio-margin-collateral-rate-market_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#portfolio-margin-collateral-rate-market_data</a>
     */
    public Mono<String> collateralRate(LinkedHashMap<String, Object> parameters) {
        return asyncRequestHandler.sendSignedRequest(baseUrl, COLLATERAL_RATE, parameters, HttpMethod.GET, showLimitUsage);
    }

    private final String PM_LOAN = "/sapi/v1/portfolio/pmLoan";
    /**
     * Query Portfolio Margin Bankruptcy Loan Record
     * <br><br>
     * GET /sapi/v1/portfolio/pmLoan
     * <br>
     * @param
     * parameters LinkedHashedMap of String,Object pair
     *            where String is the name of the parameter and Object is the value of the parameter
     * <br><br>
     * recvWindow -- optional/long <br>
     * @return String
     * @see <a href="https://binance-docs.github.io/apidocs/spot/en/#query-portfolio-margin-bankruptcy-loan-record-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-portfolio-margin-bankruptcy-loan-record-user_data</a>
     */
    public Mono<String> bankruptcyLoanRecord(LinkedHashMap<String, Object> parameters) {
        return asyncRequestHandler.sendSignedRequest(baseUrl, PM_LOAN, parameters, HttpMethod.GET, showLimitUsage);
    }

    private final String REPAY = "/sapi/v1/portfolio/repay";
    /**
     * Repay Portfolio Margin Bankruptcy Loan
     * <br><br>
     * POST /sapi/v1/portfolio/repay
     * <br>
     * @param
     * parameters LinkedHashedMap of String,Object pair
     *            where String is the name of the parameter and Object is the value of the parameter
     * <br><br>
     * recvWindow -- optional/long <br>
     * @return String
     * @see <a href="https://binance-docs.github.io/apidocs/spot/en/#portfolio-margin-bankruptcy-loan-repay">
     *     https://binance-docs.github.io/apidocs/spot/en/#portfolio-margin-bankruptcy-loan-repay</a>
     */
    public Mono<String> repay(LinkedHashMap<String, Object> parameters) {
        return asyncRequestHandler.sendSignedRequest(baseUrl, REPAY, parameters, HttpMethod.POST, showLimitUsage);
    }
}
