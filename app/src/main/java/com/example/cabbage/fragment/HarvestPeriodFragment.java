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
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_HARVEST;
import static com.example.cabbage.utils.UIUtils.checkIsValid;
import static com.example.cabbage.utils.UIUtils.setSelectionAndText;
import static com.example.cabbage.utils.UIUtils.setVisibilityOfUserDefined;
import static com.example.cabbage.utils.UIUtils.showBottomHelpDialog;

/**
 * Author:Kang
 * Date:2020/9/10
 * Description:收获期
 */
public class HarvestPeriodFragment extends Fragment {

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
    @BindView(R.id.strobilus_head_closure_type)
    Spinner strobilusHeadClosureType;
    @BindView(R.id.edt_strobilus_head_closure_type)
    AutoClearEditText edtStrobilusHeadClosureType;
    @BindView(R.id.btn_strobilus_head_closure_type)
    Button btnStrobilusHeadClosureType;
    @BindView(R.id.strobilus_head_conjugation_type)
    Spinner strobilusHeadConjugationType;
    @BindView(R.id.edt_strobilus_head_conjugation_type)
    AutoClearEditText edtStrobilusHeadConjugationType;
    @BindView(R.id.btn_strobilus_head_conjugation_type)
    Button btnStrobilusHeadConjugationType;
    @BindView(R.id.strobilus_head_shape)
    Spinner strobilusHeadShape;
    @BindView(R.id.edt_strobilus_head_shape)
    AutoClearEditText edtStrobilusHeadShape;
    @BindView(R.id.btn_strobilus_head_shape)
    Button btnStrobilusHeadShape;
    @BindView(R.id.strobilus_top_color)
    Spinner strobilusTopColor;
    @BindView(R.id.edt_strobilus_top_color)
    AutoClearEditText edtStrobilusTopColor;
    @BindView(R.id.btn_strobilus_top_color)
    Button btnStrobilusTopColor;
    @BindView(R.id.strobilus_top_green_degree)
    Spinner strobilusTopGreenDegree;
    @BindView(R.id.edt_strobilus_top_green_degree)
    AutoClearEditText edtStrobilusTopGreenDegree;
    @BindView(R.id.btn_strobilus_top_green_degree)
    Button btnStrobilusTopGreenDegree;
    @BindView(R.id.strobilus_shape)
    Spinner strobilusShape;
    @BindView(R.id.edt_strobilus_shape)
    AutoClearEditText edtStrobilusShape;
    @BindView(R.id.btn_strobilus_shape)
    Button btnStrobilusShape;
    @BindView(R.id.strobilus_height)
    Spinner strobilusHeight;
    @BindView(R.id.edt_strobilus_height)
    AutoClearEditText edtStrobilusHeight;
    @BindView(R.id.btn_strobilus_height)
    Button btnStrobilusHeight;
    @BindView(R.id.strobilus_width)
    Spinner strobilusWidth;
    @BindView(R.id.edt_strobilus_width)
    AutoClearEditText edtStrobilusWidth;
    @BindView(R.id.btn_strobilus_width)
    Button btnStrobilusWidth;
    @BindView(R.id.edt_strobilus_mid_width)
    EditText edtStrobilusMidWidth;
    @BindView(R.id.btn_strobilus_mid_width)
    Button btnStrobilusMidWidth;
    @BindView(R.id.edt_strobilus_end_width)
    EditText edtStrobilusEndWidth;
    @BindView(R.id.btn_strobilus_end_width)
    Button btnStrobilusEndWidth;
    @BindView(R.id.strobilus_compaction)
    Spinner strobilusCompaction;
    @BindView(R.id.edt_strobilus_compaction)
    AutoClearEditText edtStrobilusCompaction;
    @BindView(R.id.btn_strobilus_compaction)
    Button btnStrobilusCompaction;
    @BindView(R.id.strobilus_inner_color)
    Spinner strobilusInnerColor;
    @BindView(R.id.edt_strobilus_inner_color)
    AutoClearEditText edtStrobilusInnerColor;
    @BindView(R.id.btn_strobilus_inner_color)
    Button btnStrobilusInnerColor;
    @BindView(R.id.strobilus_leaf_count)
    Spinner strobilusLeafCount;
    @BindView(R.id.edt_strobilus_leaf_count)
    AutoClearEditText edtStrobilusLeafCount;
    @BindView(R.id.btn_strobilus_leaf_count)
    Button btnStrobilusLeafCount;
    @BindView(R.id.soft_leaf_rate)
    Spinner softLeafRate;
    @BindView(R.id.edt_soft_leaf_rate)
    AutoClearEditText edtSoftLeafRate;
    @BindView(R.id.btn_soft_leaf_rate)
    Button btnSoftLeafRate;
    @BindView(R.id.strobilus_mass)
    Spinner strobilusMass;
    @BindView(R.id.edt_strobilus_mass)
    AutoClearEditText edtStrobilusMass;
    @BindView(R.id.btn_strobilus_mass)
    Button btnStrobilusMass;
    @BindView(R.id.clean_veg_rate)
    Spinner cleanVegRate;
    @BindView(R.id.edt_clean_veg_rate)
    AutoClearEditText edtCleanVegRate;
    @BindView(R.id.btn_clean_veg_rate)
    Button btnCleanVegRate;
    @BindView(R.id.center_shape)
    Spinner centerShape;
    @BindView(R.id.edt_center_shape)
    AutoClearEditText edtCenterShape;
    @BindView(R.id.btn_center_shape)
    Button btnCenterShape;
    @BindView(R.id.center_length)
    Spinner centerLength;
    @BindView(R.id.edt_center_length)
    AutoClearEditText edtCenterLength;
    @BindView(R.id.btn_center_length)
    Button btnCenterLength;
    @BindView(R.id.harvest_type)
    Spinner harvestType;
    @BindView(R.id.edt_harvest_type)
    AutoClearEditText edtHarvestType;
    @BindView(R.id.btn_harvest_type)
    Button btnHarvestType;
    @BindView(R.id.harvest_delay_time_early)
    Spinner harvestDelayTimeEarly;
    @BindView(R.id.edt_harvest_delay_time_early)
    AutoClearEditText edtHarvestDelayTimeEarly;
    @BindView(R.id.btn_harvest_delay_time_early)
    Button btnHarvestDelayTimeEarly;
    @BindView(R.id.harvest_delay_time_late)
    Spinner harvestDelayTimeLate;
    @BindView(R.id.edt_harvest_delay_time_late)
    AutoClearEditText edtHarvestDelayTimeLate;
    @BindView(R.id.btn_harvest_delay_time_late)
    Button btnHarvestDelayTimeLate;
    @BindView(R.id.btn_add_attribute)
    CountButton btnAddAttribute;
    @BindView(R.id.btn_add_remark)
    CountButton btnAddRemark;
    @BindView(R.id.btn_upload_data)
    Button btnUploadData;
    @BindView(R.id.layout_custom_attribute)
    LinearLayout layoutCustomAttribute;

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
    Spinner.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()) {
                case R.id.strobilus_head_closure_type:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.strobilus_head_closure_type).length - 1, edtStrobilusHeadClosureType);
                    break;
                case R.id.strobilus_head_conjugation_type:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.strobilus_head_conjugation_type).length - 1, edtStrobilusHeadConjugationType);
                    break;
                case R.id.strobilus_head_shape:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.strobilus_head_shape).length - 1, edtStrobilusHeadShape);
                    break;
                case R.id.strobilus_top_color:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.strobilus_top_color).length - 1, edtStrobilusTopColor);
                    break;
                case R.id.strobilus_top_green_degree:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.strobilus_top_green_degree).length - 1, edtStrobilusTopGreenDegree);
                    break;
                case R.id.strobilus_shape:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.strobilus_shape).length - 1, edtStrobilusShape);
                    break;
                case R.id.strobilus_compaction:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.strobilus_compaction).length - 1, edtStrobilusCompaction);
                    break;
                case R.id.strobilus_inner_color:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.strobilus_inner_color).length - 1, edtStrobilusInnerColor);
                    break;
                case R.id.center_shape:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.center_shape).length - 1, edtCenterShape);
                    break;
                case R.id.harvest_type:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.harvest_type).length - 1, edtHarvestType);
                    break;
                case R.id.harvest_delay_time_early:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.harvest_delay_time_early).length - 1, edtHarvestDelayTimeEarly);
                    break;
                case R.id.harvest_delay_time_late:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.harvest_delay_time_late).length - 1, edtHarvestDelayTimeLate);
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
            case R.id.btn_strobilus_head_closure_type:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_strobilus_head_closure_type));
                break;
            case R.id.btn_strobilus_head_conjugation_type:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_strobilus_head_conjugation_type));
                break;
            case R.id.btn_strobilus_head_shape:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_strobilus_head_shape));
                break;
            case R.id.btn_strobilus_top_color:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_strobilus_top_color));
                break;
            case R.id.btn_strobilus_top_green_degree:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_strobilus_top_green_degree));
                break;
            case R.id.btn_strobilus_shape:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_strobilus_shape));
                break;
            case R.id.btn_strobilus_height:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_strobilus_height));
                break;
            case R.id.btn_strobilus_width:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_strobilus_width));
                break;
            case R.id.btn_strobilus_mid_width:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_strobilus_mid_width));
                break;
            case R.id.btn_strobilus_end_width:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_strobilus_end_width));
                break;
            case R.id.btn_strobilus_compaction:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_strobilus_compaction));
                break;
            case R.id.btn_strobilus_inner_color:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_strobilus_inner_color));
                break;
            case R.id.btn_strobilus_leaf_count:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_strobilus_leaf_count));
                break;
            case R.id.btn_soft_leaf_rate:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_soft_leaf_rate));
                break;
            case R.id.btn_strobilus_mass:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_strobilus_mass));
                break;
            case R.id.btn_clean_veg_rate:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_clean_veg_rate));
                break;
            case R.id.btn_center_shape:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_center_shape));
                break;
            case R.id.btn_center_length:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_center_length));
                break;
            case R.id.btn_harvest_type:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_harvest_type));
                break;
            case R.id.btn_harvest_delay_time_early:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_harvest_delay_time_early));
                break;
            case R.id.btn_harvest_delay_time_late:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_harvest_delay_time_late));
                break;
        }
    };
    View.OnClickListener extraAttributeClickListener = v -> {
        switch (v.getId()) {
            case R.id.btn_add_attribute:
                addExtraAttributeView(btnAddAttribute, layoutCustomAttribute, "spare1","");
                break;
            case R.id.btn_add_remark:
                addExtraAttributeView(btnAddRemark, layoutCustomAttribute, "spare2","");
                break;
        }
    };
    View.OnClickListener submitClickListener = v -> {
        if (checkIsValid(edtPlantId)) {
            Toast.makeText(self, R.string.check_required, Toast.LENGTH_SHORT).show();
        } else {
            showDialog(SURVEY_PERIOD_HARVEST);
        }
    };

    public static HarvestPeriodFragment newInstance() {
        return new HarvestPeriodFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_harvest_period, container, false);
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
//                initData(surveyPeriod);
                //复制粘贴暂不支持图片
                break;
            default:
                break;
        }

        return view;
    }


    private void initView(boolean editable) {

        //判断是否显示自定义填空
        strobilusHeadClosureType.setOnItemSelectedListener(onItemSelectedListener);
        strobilusHeadConjugationType.setOnItemSelectedListener(onItemSelectedListener);
        strobilusHeadShape.setOnItemSelectedListener(onItemSelectedListener);
        strobilusTopColor.setOnItemSelectedListener(onItemSelectedListener);
        strobilusTopGreenDegree.setOnItemSelectedListener(onItemSelectedListener);
        strobilusShape.setOnItemSelectedListener(onItemSelectedListener);
        strobilusCompaction.setOnItemSelectedListener(onItemSelectedListener);
        strobilusInnerColor.setOnItemSelectedListener(onItemSelectedListener);
        centerShape.setOnItemSelectedListener(onItemSelectedListener);
        harvestType.setOnItemSelectedListener(onItemSelectedListener);
        harvestDelayTimeEarly.setOnItemSelectedListener(onItemSelectedListener);
        harvestDelayTimeLate.setOnItemSelectedListener(onItemSelectedListener);
        //显示底部提示弹出框
        btnStrobilusHeadClosureType.setOnClickListener(helpClickListener);
        btnStrobilusHeadConjugationType.setOnClickListener(helpClickListener);
        btnStrobilusHeadShape.setOnClickListener(helpClickListener);
        btnStrobilusTopColor.setOnClickListener(helpClickListener);
        btnStrobilusTopGreenDegree.setOnClickListener(helpClickListener);
        btnStrobilusShape.setOnClickListener(helpClickListener);
        btnStrobilusHeight.setOnClickListener(helpClickListener);
        btnStrobilusWidth.setOnClickListener(helpClickListener);
        btnStrobilusMidWidth.setOnClickListener(helpClickListener);
        btnStrobilusEndWidth.setOnClickListener(helpClickListener);
        btnStrobilusCompaction.setOnClickListener(helpClickListener);
        btnStrobilusInnerColor.setOnClickListener(helpClickListener);
        btnStrobilusLeafCount.setOnClickListener(helpClickListener);
        btnSoftLeafRate.setOnClickListener(helpClickListener);
        btnStrobilusMass.setOnClickListener(helpClickListener);
        btnCleanVegRate.setOnClickListener(helpClickListener);
        btnCenterShape.setOnClickListener(helpClickListener);
        btnCenterLength.setOnClickListener(helpClickListener);
        btnHarvestType.setOnClickListener(helpClickListener);
        btnHarvestDelayTimeEarly.setOnClickListener(helpClickListener);
        btnHarvestDelayTimeLate.setOnClickListener(helpClickListener);

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
                        String surveyId = resultInfo.data.observationId;
                        uploadPics(surveyPeriod, surveyId);
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

        String strobilusHeadClosureTypeData = strobilusHeadClosureType.getSelectedItem().toString() + SEPARATOR + edtStrobilusHeadClosureType.getText();
        String strobilusHeadConjugationTypeData  = strobilusHeadConjugationType.getSelectedItem().toString() + SEPARATOR + edtStrobilusHeadConjugationType.getText();
        String strobilusHeadShapeData = strobilusHeadShape.getSelectedItem().toString() + SEPARATOR + edtStrobilusHeadShape.getText();
        String strobilusTopColorData = strobilusTopColor.getSelectedItem().toString() + SEPARATOR + edtStrobilusTopColor.getText();
        String pstrobilusTopGreenDegreeData  = strobilusTopGreenDegree.getSelectedItem().toString() + SEPARATOR + edtStrobilusTopGreenDegree.getText();
        String strobilusShapeData = strobilusShape.getSelectedItem().toString() + SEPARATOR + edtStrobilusShape.getText();
        String strobilusHeightData  = strobilusHeight.getSelectedItem().toString() + SEPARATOR + edtStrobilusHeight.getText();
        String strobilusWidthData  = strobilusWidth.getSelectedItem().toString() + SEPARATOR + edtStrobilusWidth.getText();
        String StrobilusMidWidthData  = edtStrobilusMidWidth.getText().toString();
        String StrobilusEndWidthData  = edtStrobilusEndWidth.getText().toString();
        String strobilusCompactionData  = strobilusCompaction.getSelectedItem().toString() + SEPARATOR + edtStrobilusCompaction.getText();
        String strobilusInnerColorData = strobilusInnerColor.getSelectedItem().toString() + SEPARATOR + edtStrobilusInnerColor.getText();
        String strobilusLeafCountData = strobilusLeafCount.getSelectedItem().toString() + SEPARATOR + edtStrobilusLeafCount.getText();
        String softLeafRateData  = softLeafRate.getSelectedItem().toString() + SEPARATOR + edtSoftLeafRate.getText();
        String strobilusMassData = strobilusMass.getSelectedItem().toString() + SEPARATOR + edtStrobilusMass.getText();
        String cleanVegRateData = cleanVegRate.getSelectedItem().toString() + SEPARATOR + edtCleanVegRate.getText();
        String centerShapeData  = centerShape.getSelectedItem().toString() + SEPARATOR + edtCenterShape.getText();
        String centerLengthData  = centerLength.getSelectedItem().toString() + SEPARATOR + edtCenterLength.getText();
        String harvestTypeData  = harvestType.getSelectedItem().toString() + SEPARATOR + edtHarvestType.getText();
        String harvestDelayTimeEarlyData  = harvestDelayTimeEarly.getSelectedItem().toString() + SEPARATOR + edtHarvestDelayTimeEarly.getText();
        String harvestDelayTimeLateData  = harvestDelayTimeLate.getSelectedItem().toString() + SEPARATOR + edtHarvestDelayTimeLate.getText();

        //额外属性
        String extraAttributeData  = "";
        String extraRemarkData  = "";
        if (extraAttribute != null) {
            extraAttributeData  = extraAttribute.getContent();
        }
        if (extraRemark != null) {
            extraRemarkData  = extraRemark.getContent();
        }

        jsonObject.addProperty("ballTopClosedType", strobilusHeadClosureTypeData );
        jsonObject.addProperty("ballTopHoldType", strobilusHeadConjugationTypeData );
        jsonObject.addProperty("ballTopShape", strobilusHeadShapeData );
        jsonObject.addProperty("upperLeafBulbColor", strobilusTopColorData );
        jsonObject.addProperty("greenDegreeOfUpperLeafBall", pstrobilusTopGreenDegreeData );
        jsonObject.addProperty("leafBallShape", strobilusShapeData );
        jsonObject.addProperty("leafBallHeight", strobilusHeightData) ;
        jsonObject.addProperty("leafBallWidth", strobilusWidthData );
        jsonObject.addProperty("leafBallMiddleWidth", StrobilusMidWidthData );
        jsonObject.addProperty("leafBulbEndWidth", StrobilusEndWidthData );
        jsonObject.addProperty("leafBallCompactness", strobilusCompactionData );
        jsonObject.addProperty("innerColorOfLeafBall", strobilusInnerColorData );
        jsonObject.addProperty("numberOfBulbs", strobilusLeafCountData );
        jsonObject.addProperty("softLeafRate", softLeafRateData );
        jsonObject.addProperty("leafBallWeight", strobilusMassData );
        jsonObject.addProperty("netVegetableRate", cleanVegRateData );
        jsonObject.addProperty("centerColumnShape", centerShapeData );
        jsonObject.addProperty("centerColumnLength", centerLengthData );
        jsonObject.addProperty("maturity", harvestTypeData );
        jsonObject.addProperty("extendedHarvestPeriodEarly", harvestDelayTimeEarlyData );
        jsonObject.addProperty("extendedHarvestPeriodMidLate", harvestDelayTimeLateData );
        jsonObject.addProperty("spare1", extraAttributeData );
        jsonObject.addProperty("spare2", extraRemarkData );

        return jsonObject.toString();
    }

    // 更新上传图片
    private void uploadPics(String surveyPeriod, String surveyId) {
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
        setSelectionAndText(strobilusHeadClosureType, edtStrobilusHeadClosureType, surveyInfo.data.ballTopClosedType);
        setSelectionAndText(strobilusHeadConjugationType, edtStrobilusHeadConjugationType, surveyInfo.data.ballTopHoldType);
        setSelectionAndText(strobilusHeadShape, edtStrobilusHeadShape, surveyInfo.data.ballTopShape);
        setSelectionAndText(strobilusTopColor, edtStrobilusTopColor, surveyInfo.data.upperLeafBulbColor);
        setSelectionAndText(strobilusTopGreenDegree, edtStrobilusTopGreenDegree, surveyInfo.data.greenDegreeOfUpperLeafBall);
        setSelectionAndText(strobilusShape, edtStrobilusShape, surveyInfo.data.leafBallShape);
        setSelectionAndText(strobilusHeight, edtStrobilusHeight, surveyInfo.data.leafBallHeight);
        setSelectionAndText(strobilusWidth, edtStrobilusWidth, surveyInfo.data.leafBallWidth);
        edtStrobilusMidWidth.setText(surveyInfo.data.leafBallMiddleWidth);
        edtStrobilusEndWidth.setText(surveyInfo.data.leafBulbEndWidth);
        setSelectionAndText(strobilusCompaction, edtStrobilusCompaction, surveyInfo.data.leafBallCompactness);
        setSelectionAndText(strobilusInnerColor, edtStrobilusInnerColor, surveyInfo.data.innerColorOfLeafBall);
        setSelectionAndText(strobilusLeafCount, edtStrobilusLeafCount, surveyInfo.data.numberOfBulbs);
        setSelectionAndText(softLeafRate, edtSoftLeafRate, surveyInfo.data.softLeafRate);
        setSelectionAndText(strobilusMass, edtStrobilusMass, surveyInfo.data.leafBallWeight);
        setSelectionAndText(cleanVegRate, edtCleanVegRate, surveyInfo.data.netVegetableRate);
        setSelectionAndText(centerShape, edtCenterShape, surveyInfo.data.centerColumnShape);
        setSelectionAndText(centerLength, edtCenterLength, surveyInfo.data.centerColumnLength);
        setSelectionAndText(harvestType, edtHarvestType, surveyInfo.data.maturity);
        setSelectionAndText(harvestDelayTimeEarly, edtHarvestDelayTimeEarly, surveyInfo.data.extendedHarvestPeriodEarly);
        setSelectionAndText(harvestDelayTimeLate, edtHarvestDelayTimeLate, surveyInfo.data.extendedHarvestPeriodMidLate);

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
        ExtraAttributeView extraAttributeView=new ExtraAttributeView(self, ExtraAttributeView.TYPE_ATTRIBUTE, "spare1");
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
        if(status==STATUS_READ){
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
