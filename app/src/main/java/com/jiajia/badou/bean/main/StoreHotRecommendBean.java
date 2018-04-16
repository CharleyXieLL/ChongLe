package com.jiajia.badou.bean.main;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
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
public class StoreHotRecommendBean implements Serializable{

  @SerializedName("img") String img;

  @SerializedName("tip") String tip;

  @SerializedName("account") String account;

  @SerializedName("oldAccount") String oldAccount;
}
