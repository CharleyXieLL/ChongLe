package com.jiajia.presenter.modle.primp;

import com.jiajia.presenter.bean.PetPrimpDetailListBean;
import com.jiajia.presenter.impl.MvpView;
import java.util.List;

/**
 * Created by Lei on 2018/3/15.
 */
public interface PetPrimpDetailMvpView extends MvpView {

  void selectAllService(List<PetPrimpDetailListBean> petPrimpDetailListBeans);

  void insertOrder();
}
