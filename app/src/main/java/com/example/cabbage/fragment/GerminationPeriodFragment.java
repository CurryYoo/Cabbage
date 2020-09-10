package com.example.cabbage.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cabbage.R;
import com.example.cabbage.network.HttpRequest;
import com.example.cabbage.network.ResultInfo;
import com.example.cabbage.network.SurveyInfo;
import com.example.cabbage.view.CountButton;
import com.example.cabbage.view.ExtraAttributeView;
import com.google.gson.JsonObject;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.cabbage.utils.BasicUtil.showDatePickerDialog;
import static com.example.cabbage.utils.StaticVariable.STATUS_COPY;
import static com.example.cabbage.utils.StaticVariable.STATUS_NEW;
import static com.example.cabbage.utils.StaticVariable.STATUS_READ;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_GERMINATION;
import static com.example.cabbage.utils.UIUtils.checkIsValid;
import static com.example.cabbage.utils.UIUtils.showBottomHelpDialog;

/**
 * Author:created by Kang on 2020/9/9
 * Email:zyk970512@163.com
 * Annotation:发芽期
 */
public class GerminationPeriodFragment extends Fragment {

    @BindView(R.id.edt_material_id)
    EditText edtMaterialId;
    @BindView(R.id.edt_material_type)
    EditText edtMaterialType;
    @BindView(R.id.edt_plant_id)
    EditText edtPlantId;
    @BindView(R.id.edt_investigating_time)
    TextView edtInvestigatingTime;
    @BindView(R.id.edt_investigator)
    TextView edtInvestigator;
    @BindView(R.id.layout_basic_info)
    LinearLayout layoutBasicInfo;
    @BindView(R.id.edt_germination_rate)
    EditText edtGerminationRate;
    @BindView(R.id.btn_germination_rate)
    Button btnGerminationRate;
    @BindView(R.id.layout_custom_attribute)
    LinearLayout layoutCustomAttribute;
    @BindView(R.id.btn_add_attribute)
    CountButton btnAddAttribute;
    @BindView(R.id.btn_add_remark)
    CountButton btnAddRemark;
    @BindView(R.id.btn_upload_data)
    Button btnUploadData;

    private Context self;
    private Unbinder unbinder;
    //必需数据
    private String materialId;
    private String materialType;
    private String plantId;
    private String investigatingTime;
    private int status = STATUS_NEW;
    private String surveyId;
    private String surveyPeriod;
    private String token;
    private int userId;
    private String nickname;
    private ExtraAttributeView extraAttribute = null;//额外性状
    private ExtraAttributeView extraRemark = null;//额外备注
    //spinner选择监听，选择其他是，显示自定义填空
    View.OnClickListener helpClickListener = v -> {
        showBottomHelpDialog(self, getFragmentManager(), token, getResources().getString(R.string.info_germination_rate));
    };
    View.OnClickListener extraAttributeClickListener = v -> {
        switch (v.getId()) {
            case R.id.btn_add_attribute:
                addExtraAttribute(btnAddAttribute, layoutCustomAttribute, "spare1");
                break;
            case R.id.btn_add_remark:
                addExtraAttribute(btnAddRemark, layoutCustomAttribute, "spare2");
                break;
        }
    };
    View.OnClickListener submitClickListener = v -> {
        if (checkIsValid(edtPlantId)) {
            Toast.makeText(self, R.string.check_required, Toast.LENGTH_SHORT).show();
        } else {
            showDialog(SURVEY_PERIOD_GERMINATION);
        }
    };

    public static GerminationPeriodFragment newInstance() {
        return new GerminationPeriodFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_germination_period, container, false);
        self = getContext();
        unbinder = ButterKnife.bind(this, view);
        //验证用户
        SharedPreferences sp = self.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        token = sp.getString("token", "");
        userId = sp.getInt("userId", 1);
        nickname = sp.getString("nickname", "");

