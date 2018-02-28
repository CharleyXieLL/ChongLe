/**
 * @author Doctor Gero
 * @version V1.0
 * @Date 2014年10月24日 下午3:12:10
 * @Title todo
 * @Description todo
 */

package com.jiajia.badou.adapter;

import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author Doctor Gero
 * @version V1.0
 * @Date 2014年10月24日 下午3:12:10
 * @Title todo
 * @Description
 */
public abstract class AdapterHelper<T> implements SpinnerAdapter, ListAdapter, Filterable {

  private final DataSetObservable mDataSetObservable = new DataSetObservable();
  private final Object mLock = new Object();
  protected ArrayList<T> mObjects = new ArrayList<T>();
  protected boolean mNotifyOnChange = true;
  protected LayoutInflater mInflater;
  protected Context mContext;

  public AdapterHelper(Context context) {
    mContext = context;
    mInflater = LayoutInflater.from(context);
  }

  public AdapterHelper(Context context, ArrayList<T> array) {
    mContext = context;
    mInflater = LayoutInflater.from(context);
    addAll(array);
  }

  /**
   * (non-Javadoc)
   *
   * @Description eg:<pre>
   * public View getView(int position, View convertView, ViewGroup parent) {
   * 	if (convertView == null) {
   * 		convertView = mInflater.inflate(layoutId, parent, false);
   * 	}
   * 	ImageView bananaView = ViewHolder.get(convertView, R.id.banana);
   * 	TextView phoneView = ViewHolder.get(convertView, R.id.phone);
   * 	BananaPhone bananaPhone = getItem(position);
   * 	phoneView.setText(bananaPhone.getPhone());
   * 	bananaView.setImageResource(bananaPhone.getBanana());
   * 	return convertView;
   * }
   * </pre>
   * @see android.widget.BaseAdapter#getView(int, View, ViewGroup)
   */
  @Override public abstract View getView(int position, View convertView, ViewGroup parent);

  /**
   * @author Doctor Gero
   * @version V1.0
   * @Date 2014年10月24日 下午3:57:35
   * @Title todo
   * @Description eg:<pre>
   * public View getView(int position, View convertView, ViewGroup parent) {
   * 	if (convertView == null) {
   * 		convertView = mInflater.inflate(layoutId, parent, false);
   * 	}
   * 	ImageView bananaView = ViewHolder.get(convertView, R.id.banana);
   * 	TextView phoneView = ViewHolder.get(convertView, R.id.phone);
   * 	BananaPhone bananaPhone = getItem(position);
   * 	phoneView.setText(bananaPhone.getPhone());
   * 	bananaView.setImageResource(bananaPhone.getBanana());
   * 	return convertView;
   * }
   * </pre>
   */
  protected static class ViewHolder {
    private ViewHolder() {
    }

    // I added a generic return type to reduce the casting noise in client
    // code
    @SuppressWarnings("unchecked") public static <T extends View> T get(View view, int id) {
      SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
      if (viewHolder == null) {
        viewHolder = new SparseArray<>();
        view.setTag(viewHolder);
      }
      View childView = viewHolder.get(id);
      if (childView == null) {
        childView = view.findViewById(id);
        viewHolder.put(id, childView);
      }
      return (T) childView;
    }
  }

  /**
   * @author Doctor Gero
   * @version V1.0
   * @Date 2014年10月21日 上午10:15:24
   * @Description ListView 中其他的View的事件处理接口
   */
  public interface OnClickListItemListener {
    /**
     * @return void
     * @Description ListView 中其他的View的事件处理(eg:Button)
     */
    public void onClickListItem(View view, Object obj, int position);
  }

  private OnClickListItemListener onClickListItem;

  /**
   * @return void
   * @Description 设置当前ListView中的Item OnClick事件
   */
  public void setOnClickListItemListener(OnClickListItemListener onClick) {
    onClickListItem = onClick;
  }

