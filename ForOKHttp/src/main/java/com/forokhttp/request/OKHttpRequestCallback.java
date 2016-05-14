package com.forokhttp.request;

import android.util.Log;

import java.lang.reflect.Type;

import okhttp3.Headers;
import okhttp3.Response;

/**
 * Created by huangyaping on 16/4/29.
 */
public class OKHttpRequestCallback<T> {
    /**
     * OKHttp 请求回调
     */
    public static final int ERROR_RESPONSE_DATA_PARSE_EXCEPTION = 1002;
    public static final int ERROR_RESPONSE_UNKNOWN = 1003;

    protected Type type;
    protected Headers headers;

    public OKHttpRequestCallback() {
        type = ClassTypeReflect.getModelClazz(getClass());
    }

    public void onStart() {
    }

    public void onResponse(Response httpResponse, String response, Headers headers) {

    }

    public void onResponse(String response, Headers headers) {
    }

    public void onFinish() {
    }

    protected void onSuccess(Headers headers, T t) {
    }
    protected void onSuccess(T t) {

    }

    public void onProgress(int progress, long networkSpeed, boolean done){
    }

    public void onFailure(int errorCode, String msg) {
    }

    public Headers getHeaders() {
        return headers;
    }

    protected void setResponseHeaders(Headers headers) {
        this.headers = headers;
    }
}
