package com.ziyi.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ziyi
 * @create 2019/1/22
 */
public class ServerIp {
    private List<String> ipList = new ArrayList<String>();

    public List<String> getIpList() {
        return ipList;
    }

    public void setIpList(List<String> ipList) {
        this.ipList = ipList;
    }
}
