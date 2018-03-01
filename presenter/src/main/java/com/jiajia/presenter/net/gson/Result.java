package com.jiajia.presenter.net.gson;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by johnwatsondev on 02/03/2017.
 */
// @formatter:off
@Data
@NoArgsConstructor
@AllArgsConstructor
// @formatter:on
public class Result<T> {
  @SerializedName(EntityCommonFields.CODE) int code;
  @SerializedName(EntityCommonFields.MSG) String msg;
  @SerializedName(EntityCommonFields.DATA) T data;
}