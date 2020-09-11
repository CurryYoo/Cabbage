package com.example.cabbage.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cabbage.R;
import com.example.cabbage.network.HttpRequest;
import com.example.cabbage.network.NormalInfo;
import com.example.cabbage.network.ResultInfo;
import com.example.cabbage.network.SurveyInfo;
import com.example.cabbage.view.AutoClearEditText;
import com.example.cabbage.view.CountButton;
import com.example.cabbage.view.ExtraAttributeView;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.cabbage.utils.BasicUtil.showDatePickerDialog;
import static com.example.cabbage.utils.StaticVariable.COUNT_EXTRA;
import static com.example.cabbage.utils.StaticVariable.SEPARATOR;
import static com.example.cabbage.utils.StaticVariable.STATUS_COPY;
import static com.example.cabbage.utils.StaticVariable.STATUS_NEW;
import static com.example.cabbage.utils.StaticVariable.STATUS_READ;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_FLOWERING;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_SEED_HARVEST;
import static com.example.cabbage.utils.UIUtils.checkIsValid;
import static com.example.cabbage.utils.UIUtils.setSelectionAndText;
import static com.example.cabbage.utils.UIUtils.setVisibilityOfUserDefined;
import static com.example.cabbage.utils.UIUtils.showBottomHelpDialog;

/**
 * Author:Kang
 * Date:2020/9/10
 * Description:结荚与种子收获期
 */