        switch (status) {
            case STATUS_NEW:
                initView(true);
                initBasicInfo("");
                break;
            case STATUS_READ:
                initView(false);
//                initMaps();
                initBasicInfo(plantId);
                initData(surveyPeriod);
//                initPictures();
                break;
            case STATUS_COPY:
                initView(true);
//                initMaps();
                initBasicInfo("");
                initData(surveyPeriod);
                //复制粘贴暂不支持图片
                break;
            default:
                break;
        }

        return view;
    }

    // 初始化基本数据
    private void initBasicInfo(String plantId) {
        // 展示基本信息
        edtMaterialId.setText(materialId);
        edtMaterialType.setText(materialType);
        edtPlantId.setText(plantId);
        edtInvestigatingTime.setText(investigatingTime);
        edtInvestigatingTime.setOnClickListener(v -> {
            showDatePickerDialog(self, edtInvestigatingTime);
        });
        edtInvestigator.setText(nickname);

    }

    private void initView(boolean editable) {
        //显示底部提示弹出框
        btnGerminationRate.setOnClickListener(helpClickListener);

        //额外属性和备注
        btnAddAttribute.setCount(1);
        btnAddRemark.setCount(1);
        btnAddAttribute.setOnClickListener(extraAttributeClickListener);
        btnAddRemark.setOnClickListener(extraAttributeClickListener);

        //提交按钮
        if (editable) {
            btnUploadData.setOnClickListener(submitClickListener);
        } else {
            btnUploadData.setVisibility(View.GONE);
        }
    }

    //弹出是否上传dialog
    private void showDialog(String surveyPeriod) {
        final SweetAlertDialog saveDialog = new SweetAlertDialog(self, SweetAlertDialog.NORMAL_TYPE)
                .setContentText(self.getResources().getString(R.string.upload_data_tip))
                .setConfirmText(self.getResources().getString(R.string.confirm))
                .setCancelText(self.getResources().getString(R.string.cancel))
                .setConfirmClickListener(sweetAlertDialog -> {
                    uploadPeriodData(surveyPeriod);
                    sweetAlertDialog.dismissWithAnimation();
                });
        saveDialog.setCancelClickListener(SweetAlertDialog::dismissWithAnimation);
        saveDialog.show();
    }

    // 根据时期，更新服务器数据
    private void uploadPeriodData(String surveyPeriod) {
        try {
            String mPeriodData = getPeriodData();
            HttpRequest.requestAddSurveyData(token, surveyPeriod, mPeriodData, new HttpRequest.IResultCallback() {
                @Override
                public void onResponse(ResultInfo resultInfo) {
                    if (resultInfo.code == 200 && resultInfo.message.equals(getString(R.string.option_success))) {
                        Toast.makeText(self, R.string.update_success, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(self, R.string.update_fail, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure() {
                    Toast.makeText(self, R.string.update_fail, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getPeriodData() {
        //成熟期
        JsonObject jsonObject = getBasicInfoData();

        String germinationRateString = edtGerminationRate.getText().toString();

        //额外属性
        String extraAttributeString = "";
        String extraRemarkString = "";
        if (extraAttribute != null) {
            extraAttributeString = extraAttribute.getContent();
        }
        if (extraRemark != null) {
            extraRemarkString = extraRemark.getContent();
        }

        jsonObject.addProperty("germinationRate", germinationRateString);
        jsonObject.addProperty("spare1", extraAttributeString);
        jsonObject.addProperty("spare2", extraRemarkString);

        return jsonObject.toString();
    }


    private JsonObject getBasicInfoData() {
        String plantId = edtPlantId.getText().toString();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("materialType", materialType);
        jsonObject.addProperty("materialNumber", materialId);
        jsonObject.addProperty("plantNumber", plantId);
        jsonObject.addProperty("investigatingTime", edtInvestigatingTime.getText().toString());
        jsonObject.addProperty("investigator", nickname);
        jsonObject.addProperty("userId", userId);

        return jsonObject;
    }

    // 初始化网络数据（文本数据）
    private void initData(String surveyPeriod) {
        // 网络请求具体数据
        HttpRequest.getSurveyDataDetailBySurveyId(token, surveyPeriod, surveyId, new HttpRequest.ISurveyCallback() {
            @Override
            public void onResponse(SurveyInfo surveyInfo) {
                updateUI(surveyInfo);
            }

            @Override
            public void onFailure() {
                Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                    Toast.makeText(self, R.string.network_request_wrong, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    // 更新页面中特定时期的数据
    private void updateUI(SurveyInfo surveyInfo) {
        edtGerminationRate.setText(surveyInfo.data.germinationRate);

        updateExtraView(btnAddAttribute, layoutCustomAttribute, "spare1", surveyInfo.data.spare1);
        updateExtraView(btnAddRemark, layoutCustomAttribute, "spare2", surveyInfo.data.spare2);
    }

    private void updateExtraView(CountButton btnAdd, LinearLayout layout, String keyName, String value) {
        if (!TextUtils.isEmpty(value)) {
            initAttributeView(btnAdd, layout, keyName, value);
        }
    }

    private void initAttributeView(CountButton btnAdd, LinearLayout layout, String keyName, String value) {
        btnAdd.subtractCount();
        switch (keyName) {
            case "spare1":
                ExtraAttributeView extraAttributeView = new ExtraAttributeView(self, ExtraAttributeView.TYPE_ATTRIBUTE, getString(R.string.obligate_attribute), keyName);
                Button btnDelete = extraAttributeView.findViewById(R.id.btn_delete);
                extraAttribute = extraAttributeView;
                btnDelete.setOnClickListener(v1 -> {
                    extraAttribute = null;
                    extraAttributeView.removeAllViews();
                    btnAdd.addCount();
                });
                extraAttributeView.setContent(value);
                layout.addView(extraAttributeView);
                break;
            case "spare2":
                ExtraAttributeView extraRemarkView = new ExtraAttributeView(self, ExtraAttributeView.TYPE_ATTRIBUTE, getString(R.string.info_remark), keyName);
                Button btnDelete2 = extraRemarkView.findViewById(R.id.btn_delete);
                extraRemark = extraRemarkView;
                btnDelete2.setOnClickListener(v1 -> {
                    extraRemark = null;
                    extraRemarkView.removeAllViews();
                    btnAdd.addCount();
                });
                extraRemarkView.setContent(value);
                layout.addView(extraRemarkView);
                break;
            default:
                break;
        }
    }

    //添加额外属性
    private void addExtraAttribute(CountButton btnAdd, LinearLayout layout, String keyName) {
        btnAdd.subtractCount();
        switch (keyName) {
            case "spare1":
                ExtraAttributeView extraAttributeView = new ExtraAttributeView(self, ExtraAttributeView.TYPE_ATTRIBUTE, getString(R.string.obligate_attribute), keyName);
                Button btnDelete = extraAttributeView.findViewById(R.id.btn_delete);
                extraAttribute = extraAttributeView;
                btnDelete.setOnClickListener(v1 -> {
                    extraAttribute = null;
                    extraAttributeView.removeAllViews();
                    btnAdd.addCount();
                });
                layout.addView(extraAttributeView);
                break;
            case "spare2":
                ExtraAttributeView extraRemarkView = new ExtraAttributeView(self, ExtraAttributeView.TYPE_ATTRIBUTE, getString(R.string.info_remark), keyName);
                Button btnDelete2 = extraRemarkView.findViewById(R.id.btn_delete);
                extraRemark = extraRemarkView;
                btnDelete2.setOnClickListener(v1 -> {
                    extraRemark = null;
                    extraRemarkView.removeAllViews();
                    btnAdd.addCount();
                });
                layout.addView(extraRemarkView);
                break;
            default:
                break;
        }
    }


    public void setInitValue(String materialId_
            , String materialType_
            , String plantId_
            , String investigatingTime_
            , int status_
            , String surveyId_
            , String surveyPeriod_) {
        materialId = materialId_;
        materialType = materialType_;
        plantId = plantId_;
        investigatingTime = investigatingTime_;
        status = status_;
        surveyId = surveyId_;
        surveyPeriod = surveyPeriod_;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
