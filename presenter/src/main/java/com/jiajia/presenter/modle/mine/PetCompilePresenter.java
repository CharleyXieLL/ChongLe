package com.jiajia.presenter.modle.mine;

import com.jiajia.presenter.bean.AddPetPost;
import com.jiajia.presenter.modle.BasePresenter;
import com.jiajia.presenter.net.HttpUtil;
import com.jiajia.presenter.net.OkGoHttpActionNoBean;

/**
 * Created by Lei on 2018/4/24.
 */
public class PetCompilePresenter extends BasePresenter<PetCompileMvpView> {

  public PetCompilePresenter() {

  }

  public void insertPets(AddPetPost addPetPost, String filePath) {
    new OkGoHttpActionNoBean() {
      @Override public void onResponseCodeSuccess(String msg, String code) throws Exception {
        getView().insertPets();
      }

      @Override public void onResponseCodeFailed(String msg, String code) throws Exception {
        getView().getFailed(msg, code);
      }
    }.okGoPostFile(getActivity(), HttpUtil.insertPets(), filePath, addPetPost.getPet_male(),
        addPetPost.getPet_name(), addPetPost.getPet_owner(), addPetPost.getPet_type());
  }

  public void delPetsById(int petId) {
    new OkGoHttpActionNoBean() {
      @Override public void onResponseCodeSuccess(String msg, String code) throws Exception {
        getView().delPetsById();
      }

      @Override public void onResponseCodeFailed(String msg, String code) throws Exception {
        getView().getFailed(msg, code);
      }
    }.okGoGet(getActivity(), HttpUtil.delPetsById(petId));
  }
}
