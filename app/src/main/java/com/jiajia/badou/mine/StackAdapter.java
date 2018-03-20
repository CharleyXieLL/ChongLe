package com.jiajia.badou.mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.jiajia.badou.R;
import com.jiajia.presenter.util.ViewUtil;
import java.util.List;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by CJJ on 2017/3/7.
 */

public class StackAdapter extends RecyclerView.Adapter<StackAdapter.ViewHolder> {

  private LayoutInflater inflater;
  private List<String> datas;
  private Context context;

  public StackAdapter(List<String> datas) {
    this.datas = datas;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (inflater == null) {
      context = parent.getContext();
      inflater = LayoutInflater.from(parent.getContext());
    }
    return new ViewHolder(inflater.inflate(R.layout.item_mine_pet_card, parent, false));
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    Glide.with(context)
        .load(datas.get(position))
        .bitmapTransform(new RoundedCornersTransformation(context, ViewUtil.dp2px(context, 7), 0,
            RoundedCornersTransformation.CornerType.TOP))
        .error(R.color.main_check_true)
        .into(holder.cover);
    holder.index.setText(String.valueOf(position + 1));
  }

  @Override public int getItemCount() {
    return datas == null ? 0 : datas.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    ImageView cover;
    TextView index;

    public ViewHolder(View itemView) {
      super(itemView);
      cover = itemView.findViewById(R.id.cover);
      index = itemView.findViewById(R.id.index);
      itemView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          Toast.makeText(context.getApplicationContext(), String.valueOf(getAdapterPosition() + 1),
              Toast.LENGTH_SHORT).show();
        }
      });
    }
  }
}
