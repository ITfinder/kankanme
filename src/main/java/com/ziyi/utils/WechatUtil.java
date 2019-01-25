package com.ziyi.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ziyi.common.ItpkConstant;
import com.ziyi.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author ziyi
 * @create 2019/1/21
 */
public class WechatUtil {

    private static final Logger logger = LoggerFactory.getLogger(WechatUtil.class);

    /**
     * 微信配置token时填写的内容
     */
    public static final String TOKEN = "kankanme_token";

    /**
     * 微信api调用地址
     */
    public static final String SERVER_URL = "https://api.weixin.qq.com/";

    public static final String GET_ACCESSTOKEN_METHOD = "cgi-bin/token";
    public static final String GET_SERVERIP_METHOD = "cgi-bin/getcallbackip";


    public static  final String APP_ID = "wxa2bea90d1f073f3a";

    public static final String APP_SECRET = "a81a23b353e9342e64b64778a1df40a5";




    public static boolean checkWechatToken(String signature,String timestamp,String nonce,String echostr)throws NoSuchAlgorithmException{
        System.out.println("signature = [" + signature + "], timestamp = [" + timestamp + "], nonce = [" + nonce + "], echostr = [" + echostr + "]");
        String[] params = new String[] { TOKEN, timestamp, nonce };
        Arrays.sort(params);
        // 将三个参数字符串拼接成一个字符串进行sha1加密
        String clearText = params[0] + params[1] + params[2];
        String algorithm = "SHA-1";
        String sign = new String(
                org.apache.commons.codec.binary.Hex.encodeHex(MessageDigest.getInstance(algorithm).digest((clearText).getBytes()), true));

        return sign.equals(signature);
    }


    /**
     * 微信平台开发者 获取access_token
     * @return
     */
    public WxAccessToken getAccessToken(){

        //判断redis中是否存在，存在则返回，否则访问微信获取
        //TO_DO

        String httpStr = WechatUtil.SERVER_URL + WechatUtil.GET_ACCESSTOKEN_METHOD +
                "?access_token=client_credential&appid="+ WechatUtil.APP_ID + "&secret=" + WechatUtil.APP_SECRET;
        String result = HttpUtil.getHttp(httpStr);

        JSONObject json = JSON.parseObject(result);

        if(json == null){
            return null;
        }

        WxAccessToken wxAccessToken = new WxAccessToken();
        wxAccessToken.setAccess_token(json.getString("access_token"));
        wxAccessToken.setExpires_in(json.getLong("expires_in"));

        //设置到redis
        //TO_DO

        return wxAccessToken;
    }


    /**
     * 微信公众平台 获取微信服务器IP地址
     * @param accessToken
     * @return
     */
    public ServerIp getServerIp(String accessToken){
        String httpStr = WechatUtil.SERVER_URL + WechatUtil.GET_SERVERIP_METHOD + "?access_token=" + accessToken;

        String result = HttpUtil.getHttp(httpStr);

        JSONObject json = JSON.parseObject(result);

        if(json == null){
            return null;
        }

        ServerIp serverIp = new ServerIp();
        serverIp.setIpList((List<String>) json.get("ip_list"));

        return serverIp;

    }


