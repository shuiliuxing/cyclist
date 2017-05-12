package com.huabing.cyclist.gson;

import com.huabing.cyclist.gson.musiclistgson.Result;

/**
 * Created by 30781 on 2017/5/7.
 */

public class MusicBean {
    private Result result;
    private int code;
    public void setResult(Result result) {
        this.result = result;
    }
    public Result getResult() {
        return result;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }
}
