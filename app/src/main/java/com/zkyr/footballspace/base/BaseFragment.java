package com.zkyr.footballspace.base;


import android.support.v4.app.Fragment;

import com.zkyr.footballspace.util.ToastUtil;


/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment<T extends BasePresenter> extends SimpleFragment implements BaseView {

    protected T mPresenter;

    protected abstract T getmPresenter();

    @Override
    protected void initPresenter() {
        super.initPresenter();
        getmPresenter();
        if(mPresenter!=null)
            mPresenter.attachView(this);
    }

    @Override
    public void onDestroyView() {
        if(mPresenter!=null)
            mPresenter.detachView();
        super.onDestroyView();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtil.shortShow(msg);
    }

    @Override
    public void showError() {

    }

    @Override
    public void hideProgress() {

    }
}
