package bitcoin.common.huobi;

import bitcoin.common.Market;

import java.util.HashMap;
import java.util.Map;

public class HuobiMarket implements Market {

    private Map<String, String> currencyMap = new HashMap<String, String>(){{
        this.put("btcusd","btcusdt");
    }};


    @Override
    public Map subscribeDepth(String currency, int depth) throws Exception {

        return null;
    }

    @Override
    public void subscribeKline(String currency) throws Exception {

    }


}
