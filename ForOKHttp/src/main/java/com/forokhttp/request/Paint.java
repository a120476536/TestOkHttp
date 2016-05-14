package com.forokhttp.request;

import android.text.TextUtils;

import com.forokhttp.request.packing.FilePacking;

/**
 * Created by huangyaping on 16/4/29.
 */
public class Paint {
    private String key;
    private String value;
    private FilePacking filePacking;

    public Paint(String key, String value) {
        setKey(key);
        setValue(value);
    }

    public Paint(String key, FilePacking filePacking) {
        setKey(key);
        this.filePacking = filePacking;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public FilePacking getFileWrapper() {
        return filePacking;
    }

    protected void setKey(String key) {
        if(key == null) {
            this.key = "";
        } else {
            this.key = key;
        }
    }

    public void setValue(String value) {
        if(value == null) {
            this.value = "";
        } else {
            this.value = value;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Paint)){
            return false;
        }
        Paint part = (Paint) obj;
        if (part == null){
            return false;
        }
        if (TextUtils.equals(part.getKey(), getKey()) && TextUtils.equals(part.getValue(), getValue())){
            return true;
        }
        return false;
    }
}
