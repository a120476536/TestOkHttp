package com.forokhttp.request.minterface;

/**
 * Created by huangyaping on 16/4/29.
 */
public interface NetWorkDataResultHelper {
    /**
     *  请求回调
     * @param json
     */

    void onSuccess (String json);

    void onFailure (int error , String msg);
}
