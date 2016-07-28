package com.create.serlver;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebConfiguration wc;
    private SimpleHttpServer shs;

    /**
     * 在模拟器上运行的时候，需要先telnet 127.0.0.1 5554
     * 然后再将本地ip映射到虚拟机ip，执行命令(redir add tcp:8088:8088)
     * redir add tcp:8088:8088
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wc = new WebConfiguration();
        wc.setPort(8088);
        wc.setMaxParallels(50);
        shs = new SimpleHttpServer(wc);
        shs.registerResourceHandler(new ResourceInAssetsHandler(this));
        shs.registerResourceHandler(new UpLoadImageHandler(){
            @Override
            protected void onImageLoaded(String path) {
                ShowImage(path);
            }
        });
        shs.startAsync();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        shs.stopAsync();
    }

    private void ShowImage(final String path) {

        runOnUiThread( new Runnable() {
            @Override
            public void run() {
                ImageView iv = (ImageView)findViewById(R.id.image);
                Bitmap bit = BitmapFactory.decodeFile(path);
                iv.setImageBitmap(bit);
                Toast.makeText(MainActivity.this,"上传图片成功ing",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
