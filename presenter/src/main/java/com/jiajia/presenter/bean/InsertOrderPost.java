package com.jiajia.presenter.bean;

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
public class InsertOrderPost {
  @SerializedName("customer") int customer;
  @SerializedName("orderType") String orderType;
  @SerializedName("store") String store;
  @SerializedName("petId") int petId;
}
