package com.ziyi.entity;

import com.alibaba.fastjson.JSONObject;

/**
 * 自动回复：财神爷灵签
 * @author ziyi
 * @create 2019/1/22
 */
public class CaiShenYeMsgResult {

    private String number1;
    private String number2;
    private String qianyu;
    private String zhushi;
    private String jieqian;
    private String jieshuo;
    private String jieguo;
    private String hunyin;
    private String jiaoyi;
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

    public String getQianyu() {
        return qianyu;
    }

    public void setQianyu(String qianyu) {
        this.qianyu = qianyu;
    }

    public String getZhushi() {
        return zhushi;
    }

    public void setZhushi(String zhushi) {
        this.zhushi = zhushi;
    }

    public String getJieqian() {
        return jieqian;
    }

    public void setJieqian(String jieqian) {
        this.jieqian = jieqian;
    }

    public String getJieshuo() {
        return jieshuo;
    }

    public void setJieshuo(String jieshuo) {
        this.jieshuo = jieshuo;
    }

    public String getJieguo() {
        return jieguo;
    }

    public void setJieguo(String jieguo) {
        this.jieguo = jieguo;
    }

    public String getHunyin() {
        return hunyin;
    }

    public void setHunyin(String hunyin) {
        this.hunyin = hunyin;
    }

    public String getJiaoyi() {
        return jiaoyi;
    }

    public void setJiaoyi(String jiaoyi) {
        this.jiaoyi = jiaoyi;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String parseCaiShenYe(){
        StringBuffer sb = new StringBuffer();
        sb.append("您抽取的是第" + this.number2 + "签" + "\n");
        sb.append("签语：" + this.qianyu + "\n");
        sb.append("注释：" + this.zhushi + "\n");
        sb.append("解签：" + this.jieqian + "\n");
        sb.append("解说：" + this.jieshuo + "\n");

        return sb.toString();
    }

}
