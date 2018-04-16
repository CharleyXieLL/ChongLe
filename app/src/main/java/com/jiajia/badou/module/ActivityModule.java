package com.jiajia.badou.module;

import com.jiajia.badou.activity.LoginActivity;
import dagger.Module;

/**
 * Created by Lei on 2018/3/2.
 */
@Module
public class ActivityModule {

  private LoginActivity loginActivity;

  public ActivityModule(LoginActivity loginActivity) {
    this.loginActivity = loginActivity;
  }
}
