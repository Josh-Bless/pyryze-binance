package com.pyryze.binance.connector.client.utils;

import com.pyryze.binance.connector.client.enums.HttpMethod;
import com.pyryze.binance.connector.client.enums.RequestType;
import com.pyryze.binance.connector.client.exceptions.BinanceConnectorException;
import java.util.LinkedHashMap;
import java.util.Map;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RequestHandler {
    private final String apiKey;
    private final SignatureGenerator signatureGenerator;
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final ProxyAuth proxy;

    public RequestHandler(String apiKey, ProxyAuth proxy) {
        this(apiKey, null, proxy);
    }

    public RequestHandler(String apiKey, SignatureGenerator signatureGenerator, ProxyAuth proxy) {
        this.apiKey = apiKey;
        this.signatureGenerator = signatureGenerator;
        this.proxy = proxy;
    }

    /**
     * Build request based on request type and send the requests to server.
     * @param baseUrl
     * @param urlPath
     * @param signature
     * @param parameters
     * @param httpMethod
     * @param requestType
     * @return String - response from server
     */
    
    private String sendApiRequest(
        String baseUrl, 
        final String urlPath, 
        String signature, 
        LinkedHashMap<String, Object> parameters,
        HttpMethod httpMethod, 
        RequestType requestType, 
        boolean showLimitUsage
    ){
        
        Map<String,Object>properties = getProperties(parameters);
        if(properties!=null){
            properties.put("urlPath",urlPath);
            properties.put("baseUrl",baseUrl);
        }
        String fullUrl = UrlBuilder.buildFullUrl(baseUrl, urlPath, parameters, signature);
        logger.info("{} {}", httpMethod, fullUrl);
        Request request;
        switch (requestType) {
            case PUBLIC:
                request = RequestBuilder.buildPublicRequest(fullUrl, httpMethod);
                break;
            case WITH_API_KEY:
            case SIGNED:
                request = RequestBuilder.buildApiKeyRequest(fullUrl, httpMethod, apiKey);
                break;
            default:
                throw new BinanceConnectorException("[RequestHandler] Invalid request type: " + requestType);
        }
        return ResponseHandler.handleResponse(request, showLimitUsage, proxy, properties);
    }
    
    public String sendPublicRequest(
        String baseUrl, 
        String urlPath, 
        LinkedHashMap<String, Object> parameters,
        HttpMethod httpMethod, 
        boolean showLimitUsage
    ){
        return sendApiRequest(baseUrl, urlPath, null, parameters, httpMethod, RequestType.PUBLIC, showLimitUsage);
    }

    public String sendWithApiKeyRequest(
        String baseUrl, 
        String urlPath, 
        LinkedHashMap<String, Object> parameters,
        HttpMethod httpMethod, 
        boolean showLimitUsage
    ){
        if (null == apiKey || apiKey.isEmpty()) {
            throw new BinanceConnectorException("[RequestHandler] API key cannot be null or empty!");
        }
        return sendApiRequest(baseUrl, urlPath, null, parameters, httpMethod, RequestType.WITH_API_KEY, showLimitUsage);
    }

    public String sendSignedRequest(
        String baseUrl, 
        String urlPath, 
        LinkedHashMap<String, Object> parameters,
        HttpMethod httpMethod, 
        boolean showLimitUsage
    ){
                                        
        if(apiKey == null || apiKey.isEmpty()){
            throw new BinanceConnectorException("[RequestHandler] In SignedRequest, API key cannot be null or empty!");
        }
        if(parameters!=null){
            parameters.putIfAbsent("timestamp", UrlBuilder.buildTimestamp());
        }
        String queryString = UrlBuilder.joinQueryParameters(parameters);
        String signature = this.signatureGenerator.getSignature(queryString);
        return signature!=null ? sendApiRequest(baseUrl, urlPath, signature, parameters, httpMethod, RequestType.SIGNED, showLimitUsage) : null;
    }
    
    Map<String,Object>getProperties(LinkedHashMap<String, Object> parameters){
        if(parameters==null) return null;
        Map<String,Object>properties = (Map<String,Object>)parameters.get("properties");
        parameters.remove("properties");
        return properties;
    }
}
