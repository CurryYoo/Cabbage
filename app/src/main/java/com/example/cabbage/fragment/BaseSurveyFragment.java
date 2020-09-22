package com.example.cabbage.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.cabbage.R;
import com.example.cabbage.network.SurveyInfo;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

/**
 * Author: xiemugan
 * Date: 2020/9/19
 * Description:
 **/
public abstract class BaseSurveyFragment extends Fragment {
    private static ExecutorService executorService = newSingleThreadExecutor();

    public void cache() {
        executorService.execute(() -> {
            File file = new File(getContext().getCacheDir(), "cache.txt");
            try {
                OutputStream outputStream = new FileOutputStream(file);
                String cacheData = getCacheData();
                Log.e("cacheData create", cacheData);
                outputStream.write(cacheData.getBytes());
                outputStream.close();
                SharedPreferences.Editor editor = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE).edit();
                editor.putBoolean("hasCache", true);
                editor.apply();
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getActivity(), getResources().getString(R.string.option_success), Toast.LENGTH_SHORT).show();
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getActivity(), getResources().getString(R.string.option_fail), Toast.LENGTH_SHORT).show();
                    });
                }
            }

        });
    }

    public abstract String getCacheData();
}
