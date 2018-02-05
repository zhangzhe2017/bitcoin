package bitcoin.common;

import bitcoin.service.DataRelay;
import java.util.Map;

public interface Market {
    public Map subscribeDepth(String currency, int depth) throws Exception;

    public void subscribeKline(String currency) throws Exception;
}
