package com.jiajia.badou.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.jiajia.badou.R;
import com.jiajia.presenter.bean.PetPrimpDetailListBean;
import com.jiajia.presenter.util.ViewUtil;
import java.util.List;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Lei on 2018/4/24.
 */
public class PetPrimpDetailAdapter extends RecyclerView.Adapter<PetPrimpDetailAdapter.ViewHolder> {

  public static final int NO_DATA = 100;

  private List<PetPrimpDetailListBean> mDatas;
  private LayoutInflater mInflater;
  private Context context;

  private String type;

  private int[] imgMeiRong =
      new int[] { R.mipmap.jieya, R.mipmap.meirongzaoxing, R.mipmap.jiesong };
  private int[] imgYiLiao = new int[] { R.mipmap.waike, R.mipmap.yanke, R.mipmap.yimiao };

  public PetPrimpDetailAdapter(Context mContext, List<PetPrimpDetailListBean> mDatas, String type) {
    this.context = mContext;
    this.mDatas = mDatas;
    this.type = type;
    mInflater = LayoutInflater.from(mContext);
  }

  public List<PetPrimpDetailListBean> getDatas() {
    return mDatas;
  }

  public PetPrimpDetailAdapter setDatas(List<PetPrimpDetailListBean> datas) {
    mDatas = datas;
    return this;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(mInflater.inflate(R.layout.item_primp_detail, parent, false));
  }

  @SuppressLint("SetTextI18n") @Override public void onBindViewHolder(ViewHolder holder,
      @SuppressLint("RecyclerView") final int position) {
    PetPrimpDetailListBean petPrimpDetailListBean = mDatas.get(position);
    if (type.equals(PetPrimpAdapter.MEI_RONG)) {
      if (position < imgMeiRong.length) {
        Glide.with(context)
            .load(imgMeiRong[position])
            .bitmapTransform(
                new RoundedCornersTransformation(context, ViewUtil.dp2px(context, 8), 0,
                    RoundedCornersTransformation.CornerType.ALL))
            .into(holder.imgItemPetPrimpDetail);
      } else {
        Glide.with(context)
            .load(R.mipmap.jiesong)
            .bitmapTransform(
                new RoundedCornersTransformation(context, ViewUtil.dp2px(context, 8), 0,
                    RoundedCornersTransformation.CornerType.ALL))
            .into(holder.imgItemPetPrimpDetail);
      }
    }
    if (type.equals(PetPrimpAdapter.YI_LIAO)) {
      if (position < imgYiLiao.length) {
        Glide.with(context)
            .load(imgYiLiao[position])
            .bitmapTransform(
                new RoundedCornersTransformation(context, ViewUtil.dp2px(context, 8), 0,
                    RoundedCornersTransformation.CornerType.ALL))
            .into(holder.imgItemPetPrimpDetail);
      } else {
        Glide.with(context)
            .load(R.mipmap.waike)
            .bitmapTransform(
                new RoundedCornersTransformation(context, ViewUtil.dp2px(context, 8), 0,
                    RoundedCornersTransformation.CornerType.ALL))
            .into(holder.imgItemPetPrimpDetail);
      }
    }
    if (petPrimpDetailListBean.isCheck()) {
      holder.imgTag.setVisibility(View.VISIBLE);
    } else {
      holder.imgTag.setVisibility(View.GONE);
    }
    holder.relatItemPetPrimpDetail.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        setItemCheck(position);
        petPrimpAdapterCallBack.onClick(position);
      }
    });
  }

  public void addAll(List<PetPrimpDetailListBean> datas) {
    mDatas.addAll(datas);
    notifyDataSetChanged();
  }

  public void clear() {
    mDatas.clear();
    notifyDataSetChanged();
  }

  public void addNewAll(List<PetPrimpDetailListBean> datas) {
    mDatas.clear();
    mDatas.addAll(datas);
    notifyDataSetChanged();
  }

  private void setItemCheck(int position) {
    PetPrimpDetailListBean petPrimpDetailListBean = mDatas.get(position);
    for (int i = 0; i < mDatas.size(); i++) {
      mDatas.get(i).setCheck(false);
    }
    for (int i = 0; i < mDatas.size(); i++) {
      if (!petPrimpDetailListBean.isCheck()) {
        mDatas.get(position).setCheck(true);
      }
    }
    notifyDataSetChanged();
  }

  public int getCheckPosition() {
    int position = NO_DATA;
    for (int i = 0; i < mDatas.size(); i++) {
      if (mDatas.get(i).isCheck()) {
        position = i;
      }
    }
    return position;
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
    @BindView(R.id.img_item_pet_primp_detail) ImageView imgItemPetPrimpDetail;
    @BindView(R.id.relat_item_pet_primp_detail) RelativeLayout relatItemPetPrimpDetail;
    @BindView(R.id.img_item_pet_primp_detail_tag) ImageView imgTag;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  public interface PetPrimpDetailAdapterCallBack {
    void onClick(int position);
  }

  private PetPrimpDetailAdapterCallBack petPrimpAdapterCallBack;

  public void setPetPrimpDetailAdapterCallBack(
      PetPrimpDetailAdapterCallBack petPrimpAdapterCallBack) {
    this.petPrimpAdapterCallBack = petPrimpAdapterCallBack;
  }
}
