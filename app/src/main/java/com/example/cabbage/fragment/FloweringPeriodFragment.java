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
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_HARVEST;
import static com.example.cabbage.utils.UIUtils.checkIsValid;
import static com.example.cabbage.utils.UIUtils.setSelectionAndText;
import static com.example.cabbage.utils.UIUtils.setVisibilityOfUserDefined;
import static com.example.cabbage.utils.UIUtils.showBottomHelpDialog;

/**
 * Author:Kang
 * Date:2020/9/10
 * Description:现蕾开花期
 */
public class FloweringPeriodFragment extends Fragment {

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
    @BindView(R.id.half_plant_flower_bud)
    Spinner halfPlantFlowerBud;
    @BindView(R.id.edt_half_plant_flower_bud)
    AutoClearEditText edtHalfPlantFlowerBud;
    @BindView(R.id.btn_half_plant_flower_bud)
    Button btnHalfPlantFlowerBud;
    @BindView(R.id.edt_time_of_first_flower)
    EditText edtTimeOfFirstFlower;
    @BindView(R.id.btn_time_of_first_flower)
    Button btnTimeOfFirstFlower;
    @BindView(R.id.flower_bud_status)
    Spinner flowerBudStatus;
    @BindView(R.id.edt_flower_bud_status)
    AutoClearEditText edtFlowerBudStatus;
    @BindView(R.id.btn_flower_bud_status)
    Button btnFlowerBudStatus;
    @BindView(R.id.flower_bud_shape)
    Spinner flowerBudShape;
    @BindView(R.id.edt_flower_bud_shape)
    AutoClearEditText edtFlowerBudShape;
    @BindView(R.id.btn_flower_bud_shape)
    Button btnFlowerBudShape;
    @BindView(R.id.flower_bud_size)
    Spinner flowerBudSize;
    @BindView(R.id.edt_flower_bud_size)
    AutoClearEditText edtFlowerBudSize;
    @BindView(R.id.btn_flower_bud_size)
    Button btnFlowerBudSize;
    @BindView(R.id.petal_shape)
    Spinner petalShape;
    @BindView(R.id.edt_petal_shape)
    AutoClearEditText edtPetalShape;
    @BindView(R.id.btn_petal_shape)
    Button btnPetalShape;
    @BindView(R.id.petal_size)
    Spinner petalSize;
    @BindView(R.id.edt_petal_size)
    AutoClearEditText edtPetalSize;
    @BindView(R.id.btn_petal_size)
    Button btnPetalSize;
    @BindView(R.id.petal_color)
    Spinner petalColor;
    @BindView(R.id.edt_petal_color)
    AutoClearEditText edtPetalColor;
    @BindView(R.id.btn_petal_color)
    Button btnPetalColor;
    @BindView(R.id.edt_petal_count)
    EditText edtPetalCount;
    @BindView(R.id.btn_petal_count)
    Button btnPetalCount;
    @BindView(R.id.edt_germination_rate)
    EditText edtGerminationRate;
    @BindView(R.id.btn_germination_rate)
    Button btnGerminationRate;
    @BindView(R.id.branch_ability)
    Spinner branchAbility;
    @BindView(R.id.edt_branch_ability)
    AutoClearEditText edtBranchAbility;
    @BindView(R.id.btn_branch_ability)
    Button btnBranchAbility;
    @BindView(R.id.single_flower_sterile_degree)
    Spinner singleFlowerSterileDegree;
    @BindView(R.id.edt_single_flower_sterile_degree)
    AutoClearEditText edtSingleFlowerSterileDegree;
    @BindView(R.id.btn_single_flower_sterile_degree)
    Button btnSingleFlowerSterileDegree;
    @BindView(R.id.single_plant_sterile_degree)
    Spinner singlePlantSterileDegree;
    @BindView(R.id.edt_single_plant_sterile_degree)
    AutoClearEditText edtSinglePlantSterileDegree;
    @BindView(R.id.btn_single_plant_sterile_degree)
    Button btnSinglePlantSterileDegree;
    @BindView(R.id.group_sterile_degree)
    Spinner groupSterileDegree;
    @BindView(R.id.edt_group_sterile_degree)
    AutoClearEditText edtGroupSterileDegree;
    @BindView(R.id.btn_group_sterile_degree)
    Button btnGroupSterileDegree;
    @BindView(R.id.group_sterile_rate)
    Spinner groupSterileRate;
    @BindView(R.id.edt_group_sterile_rate)
    AutoClearEditText edtGroupSterileRate;
    @BindView(R.id.btn_group_sterile_rate)
    Button btnGroupSterileRate;
    @BindView(R.id.male_sterile)
    Spinner maleSterile;
    @BindView(R.id.edt_male_sterile)
    AutoClearEditText edtMaleSterile;
    @BindView(R.id.btn_male_sterile)
    Button btnMaleSterile;
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
    private String surveyPeriod = SURVEY_PERIOD_FLOWERING;
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
                case R.id.flower_bud_status:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.flower_bud_status).length - 1, edtFlowerBudStatus);
                    break;
                case R.id.flower_bud_shape:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.flower_bud_shape).length - 1, edtFlowerBudShape);
                    break;
                case R.id.petal_shape:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.petal_shape).length - 1, edtPetalShape);
                    break;
                case R.id.petal_color:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.petal_color).length - 1, edtPetalColor);
                    break;
                case R.id.branch_ability:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.branch_ability).length - 1, edtBranchAbility);
                    break;
                case R.id.male_sterile:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.male_sterile).length - 1, edtMaleSterile);
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
            case R.id.btn_half_plant_flower_bud:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_half_plant_flower_bud));
                break;
            case R.id.btn_time_of_first_flower:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_time_of_first_flower));
                break;
            case R.id.btn_flower_bud_status:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_flower_bud_status));
                break;
            case R.id.btn_flower_bud_shape:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_flower_bud_shape));
                break;
            case R.id.btn_flower_bud_size:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_flower_bud_size));
                break;
            case R.id.btn_petal_shape:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_petal_shape));
                break;
            case R.id.btn_petal_size:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_petal_size));
                break;
            case R.id.btn_petal_color:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_petal_color));
                break;
            case R.id.btn_petal_count:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_petal_count));
                break;
            case R.id.btn_germination_rate:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_flowing_plant_height));
                break;
            case R.id.btn_branch_ability:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_branch_ability));
                break;
            case R.id.btn_single_flower_sterile_degree:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_single_flower_sterile_degree));
                break;
            case R.id.btn_single_plant_sterile_degree:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_single_plant_sterile_degree));
                break;
            case R.id.btn_group_sterile_degree:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_group_sterile_degree));
                break;
            case R.id.btn_group_sterile_rate:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_group_sterile_rate));
                break;
            case R.id.btn_male_sterile:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_male_sterile));
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

    public static FloweringPeriodFragment newInstance() {
        return new FloweringPeriodFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flowering_period, container, false);
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
        flowerBudStatus.setOnItemSelectedListener(onItemSelectedListener);
        flowerBudShape.setOnItemSelectedListener(onItemSelectedListener);
        petalShape.setOnItemSelectedListener(onItemSelectedListener);
        petalColor.setOnItemSelectedListener(onItemSelectedListener);
        branchAbility.setOnItemSelectedListener(onItemSelectedListener);
        maleSterile.setOnItemSelectedListener(onItemSelectedListener);
       //显示底部提示弹出框
        btnHalfPlantFlowerBud.setOnClickListener(helpClickListener);
        btnTimeOfFirstFlower.setOnClickListener(helpClickListener);
        btnFlowerBudStatus.setOnClickListener(helpClickListener);
        btnFlowerBudShape.setOnClickListener(helpClickListener);
        btnFlowerBudSize.setOnClickListener(helpClickListener);
        btnPetalShape.setOnClickListener(helpClickListener);
        btnPetalSize.setOnClickListener(helpClickListener);
        btnPetalColor.setOnClickListener(helpClickListener);
        btnPetalCount.setOnClickListener(helpClickListener);
        btnGerminationRate.setOnClickListener(helpClickListener);
        btnBranchAbility.setOnClickListener(helpClickListener);
        btnSingleFlowerSterileDegree.setOnClickListener(helpClickListener);
        btnSinglePlantSterileDegree.setOnClickListener(helpClickListener);
        btnGroupSterileDegree.setOnClickListener(helpClickListener);
        btnGroupSterileRate.setOnClickListener(helpClickListener);
        btnMaleSterile.setOnClickListener(helpClickListener);

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

        String halfPlantFlowerBudData = halfPlantFlowerBud.getSelectedItem().toString() + SEPARATOR + edtHalfPlantFlowerBud.getText();
        String edtTimeOfFirstFlowerData = edtTimeOfFirstFlower.getText().toString();
        String flowerBudStatusData = flowerBudStatus.getSelectedItem().toString() + SEPARATOR + edtFlowerBudStatus.getText();
        String flowerBudShapeData = flowerBudShape.getSelectedItem().toString() + SEPARATOR + edtFlowerBudShape.getText();
        String flowerBudSizeData = flowerBudSize.getSelectedItem().toString() + SEPARATOR + edtFlowerBudSize.getText();
        String petalShapeData = petalShape.getSelectedItem().toString() + SEPARATOR + edtPetalShape.getText();
        String petalSizeData = petalSize.getSelectedItem().toString() + SEPARATOR + edtPetalSize.getText();
        String petalColorData = petalColor.getSelectedItem().toString() + SEPARATOR + edtPetalColor.getText();
        String edtPetalCountData = edtPetalCount.getText().toString();
        String edtGerminationRateData = edtGerminationRate.getText().toString();
        String branchAbilityData = branchAbility.getSelectedItem().toString() + SEPARATOR + edtBranchAbility.getText();
        String singleFlowerSterileDegreeData = singleFlowerSterileDegree.getSelectedItem().toString() + SEPARATOR + edtSingleFlowerSterileDegree.getText();
        String singlePlantSterileDegreeData = singlePlantSterileDegree.getSelectedItem().toString() + SEPARATOR + edtSinglePlantSterileDegree.getText();
        String groupSterileDegreeData = groupSterileDegree.getSelectedItem().toString() + SEPARATOR + edtGroupSterileDegree.getText();
        String groupSterileRateData = groupSterileRate.getSelectedItem().toString() + SEPARATOR + edtGroupSterileRate.getText();
        String maleSterileData = maleSterile.getSelectedItem().toString() + SEPARATOR + edtMaleSterile.getText();

        //额外属性
        String extraAttributeData = "";
        String extraRemarkData = "";
        if (extraAttribute != null) {
            extraAttributeData = extraAttribute.getContent();
        }
        if (extraRemark != null) {
            extraRemarkData = extraRemark.getContent();
        }

        jsonObject.addProperty("halfPlantBudding", halfPlantFlowerBudData);
        jsonObject.addProperty("timeRequiredForTheFirstFlower", edtTimeOfFirstFlowerData);
        jsonObject.addProperty("budState", flowerBudStatusData);
        jsonObject.addProperty("budShape", flowerBudShapeData);
        jsonObject.addProperty("budSize", flowerBudSizeData);
        jsonObject.addProperty("petalShape", petalShapeData);
        jsonObject.addProperty("petalSize", petalSizeData);
        jsonObject.addProperty("petalSize", petalColorData);
        jsonObject.addProperty("petalNumber", edtPetalCountData);
        jsonObject.addProperty("plantHeight", edtGerminationRateData);
        jsonObject.addProperty("branchingAbility", branchAbilityData);
        jsonObject.addProperty("sterilityOfSingleFlower", singleFlowerSterileDegreeData);
        jsonObject.addProperty("sterilityPerPlant", singlePlantSterileDegreeData);
        jsonObject.addProperty("populationSterility", groupSterileDegreeData);
        jsonObject.addProperty("populationSterilePlantRate", groupSterileRateData);
        jsonObject.addProperty("maleSterile", maleSterileData);
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
        setSelectionAndText(halfPlantFlowerBud, edtHalfPlantFlowerBud, surveyInfo.data.halfPlantBudding);
        edtTimeOfFirstFlower.setText(surveyInfo.data.timeRequiredForTheFirstFlower);
        setSelectionAndText(flowerBudStatus, edtFlowerBudStatus, surveyInfo.data.budState);
        setSelectionAndText(flowerBudShape, edtFlowerBudShape, surveyInfo.data.budShape);
        setSelectionAndText(flowerBudSize, edtFlowerBudSize, surveyInfo.data.budSize);
        setSelectionAndText(petalShape, edtPetalShape, surveyInfo.data.petalShape);
        setSelectionAndText(petalSize, edtPetalSize, surveyInfo.data.petalSize);
        setSelectionAndText(petalColor, edtPetalColor, surveyInfo.data.petalColor);
        edtPetalCount.setText(surveyInfo.data.petalNumber);
        edtGerminationRate.setText(surveyInfo.data.plantHeight);
        setSelectionAndText(branchAbility, edtBranchAbility, surveyInfo.data.branchingAbility);
        setSelectionAndText(singleFlowerSterileDegree, edtSingleFlowerSterileDegree, surveyInfo.data.sterilityOfSingleFlower);
        setSelectionAndText(singlePlantSterileDegree, edtSinglePlantSterileDegree, surveyInfo.data.sterilityPerPlant);
        setSelectionAndText(groupSterileDegree, edtGroupSterileDegree, surveyInfo.data.populationSterility);
        setSelectionAndText(groupSterileRate, edtGroupSterileRate, surveyInfo.data.populationSterilePlantRate);
        setSelectionAndText(maleSterile, edtMaleSterile, surveyInfo.data.maleSterile);

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
