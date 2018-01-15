package com.example.tuionf.owspacelearn.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tuionf.owspacelearn.R;
import com.example.tuionf.owspacelearn.model.entity.Item;
import com.example.tuionf.owspacelearn.util.TimeUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author tuionf
 * @date 2018/1/5
 * @email 596019286@qq.com
 * @explain
 */

public class DailyItemFragment extends Fragment {

    @BindView(R.id.date_rl)
    RelativeLayout dateRl;
    @BindView(R.id.month_tv)
    TextView monthTv;
    @BindView(R.id.year_tv)
    TextView yearTv;
    @BindView(R.id.calendar_iv)
    ImageView calendarIv;

    public static Fragment getInstance(Item item){
        Fragment fragment = new DailyItemFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("item",item);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_daily,container,false);
        ButterKnife.bind(this,view);
        initData();
        return view;
    }

    private void initData() {
        Item item = getArguments().getParcelable("item");
        Glide.with(getActivity()).load(item.getThumbnail()).centerCrop().into(calendarIv);
        String[] arrayOfString = TimeUtil.getCalendarShowTime(item.getUpdate_time());
        if ((arrayOfString != null) && (arrayOfString.length == 3)){
            monthTv.setText(arrayOfString[1] + " , " + arrayOfString[2]);
            yearTv.setText(arrayOfString[0]);
        }
    }
}
