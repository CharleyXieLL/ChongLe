package com.jiajia.badou.di;

import com.jiajia.badou.activity.LoginActivity;
import com.jiajia.badou.module.ActivityMoudle;
import dagger.Component;

/**
 * Created by Lei on 2018/3/2.
 */
@Component(modules = ActivityMoudle.class) public interface ComponentModule {
  void inject(LoginActivity activity);
}
