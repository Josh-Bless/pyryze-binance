package com.pyryze.binance.connector.client;

import com.pyryze.binance.connector.client.impl.futures.Account;
import com.pyryze.binance.connector.client.impl.futures.UserData;
import com.pyryze.binance.connector.client.impl.futures.Market;
import com.pyryze.binance.connector.client.impl.futures.PortfolioMargin;

public interface FuturesClient {
    Market market();
    Account account();
    UserData userData();
    PortfolioMargin portfolioMargin();
}
