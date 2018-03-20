package com.jiajia.badou.bean;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Lei on 2018/3/13.
 */
// @formatter:off
@Data
@NoArgsConstructor
@AllArgsConstructor
// @formatter:on
public class PetPrimpBean {

  @SerializedName("img") String img;

  @SerializedName("title") String title;

  @SerializedName("address1") String address1;

  @SerializedName("address2") String address2;

  @SerializedName("phone") String phone;

  @SerializedName("distance") String distance;

}
