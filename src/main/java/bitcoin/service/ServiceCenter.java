package bitcoin.service;

import java.util.List;
import java.util.Map;

public interface ServiceCenter {

    public void calculateMarketPrice();

    public Map windowsExist();

    public List getMarketPrice();

    public void calculateBchBtcWindow();
}
