package com.jiajia.presenter.net.gson;

/**
 * 纯净的网络请求响应接口（直接返回拿到的结果，不做任何处理。）
 * <br>Created by lei on 5/17/16.
 */
public interface PureResponseCallback<T> {
  void onSuccess(T t);

  void onFailure(Throwable e);

  void onCompleted();
}