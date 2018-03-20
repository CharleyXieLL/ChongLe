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
import com.allen.library.SuperTextView;
import com.jiajia.badou.R;
import com.jiajia.badou.bean.PetPrimpBean;
import java.util.List;

/**
 * Created by Lei on 2018/3/13.
 */
public class PetPrimpAdapter extends RecyclerView.Adapter<PetPrimpAdapter.ViewHolder> {

  private List<PetPrimpBean> mDatas;
  private LayoutInflater mInflater;
  private Context context;

  public PetPrimpAdapter(Context mContext, List<PetPrimpBean> mDatas) {
    this.context = mContext;
    this.mDatas = mDatas;
    mInflater = LayoutInflater.from(mContext);
  }

  public List<PetPrimpBean> getDatas() {
    return mDatas;
  }

  public PetPrimpAdapter setDatas(List<PetPrimpBean> datas) {
    mDatas = datas;
    return this;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(mInflater.inflate(R.layout.item_pet_primp, parent, false));
  }

  @SuppressLint("SetTextI18n") @Override public void onBindViewHolder(ViewHolder holder,
      @SuppressLint("RecyclerView") final int position) {
    PetPrimpBean petPrimpBean = mDatas.get(position);
    //Glide.with(context).load(R.mipmap.ic_launcher).into(holder.img);
    holder.tvAddress.setText(petPrimpBean.getAddress1());
    holder.tvAddress2.setText(petPrimpBean.getAddress2());
    holder.tvTitle.setText(petPrimpBean.getTitle());
    holder.tvDistance.setText(petPrimpBean.getDistance());
    holder.tvPhone.setText(petPrimpBean.getPhone());
    holder.layoutContent.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        petPrimpAdapterCallBack.onClick(position);
      }
    });
  }

  public void addAll(List<PetPrimpBean> datas) {
    mDatas.addAll(datas);
    notifyDataSetChanged();
  }

  public void clear() {
    mDatas.clear();
    notifyDataSetChanged();
  }

  public void addNewAll(List<PetPrimpBean> datas) {
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
    @BindView(R.id.img_item_pet_primp) ImageView img;
    @BindView(R.id.tv_item_pet_primp_title) TextView tvTitle;
    @BindView(R.id.tv_item_pet_primp_address) TextView tvAddress;
    @BindView(R.id.tv_item_pet_primp_address2) TextView tvAddress2;
    @BindView(R.id.tv_item_pet_primp_distance) TextView tvDistance;
    @BindView(R.id.tv_item_pet_primp_phone) TextView tvPhone;
    @BindView(R.id.super_tv_item_pet_primp_submit) SuperTextView superTvSubmit;
    @BindView(R.id.layout_item_pet_primp_content) LinearLayout layoutContent;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  public interface PetPrimpAdapterCallBack {
    void onClick(int position);
  }

  private PetPrimpAdapterCallBack petPrimpAdapterCallBack;

  public void setPetPrimpAdapterCallBack(PetPrimpAdapterCallBack petPrimpAdapterCallBack) {
    this.petPrimpAdapterCallBack = petPrimpAdapterCallBack;
  }
}
