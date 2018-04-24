package com.jiajia.presenter.modle.mine;

import com.jiajia.presenter.bean.mine.SelectPetsByOwnerBean;
import com.jiajia.presenter.impl.MvpView;
import java.util.List;

/**
 * Created by Lei on 2018/3/9.
 */
public interface MineFragmentMvpView extends MvpView {

  void selectPetsByOwner(List<SelectPetsByOwnerBean> selectPetsByOwnerBeans);
}
