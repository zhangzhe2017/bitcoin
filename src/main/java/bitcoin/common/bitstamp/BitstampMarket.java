package bitcoin.common.bitstamp;

import bitcoin.common.Market;
import bitcoin.common.utils.HttpClientUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BitstampMarket implements Market {

    //获取对应深度数据
    @Override
    public Map subscribeDepth(String currency, int depth) throws Exception {
        String urlParam = "https://www.bitstamp.net/api/v2/order_book/".concat(currency);
        String response = HttpClientUtils.sendGet(urlParam,null,"utf-8");

        //解析response
        JSONObject jsonObject = JSON.parseObject(response);
        JSONArray bids = jsonObject.getJSONArray("bids");
        JSONArray asks = jsonObject.getJSONArray("asks");

        List bidsDepth = bids.subList(0,depth);
        JSONArray bidsDepthArray = (JSONArray)bidsDepth.get(1);
        String bidPirce = (String)bidsDepthArray.get(0);
        List asksDepth = asks.subList(0,depth);
        JSONArray asksDepthArray = (JSONArray)asksDepth.get(1);
        String askPrice = (String)asksDepthArray.get(0);
        Map depthMap = new HashMap();
        depthMap.put("bids",bidPirce);
        depthMap.put("asks",askPrice);

        Map marketPriceData = new HashMap();
        marketPriceData.put("bitstamp", depthMap);
        return marketPriceData;
    }

    @Override
    public void subscribeKline(String currency) throws Exception {

    }
}
