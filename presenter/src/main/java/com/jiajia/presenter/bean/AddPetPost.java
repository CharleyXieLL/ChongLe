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
public class AddPetPost {
  @SerializedName("pet_male") String pet_male;
  @SerializedName("pet_name") String pet_name;
  @SerializedName("pet_owner") String pet_owner;
  @SerializedName("pet_type") String pet_type;
}
