package com.forokhttp.request.okhttp;

import android.text.TextUtils;

import com.forokhttp.request.Paint;
import com.forokhttp.request.RequestTime;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;

import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.OkHttpClient;

/**
 * Created by huangyaping on 16/4/29.
 */
public class OkHttpHelper {

    private OkHttpClient okHttpClient;

    private static OkHttpHelper okHttpHelper;
    private OkHttpConfigurHelper configuration;

    private OkHttpHelper() {
    }

    public static OkHttpHelper getInstance() {
        if (okHttpHelper == null) {
            okHttpHelper = new OkHttpHelper();
        }
        return okHttpHelper;
    }

    public synchronized void init (OkHttpConfigurHelper configuration){
        this.configuration = configuration;
        long timeout = configuration.getTimeout();
        OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(timeout, TimeUnit.MILLISECONDS).writeTimeout(timeout, TimeUnit.MILLISECONDS).readTimeout(timeout, TimeUnit.MILLISECONDS);
        if ( configuration.getHostnameVerifier() != null ) {
            builder.hostnameVerifier(configuration.getHostnameVerifier());
        }

        List<InputStream> certificateList = configuration.getCertificateList();
        if (certificateList != null && certificateList.size() > 0) {
            OkHttpManager.getInstance(builder).setCertificates(certificateList);
        }

        CookieJar cookieJar = configuration.getCookieJar();
        if (cookieJar != null) {
            builder.cookieJar(cookieJar);
        }

        if(configuration.getCache() != null) {
            builder.cache(configuration.getCache());
        }

        if (configuration.getAuthenticator() != null){
            builder.authenticator(configuration.getAuthenticator());
        }
        if (configuration.getCertificatePinner() != null) {
            builder.certificatePinner(configuration.getCertificatePinner());
        }
        builder.followRedirects(configuration.isFollowRedirects());
        builder.followSslRedirects(configuration.isFollowSslRedirects());
        if(configuration.getSslSocketFactory() != null) {
            builder.sslSocketFactory(configuration.getSslSocketFactory());
        }
        if(configuration.getDispatcher() != null) {
            builder.dispatcher(configuration.getDispatcher());
        }
        builder.retryOnConnectionFailure(configuration.isRetryOnConnectionFailure());
        if (configuration.getNetworkInterceptorList() != null) {
            builder.networkInterceptors().addAll(configuration.getNetworkInterceptorList());
        }
        if (configuration.getInterceptorList() != null) {
            builder.interceptors().addAll(configuration.getInterceptorList());
        }

        if(configuration.getProxy() != null){
            builder.proxy(configuration.getProxy());
        }
        RequestTime.DEBUG = configuration.isDebug();

        okHttpClient = builder.build();
    }

    /**
     * 修改公共请求参数信息
     * @param key
     * @param value
     */
    public void updateCommonParams(String key, String value) {
        boolean add = false;
        List<Paint> commonParams = configuration.getCommonParams();
        if (commonParams != null){
            for (Paint param : commonParams) {
                if (param != null && TextUtils.equals(param.getKey(), key)){
                    param.setValue(value);
                    add = true;
                    break;
                }
            }
        }
        if (!add) {
            commonParams.add(new Paint(key, value));
        }
    }

    /**
     * 修改公共header信息
     * @param key
     * @param value
     */
    public void updateCommonHeader(String key, String value) {
        Headers headers = configuration.getCommonHeaders();
        if ( headers == null){
            headers = new Headers.Builder().build();
        }
        configuration.commonHeaders = headers.newBuilder().set(key, value).build();
    }

    @Deprecated
    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public OkHttpClient.Builder getOkHttpClientBuilder() {
        return okHttpClient.newBuilder();
    }

    public List<Paint> getCommonParams() {
        return configuration.getCommonParams();
    }

    public List<InputStream> getCertificateList() {
        return configuration.getCertificateList();
    }

    public HostnameVerifier getHostnameVerifier() {
        return configuration.getHostnameVerifier();
    }

    public long getTimeout() {
        return configuration.getTimeout();
    }

    public Headers getCommonHeaders() {
        return configuration.getCommonHeaders();
    }
}
