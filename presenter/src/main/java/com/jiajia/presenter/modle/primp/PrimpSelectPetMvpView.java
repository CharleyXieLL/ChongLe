package com.jiajia.presenter.modle.primp;

import com.jiajia.presenter.bean.mine.SelectPetsByOwnerBean;
import com.jiajia.presenter.impl.MvpView;
import java.util.List;

/**
 * Created by Lei on 2018/4/24.
 */
public interface PrimpSelectPetMvpView extends MvpView {
  void selectPetsByOwner(List<SelectPetsByOwnerBean> selectPetsByOwnerBeans);
  void insertOrder();
}
