package com.example.tuionf.owspacelearn.presenter;

import com.example.tuionf.owspacelearn.model.entity.DetailEntity;

/**
 * @author tuionf
 * @date 2017/12/27
 * @email 596019286@qq.com
 * @explain
 */

public interface DetailContract {
    interface Present{
        void getDetail(String itemId);
    }
    interface View{
        void showLoading();
        void dismissLoading();
        void updateListUI(DetailEntity detailEntity);
        void showOnFailure();
    }
}
