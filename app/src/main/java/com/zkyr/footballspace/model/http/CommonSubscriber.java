package com.zkyr.footballspace.model.http;

import android.text.TextUtils;

import com.zkyr.footballspace.base.BaseView;
import com.zkyr.footballspace.util.DisplayUtil;

import java.net.ConnectException;

import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.HttpException;

/**
 * Created by codeest on 2017/2/23.
 */

public abstract class CommonSubscriber<T> extends ResourceSubscriber<T> {
    private BaseView mView;
    private String mErrorMsg;
    private boolean isShowErrorState = true;

    private boolean isShowLoadingDialog = true;

    protected CommonSubscriber(BaseView view) {
        this.mView = view;
    }

    protected CommonSubscriber(BaseView view, boolean isShowLoadingDialog) {
        this.mView = view;
        this.isShowLoadingDialog = isShowLoadingDialog;
    }

    protected CommonSubscriber(BaseView view, String errorMsg) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
    }

    protected CommonSubscriber(BaseView view, String errorMsg, boolean isShowErrorState) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
        this.isShowErrorState = isShowErrorState;
    }

    @Override
    public void onComplete() {
        DisplayUtil.hideLoading(mView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isShowLoadingDialog)
            DisplayUtil.displayLoading(mView);
    }

    @Override
    public void onError(Throwable e) {
        if (mView == null) {
            return;
        }
        DisplayUtil.hideLoading(mView);
        if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
            mView.showErrorMsg(mErrorMsg);
        } else if (e instanceof ApiException) {
            mView.showErrorMsg(((ApiException) e).getMsg());
        } else if (e instanceof HttpException||e instanceof ConnectException) {
            mView.showErrorMsg("服务器异常ヽ(≧Д≦)ノ");
        } else {
            mView.showErrorMsg("未知错误ヽ(≧Д≦)ノ");
//            LogUtil.d(e.toString());
        }
        if (isShowErrorState) {
            mView.showError();
        }
    }
}
