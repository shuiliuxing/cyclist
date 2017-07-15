package com.huabing.cyclist.bean;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 30781 on 2017/7/14.
 */

public class DownloadTask extends AsyncTask<String,Integer,Integer> {
    public static final int TYPE_SUCCESS=0;
    public static final int TYPE_FAIL=1;
    public static final int TYPE_PAUSE=2;
    public static final int TYPE_CANCEL=3;

    private DownloadListener listener;

    private boolean isPause=false;
    private boolean isCancel=false;

    private int lastProgress;  //上一次的下载进度

    public DownloadTask(DownloadListener listener)
    {
        this.listener=listener;
    }

    //获取网上下载文件的长度
    public long getContentLength(String address) throws IOException
    {
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(address).build();
        Response response=client.newCall(request).execute();

        //获取文件流
        if(response!=null && response.isSuccessful())
        {
            //获取文件长度
            long contentLength=response.body().contentLength();
            //关闭response
            response.close();
            return contentLength;
        }
        return 0;
    }

    //后台下载
    @Override
    protected Integer doInBackground(String... params)
    {
        InputStream is=null;
        RandomAccessFile randomAccessFile=null;
        File file=null;

        try
        {
            //下载进度
            long downLength=0;
            //音乐外链地址
            String address=params[0];
            //从音乐外链截取文件名
            String fileName=address.substring(address.lastIndexOf("/"));
            //下载文件目录路径
            String fileDirectory= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            //在手机存储中创建存放的音乐文件
            file=new File(fileDirectory+fileName);
            if(file.exists()) //若文件存在则当前下载长度为文件长度
            {
                downLength=file.length();
            }
            //网上下载的文件长度
            long contentLength=getContentLength(address);
            if(contentLength==0)  //如果要下载文件为零,下载失败
            {
                return TYPE_FAIL;
            }
            else if(contentLength==downLength) //档期下载进度等于音乐文件总长度，下载成功
            {
                return TYPE_SUCCESS;
            }
            //下载中......
            OkHttpClient client=new OkHttpClient();
            Request request=new Request.Builder()
                    .addHeader("RANGE","bytes="+downLength+"-")
                    .url(address)
                    .build();
            Response response=client.newCall(request).execute();

            //有响应
            if(response!=null)
            {
                //获取的文件流
                is = response.body().byteStream();
                //随机可读写文件对象
                randomAccessFile = new RandomAccessFile(file, "rw");
                //定位到之前已下载的长度
                randomAccessFile.seek(downLength);
                byte[] byteArray = new byte[1024];
                int total = 0;
                int len;
                //每次读取1024个字节
                while ((len=is.read(byteArray)) != -1)
                {
                    if (isCancel) //如果取消
                        return TYPE_CANCEL;
                    else if (isPause)  //如果暂停
                        return TYPE_PAUSE;
                    else
                    {
                        total += len;
                        //写入本地文件
                        randomAccessFile.write(byteArray, 0, len);
                        //计算已下载的百分比
                        int progress = (int) ((total + downLength) * 100 / contentLength);
                        //显示进度
                        publishProgress(progress);
                    }
                }
                response.body().close();
                return TYPE_SUCCESS;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(is!=null) {
                    is.close();
                }
                if(randomAccessFile!=null)
                {
                    randomAccessFile.close();
                }
                if(isCancel && file!=null)
                {
                    file.delete();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return TYPE_FAIL;
    }

    //下载进度显示
    @Override
    protected void onProgressUpdate(Integer... values)
    {
        int progress=values[0];
        if(progress>lastProgress)
        {
            listener.onProgress(progress);
            lastProgress=progress;
        }
    }

    //下载完成后
    @Override
    protected void onPostExecute(Integer status)
    {
        switch(status)
        {
            case TYPE_SUCCESS:
                listener.onSuccess();
                break;
            case TYPE_FAIL:
                listener.onFail();
                break;
            case TYPE_PAUSE:
                listener.onPause();
                break;
            case TYPE_CANCEL:
                listener.onCanceled();
                break;
            default:
                break;
        }
    }

    //暂停下载
    public void pauseDownload()
    {
        isPause=true;
    }

    //取消下载
    public void cancelDownload()
    {
        isCancel=true;
    }

}
