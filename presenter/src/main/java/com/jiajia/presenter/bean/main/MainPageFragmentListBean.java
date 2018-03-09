package com.jiajia.presenter.bean.main;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Lei on 2018/3/9.
 */
// @formatter:off
@Data
@NoArgsConstructor
@AllArgsConstructor
// @formatter:on
public class MainPageFragmentListBean {
  @SerializedName("img") String img;
  @SerializedName("tip") String tip;
  @SerializedName("name") String name;
  @SerializedName("account") String account;
}
