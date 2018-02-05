package bitcoin.service.impl;

import bitcoin.common.Market;
import bitcoin.common.bitstamp.BitstampMarket;
import bitcoin.service.ServiceCenter;
import bitcoin.service.executor.BitstampBchBtcPriceExecutor;
import bitcoin.service.executor.MarketPriceExecutor;
import bitcoin.utils.DataCenter;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServiceCenterImpl implements ServiceCenter {


    //这里要启线程去轮询各个交易所的行情数据
    @Override
    public void calculateMarketPrice() {
        List<Market> marketList = new ArrayList<>();
        Market bitstampMarket = new BitstampMarket();
        marketList.add(bitstampMarket);

        Thread thread = new Thread(new MarketPriceExecutor(marketList));
        thread.run();
    }

    @Override
    public Map windowsExist() {
        return null;
    }

    @Override
    public List getMarketPrice() {
        List<Map> dataList = new ArrayList<>();

        Map<String, Map> map = DataCenter.getMarketData();
        for(String key: map.keySet()){
            Map marketMap = new HashMap<>();
            marketMap.put("marketname",key);
            marketMap.put("bid",map.get(key).get("bids"));
            marketMap.put("ask",map.get(key).get("asks"));
            marketMap.put("windows",map.get(key).get("windows"));

            dataList.add(marketMap);
        }
        return dataList;
    }

    @Override
    public void calculateBchBtcWindow() {
        Market bitstampMarket = new BitstampMarket();
        Thread thread = new Thread(new BitstampBchBtcPriceExecutor(bitstampMarket));
        thread.run();
    }
}
