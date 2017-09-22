package com.songchao.mybilibili.fragment;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.songchao.mybilibili.R;
import com.songchao.mybilibili.adapter.DiaryAdapter;
import com.songchao.mybilibili.db.MySaveDatabaseHelper;
import com.songchao.mybilibili.model.DiaryBean;
import com.songchao.mybilibili.util.GetDate;
import com.songchao.mybilibili.util.StartUpdateDiaryEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class FouthFragment extends Fragment {
    //左上角橘黄色的圆圈
    @Bind(R.id.main_iv_circle)
    ImageView mMainIvCircle;
    //时间的textview
    @Bind(R.id.main_tv_date)
    TextView mMainTvDate;
    //今天你什么都没写的textview
    @Bind(R.id.main_tv_content)
    TextView mMainTvContent;
    //今天你什么都没写的LinearLayout
    @Bind(R.id.item_ll_control)
    LinearLayout mItemLlControl;
    //展示日记内容的recyclerview
    @Bind(R.id.main_rv_show_diary)
    RecyclerView mMainRvShowDiary;
    //整个的RelativeLayout
    @Bind(R.id.main_rl_main)
    RelativeLayout mMainRlMain;
    //包裹圆圈日期什么都没写的LinearLayout
    @Bind(R.id.item_first)
    LinearLayout mItemFirst;
    //次外层的LinearLayout
    @Bind(R.id.main_ll_main)
    LinearLayout mMainLlMain;
    private List<DiaryBean> mDiaryBeanList;
    private MySaveDatabaseHelper mHelper;


   public FouthFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstancState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_fouth,container,false);
        ButterKnife.bind(this,view);
        //EventBus第二步注册事件，这块不能用getActivity
        EventBus.getDefault().register(this);
        mHelper = new MySaveDatabaseHelper(getActivity(), "QiuShi.db", null, 5);
        getDiaryBeanList();
        initTitle();
        return view;
    }
    private void initTitle() {
        mMainTvDate.setText("今天，" + GetDate.getDate());
        mMainRvShowDiary.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMainRvShowDiary.setAdapter(new DiaryAdapter(getActivity(), mDiaryBeanList));
    }

    private List<DiaryBean> getDiaryBeanList() {

        mDiaryBeanList = new ArrayList<>();
        List<DiaryBean> diaryList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = mHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("Diary", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String dateSystem = GetDate.getDate().toString();
                if (date.equals(dateSystem)) {
                    mMainLlMain.removeView(mItemFirst);
                    break;
                }
            } while (cursor.moveToNext());
        }


        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String tag = cursor.getString(cursor.getColumnIndex("tag"));
                mDiaryBeanList.add(new DiaryBean(date, title, content, tag));
            } while (cursor.moveToNext());
        }
        cursor.close();

        for (int i = mDiaryBeanList.size() - 1; i >= 0; i--) {
            diaryList.add(mDiaryBeanList.get(i));
        }

        mDiaryBeanList = diaryList;
        return mDiaryBeanList;
    }

    //EventBus的第四个步骤处理事件
    @Subscribe
    public void StartUpdateDiaryEvent(StartUpdateDiaryEvent event) {
        DiaryBean diaryBean = event.getDiaryBean();
        String title = diaryBean.getTitle();
        String content = diaryBean.getContent();
        String tag = diaryBean.getTag();
        UpdateDiaryActivity.startActivity(getActivity(), title, content, tag);
    }

    @Override
    public void onResume() {
        super.onResume();
        getDiaryBeanList();
        initTitle();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //第五步取消注册
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.main_fab_enter_edit)
    public void onClick() {
        AddDiaryActivity.startActivity(getActivity());
    }
}
