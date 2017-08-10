package com.songchao.mybilibili.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.songchao.mybilibili.adapter.MyFragmentPageAdapter;
import com.songchao.mybilibili.fragment.DongTaiFragment;
import com.songchao.mybilibili.fragment.FenQuFragment;
import com.songchao.mybilibili.fragment.TuiJianFragment;
import com.songchao.mybilibili.fragment.ZhiBoFragment;
import com.songchao.mybilibili.fragment.ZhuiFanFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{
    private long lastPressTime = 0;
    //ActionBar mActionBar;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private NavigationView mNavigationView;
    private CircleImageView mCircleImageView;
    private TextView textViewLogin;
    private IntentFilter mFilter;
    private MainActivity.NotifyLoginUIReceiver mNotifyLoginUIReceiver;
    private SharedPreferences mPreferences;
    private Boolean isLogin = false;
    private Dialog mCameraDialog;
    //取数据时不用editor
    //private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        String name = mPreferences.getString("userName", "0");
        String passWord = mPreferences.getString("passWord", "0");
        isLogin = mPreferences.getBoolean("isLogin", false);
        //判断是否登录状态已经登录了再次进入程序依旧会显示已登录
        if(isLogin){
            textViewLogin.setText("已登录");
        }
        //隐藏Actionbar
        //mActionBar = getSupportActionBar();
        //mActionBar.hide();
    }

    private void initData() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new ZhiBoFragment());
        fragments.add(new TuiJianFragment());
        fragments.add(new ZhuiFanFragment());
        fragments.add(new FenQuFragment());
        fragments.add(new DongTaiFragment());
        MyFragmentPageAdapter myFragmentPageAdapter = new MyFragmentPageAdapter(getSupportFragmentManager(),fragments);
        mViewPager.setAdapter(myFragmentPageAdapter);
        mTabLayout.setOnTabSelectedListener(this);
        TabLayout.Tab tab0 = mTabLayout.newTab();
        tab0.setText("直播");
        mTabLayout.addTab(tab0);
        TabLayout.Tab tab1 = mTabLayout.newTab();
        tab1.setText("推荐");
        mTabLayout.addTab(tab1);
        TabLayout.Tab tab2 = mTabLayout.newTab();
        tab2.setText("追番");
        mTabLayout.addTab(tab2);
        TabLayout.Tab tab3 = mTabLayout.newTab();
        tab3.setText("分区");
        mTabLayout.addTab(tab3);
        TabLayout.Tab tab4 = mTabLayout.newTab();
        tab4.setText("动态");
        mTabLayout.addTab(tab4);
        TabLayout.TabLayoutOnPageChangeListener listener = new TabLayout.TabLayoutOnPageChangeListener(mTabLayout);
        mViewPager.addOnPageChangeListener(listener);
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
                        Intent intent3 = new Intent(MainActivity.this,OutLoginActivity.class);
                        startActivity(intent3);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

    }
    public void Login(View view){
        //通过存储在本地的用户信息及登录状态判断所要打开的页面
        String name = mPreferences.getString("userName", "0");
        String passWord = mPreferences.getString("passWord", "0");
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
           dialogWindow.setWindowAnimations(R.style.dialogstyle);
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
        mViewPager = (ViewPager) findViewById(R.id.vp_main);
        mTabLayout = (TabLayout) findViewById(R.id.tab_main);
        mCircleImageView = (CircleImageView) findViewById(R.id.icon_image);
        mFilter = new IntentFilter();
        mFilter.addAction("com.songchao.mybilibili.notifilogin");
        mNotifyLoginUIReceiver = new NotifyLoginUIReceiver();
        registerReceiver(mNotifyLoginUIReceiver,mFilter);
        //应该把SharedPreferences封装为一个工具类，这样会减少很多重复代码
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

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
                    Intent intent = new Intent(MainActivity.this,GameActivity.class);
                    startActivity(intent);
                    break;
                case R.id.action_download:
                    Intent intent1 = new Intent(MainActivity.this,DownloadActivity.class);
                    startActivity(intent1);
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

                    break;
                case R.id.btn_photo:

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
     * @param tab
     */
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
    //广播接收器
    public class NotifyLoginUIReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            textViewLogin.setText("已登录");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mNotifyLoginUIReceiver);
    }
}
