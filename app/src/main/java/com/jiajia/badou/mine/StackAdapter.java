package com.jiajia.badou.mine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.jiajia.badou.R;
import com.jiajia.presenter.bean.mine.SelectPetsByOwnerBean;
import com.jiajia.presenter.util.ViewUtil;
import java.util.ArrayList;
import java.util.List;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Lei on 2017/3/7.
 */

public class StackAdapter extends RecyclerView.Adapter<StackAdapter.ViewHolder> {

  private LayoutInflater inflater;
  private List<SelectPetsByOwnerBean> datas = new ArrayList<>();
  private Context context;

  public StackAdapter(List<SelectPetsByOwnerBean> datas) {
    this.datas.clear();
    this.datas.addAll(datas);
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (inflater == null) {
      context = parent.getContext();
      inflater = LayoutInflater.from(parent.getContext());
    }
    return new ViewHolder(inflater.inflate(R.layout.item_mine_pet_card, parent, false));
  }

  @SuppressLint("SetTextI18n") @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    SelectPetsByOwnerBean selectPetsByOwnerBean = datas.get(position);
    Glide.with(context)
        .load(datas.get(position).getPet_img())
        .bitmapTransform(new RoundedCornersTransformation(context, ViewUtil.dp2px(context, 7), 0,
            RoundedCornersTransformation.CornerType.TOP))
        .error(R.color.main_check_true)
        .into(holder.cover);
    holder.name.setText(selectPetsByOwnerBean.getPet_name());
    holder.type.setText(selectPetsByOwnerBean.getPet_type());
    holder.birthday.setText("生日：2017.05.26");
  }

  @Override public int getItemCount() {
    return datas == null ? 0 : datas.size();
  }

  public void addAll(List<SelectPetsByOwnerBean> datas) {
    datas.addAll(datas);
    notifyDataSetChanged();
  }

  public void clear() {
    datas.clear();
    notifyDataSetChanged();
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    ImageView cover;
    TextView name;
    TextView birthday;
    TextView type;

    public ViewHolder(View itemView) {
      super(itemView);
      cover = itemView.findViewById(R.id.cover);
      name = itemView.findViewById(R.id.tv_item_mine_pet_name);
      birthday = itemView.findViewById(R.id.tv_item_mine_pet_birthday);
      type = itemView.findViewById(R.id.tv_item_mine_pet_type);
      itemView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          stackAdapterCallBack.onStackClick(getAdapterPosition());
        }
      });
    }
  }

  public interface StackAdapterCallBack {
    void onStackClick(int position);
  }

  private StackAdapterCallBack stackAdapterCallBack;

  public void setStackAdapterCallBack(StackAdapterCallBack stackAdapterCallBack) {
    this.stackAdapterCallBack = stackAdapterCallBack;
  }
}
