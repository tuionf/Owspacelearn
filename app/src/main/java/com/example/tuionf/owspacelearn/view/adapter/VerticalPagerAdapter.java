package com.example.tuionf.owspacelearn.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.tuionf.owspacelearn.model.entity.Item;
import com.example.tuionf.owspacelearn.view.fragment.MainFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tuionf
 * @date 2017/12/26
 * @email 596019286@qq.com
 * @explain
 */

public class VerticalPagerAdapter extends FragmentStatePagerAdapter {

    private List<Item> dataList=new ArrayList<>();

    public VerticalPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return MainFragment.instance(dataList.get(position));
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    public void setDataList(List<Item> data){
        dataList.addAll(data);
        notifyDataSetChanged();
    }
    public String getLastItemId(){
        if (dataList.size()==0){
            return "0";
        }
        Item item = dataList.get(dataList.size()-1);
        return item.getId();
    }
    public String getLastItemCreateTime(){
        if (dataList.size()==0){
            return "0";
        }
        Item item = dataList.get(dataList.size()-1);
        return item.getCreate_time();
    }
}
