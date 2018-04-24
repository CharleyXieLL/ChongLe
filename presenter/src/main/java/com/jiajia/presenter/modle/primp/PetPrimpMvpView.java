package com.jiajia.presenter.modle.primp;

import com.jiajia.presenter.bean.PetPrimpBean;
import com.jiajia.presenter.impl.MvpView;
import java.util.List;

/**
 * Created by Lei on 2018/3/13.
 */
public interface PetPrimpMvpView extends MvpView {

  void selectAllStoreMess(List<PetPrimpBean> petPrimpBeans);
}
