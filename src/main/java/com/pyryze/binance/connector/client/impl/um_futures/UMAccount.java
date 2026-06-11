package com.pyryze.binance.connector.client.impl.um_futures;

import com.pyryze.binance.connector.client.enums.HttpMethod;
import com.pyryze.binance.connector.client.utils.ParameterChecker;
import java.util.LinkedHashMap;
import com.pyryze.binance.connector.client.impl.futures.Account;
import com.pyryze.binance.connector.client.utils.ProxyAuth;
import com.pyryze.binance.connector.client.utils.SignatureGenerator;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <h2>USDⓈ-Margined Trade Endpoints</h2>
 * All endpoints under the
 * <a href="https://binance-docs.github.io/apidocs/futures/en/#account-trades-endpoints">Futures Account/Trade Endpoint</a>
 * section of the API documentation will be implemented in this class.
 * <br>
 * Response will be returned in <i>String format</i>.
 */
public class UMAccount extends Account {
    public UMAccount(String productUrl, String apiKey, SignatureGenerator signatureGenerator, boolean showLimitUsage, ProxyAuth proxy) {
        super(productUrl, apiKey, signatureGenerator, showLimitUsage, proxy);
    }

    private final String MULTI_ASSETS_MARGIN = "/v1/multiAssetsMargin";
    /**
     * Change user's Multi-Assets mode (Multi-Assets Mode or Single-Asset Mode) on Every symbol
     * <br><br>
     * POST /v1/multiAssetsMargin
     * <br>
     * @param
     * parameters LinkedHashedMap of String,Object pair
     *            where String is the name of the parameter and Object is the value of the parameter
     * <br><br>
     * multiAssetsMargin -- mandatory/string <br>
     * recvWindow -- optional/long <br>
     * @return String
     * @see <a href="https://binance-docs.github.io/apidocs/futures/en/#change-multi-assets-mode-trade">
     *     https://binance-docs.github.io/apidocs/futures/en/#change-multi-assets-mode-trade</a>
     */
    public Mono<String> changeMultiAssetsMode(LinkedHashMap<String, Object> parameters) {
        ParameterChecker.checkParameter(parameters, "dualSidePosition", String.class);
        return getRequestHandler().sendSignedRequest(getProductUrl(), MULTI_ASSETS_MARGIN, parameters, HttpMethod.POST, getShowLimitUsage());
    }

    /**
     * Get user's Multi-Assets mode (Multi-Assets Mode or Single-Asset Mode) on Every symbol
     * <br><br>
     * GET /v1/multiAssetsMargin
     * <br>
     * @param
     * parameters LinkedHashedMap of String,Object pair
     *            where String is the name of the parameter and Object is the value of the parameter
     * <br><br>
     * recvWindow -- optional/long <br>
     * @return String
     * @see <a href="https://binance-docs.github.io/apidocs/futures/en/#get-current-multi-assets-mode-user_data">
     *     https://binance-docs.github.io/apidocs/futures/en/#get-current-multi-assets-mode-user_data</a>
     */
    public Mono<String> getCurrentMultiAssetMode(LinkedHashMap<String, Object> parameters) {
        return getRequestHandler().sendSignedRequest(getProductUrl(), MULTI_ASSETS_MARGIN, parameters, HttpMethod.GET, getShowLimitUsage());
    }


    /**
     * Get all open orders on a symbol. Careful when accessing this with no symbol.
     * <br><br>
     * GET /v1/openOrders
     * <br>
     * @param
     * parameters LinkedHashedMap of String,Object pair
     *            where String is the name of the parameter and Object is the value of the parameter
     * <br><br>
     * symbol -- optional/string <br>
     * recvWindow -- optional/long <br>
     * @return String
     * @see <a href="https://binance-docs.github.io/apidocs/futures/en/#current-all-open-orders-user_data">
     *    https://binance-docs.github.io/apidocs/futures/en/#current-all-open-orders-user_data</a>
     */
    public Mono<String> currentAllOpenOrders(LinkedHashMap<String, Object> parameters) {
        return super.currentAllOpenOrders(parameters);
    }