    public static String weixinPost(HttpServletRequest request) {
        String respMessage = null;
        try {

            // xml请求解析
            Map<String, String> requestMap = MessageUtil.xmlToMap(request);

            // 发送方帐号（open_id）
            final String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            final String toUserName = requestMap.get("ToUserName");
            // 消息类型
            final String msgType = requestMap.get("MsgType");
            // 消息内容
            final String content = requestMap.get("Content");

            final String msgId = requestMap.get("MsgId");

            // 文本消息
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {

                //开启线程
                new Thread(new Runnable() {
                    public void run() {
                        //这里根据关键字执行相应的逻辑，只有你想不到的，没有做不到的
                        String contentResult = null;

                        if(content.equals(ItpkConstant.XIAOHUA) || content.equals(ItpkConstant.JOKE)){//笑话
                            contentResult = AutoReplyUtil.getResult(new JokeMsgResult(),ItpkConstant.XIAOHUA);
                        }else if(content.equals(ItpkConstant.GUANYINLINQIAN)){//观音灵签
                            contentResult = AutoReplyUtil.getResult(new GuanYinMsgResult(),ItpkConstant.GUANYINLINQIAN);
                        }else if(content.equals(ItpkConstant.YUELAOLINGQIAN)){//月老灵签
                            contentResult = AutoReplyUtil.getResult(new YueLaoMsgResult(),ItpkConstant.YUELAOLINGQIAN);
                        }else if(content.equals(ItpkConstant.CAISHENYELINGQIAN)){//财神爷灵签
                            contentResult = AutoReplyUtil.getResult(new CaiShenYeMsgResult(),ItpkConstant.CAISHENYELINGQIAN);
                        }else{//普通的
                            contentResult = AutoReplyUtil.getResult(new SingleMsgResult(),content);
                        }

                        logger.info("机器人查询内容：【{}】,结果：【{}】",content,contentResult);

                        //给用户发送消息结果


//                        //自动回复
//                        TextMessage text = new TextMessage();
//                        text.setContent(contentResult);
//                        text.setToUserName(fromUserName);
//                        text.setFromUserName(toUserName);
//                        text.setCreateTime(new Date().getTime() + "");
//                        text.setMsgType(msgType);
//                        text.setMsgId(msgId);
//
//                        String xmlRespMessage = MessageUtil.textMessageToXml(text);//最终要返回的数据（xml）

                    }
                }).start();

                logger.info("********************直接回复success.....*****************");
                return "success";

            } /*else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {// 事件推送
                String eventType = requestMap.get("Event");// 事件类型

                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {// 订阅
                    respContent = "欢迎关注xxx公众号！";
                    return MessageResponse.getTextMessage(fromUserName , toUserName , respContent);
                } else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {// 自定义菜单点击事件
                    String eventKey = requestMap.get("EventKey");// 事件KEY值，与创建自定义菜单时指定的KEY值对应
                    logger.info("eventKey is:" +eventKey);
                    return xxx;
                }
            }
            //开启微信声音识别测试 2015-3-30
            else if(msgType.equals("voice"))
            {
                String recvMessage = requestMap.get("Recognition");
                //respContent = "收到的语音解析结果："+recvMessage;
                if(recvMessage!=null){
                    respContent = TulingApiProcess.getTulingResult(recvMessage);
                }else{
                    respContent = "您说的太模糊了，能不能重新说下呢？";
                }
                return MessageResponse.getTextMessage(fromUserName , toUserName , respContent);
            }
            //拍照功能
            else if(msgType.equals("pic_sysphoto"))
            {

            }
            else
            {
                return MessageResponse.getTextMessage(fromUserName , toUserName , "返回为空");
            }*/
            // 事件推送
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                String eventType = requestMap.get("Event");// 事件类型
                // 订阅
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {


                    StringBuffer sb = new StringBuffer();
                    sb.append("欢迎关注侃侃么~我可以跟你唠家常哦，您还回复以下内容\n");
                    sb.append("笑话\n");
                    sb.append("观音灵签\n");
                    sb.append("月老灵签\n");
                    sb.append("财神爷灵签");
                    TextMessage text = new TextMessage();
                    text.setContent(sb.toString());
                    text.setToUserName(fromUserName);
                    text.setFromUserName(toUserName);
                    text.setCreateTime(new Date().getTime() + "");
                    text.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

                    respMessage = MessageUtil.textMessageToXml(text);
                }
                // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {// 取消订阅
                }
                // 自定义菜单点击事件
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    String eventKey = requestMap.get("EventKey");// 事件KEY值，与创建自定义菜单时指定的KEY值对应
                    if (eventKey.equals("customer_telephone")) {
                        TextMessage text = new TextMessage();
                        text.setContent("0755-86671980");
                        text.setToUserName(fromUserName);
                        text.setFromUserName(toUserName);
                        text.setCreateTime(new Date().getTime() + "");
                        text.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

                        respMessage = MessageUtil.textMessageToXml(text);
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error("error......");
        }
        return respMessage;
    }





}
