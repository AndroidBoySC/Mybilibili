package com.songchao.mybilibili.activity;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    //取数据时不用editor
    //private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
        initData();
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
                    case R.id.nav_shaohou:
                        Intent intent3 = new Intent(MainActivity.this,ShaohouActivity.class);
                        startActivity(intent3);
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
                    default:
                        break;
                }
                return true;
            }
        });

    }
    public void Login(View view){
//        if(view != null){
//            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
//            startActivity(intent);
//        }

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

    @Override
    public void onBackPressed() {
        if(new Date().getTime() - lastPressTime <2000){
            finish();
        }else {
            lastPressTime = new Date().getTime();
            Toast.makeText(this,"再按一次返回键退出",Toast.LENGTH_SHORT).show();
        }
    }

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
