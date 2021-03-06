package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class Huobi extends Market {

	private final static String NAME = "Huobi";
	private final static String TTS_NAME = NAME;
	private final static String URL_BTC = "http://market.huobi.com/staticmarket/ticker_btc_json.js";
	private final static String URL_LTC = "http://market.huobi.com/staticmarket/ticker_ltc_json.js";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.CNY
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				Currency.CNY
			});
	}
	
	public Huobi() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		if(VirtualCurrency.LTC.equals(checkerInfo.getCurrencyBase()))
			return URL_LTC;
		else
			return URL_BTC;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject tickerJsonObject = jsonObject.getJSONObject("ticker");
		
		ticker.bid = getDoubleFromString(tickerJsonObject, "buy");
		ticker.ask = getDoubleFromString(tickerJsonObject, "sell");
		ticker.vol = getDoubleFromString(tickerJsonObject, "vol");
		ticker.high = getDoubleFromString(tickerJsonObject, "high");
		ticker.low = getDoubleFromString(tickerJsonObject, "low");
		ticker.last = getDoubleFromString(tickerJsonObject, "last");
	}
	
	private double getDoubleFromString(JSONObject jsonObject, String name) throws NumberFormatException, JSONException {
		return Double.parseDouble(jsonObject.getString(name));
	}
}
