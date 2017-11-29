package com.zkyr.footballspace.model.http;


import com.zkyr.footballspace.app.APPConstants;
import com.zkyr.footballspace.app.MyApplication;
import com.zkyr.footballspace.util.LogUtil;
import com.zkyr.footballspace.util.SystemUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/6/17.
 */

public class RetrofitHelper {

    public static final String TAG = RetrofitHelper.class.getSimpleName();
    //    public static final String BASE_URL = "http://47.93.33.168:9999/";
//    public static final String BASE_URL = "http://api.tianapi.com/";
    public static final String BASE_URL = "http://192.168.1.178:8080/peacock/webapi/";
//    public static final String BASE_URL = "http://192.168.1.215:8080/peacock/webapi/";

    //测试
//    public static final String BASE_URL = "http://172.16.10.241:9999/";
//        public static final String BASE_URL = "http://172.16.10.246:9999/";
//    public static final String BASE_URL = "http://172.16.10.242:9999/";
//    public static final String BASE_URL = "http://172.16.10.246:9999/";
    private static final int DEFAULT_TIMEOUT = 10;
    private ApiManager apiManager;
    private Retrofit retrofit;

    /**
     * 创建私有构造函数  只能在本类中进行初始化
     */
    private RetrofitHelper() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                LogUtil.e("RetrofitLog", message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();


        File cacheFile = new File(APPConstants.PATH_CACHE);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!SystemUtil.isNetworkConnected(MyApplication.getInstance().getApplicationContext())) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (SystemUtil.isNetworkConnected(MyApplication.getInstance().getApplicationContext())) {
                    int maxAge = 0;
                    // 有网络时, 不缓存, 最大保存时长为0
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };


        OkHttpClient client = builder
                .addNetworkInterceptor(cacheInterceptor)
                .addInterceptor(cacheInterceptor) //缓存
                .addInterceptor(loggingInterceptor)//log
                .cache(cache)
                //设置超时
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                //错误重连
                .retryOnConnectionFailure(true)
                .build();

        //实例化 retrofit对象
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiManager = retrofit.create(ApiManager.class);
    }

    /**
     * 创建HttpMethod实例
     */
    public static class SingleInstance {
        private static final RetrofitHelper INSTANCE = new RetrofitHelper();
    }

    /**
     * 获得HttpMethod实例
     *
     * @return
     */
    public static RetrofitHelper getInstance() {
        return SingleInstance.INSTANCE;
    }


    /**
     * 获取ApiManger实例
     * @return
     */
    public ApiManager getApiManager() {
        return apiManager;
    }
}
