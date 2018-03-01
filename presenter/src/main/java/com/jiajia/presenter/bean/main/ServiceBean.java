package com.jiajia.presenter.bean.main;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Lei on 2018/3/1.
 * 服务
 */
// @formatter:off
@Data
@NoArgsConstructor
@AllArgsConstructor
// @formatter:on
public class ServiceBean {

  /**
   * 服务时间
   */
  @SerializedName("service_time") String service_time;

  /**
   * 服务描述
   */
  @SerializedName("service_desc") String service_desc;

  /**
   * 服务名称
   */
  @SerializedName("service_name") String service_name;

  /**
   * 服务支付类型
   */
  @SerializedName("service_pay") String service_pay;

  /**
   * 服务id
   */
  @SerializedName("id") long id;
}
