package com.zkyr.footballspace.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by xiaohua on 2017/10/13.
 */

public class ViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private Context mContext;
    private View mContentView;

    public ViewHolder(View itemView, Context context) {
        super(itemView);
        mContentView = itemView;
        mContext = context;
        mViews = new SparseArray<>();
    }

    /**
     * 根据itemView创建ViewHolder
     *
     * @param context
     * @param itemView
     * @return
     */
    public static ViewHolder createViewHolder(Context context, View itemView) {
        return new ViewHolder(itemView, context);
    }

    /**
     * 根据layoutId创建ViewHolder
     *
     * @param context
     * @param parent
     * @param layoutId
     * @return
     */
    public static ViewHolder createViewHolder(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new ViewHolder(itemView, context);
    }


    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mContentView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }


    public View getmContentView() {
        return mContentView;
    }


    /**
     * 以下为 辅助方法
     **/

    public ViewHolder setText(int viewId, String text) {
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }

    public ViewHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public ViewHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /****关于点击事件和长按事件*****/
    public ViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public ViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    public ViewHolder setItemViewOnClickListener(View.OnClickListener listener){
        mContentView.setOnClickListener(listener);
        return this;
    }
}
