package com.jiajia.presenter.bean.mine;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
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
public class SelectPetsByOwnerBean implements Serializable{
  @SerializedName("pet_img") String pet_img;
  @SerializedName("pet_name") String pet_name;
  @SerializedName("pet_male") String pet_male;
  @SerializedName("pet_type") String pet_type;
  @SerializedName("pet_owner") String pet_owner;
  @SerializedName("pet_id") int pet_id;
  @SerializedName("isCheck") boolean isCheck;
}
