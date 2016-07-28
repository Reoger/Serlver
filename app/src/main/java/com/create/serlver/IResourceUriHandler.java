package com.create.serlver;

import java.io.IOException;

/**
 * Created by 24540 on 2016/7/27.
 */
public interface IResourceUriHandler {
    boolean accept(String uri);
     void handle(String uri,HttpContext httpContext) throws IOException;

}
