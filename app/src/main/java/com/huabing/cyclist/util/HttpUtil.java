package com.huabing.cyclist.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by 30781 on 2017/3/26.
 */

public class HttpUtil {

    //发送请求
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback)
    {
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                                   .url(address)
                                   .build();
        client.newCall(request).enqueue(callback);
    }
}
