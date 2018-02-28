/**
 *
 */
package com.jiajia.badou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jiajia.badou.util.GlobalException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SimpleAdapterHelper<T> extends AdapterHelper<T> {
  private int mResource;
  private LayoutInflater mInflater;
  private SimpleViewHolderFactory<T> mFactory;
  private SimpleViewHolder<T> mHolder;

  public void setNotifyOnChange(boolean notifyOnChange) {
    this.mNotifyOnChange = notifyOnChange;
  }

  public SimpleAdapterHelper(Context context, int resource,
      SimpleViewHolderFactory<T> holderFactory) {
    super(context);
    mResource = resource;
    mFactory = holderFactory;
    mInflater = LayoutInflater.from(context);
  }

  /**
   * @param holder 不能是内部类
   */
  public SimpleAdapterHelper(Context context, int resource, SimpleViewHolder<T> holder) {
    super(context);
    mResource = resource;
    mHolder = holder;
    mInflater = LayoutInflater.from(context);
  }

  private ByteArrayOutputStream bos;

  @SuppressWarnings("unchecked") public SimpleViewHolder<T> copy(SimpleViewHolder<T> t) {
    try {
      if (bos == null) {
        bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(t);
      }
      ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
      ObjectInputStream ois = new ObjectInputStream(bis);
      return (SimpleViewHolder<T>) ois.readObject();
    } catch (Throwable throwable) {
      throw new GlobalException("SimpleViewHolder 不能是内部类,无法读取");
    }
  }

  @SuppressWarnings("unchecked") @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    SimpleViewHolder<T> holder;
    if (convertView == null) {
      convertView = mInflater.inflate(mResource, parent, false);
      holder = mFactory != null ? mFactory.getTag(convertView) : copy(mHolder);
      //ViewUtils.inject(holder, convertView);
      convertView.setTag(holder);
    } else {
      holder = (SimpleViewHolder<T>) convertView.getTag();
    }
    holder.init(getItem(position), position);
    return convertView;
  }

  public static interface SimpleViewHolderFactory<T> {
    SimpleViewHolder<T> getTag(View v);
  }

  /***
   *
   * @param <T>
   */
  public static interface SimpleViewHolder<T> extends Serializable {
    void init(T t, int position);
  }
}