    /**
     * Get all open orders on a symbol. Careful when accessing this with no symbol.
     * <br><br>
     * GET /v1/allOrders
     * <br>
     * @param
     * parameters LinkedHashedMap of String,Object pair
     *            where String is the name of the parameter and Object is the value of the parameter
     * <br><br>
     * symbol -- mandatory/string <br>
     * orderId -- optional/long <br>
     * startTime -- optional/long <br>
     * endTime -- optional/long <br>
     * limit -- optional/integer <br>
     * recvWindow -- optional/long <br>
     * @return String
     * @see <a href="https://binance-docs.github.io/apidocs/futures/en/#all-orders-user_data">
     *    https://binance-docs.github.io/apidocs/futures/en/#all-orders-user_data</a>
     */
    public Mono<String> allOrders(LinkedHashMap<String, Object> parameters) {
        ParameterChecker.checkParameter(parameters, "symbol", String.class);
        return super.allOrders(parameters);
    }

    private final String BALANCE = "/v3/balance";
    /**
     * Get Futures Account Balance
     * <br><br>
     * GET /v3/balance
     * <br>
     * @param
     * parameters LinkedHashedMap of String,Object pair
     *            where String is the name of the parameter and Object is the value of the parameter
     * <br><br>
     * recvWindow -- optional/long <br>
     * @return String
     * @see <a href="https://binance-docs.github.io/apidocs/futures/en/#futures-account-balance-v3-user_data">
     *    https://binance-docs.github.io/apidocs/futures/en/#futures-account-balance-v2-user_data</a>
     */
    public Mono<String> futuresAccountBalance(LinkedHashMap<String, Object> parameters) {
        return getRequestHandler().sendSignedRequest(getProductUrl(), BALANCE, parameters, HttpMethod.GET, getShowLimitUsage());
    }

    private final String ACCOUNT_INFORMATION = "/v3/account";
    /**
     * Get current account information. User in single-asset/ multi-assets mode will see different value, see comments in response section for detail.
     * <br><br>
     * GET /v3/account
     * <br>
     * @param
     * parameters LinkedHashedMap of String,Object pair
     *            where String is the name of the parameter and Object is the value of the parameter
     * <br><br>
     * recvWindow -- optional/long <br>
     * @return String
     * @see <a href="https://binance-docs.github.io/apidocs/futures/en/#account-information-v3-user_data">
     *    https://binance-docs.github.io/apidocs/futures/en/#account-information-v2-user_data</a>
     */
    public Mono<String> accountInformation(LinkedHashMap<String, Object> parameters) {
        return getRequestHandler().sendSignedRequest(getProductUrl(), ACCOUNT_INFORMATION, parameters, HttpMethod.GET, getShowLimitUsage());
    }

    private final String POSITION_RISK = "/v2/positionRisk";
    /**
     * Get current position information.
     * <br><br>
     * GET /v2/positionRisk
     * <br>
     * @param
     * parameters LinkedHashedMap of String,Object pair
     *            where String is the name of the parameter and Object is the value of the parameter
     * <br><br>
     * symbol -- optional/string <br>
     * recvWindow -- optional/long <br>
     * @return String
     * @see <a href="https://binance-docs.github.io/apidocs/futures/en/#position-information-v2-user_data">
     *    https://binance-docs.github.io/apidocs/futures/en/#position-information-v2-user_data</a>
     */
    public Mono<String> positionInformation(LinkedHashMap<String, Object> parameters) {
        return getRequestHandler().sendSignedRequest(getProductUrl(), POSITION_RISK, parameters, HttpMethod.GET, getShowLimitUsage());
    }

    /**
     * Get trades for a specific account and symbol.
     * <br><br>
     * GET /v1/userTrades
     * <br>
     * @param
     * parameters LinkedHashedMap of String,Object pair
     *            where String is the name of the parameter and Object is the value of the parameter
     * <br><br>
     * symbol -- mandatory/string <br>
     * startTime -- optional/long <br>
     * endTime -- optional/long <br>
     * fromId -- optional/long <br>
     * limit -- optional/integer <br>
     * recvWindow -- optional/long <br>
     * @return String
     * @see <a href="https://binance-docs.github.io/apidocs/futures/en/#account-trade-list-user_data">
     *    https://binance-docs.github.io/apidocs/futures/en/#account-trade-list-user_data</a>
     */
    public Mono<String> accountTradeList(LinkedHashMap<String, Object> parameters) {
        ParameterChecker.checkParameter(parameters, "symbol", String.class);
        return super.accountTradeList(parameters);
    }

