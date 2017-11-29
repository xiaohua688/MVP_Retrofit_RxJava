package com.zkyr.footballspace.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by xiaohua on 2017/10/16.
 */

public abstract class SingleAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    protected Context mContext;
    protected List<T> dataList;
    protected int layoutId;

    public SingleAdapter(Context mContext, List<T> dataList,int layoutId) {
        this.mContext = mContext;
        this.dataList = dataList;
        this.layoutId=layoutId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(layoutId,parent,false);
        return ViewHolder.createViewHolder(mContext,itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder,dataList.get(position),position);
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public abstract void convert(ViewHolder holder,T t,int position);
}
