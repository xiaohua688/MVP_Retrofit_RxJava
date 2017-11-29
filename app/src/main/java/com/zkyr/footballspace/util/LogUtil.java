package com.zkyr.footballspace.util;

import android.util.Log;

/**
 * Created by Administrator on 2017/5/23.
 */

public class LogUtil {

    //测试时使用,上线后改为false
    private static final boolean isDebug=true;

    public static void e(String tag,String method,String message){
        if(isDebug)
            Log.e(tag, "+++"+method+": "+message );
    }

    public static void e(String tag,String message){
        if(isDebug)
            Log.e(tag, "+++e: "+message );
    }
}
