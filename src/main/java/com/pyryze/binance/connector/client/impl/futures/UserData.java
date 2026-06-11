package com.pyryze.binance.connector.client.impl.futures;

import com.pyryze.binance.connector.client.enums.HttpMethod;
import com.pyryze.binance.connector.client.utils.ProxyAuth;
import com.pyryze.binance.connector.client.utils.AsyncRequestHandler;

import reactor.core.publisher.Mono;

/**
 * <h2>User Data Streams Endpoints</h2>
 * Response will be returned in <i>String format</i>.
 */
public abstract class UserData {
    private String productUrl;
    private AsyncRequestHandler asyncAsyncRequestHandler;
    private boolean showLimitUsage;

    public UserData(String productUrl, String apiKey, boolean showLimitUsage, ProxyAuth proxy) {
        this.productUrl = productUrl;
        this.asyncAsyncRequestHandler = new AsyncRequestHandler(apiKey, proxy);
        this.showLimitUsage = showLimitUsage;
    }

    public String getProductUrl() {
        return this.productUrl;
    }

    public AsyncRequestHandler getAsyncRequestHandler() {
        return this.asyncAsyncRequestHandler;
    }

    public boolean getShowLimitUsage() {
        return this.showLimitUsage;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public void setAsyncRequestHandler(String apiKey, ProxyAuth proxy) {
        this.asyncAsyncRequestHandler = new AsyncRequestHandler(apiKey, proxy);
    }

    public void setShowLimitUsage(boolean showLimitUsage) {
        this.showLimitUsage = showLimitUsage;
    }

    private final String LISTEN_KEY = "/v1/listenKey";
    /**
     * Start a new user data stream. The stream will close after 60 minutes unless a keepalive is sent.
     * If the account has an active listenKey, that listenKey will be returned and its validity will be extended for 60 minutes.
     * <br><br>
     * POST /v1/listenKey
     * <br>
     * @return String
     * @see <a href="https://binance-docs.github.io/apidocs/futures/en/#start-user-data-stream-user_stream">
     *     https://binance-docs.github.io/apidocs/futures/en/#start-user-data-stream-user_stream</a>
     */
    public Mono<String> createListenKey() {
        return asyncAsyncRequestHandler.sendWithApiKeyRequest(productUrl, LISTEN_KEY, null, HttpMethod.POST, showLimitUsage);
    }

    /**
     * Keepalive a user data stream to prevent a time out. User data streams will close after 60 minutes.
     * It's recommended to send a ping about every 60 minutes.
     * <br><br>
     * PUT /v1/listenKey
     * <br>
     * @return String
     * @see <a href="https://binance-docs.github.io/apidocs/futures/en/#keepalive-user-data-stream-user_stream">
     *     https://binance-docs.github.io/apidocs/futures/en/#keepalive-user-data-stream-user_stream</a>
     */
    public Mono<String> extendListenKey() {
        return asyncAsyncRequestHandler.sendWithApiKeyRequest(productUrl, LISTEN_KEY, null, HttpMethod.PUT, showLimitUsage);
    }

    /**
     * Close out a user data stream.
     * <br><br>
     * DELETE /v1/listenKey
     * <br>
     * @return String
     * @see <a href="https://binance-docs.github.io/apidocs/futures/en/#close-user-data-stream-user_stream">
     *     https://binance-docs.github.io/apidocs/futures/en/#close-user-data-stream-user_stream</a>
     */
    public Mono<String> closeListenKey() {
        return asyncAsyncRequestHandler.sendWithApiKeyRequest(productUrl, LISTEN_KEY, null, HttpMethod.DELETE, showLimitUsage);
    }
}