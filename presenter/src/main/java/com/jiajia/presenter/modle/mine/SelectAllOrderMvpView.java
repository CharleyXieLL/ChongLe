package com.jiajia.presenter.modle.mine;

import com.jiajia.presenter.bean.mine.SelectAllOrderBean;
import com.jiajia.presenter.bean.mine.SelectPetsByOwnerBean;
import com.jiajia.presenter.impl.MvpView;
import java.util.List;

/**
 * Created by Lei on 2018/4/24.
 */
public interface SelectAllOrderMvpView extends MvpView {
  void selectAllOrderByCustomer(List<SelectAllOrderBean> selectAllOrderBeans);
  void selectPetsByOwner(List<SelectPetsByOwnerBean> selectPetsByOwnerBeans);
}
