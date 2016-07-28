package com.create.serlver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by 24540 on 2016/7/27.
 */
public class UpLoadImageHandler implements IResourceUriHandler{
    public String acceptPreFix = "/upload_image/";

    @Override
    public boolean accept(final String uri) {
        boolean flag = uri.startsWith(acceptPreFix);
        return flag;
    }

    @Override
    public void handle(String uri, HttpContext httpContext) throws IOException {
//        OutputStream as = httpContext.getUnderlySocket().getOutputStream();
//        PrintStream out = new PrintStream(as);
//        out.println("HTTP/1.1 200 OK");
//        out.println();
//        out.println("from upload image in assets handler");
//        out.flush();

        String tmpath = "/mnt/sdcard/test_upload.jpg";
         long totalLength=Long.parseLong(httpContext.getRequestHeaderValue("Content-Length"));
        FileOutputStream fos = new FileOutputStream(tmpath);
        InputStream nis = httpContext.getUnderlySocket().getInputStream();
        byte[] buffer = new byte[10240];
        int nReaded = 0;
        long mLeftLength = totalLength;
        while((nReaded = nis.read(buffer))> 0 && mLeftLength >0) {
            fos.write(buffer,0,nReaded);
            mLeftLength -= nReaded;
        }
        fos.close();

        OutputStream nos = httpContext.getUnderlySocket().getOutputStream();
         PrintStream out = new PrintStream(nos);
        out.println("HTTP/1.1 200 OK");
        out.println();
        out.flush();

        onImageLoaded(tmpath);
    }

    protected void onImageLoaded(String path){

    }
}
