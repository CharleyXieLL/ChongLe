package com.jiajia.badou.bean;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Lei on 2018/3/1.
 * 网络请求的返回结果
 */
// @formatter:off
@Data
@NoArgsConstructor
@AllArgsConstructor
// @formatter:on
public class ResultObject {

  /**
   * 请求提示消息
   */
  @SerializedName("msg") String msg;

  /**
   * 请求码
   */
  @SerializedName("code") String code;

  /**
   * 用户姓名
   */
  @SerializedName("userName") String userName;
}
