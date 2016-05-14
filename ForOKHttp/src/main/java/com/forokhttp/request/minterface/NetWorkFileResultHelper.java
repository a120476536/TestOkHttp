package com.forokhttp.request.minterface;

import com.forokhttp.request.packing.UploadFileResponse;

/**
 * Created by huangyaping on 16/4/30.
 */
public interface NetWorkFileResultHelper {
    void onSuccess (UploadFileResponse uploadFileResponse);
    void onFailure (int errorCode, String msg);
    void onProgress (int progress, long networkSpeed, boolean done);
}
