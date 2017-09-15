package com.songchao.mybilibili.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.songchao.mybilibili.R;
import com.songchao.mybilibili.db.MySaveDatabaseHelper;
import com.songchao.mybilibili.util.GetDate;
import com.songchao.mybilibili.util.RefreshViewEvent;
import com.songchao.mybilibili.view.LinedEditText;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.trity.floatingactionbutton.FloatingActionButton;
import cc.trity.floatingactionbutton.FloatingActionsMenu;

public class UpdateDiaryActivity extends AppCompatActivity {

    @Bind(R.id.update_diary_tv_date)
    TextView mUpdateDiaryTvDate;
    @Bind(R.id.update_diary_et_title)
    EditText mUpdateDiaryEtTitle;
    @Bind(R.id.update_diary_et_content)
    LinedEditText mUpdateDiaryEtContent;
    @Bind(R.id.update_diary_fab_back)
    FloatingActionButton mUpdateDiaryFabBack;
    @Bind(R.id.update_diary_fab_add)
    FloatingActionButton mUpdateDiaryFabAdd;
    @Bind(R.id.update_diary_fab_delete)
    FloatingActionButton mUpdateDiaryFabDelete;
    @Bind(R.id.right_labels)
    FloatingActionsMenu mRightLabels;
    @Bind(R.id.update_diary_tv_tag)
    TextView mTvTag;
    @Bind(R.id.back_title)
    ImageView mIvDraw;
    private MySaveDatabaseHelper mHelper;

    public static void startActivity(Context context, String title, String content, String tag) {
        Intent intent = new Intent(context, UpdateDiaryActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        intent.putExtra("tag", tag);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_diary);
        TextView textView = (TextView) findViewById(R.id.tv_title);
        textView.setText("修改日记");
        ButterKnife.bind(this);
        mHelper = new MySaveDatabaseHelper(this, "QiuShi.db", null, 4);
        Intent intent = getIntent();
        mUpdateDiaryTvDate.setText("今天，" + GetDate.getDate());
        mUpdateDiaryEtTitle.setText(intent.getStringExtra("title"));
        mUpdateDiaryEtContent.setText(intent.getStringExtra("content"));
        mTvTag.setText(intent.getStringExtra("tag"));

    }

    @OnClick({R.id.back_title, R.id.update_diary_fab_back, R.id.update_diary_fab_add, R.id.update_diary_fab_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_title:
                finish();
            case R.id.update_diary_fab_back:
                showTips();
                break;
            case R.id.update_diary_fab_add:
                SQLiteDatabase dbUpdate = mHelper.getWritableDatabase();
                ContentValues valuesUpdate = new ContentValues();
                String title = mUpdateDiaryEtTitle.getText().toString();
                String content = mUpdateDiaryEtContent.getText().toString();
                valuesUpdate.put("title", title);
                valuesUpdate.put("content", content);
                dbUpdate.update("Diary", valuesUpdate, "title = ?", new String[]{title});
                dbUpdate.update("Diary", valuesUpdate, "content = ?", new String[]{content});
                finish();
                break;
            case R.id.update_diary_fab_delete:
                finish();
                break;
        }
    }
    private void showTips() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("确定要删除该日记吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String tag = mTvTag.getText().toString();
                SQLiteDatabase dbDelete = mHelper.getWritableDatabase();
                dbDelete.delete("Diary", "tag = ?", new String[]{tag});
                finish();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
        //EventBus第三步发送事件
        EventBus.getDefault().post(new RefreshViewEvent());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
