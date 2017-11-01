package com.songchao.mybilibili.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.songchao.mybilibili.R;
import com.songchao.mybilibili.fragment.DongTaiFragment;
import com.songchao.mybilibili.fragment.FirstFragment;
import com.songchao.mybilibili.fragment.SecondFragment;
import com.songchao.mybilibili.util.BottomNavigationViewHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private long lastPressTime = 0;
    //ActionBar mActionBar;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    public TabLayout mTabLayout;
    private NavigationView mNavigationView;
    private CircleImageView mCircleImageView;
    private TextView textViewLogin,textViewShare;
    private IntentFilter mFilter;
    private MainActivity.NotifyLoginUIReceiver mNotifyLoginUIReceiver;
    private SharedPreferences mPreferences;
    private Boolean isLogin;
    private Dialog mCameraDialog;
    private BottomNavigationView bottom_main_navigation;
    private List<Fragment> fragments;
    //头像文件
    private static final String IMAGE_FILE_NAME = "head_image.jpg";
    //请求识别码
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    //裁剪后的图片的宽x和高y
    private static int output_X = 100;
    private static int output_Y = 100;
    //取数据时不用editor
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //可能会解决surfaceview在fragment切换时闪屏问题。这行代码确实能解决！！！
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        //有动画效果
//        Explode explode = new Explode();
//        explode.setDuration(500);
//        getWindow().setExitTransition(explode);
//        getWindow().setEnterTransition(explode);
        fragments = new ArrayList<>();
        FragmentManager manager = getSupportFragmentManager();
        if(savedInstanceState == null){
            Fragment fragment = new FirstFragment();
            fragments.add(fragment);
            fragment = new SecondFragment();
            fragments.add(fragment);
            fragment = new DongTaiFragment();
            fragments.add(fragment);
            //fragment = new FouthFragment();
            //fragments.add(fragment);
            FragmentTransaction transaction = manager.beginTransaction();
            int index = 0;
            for (Fragment f : fragments){
                transaction.add(R.id.container,f,"tag"+index);
                transaction.hide(f);
                index++;
            }
            transaction.show(fragments.get(0));
            transaction.commit();
        }else {
            for (int i = 0; i < 4; i++) {
                String tag = "tag"+i;
                Fragment f = manager.findFragmentByTag(tag);
                if(f != null){
                    fragments.add(f);
                }
            }
        }
        initView();
        initData();

        String name = mPreferences.getString("userName", "0");
        String passWord = mPreferences.getString("passWord", "0");
        isLogin = mPreferences.getBoolean("isLogin", false);
        Log.d("Photo","isLogin:"+ isLogin);
        //判断是否登录状态已经登录了再次进入程序依旧会显示已登录未登录显示点击用户头像登录
        if(isLogin == true){
            textViewLogin.setText("已登录");
        }else {
            textViewLogin.setText("点击头像登录");
        }
        //隐藏ActionbarLo
        //mActionBar = getSupportActionBar();
        //mActionBar.hide();
        textViewShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showShare();
            }
        });
    }

    /**
     * 底部导航栏切换
     * @param index
     */
    private void switchFragment(int index) {
        if (index >= 0 && index < fragments.size()) {
            int size = fragments.size();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            for (int i = 0; i < size; i++) {
                Fragment f = fragments.get(i);
                if (i == index) {
                    // 显示
                    transaction.show(f);
                } else {
                    // 隐藏
                    transaction.hide(f);
                }
            }
            transaction.commit();

        }
    }

    /**
     * 三方分享
     */
    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.app_name));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }

    private void initData() {
        //以下被注释掉的代码是旧版UI的实现代码
//        List<Fragment> fragments = new ArrayList<>();
//        fragments.add(new ZhiBoFragment());
//        fragments.add(new TuiJianFragment());
//        fragments.add(new ZhuiFanFragment());
//        fragments.add(new FenQuFragment());
//        fragments.add(new DongTaiFragment());
//        MyFragmentPageAdapter myFragmentPageAdapter = new MyFragmentPageAdapter(getSupportFragmentManager(),fragments);
//        mViewPager.setAdapter(myFragmentPageAdapter);
        //mTabLayout.setOnTabSelectedListener(this);
//        TabLayout.Tab tab0 = mTabLayout.newTab();
//        tab0.setText("直播");
//        mTabLayout.addTab(tab0);
//        TabLayout.Tab tab1 = mTabLayout.newTab();
//        tab1.setText("推荐");
//        mTabLayout.addTab(tab1);
//        TabLayout.Tab tab2 = mTabLayout.newTab();
//        tab2.setText("追番");
//        mTabLayout.addTab(tab2);
//        TabLayout.Tab tab3 = mTabLayout.newTab();
//        tab3.setText("分区");
//        mTabLayout.addTab(tab3);
//        TabLayout.Tab tab4 = mTabLayout.newTab();
//        tab4.setText("动态");
//        mTabLayout.addTab(tab4);
        //TabLayout.TabLayoutOnPageChangeListener listener = new TabLayout.TabLayoutOnPageChangeListener(mTabLayout);
        //mViewPager.addOnPageChangeListener(listener);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_shouye:
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_history:
                        Intent intent = new Intent(MainActivity.this,HistoryActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_huancun:
                        Intent intent1 = new Intent(MainActivity.this,HuancunActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_shoucang:
                        Intent intent2 = new Intent(MainActivity.this,ShoucangActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_huiyuan:
                        Intent intent4 = new Intent(MainActivity.this,HuiyuanActivity.class);
                        startActivity(intent4);
                        break;
                    case R.id.nav_setting:
                        Intent intent5 = new Intent(MainActivity.this,SettingActivity.class);
                        startActivity(intent5);
                        break;
                    case R.id.nav_theme:
                        Intent intent6 = new Intent(MainActivity.this,ThemeActivity.class);
                        startActivity(intent6);
                        break;
                    case R.id.nav_night:
                        Intent intent7 = new Intent(MainActivity.this,NightActivity.class);
                        startActivity(intent7);
                        break;
                    case R.id.out_login:
//                        Intent intent3 = new Intent(MainActivity.this,OutLoginActivity.class);
//                        startActivity(intent3);
                        mDrawerLayout.closeDrawers();
                        //在菜单布局中使用snackbar第一个view参数填菜单的父容器view即可
                        Snackbar.make(mNavigationView,"确定不是手滑吗",Snackbar.LENGTH_LONG).setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //Toast.makeText(MainActivity.this,"已退出",Toast.LENGTH_SHORT).show();
                                Intent intent3 = new Intent("com.songchao.mybilibili.notifiout");
                                sendBroadcast(intent3);
                                isLogin = false;
                                //要重新再存一次，否则取不到
                                mEditor.putBoolean("isLogin",isLogin);
                                //必须提交，忘提交不会有效果
                                mEditor.apply();
                            }
                        }).show();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

    }

    /**
     * 点击头像后的操作
     * @param view
     */
    public void Login(View view){
        //通过存储在本地的用户信息及登录状态判断所要打开的页面
//        String name = mPreferences.getString("userName", "0");
//        String passWord = mPreferences.getString("passWord", "0");
        //这块必须要再取一次，否则由未登录转为登录时isLogin值还是false
        isLogin = mPreferences.getBoolean("isLogin", false);
       if(isLogin){
            //打个吐司测试一下，实际上要做选取图片或拍照的操作
            //Toast.makeText(MainActivity.this,"你已经登陆了",Toast.LENGTH_SHORT).show();
            //textViewLogin.setText("已登录");这句写在这里是有问题的，应该写在onCreate方法中
           mCameraDialog = new Dialog(MainActivity.this,R.style.my_dialog);
           LinearLayout root = (LinearLayout) LayoutInflater.from(MainActivity.this).inflate(R.layout.camera_control,null);
           root.findViewById(R.id.btn_camera).setOnClickListener(btnListener);
           root.findViewById(R.id.btn_photo).setOnClickListener(btnListener);
           root.findViewById(R.id.btn_cancel).setOnClickListener(btnListener);
           //将dialog布局通过setcontentview填充
           mCameraDialog.setContentView(root);
           Window dialogWindow = mCameraDialog.getWindow();
           dialogWindow.setGravity(Gravity.BOTTOM);
           //有个滑出的动画，对高度进行设置，不让他全屏
           //dialogWindow.setWindowAnimations(R.style.dialogstyle);
           WindowManager.LayoutParams lp = dialogWindow.getAttributes();//获取对话框当前的参数值
           lp.x = 0; // 新位置X坐标
           lp.y = -20; // 新位置Y坐标
           lp.width =(int)getResources().getDisplayMetrics().widthPixels;// 宽度  
//      lp.height = WindowManager.LayoutParams.WRAP_CONTENT;//高度
//     lp.alpha = 9f; //透明度
           root.measure(0,0);
           lp.height = root.getMeasuredHeight();
           lp.alpha = 9f; // 透明度  
           dialogWindow.setAttributes(lp);
           mCameraDialog.show();
        }else {
           Intent intent = new Intent(MainActivity.this,LoginActivity.class);
           startActivity(intent);
       }
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        //这里注意设置标题要在setsupportactionbar方法前调用才会有效
        mToolbar.setTitle(" ");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.home3);
        mToolbar.setLogo(R.mipmap.toolicon2);
        mToolbar.setOnMenuItemClickListener(mOnMenuItemClickListener);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        //以下两行代码是如何获取navigationView中子view的方式
        View headerView = mNavigationView.getHeaderView(0);
        textViewLogin = (TextView) headerView.findViewById(R.id.tv_login);
        textViewShare = (TextView) findViewById(R.id.share_main);
        //mViewPager = (ViewPager) findViewById(R.id.vp_main);
        mTabLayout = (TabLayout) findViewById(R.id.tab_main);
        bottom_main_navigation = (BottomNavigationView) findViewById(R.id.bottom_main_navigation);
        //该方法调用helper类去掉大于3时的动画
        BottomNavigationViewHelper.disableShiftMode(bottom_main_navigation);
        MenuItem item = bottom_main_navigation.getMenu().getItem(0);
        bottom_main_navigation.setOnNavigationItemSelectedListener(this);
        onNavigationItemSelected(item);//默认选中第一个
        mCircleImageView = (CircleImageView) headerView.findViewById(R.id.icon_image);
        mFilter = new IntentFilter();
        mFilter.addAction("com.songchao.mybilibili.notifilogin");
        mFilter.addAction("com.songchao.mybilibili.notifiout");
        mNotifyLoginUIReceiver = new NotifyLoginUIReceiver();
        registerReceiver(mNotifyLoginUIReceiver,mFilter);
        //应该把SharedPreferences封装为一个工具类，这样会减少很多重复代码
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();
    }

    /**
     * 创建关联菜单
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tool,menu);
        return true;
    }

    /**
     * toolbar上item点击事件的逻辑处理方法
     */
    private Toolbar.OnMenuItemClickListener mOnMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.action_game:
                    Intent intent = new Intent(MainActivity.this,QRCodeActivity.class);
                    startActivity(intent);
                    break;
                case R.id.action_download:
                    //Intent intent1 = new Intent(MainActivity.this,RecordActivity.class);
                    //startActivity(intent1);
                    break;
                case R.id.action_search:
                    Intent intent2 = new Intent(MainActivity.this,SearchActivity.class);
                    startActivity(intent2);
                    default:
                        break;
            }
            return true;
        }
    };

    /**
     * 选择头像时底部的对话框选择点击事件
     */
    private View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                //通过shape使button圆角化
                case R.id.btn_camera:
                    choseHeadImageFromCameraCapture();
                    mCameraDialog.dismiss();
                    break;
                case R.id.btn_photo:
                    choseHeadImageFromGallery();
                    mCameraDialog.dismiss();
                    break;
                case R.id.btn_cancel:
                    if(mCameraDialog != null){
                        mCameraDialog.dismiss();
                    }
                    break;
                default:
                    break;
            }

        }
    };

    /**
     * 从手机相机拍摄图片设置用户头像
     */
    private void choseHeadImageFromCameraCapture() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断存储卡是否可用，存储照片文件
        if(hasSdcard()){
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(Environment.getExternalStorageDirectory(),IMAGE_FILE_NAME)));
            }
        startActivityForResult(intentFromCapture,CODE_CAMERA_REQUEST);
    }

    /**
     * 从手机相册选择图片设置用户头像
     */
    private void choseHeadImageFromGallery() {
        Intent intentFromGallery = new Intent();
        //设置文件类型
        intentFromGallery.setType("image/*");
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentFromGallery,CODE_GALLERY_REQUEST);
    }

    /**
     * 处理拍照后及相册选中的图片
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //用户没有进行有效的设置操作，返回
        if(resultCode == RESULT_CANCELED){
            Toast.makeText(getApplication(),"取消",Toast.LENGTH_LONG).show();
            return;
            }
        switch(requestCode){
            case CODE_GALLERY_REQUEST:
            cropRawPhoto(intent.getData());
            break;
            case CODE_CAMERA_REQUEST:
            if(hasSdcard()){
                File tempFile = new File(
                        Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME);
                cropRawPhoto(Uri.fromFile(tempFile));
                }else{
                Toast.makeText(getApplication(),"没有SDCard!",Toast.LENGTH_LONG).show();
                }
            break;
            case CODE_RESULT_REQUEST:
                Log.d("Photo","CODE_RESULT_REQUEST....");
            if(intent != null){
                setImageToHeadView(intent);
                }
            break;
            }
    }

    /**  
      * 裁剪原始的图片
      */
    public void cropRawPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");
        //设置裁剪
        intent.putExtra("crop","true");
        //aspectX,aspectY:宽高的比例
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        //outputX,outputY:裁剪图片宽高
        intent.putExtra("outputX",output_X);
        intent.putExtra("outputY",output_Y);
        intent.putExtra("return-data",true);
        startActivityForResult(intent,CODE_RESULT_REQUEST);
        Log.d("Photo","cropRawPhoto....");
        }
    /**  
     *提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    private void setImageToHeadView(Intent intent){

        Bundle extras = intent.getExtras();
        if(extras != null){
            Bitmap photo = extras.getParcelable("data");
            Log.d("Photo","CODE_RESULT_REQUEST...." + photo);
            mCircleImageView.setImageBitmap(photo);
            }
        }
    /** 
     *检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if(state.equals(Environment.MEDIA_MOUNTED)){
            //有存储的SDCard
            return true;
            }else{
            return false;
            }
        }

    /**
     * 侧滑菜单导航按钮点击事件
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 再按一次返回键退出程序
     */
    @Override
    public void onBackPressed() {
        if(new Date().getTime() - lastPressTime <2000){
            finish();
        }else {
            lastPressTime = new Date().getTime();
            Toast.makeText(this,"再按一次返回键退出",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 以下3个方法为tablayout与viewpager联动的回调方法
     * @param
     */
//    @Override
//    public void onTabSelected(TabLayout.Tab tab) {
//        //int position = tab.getPosition();
//        //mViewPager.setCurrentItem(position);
//    }
//
//    @Override
//    public void onTabUnselected(TabLayout.Tab tab) {
//
//    }
//
//    @Override
//    public void onTabReselected(TabLayout.Tab tab) {
//
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int index =0;
        switch (item.getItemId()){
            case R.id.menu_main_item_beauty:
                //显示1Fragement
                mTabLayout.setVisibility(View.VISIBLE);
                index = 0;
                break;
            case R.id.menu_main_item_android:
                //显示2Fragement
                mTabLayout.setVisibility(View.GONE);
                index = 1;
                break;
            case R.id.menu_main_item_three:
                mTabLayout.setVisibility(View.GONE);
                index = 2;
                break;
            case R.id.menu_main_item_message:
                mTabLayout.setVisibility(View.GONE);
                index = 3;
                break;
        }
        switchFragment(index);
        return true;
    }

    //广播接收器
    public class NotifyLoginUIReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("com.songchao.mybilibili.notifilogin")){
                textViewLogin.setText("已登录");
            }else if(action.equals("com.songchao.mybilibili.notifiout")){
                textViewLogin.setText("点击头像登录");
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mNotifyLoginUIReceiver);
    }
}
