package top.zsmile.tool.wechat.gzh.wxgzh.controller;

import com.air.basis.common.libs.http.OkHttpUtil;
import com.air.basis.common.utils.R;
import com.air.basis.modules.wechat.wxgzh.config.WechatConfig;
import com.air.basis.modules.wechat.wxgzh.utils.WechatHandleUtil;
import com.air.basis.modules.wechat.wxgzh.utils.WechatSignUtil;
import com.air.basis.modules.wechat.wxpay.utils.WxPayUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/gzh/auth")
public class AuthorTokenController {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @RequestMapping("/wx")
    public @ResponseBody
    String wx(@RequestParam Map<String, String> params, HttpServletRequest request) {
        try {
            /**
             * 接收微信服务器发送请求时传递过来的参数
             */
            String signature = params.get("signature");
            String timestamp = params.get("timestamp");
            String nonce = params.get("nonce");
            String echostr = params.get("echostr");

            boolean signState = WechatSignUtil.checkSign(timestamp, nonce, signature);

            if (signState) {
                logger.info("-----签名校验通过-----");
                StringBuilder notifyData = new StringBuilder(); // 支付结果通知的xml格式数据
                String inputLine;
                while ((inputLine = request.getReader().readLine()) != null) {
                    notifyData.append(inputLine);
                }
                request.getReader().close();

                if (!notifyData.toString().equals("")) {
                    Map<String, String> notifyMap = null;
                    if (WechatConfig.AESState) {
                        //用于解密对比的签名
                        String msgSignature = params.get("msg_signature");
                        String result2 = WechatConfig.pc.decryptMsg(msgSignature, timestamp, nonce, notifyData.toString());
                        System.out.println("解密后明文: " + result2);
                        //解密后转换成map
                        notifyMap = WxPayUtil.xmlToMap(result2);  // 转换成map
                    } else {
                        notifyMap = WxPayUtil.xmlToMap(notifyData.toString());  // 转换成map
                    }
                    String res = WechatHandleUtil.reqHandle(notifyMap, timestamp, nonce);
                    if (res != null) {
                        return res;
                    }
                }
                if (echostr != null) {
                    return echostr;
                } else {
                    return "success";
                }
            } else {
                logger.info("-----校验签名失败-----");
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    @RequestMapping("/accessToken")
    public R accessToken() {
        String accessToken = WechatSignUtil.getAccessToken();
        if (accessToken != null) {
            return R.ok();
        } else {
            return R.error();
        }

    }

    @RequestMapping("/jsapiTicket")
    public R jsapiTickets() {
        String accessToken = WechatSignUtil.getJsapiTicket();
        if (accessToken != null) {
            return R.ok();
        } else {
            return R.error();
        }

    }


    @RequestMapping("/menu")
    public R menu() {
        JSONObject menu = new JSONObject();
        JSONArray sub_button = new JSONArray();

        JSONArray buttonArr = new JSONArray();


        JSONObject device = new JSONObject();
        device.put("name", "设备管理");
        device.put("type", "view");
        device.put("url", "https://wxgzh.airworksoft.com/wxgzh/login/login.html");
        buttonArr.add(device);

//        JSONObject device1 = new JSONObject();
//        device1.put("name", "灭菌机");
//        device1.put("type", "view");
//        device1.put("url", "https://gzh.airworksoft.com/tmp/HiLinkJSAPIDemo.html");
//        buttonArr.add(device1);


        JSONObject sub_button1 = new JSONObject();
        sub_button1.put("name", "控制");
        sub_button1.put("sub_button", buttonArr);


        JSONObject button = new JSONObject();
        button.put("name", "WIFI设置");
        button.put("type", "view");
        button.put("url", "https://wxgzh.airworksoft.com/wifi/wifi.html");


        JSONArray buttonArr1 = new JSONArray();
        buttonArr1.add(sub_button1);
        buttonArr1.add(button);


        menu.put("button", buttonArr1);


        String res = OkHttpUtil.postJson("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + WechatConfig.ACCESS_TOKEN, menu.toJSONString());
        JSONObject resJson = JSON.parseObject(res);
        if (resJson.getIntValue("errcode") == 0) {
            return R.ok();
        } else {
            return R.error(resJson.getString("errmsg"));
        }
    }


    @RequestMapping("/jsticket")
    public R jsticket() {
        String nonceStr = "393538757";
        String timestamp = new Date().getTime() / 1000 + "";
        String url = "https://new.airworksoft.com/wifi/wifi.html";
        Map<String, String> map = new HashMap<String, String>();
        map.put("noncestr", nonceStr);
        map.put("timestamp", timestamp);
        map.put("url", url);
        map.put("jsapi_ticket", WechatConfig.JSAPI_TICKET);
        String signature = WechatSignUtil.ticketSign(map);
        map.put("signature", signature);
        return R.ok().put("config", map);
    }


}
