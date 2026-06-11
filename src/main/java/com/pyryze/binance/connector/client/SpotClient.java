package com.pyryze.binance.connector.client;

import com.pyryze.binance.connector.client.SpotClient;
import com.pyryze.binance.connector.client.enums.DefaultUrls;
import com.pyryze.binance.connector.client.impl.spot.Futures;
import com.pyryze.binance.connector.client.impl.spot.Margin;
import com.pyryze.binance.connector.client.impl.spot.PortfolioMargin;
import com.pyryze.binance.connector.client.impl.spot.SubAccount;
import com.pyryze.binance.connector.client.impl.spot.Trade;
import com.pyryze.binance.connector.client.impl.spot.UserData;
import com.pyryze.binance.connector.client.impl.spot.Wallet;


public interface SpotClient {
    Futures createFutures();
    Margin createMargin();
    PortfolioMargin createPortfolioMargin();
    SubAccount createSubAccount();
    Trade createTrade();
    UserData createUserData();
    Wallet createWallet();
}
