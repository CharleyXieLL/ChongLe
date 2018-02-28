package com.jiajia.badou;

import android.app.Application;
import cn.jpush.android.api.JPushInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import okhttp3.OkHttpClient;

/**
 * Created by Lei on 2018/1/30.
 */
public class MainApplication extends Application {

  //维护全局context
  protected static MainApplication instance;

  public MainApplication() {
    instance = this;
  }

  public synchronized static MainApplication getInstance() {
    return instance;
  }

  @Override public void onCreate() {
    super.onCreate();
    instance = this;
    initJGPush();
    initOkGo();
  }

  private void initJGPush() {
    JPushInterface.setDebugMode(true);
    JPushInterface.init(this);
  }

  private void initOkGo() {
    OkGo.getInstance().init(this);
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
    //log打印级别，决定了log显示的详细程度
    loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
    //log颜色级别，决定了log在控制台显示的颜色
    loggingInterceptor.setColorLevel(Level.INFO);
    builder.addInterceptor(loggingInterceptor);

    //全局的读取超时时间
    builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
    //全局的写入超时时间
    builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
    //全局的连接超时时间
    builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);

    //方法一：信任所有证书,不安全有风险
    HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();

    OkGo.getInstance().init(this)                       //必须调用初始化
        .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
        .setRetryCount(
            3);                              //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
  }
}
