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
import com.songchao.mybilibili.view.LinedEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.trity.floatingactionbutton.FloatingActionButton;
import cc.trity.floatingactionbutton.FloatingActionsMenu;

public class AddDiaryActivity extends AppCompatActivity {
    //添加日记界面日期
    @Bind(R.id.add_diary_tv_date)
    TextView mAddDiaryTvDate;
    //日记标题的edittext
    @Bind(R.id.add_diary_et_title)
    EditText mAddDiaryEtTitle;
    //日记内容的自定义edittext
    @Bind(R.id.add_diary_et_content)
    LinedEditText mAddDiaryEtContent;
    //x的floatingbutton
    @Bind(R.id.add_diary_fab_back)
    FloatingActionButton mAddDiaryFabBack;
    //对勾的floatingbutton
    @Bind(R.id.add_diary_fab_add)
    FloatingActionButton mAddDiaryFabAdd;
    //整个的弹出floatingbutton
    @Bind(R.id.right_labels)
    FloatingActionsMenu mRightLabels;
    @Bind(R.id.back_title)
    ImageView back_title;
    private MySaveDatabaseHelper mHelper;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AddDiaryActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diary);
        TextView textView = (TextView) findViewById(R.id.tv_title);
        textView.setText("添加日记");
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mAddDiaryEtTitle.setText(intent.getStringExtra("title"));
        mAddDiaryTvDate.setText("今天，" + GetDate.getDate());
        mAddDiaryEtContent.setText(intent.getStringExtra("content"));
        mHelper = new MySaveDatabaseHelper(this, "QiuShi.db", null, 5);
    }


    @OnClick({R.id.back_title, R.id.add_diary_et_title, R.id.add_diary_et_content, R.id.add_diary_fab_back, R.id.add_diary_fab_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_title:
                backToDiaryFragment();
            case R.id.add_diary_et_title:
                break;
            case R.id.add_diary_et_content:
                break;
            case R.id.add_diary_fab_back:
                String date = GetDate.getDate().toString();
                String tag = String.valueOf(System.currentTimeMillis());
                String title = mAddDiaryEtTitle.getText().toString() + "";
                String content = mAddDiaryEtContent.getText().toString() + "";
                if (!title.equals("") || !content.equals("")) {
                    SQLiteDatabase db = mHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("date", date);
                    values.put("title", title);
                    values.put("content", content);
                    values.put("tag", tag);
                    db.insert("Diary", null, values);
                    values.clear();
                }
                finish();
                break;
            case R.id.add_diary_fab_add:
                backToDiaryFragment();
                break;
        }
    }

    private void backToDiaryFragment() {
        final String dateBack = GetDate.getDate().toString();
        final String titleBack = mAddDiaryEtTitle.getText().toString();
        final String contentBack = mAddDiaryEtContent.getText().toString();
        if (!titleBack.isEmpty() || !contentBack.isEmpty()) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("是否保存日记内容？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    SQLiteDatabase db = mHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("date", dateBack);
                    values.put("title", titleBack);
                    values.put("content", contentBack);
                    db.insert("Diary", null, values);
                    values.clear();
                    finish();
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).show();
        } else {
            finish();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
