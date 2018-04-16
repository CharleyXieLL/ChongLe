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
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.jiajia.badou.R;
import com.jiajia.badou.bean.ChongQuanBean;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.List;

/**
 * Created by Lei on 2018/4/16.
 * 宠圈
 */
public class ChongQuanAdapter extends RecyclerView.Adapter<ChongQuanAdapter.ViewHolder> {

  private static final int CLICK_CONTENT_COLLECT = 500;

  private List<ChongQuanBean> mDatas;
  private LayoutInflater mInflater;
  private Context context;

  public ChongQuanAdapter(Context mContext, List<ChongQuanBean> mDatas) {
    this.context = mContext;
    this.mDatas = mDatas;
    mInflater = LayoutInflater.from(mContext);
  }

  public List<ChongQuanBean> getDatas() {
    return mDatas;
  }

  public ChongQuanAdapter setDatas(List<ChongQuanBean> datas) {
    mDatas = datas;
    return this;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(mInflater.inflate(R.layout.ycjt_item_chong_quan, parent, false));
  }

  @SuppressLint("SetTextI18n") @Override public void onBindViewHolder(ViewHolder holder,
      @SuppressLint("RecyclerView") final int position) {
    final ChongQuanBean chongQuanBean = mDatas.get(position);
    Glide.with(context).load(chongQuanBean.getImgAvatar()).into(holder.imgChongquanAvatar);
    Glide.with(context).load(chongQuanBean.getImgContent()).into(holder.imgChongquanContent);
    holder.tvChongquanName.setText(chongQuanBean.getUserName());
    holder.tvChongquanPublishTime.setText(chongQuanBean.getPublishTime());
    holder.tvChongquanTip.setText(chongQuanBean.getTip());
    holder.tvChongquanLook.setText(String.valueOf(chongQuanBean.getSeeCount()));
    holder.tvChongquanAixin.setText(String.valueOf(chongQuanBean.getCollectCount()));
    if (chongQuanBean.isCollect()) {
      holder.imgChongquanCollect.setBackgroundResource(R.mipmap.cl_aixin_red);
    } else {
      holder.imgChongquanCollect.setBackgroundResource(R.mipmap.cl_aixin_blank);
    }
    holder.imgChongquanContent.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        setCollectClickContent(position);
      }
    });
    holder.imgChongquanCollect.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        setCollect(position);
      }
    });
  }

  public void addAll(List<ChongQuanBean> datas) {
    mDatas.addAll(datas);
    notifyDataSetChanged();
  }

  public void clear() {
    mDatas.clear();
    notifyDataSetChanged();
  }

  public void addNewAll(List<ChongQuanBean> datas) {
    mDatas.clear();
    mDatas.addAll(datas);
    notifyDataSetChanged();
  }

  public void setCollect(int position) {
    ChongQuanBean chongQuanBean = mDatas.get(position);
    if (chongQuanBean.isCollect()) {
      chongQuanBean.setCollect(false);
      chongQuanBean.setCollectCount(chongQuanBean.getCollectCount() - 1);
    } else {
      chongQuanBean.setCollect(true);
      chongQuanBean.setCollectCount(chongQuanBean.getCollectCount() + 1);
    }
    notifyDataSetChanged();
  }

  private long mLastClickBackKeyTime;

  public void setCollectClickContent(int position) {
    ChongQuanBean chongQuanBean = mDatas.get(position);
    boolean isProcessed = false;
    if ((System.currentTimeMillis() - mLastClickBackKeyTime) > CLICK_CONTENT_COLLECT) {
      mLastClickBackKeyTime = System.currentTimeMillis();
    } else {
      isProcessed = true;
    }
    if (isProcessed) {
      if (!chongQuanBean.isCollect()) {
        chongQuanBean.setCollect(true);
        chongQuanBean.setCollectCount(chongQuanBean.getCollectCount() + 1);
        notifyDataSetChanged();
      }
    }
  }

  @Override public int getItemCount() {
    return mDatas != null ? mDatas.size() : 0;
  }

  @OnClick({ R.id.img_chongquan_content, R.id.img_chongquan_collect })
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.img_chongquan_content:
        break;
      case R.id.img_chongquan_collect:
        break;
    }
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.img_chongquan_avatar) CircleImageView imgChongquanAvatar;
    @BindView(R.id.tv_chongquan_name) TextView tvChongquanName;
    @BindView(R.id.tv_chongquan_publish_time) TextView tvChongquanPublishTime;
    @BindView(R.id.img_chongquan_content) ImageView imgChongquanContent;
    @BindView(R.id.tv_chongquan_tip) TextView tvChongquanTip;
    @BindView(R.id.tv_chongquan_look) TextView tvChongquanLook;
    @BindView(R.id.tv_chongquan_aixin) TextView tvChongquanAixin;
    @BindView(R.id.img_chongquan_collect) ImageView imgChongquanCollect;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  public interface ChongQuanAdapterCallBack {
    void onClick(int position);
  }

  private ChongQuanAdapterCallBack chongQuanAdapterCallBack;

  public void setChongQuanAdapterCallBack(ChongQuanAdapterCallBack chongQuanAdapterCallBack) {
    this.chongQuanAdapterCallBack = chongQuanAdapterCallBack;
  }
}
