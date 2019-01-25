package com.ziyi.entity;

import com.alibaba.fastjson.JSONObject;

/**
 * 自动回复：笑话
 * @author ziyi
 * @create 2019/1/22
 */
public class JokeMsgResult {
    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String parseJoke(){
        StringBuffer sb = new StringBuffer();
        sb.append("【标题】" + this.getTitle() + "\n");
        sb.append(this.getContent() + "\n");
        return sb.toString();
    }
}
