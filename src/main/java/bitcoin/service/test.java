package bitcoin.service;

import bitcoin.common.bitstamp.BitstampMarket;
import bitcoin.service.impl.ServiceCenterImpl;

public class test {
    public static void main(String [] args) throws Exception {
        ServiceCenter serviceCenter = new ServiceCenterImpl();
        serviceCenter.calculateMarketPrice();
    }
}
