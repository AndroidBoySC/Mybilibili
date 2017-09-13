package com.songchao.mybilibili.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.songchao.mybilibili.R;
import com.songchao.mybilibili.fragment.PlaybackDialogFragment;
import com.songchao.mybilibili.fragment.RecordAudioDialogFragment;
import com.songchao.mybilibili.model.RecordingItem;

public class RecordActivity extends AppCompatActivity {
    private TextView titleText;
    private ImageView backImageView;
    private Button startButton,playButton;

    /**
     * private DownloadService.DownloadBinder mDownloadBinder;
     private ServiceConnection mConnection = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
    -----------------------------------------------------------------------------------------------------
    第一种方式写的时候遇到的空指针解决后明白了所有的回调方法都是异步的，其实也可以写到外面，调整下绑定服务的代码位置即可
    1 //这块要写到这里，得等到service连接完才能开启下载，否则直接写在点击事件里mDownloadBinder会一直为空  1
    1 //因为所有的回调方法都是异步的                                                                      1
    ------------------------------------------------------------------------------------------------------
    Log.d("Photo","IBinder:" + iBinder);
    //向下转型
    mDownloadBinder = (DownloadService.DownloadBinder) iBinder;
    Intent value = getIntent();
    String url = value.getStringExtra("currenturl");
    if (mDownloadBinder == null){
    Log.d("Photo", "mDownloadBinder:="+mDownloadBinder);
    return;
    }
    mDownloadBinder.startDownload(url);
    Log.d("Photo", "url:"+url);
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }
    };
     *
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        titleText = (TextView) findViewById(R.id.tv_title);
        titleText.setText("录音");
        backImageView = (ImageView) findViewById(R.id.back_title);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        startButton = (Button) findViewById(R.id.recordbtn);
        playButton = (Button) findViewById(R.id.recorplaydbtn);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final RecordAudioDialogFragment fragment = RecordAudioDialogFragment.newInstance();
                fragment.show(getSupportFragmentManager(), RecordAudioDialogFragment.class.getSimpleName());
                fragment.setOnCancelListener(new RecordAudioDialogFragment.OnAudioCancelListener() {
                    @Override
                    public void onCancel() {
                        fragment.dismiss();
                    }
                });
            }
        });
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecordingItem recordingItem = new RecordingItem();
                SharedPreferences sharePreferences = getSharedPreferences("sp_name_audio", MODE_PRIVATE);
                final String filePath = sharePreferences.getString("audio_path", "");
                long elpased = sharePreferences.getLong("elpased", 0);
                recordingItem.setFilePath(filePath);
                recordingItem.setLength((int) elpased);
                PlaybackDialogFragment fragmentPlay = PlaybackDialogFragment.newInstance(recordingItem);
                fragmentPlay.show(getSupportFragmentManager(), PlaybackDialogFragment.class.getSimpleName());
            }
        });
    }

}
