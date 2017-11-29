package com.zkyr.footballspace.base;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zkyr.footballspace.R;
import com.zkyr.footballspace.model.event.DummyEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class SimpleFragment extends Fragment {


    protected Context mContext;
    protected View mView;
    protected Unbinder mUnBinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView=inflater.inflate(getLayout(),null);
        EventBus.getDefault().register(this);
        return mView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder= ButterKnife.bind(this,view);
        initPresenter();
        initView();
        initData();
    }

    /**
     * 初始化Presenter
     */
    protected void initPresenter(){

    }

    /**
     * 跳转到某个Activity
     */
    protected void gotoActivity(Context mContext,  Class<?> toActivityClass) {
        gotoActivity(mContext,toActivityClass,null);
    }

    /**
     * 跳转到某个Activity
     */
    protected void gotoActivity(Context mContext,  Class<?> toActivityClass,  Bundle bundle) {
        Intent intent = new Intent(mContext, toActivityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        mContext.startActivity(intent);
        if(mContext instanceof Activity){
            ((Activity)mContext).overridePendingTransition(R.anim.push_right_in, R.anim.not_exit_push_left_out);
        }
    }

    /**
     * 反向传值跳转
     * @param mContext
     * @param intent
     * @param requestCode
     */
    protected void gotoActivityForResult(Context mContext, Intent intent,  int requestCode) {
        super.startActivityForResult(intent, requestCode);
        ((Activity)mContext).overridePendingTransition(R.anim.push_right_in, R.anim.not_exit_push_left_out);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    protected abstract int getLayout();

    protected abstract void initView();

    protected abstract void initData();

    /**
     * 该方法不执行，只是让Event编译通过
     */
    @Subscribe
    public void dummy(DummyEvent event) {
    }

}
