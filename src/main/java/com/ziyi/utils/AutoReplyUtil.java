package com.ziyi.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ziyi.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ziyi
 * @create 2019/1/21
 */
public class AutoReplyUtil<T> {

    private static Logger logger = LoggerFactory.getLogger(AutoReplyUtil.class);


    private static final String API = "http://i.itpk.cn/api.php?question=";
    private static final String LAST_STR = "&api_key=b4f7998e030543d95c8c0b8d80879af6&api_secret=ugjx84pxrjeh";

    public static String getUrl(String msg) {
        String httpStr = AutoReplyUtil.API + msg + AutoReplyUtil.LAST_STR;
        return httpStr;
    }

    public static <T> String getResult(T t,String msg){
        try {
            String result = HttpRequestUtil.get(AutoReplyUtil.getUrl(msg));
            if(!JsonUtil.isJSONValid(result) || t.getClass() == SingleMsgResult.class){
                return result;
            }
            JSONObject json = JSON.parseObject(result);
            JsonUtil.patchUpdate(t,json);
            return AutoReplyUtil.parseResult(t);
        }catch (Exception e){
            logger.error("itpk获取失败",e);
            return msg;
        }
    }

    public static <T> String parseResult(T t){
        if(t.getClass() == JokeMsgResult.class){
            JokeMsgResult joke = (JokeMsgResult)t;
            return joke.parseJoke();
        }else if(t.getClass() == GuanYinMsgResult.class){
            GuanYinMsgResult joke = (GuanYinMsgResult)t;
            return joke.parseGuanYin();
        }else if(t.getClass() == CaiShenYeMsgResult.class){
            CaiShenYeMsgResult joke = (CaiShenYeMsgResult)t;
            return joke.parseCaiShenYe();
        }else if(t.getClass() == YueLaoMsgResult.class){
            YueLaoMsgResult joke = (YueLaoMsgResult)t;
            return joke.parseYueLao();
        }

        return "";

    }


    public static void main(String[] args) {
//        String result = HttpRequestUtil.get(AutoReplyUtil.getUrl("笑话"));
//
//        JSONObject json = JSON.parseObject(result);
//
//        String title = json.getString("title");
//        String content = json.getString("content");
//
//        System.out.println("title = [" + title + "]");
//        System.out.println("content = [" + content + "]");

        String result = AutoReplyUtil.getResult(new JokeMsgResult(),"笑话");
        logger.info(result);
    }

}
