package com.zkyr.footballspace.util;


import com.zkyr.footballspace.model.bean.MyHttpResponse;
import com.zkyr.footballspace.model.http.ApiException;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by codeest on 2016/8/3.
 */
public class RxUtil {

    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new FlowableTransformer<T, T>() {
            @Override
            public Flowable<T> apply(Flowable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一返回结果处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<MyHttpResponse<T>, T> handleMyResult() {   //compose判断结果
        return new FlowableTransformer<MyHttpResponse<T>, T>() {
            @Override
            public Flowable<T> apply(Flowable<MyHttpResponse<T>> httpResponseFlowable) {
                return httpResponseFlowable.flatMap(new Function<MyHttpResponse<T>, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(MyHttpResponse<T> tMyHttpResponse) {
                        /*if(tMyHttpResponse.getCode() == 200) {
                            return createData(tMyHttpResponse.getNewslist());
                        } else {
                            return Flowable.error(new ApiException(tMyHttpResponse.getMessage(), tMyHttpResponse.getCode()));
                        }*/

                        if ("200".equals(tMyHttpResponse.getStatus())) {
                            return createData(tMyHttpResponse.getData());
                        }else {
                            return Flowable.error(new ApiException(tMyHttpResponse.getMessage(), /*tMyHttpResponse.getCode()*/0));
                        }
                    }
                });
            }
        };
    }


    /**
     * 生成Flowable
     *
     * @param <T>
     * @return
     */
    public static <T> Flowable<T> createData(final T t) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> emitter) throws Exception {
                try {
                    if (t != null) {
                        emitter.onNext(t);
                        emitter.onComplete();
                    } else {
                        emitter.onError(new Exception());
                    }

                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }
}
