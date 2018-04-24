package com.jiajia.badou.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jiajia.badou.R;

/**
 * Created by Administrator on 2017/3/2 0002.
 */

public class UserCenterItemView extends LinearLayout {

  @BindView(R.id.img_logo) ImageView imgLogo;
  @BindView(R.id.tv_title) TextView tvTitle;
  @BindView(R.id.img_arrow) ImageView imgArrow;
  @BindView(R.id.tv_left) TextView tvLeft;
  @BindView(R.id.line) View line;

  private View contentView;
  private TypedArray typedArray;
  private int imgId;
  private int arrowImgId;
  private String titles;
  private boolean bottomLineVisiable;
  private String leftStr;
  private boolean arrow_visiable;
  private boolean left_text_visiable;
  private TextView tv_left;

  public UserCenterItemView(Context context) {
    this(context, null);
  }

  public UserCenterItemView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public UserCenterItemView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    contentView = LayoutInflater.from(context).inflate(R.layout.yq_usercorner_item, this, true);
    ButterKnife.bind(contentView);
    typedArray =
        context.obtainStyledAttributes(attrs, R.styleable.UserCenterItemView, defStyleAttr, 0);
    imgId = typedArray.getResourceId(R.styleable.UserCenterItemView_imgid, imgId);
    titles = typedArray.getString(R.styleable.UserCenterItemView_corner_titles);
    arrowImgId = typedArray.getResourceId(R.styleable.UserCenterItemView_arrowimgid, arrowImgId);
    bottomLineVisiable = typedArray.getBoolean(R.styleable.UserCenterItemView_bottomline_visiable,
        bottomLineVisiable);
    arrow_visiable =
        typedArray.getBoolean(R.styleable.UserCenterItemView_arrow_visiable, arrow_visiable);
    left_text_visiable = typedArray.getBoolean(R.styleable.UserCenterItemView_left_text_visiable,
        left_text_visiable);
    leftStr = typedArray.getString(R.styleable.UserCenterItemView_left_text);
    typedArray.recycle();
    initView();
  }

  private void initView() {
    imgLogo.setBackgroundResource(imgId);
    tvTitle.setText(titles);
    imgArrow.setBackgroundResource(arrowImgId);
    tv_left = contentView.findViewById(R.id.tv_left);

    if (!bottomLineVisiable) {
      line.setVisibility(GONE);
    } else {
      line.setVisibility(VISIBLE);
    }
    if (!arrow_visiable) {
      imgArrow.setVisibility(GONE);
    } else {
      imgArrow.setVisibility(VISIBLE);
    }
    if (!left_text_visiable) {
      tv_left.setVisibility(GONE);
    } else {
      tv_left.setVisibility(VISIBLE);
    }
  }

  public void setImgId(int imgId) {
    this.imgId = imgId;
    imgLogo.setBackgroundResource(imgId);
  }

  public void setArrowImgId(int arrowImgId) {
    this.arrowImgId = arrowImgId;
    imgArrow.setBackgroundResource(arrowImgId);
  }

  public void setTitles(String titles) {
    this.titles = titles;
    tvTitle.setText(titles);
  }

  public void setLeftStr(String leftStr) {
    this.leftStr = leftStr;
    tvLeft.setText(leftStr);
  }

  public void setLeftTextVisiable(boolean left_text_visiable) {
    if (!left_text_visiable) {
      tv_left.setVisibility(GONE);
    } else {
      tv_left.setVisibility(VISIBLE);
    }
  }

  public void setBottomlineVisiable(boolean bottomline_visiable) {
    this.bottomLineVisiable = bottomline_visiable;
    if (!bottomline_visiable) {
      line.setVisibility(GONE);
    } else {
      line.setVisibility(VISIBLE);
    }
  }
}
