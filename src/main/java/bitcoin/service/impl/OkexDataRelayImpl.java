package bitcoin.service.impl;

import bitcoin.infrastructure.model.Kline;
import bitcoin.infrastructure.repository.KlineRepository;
import bitcoin.service.DataRelay;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;

public class OkexDataRelayImpl implements DataRelay {

    @Autowired
    public KlineRepository klineRepository;

    @Override
    public void transferData(String msg) {
        //根据不同的channel处理不同的类型
        if(msg.contains("depth")){
            //
        }
        if(msg.contains("kline")){
            //k线数据落库
            storeKlineData(msg);
        }
    }

    private void storeKlineData(String msg){
        JSONArray jsonArray = JSON.parseArray(msg);
        JSONObject jsonObject = (JSONObject) jsonArray.get(0);
        JSONArray dataArray = (JSONArray) jsonObject.get("data");

        Kline kline = new Kline();
        kline.setDatatime(new Date((long)dataArray.get(0)));
        kline.setGmtCreate(new Date());
        kline.setOpenPrice((double)dataArray.get(1));
        kline.setHighPrice((double)dataArray.get(2));
        kline.setLowPrice((double)dataArray.get(3));
        kline.setClosePrice((double)dataArray.get(4));
        kline.setVolume((double)dataArray.get(5));
        kline.setSource("okex");

        klineRepository.insertKline(kline);
    }
}
