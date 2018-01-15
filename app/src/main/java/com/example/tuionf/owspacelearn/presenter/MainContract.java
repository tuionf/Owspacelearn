package com.example.tuionf.owspacelearn.presenter;

import com.example.tuionf.owspacelearn.model.entity.Item;

import java.util.List;

/**
 * @author tuionf
 * @date 2017/12/26
 * @email 596019286@qq.com
 * @explain
 */

public interface MainContract {
    interface Presenter{
        void getListByPage(int page, int model, String pageId,String deviceId,String createTime);
        void getRecommend(String deviceId);
    }
    interface View{
        void showLoading();
        void dismissLoading();
        void showNoData();
        void showNoMore();
        void updateListUI(List<Item> itemList);
        void showOnFailure();
        void showLunar(String content);
    }
}
