package com.forokhttp.request;

/**
 * Created by huangyaping on 16/4/29.
 */
public class StringRequestCallbackHelper extends OKHttpRequestCallback<String> {

    public StringRequestCallbackHelper (){
        super();
        type = String.class;
    }
}
