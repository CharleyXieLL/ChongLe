package com.jiajia.presenter.net;

import com.jiajia.presenter.bean.InsertOrderPost;

/**
 * Created by Lei on 2018/3/1.
 * 网络请求工具类
 */
public class HttpUtil {

  private static String BASE_URL = "http://dongceha.oicp.io:42440/";

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

  /**
   * 查询所有店铺(列表)
   */
  public static String selectAllStoreMess() {
    return BASE_URL + "selectAllStoreMess";
  }

  /**
   * 查询所有服务
   *
   * @param store 门店
   * @param station 服务类型(宠物医疗，宠物托管，宠物美容)
   */
  public static String selectStaff(String store, String station) {
    return BASE_URL + "selectTheStaff?store=" + store + "&station=" + station;
  }

  /**
   * 预约项目
   */
  public static String insertOrder(InsertOrderPost insertOrderPost) {
    return BASE_URL
        + "insertOrder?customer="
        + insertOrderPost.getCustomer()
        + "&orderType="
        + insertOrderPost.getOrderType()
        + "&store="
        + insertOrderPost.getStore()
        + "&petId="
        + insertOrderPost.getPetId();
  }

  /**
   * 根据 id 查询用户信息
   */
  public static String selectById(int id) {
    return BASE_URL + "selectById?id=" + id;
  }

  /**
   * 添加宠物
   */
  public static String insertPets() {
    return BASE_URL + "insertPets";
  }

  /**
   * 查询宠物
   */
  public static String selectPetsByOwner(int userId) {
    return BASE_URL + "selectPetsByOwner?owner=" + userId;
  }

  /**
   * 删除宠物
   */
  public static String delPetsById(int petId) {
    return BASE_URL + "delPetsById?id=" + petId;
  }

  /**
   * 查询自己的订单
   */
  public static String selectAllOrderByCustomer(int userId) {
    return BASE_URL + "selectAllOrderByCustomer?customer=" + userId;
  }
}
