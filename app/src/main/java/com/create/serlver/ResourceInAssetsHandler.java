package com.create.serlver;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by 24540 on 2016/7/27.
 */
public class ResourceInAssetsHandler implements IResourceUriHandler{
    private  Context context;
    public String acceptPreFix = "/static/";

    public ResourceInAssetsHandler(Context context) {
        this.context = context;
    }

    @Override
    public boolean accept( final String uri) {
        boolean flag =uri.startsWith(acceptPreFix);
        return flag;
    }

    @Override
    public void handle(String uri, HttpContext httpContext) throws IOException {
//        OutputStream as = httpContext.getUnderlySocket().getOutputStream();
//        PrintStream out = new PrintStream(as);
//        out.println("HTTP/1.1 200 OK");
//        out.println();
//        out.println("from resource in assets handler");
//        out.flush();
//        out.close();
        int startIndex = acceptPreFix.length();
        String assetsPath = uri.substring(startIndex);
        InputStream fis = context.getAssets().open(assetsPath);
        byte[] raw = Utils.readRawFromSteam(fis);
        fis.close();
        OutputStream nos = httpContext.getUnderlySocket().getOutputStream();
         PrintStream printer = new PrintStream(nos);
        printer.println("HTTP/1.1 200 OK");
        printer.println("Content-length:"+raw.length);
        if(assetsPath.endsWith(".html")){
            printer.println("Content-Type:text/html");
        }else if(assetsPath.endsWith(".js")){
            printer.println("Content-Type:text/js");
        }else if(assetsPath.endsWith(".css")){
            printer.println("Content-Type:text/css");
        }else if(assetsPath.endsWith(".jpg")){
            printer.println("Content-Type:text/jpg");
        }else if(assetsPath.endsWith(".png")){
            printer.println("Content-Type:text/png");
        }
        printer.println();
        printer.write(raw);
        printer.flush();
    }
}
