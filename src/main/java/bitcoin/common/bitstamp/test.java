package bitcoin.common.bitstamp;

public class test {
    public static void main(String [] args) throws Exception {
        BitstampMarket bitstampMarket = new BitstampMarket();
        bitstampMarket.subscribeDepth("btcusd",5);
    }
}
