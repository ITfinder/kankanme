package com.ziyi.entity;

import com.thoughtworks.xstream.XStream;

/**
 * @author ziyi
 * @create 2019/1/22
 */
public class ReplyTextMsg extends ReplyBaseMessage {
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
    /**
     * 将对象转换为XML
     * @return
     */
    public String Msg2Xml(){
        XStream xstream=new XStream();
        xstream.alias("xml", this.getClass());
        return xstream.toXML(this);
    }
}
