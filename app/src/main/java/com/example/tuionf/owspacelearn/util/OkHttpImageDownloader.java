package com.example.tuionf.owspacelearn.util;

import com.orhanobut.logger.Logger;

import org.eclipse.core.internal.utils.FileUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author tuionf
 * @date 2017/12/23
 * @email 596019286@qq.com
 * @explain
 */

public class OkHttpImageDownloader {
    public static void download(String url){
        Request request = new Request.Builder().url(url).build();
        HttpUtils.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.d(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                FileUtils.createSdDir();
                String url = response.request().url().toString();
                int index = url.lastIndexOf("/");
                String pictureName = url.substring(index+1);
                if(FileUtils.isFileExist(pictureName)){
                    return;
                }
                Logger.i("pictureName="+pictureName);
                FileOutputStream fos = new FileOutputStream(FileUtils.createFile(pictureName));
                InputStream in = response.body().byteStream();
                byte[] buf = new byte[1024];
                int len = 0;
                while ((len = in.read(buf))!=-1){
                    fos.write(buf,0,len);
                }
                fos.flush();
                in.close();
                fos.close();
            }
        });
    }
}
