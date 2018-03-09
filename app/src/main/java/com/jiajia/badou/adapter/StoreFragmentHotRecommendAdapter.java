package com.jiajia.badou.adapter;

import android.app.Activity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.jiajia.badou.R;
import com.jiajia.badou.bean.main.StoreHotRecommendBean;
import com.jiajia.presenter.util.ViewUtil;
import java.util.List;

/**
 * Created by Lei on 2018/3/9.
 */
public class StoreFragmentHotRecommendAdapter {

  private Activity activity;

  public StoreFragmentHotRecommendAdapter(Activity activity) {
    this.activity = activity;
  }

  public void addAll(List<StoreHotRecommendBean> list, LinearLayout linearLayout) {
    linearLayout.removeAllViews();
    for (int i = 0; i < list.size(); i++) {
      View view =
          LayoutInflater.from(activity).inflate(R.layout.item_store_hot, null, false);
      LinearLayout.LayoutParams layoutParams =
          new LinearLayout.LayoutParams(ViewUtil.dp2px(activity, 140),
              LinearLayout.LayoutParams.WRAP_CONTENT);
      view.setLayoutParams(layoutParams);

      final LinearLayout linearContent =
          view.findViewById(R.id.linear_item_store_hot_recommend_content);
      ImageView imageView = view.findViewById(R.id.img_item_store_hot_recommend);
      TextView tvTip = view.findViewById(R.id.tv_item_store_hot_recommend_tip);

      TextView tvAccount = view.findViewById(R.id.tv_item_store_hot_recommend_account);
      TextView tvAccountOld = view.findViewById(R.id.tv_item_store_hot_recommend_account_old);

      StoreHotRecommendBean storeHotRecommendBean = list.get(i);

      Glide.with(activity).load(storeHotRecommendBean.getImg()).into(imageView);
      tvTip.setText(storeHotRecommendBean.getTip());
      tvAccount.setText(storeHotRecommendBean.getAccount());

      SpannableString spannableString = new SpannableString(storeHotRecommendBean.getOldAccount());
      spannableString.setSpan(new StrikethroughSpan(), 0,
          storeHotRecommendBean.getOldAccount().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
      tvAccountOld.setText(spannableString);

      linearContent.setTag(i);

      linearContent.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          storeFragmentHotRecommendCallBack.onItemClick((int) linearContent.getTag());
        }
      });
      linearLayout.addView(view);
    }
  }

  public interface StoreFragmentHotRecommendCallBack {
    void onItemClick(int position);
  }

  private StoreFragmentHotRecommendCallBack storeFragmentHotRecommendCallBack;

  public void setStoreFragmentAdapterCallBack(
      StoreFragmentHotRecommendCallBack storeFragmentAdapterCallBack) {
    this.storeFragmentHotRecommendCallBack = storeFragmentAdapterCallBack;
  }
}
