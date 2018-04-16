package com.jiajia.badou.bean;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Lei on 2018/4/16.
 */
// @formatter:off
@Data
@NoArgsConstructor
@AllArgsConstructor
// @formatter:on
public class ChongQuanBean {
  @SerializedName("imgAvatar") int imgAvatar;
  @SerializedName("userName") String userName;
  @SerializedName("publishTime") String publishTime;
  @SerializedName("imgContent") int imgContent;
  @SerializedName("tip") String tip;
  @SerializedName("seeCount") int seeCount;
  @SerializedName("collectCount") int collectCount;
  @SerializedName("isCollect") boolean isCollect;
}
