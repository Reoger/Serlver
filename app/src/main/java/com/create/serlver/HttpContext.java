package com.create.serlver;

import java.net.Socket;
import java.util.HashMap;

/**
 * Created by 24540 on 2016/7/27.
 */
public class HttpContext {
    private Socket underlySocket;
    private HashMap<String, String> requestHeader;

    public HttpContext() {
       requestHeader = new HashMap<String, String>();
    }

    public void setUnderlySocket(Socket socket) {
        this.underlySocket = socket;
    }

    public Socket getUnderlySocket() {
       return underlySocket;
    }

    public void addRequestHeader(String s, String s1) {
        requestHeader.put(s,s1);
    }

    public String getRequestHeaderValue(String headerName){
       return  requestHeader.get(headerName);
    }
}
