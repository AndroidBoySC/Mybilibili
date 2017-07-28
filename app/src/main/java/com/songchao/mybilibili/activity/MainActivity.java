package com.songchao.mybilibili.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.songchao.mybilibili.R;
import com.songchao.mybilibili.adapter.MyFragmentPageAdapter;
import com.songchao.mybilibili.adapter.RecyclerViewCardAdapter;
import com.songchao.mybilibili.fragment.DongTaiFragment;
import com.songchao.mybilibili.fragment.FenQuFragment;
import com.songchao.mybilibili.fragment.TuiJianFragment;
import com.songchao.mybilibili.fragment.ZhiBoFragment;
import com.songchao.mybilibili.fragment.ZhuiFanFragment;
import com.songchao.mybilibili.model.ImageCard;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{
    private long lastPressTime = 0;
    //ActionBar mActionBar;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mRecyclerView;
    //先模拟假数据做出效果，后期会替换真实数据接口
    private ImageCard[] mImageCards = {new ImageCard("one",R.mipmap.yasuo_01_cn),new ImageCard("two",R.mipmap.yasuo_04_cn),
    new ImageCard("three",R.mipmap.yasuo_05_cn),new ImageCard("four",R.mipmap.yasuo_06_cn)};
    private List<ImageCard> mImageCardList = new ArrayList<>();
    private RecyclerViewCardAdapter mAdapter;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        init();
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
    }

    private void init() {
        mImageCardList.clear();
        for (int i = 0; i < 30; i++) {
            Random random = new Random();
            int index = random.nextInt(mImageCards.length);
            mImageCardList.add(mImageCards[index]);
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
        mViewPager = (ViewPager) findViewById(R.id.vp_main);
        mTabLayout = (TabLayout) findViewById(R.id.tab_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_main);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerViewCardAdapter(this,mImageCardList);
        mRecyclerView.setAdapter(mAdapter);
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
}
