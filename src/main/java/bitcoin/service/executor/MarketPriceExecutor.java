package bitcoin.service.executor;

import bitcoin.common.Market;
import bitcoin.utils.DataCenter;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MarketPriceExecutor implements Runnable {

    public List<Market> marketList;

    public MarketPriceExecutor(List<Market> marketList) {
        this.marketList = marketList;
    }

    @Override
    public void run() {
        for(;;){
            for(Market market: marketList){
                try {
                    Map<String, Map> marketPrice = market.subscribeDepth("btcusd",5);
                    for(String marketName: marketPrice.keySet()){
                        DataCenter.setMarketData(marketName, marketPrice.get(marketName));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(2000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
