package bitcoin.utils;

import java.util.HashMap;
import java.util.Map;

public class DataCenter {
    private static Map<String, Map> marketData = new HashMap<String, Map>();

    public static Map<String, Map> getMarketData() {
        return marketData;
    }

    public static void setMarketData(String marketName, Map market) {
        if(marketData.containsKey(marketName)){
            marketData.remove(marketName);
            marketData.put(marketName,market);
        }else{
            marketData.put(marketName,market);
        }

        for(String key: marketData.keySet()){
            Map map = marketData.get(key);
            System.out.println(key+" "+map.get("bids")+" "+map.get("asks"));
        }
    }

    public static Map getMarketDataByName(String marketName) {
        return marketData.get(marketName);
    }
}
