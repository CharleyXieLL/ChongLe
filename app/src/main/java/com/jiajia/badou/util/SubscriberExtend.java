package com.jiajia.badou.util;

import rx.Subscriber;

public abstract class SubscriberExtend<T> extends Subscriber<T> {

  @Override
  public void onCompleted() {

  }

  @Override
  public void onError(Throwable e) {

    //onError之后onNext，onCompleted不会回调
    onNext(null);
    onCompleted();
  }
}
