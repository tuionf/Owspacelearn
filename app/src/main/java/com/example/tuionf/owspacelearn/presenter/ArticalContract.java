package com.example.tuionf.owspacelearn.presenter;

import com.example.tuionf.owspacelearn.model.entity.Item;

import java.util.List;

/**
 * @author tuionf
 * @date 2018/1/3
 * @email 596019286@qq.com
 * @explain
 */

public interface ArticalContract {
    interface Presenter{
        void getListByPage(int page, int model, String pageId,String deviceId,String createTime);
    }
    interface View{
        void showLoading();
        void dismissLoading();
        void showNoData();
        void showNoMore();
        void updateListUI(List<Item> itemList);
        void showOnFailure();
    }
}
