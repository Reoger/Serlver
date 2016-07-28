package com.create.serlver;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 24540 on 2016/7/27.
 */
public class Utils {

    public static final String readLine(InputStream mis) throws IOException {
        StringBuilder sb = new StringBuilder();
        int c1 =0;
        int c2 =0;
        while(c2 != -1 && !(c1=='\r' && c2=='\n')){
                c1 = c2;
                c2 = mis.read();
                sb.append((char)c2);
        }
        Log.d("TAg","TAg"+sb.toString());

        if(sb.length() ==0){
            return null;
        }
        if(sb.length()>2){
            return sb.toString().substring(0,sb.length()-2);
        }
        return sb.toString();
    }

    public static byte[] readRawFromSteam(InputStream fis) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[10240];
        int nReaded;
        while((nReaded = fis.read(buffer))>0){
            bos.write(buffer,0,nReaded);
        }
        return bos.toByteArray();
    }
}