  /**
   * @return void
   * @Description View Item Onclick Event
   */
  protected void setOnClick(View v, final Object obj, final int position) {
    if (onClickListItem != null) {
      v.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          onClickListItem.onClickListItem(v, obj, position);
        }
      });
    }
  }

  public void setNotifyOnChange(boolean notifyOnChange) {
    this.mNotifyOnChange = notifyOnChange;
  }

  public int getCount() {
    return mObjects.size();
  }

  public T getItem(int position) {
    return mObjects.get(position);
  }

  public long getItemId(int position) {
    return position;
  }

  public void add(T object) {
    synchronized (mLock) {
      if (mOriginalValues != null) {
        mOriginalValues.add(object);
      }
      mObjects.add(object);
    }
    if (mNotifyOnChange) notifyDataSetChanged();
  }

  public void addAll(Collection<? extends T> collection) {
    synchronized (mLock) {
      if (mOriginalValues != null) {
        mOriginalValues.addAll(collection);
      }
      mObjects.addAll(collection);
    }
    if (mNotifyOnChange) notifyDataSetChanged();
  }

  public void addAll(T... items) {
    synchronized (mLock) {
      if (mOriginalValues != null) {
        Collections.addAll(mOriginalValues, items);
      }
      Collections.addAll(mObjects, items);
    }
    if (mNotifyOnChange) notifyDataSetChanged();
  }

  public void insert(T object, int index) {
    synchronized (mLock) {
      if (mOriginalValues != null) {
        mOriginalValues.add(index, object);
      }
      mObjects.add(index, object);
    }
    if (mNotifyOnChange) notifyDataSetChanged();
  }

  public void set(T object, int index) {
    synchronized (mLock) {
      if (mOriginalValues != null) {
        mOriginalValues.set(index, object);
      }
      mObjects.set(index, object);
    }
    if (mNotifyOnChange) notifyDataSetChanged();
  }

  public void remove(T object) {
    synchronized (mLock) {
      if (mOriginalValues != null) {
        mOriginalValues.remove(object);
      }
      mObjects.remove(object);
    }
    if (mNotifyOnChange) notifyDataSetChanged();
  }

  public void clear() {
    synchronized (mLock) {
      if (mOriginalValues != null) {
        mOriginalValues.clear();
      }
      mObjects.clear();
    }
    if (mNotifyOnChange) notifyDataSetChanged();
  }

  public void sort(Comparator<? super T> comparator) {
    synchronized (mLock) {
      if (mOriginalValues != null) {
        Collections.sort(mOriginalValues, comparator);
      } else {
        Collections.sort(mObjects, comparator);
      }
    }
    if (mNotifyOnChange) notifyDataSetChanged();
  }

  /********************* Filterable ************************/
  private ArrayList<T> mOriginalValues;
  private SimpleFilter mFilter;

  public Filter getFilter() {
    if (mFilter == null) {
      mFilter = new SimpleFilter();
    }
    return mFilter;
  }

  /**
   * @author Doctor Gero
   * @version V1.0
   * @Date 2014年10月21日 上午10:21:19
   * @Description 简单实现Filter
   */
  private class SimpleFilter extends Filter {
    protected FilterResults performFiltering(CharSequence prefix) {
      FilterResults results = new FilterResults();
      if (mOriginalValues == null) {
        synchronized (mLock) {
          mOriginalValues = new ArrayList<T>(mObjects);
        }
      }
      ArrayList<T> list;
      synchronized (mLock) {
        list = new ArrayList<T>(mOriginalValues);
      }
      if (prefix == null || prefix.length() == 0) {
        results.values = list;
        results.count = list.size();
      } else {
        String prefixString = prefix.toString();
        final int count = list.size();
        final ArrayList<T> newValues = new ArrayList<T>();
        for (int i = 0; i < count; i++) {
          final T value = list.get(i);
          final String valueText = value.toString();
          if (valueText.startsWith(prefixString)) {
            newValues.add(value);
          }
        }
        results.values = newValues;
        results.count = newValues.size();
      }
      return results;
    }

    @SuppressWarnings("unchecked")
    protected void publishResults(CharSequence constraint, FilterResults results) {
      mObjects = (ArrayList<T>) results.values;
      if (results.count > 0) {
        notifyDataSetChanged();
      } else {
        notifyDataSetInvalidated();
      }
    }
  }

  public boolean hasStableIds() {
    return false;
  }

  public void registerDataSetObserver(DataSetObserver observer) {
    mDataSetObservable.registerObserver(observer);
  }

  public void unregisterDataSetObserver(DataSetObserver observer) {
    mDataSetObservable.unregisterObserver(observer);
  }

  /**
   * Notifies the attached observers that the underlying data has been changed
   * and any View reflecting the data set should refresh itself.
   */
  public void notifyDataSetChanged() {
    mDataSetObservable.notifyChanged();
    this.mNotifyOnChange = true;
  }

  /**
   * Notifies the attached observers that the underlying data is no longer
   * valid or available. Once invoked this adapter is no longer valid and
   * should not report further data set changes.
   */
  public void notifyDataSetInvalidated() {
    mDataSetObservable.notifyInvalidated();
  }

  public boolean areAllItemsEnabled() {
    return true;
  }

  public boolean isEnabled(int position) {
    return true;
  }

  public View getDropDownView(int position, View convertView, ViewGroup parent) {
    return getView(position, convertView, parent);
  }

  public int getItemViewType(int position) {
    return 0;
  }

  public int getViewTypeCount() {
    return 1;
  }

  public boolean isEmpty() {
    return mObjects == null || mObjects.size() == 0;
  }
}