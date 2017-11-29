package com.zkyr.footballspace.model.http;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by xiaohua on 2017/11/28.
 */

public class HttpIntercepter implements Interceptor {

    private final String token;

    public HttpIntercepter(String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request=chain.request();
        RequestBody body=request.body();
        if(body!=null){
            RequestBody newBody=null;
            if(body instanceof FormBody){
                newBody=new FormBody.Builder()
                        .add("token",token)
                        .build();
            }else{

            }

            Request newRequest=request.newBuilder()
                    .url(request.url())
                    .method(request.method(),newBody)
                    .build();
            return chain.proceed(newRequest);
        }
        return chain.proceed(request);
    }
}
