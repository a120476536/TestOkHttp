package com.forokhttp.request;

import android.widget.Toast;

import com.forokhttp.request.okhttp.OkHttpCallManager;
import com.forokhttp.request.okhttp.OkHttpHelper;
import com.forokhttp.request.packing.UploadFileResponse;
import com.forokhttp.request.utils.StringUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * Created by huangyaping on 16/4/29.
 */
public class HttpRequest {
    /**
     * okHttp 网络请求类
     * @param url
     */
    public static void get(String url) {
        get(url, null, null);
    }

    public static void get(String url, RequestParams params) {
        get(url, params, null);
    }

    public static void get(String url, OKHttpRequestCallback callback) {
        get(url, null, callback);
    }

    /**
     * Get请求
     * @param url
     * @param params
     * @param callback
     */
    public static void get(String url, RequestParams params, OKHttpRequestCallback callback) {
        get(url, params, RequestTime.QUEYEST_TIME, callback);
    }

    public static void get(String url, RequestParams params, long timeout, OKHttpRequestCallback callback) {
        OkHttpClient.Builder builder = OkHttpHelper.getInstance().getOkHttpClientBuilder();
        builder.readTimeout(timeout, TimeUnit.MILLISECONDS);
        builder.connectTimeout(timeout, TimeUnit.MILLISECONDS);
        builder.writeTimeout(timeout, TimeUnit.MILLISECONDS);
        executeRequest(POSTMethod.GET, url, params, builder, callback);
    }

    public static void get(String url, RequestParams params, OkHttpClient.Builder builder, OKHttpRequestCallback callback) {
        executeRequest(POSTMethod.GET, url, params, builder, callback);
    }

    public static void post(String url) {
        post(url, null, null);
    }

    public static void post(String url, RequestParams params) {
        post(url, params, null);
    }

    public static void post(String url, OKHttpRequestCallback callback) {
        post(url, null, callback);
    }

    /**
     * Post请求
     * @param url
     * @param params
     * @param callback
     */
    public static void post(String url, RequestParams params, OKHttpRequestCallback callback) {
        post(url, params, RequestTime.QUEYEST_TIME, callback);
    }

    public static void post(String url, RequestParams params, long timeout, OKHttpRequestCallback callback) {
        OkHttpClient.Builder builder = OkHttpHelper.getInstance().getOkHttpClientBuilder();
        builder.readTimeout(timeout, TimeUnit.MILLISECONDS);
        builder.connectTimeout(timeout, TimeUnit.MILLISECONDS);
        builder.writeTimeout(timeout, TimeUnit.MILLISECONDS);
        executeRequest(POSTMethod.POST, url, params, builder, callback);
    }

    public static void post(String url, RequestParams params, OkHttpClient.Builder builder, OKHttpRequestCallback callback) {
        executeRequest(POSTMethod.POST, url, params, builder, callback);
    }

    private static void executeRequest(POSTMethod method, String url, RequestParams params, OkHttpClient.Builder builder, OKHttpRequestCallback callback) {
        if (!StringUtils.isEmpty(url)) {
            if(builder == null) {
                builder = OkHttpHelper.getInstance().getOkHttpClientBuilder();
            }
            OKHttpNetWorkHelper okHttpNetWorkHelper = new OKHttpNetWorkHelper(method, url, params, builder, callback);
            okHttpNetWorkHelper.execute();
        }
    }

    /** 上传图片 */
    public static void postFile (String url, RequestParams params){
        post(url, params, new OKHttpRequestCallback<UploadFileResponse>(){

            @Override
            protected void onSuccess(UploadFileResponse uploadFileResponse) {
                super.onSuccess(uploadFileResponse);
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
            }

            @Override
            public void onProgress(int progress, long networkSpeed, boolean done) {
                super.onProgress(progress, networkSpeed, done);
            }
        });
    }

    /** 取消请求 */
    public static void cancel (String url){
        if (!StringUtils.isEmpty(url)){
            Call call = OkHttpCallManager.getInstance().getCall(url);
            if (call != null){
                call.cancel();
            }
            OkHttpCallManager.getInstance().removeCall(url);
        }
    }
}
