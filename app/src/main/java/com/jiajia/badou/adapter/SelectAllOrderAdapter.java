package com.jiajia.badou.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.jiajia.badou.R;
import com.jiajia.presenter.bean.mine.SelectAllOrderBean;
import com.jiajia.presenter.bean.mine.SelectPetsByOwnerBean;
import com.jiajia.presenter.util.ViewUtil;
import java.util.ArrayList;
import java.util.List;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Lei on 2018/4/24.
 */
public class SelectAllOrderAdapter extends RecyclerView.Adapter<SelectAllOrderAdapter.ViewHolder> {

  private List<SelectAllOrderBean> mDatas;
  private List<SelectPetsByOwnerBean> selectPetsByOwnerBeans;
  private LayoutInflater mInflater;
  private Context context;

  public SelectAllOrderAdapter(Context mContext, List<SelectAllOrderBean> mDatas,
      List<SelectPetsByOwnerBean> selectPetsByOwnerBeans) {
    this.context = mContext;
    this.mDatas = mDatas;
    this.selectPetsByOwnerBeans = selectPetsByOwnerBeans;
    mInflater = LayoutInflater.from(mContext);
  }

  public List<SelectAllOrderBean> getDatas() {
    return mDatas;
  }

  public SelectAllOrderAdapter setDatas(List<SelectAllOrderBean> datas) {
    mDatas = datas;
    return this;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(mInflater.inflate(R.layout.item_select_all_order, parent, false));
  }

  @SuppressLint("SetTextI18n") @Override public void onBindViewHolder(ViewHolder holder,
      @SuppressLint("RecyclerView") final int position) {
    SelectAllOrderBean selectAllOrderBean = mDatas.get(position);
    Glide.with(context)
        .load(selectAllOrderBean.getPet_img())
        .bitmapTransform(new RoundedCornersTransformation(context, ViewUtil.dp2px(context, 8), 0,
            RoundedCornersTransformation.CornerType.ALL))
        .into(holder.imgItemSelectAllOrder);
    holder.tvPetName.setText(selectAllOrderBean.getPet_name());
    holder.tvItemSelectAllOrderStore.setText("预约门店：" + selectAllOrderBean.getStore());
    holder.tvItemSelectAllOrderType.setText("预约内容：" + selectAllOrderBean.getOrderType());
  }

  public void addAll(List<SelectAllOrderBean> datas) {
    mDatas.addAll(datas);
    clearData();
    notifyDataSetChanged();
  }

  public void clear() {
    mDatas.clear();
    notifyDataSetChanged();
  }

  public void addNewAll(List<SelectAllOrderBean> datas) {
    mDatas.clear();
    mDatas.addAll(datas);
    notifyDataSetChanged();
  }

  private void clearData() {
    List<Integer> indexs = new ArrayList<>();
    for (int i = 0; i < mDatas.size(); i++) {
      int petId = mDatas.get(i).getPet_id();
      boolean isExist = false;
      for (int j = 0; j < selectPetsByOwnerBeans.size(); j++) {
        if (selectPetsByOwnerBeans.get(j).getPet_id() == petId) {
          isExist = true;
        }
      }
      if (!isExist) {
        indexs.add(i);
      }
    }
    int i = 0;
    for (int k = 0; k < indexs.size(); k++) {
      mDatas.remove(indexs.get(k) - i);
      i++;
    }
    for (int p = 0; p < mDatas.size(); p++) {
      for (int z = 0; z < selectPetsByOwnerBeans.size(); z++) {
        if (selectPetsByOwnerBeans.get(z).getPet_id() == mDatas.get(p).getPet_id()) {
          mDatas.get(p).setPet_img(selectPetsByOwnerBeans.get(z).getPet_img());
          mDatas.get(p).setPet_name(selectPetsByOwnerBeans.get(z).getPet_name());
        }
      }
    }
  }

  @Override public int getItemCount() {
    return mDatas != null ? mDatas.size() : 0;
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.img_item_select_all_order) ImageView imgItemSelectAllOrder;
    @BindView(R.id.tv_item_select_all_order_type) TextView tvItemSelectAllOrderType;
    @BindView(R.id.tv_item_select_all_order_store) TextView tvItemSelectAllOrderStore;
    @BindView(R.id.tv_item_select_all_order_name) TextView tvPetName;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  public interface SelectAllOrderAdapterCallBack {
    void onClick(int position);
  }

  private SelectAllOrderAdapterCallBack selectAllOrderAdapterCallBack;

  public void setSelectAllOrderAdapterCallBack(
      SelectAllOrderAdapterCallBack selectAllOrderAdapterCallBack) {
    this.selectAllOrderAdapterCallBack = selectAllOrderAdapterCallBack;
  }
}
