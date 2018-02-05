package bitcoin.service.executor;

import bitcoin.common.Market;
import bitcoin.utils.FileUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class BitstampBchBtcPriceExecutor implements Runnable {

    //当前交易点位
    private BigDecimal tradePositon = new BigDecimal(0);

    //窗口比例
    private BigDecimal tradeRatio = new BigDecimal("0.005");

    //bch币数
    private BigDecimal bchVolume = new BigDecimal(15);

    //btc币数
    private BigDecimal btcVolume = new BigDecimal(1);

    private int sellOper = 0 ;

    private int buyOper = 0 ;

    public Market market;

    public BitstampBchBtcPriceExecutor(Market market) {
        this.market = market;
    }

    @Override
    public void run() {
        for(;;){
            try {
                Map<String, Map> marketPrice = market.subscribeDepth("bchbtc",5);
                BigDecimal sellPrice = new BigDecimal((String)marketPrice.get("bitstamp").get("asks"));
                BigDecimal buyPrice = new BigDecimal((String)marketPrice.get("bitstamp").get("bids"));

                boolean flag = transfer(sellPrice,buyPrice);

                if(flag){
                    FileUtils.write2File("---------------","info");
                    FileUtils.write2File("当前交易价格： "+ tradePositon,"info");
                    FileUtils.write2File("当前交易价格： "+ tradePositon,"info");
                    FileUtils.write2File("bchVolum :" + bchVolume,"info");
                    FileUtils.write2File("btcVolume :" + btcVolume,"info");
                    FileUtils.write2File("sell operate times: " + sellOper,"info");
                    FileUtils.write2File("buy operate times: " + buyOper,"info");
                    FileUtils.write2File("---------------","info");
                }else{
                    FileUtils.write2File("---------------","info");
                    FileUtils.write2File("当前交易价格： "+ tradePositon,"info");
                    FileUtils.write2File("当前交易价格： "+ tradePositon,"info");
                    FileUtils.write2File("没有窗口期","info");
                    FileUtils.write2File("---------------","info");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(2000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //处理买卖价格，判断是否交易
    public boolean transfer(BigDecimal sellPrice, BigDecimal buyPrice){
        BigDecimal targetSellPrice = tradePositon.multiply(tradeRatio.add(BigDecimal.ONE));
        BigDecimal targetBuyPrice = tradePositon.multiply(BigDecimal.ONE.subtract(tradeRatio));
        FileUtils.write2File("窗口区间： "+ targetBuyPrice+ " " + targetSellPrice,"info");
        if(buyPrice.compareTo(targetSellPrice) >= 0){
            //每次交易1/5仓位
            //买入价格大于窗口价，则进行卖出操作，更新tradePosition以及仓位
            tradePositon = buyPrice;
            bchVolume = bchVolume.multiply(new BigDecimal("0.9")).subtract(bchVolume.multiply(new BigDecimal("0.1")).multiply(new BigDecimal("0.002")) );
            btcVolume = btcVolume.add(bchVolume.multiply(new BigDecimal("0.1")).multiply(buyPrice));
            sellOper ++;
            return true;
        }

        if(sellPrice.compareTo(targetBuyPrice) <= 0){
            tradePositon = sellPrice;
            bchVolume = bchVolume.multiply(new BigDecimal("1.1")).subtract(bchVolume.multiply(new BigDecimal("0.1")).multiply(new BigDecimal("0.002")));
            btcVolume = btcVolume.subtract(bchVolume.multiply(new BigDecimal("0.1")).multiply(sellPrice));
            buyOper ++;
            return true;
        }

        return false;
    }
}
