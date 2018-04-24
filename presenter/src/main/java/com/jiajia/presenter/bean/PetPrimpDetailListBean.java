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
public class PetPrimpDetailListBean {

  @SerializedName("service_time") String service_time;
  @SerializedName("service_desc") String service_desc;
  @SerializedName("service_name") String service_name;
  @SerializedName("service_pay") String service_pay;
  @SerializedName("id") int id;
  @SerializedName("isCheck") boolean isCheck;
}
