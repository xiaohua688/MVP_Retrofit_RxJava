package com.zkyr.footballspace.base;

/**
 * Created by codeest on 2016/8/2.
 * View基类
 */
public interface BaseView {

    void showProgress();

    void hideProgress();

    void showError();

    void showErrorMsg(String msg);

    void showEmpty();
}
