package com.forokhttp.request;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.forokhttp.request.minterface.HttpContextHelper;
import com.forokhttp.request.okhttp.OkHttpHelper;
import com.forokhttp.request.packing.FilePacking;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.toolsfinal.StringUtils;
import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by huangyaping on 16/4/29.
 */
public class RequestParams {

    protected final Headers.Builder headers = new Headers.Builder();
    private final List<Paint> params = new ArrayList<>();
    private final List<Paint> files = new ArrayList<>();

    protected HttpContextHelper httpContextHelper;

    private String httpTaskKey;
    private RequestBody requestBody;
    private boolean applicationJson;
    /** 是否进行URL编码 */
    private boolean urlEncoder;
    private JSONObject jsonParams;
    protected CacheControl cacheControl;
    public RequestParams() {
        this(null);
    }

    public RequestParams(HttpContextHelper cycleContext) {
        this.httpContextHelper = cycleContext;
    }

    private void init() {
        headers.add("charset", "UTF-8");
        List<Paint> commonParams = OkHttpHelper.getInstance().getCommonParams();
        if (commonParams != null && commonParams.size() > 0){
            params.addAll(commonParams);
        }

        Headers commonHeaders = OkHttpHelper.getInstance().getCommonHeaders();
        if ( commonHeaders != null && commonHeaders.size() > 0 ) {
            for (int i = 0; i < commonHeaders.size(); i++) {
                String key = commonHeaders.name(i);
                String value = commonHeaders.value(i);
                headers.add(key, value);
            }
        }

        if ( httpContextHelper != null ) {
            httpTaskKey = httpContextHelper.getOkHttpTask();
        }
    }

    public String getHttpTaskKey() {
        return this.httpTaskKey;
    }

