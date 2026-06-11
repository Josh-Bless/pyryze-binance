package com.pyryze.binance.connector.client.utils;

import reactor.core.publisher.Mono;

public interface SignatureGenerator{
    String getSignature(String payload);
    Mono<String> getSignatureAsynchronously(String payload);
}
