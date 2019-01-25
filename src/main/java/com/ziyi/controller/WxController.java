package com.ziyi.controller;

import com.ziyi.utils.WechatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * @author ziyi
 * @create 2019/1/21
 */
@Controller
@RequestMapping("wx")
public class WxController {

    private static final Logger logger = LoggerFactory.getLogger(WxController.class);
    /**
     * 1.微信Token验证   GET方法
     * 1)将token、timestamp、nonce三个参数进行字典序排序
     * 2）将三个参数字符串拼接成一个字符串进行sha1加密
     * 3）开发者获得加密后的字符串可与signature对比
     *
     * 2.接收微信公众号转发过来的消息  POST方法
     *
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    @RequestMapping(value = "checkToken",method = {RequestMethod.GET, RequestMethod.POST})
    public void checkTokenOrGetWxPost(HttpServletRequest request, HttpServletResponse response)throws NoSuchAlgorithmException,IOException {
        boolean isGet = request.getMethod().toLowerCase().equals("get");

        if (isGet) {
            String signature = request.getParameter("signature");/// 微信加密签名
            String timestamp = request.getParameter("timestamp");/// 时间戳
            String nonce = request.getParameter("nonce"); /// 随机数
            String echostr = request.getParameter("echostr"); // 随机字符串
            boolean result = WechatUtil.checkWechatToken(signature,timestamp,nonce,echostr);
            if (result) {
                response.getWriter().print(echostr);
            }
        }else{
            String respMessage = "异常消息！";

            try {
                respMessage = WechatUtil.weixinPost(request);
                response.getWriter().write(respMessage);
                logger.info("The request completed successfully");
            } catch (Exception e) {
                logger.error("Failed to convert the message from weixin!");
            }

        }


    }

}
