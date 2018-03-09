package com.jiajia.badou.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import com.jiajia.presenter.bean.main.MainPageFragmentListBean;
import java.util.List;

/**
 * Created by Lei on 2018/2/6.
 */
public class MainPageFragmentAdapter
    extends RecyclerView.Adapter<MainPageFragmentAdapter.ViewHolder> {

  private List<MainPageFragmentListBean> mDatas;
  private LayoutInflater mInflater;
  private Context context;

  public MainPageFragmentAdapter(Context mContext, List<MainPageFragmentListBean> mDatas) {
    this.context = mContext;
    this.mDatas = mDatas;
    mInflater = LayoutInflater.from(mContext);
  }

  public List<MainPageFragmentListBean> getDatas() {
    return mDatas;
  }

  public MainPageFragmentAdapter setDatas(List<MainPageFragmentListBean> datas) {
    mDatas = datas;
    return this;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(mInflater.inflate(R.layout.item_main_page_adapter, parent, false));
  }

  @SuppressLint("SetTextI18n") @Override public void onBindViewHolder(ViewHolder holder,
      @SuppressLint("RecyclerView") final int position) {
    MainPageFragmentListBean mainPageFragmentListBean = mDatas.get(position);
    Glide.with(context).load(mainPageFragmentListBean.getImg()).into(holder.imgListAvatar);
    holder.tvTip.setText(mainPageFragmentListBean.getTip());
    holder.tvName.setText(mainPageFragmentListBean.getName());
    holder.tvLookAccount.setText(mainPageFragmentListBean.getAccount());
    holder.layoutContent.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        mainPageFragmentAdapterCallBack.onClick(position);
      }
    });
  }

  public void addAll(List<MainPageFragmentListBean> datas) {
    mDatas.addAll(datas);
    notifyDataSetChanged();
  }

  public void clear() {
    mDatas.clear();
    notifyDataSetChanged();
  }

  public void addNewAll(List<MainPageFragmentListBean> datas) {
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

    @BindView(R.id.img_main_page_fragment_list_avatar) ImageView imgListAvatar;
    @BindView(R.id.tv_main_page_fragment_tip) TextView tvTip;
    @BindView(R.id.tv_main_page_fragment_name) TextView tvName;
    @BindView(R.id.tv_main_page_fragment_look_account) TextView tvLookAccount;
    @BindView(R.id.layout_item_main_page_content) LinearLayout layoutContent;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  public interface MainPageFragmentAdapterCallBack {
    void onClick(int position);
  }

  private MainPageFragmentAdapterCallBack mainPageFragmentAdapterCallBack;

  public void setMainPageFragmentAdapterCallBack(
      MainPageFragmentAdapterCallBack mainPageFragmentAdapterCallBack) {
    this.mainPageFragmentAdapterCallBack = mainPageFragmentAdapterCallBack;
  }
}
