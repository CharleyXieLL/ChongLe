package com.jiajia.presenter.bean.login;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Lei on 2018/3/1.
 */
// @formatter:off
@Data
@NoArgsConstructor
@AllArgsConstructor
// @formatter:on
public class LoginSuccessBean {

  @SerializedName("user_password") String user_password;
  @SerializedName("user_name") String user_name;
  @SerializedName("id") int id;

}
