package com.forokhttp.request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by huangyaping on 16/4/29.
 */
public class RequestUtils {

    /**
     * 处理请求接口地址
     * @param api
     * @param params
     * @param urlEncoder
     * @return
     */
    public static String getUrl (String api, List<Paint> params, boolean urlEncoder){
        StringBuffer url = new StringBuffer();
        url.append(api);
        if (url.indexOf("?", 0) < 0 && params.size() > 0) {
            url.append("?");
        }
        int flag = 0;
        for (Paint part : params){
            String key = part.getKey();
            String value = part.getValue();
            if(urlEncoder){//只对key和value编码
                try {
                    key = URLEncoder.encode(key, "UTF-8");
                    value = URLEncoder.encode(value, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            url.append(key).append("=").append(value);
            if (++flag != params.size()){
                url.append("&");
            }
        }

        return url.toString();
    }
}
