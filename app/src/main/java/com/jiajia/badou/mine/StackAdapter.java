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
import java.util.Arrays;
import java.util.List;

/**
 * Created by CJJ on 2017/3/7.
 */

public class StackAdapter extends RecyclerView.Adapter<StackAdapter.ViewHolder> {

  private LayoutInflater inflater;
  private List<String> datas;
  private Context context;
  private List<Integer> imageUrls =
      Arrays.asList(R.mipmap.xm2, R.mipmap.xm3, R.mipmap.xm4, R.mipmap.xm5, R.mipmap.xm6,
          R.mipmap.xm7, R.mipmap.xm1, R.mipmap.xm8, R.mipmap.xm9, R.mipmap.xm1, R.mipmap.xm2,
          R.mipmap.xm3, R.mipmap.xm4, R.mipmap.xm5, R.mipmap.xm6);

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
    Glide.with(context).load(imageUrls.get(position)).into(holder.cover);
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
          Toast.makeText(context.getApplicationContext(), String.valueOf(getAdapterPosition()),
              Toast.LENGTH_SHORT).show();
        }
      });
    }
  }
}