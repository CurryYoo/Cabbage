package com.example.cabbage.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.example.cabbage.R;
import com.example.cabbage.network.HelpInfo;
import com.example.cabbage.network.HttpRequest;
import com.example.cabbage.network.ResultInfo;
import com.example.cabbage.network.SurveyInfo;
import com.example.cabbage.view.CustomAttributeView;
import com.example.cabbage.view.InfoBottomDialog;
import com.google.gson.JsonObject;

import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

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

    public static String getFinalKey(Map<String, Integer> map, String keyName) {
        if (map.containsKey(keyName) && map.get(keyName) != null) {
            int num = map.get(keyName) + 1;
            map.put(keyName, num);
            return keyName + num;
        } else {
            return "";
        }
    }
}
