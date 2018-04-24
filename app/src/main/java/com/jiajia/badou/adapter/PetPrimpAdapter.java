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
import com.bumptech.glide.Glide;
import com.jiajia.badou.R;
import com.jiajia.presenter.bean.PetPrimpBean;
import java.util.List;

/**
 * Created by Lei on 2018/3/13.
 */
public class PetPrimpAdapter extends RecyclerView.Adapter<PetPrimpAdapter.ViewHolder> {

  public final static String MEI_RONG = "美容";
  public final static String YI_LIAO = "医疗";
  public final static String JI_YANG = "托管";

  private List<PetPrimpBean> mDatas;
  private LayoutInflater mInflater;
  private Context context;
  private String type;

  private int[] imgs = new int[] { R.mipmap.c_001, R.mipmap.c_002, R.mipmap.c_003, R.mipmap.c_004 };

  public PetPrimpAdapter(Context mContext, List<PetPrimpBean> mDatas, String type) {
    this.context = mContext;
    this.mDatas = mDatas;
    this.type = type;
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
    if (position < imgs.length) {
      Glide.with(context).load(imgs[position]).into(holder.img);
    } else {
      Glide.with(context).load(R.mipmap.c_004).into(holder.img);
    }
    holder.tvAddress.setText(petPrimpBean.getLocation());
    holder.tvAddress2.setText(petPrimpBean.getStore());
    String typeString = null;
    if (type.equals(MEI_RONG)) {
      typeString = "美容中心";
    }
    if (type.equals(JI_YANG)) {
      typeString = "寄养中心";
    }
    if (type.equals(YI_LIAO)) {
      typeString = "医疗中心";
    }
    holder.tvTitle.setText(petPrimpBean.getLocation() + typeString);
    holder.tvDistance.setText("2.2km");
    holder.tvPhone.setText(petPrimpBean.getQq());
    holder.superTvSubmit.setOnClickListener(new View.OnClickListener() {
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
