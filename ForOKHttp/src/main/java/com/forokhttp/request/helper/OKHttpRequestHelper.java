package com.forokhttp.request.helper;

import com.forokhttp.request.HttpRequest;
import com.forokhttp.request.OKHttpRequestCallback;
import com.forokhttp.request.Paint;
import com.forokhttp.request.RequestParams;
import com.forokhttp.request.RequestTime;
import com.forokhttp.request.StringRequestCallbackHelper;
import com.forokhttp.request.minterface.NetWorkDataResultHelper;
import com.forokhttp.request.minterface.NetWorkFileResultHelper;
import com.forokhttp.request.okhttp.OkHttpConfigurHelper;
import com.forokhttp.request.okhttp.OkHttpHelper;
import com.forokhttp.request.packing.UploadFileResponse;
import com.forokhttp.request.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;
import okhttp3.Interceptor;

/**
 * Created by huangyaping on 16/4/29.
 */
public class OKHttpRequestHelper {

    private static NetWorkFileResultHelper netWorkFileResultHelper;
    private static OKHttpRequestHelper networdRequestHelper;
    private NetWorkDataResultHelper datahelper;

    public OKHttpRequestHelper(){};

    public static synchronized OKHttpRequestHelper getInstance (){
        if (null == networdRequestHelper){
            networdRequestHelper = new OKHttpRequestHelper();
        }
        return networdRequestHelper;
    }

    public void init (){
        List<Paint> commomParams = new ArrayList<>();
        Headers commonHeaders = new Headers.Builder().build();
        List<Interceptor> interceptorList = new ArrayList<>();
        OkHttpConfigurHelper.Builder builder = new OkHttpConfigurHelper.Builder()
                .setCommenParams(commomParams)
                .setCommenHeaders(commonHeaders)
                .setTimeout(RequestTime.QUEYEST_TIME)
                .setInterceptors(interceptorList)
                .setDebug(true);
        OkHttpHelper.getInstance().init(builder.build());
    }

    /** POST 请求 */
    public void post (String url,RequestParams params,NetWorkDataResultHelper netWorkDataResultHelper){
        this.datahelper = netWorkDataResultHelper;
        HttpRequest.post(url, params, new StringRequestCallbackHelper() {
            @Override
            protected void onSuccess(String s) {
                super.onSuccess(s);
                datahelper.onSuccess(s);
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                datahelper.onFailure(errorCode, msg);
            }
        });
    }

    /** Get 请求 */
    public void get (String url,RequestParams params,NetWorkDataResultHelper netWorkDataResultHelper){
        this.datahelper = netWorkDataResultHelper;
        HttpRequest.get(url, params, new StringRequestCallbackHelper() {
            @Override
            protected void onSuccess(String s) {
                super.onSuccess(s);
                datahelper.onSuccess(s);
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                datahelper.onFailure(errorCode, msg);
            }
        });
    }

    /** 上传图片 */
    public void uploadFile (String url, final RequestParams params, NetWorkFileResultHelper fileResultHelper){
        this.netWorkFileResultHelper = fileResultHelper;
        HttpRequest.post(url, params, new OKHttpRequestCallback<UploadFileResponse>() {

            @Override
            protected void onSuccess(UploadFileResponse uploadFileResponse) {
                super.onSuccess(uploadFileResponse);
                netWorkFileResultHelper.onSuccess(uploadFileResponse);
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                netWorkFileResultHelper.onFailure(errorCode, msg);
            }

            @Override
            public void onProgress(int progress, long networkSpeed, boolean done) {
                super.onProgress(progress, networkSpeed, done);
                netWorkFileResultHelper.onProgress(progress, networkSpeed, done);
            }
        });
    }

    /** 取消请求 */
    public void cancel (String url){
        if (!StringUtils.isEmpty(url)) HttpRequest.cancel(url);
    }
}
