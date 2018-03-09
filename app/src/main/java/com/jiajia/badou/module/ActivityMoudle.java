package com.jiajia.badou.module;

import com.jiajia.badou.activity.LoginActivity;
import dagger.Module;

/**
 * Created by Lei on 2018/3/2.
 */
@Module
public class ActivityMoudle {

  private LoginActivity loginActivity;

  public ActivityMoudle(LoginActivity loginActivity) {
    this.loginActivity = loginActivity;
  }
}
