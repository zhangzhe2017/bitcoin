package bitcoin.web;

import bitcoin.service.ServiceCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by viczhang.zhangz on 2016/6/23.
 */
@Controller
public class TestController {


    @Autowired
    private ServiceCenter serviceCenter;

    @RequestMapping("/bchbtc")
    public String home() throws Exception {
        serviceCenter.calculateBchBtcWindow();
        return "list";
    }

    @RequestMapping("/market.html")
    public String detail() {
        return "market";
    }


    @RequestMapping("/getMarketPrice")
    @ResponseBody
    public List getMarketPrice(){
        return serviceCenter.getMarketPrice();
    }

}
