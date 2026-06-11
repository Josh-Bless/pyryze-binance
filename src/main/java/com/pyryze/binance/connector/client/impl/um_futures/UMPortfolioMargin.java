package com.pyryze.binance.connector.client.impl.um_futures;

import java.util.LinkedHashMap;
import com.pyryze.binance.connector.client.impl.futures.PortfolioMargin;
import com.pyryze.binance.connector.client.utils.ProxyAuth;
import com.pyryze.binance.connector.client.utils.SignatureGenerator;

import reactor.core.publisher.Mono;

/**
 * <h2>USDⓈ-Margined Portfolio Margin Endpoints</h2>
 * All endpoints under the
 * <a href="https://binance-docs.github.io/apidocs/futures/en/#portfolio-margin-endpoints">PortfolioMargin Endpoint</a>
 * section of the API documentation will be implemented in this class.
 * <br>
 * Response will be returned in <i>String format</i>.
 */
public class UMPortfolioMargin extends PortfolioMargin {
    public UMPortfolioMargin(String productUrl, String apiKey, SignatureGenerator signatureGenerator, boolean showLimitUsage, ProxyAuth proxy) {
        super(productUrl, apiKey, signatureGenerator, showLimitUsage, proxy);
    }

    /**
     * Current Portfolio Margin exchange trading rules.
     * GET /v1/pmExchangeInfo
     * <br>
     * @param
     * parameters LinkedHashedMap of String,Object pair
     *            where String is the name of the parameter and Object is the value of the parameter
     * <br><br>
     * symbol -- optional/string <br>
     * @return String
     * @see <a href="https://binance-docs.github.io/apidocs/futures/en/#portfolio-margin-exchange-information">
     *     https://binance-docs.github.io/apidocs/futures/en/#portfolio-margin-exchange-information</a>
     */
    public Mono<String> portfolioMarginExchangeInfo(LinkedHashMap<String, Object> parameters) {
        return super.portfolioMarginExchangeInfo(parameters);
    }
}