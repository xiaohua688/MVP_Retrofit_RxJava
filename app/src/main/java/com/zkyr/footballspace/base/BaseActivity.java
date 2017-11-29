package com.zkyr.footballspace.base;


import com.zkyr.footballspace.util.ToastUtil;

public abstract class BaseActivity<T extends BasePresenter> extends SimpleActivity implements BaseView {


    protected  T mPresenter;

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        getmPresenter();
        if (mPresenter != null)
            mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        if(mPresenter!=null)
            mPresenter.detachView();
        super.onDestroy();
    }

    protected abstract T getmPresenter();


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtil.shortShow(msg);
    }

    @Override
    public void showEmpty() {

    }
}
