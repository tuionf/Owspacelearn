package com.example.tuionf.owspacelearn.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tuionf.owspacelearn.R;
import com.example.tuionf.owspacelearn.model.entity.Event;
import com.example.tuionf.owspacelearn.util.RxBus;
import com.example.tuionf.owspacelearn.view.activity.ArtActivity;
import com.example.tuionf.owspacelearn.view.activity.DailyActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author tuionf
 * @date 2017/12/25
 * @email 596019286@qq.com
 * @explain
 */

public class LeftMenuFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_left_menu,container,false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.calendar_tv,R.id.right_slide_close, R.id.search,R.id.home_page_tv,R.id.words_tv,R.id.video_tv,R.id.voice_tv})
    public void onClick(View view){
        Intent intent;
        switch(view.getId()){
            case R.id.right_slide_close:
                RxBus.getInstance().postEvent(new Event(1000,"closeMenu"));
                break;
            case R.id.search:
                Toast.makeText(getContext(), "search", Toast.LENGTH_SHORT).show();
                break;
            case R.id.home_page_tv:
                RxBus.getInstance().postEvent(new Event(1000,"closeMenu"));
                break;
            case R.id.words_tv:
                intent = new Intent(getActivity(), ArtActivity.class);
                intent.putExtra("mode",1);
                intent.putExtra("title","文  字");
                startActivity(intent);
                break;
            case R.id.voice_tv:
                intent = new Intent(getActivity(), ArtActivity.class);
                intent.putExtra("mode",3);
                intent.putExtra("title","声  音");
                startActivity(intent);
                break;
            case R.id.video_tv:
                intent = new Intent(getActivity(), ArtActivity.class);
                intent.putExtra("mode",2);
                intent.putExtra("title","影  像");
                startActivity(intent);
                break;
            case R.id.calendar_tv:
                intent = new Intent(getActivity(), DailyActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }
}
