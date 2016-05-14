package com.forokhttp.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by huangyaping on 16/4/29.
 */
public class OKHttpTaskHandler {

    /** 正在请求的任务集合 */
    private static Map<String, List<OKHttpNetWorkHelper>> httpTaskMap;
    /** 单例请求处理器 */
    private static OKHttpTaskHandler okHttpTaskHandler = null;

    private OKHttpTaskHandler() {
        httpTaskMap = new ConcurrentHashMap<>();
    }

    /**
     * 获得处理器实例
     */
    public static OKHttpTaskHandler getInstance() {
        if (okHttpTaskHandler == null) {
            okHttpTaskHandler = new OKHttpTaskHandler();
        }
        return okHttpTaskHandler;
    }

    /**
     * 移除KEY
     * @param key
     */
    public void removeTask(String key) {
        if (httpTaskMap.containsKey(key)) {
            //移除对应的Key
            httpTaskMap.remove(key);
        }
    }

    /**
     * 将请求放到Map里面
     * @param key
     * @param task
     */
    void addTask(String key, OKHttpNetWorkHelper task) {
        if (httpTaskMap.containsKey(key)) {
            List<OKHttpNetWorkHelper> tasks = httpTaskMap.get(key);
            tasks.add(task);
            httpTaskMap.put(key, tasks);
        } else {
            List<OKHttpNetWorkHelper> tasks = new ArrayList<>();
            tasks.add(task);
            httpTaskMap.put(key, tasks);
        }
    }

    /**
     * 判断是否存在
     * @param key
     * @return
     */
    boolean contains(String key) {
        return httpTaskMap.containsKey(key);
    }
}
