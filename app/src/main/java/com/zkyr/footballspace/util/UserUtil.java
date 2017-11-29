package com.zkyr.footballspace.util;

import android.content.Context;

/**
 * Created by xiaohua on 2017/10/24.
 */

public class UserUtil {
    private final static String TOKEN="token";
    private final static String ACCOUNT="account";
    private final static String PASSWORD="password";
    private final static String SEARCHHISTORY="searchHistory";

    private final static String HEADIMG="headimg";
    private final static String NICKNAME="nickname";

    /**
     * 保存Token
     */
    public static void saveToken(Context context, String token) {
        SharedPreferencesUtil.getInstance(context).putString(TOKEN, token);
    }

    /**
     * 得到Token
     */
    public static String getToken(Context context){
        return SharedPreferencesUtil.getInstance(context).getString(TOKEN);
    }

    /**
     * 保存用户账号
     */
    public static void saveAccount(Context context, String account) {
        SharedPreferencesUtil.getInstance(context).putString(ACCOUNT, account);
    }

    /**
     * 得到用户账号
     */
    public static String getAccount(Context context){
        return SharedPreferencesUtil.getInstance(context).getString(ACCOUNT);
    }


    /**
     * 保存用户密码
     */
    public static void savePassword(Context context, String passWord) {
        SharedPreferencesUtil.getInstance(context).putString(PASSWORD, passWord);
    }

    /**
     * 得到用户密码
     */
    public static String getPassword(Context context){
        return SharedPreferencesUtil.getInstance(context).getString(PASSWORD);
    }

    /**
     * 保存用户头像
     */
    public static void saveHeadImg(Context context, String headImg) {
        SharedPreferencesUtil.getInstance(context).putString(HEADIMG, headImg);
    }

    /**
     * 得到用户头像
     */
    public static String getHeadImg(Context context){
        return SharedPreferencesUtil.getInstance(context).getString(HEADIMG);
    }

    /**
     * 保存用户昵称
     */
    public static void saveNickname(Context context, String nickName) {
        SharedPreferencesUtil.getInstance(context).putString(NICKNAME, nickName);
    }

    /**
     * 得到用户昵称
     */
    public static String getNickname(Context context){
        return SharedPreferencesUtil.getInstance(context).getString(NICKNAME);
    }


    /**
     * 保存用户搜索历史
     * @param context
     * @param searchHistory
     */
    public static void saveSearchHistory(Context context,String searchHistory){
        SharedPreferencesUtil.getInstance(context).putString(SEARCHHISTORY,searchHistory);
    }

    /**
     * 得到用户搜索历史
     */
    public static String getSearchHistory(Context context){
        return SharedPreferencesUtil.getInstance(context).getString(SEARCHHISTORY);
    }


    /**
     * 清除所有的用户信息
     */
    public static void clearUser(Context context) {
        SharedPreferencesUtil.getInstance(context).clearSp();
    }
}
