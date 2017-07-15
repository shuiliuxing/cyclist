package com.huabing.cyclist.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.huabing.cyclist.MusicDetailActivity;
import com.huabing.cyclist.R;
import com.huabing.cyclist.bean.DownloadListener;
import com.huabing.cyclist.bean.DownloadTask;

import java.io.File;

/**
 * Created by 30781 on 2017/7/15.
 */

public class DownloadService extends Service {
    private DownloadTask downloadTask;
    private DownloadBinder mBinder=new DownloadBinder();
    private String address;

    //创建通知
    private Notification getNotification(String title,int progress)
    {
        //点击通知后事件
        Intent intent=new Intent(this, MusicDetailActivity.class);
        PendingIntent pi= PendingIntent.getActivity(this,0,intent,0);
        //通知
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.music_down);  //通知小图标
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.music_down)); //通知大图标
        builder.setContentTitle(title); //标题
        builder.setContentIntent(pi);  //通知点击事件

        if(progress>0)  //当下载进度大于或等于0时才需要显示下载进度
        {
            builder.setContentText(progress+"%");
            builder.setProgress(100,progress,false);
        }
        return builder.build();
    }

    //获取通知管理
    private NotificationManager getNotificationManager()
    {
        return (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    }
    //DownloadListener
    private DownloadListener listener=new DownloadListener() {

        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(1,getNotification("下载中...",progress));
        }
        @Override
        public void onSuccess() {
            downloadTask=null;
            //下载成功时关闭前台服务通知
            stopForeground(true);
            //创建新的成功通知
            getNotificationManager().notify(1,getNotification("下载成功！",-1));
            Toast.makeText(DownloadService.this,"下载成功！",Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onFail()
        {
            downloadTask=null;
            //下载失败关闭前台服务通知
            stopForeground(true);
            //创建新的失败通知
            getNotificationManager().notify(1,getNotification("下载失败！",-1));
            Toast.makeText(DownloadService.this,"下载失败",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPause() {
            downloadTask=null;
            Toast.makeText(DownloadService.this,"暂停！",Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onCanceled() {
            downloadTask=null;
            //下载取消时关闭前台服务通知
            stopForeground(true);
            Toast.makeText(DownloadService.this,"下载取消！",Toast.LENGTH_SHORT).show();
        }
    };

    //Binder类
    public class DownloadBinder extends Binder
    {
        //开始下载
        public void startDownload(String url)
        {
            if(downloadTask==null)
            {
                address=url;
                //开始异步加载
                downloadTask=new DownloadTask(listener);
                downloadTask.execute(address);

                startForeground(1,getNotification("下载中...",0));
                Toast.makeText(DownloadService.this,"下载中",Toast.LENGTH_SHORT).show();

            }
        }

        //暂停下载
        public void pauseDownload()
        {
            if(downloadTask!=null)
            {
                downloadTask.pauseDownload();
            }
        }

        //取消下载
        public void cancelDownload()
        {
            if(downloadTask!=null)  //没启动下载的
            {
                downloadTask.cancelDownload();
            }
            else   //启动了的
            {
                //下载地址不为空
                if(address!=null)
                {
                    //删除文件
                    String name=address.substring(address.lastIndexOf("/"));//下载的文件名
                    String directory= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();//下载存放的外存目录
                    File file=new File(directory+name);  //文件对象

                    if(file.exists())  //文件存在则删除
                    {
                        file.delete();
                    }

                    //取消通知
                    getNotificationManager().cancel(1);
                    //暂停服务
                    stopForeground(true);
                    Toast.makeText(DownloadService.this,"下载取消！",Toast.LENGTH_SHORT).show();
                }
            }
        }


    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return mBinder;
    }
}
