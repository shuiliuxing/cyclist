package com.huabing.cyclist.bean;

/**
 * Created by 30781 on 2017/7/14.
 */

public interface DownloadListener {
    void onProgress(int progress);  //进度
    void onSuccess();   //成功
    void onFail();    //失败
    void onPause();     //暂停
    void onCanceled();  //取消
}
