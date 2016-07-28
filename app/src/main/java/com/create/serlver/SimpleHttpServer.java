package com.create.serlver;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 24540 on 2016/7/27.
 */
public class SimpleHttpServer {
    private boolean isEnable;
    private final WebConfiguration wedConfig;
    private ServerSocket socket;
    private ExecutorService threadpool;
    private Set<IResourceUriHandler> resourceUriHandlers;

    public SimpleHttpServer(WebConfiguration wedConfig) {
        this.wedConfig = wedConfig;
        threadpool = Executors.newCachedThreadPool();
        resourceUriHandlers = new HashSet<IResourceUriHandler>();
    }

    /**
     * 启动server
     */
    public void startAsync() {
        isEnable = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                doPrcoSync();
            }
        }).start();
    }

    /**
     * 停止server
     */
    public void stopAsync() {
        if (isEnable) {
            isEnable = false;
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            socket = null;
        } else {
            return;
        }
    }

    private void doPrcoSync() {
        try {
            InetSocketAddress socketAdders = new InetSocketAddress(wedConfig.getPort());
            socket = new ServerSocket();
            socket.bind(socketAdders);
            while (isEnable) {
                final Socket remotePeer = socket.accept();
                threadpool.submit(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("TAg", "accept.." + remotePeer.getRemoteSocketAddress().toString());
                        onAcceptRemotePeet(remotePeer);
                    }

                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onAcceptRemotePeet(Socket remotePeer) {
        try {
            Log.d("", "+++++++++++++++++++++++++");
            //  remotePeer.getOutputStream().write("successful".getBytes());
            HttpContext httpContext = new HttpContext();
            httpContext.setUnderlySocket(remotePeer);
            InputStream inputStream = remotePeer.getInputStream();
            String headLine = null;

            String haha = Utils.readLine(inputStream);

            String bb = haha.split(" ")[1];
            final String resourceUri =bb;
            Log.d("TAg",resourceUri+"要判断的");

            while ((headLine = Utils.readLine(inputStream)) != null) {
                Log.d("TAg", "++++" + headLine);
                if (headLine.equals("\r\n")) {
                    break;
                }

                String[] pair = headLine.split(": ");
                if (pair.length > 1) {
                    httpContext.addRequestHeader(pair[0], pair[1]);
                }

                for (IResourceUriHandler handler : resourceUriHandlers) {
                    if (!handler.accept(resourceUri)) {
                        continue;
                    }
                    handler.handle(resourceUri, httpContext);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerResourceHandler(IResourceUriHandler handler) {
        resourceUriHandlers.add(handler);
    }

}
