package com.example.cabbage.utils;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.cabbage.network.SurveyInfo;

/**
 * Author: xiemugan
 * Date: 2020/8/22
 * Description:
 **/
public class UIUtils {
    public static final String separator = "/";
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
}
