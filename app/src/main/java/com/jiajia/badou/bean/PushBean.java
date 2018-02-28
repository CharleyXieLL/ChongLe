package com.jiajia.badou.bean;

import java.io.Serializable;

/**
 * Created by Lei on 2018/1/9.
 */
public class PushBean implements Serializable{

  private String title;

  private String url;

  public PushBean(String title, String url) {
    this.title = title;
    this.url = url;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