    /**
     * 添加参数
     * @param key
     * @param value
     */
    public void put(String key, String value) {
        try{
            if ( value == null ) {
                value = "";
            }

            Paint part = new Paint(key, value);
            if (!StringUtils.isEmpty(key) && !params.contains(part)) {
                params.add(part);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void put(String key, int value) {
        try{
            if (isKeyMap(key)) put(key, String.valueOf(value));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void put(String key, long value) {
        try{
            if (isKeyMap(key)) put(key, String.valueOf(value));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void put(String key, float value) {
        try {
            if (isKeyMap(key)) put(key, String.valueOf(value));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void put(String key, double value) {
        try{
            if (isKeyMap(key)) put(key, String.valueOf(value));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean isKeyMap (String key){
        return key == null ? true : false;
    }

    /**
     * @param key
     * @param file
     */
    public void put(String key, File file) {
        if (file == null || !file.exists() || file.length() == 0) {
            return;
        }

        boolean isPng = file.getName().lastIndexOf("png") > 0 || file.getName().lastIndexOf("PNG") > 0;
        if (isPng) {
            addFormDataPart(key, file, "image/png; charset=UTF-8");
            return;
        }

        boolean isJpg = file.getName().lastIndexOf("jpg") > 0 || file.getName().lastIndexOf("JPG") > 0
                ||file.getName().lastIndexOf("jpeg") > 0 || file.getName().lastIndexOf("JPEG") > 0;
        if (isJpg) {
            addFormDataPart(key, file, "image/jpeg; charset=UTF-8");
            return;
        }

        if (!isPng && !isJpg) {
            put(key, new FilePacking(file, null));
        }
    }

    public void addFormDataPart(String key, File file, String contentType) {
        if (file == null || !file.exists() || file.length() == 0) {
            return;
        }

        MediaType mediaType = null;
        try {
            mediaType = MediaType.parse(contentType);
        } catch (Exception e){
            e.printStackTrace();
        }

        put(key, new FilePacking(file, mediaType));
    }

    public void put(String key, File file, MediaType mediaType) {
        if (file == null || !file.exists() || file.length() == 0) {
            return;
        }
        put(key, new FilePacking(file, mediaType));
    }


    public void put(String key, List<File> files, MediaType mediaType) {
        for (File file:files){
            if (file == null || !file.exists() || file.length() == 0) {
                continue;
            }
            put(key, new FilePacking(file, mediaType));
        }
    }

    public void put(String key, FilePacking filePacking) {
        if (!StringUtils.isEmpty(key) && filePacking != null) {
            File file = filePacking.getFile();
            if (file == null || !file.exists() || file.length() == 0) {
                return;
            }
            files.add(new Paint(key, filePacking));
        }
    }

    public void put(String key, List<FilePacking> filePackings) {
        for (FilePacking file : filePackings){
            put(key, file);
        }
    }

    public void put(List<Paint> params) {
        this.params.addAll(params);
    }

    public void addHeader(String line) {
        headers.add(line);
    }

    public void addHeader(String key, String value) {
        if ( value == null ) {
            value = "";
        }

        if (!TextUtils.isEmpty(key)) {
            headers.add(key, value);
        }
    }

    public void addHeader(String key, int value) {
        addHeader(key, String.valueOf(value));
    }

    public void addHeader(String key, long value) {
        addHeader(key, String.valueOf(value));
    }

    public void addHeader(String key, float value) {
        addHeader(key, String.valueOf(value));
    }

    public void addHeader(String key, double value) {
        addHeader(key, String.valueOf(value));
    }

    public void addHeader(String key, boolean value) {
        addHeader(key, String.valueOf(value));
    }

    /**
     * URL编码，只对GET,DELETE,HEAD有效
     */
    public void urlEncoder() {
        urlEncoder = true;
    }

    public boolean isUrlEncoder() {
        return urlEncoder;
    }

    public void setCacheControl(CacheControl cacheControl) {
        this.cacheControl = cacheControl;
    }

    public void clear() {
        params.clear();
        files.clear();
    }

    /**
     * 设置application/json方式传递数据
     * @param jsonParams 请求的JSON实例
     */
    public void applicationJson(JSONObject jsonParams){
        applicationJson = true;
        this.jsonParams = jsonParams;
    }

    public void applicationJson() {
        applicationJson = true;
    }

    public void setCustomRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
    }

    public void setRequestBodyString(String string) {
        setRequestBody(MediaType.parse("text/plain; charset=utf-8"), string);
    }

    public void setRequestBody(String mediaType, String string) {
        setRequestBody(MediaType.parse(mediaType), string);
    }

    public void setRequestBody(MediaType mediaType, String string) {
        setCustomRequestBody(RequestBody.create(mediaType, string));
    }

    public List<Paint> getFormParams() {
        return params;
    }

    protected RequestBody getRequestBody() {
        RequestBody body = null;
        if (applicationJson) {
            String json;
            if (jsonParams == null) {
                JSONObject jsonObject = new JSONObject();
                for (Paint part : params) {
                    jsonObject.put(part.getKey(), part.getValue());
                }
                json = jsonObject.toJSONString();
            } else {
                json = jsonParams.toJSONString();
            }
            body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        } else if (requestBody != null) {
            body = requestBody;
        } else if (files.size() > 0) {
            boolean hasData = false;
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
            for (Paint part:params){
                String key = part.getKey();
                String value = part.getValue();
                builder.addFormDataPart(key, value);
                hasData = true;
            }

            for (Paint part:files){
                String key = part.getKey();
                FilePacking file = part.getFileWrapper();
                if (file != null) {
                    hasData = true;
                    builder.addFormDataPart(key, file.getFileName(), RequestBody.create(file.getMediaType(), file.getFile()));
                }
            }
            if (hasData) {
                body = builder.build();
            }
        } else {
            FormBody.Builder builder = new FormBody.Builder();
            for (Paint part:params){
                String key = part.getKey();
                String value = part.getValue();
                builder.add(key, value);
            }
            body = builder.build();
        }

        return body;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Paint part:params){
            String key = part.getKey();
            String value = part.getValue();
            if (result.length() > 0)
                result.append("&");

            result.append(key);
            result.append("=");
            result.append(value);
        }

        for (Paint part:files){
            String key = part.getKey();
            if (result.length() > 0)
                result.append("&");

            result.append(key);
            result.append("=");
            result.append("FILE");
        }

        if(jsonParams != null) {
            result.append(jsonParams.toJSONString());
        }

        return result.toString();
    }
}
