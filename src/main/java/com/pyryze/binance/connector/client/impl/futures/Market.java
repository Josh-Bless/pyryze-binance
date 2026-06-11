package com.pyryze.binance.connector.client.impl.futures;

import com.pyryze.binance.connector.client.enums.HttpMethod;
import com.pyryze.binance.connector.client.utils.ParameterChecker;
import com.pyryze.binance.connector.client.utils.ProxyAuth;
import com.pyryze.binance.connector.client.utils.AsyncRequestHandler;
import com.pyryze.binance.connector.client.utils.SignatureGenerator;
import java.util.LinkedHashMap;

import reactor.core.publisher.Mono;

/**
 * <h2>Market Endpoints</h2>
 * Response will be returned in <i>String format</i>.
 */
public abstract class Market {
    private String baseUrl;
    private String productUrl;
    private AsyncRequestHandler asyncRequestHandler;
    private boolean showLimitUsage;

    public Market(String productUrl, String baseUrl, String apiKey, boolean showLimitUsage, ProxyAuth proxy) {
        this.baseUrl = baseUrl;
        this.productUrl = productUrl;
        this.asyncRequestHandler = new AsyncRequestHandler(apiKey, proxy);
        this.showLimitUsage = showLimitUsage;
    }

    public String getBaseUrl() {
        return this.baseUrl;
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

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public void setAsyncRequestHandler(String apiKey, SignatureGenerator signatureGenerator, ProxyAuth proxy) {
        asyncRequestHandler = new AsyncRequestHandler(apiKey, signatureGenerator, proxy);
    }

    public void setShowLimitUsage(boolean showLimitUsage) {
        this.showLimitUsage = showLimitUsage;
    }

    private final String MARK_PRICE = "/v1/premiumIndex";
    public Mono<String> markPrice(LinkedHashMap<String, Object> parameters) {
        return asyncRequestHandler.sendPublicRequest(productUrl, MARK_PRICE, parameters, HttpMethod.GET, showLimitUsage);
    }

    private final String TICKER_24H = "/v1/ticker/24hr";
    public Mono<String> ticker24H(LinkedHashMap<String, Object> parameters) {
        return asyncRequestHandler.sendPublicRequest(productUrl, TICKER_24H, parameters, HttpMethod.GET, showLimitUsage);
    }

    private final String TICKER_SYMBOL = "/v1/ticker/price";
    public Mono<String> tickerSymbol(LinkedHashMap<String, Object> parameters) {
        return asyncRequestHandler.sendPublicRequest(productUrl, TICKER_SYMBOL, parameters, HttpMethod.GET, showLimitUsage);
    }

    private final String BOOK_TICKER = "/v1/ticker/bookTicker";
    public Mono<String> bookTicker(LinkedHashMap<String, Object> parameters) {
        return asyncRequestHandler.sendPublicRequest(productUrl, BOOK_TICKER, parameters, HttpMethod.GET, showLimitUsage);
    }

    private final String OPEN_INTEREST_STATS = "/futures/data/openInterestHist";
    public Mono<String> openInterestStatistics(LinkedHashMap<String, Object> parameters) {
        return asyncRequestHandler.sendPublicRequest(baseUrl, OPEN_INTEREST_STATS, parameters, HttpMethod.GET, showLimitUsage);
    }

    private final String TOP_TRADER_LONG_SHORT_RATIO_POSITIONS = "/futures/data/topLongShortPositionRatio";
    public Mono<String> topTraderLongShortPos(LinkedHashMap<String, Object> parameters) {
        return asyncRequestHandler.sendPublicRequest(baseUrl, TOP_TRADER_LONG_SHORT_RATIO_POSITIONS, parameters, HttpMethod.GET, showLimitUsage);
    }

    private final String TOP_TRADER_LONG_SHORT_RATIO_ACCOUNTS = "/futures/data/topLongShortAccountRatio";
    public Mono<String> topTraderLongShortAccs(LinkedHashMap<String, Object> parameters) {
        return asyncRequestHandler.sendPublicRequest(baseUrl, TOP_TRADER_LONG_SHORT_RATIO_ACCOUNTS, parameters, HttpMethod.GET, showLimitUsage);
    }

    private final String GLOBAL_LONG_SHORT = "/futures/data/globalLongShortAccountRatio";
    public Mono<String> longShortRatio(LinkedHashMap<String, Object> parameters) {
        return asyncRequestHandler.sendPublicRequest(baseUrl, GLOBAL_LONG_SHORT, parameters, HttpMethod.GET, showLimitUsage);
    }


    private final String PING = "/v1/ping";
    /**
     * Test connectivity to the Rest API.
     * <br><br>
     * GET /v1/ping
     * <br>
     * @return String
     * @see <a href="https://binance-docs.github.io/apidocs/futures/en/#test-connectivity">
     *     https://binance-docs.github.io/apidocs/futures/en/#test-connectivity</a>
     * @see <a href="https://binance-docs.github.io/apidocs/delivery/en/#test-connectivity">
     * https://binance-docs.github.io/apidocs/delivery/en/#test-connectivity</a>
     */
    public Mono<String> ping() {
        return asyncRequestHandler.sendPublicRequest(productUrl, PING, null, HttpMethod.GET, showLimitUsage);
    }

    private final String TIME = "/v1/time";
    /**
     * Test connectivity to the Rest API and get the current server time.
     * <br><br>
     * GET /api/v1/time
     * <br>
     * @return String
     * @see <a href="https://binance-docs.github.io/apidocs/futures/en/#check-server-time">
     *     https://binance-docs.github.io/apidocs/futures/en/#check-server-time</a>
     * @see <a href="https://binance-docs.github.io/apidocs/delivery/en/#check-server-time">
     *     https://binance-docs.github.io/apidocs/delivery/en/#check-server-time</a>
     */
    public Mono<String> time() {
        return asyncRequestHandler.sendPublicRequest(productUrl, TIME, null, HttpMethod.GET, showLimitUsage);
    }

    private final String EXCHANGE_INFO = "/v1/exchangeInfo";
    /**
     * Current exchange trading rules and symbol information.
     * <br><br>
     * GET /v1/exchangeinfo
     * <br>
     * @return String
     * @see <a href="https://binance-docs.github.io/apidocs/futures/en/#exchange-information">
     *     https://binance-docs.github.io/apidocs/futures/en/#exchange-information</a>
     * @see <a href="https://binance-docs.github.io/apidocs/delivery/en/#exchange-information">
     *     https://binance-docs.github.io/apidocs/delivery/en/#exchange-information</a>
     */
    public Mono<String> exchangeInfo() {
        return asyncRequestHandler.sendPublicRequest(productUrl, EXCHANGE_INFO, null, HttpMethod.GET, showLimitUsage);
    }

    private final String DEPTH = "/v1/depth";
    /**
     * GET /v1/depth
     * <br>
     * @param
     * parameters LinkedHashedMap of String,Object pair
     *            where String is the name of the parameter and Object is the value of the parameter
     * <br><br>
     * symbol -- mandatory/string <br>
     * limit -- optional/integer -- limit the results
     *            Default 100; max 5000. Valid limits:[5, 10, 20, 50, 100, 500, 1000, 5000] <br>
     * @return String
     * @see <a href="https://binance-docs.github.io/apidocs/futures/en/#order-book">
     *     https://binance-docs.github.io/apidocs/futures/en/#order-book</a>
     * @see <a href="https://binance-docs.github.io/apidocs/delivery/en/#order-book">
     *     https://binance-docs.github.io/apidocs/delivery/en/#order-book</a>
     */
    public Mono<String> depth(LinkedHashMap<String, Object> parameters) {
        ParameterChecker.checkParameter(parameters, "symbol", String.class);
        return asyncRequestHandler.sendPublicRequest(productUrl, DEPTH, parameters, HttpMethod.GET, showLimitUsage);
    }

    private final String TRADES = "/v1/trades";
    /**
     * Get recent trades.
     * <br><br>
     * GET /v1/trades
     * <br>
     * @param
     * parameters LinkedHashedMap of String,Object pair
     *            where String is the name of the parameter and Object is the value of the parameter
     * <br><br>
     * symbol -- mandatory/string <br>
     * limit -- optional/integer -- limit the results Default 500; max 1000 <br>
     * @return String
     * @see <a href="https://binance-docs.github.io/apidocs/futures/en/#recent-trades-list">
     *     https://binance-docs.github.io/apidocs/futures/en/#recent-trades-list</a>
     * @see <a href="https://binance-docs.github.io/apidocs/delivery/en/#recent-trades-list">
     *     https://binance-docs.github.io/apidocs/delivery/en/#recent-trades-list</a>
     */
    public Mono<String> trades(LinkedHashMap<String, Object> parameters) {
        ParameterChecker.checkParameter(parameters, "symbol", String.class);
        return asyncRequestHandler.sendPublicRequest(productUrl, TRADES, parameters, HttpMethod.GET, showLimitUsage);
    }

    private final String HISTORICAL_TRADES = "/v1/historicalTrades";
    /**
     * Get older market trades.
     * <br><br>
     * GET /v1/historicalTrades
     * <br>
     * @param
     * parameters LinkedHashedMap of String,Object pair
     *            where String is the name of the parameter and Object is the value of the parameter
     * <br><br>
     * symbol -- mandatory/string <br>
     * limit -- optional/integer -- limit the result Default 500; max 1000 <br>
     * fromId -- optional/long -- trade id to fetch from. Default gets most recent trades <br>
     * @return String
     * @see <a href="https://binance-docs.github.io/apidocs/futures/en/#old-trades-lookup-market_data">
     *     https://binance-docs.github.io/apidocs/futures/en/#old-trades-lookup-market_data</a>
     * @see <a href="https://binance-docs.github.io/apidocs/delivery/en/#old-trades-lookup-market_data">
     *     https://binance-docs.github.io/apidocs/delivery/en/#old-trades-lookup-market_data</a>
     *
     */
    public Mono<String> historicalTrades(LinkedHashMap<String, Object> parameters) {
        ParameterChecker.checkParameter(parameters, "symbol", String.class);
        return asyncRequestHandler.sendWithApiKeyRequest(productUrl, HISTORICAL_TRADES, parameters, HttpMethod.GET, showLimitUsage);
    }

    private final String AGG_TRADES = "/v1/aggTrades";
    /**
     * Get compressed, aggregate trades. Trades that fill at the time, from the same order,
     * with the same price will have the quantity aggregated.
     * <br><br>
     * GET /v1/aggTrades
     * <br>
     * @param
     * parameters LinkedHashedMap of String,Object pair
     *            where String is the name of the parameter and Object is the value of the parameter
     * <br><br>
     * symbol -- mandatory/string <br>
     * fromId -- optional/long -- id to get aggregate trades from INCLUSIVE <br>
     * startTime -- optional/long -- Timestamp in ms to get aggregate trades from INCLUSIVE <br>
     * endTime -- optional/long -- Timestamp in ms to get aggregate trades until INCLUSIVE <br>
     * limit -- optional/integer -- limit the results Default 500; max 1000 <br>
     * @return String
     * @see <a href="https://binance-docs.github.io/apidocs/futures/en/#compressed-aggregate-trades-list">
     *     https://binance-docs.github.io/apidocs/futures/en/#compressed-aggregate-trades-list</a>
     * @see <a href="https://binance-docs.github.io/apidocs/delivery/en/#compressed-aggregate-trades-list">
     *     https://binance-docs.github.io/apidocs/delivery/en/#compressed-aggregate-trades-list</a>
     */
    public Mono<String> aggTrades(LinkedHashMap<String, Object> parameters) {
        ParameterChecker.checkParameter(parameters, "symbol", String.class);
        return asyncRequestHandler.sendPublicRequest(productUrl, AGG_TRADES, parameters, HttpMethod.GET, showLimitUsage);
    }

    private final String KLINES = "/v1/klines";
    /**
     * Kline/candlestick bars for a symbol.
     * Klines are uniquely identified by their open time.
     * <br><br>
     * GET /v1/klines
     * <br>
     * @param
     * parameters LinkedHashedMap of String,Object pair
     *            where String is the name of the parameter and Object is the value of the parameter
     * <br><br>
     * symbol -- mandatory/string <br>
     * interval -- mandatory/string <br>
     * startTime -- optional/long <br>
     * endTime -- optional/long <br>
     * limit -- optional/integer -- limit the results Default 500; max 1000 <br>
     * @return String
     * @see <a href="https://binance-docs.github.io/apidocs/futures/en/#kline-candlestick-data">
     *     https://binance-docs.github.io/apidocs/futures/en/#kline-candlestick-data</a>
     * @see <a href="https://binance-docs.github.io/apidocs/delivery/en/#kline-candlestick-data">
     *     https://binance-docs.github.io/apidocs/delivery/en/#kline-candlestick-data</a>
     */
    public Mono<String> klines(LinkedHashMap<String, Object> parameters) {
        ParameterChecker.checkParameter(parameters, "symbol", String.class);
        ParameterChecker.checkParameter(parameters, "interval", String.class);
        return asyncRequestHandler.sendPublicRequest(productUrl, KLINES, parameters, HttpMethod.GET, showLimitUsage);
    }

    private final String CONTINUOUSKLINES = "/v1/continuousKlines";
    /**
     * Kline/candlestick bars for a specific contract type.
     * Klines are uniquely identified by their open time.
     * <br><br>
     * GET /v1/continuousKlines
     * <br>
     * @param
     * parameters LinkedHashedMap of String,Object pair
     *            where String is the name of the parameter and Object is the value of the parameter
     * <br><br>
     * pair -- mandatory/string <br>
     * contractType -- mandatory/enum <br>
     * interval -- mandatory/enum <br>
     * startTime -- optional/long <br>
     * endTime -- optional/long <br>
     * limit -- optional/integer -- limit the results Default 500; max 1000 <br>
     * @return String
     * @see <a href="https://binance-docs.github.io/apidocs/futures/en/#continuous-contract-kline-candlestick-data">
     *     https://binance-docs.github.io/apidocs/futures/en/#continuous-contract-kline-candlestick-data</a>
     * @see <a href="https://binance-docs.github.io/apidocs/delivery/en/#continuous-contract-kline-candlestick-data">
     *     https://binance-docs.github.io/apidocs/delivery/en/#continuous-contract-kline-candlestick-data</a>
     */
    public Mono<String> continuousKlines(LinkedHashMap<String, Object> parameters) {
        ParameterChecker.checkParameter(parameters, "pair", String.class);
        ParameterChecker.checkParameter(parameters, "contractType", String.class);
        ParameterChecker.checkParameter(parameters, "interval", String.class);
        return asyncRequestHandler.sendPublicRequest(productUrl, CONTINUOUSKLINES, parameters, HttpMethod.GET, showLimitUsage);
    }

    private final String INDEXPRICEKLINES = "/v1/indexPriceKlines";
    /**
     * Kline/candlestick bars for the index price of a pair.
     * Klines are uniquely identified by their open time.
     * <br><br>
     * GET /v1/indexPriceKlines
     * <br>
     * @param
     * parameters LinkedHashedMap of String,Object pair
     *            where String is the name of the parameter and Object is the value of the parameter
     * <br><br>
     * pair -- mandatory/string <br>
     * interval -- mandatory/enum <br>
     * startTime -- optional/long <br>
     * endTime -- optional/long <br>
     * limit -- optional/integer -- limit the results Default 500; max 1000 <br>
     * @return String
     * @see <a href="https://binance-docs.github.io/apidocs/futures/en/#index-price-kline-candlestick-data">
     *     https://binance-docs.github.io/apidocs/futures/en/#index-price-kline-candlestick-data</a>
     * @see <a href="https://binance-docs.github.io/apidocs/delivery/en/#index-price-kline-candlestick-data">
     *     https://binance-docs.github.io/apidocs/delivery/en/#index-price-kline-candlestick-data</a>
     */
    public Mono<String> indexPriceKlines(LinkedHashMap<String, Object> parameters) {
        ParameterChecker.checkParameter(parameters, "pair", String.class);
        ParameterChecker.checkParameter(parameters, "interval", String.class);
        return asyncRequestHandler.sendPublicRequest(productUrl, INDEXPRICEKLINES, parameters, HttpMethod.GET, showLimitUsage);
    }

    private final String MARKPRICEKLINES = "/v1/markPriceKlines";
    /**
     * Kline/candlestick bars for the mark price of a symbol.
     * Klines are uniquely identified by their open time.
     * <br><br>
     * GET /v1/markPriceKlines
     * <br>
     * @param
     * parameters LinkedHashedMap of String,Object pair
     *            where String is the name of the parameter and Object is the value of the parameter
     * <br><br>
     * symbol -- mandatory/string <br>
     * interval -- mandatory/enum <br>
     * startTime -- optional/long <br>
     * endTime -- optional/long <br>
     * limit -- optional/integer -- limit the results Default 500; max 1000 <br>
     * @return String
     * @see <a href="https://binance-docs.github.io/apidocs/futures/en/#mark-price-kline-candlestick-data">
     *     https://binance-docs.github.io/apidocs/futures/en/#mark-price-kline-candlestick-data</a>
     * @see <a href="https://binance-docs.github.io/apidocs/delivery/en/#mark-price-kline-candlestick-data">
     *     https://binance-docs.github.io/apidocs/delivery/en/#mark-price-kline-candlestick-data</a>
     */
    public Mono<String> markPriceKlines(LinkedHashMap<String, Object> parameters) {
        ParameterChecker.checkParameter(parameters, "symbol", String.class);
        ParameterChecker.checkParameter(parameters, "interval", String.class);
        return asyncRequestHandler.sendPublicRequest(productUrl, MARKPRICEKLINES, parameters, HttpMethod.GET, showLimitUsage);
    }

    private final String FUNDING_RATE = "/v1/fundingRate";
    /**
     * Get funding rate history
     * <br><br>
     * GET /v1/fundingRate
     * <br>
     * @param
     * parameters LinkedHashedMap of String,Object pair
     *            where String is the name of the parameter and Object is the value of the parameter
     * <br><br>
     * symbol -- optional/string -- the trading pair <br>
     * startTime -- optional/long -- Timestamp in ms to get funding rate from INCLUSIVE. <br>
     * endTime -- optional/long -- Timestamp in ms to get funding rate until INCLUSIVE. <br>
     * limit -- optional/int -- Default 100; max 1000 <br>
     * @return String
     * @see <a href="https://binance-docs.github.io/apidocs/futures/en/#get-funding-rate-history">
     *     https://binance-docs.github.io/apidocs/futures/en/#get-funding-rate-history</a>
     * @see <a href="https://binance-docs.github.io/apidocs/delivery/en/#get-funding-rate-history-of-perpetual-futures">
     *     https://binance-docs.github.io/apidocs/delivery/en/#get-funding-rate-history-of-perpetual-futures</a>
     */
    public Mono<String> fundingRate(LinkedHashMap<String, Object> parameters) {
        return asyncRequestHandler.sendPublicRequest(productUrl, FUNDING_RATE, parameters, HttpMethod.GET, showLimitUsage);
    }

    private final String OPEN_INTEREST = "/v1/openInterest";
    /**
     * Get present open interest of a specific symbol.
     * <br><br>
     * GET /v1/openInterest
     * <br>
     * https://binance-docs.github.io/apidocs/futures/en/#open-interest
     * @param
     * parameters LinkedHashedMap of String,Object pair
     *            where String is the name of the parameter and Object is the value of the parameter
     * <br><br>
     * symbol -- mandatory/string -- the trading pair <br>
     * @return String
     * @see <a href="https://binance-docs.github.io/apidocs/futures/en/#open-interest">
     *     https://binance-docs.github.io/apidocs/futures/en/#open-interest</a>
     * @see <a href="https://binance-docs.github.io/apidocs/delivery/en/#open-interest">
     *     https://binance-docs.github.io/apidocs/delivery/en/#open-interest</a>
     */
    public Mono<String> openInterest(LinkedHashMap<String, Object> parameters) {
        ParameterChecker.checkParameter(parameters, "symbol", String.class);
        return asyncRequestHandler.sendPublicRequest(productUrl, OPEN_INTEREST, parameters, HttpMethod.GET, showLimitUsage);
    }
}