public class SeedHarvestPeriodFragment extends Fragment {

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
    @BindView(R.id.seed_pod_length)
    Spinner seedPodLength;
    @BindView(R.id.edt_seed_pod_length)
    AutoClearEditText edtSeedPodLength;
    @BindView(R.id.btn_seed_pod_length)
    Button btnSeedPodLength;
    @BindView(R.id.seed_pod_beak_length)
    Spinner seedPodBeakLength;
    @BindView(R.id.edt_seed_pod_beak_length)
    AutoClearEditText edtSeedPodBeakLength;
    @BindView(R.id.btn_seed_pod_beak_length)
    Button btnSeedPodBeakLength;
    @BindView(R.id.seed_size)
    Spinner seedSize;
    @BindView(R.id.edt_seed_size)
    AutoClearEditText edtSeedSize;
    @BindView(R.id.btn_seed_size)
    Button btnSeedSize;
    @BindView(R.id.seed_shape)
    Spinner seedShape;
    @BindView(R.id.edt_seed_shape)
    AutoClearEditText edtSeedShape;
    @BindView(R.id.btn_seed_shape)
    Button btnSeedShape;
    @BindView(R.id.seed_color)
    Spinner seedColor;
    @BindView(R.id.edt_seed_color)
    AutoClearEditText edtSeedColor;
    @BindView(R.id.btn_seed_color)
    Button btnSeedColor;
    @BindView(R.id.edt_seed_rate)
    EditText edtSeedRate;
    @BindView(R.id.btn_seed_rate)
    Button btnSeedRate;
    @BindView(R.id.edt_single_seed_pod_rate)
    EditText edtSingleSeedPodRate;
    @BindView(R.id.btn_single_seed_pod_rate)
    Button btnSingleSeedPodRate;
    @BindView(R.id.edt_petal_count)
    EditText edtPetalCount;
    @BindView(R.id.btn_petal_count)
    Button btnPetalCount;
    @BindView(R.id.self_compatible)
    Spinner selfCompatible;
    @BindView(R.id.edt_self_compatible)
    AutoClearEditText edtSelfCompatible;
    @BindView(R.id.btn_self_compatible)
    Button btnSelfCompatible;
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
    private String surveyPeriod = SURVEY_PERIOD_SEED_HARVEST;
    private String token;
    private int userId;
    private String nickname;
    private ExtraAttributeView extraAttribute = null;//额外性状
    private ExtraAttributeView extraRemark = null;//额外备注
    //spinner选择监听，选择其他是，显示自定义填空
    Spinner.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()) {
                case R.id.seed_shape:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.seed_shape).length - 1, edtSeedShape);
                    break;
                case R.id.seed_color:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.seed_color).length - 1, edtSeedColor);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    View.OnClickListener helpClickListener = v -> {
        switch (v.getId()) {
            case R.id.btn_seed_pod_length:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_seed_pod_length));
                break;
            case R.id.btn_seed_pod_beak_length:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_seed_pod_beak_length));
                break;
            case R.id.btn_seed_size:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_seed_size));
                break;
            case R.id.btn_seed_shape:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_seed_shape));
                break;
            case R.id.btn_seed_color:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_seed_color));
                break;
            case R.id.btn_seed_rate:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_seed_rate));
                break;
            case R.id.btn_single_seed_pod_rate:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_single_seed_pod_rate));
                break;
            case R.id.btn_petal_count:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_petal_count));
                break;
            case R.id.btn_self_compatible:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_self_compatible));
                break;
        }
    };
    View.OnClickListener extraAttributeClickListener = v -> {
        switch (v.getId()) {
            case R.id.btn_add_attribute:
                addExtraAttributeView(btnAddAttribute, layoutCustomAttribute, "spare1", "");
                break;
            case R.id.btn_add_remark:
                addExtraAttributeView(btnAddRemark, layoutCustomAttribute, "spare2", "");
                break;
        }
    };
    View.OnClickListener submitClickListener = v -> {
        if (checkIsValid(edtPlantId)) {
            Toast.makeText(self, R.string.check_required, Toast.LENGTH_SHORT).show();
        } else {
            showDialog();
        }
    };


    public static SeedHarvestPeriodFragment newInstance() {
        return new SeedHarvestPeriodFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seed_harvest_period, container, false);
        self = getContext();
        unbinder = ButterKnife.bind(this, view);
        //验证用户
        SharedPreferences sp = self.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        token = sp.getString("token", "");
        userId = sp.getInt("userId", 1);
        nickname = sp.getString("nickname", "");

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        layoutCustomAttribute.removeAllViews();//清除view，防止重复加载
        initFragment();
    }

    private void initFragment() {
        switch (status) {
            case STATUS_NEW:
                initView(true);
                initBasicInfo("");
                break;
            case STATUS_READ:
                initView(false);
//                initMaps();
                initBasicInfo(plantId);
                initData();
//                initPictures();
                break;
            case STATUS_COPY:
                initView(true);
//                initMaps();
                initBasicInfo("");
                initData();
                break;
            default:
                break;
        }
    }

    private void initView(boolean editable) {

        //判断是否显示自定义填空
        seedShape.setOnItemSelectedListener(onItemSelectedListener);
        seedColor.setOnItemSelectedListener(onItemSelectedListener);
        //显示底部提示弹出框
        btnSeedPodLength.setOnClickListener(helpClickListener);
        btnSeedPodBeakLength.setOnClickListener(helpClickListener);
        btnSeedSize.setOnClickListener(helpClickListener);
        btnSeedShape.setOnClickListener(helpClickListener);
        btnSeedColor.setOnClickListener(helpClickListener);
        btnSeedRate.setOnClickListener(helpClickListener);
        btnSingleSeedPodRate.setOnClickListener(helpClickListener);
        btnPetalCount.setOnClickListener(helpClickListener);
        btnSelfCompatible.setOnClickListener(helpClickListener);

        //额外属性和备注
        btnAddAttribute.setCount(COUNT_EXTRA);
        btnAddRemark.setCount(COUNT_EXTRA);

        //判断是否显示按钮
        if (editable) {
            btnAddAttribute.setOnClickListener(extraAttributeClickListener);
            btnAddRemark.setOnClickListener(extraAttributeClickListener);
            btnUploadData.setOnClickListener(submitClickListener);
        } else {
            btnAddAttribute.setVisibility(View.GONE);
            btnAddRemark.setVisibility(View.GONE);
            btnUploadData.setVisibility(View.GONE);
        }
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

    //弹出是否上传dialog
    private void showDialog() {
        final SweetAlertDialog saveDialog = new SweetAlertDialog(self, SweetAlertDialog.NORMAL_TYPE)
                .setContentText(self.getResources().getString(R.string.upload_data_tip))
                .setConfirmText(self.getResources().getString(R.string.confirm))
                .setCancelText(self.getResources().getString(R.string.cancel))
                .setConfirmClickListener(sweetAlertDialog -> {
                    uploadPeriodData();
                    sweetAlertDialog.dismissWithAnimation();
                });
        saveDialog.setCancelClickListener(SweetAlertDialog::dismissWithAnimation);
        saveDialog.show();
    }

    // 根据时期，更新服务器数据
    private void uploadPeriodData() {
        try {
            String mPeriodData = getPeriodData();
            HttpRequest.requestAddSurveyData(token, surveyPeriod, mPeriodData, new HttpRequest.IResultCallback() {
                @Override
                public void onResponse(ResultInfo resultInfo) {
                    if (resultInfo.code == 200 && resultInfo.message.equals(getString(R.string.option_success))) {
                        String surveyId = resultInfo.data.observationId;
                        uploadPics(surveyId);
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

    private String getPeriodData() {
        //成熟期
        JsonObject jsonObject = getBasicInfoData();

        String seedPodLengthData = seedPodLength.getSelectedItem().toString() + SEPARATOR + edtSeedPodLength.getText();
        String seedPodBeakLengthData = seedPodBeakLength.getSelectedItem().toString() + SEPARATOR + edtSeedPodBeakLength.getText();
        String seedSizeData = seedSize.getSelectedItem().toString() + SEPARATOR + edtSeedSize.getText();
        String seedShapeData = seedShape.getSelectedItem().toString() + SEPARATOR + edtSeedShape.getText();
        String seedColorData = seedColor.getSelectedItem().toString() + SEPARATOR + edtSeedColor.getText();
        String edtSeedRateData = edtSeedRate.getText().toString();
        String edtSingleSeedPodRateData = edtSingleSeedPodRate.getText().toString();
        String edtPetalCountData = edtPetalCount.getText().toString();
        String selfCompatibleData = selfCompatible.getSelectedItem().toString() + SEPARATOR + edtSelfCompatible.getText();

        //额外属性
        String extraAttributeData = "";
        String extraRemarkData = "";
        if (extraAttribute != null) {
            extraAttributeData = extraAttribute.getContent();
        }
        if (extraRemark != null) {
            extraRemarkData = extraRemark.getContent();
        }

        jsonObject.addProperty("seedPodLength", seedPodLengthData);
        jsonObject.addProperty("longBeakOfSeedPod", seedPodBeakLengthData);
        jsonObject.addProperty("seedSize", seedSizeData);
        jsonObject.addProperty("seedShape", seedShapeData);
        jsonObject.addProperty("seedColor", seedColorData);
        jsonObject.addProperty("poddingRate", edtSeedRateData);
        jsonObject.addProperty("numberOfSeeds", edtSingleSeedPodRateData);
        jsonObject.addProperty("seedSettingRate", edtPetalCountData);
        jsonObject.addProperty("selfCompatibility", selfCompatibleData);
        jsonObject.addProperty("spare1", extraAttributeData);
        jsonObject.addProperty("spare2", extraRemarkData);

        return jsonObject.toString();
    }

    // 更新上传图片
    private void uploadPics(String surveyId) {
        Map<String, ArrayList<String>> imageMap;
        imageMap = new HashMap<>();
        for (String specCharacter : imageMap.keySet()) {
            ArrayList<String> images = imageMap.get(specCharacter);
            if (images.isEmpty()) {
                continue;
            }
            for (String imgPath : images) {
                HttpRequest.uploadPicture(token, surveyPeriod, surveyId, specCharacter, imgPath, new HttpRequest.INormalCallback() {
                    @Override
                    public void onResponse(NormalInfo normalInfo) {

                    }

                    @Override
                    public void onFailure() {

                    }
                });
            }
        }
    }

    // 初始化网络数据（文本数据）
    private void initData() {
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
        setSelectionAndText(seedPodLength, edtSeedPodLength, surveyInfo.data.seedPodLength);
        setSelectionAndText(seedPodBeakLength, edtSeedPodBeakLength, surveyInfo.data.longBeakOfSeedPod);
        setSelectionAndText(seedSize, edtSeedSize, surveyInfo.data.seedSize);
        setSelectionAndText(seedShape, edtSeedShape, surveyInfo.data.seedShape);
        setSelectionAndText(seedColor, edtSeedColor, surveyInfo.data.seedColor);
        edtSeedRate.setText(String.valueOf(surveyInfo.data.poddingRate));
        edtSingleSeedPodRate.setText(surveyInfo.data.numberOfSeeds);
        edtPetalCount.setText(String.valueOf(surveyInfo.data.seedSettingRate));
        setSelectionAndText(selfCompatible, edtSelfCompatible, surveyInfo.data.selfCompatibility);

        updateExtraView(btnAddAttribute, layoutCustomAttribute, "spare1", surveyInfo.data.spare1);
        updateExtraView(btnAddRemark, layoutCustomAttribute, "spare2", surveyInfo.data.spare2);
    }

    private void updateExtraView(CountButton btnAdd, LinearLayout layout, String keyName, String value) {
        if (!TextUtils.isEmpty(value)) {
            addExtraAttributeView(btnAdd, layout, keyName, value);
        }
    }

    private void addExtraAttributeView(CountButton btnAdd, LinearLayout layout, String keyName, String value) {
        btnAdd.subtractCount();
        ExtraAttributeView extraAttributeView = new ExtraAttributeView(self, ExtraAttributeView.TYPE_ATTRIBUTE, "spare1");
        switch (keyName) {
            case "spare1":
                extraAttributeView = new ExtraAttributeView(self, ExtraAttributeView.TYPE_ATTRIBUTE, keyName);
                extraAttribute = extraAttributeView;
                break;
            case "spare2":
                extraAttributeView = new ExtraAttributeView(self, ExtraAttributeView.TYPE_REMARK, keyName);
                extraRemark = extraAttributeView;
                break;
            default:
                break;
        }
        Button btnDelete = extraAttributeView.findViewById(R.id.btn_delete);
        if (status == STATUS_READ) {
            btnDelete.setVisibility(View.GONE);
        }
        ExtraAttributeView finalExtraAttributeView = extraAttributeView;
        btnDelete.setOnClickListener(v1 -> {
            extraAttribute = null;
            finalExtraAttributeView.removeAllViews();
            btnAdd.addCount();
        });
        extraAttributeView.setContent(value);
        layout.addView(extraAttributeView);
    }

    public void setInitValue(String materialId_
            , String materialType_
            , String plantId_
            , String investigatingTime_
            , int status_
            , String surveyId_) {
        materialId = materialId_;
        materialType = materialType_;
        plantId = plantId_;
        investigatingTime = investigatingTime_;
        status = status_;
        surveyId = surveyId_;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
