package com.zkyr.footballspace.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.zkyr.footballspace.R;
import com.zkyr.footballspace.app.MyApplication;
import com.zkyr.footballspace.model.event.DummyEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 无Mvp的Activity的基类
 */
public abstract class SimpleActivity extends AppCompatActivity {

    protected Activity mActivity;
    protected Unbinder mUnBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mUnBinder = ButterKnife.bind(this);
        mActivity = this;
        MyApplication.getInstance().addActivity(mActivity);
        EventBus.getDefault().register(mActivity);
        onViewCreated();
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(mActivity);
        MyApplication.instance.removeActivity(mActivity);
        mUnBinder.unbind();
    }


    /**
     * 跳转到某个Activity
     */
    protected void gotoActivity(Context mContext, Class<?> toActivityClass) {
        gotoActivity(mContext, toActivityClass, null);
    }

    /**
     * 跳转到某个Activity
     */
    protected void gotoActivity(Context mContext, Class<?> toActivityClass, Bundle bundle) {
        Intent intent = new Intent(mContext, toActivityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        mContext.startActivity(intent);
        if (mContext instanceof Activity) {
            overridePendingTransition(R.anim.push_right_in, R.anim.not_exit_push_left_out);
        }
    }

    /**
     * 退出Activity
     */
    protected void backActivity() {
        finish();
        overridePendingTransition(R.anim.not_exit_push_left_in, R.anim.push_right_out);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backActivity();
        }
        return false;
    }

    /**
     * 初始化子类布局
     */
    protected abstract int getLayout();

    /**
     * 初始化子类View
     */
    protected abstract void initView();

    /**
     * 初始化子类一些数据
     */
    protected abstract void initData();

    /**
     * 该方法不执行，只是让Event编译通过
     */
    @Subscribe
    public void dummy(DummyEvent event) {
    }


    /**
     * 用于绑定Presenter
     */
    protected void onViewCreated() {

    }


    /**
     * 点击空白区域是软键盘消失
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
//        EventBus.getDefault().post(new EditTextFocusChangeEvent(false));
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
