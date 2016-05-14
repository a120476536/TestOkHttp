package com.forokhttp.request.minterface;

/**
 * Created by huangyaping on 16/4/29.
 */
public interface ProgressCallback {
    /** 下载进度回调 */
    void updateProgress(int progress, long networkSpeed, boolean done);
}
