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
import java.io.File;

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

  public void okGoPost(Context context, String data, String url) {
    OkGo.<String>post(url).upJson(data).tag(context).execute(new StringCallback() {
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

  public void okGoPostFile(Context context, String data, String url, String filePath) {
    OkGo.<String>post(url).upJson(data)
        .tag(context)
        .params("file", new File(filePath))
        .execute(new StringCallback() {
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

  public void okGoPostFile(Context context, String url, String filePath,
      String pet_male, String pet_name, String pet_owner, String pet_type) {
    OkGo.<String>post(url)
        .tag(context)
        .params("pet_male", pet_male)
        .params("pet_name", pet_name)
        .params("pet_owner", pet_owner)
        .params("pet_type", pet_type)
        .params("file", new File(filePath))
        .execute(new StringCallback() {
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
