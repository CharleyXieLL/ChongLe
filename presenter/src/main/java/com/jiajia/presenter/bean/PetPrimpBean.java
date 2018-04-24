package com.jiajia.presenter.bean;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
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
public class PetPrimpBean implements Serializable{

  @SerializedName("img") String img;

  @SerializedName("location") String location;

  @SerializedName("store") String store;

  @SerializedName("qq") String qq;

  @SerializedName("id") int id;

}
