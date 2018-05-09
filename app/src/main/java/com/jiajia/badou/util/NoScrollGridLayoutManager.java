package com.jiajia.badou.util;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * Created by Lei on 2018/5/9.
 */
public class NoScrollGridLayoutManager extends GridLayoutManager {

  public NoScrollGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  public NoScrollGridLayoutManager(Context context, int spanCount) {
    super(context, spanCount);
  }

  public NoScrollGridLayoutManager(Context context, int spanCount, int orientation,
      boolean reverseLayout) {
    super(context, spanCount, orientation, reverseLayout);
  }

  @Override public boolean canScrollVertically() {
    return false;
  }
}
