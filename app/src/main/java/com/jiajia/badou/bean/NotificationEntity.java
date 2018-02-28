package com.jiajia.badou.bean;

/**
 * Created by Lei on 2018/1/9.
 */
public class NotificationEntity {

  private String title;

  private String content;

  private String data;

  public NotificationEntity(String title, String content, String data) {
    this.title = title;
    this.content = content;
    this.data = data;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }
}
