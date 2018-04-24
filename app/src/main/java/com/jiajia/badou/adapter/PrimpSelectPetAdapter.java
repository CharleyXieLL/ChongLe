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
import com.jiajia.presenter.bean.mine.SelectPetsByOwnerBean;
import java.util.List;

/**
 * Created by Lei on 2018/4/24.
 */
public class PrimpSelectPetAdapter extends RecyclerView.Adapter<PrimpSelectPetAdapter.ViewHolder> {

  private List<SelectPetsByOwnerBean> mDatas;
  private LayoutInflater mInflater;
  private Context context;

  public PrimpSelectPetAdapter(Context mContext, List<SelectPetsByOwnerBean> mDatas) {
    this.context = mContext;
    this.mDatas = mDatas;
    mInflater = LayoutInflater.from(mContext);
  }

  public List<SelectPetsByOwnerBean> getDatas() {
    return mDatas;
  }

  public PrimpSelectPetAdapter setDatas(List<SelectPetsByOwnerBean> datas) {
    mDatas = datas;
    return this;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(mInflater.inflate(R.layout.item_select_pet, parent, false));
  }

  @SuppressLint("SetTextI18n") @Override public void onBindViewHolder(ViewHolder holder,
      @SuppressLint("RecyclerView") final int position) {
    SelectPetsByOwnerBean selectPetsByOwnerBean = mDatas.get(position);
    Glide.with(context).load(selectPetsByOwnerBean.getPet_img()).into(holder.imgItemPrimpSelectPet);
    holder.tvItemPrimpSelectPetName.setText(selectPetsByOwnerBean.getPet_name());
    if (selectPetsByOwnerBean.isCheck()) {
      holder.imgItemPrimpSelectPetCheckTag.setVisibility(View.VISIBLE);
    } else {
      holder.imgItemPrimpSelectPetCheckTag.setVisibility(View.GONE);
    }
    holder.layoutItemSelectPetContent.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        setItemCheck(position);
        primpSelectPetAdapterCallBack.onClick(position);
      }
    });
  }

  public void addAll(List<SelectPetsByOwnerBean> datas) {
    mDatas.addAll(datas);
    if (datas.size() == 1) {
      mDatas.get(0).setCheck(true);
    }
    notifyDataSetChanged();
  }

  public void clear() {
    mDatas.clear();
    notifyDataSetChanged();
  }

  public void addNewAll(List<SelectPetsByOwnerBean> datas) {
    mDatas.clear();
    mDatas.addAll(datas);
    notifyDataSetChanged();
  }

  private void setItemCheck(int position) {
    SelectPetsByOwnerBean selectPetsByOwnerBean = mDatas.get(position);
    for (int i = 0; i < mDatas.size(); i++) {
      mDatas.get(i).setCheck(false);
    }
    for (int i = 0; i < mDatas.size(); i++) {
      if (!selectPetsByOwnerBean.isCheck()) {
        mDatas.get(position).setCheck(true);
      }
    }
    notifyDataSetChanged();
  }

  @Override public int getItemCount() {
    return mDatas != null ? mDatas.size() : 0;
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.img_item_primp_select_pet) ImageView imgItemPrimpSelectPet;
    @BindView(R.id.img_item_primp_select_pet_check_tag) ImageView imgItemPrimpSelectPetCheckTag;
    @BindView(R.id.tv_item_primp_select_pet_name) TextView tvItemPrimpSelectPetName;
    @BindView(R.id.layout_item_select_pet_content) LinearLayout layoutItemSelectPetContent;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  public interface PrimpSelectPetAdapterCallBack {
    void onClick(int position);
  }

  private PrimpSelectPetAdapterCallBack primpSelectPetAdapterCallBack;

  public void setPrimpSelectPetAdapterCallBack(
      PrimpSelectPetAdapterCallBack primpSelectPetAdapterCallBack) {
    this.primpSelectPetAdapterCallBack = primpSelectPetAdapterCallBack;
  }
}
