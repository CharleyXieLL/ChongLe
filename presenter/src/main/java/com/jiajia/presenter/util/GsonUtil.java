package com.jiajia.presenter.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by johnwatsondev on 03/03/2017.
 * <br>Credit: http://www.jianshu.com/p/9ddd2d5b2d37
 * <br>Credit: http://www.jianshu.com/p/d62c2be60617
 */
public class GsonUtil {

  private static Gson sGson = null;

  static {
    if (sGson == null) {
      sGson = new Gson();
    }
  }

  private GsonUtil() {
  }

  public static <T> List<T> toList(String json, Class<T> clz) {
    List<T> list = sGson.fromJson(json, TypeFactory.$List(clz));
    return list;
  }

  public static <T> List<List<T>> toListList(String json, Class<T> clz) {
    List<List<T>> list = sGson.fromJson(json, TypeFactory.$List(TypeFactory.$List(clz)));
    return list;
  }

  public static <T> T[] toArray(String json, Class<T> clz) {
    T[] array = sGson.fromJson(json, TypeFactory.$Array(clz));
    return array;
  }

  public static <T> Set<T> toSet(String json, Class<T> clz) {
    Set<T> set = sGson.fromJson(json, TypeFactory.$Set(clz));
    return set;
  }

  public static <K, V> Map<K, V> toMap(String json, Class<K> keyClz, Class<V> valueClz) {
    Map<K, V> map = sGson.fromJson(json, TypeFactory.$Map(keyClz, valueClz));
    return map;
  }

  public static <K, V> Map<K, List<V>> toMapNestList(String json, Class<K> keyClz,
      Class<V> valueClz) {
    Map<K, List<V>> map =
        sGson.fromJson(json, TypeFactory.$Map(keyClz, TypeFactory.$List(valueClz)));
    return map;
  }

  public static String toJson(Object src) {
    return sGson.toJson(src);
  }

  public static <T> JsonObject toJsonObject(T src) {
    JsonElement jsonElement = sGson.fromJson(toJson(src), JsonElement.class);
    return jsonElement.getAsJsonObject();
  }

  public static JsonObject toJsonObject(String json) {
    JsonElement jsonElement = sGson.fromJson(json, JsonElement.class);
    return jsonElement.getAsJsonObject();
  }
}