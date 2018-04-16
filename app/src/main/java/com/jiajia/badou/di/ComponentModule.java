package com.jiajia.badou.di;

import com.jiajia.badou.activity.LoginActivity;
import com.jiajia.badou.module.ActivityModule;
import dagger.Component;

/**
 * Created by Lei on 2018/3/2.
 */
@Component(modules = ActivityModule.class) public interface ComponentModule {
  void inject(LoginActivity activity);
}
