package com.example.oucnews.Utils;

import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OKhttpUtils {

    private static OkHttpClient okHttpClient;

    public OKhttpUtils(){
        okHttpClient=new OkHttpClient().newBuilder()
                .callTimeout(8, TimeUnit.SECONDS)
                .connectTimeout(8,TimeUnit.SECONDS)
                .readTimeout(8,TimeUnit.SECONDS)
                .build();
    }

    private static OKhttpUtils instance;

    public static OKhttpUtils getInstance() {
        if (instance==null){
            instance=new OKhttpUtils();
        }
        return instance;
    }
    //数据返回
    public interface OkhttpCallBack{
        void onSuccess(Response response);
        void onFail(String error);
    }

    //get方法
    public static void get(final String url, final OkhttpCallBack okhttpCallBack){
        try {
            final Thread thread=new Thread(new Runnable() {
                @Override
                public void run() {

                    getInstance();
                    Request request = new Request.Builder()
                            .url(url)
                            .method("GET", null)
                            .addHeader("Accept", "application/json")
                            .addHeader("User-Agent", "apifox/1.0.26 (https://www.apifox.cn)")
                            .build();

                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            okhttpCallBack.onFail(e.getMessage()+"失败的");
                            Log.i("asd",e.getMessage());
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            okhttpCallBack.onSuccess(response);
                            Log.i("asd","OKHTTPUtils连接成功");
                        }
                    });
                }
            });
            thread.start();} catch (Exception e) {
            e.printStackTrace();
            Log.i("asd","OKHTTPUtils连接失败");
        }
    }
    //get方法
    public static void get_token( final String token,final String url, final OkhttpCallBack okhttpCallBack){
        try {
            final Thread thread=new Thread(new Runnable() {
                @Override
                public void run() {

                    getInstance();
                    Request request = new Request.Builder()
                            .url(url)
                            .method("GET", null)
                            .addHeader("Authorization", token)
                            .addHeader("User-Agent", "apifox/1.0.26 (https://www.apifox.cn)")
                            .build();

                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            okhttpCallBack.onFail(e.getMessage()+"asdfghjkl");
                            Log.i("asd",e.getMessage());
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            okhttpCallBack.onSuccess(response);
                            Log.i("asd","OKHTTPUtils连接成功");
                        }
                    });
                }
            });
            thread.start();} catch (Exception e) {
            e.printStackTrace();
            Log.i("asd","OKHTTPUtils连接失败");
        }
    }
    // post json数据  token数据
    public static void post_json(final String token,final String url, final String json,  final OkhttpCallBack okhttpCallBack) throws JSONException {

        try {
            Thread thread=new Thread(new Runnable() {
                @Override
                public void run() {
                    getInstance();
                    try {
                        JSONObject jsonObject=new JSONObject(json);

                        MediaType mediaType = MediaType.parse("application/json");
                        RequestBody body = RequestBody.create(mediaType, jsonObject.toString());

                        Log.i("asdqq", jsonObject.toString());
                        Request request = new Request.Builder()
                                .url(url)
                                .post(body)
                                .addHeader("Accept", "application/json")
                                .addHeader("Authorization", token)
                                .addHeader("User-Agent", "apifox/1.0.26 (https://www.apifox.cn)")
                                .addHeader("Content-Type", "application/json")
                                .build();
                        okHttpClient.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                okhttpCallBack.onFail(e.getMessage() + "失败的OKHTTP返回数据");
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                okhttpCallBack.onSuccess(response);
                                Log.i("asd","OKHTTPUtils连接成功");
                            }

                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();  } catch (Exception e) {
            e.printStackTrace();
            Log.i("asd","OKHTTPUtils连接失败");
        }
    }


}
