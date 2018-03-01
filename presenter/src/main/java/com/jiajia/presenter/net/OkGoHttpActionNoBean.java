package com.jiajia.presenter.net;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.jiajia.presenter.util.RequestCode;
import com.jiajia.presenter.util.Strings;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

/**
 * Created by Lei on 2018/3/1.
 */
public abstract class OkGoHttpActionNoBean {
  public void okGoGet(Context context, String url) {
    OkGo.<String>get(url).tag(context).execute(new StringCallback() {
      @Override public void onSuccess(Response<String> response) {
        if (!Strings.isNullOrEmpty(response.body())) {
          JsonObject jsonObject = null;
          try {
            JsonElement jsonElement = new Gson().fromJson(response.body(), JsonElement.class);
            if (jsonElement != null) {
              jsonObject = jsonElement.getAsJsonObject();
            }
          } catch (JsonSyntaxException e) {
            e.printStackTrace();
          }
          if (jsonObject != null) {
            String code = jsonObject.get("code").getAsString();
            String msg = jsonObject.get("msg").getAsString();
            try {
              if (isResponseSuccess(code)) {
                onResponseCodeSuccess(msg, code);
              } else {
                onResponseCodeFailed(msg, code);
              }
            } catch (Exception e) {
              e.printStackTrace();
            }
          } else {
            try {
              onResponseCodeFailed("网络请求错误", "400");
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        }
      }
    });
  }

  private boolean isResponseSuccess(String code) {
    if (code.equals(RequestCode.SUCCESS)) {
      return true;
    }
    return false;
  }

  public abstract void onResponseCodeSuccess(String msg, String code) throws Exception;

  public abstract void onResponseCodeFailed(String msg, String code) throws Exception;
}
