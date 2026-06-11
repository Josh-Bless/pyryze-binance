package com.pyryze.binance.connector.client.utils;

import com.pyryze.binance.connector.client.exceptions.BinanceClientException;
import com.pyryze.binance.connector.client.exceptions.BinanceConnectorException;
import com.pyryze.binance.connector.client.exceptions.BinanceServerException;
import com.pyryze.binance.connector.client.utils.httpclient.HttpClientSingleton;
import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.Map;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.*;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

import java.util.function.Consumer;

public final class ResponseHandler {
    private static OkHttpClient client;
    private static final int HTTP_STATUS_CODE_400 = 400;
    private static final int HTTP_STATUS_CODE_499 = 499;
    private static final int HTTP_STATUS_CODE_500 = 500;

    private ResponseHandler() {
    }
    
    public static String handleResponse(Request request, boolean showLimitUsage, ProxyAuth proxy, Map<String,Object>properties) {
        client = HttpClientSingleton.getHttpClient(proxy);
        try (Response response = client.newCall(request).execute()) {
            if (null == response) {
                throw new BinanceServerException("[ResponseHandler] No response from server");
            }

            String responseAsString = getResponseBodyAsString(response.body());

            if (response.code() >= HTTP_STATUS_CODE_400 && response.code() <= HTTP_STATUS_CODE_499) {
                throw handleErrorResponse(responseAsString, response.code(), response.header("Retry-After"));
            } else if (response.code() >= HTTP_STATUS_CODE_500) {
                throw new BinanceServerException(responseAsString, response.code());
            }

            if (showLimitUsage) {
                setlimitUsage(properties,response);
            } 
            return responseAsString;
        } catch (IOException | IllegalStateException e) {
            String exceptionMsg = "OKHTTP Error: ";
            if (proxy != null) {
                if ((e.getClass().equals(ConnectException.class))) {
                    exceptionMsg = "Proxy Connection Error: ";
                } else if ((e.getClass().equals(UnknownHostException.class))) {
                    exceptionMsg = "Proxy Unknown Host Error: ";
                }
            }
            throw new BinanceConnectorException("[ResponseHandler] " + exceptionMsg + e.getMessage());
        }
    }
    
    public static Mono<String> handleAsyncResponse(Request request, boolean showLimitUsage, ProxyAuth proxy, Map<String,Object>properties) {
        
        client = HttpClientSingleton.getHttpClient(proxy);
        return Mono.create(sink -> {
            client.newCall(request).enqueue(getCallback(sink,showLimitUsage,properties,proxy));
        });
    }
    
    private static Callback getCallback(MonoSink<String> sink, boolean showLimitUsage, Map<String,Object>properties, ProxyAuth proxy){
        
        Consumer<IOException>onError = e -> {
            
            String exceptionMsg = "OKHTTP Error: ";
                    
            if (proxy != null) {
                        
                if ((e.getClass().equals(ConnectException.class))) {
                    exceptionMsg = "Proxy Connection Error: ";
                } 
                    
                else if ((e.getClass().equals(UnknownHostException.class))) {
                    exceptionMsg = "Proxy Unknown Host Error: ";
                }
            }
            sink.error(new BinanceConnectorException("[ResponseHandler] " + exceptionMsg + e.getMessage()));// Signal the error to the Mono sink
        };
        
        return new Callback(){
            
            @Override
            public void onFailure(Call call, IOException e) {
                onError.accept(e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                
                try {
                    
                    if (null == response) {
                        
                        sink.error(new BinanceServerException("[ResponseHandler] No response from server"));
                    }

                    String responseAsString = getResponseBodyAsString(response.body());

                    if (response.code() >= HTTP_STATUS_CODE_400 && response.code() <= HTTP_STATUS_CODE_499) {
                        
                        sink.error(handleErrorResponse(responseAsString, response.code(), response.header("Retry-After")));
                    } else if (response.code() >= HTTP_STATUS_CODE_500) {
                        
                        sink.error(new BinanceServerException(responseAsString, response.code()));
                    }

                    if (showLimitUsage) {
                        setlimitUsage(properties,response);
                    } 
                    sink.success(responseAsString);
                }
                catch(IOException e){
                    onError.accept(e);
                }
                finally {
                    try{
                        response.close(); // Ensure the response body is closed
                    }
                    catch(Exception _e){}
                }
            }
        };
    }
    
    private static void setlimitUsage(Map<String,Object>properties, Response response) {
        
        if(properties != null){
            properties.put("X-SAPI-USED-IP-WEIGHT-1M", response.header("X-SAPI-USED-IP-WEIGHT-1M"));
            properties.put("X-SAPI-USED-UID-WEIGHT-1S", response.header("X-SAPI-USED-UID-WEIGHT-1S"));
            properties.put("x-mbx-used-weight", response.header("x-mbx-used-weight"));
            properties.put("x-mbx-used-weight-1m", response.header("x-mbx-used-weight-1m"));
        }
    }

    private static BinanceClientException handleErrorResponse(String responseBody, int responseCode, String retryAfter) {
        try {
            String errorMsg = JSONParser.getJSONStringValue(responseBody, "msg");
            int errorCode = JSONParser.getJSONIntValue(responseBody, "code");
            return new BinanceClientException(responseBody, errorMsg, responseCode, errorCode, retryAfter);
        } catch (JSONException e) {
            return new BinanceClientException(responseBody, responseCode, retryAfter);
        }
    }

    private static String getResponseBodyAsString(ResponseBody body) throws IOException {
        if (null != body) {
            return body.string();
        } else {
            return "";
        }
    }
}
