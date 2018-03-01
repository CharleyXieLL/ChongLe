package com.jiajia.badou.util;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.jiajia.presenter.util.Strings;
import com.jiajia.presenter.util.ViewUtil;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by johnwatsondev on 06/03/2017.
 */
public class EditTextUtil {

  public static final Double MAX_AMOUNT = 100000000.0;

  /**
   * 设置EditText中Hint的字体大小
   */
  public static void setEditHint(int textSize, String hint, EditText editText) {
    SpannableString ss = new SpannableString(hint);
    // 新建一个属性对象,设置文字的大小
    AbsoluteSizeSpan ass = new AbsoluteSizeSpan(textSize, true);
    // 附加属性到文本
    ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    // 设置hint
    editText.setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失
  }

  /**
   * 验卷，银行卡格式
   */
  public static void bankCardNumAddSpace(final EditText mEditText) {
    mEditText.addTextChangedListener(new TextWatcher() {
      int beforeTextLength = 0;
      int onTextLength = 0;
      boolean isChanged = false;

      /*
       * 记录光标的位置
       */ int location = 0;
      private char[] tempChar;
      private StringBuffer buffer = new StringBuffer();
      int konggeNumberB = 0;

      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        beforeTextLength = s.length();
        if (buffer.length() > 0) {
          buffer.delete(0, buffer.length());
        }
        konggeNumberB = 0;
        for (int i = 0; i < s.length(); i++) {
          if (s.charAt(i) == ' ') {
            konggeNumberB++;
          }
        }
      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
        onTextLength = s.length();
        buffer.append(s.toString());
        if (onTextLength == beforeTextLength || onTextLength <= 3 || isChanged) {
          isChanged = false;
          return;
        }
        isChanged = true;
      }

      @Override public void afterTextChanged(Editable s) {
        if (isChanged) {
          location = mEditText.getSelectionEnd();
          int index = 0;
          while (index < buffer.length()) {
            if (buffer.charAt(index) == ' ') {
              buffer.deleteCharAt(index);
            } else {
              index++;
            }
          }

          index = 0;
          int konggeNumberC = 0;
          while (index < buffer.length()) {
            /*
             * 银行卡
             */
            if ((index == 4 || index == 9 || index == 14 || index == 19)) {
              buffer.insert(index, ' ');
              konggeNumberC++;
            }
            index++;
          }

          if (konggeNumberC > konggeNumberB) {
            location += (konggeNumberC - konggeNumberB);
          }

          tempChar = new char[buffer.length()];
          buffer.getChars(0, buffer.length(), tempChar, 0);
          String str = buffer.toString();
          if (location > str.length()) {
            location = str.length();
          } else if (location < 0) {
            location = 0;
          }

          mEditText.setText(str);
          Editable etable = mEditText.getText();
          Selection.setSelection(etable, location);
          isChanged = false;
        }
      }
    });
  }

  /**
   * 获取本息和
   */
  public static String getCapitalAddInterests(double amount, int days, double annulRate) {
    int years = days / 365;

    int day = days - years * 365;

    double capitalAddInterest = 0;

    if (years > 0) {
      for (int i = 0; i < years; i++) {

        if (i == 0) {
          capitalAddInterest = amount + (amount * 365 * annulRate) / 365;
        } else {
          capitalAddInterest = capitalAddInterest + (capitalAddInterest * 365 * annulRate) / 365;
        }
      }
      capitalAddInterest = capitalAddInterest + (capitalAddInterest * day * annulRate) / 365;
    } else {
      capitalAddInterest = amount + (amount * day * annulRate) / 365;
    }

    double capital = ViewUtil.decimalFormat(2, capitalAddInterest);

    DecimalFormat decimalFormat = new DecimalFormat("0.00");

    return String.valueOf(decimalFormat.format(capital));
  }

  /**
   * 为EditText增加删除功能
   */
  public static void setDeleteEditText(final ImageView imageView, final EditText editText) {
    imageView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        editText.setText("");
        imageView.setVisibility(View.GONE);
      }
    });
    editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override public void onFocusChange(View v, boolean hasFocus) {
        String editString = editText.getText().toString();
        if (hasFocus) {
          if (!Strings.isNullOrEmpty(editString)) {
            imageView.setVisibility(View.VISIBLE);
          } else {
            imageView.setVisibility(View.GONE);
          }
        } else {
          imageView.setVisibility(View.GONE);
        }
      }
    });
  }

  /**
   * 为EditText增加删除功能
   */
  public static void setDeleteEditTextAddTextChanged(final ImageView imageView,
      final EditText editText) {
    imageView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        editText.setText("");
        imageView.setVisibility(View.GONE);
      }
    });
    editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override public void onFocusChange(View v, boolean hasFocus) {
        String editString = editText.getText().toString();
        if (hasFocus) {
          if (!Strings.isNullOrEmpty(editString)) {
            imageView.setVisibility(View.VISIBLE);
          } else {
            imageView.setVisibility(View.GONE);
          }
        } else {
          imageView.setVisibility(View.GONE);
        }
      }
    });
    editText.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override public void afterTextChanged(Editable s) {
        if (s.length() > 0) {
          imageView.setVisibility(View.VISIBLE);
        } else {
          imageView.setVisibility(View.GONE);
        }
      }
    });
  }

  /**
   * 为EditText增加删除和搜索功能
   */
  public static void setDeleteEditTextAddTextChangedSearch(final ImageView imageView,
      final EditText editText, final View view) {
    imageView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        editText.setText("");
        imageView.setVisibility(View.GONE);
        view.setVisibility(View.GONE);
      }
    });
    editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override public void onFocusChange(View v, boolean hasFocus) {
        String editString = editText.getText().toString();
        if (hasFocus) {
          if (!Strings.isNullOrEmpty(editString)) {
            imageView.setVisibility(View.VISIBLE);
          } else {
            imageView.setVisibility(View.GONE);
          }
        } else {
          imageView.setVisibility(View.GONE);
        }
      }
    });
    editText.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override public void afterTextChanged(Editable s) {
        if (s.length() > 0) {
          imageView.setVisibility(View.VISIBLE);
          view.setVisibility(View.VISIBLE);
        } else {
          imageView.setVisibility(View.GONE);
          view.setVisibility(View.GONE);
        }
      }
    });
  }

  /**
   * 设置 EditText 输入限制
   * 年利率输入限制
   */
  public static void setEditFilter(final EditText editText) {
    InputFilter[] filterArray = new InputFilter[1];
    filterArray[0] = new InputFilter() {
      @Override
      public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart,
          int dend) {
        String inputStr = editText.getText().toString().trim();
        char[] c = inputStr.toCharArray();
        // 不能一直输入 0
        if (inputStr.length() > 0 && c[0] == '0') {
          if (source.equals("0")) {
            return "";
          }
        }

        if (inputStr.length() == 1 && c[0] == '0') {
          if (!source.equals(".")) {
            return "";
          }
        }

        if (inputStr.length() > 2 && inputStr.contains(".")) {
          int dotIndex = 10000;
          for (int i = 0; i < inputStr.length(); i++) {
            if (c[i] == '.') {
              dotIndex = i;
            }
          }
          if (inputStr.substring(dotIndex + 1, inputStr.length()).length() > 0) {
            return "";
          }
        }

        if (inputStr.length() == 0) {
          if (source.equals(".")) {
            return "";
          }
        }

        // 利率不能高于 24%
        if (inputStr.length() > 0) {
          if (inputStr.contains(".") && source.equals(".")) {
            return "";
          }
          if (c[0] != '.' && ViewUtil.isLegal(String.valueOf(source))) {
            if (Double.parseDouble(inputStr + source) > 24) {
              return "";
            }
          }
        }

        // 输入长度不得大于 6
        if (inputStr.length() > 6) {
          return "";
        }
        return source;
      }
    };
    editText.setFilters(filterArray);
  }

  /**
   * 普通的金额输入限制
   */
  public static void setNormalEditFilter(final EditText editText) {
    InputFilter[] filterArrayAmount = new InputFilter[1];
    filterArrayAmount[0] = new InputFilter() {
      @Override
      public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart,
          int dend) {
        String inputStr = editText.getText().toString().trim();
        if (inputStr.length() == 0 && source.equals("0")) {
          return "";
        }
        if (inputStr.length() == 0 && source.equals(".")) {
          return "";
        }
        // 输入长度不得大于 7
        if (inputStr.length() > 7) {
          return "";
        }
        if (dstart == 0 && source.equals("0")) {
          return "";
        }
        return source;
      }
    };
    editText.setFilters(filterArrayAmount);
  }

  public static void setNormalAmountEditFilter(final EditText editText) {
    InputFilter[] filterArray = new InputFilter[1];
    filterArray[0] = new InputFilter() {
      @Override
      public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart,
          int dend) {
        String inputStr;
        inputStr = editText.getText().toString().trim();
        char[] c = inputStr.toCharArray();
        // 不能一直输入 0
        if (inputStr.length() > 0 && c[0] == '0') {
          if (source.equals("0")) {
            return "";
          }
        }

        if (inputStr.length() == 1 && c[0] == '0') {
          if (!source.equals(".")) {
            return "";
          }
        }

        if (inputStr.length() > 3 && inputStr.contains(".")) {
          int dotIndex = 10000;
          for (int i = 0; i < inputStr.length(); i++) {
            if (c[i] == '.') {
              dotIndex = i;
            }
          }
          if (inputStr.substring(dotIndex + 1, inputStr.length()).length() > 1) {
            return "";
          }
        }

        if (inputStr.length() == 0) {
          if (source.equals(".")) {
            return "";
          }
        }

        if (inputStr.length() > 0) {
          if (inputStr.contains(".") && source.equals(".")) {
            return "";
          }
        }

        if (ViewUtil.isLegal(String.valueOf(source))
            && Double.parseDouble(inputStr + source) >= EditTextUtil.MAX_AMOUNT) {
          return "";
        }

        return source;
      }
    };

    editText.setFilters(filterArray);
  }

  /**
   * 检测是否为手机号
   */
  public static boolean isChinaPhoneLegal(String str) {
    String regExp = "^1(3|4|5|7|8)[0-9]\\d{8}$";
    Pattern p = Pattern.compile(regExp);
    Matcher m = p.matcher(str);
    return m.matches();
  }
}