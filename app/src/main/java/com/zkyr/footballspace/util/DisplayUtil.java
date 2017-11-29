package com.zkyr.footballspace.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zkyr.footballspace.R;
import com.zkyr.footballspace.app.MyApplication;


/**
 * Created by Administrator on 2017/5/24.
 */

public class DisplayUtil {

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param context
     * @param pxValue （DisplayMetrics类中属性density）
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param context
     * @param dipValue （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int getScreenWidth(){
        WindowManager wm = (WindowManager) MyApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    /**
     * 吐司  时间短
     *
     * @param context
     * @param message
     */
    public static void displayShortToast(Context context, String message) {
        if (context == null)
            return;
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 吐司  时间长
     *
     * @param context
     * @param message
     */
    public static void diaplayLongToast(Context context, String message) {
        if (context == null)
            return;
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 显示自定义吐司
     *
     * @param context
     * @param view    自定义View
     */
    public static void diaplayCustomeToast(Context context, View view) {
        if (context == null || view == null)
            return;
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM, 0, 0);//位置
        toast.setDuration(Toast.LENGTH_SHORT);//时间
        toast.setView(view);
        toast.show();
    }


    /**
     * 弹出框显示
     *
     * @param context   上下文
     * @param title     弹出框标题
     * @param message   弹出框内容
     * @param listeners 弹出框底部点击事件
     */
    public static void diaplayDialog(Context context, String title, String message, final View.OnClickListener... listeners) {
        if (context instanceof Activity) {
            Activity mActivity = (Activity) context;

            //获得XML中显示区域的根视图
            final FrameLayout parent = (FrameLayout) mActivity.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
            final ScaleAnimation scaleShow = new ScaleAnimation(0f, 1f, 0f, 1f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            scaleShow.setInterpolator(new OvershootInterpolator());
            scaleShow.setDuration(300);
            View child = parent.findViewById(R.id.ll_display);
            if (child != null)
                parent.removeView(child);

            //获得弹出框布局
            final LinearLayout ll_display = (LinearLayout) mActivity.getLayoutInflater().inflate(R.layout.display_view, null, false);
            parent.addView(ll_display);

            TextView tv_display_title = (TextView) ll_display.findViewById(R.id.tv_display_title);
            TextView tv_display_message = (TextView) ll_display.findViewById(R.id.tv_display_message);
            if (TextUtils.isEmpty(title))
                tv_display_title.setVisibility(View.GONE);

            tv_display_title.setText(title);
            tv_display_message.setText(message);

            TextView tv_cancel = (TextView) ll_display.findViewById(R.id.tv_cancel);
            TextView tv_ok = (TextView) ll_display.findViewById(R.id.tv_ok);
            tv_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listeners[0] != null)
                        listeners[0].onClick(view);
                    parent.removeView(ll_display);//将布局移除
                }
            });
            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listeners[1] != null)
                        listeners[1].onClick(view);
                    parent.removeView(ll_display);//将布局移除
                }
            });


            //在View布局完成后获得View的宽度和高度,布局完成后调用
            ll_display.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (ll_display.getWidth() > 0) {
                        ll_display.getChildAt(0).startAnimation(scaleShow);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            ll_display.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    }
                }
            });
        }
    }


    /**
     * @param context
     * @param title
     * @param message
     * @param cancel    //取消按钮显示
     * @param ok        //确定按钮显示
     * @param listeners
     */
    public static void diaplayDialog(Context context, String title, String message, String cancel, String ok, final View.OnClickListener... listeners) {
        if (context instanceof Activity) {
            Activity mActivity = (Activity) context;

            //获得XML中显示区域的根视图
            final FrameLayout parent = (FrameLayout) mActivity.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
            final ScaleAnimation scaleShow = new ScaleAnimation(0f, 1f, 0f, 1f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            scaleShow.setInterpolator(new OvershootInterpolator());
            scaleShow.setDuration(300);
            View child = parent.findViewById(R.id.ll_display);
            if (child != null)
                parent.removeView(child);

            //获得弹出框布局
            final LinearLayout ll_display = (LinearLayout) mActivity.getLayoutInflater().inflate(R.layout.display_view, null, false);
            parent.addView(ll_display);

            TextView tv_display_title = (TextView) ll_display.findViewById(R.id.tv_display_title);
            TextView tv_display_message = (TextView) ll_display.findViewById(R.id.tv_display_message);
            if (TextUtils.isEmpty(title))
                tv_display_title.setVisibility(View.GONE);

            tv_display_title.setText(title);
            tv_display_message.setText(message);

            TextView tv_cancel = (TextView) ll_display.findViewById(R.id.tv_cancel);
            TextView tv_ok = (TextView) ll_display.findViewById(R.id.tv_ok);
            tv_cancel.setText(cancel);
            tv_ok.setText(ok);
            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listeners[0] != null)
                        listeners[0].onClick(view);
                    parent.removeView(ll_display);//将布局移除
                }
            });
            tv_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listeners[1] != null)
                        listeners[1].onClick(view);
                    parent.removeView(ll_display);//将布局移除
                }
            });


            //在View布局完成后获得View的宽度和高度,布局完成后调用
            ll_display.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (ll_display.getWidth() > 0) {
                        ll_display.getChildAt(0).startAnimation(scaleShow);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            ll_display.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    }
                }
            });
        }
    }

    /**
     * x显示加载动画
     *
     * @param context 上下文  只传Activity和Fragment
     */
    public static void displayLoading(Object context) {
        if (context == null)
            return;
        try {
            Activity mActivity = null;
            if (context instanceof Activity)
                mActivity = (Activity) context;
            if (context instanceof Fragment)
                mActivity = ((Fragment) context).getActivity();
            if (mActivity == null)
                return;
            FrameLayout parent = (FrameLayout) mActivity.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
            String tagName = context.getClass().getSimpleName();
            View child = parent.findViewById(R.id.loading);
            if (child == null) {
                LinearLayout ll_loading_parent = new LinearLayout(mActivity);
                ll_loading_parent.setGravity(Gravity.CENTER);
                ll_loading_parent.setTag(R.id.loading, tagName);
                ll_loading_parent.setId(R.id.loading);
                ll_loading_parent.setClickable(true);//获得焦点

                LinearLayout ll_loading_view = (LinearLayout) mActivity.getLayoutInflater().inflate(R.layout.display_loading, null, false);
                ImageView iv_loading = (ImageView) ll_loading_view.findViewById(R.id.iv_loading);
                ll_loading_parent.addView(ll_loading_view);
                parent.addView(ll_loading_parent);

                /**
                 * 选择动画
                 */
                RotateAnimation rotateAnimation = new RotateAnimation(0f, 360f, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
//                rotateAnimation.setInterpolator(new LinearInterpolator());
                rotateAnimation.setRepeatCount(-1);
                rotateAnimation.setFillAfter(true);
                rotateAnimation.setDuration(1000L);
                iv_loading.startAnimation(rotateAnimation);

                //动画显示图片下标
//                int randomIndex=(int)(Math.random()*loadingAnimRess.length);

                //启动场景动画
//                SceneAnimation sceneAnimation=new SceneAnimation(iv_loading,loadingAnimRess[randomIndex],30);

            } else {
                child.setTag(R.id.loading, tagName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏加载动画
     *
     * @param context 只传Activity和Fragment
     */
    public static void hideLoading(Object context) {
        if (context == null)
            return;
        try {
            Activity mActivity = null;
            if (context instanceof Activity)
                mActivity = (Activity) context;
            if (context instanceof Fragment)
                mActivity = ((Fragment) context).getActivity();
            if (mActivity == null)
                return;
            FrameLayout parent = (FrameLayout) mActivity.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
            View child = parent.findViewById(R.id.loading);
            if (child != null) {
                String tagName = (String) child.getTag(R.id.loading);
                if (tagName.equals(context.getClass().getSimpleName()))//只有当前开启动画的界面才能关闭界面
                    parent.removeView(child);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
