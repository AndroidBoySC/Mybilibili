package com.songchao.mybilibili.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.songchao.mybilibili.R;
import com.songchao.mybilibili.view.LuckyPanView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZhuiFanFragment extends Fragment {
    private LuckyPanView mLuckyPanView;
    private ImageView mStartBtn;


    public ZhuiFanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_zhui_fan,container,false);
        mLuckyPanView = (LuckyPanView) view.findViewById(R.id.id_luckypan);
        mStartBtn = (ImageView) view.findViewById(R.id.id_start_btn);
        mStartBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!mLuckyPanView.isStart())
                {
                    mStartBtn.setImageResource(R.drawable.stop);
                    mLuckyPanView.luckyStart(1);
                } else
                {
                    if (!mLuckyPanView.isShouldEnd())

                    {
                        mStartBtn.setImageResource(R.drawable.start);
                        mLuckyPanView.luckyEnd();
                    }
                }
            }
        });
        return view;
    }

}
