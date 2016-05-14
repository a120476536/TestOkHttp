package com.forokhttp.request;

import okhttp3.Headers;
import okhttp3.Response;

/**
 * Created by huangyaping on 16/4/29.
 */
public class OKHttpResponseData {
    /** http是否无响应 */
    private boolean responseNull;
    /** 是否请求超时 */
    private boolean timeout;//
    /** http code */
    private int code;
    /** http响应消息 */
    private String message;
    /** http响应结果 */
    private String response;
    /** 是否成功 */
    private boolean success;
    /** http headers */
    private Headers headers;

    private Response httpResponse;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public boolean isResponseNull() {
        return responseNull;
    }

    public void setResponseNull(boolean responseNull) {
        this.responseNull = responseNull;
    }

    public boolean isTimeout() {
        return timeout;
    }

    public void setTimeout(boolean timeout) {
        this.timeout = timeout;
    }

    public Response getHttpResponse() {
        return httpResponse;
    }

    public void setHttpResponse(Response httpResponse) {
        this.httpResponse = httpResponse;
    }
}
