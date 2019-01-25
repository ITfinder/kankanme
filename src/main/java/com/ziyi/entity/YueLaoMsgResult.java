package com.ziyi.entity;

import com.alibaba.fastjson.JSONObject;

/**
 * 自动回复：月老灵签
 * @author ziyi
 * @create 2019/1/22
 */
public class YueLaoMsgResult {

    private String number1;
    private String number2;
    private String haohua;
    private String shiyi;
    private String jieqian;
    private String zhushi;
    private String baihua;
    private String type;

    public String getNumber1() {
        return number1;
    }

    public void setNumber1(String number1) {
        this.number1 = number1;
    }

    public String getNumber2() {
        return number2;
    }

    public void setNumber2(String number2) {
        this.number2 = number2;
    }

    public String getHaohua() {
        return haohua;
    }

    public void setHaohua(String haohua) {
        this.haohua = haohua;
    }

    public String getZhushi() {
        return zhushi;
    }

    public void setZhushi(String zhushi) {
        this.zhushi = zhushi;
    }

    public String getBaihua() {
        return baihua;
    }

    public void setBaihua(String baihua) {
        this.baihua = baihua;
    }

    public String getShiyi() {
        return shiyi;
    }

    public void setShiyi(String shiyi) {
        this.shiyi = shiyi;
    }

    public String getJieqian() {
        return jieqian;
    }

    public void setJieqian(String jieqian) {
        this.jieqian = jieqian;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String parseYueLao(){
        StringBuffer sb = new StringBuffer();
        sb.append("您抽取的是第" + this.number2 + "签" + "\n");
        sb.append("签位：" + this.haohua + "\n");
        sb.append("签语：" + this.shiyi + "\n");
        sb.append("注释：" + this.zhushi + "\n");
        sb.append("解签：" + this.jieqian + "\n");
        sb.append("白话释义：" + this.baihua + "\n");

        return sb.toString();
    }
}
