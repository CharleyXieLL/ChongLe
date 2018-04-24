package com.jiajia.presenter.bean.mine;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Lei on 2018/4/24.
 */
// @formatter:off
@Data
@NoArgsConstructor
@AllArgsConstructor
// @formatter:on
public class SelectAllOrderBean {
  @SerializedName("orderType") String orderType;
  @SerializedName("No") int No;
  @SerializedName("id") int id;
  @SerializedName("store") String store;
  @SerializedName("situation") int situation;
  @SerializedName("customer") String customer;
  @SerializedName("pet_id") int pet_id;
  @SerializedName("pet_name") String pet_name;
  @SerializedName("pet_img") String pet_img;
}
