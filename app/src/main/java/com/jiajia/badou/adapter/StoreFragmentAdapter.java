package com.jiajia.badou.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.jiajia.badou.R;
import com.jiajia.badou.bean.main.StoreHotRecommendBean;
import java.util.List;

/**
 * Created by Lei on 2018/2/7.
 */
public class StoreFragmentAdapter extends RecyclerView.Adapter<StoreFragmentAdapter.ViewHolder> {

  private List<StoreHotRecommendBean> mDatas;
  private LayoutInflater mInflater;
  private Context context;

  public StoreFragmentAdapter(Context mContext, List<StoreHotRecommendBean> mDatas) {
    this.context = mContext;
    this.mDatas = mDatas;
    mInflater = LayoutInflater.from(mContext);
  }

  public List<StoreHotRecommendBean> getDatas() {
    return mDatas;
  }

  public StoreFragmentAdapter setDatas(List<StoreHotRecommendBean> datas) {
    mDatas = datas;
    return this;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(mInflater.inflate(R.layout.item_store_hot_recommend, parent, false));
  }

  @SuppressLint("SetTextI18n") @Override public void onBindViewHolder(ViewHolder holder,
      @SuppressLint("RecyclerView") final int position) {
    StoreHotRecommendBean storeHotRecommendBean = mDatas.get(position);
    Glide.with(context).load(storeHotRecommendBean.getImg()).into(holder.img);
    holder.tvTip.setText(storeHotRecommendBean.getTip());
    holder.tvAccount.setText(storeHotRecommendBean.getAccount());
    SpannableString spannableString = new SpannableString(storeHotRecommendBean.getOldAccount());
    spannableString.setSpan(new StrikethroughSpan(), 0,
        storeHotRecommendBean.getOldAccount().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    holder.tvAccountOld.setText(spannableString);
    holder.linearContent.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        storeFragmentAdapterCallBack.onClick(position);
      }
    });
  }

  public void addAll(List<StoreHotRecommendBean> datas) {
    mDatas.addAll(datas);
    notifyDataSetChanged();
  }

  public void clear() {
    mDatas.clear();
    notifyDataSetChanged();
  }

  public void addNewAll(List<StoreHotRecommendBean> datas) {
    mDatas.clear();
    mDatas.addAll(datas);
    notifyDataSetChanged();
  }

  public void removeTargetItem(int position) {
    if (position < mDatas.size()) {
      mDatas.remove(position);
      notifyDataSetChanged();
    }
  }

  @Override public int getItemCount() {
    return mDatas != null ? mDatas.size() : 0;
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.img_item_store_hot_recommend) ImageView img;
    @BindView(R.id.tv_item_store_hot_recommend_tip) TextView tvTip;
    @BindView(R.id.tv_item_store_hot_recommend_account) TextView tvAccount;
    @BindView(R.id.tv_item_store_hot_recommend_account_old) TextView tvAccountOld;
    @BindView(R.id.linear_item_store_hot_recommend_content) LinearLayout linearContent;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  public interface StoreFragmentAdapterCallBack {
    void onClick(int position);
  }

  private StoreFragmentAdapterCallBack storeFragmentAdapterCallBack;

  public void setStoreFragmentAdapterCallBack(
      StoreFragmentAdapterCallBack storeFragmentAdapterCallBack) {
    this.storeFragmentAdapterCallBack = storeFragmentAdapterCallBack;
  }
}
