package com.jiajia.presenter.net;

/**
 * Created by Lei on 2018/3/1.
 * 网络请求工具类
 */
public class HttpUtil {

  private static String BASE_URL = "http://dongceha.oicp.io/";

  /**
   * 用户注册
   */
  public static String userRegister(String userName, String password) {
    return BASE_URL + "reagisterUser?username=" + userName + "&password=" + password;
  }

  /**
   * 用户登录
   */
  public static String userLogin(String userName, String password) {
    return BASE_URL + "userLogin?username=" + userName + "&password=" + password;
  }

  /**
   * 用户注册时检测账号是否可用
   */
  public static String checkCanRegister(String userName) {
    return BASE_URL + "selectUserByUsername?username=" + userName;
  }

  /**
   * 获取所有服务类型
   */
  public static String selectAllService() {
    return BASE_URL + "selectAllService";
  }

  /**
   * 修改密码
   */
  public static String updatePassword(String userName) {
    return BASE_URL + "upUserPassById?" + "username=" + userName;
  }
}