    /**
     * Notional and Leverage Brackets
     * <br><br>
     * GET /v1/leverageBracket
     * <br>
     * @param
     * parameters LinkedHashedMap of String,Object pair
     *            where String is the name of the parameter and Object is the value of the parameter
     * <br><br>
     * symbol -- optional/string <br>
     * recvWindow -- optional/long <br>
     * @return String
     * @see <a href="https://binance-docs.github.io/apidocs/futures/en/#notional-and-leverage-brackets-user_data">
     *    https://binance-docs.github.io/apidocs/futures/en/#notional-and-leverage-brackets-user_data</a>
     */
    public Mono<String> getLeverageBracket(LinkedHashMap<String, Object> parameters) {
        return super.getLeverageBracket(parameters);
    }

    private final String API_TRADING_STATUS = "/v1/apiTradingStatus";
    /**
     * Futures Trading Quantitative Rules Indicators
     * For more information on this, please refer to the <a href="https://www.binance.com/en/support/faq/4f462ebe6ff445d4a170be7d9e897272">Futures Trading Quantitative Rules</a>
     * <br><br>
     * GET /v1/apiTradingStatus
     * <br>
     * @param
     * parameters LinkedHashedMap of String,Object pair
     *            where String is the name of the parameter and Object is the value of the parameter
     * <br><br>
     * symbol -- optional/string <br>
     * recvWindow -- optional/long <br>
     * @return String
     * @see <a href="https://binance-docs.github.io/apidocs/futures/en/#futures-trading-quantitative-rules-indicators-user_data">
     *    https://binance-docs.github.io/apidocs/futures/en/#futures-trading-quantitative-rules-indicators-user_data</a>
     */
    public Mono<String> getTradingRulesIndicators(LinkedHashMap<String, Object> parameters) {
        return getRequestHandler().sendSignedRequest(getProductUrl(), API_TRADING_STATUS, parameters, HttpMethod.GET, getShowLimitUsage());
    }

    private final String INCOME_ASYN = "/v1/income/asyn";
    /**
     * Get Download Id For Futures Transaction History
     * <br><br>
     * GET /v1/income/asyn
     * <br>
     * @param
     * parameters LinkedHashedMap of String,Object pair
     *            where String is the name of the parameter and Object is the value of the parameter
     * <br><br>
     * startTime -- optional/long <br>
     * endTime -- optional/long <br>
     * recvWindow -- optional/long <br>
     * @return String
     * @see <a href="https://binance-docs.github.io/apidocs/futures/en/#get-download-id-for-futures-transaction-history-user_data">
     *    https://binance-docs.github.io/apidocs/futures/en/#get-download-id-for-futures-transaction-history-user_data</a>
     */
    public Mono<String> futuresDownloadId(LinkedHashMap<String, Object> parameters) {
        return getRequestHandler().sendSignedRequest(getProductUrl(), INCOME_ASYN, parameters, HttpMethod.GET, getShowLimitUsage());
    }

    private final String INCOME_ASYN_ID = "/v1/income/asyn/id";
    /**
     * Get Futures Transaction History Download Link by Id
     * <br><br>
     * GET /v1/income/asyn/id
     * <br>
     * @param
     * parameters LinkedHashedMap of String,Object pair
     *            where String is the name of the parameter and Object is the value of the parameter
     * <br><br>
     * downloadId -- mandatory/string <br>
     * recvWindow -- optional/long <br>
     * @return String
     * @see <a href="https://binance-docs.github.io/apidocs/futures/en/#get-futures-transaction-history-download-link-by-id-user_data">
     *    https://binance-docs.github.io/apidocs/futures/en/#get-futures-transaction-history-download-link-by-id-user_data</a>
     */
    public Mono<String> futuresDownloadLink(LinkedHashMap<String, Object> parameters) {
        ParameterChecker.checkParameter(parameters, "downloadId", String.class);
        return getRequestHandler().sendSignedRequest(getProductUrl(), INCOME_ASYN_ID, parameters, HttpMethod.GET, getShowLimitUsage());
    }
}
