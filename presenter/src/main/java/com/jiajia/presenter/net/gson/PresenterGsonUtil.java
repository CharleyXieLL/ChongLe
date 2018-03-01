package com.jiajia.presenter.net.gson;

import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by johnwatsondev on 03/03/2017.
 * <br>Credit: http://www.jianshu.com/p/9ddd2d5b2d37
 * <br>Credit: http://www.jianshu.com/p/d62c2be60617
 */
public class PresenterGsonUtil {

  private static Gson sGson = null;

  static {
    if (sGson == null) {
      sGson = new Gson();
    }
  }

  private PresenterGsonUtil() {
  }

  /**
   * Result 中 data 为 JsonArray 时使用
   */
  public static <T> Result<List<T>> fromJsonArray(String json, Class<T> clz) {
    Type type = TypeBuilder.newInstance(Result.class)
        .beginSubType(List.class)
        .addTypeParam(clz)
        .endSubType()
        .build();
    return sGson.fromJson(json, type);
  }

  /**
   * Result 中 data 为 JsonObject 时使用
   */
  public static <T> Result<T> fromJsonObject(String json, Class<T> clz) {
    Type type = TypeBuilder.newInstance(Result.class).addTypeParam(clz).build();
    return sGson.fromJson(json, type);
  }

}