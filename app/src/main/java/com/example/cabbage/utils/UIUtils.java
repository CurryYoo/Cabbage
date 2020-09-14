package com.example.cabbage.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.fragment.app.FragmentManager;

import com.example.cabbage.R;
import com.example.cabbage.network.HelpInfo;
import com.example.cabbage.network.HttpRequest;
import com.example.cabbage.view.InfoBottomDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.io.File.separator;

/**
 * Author: xiemugan
 * Date: 2020/8/22
 * Description:
 **/
public class UIUtils {

    public static void setSelection(Spinner spinner, String data) {
        if (TextUtils.isEmpty(data)) {
            return;
        }
        SpinnerAdapter spinnerAdapter = spinner.getAdapter();
        for (int j = 0; j < spinnerAdapter.getCount(); j++) {
            if (spinnerAdapter.getItem(j).toString().equals(data)) {
                spinner.setSelection(j, true);
            } else if (j == spinnerAdapter.getCount() - 1) {

            }
        }
    }

    public static void setSelectionAndText(Spinner spinner, EditText editText, String dataString) {
        if (TextUtils.isEmpty(dataString)) {
            return;
        }
        String[] dataArray = dataString.split(separator);
        setSelection(spinner, dataArray[0]);
        if (dataArray.length > 1) {
            editText.setText(dataArray[1]);
        }
    }

    //调查页面底部帮助对话框
    public static void showBottomHelpDialog(Context context, FragmentManager fragmentManager, String token, String specificCharacter) {
        // 获取数据
        HttpRequest.getMeasurementBySpecificCharacter(token, specificCharacter, new HttpRequest.IHelpCallback() {
            @Override
            public void onResponse(HelpInfo helpInfo) {
                String measurementBasis = "";
                String observationMethod = "";
                if (helpInfo.data != null) {
                    measurementBasis = helpInfo.data.measurementBasis;
                    observationMethod = helpInfo.data.observationMethod;
                }
                String helpText = context.getString(R.string.measure_standard) + measurementBasis + "\n\n" + context.getString(R.string.measure_method) + observationMethod;
                InfoBottomDialog dialog = new InfoBottomDialog();
                dialog.setTxtInfo(helpText);
                dialog.show(fragmentManager);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    //调查页面判断是否显示用户自定义填空
    public static void setVisibilityOfUserDefined(int positionSelect, int positionOther, EditText userDefined) {
        if (positionSelect == positionOther) {
            userDefined.setVisibility(View.VISIBLE);
        } else {
            userDefined.setVisibility(View.GONE);
        }
    }

    // 校验必填数据是否填写
    public static boolean checkIsValid(EditText editText) {
        return TextUtils.isEmpty(editText.getText());
    }

    public static String checkPeriod(String period, String surveyPeriod, String surveyId) {
        return period.equals(surveyPeriod) ? surveyId : "";
    }

    public static String getFinalKey(Map<String, Integer> map, String keyName) {
        if (map.containsKey(keyName) && map.get(keyName) != null) {
            int num = map.get(keyName) + 1;
            map.put(keyName, num);
            return keyName + num;
        } else {
            return "";
        }
    }

    public static void selectPic(Activity activity, int maxTotal, int resultCode) {
        PictureSelectorConfig.initMultiConfig(activity, maxTotal, resultCode);
    }

    /**
     * 隐藏软键盘(只适用于Activity，不适用于Fragment)
     */
    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 隐藏软键盘(可用于Activity，Fragment)
     */
    public static void hideSoftKeyboard(Context context, List<View> viewList) {
        if (viewList == null) return;

        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);

        for (View v : viewList) {
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    public static String getSystemTime() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return formatter.format(curDate);
    }

}
