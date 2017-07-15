package com.huabing.cyclist;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.huabing.cyclist.adapter.SectionsPagerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final int TAKE_PHOTO=1;
    public static final int CHOOSE_PHOTO=2;
    private DrawerLayout dlLayout;
    private Toolbar tbToolbar;
    private NavigationView navView;
    private CircleImageView civNav;
    private ViewPager vpPager;
    private BottomNavigationBar bnbNavigation;

    private FragmentManager fragManager;
    private List<Fragment> fragmentList;

    private Uri imgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //修改状态栏
        if(Build.VERSION.SDK_INT>=21)
        {
            View view=getWindow().getDecorView();
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_main);
        //getWindow().setFormat(PixelFormat.TRANSLUCENT);
        //滑动菜单DrawerLayout布局
        dlLayout=(DrawerLayout)findViewById(R.id.dl_layout);
        //标题栏Toolbar
        tbToolbar=(Toolbar)findViewById(R.id.tb_toolbar);
        tbToolbar.setTitle("骑行者");
        tbToolbar.setPopupTheme(R.style.OverflowMenu);//设置弹出的MenuItem字体为白色
        setSupportActionBar(tbToolbar);
        //导航按钮HomeAsUp
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.actionbar_menu);
        }
        //滑动菜单页面NavigationView
        navView=(NavigationView)findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.civ_nav:
                        Toast.makeText(MainActivity.this,"你好",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_record:
                        Toast.makeText(MainActivity.this,"点击了",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                dlLayout.closeDrawers();
                return true;
            }
        });
        //圆形按钮
        final View view=navView.inflateHeaderView(R.layout.nav_head);
        civNav=(CircleImageView)view.findViewById(R.id.civ_nav);
        civNav.setOnClickListener(this);
        navView.setCheckedItem(R.id.nav_record);

        //初始化ViewPager并添加Fragment(静态工厂方法)
        vpPager=(ViewPager)findViewById(R.id.vp_pager);
        fragmentList=new ArrayList<Fragment>();
        fragmentList.add(WeatherFragment.newInstance("天气"));
        fragmentList.add(CycleFragment.newInstance("骑行"));
        fragmentList.add(MusicFragment.newInstance("音乐"));
        fragmentList.add(NewsFragment.newInstance("新闻"));
        fragManager=getSupportFragmentManager();
        SectionsPagerAdapter adapter=new SectionsPagerAdapter(fragManager,fragmentList);
        vpPager.setAdapter(adapter);
        //ViewPager事件
        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                bnbNavigation.selectTab(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //底部导航栏
        bnbNavigation=(BottomNavigationBar)findViewById(R.id.bnb_navigation);
        bnbNavigation.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bnbNavigation.setMode(BottomNavigationBar.MODE_FIXED);
        bnbNavigation.addItem(new BottomNavigationItem(R.drawable.ic_home,"主页")
                                .setActiveColorResource(R.color.colorBlue))
                     .addItem(new BottomNavigationItem(R.drawable.ic_cycle,"骑行")
                                .setActiveColorResource(R.color.colorBlue))
                     .addItem(new BottomNavigationItem(R.drawable.ic_music,"音乐")
                                .setActiveColorResource(R.color.colorBlue))
                     .addItem(new BottomNavigationItem(R.drawable.ic_news,"新闻")
                             .setActiveColorResource(R.color.colorBlue))
                     .initialise();
        bnbNavigation.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                vpPager.setCurrentItem(position);
                switch (position)
                {
                    case 0:
                        tbToolbar.setTitle("天气");
                        break;
                    case 1:
                        tbToolbar.setTitle("骑行");
                        break;
                    case 2:
                        tbToolbar.setTitle("音乐");
                        break;
                    case 3:
                        tbToolbar.setTitle("新闻");
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void onTabUnselected(int position) {

            }
            @Override
            public void onTabReselected(int position) {

            }
        });

        bnbNavigation.selectTab(2);
    }

    //加入标题栏菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    //标题栏菜单点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            //导航HomeAsUp按钮事件
            case android.R.id.home:
                dlLayout.openDrawer(GravityCompat.START);
                break;
            //照相机
            case R.id.it_photo:
                takePhoto();
                break;
            case R.id.it_scan:
                scanQRCode();
                break;
            case R.id.it_light:
                openLight();
                break;
            default:
                break;
        }
        return true;
    }

    //标题栏MenuItem显示图标
    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class)
            {
                try
                {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }
    //拍照
    private void takePhoto()
    {
        //创建File对象，用于存储拍照后的图片
        File file=new File(getExternalCacheDir(),"photo_image.jpg");
        try
        {
            if(file.exists())
                file.delete();
            //创建图片文件
            file.createNewFile();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //获取图片路径URI
        if(Build.VERSION.SDK_INT>=24)
            imgUri= FileProvider.getUriForFile(MainActivity.this,"com.huabing.cyclist.fileprovider",file);
        else
            imgUri=Uri.fromFile(file);
        //启动相机程序
        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imgUri);
        startActivityForResult(intent,TAKE_PHOTO);
    }

    //扫一扫
    private void scanQRCode()
    {

    }

    //手电筒
    private void openLight()
    {

    }

    //从相册选择头像
    private void circleImageAlbum()
    {
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }


    //MainActivity各类点击事件
    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.civ_nav:
                if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        !=PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{},1);
                }
                else
                    circleImageAlbum();
                break;
            default:
                break;
        }
    }

    //权限管理
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults)
    {
        switch(requestCode)
        {
            case 1:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    circleImageAlbum();
                }
                else
                    Toast.makeText(MainActivity.this,"你禁止了权限！",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    //下一个Activity返回处理
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        switch(requestCode)
        {
            case TAKE_PHOTO:
                if(resultCode==RESULT_OK)
                {
                    try
                    {
                        //将拍摄的照片显示出来
                        Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(imgUri));
                        Toast.makeText(MainActivity.this,"拍照成功!",Toast.LENGTH_SHORT).show();
                    }
                    catch (FileNotFoundException e)
                    {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if(resultCode==RESULT_OK)
                {
                    //Android4.4以上系统
                    if(Build.VERSION.SDK_INT>=19)
                    {
                        handleImageOnKitKat(data);
                    }
                    //Android4.4以下系统
                    else
                    {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    //Android4.4以上系统处理图片
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data)
    {
        String imagePath=null;
        //文件路径
        Uri uri=data.getData();
        //根据文件类型进行相应处理
        //如果是document类型的Uri,则通过document id处理
        if(DocumentsContract.isDocumentUri(this,uri))
        {
            //类型document
            String docId=DocumentsContract.getDocumentId(uri);
            if(uri.getAuthority().equals("com.android.providers.media.documents"))
            {
                //解析出数字格式的id
                // (将docId用:分隔，获取分割后数组第一个下标内容)
                String id=docId.split(":")[1];
                String selection=MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }
            else if(uri.getAuthority().equals("com.android.providers.downloada.documents"))
            {
                Uri contentUri= ContentUris.withAppendedId(Uri.parse("content://downloads/public _downloads"),Long.valueOf(docId));
                imagePath=getImagePath(contentUri,null);
            }

        }
        //如果是content类型的Uri,则使用普通方式处理
        else if(uri.getScheme().equalsIgnoreCase("content"))
        {
            imagePath=getImagePath(uri,null);
        }
        //如果是file类型的Uri,直接获取图片路径
        else if(uri.getScheme().equalsIgnoreCase("file"))
        {
            imagePath=uri.getPath();
        }
        //根据图片路径显示图片
        displayImage(imagePath);
    }

    //Android4.4以下系统处理图片
    private void handleImageBeforeKitKat(Intent data)
    {
        Uri uri=data.getData();
        String imagePath=getImagePath(uri,null);
        displayImage(imagePath);
    }

    //内容提供器获取图片路径
    private String getImagePath(Uri uri,String selection)
    {
        String path=null;
        //通过uri和selection获取真实的图片路径
        Cursor cursor=getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null)
        {
            if(cursor.moveToFirst())
            {
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    //根据图片路径显示图片
    private void displayImage(String imagePath)
    {
        if(imagePath!=null)
        {
            Bitmap bitmap=BitmapFactory.decodeFile(imagePath);
            civNav.setImageBitmap(bitmap);
        }
        else
            Toast.makeText(MainActivity.this,"获取头像失败！",Toast.LENGTH_SHORT).show();
    }
}
