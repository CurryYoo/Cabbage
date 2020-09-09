package com.example.cabbage.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.example.cabbage.view.AutoClearEditText;
import com.example.cabbage.view.CountButton;
import com.example.cabbage.view.ExtraAttributeView;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.cabbage.utils.BasicUtil.showDatePickerDialog;
import static com.example.cabbage.utils.StaticVariable.SEPARATOR;
import static com.example.cabbage.utils.StaticVariable.STATUS_COPY;
import static com.example.cabbage.utils.StaticVariable.STATUS_NEW;
import static com.example.cabbage.utils.StaticVariable.STATUS_READ;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_HARVEST;
import static com.example.cabbage.utils.UIUtils.checkIsValid;
import static com.example.cabbage.utils.UIUtils.setVisibilityOfUserDefined;
import static com.example.cabbage.utils.UIUtils.showBottomHelpDialog;

public class HeadingPeriodFragment extends Fragment {
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
    @BindView(R.id.plant_shape)
    Spinner plantShape;
    @BindView(R.id.edt_plant_shape)
    AutoClearEditText edtPlantShape;
    @BindView(R.id.btn_plant_shape)
    Button btnPlantShape;
    @BindView(R.id.plant_height)
    Spinner plantHeight;
    @BindView(R.id.edt_plant_height)
    AutoClearEditText edtPlantHeight;
    @BindView(R.id.btn_plant_height)
    Button btnPlantHeight;
    @BindView(R.id.development_degree)
    Spinner developmentDegree;
    @BindView(R.id.edt_development_degree)
    AutoClearEditText edtDevelopmentDegree;
    @BindView(R.id.btn_development_degree)
    Button btnDevelopmentDegree;
    @BindView(R.id.is_knot)
    Spinner isKnot;
    @BindView(R.id.edt_is_knot)
    AutoClearEditText edtIsKnot;
    @BindView(R.id.btn_is_knot)
    Button btnIsKnot;
    @BindView(R.id.flower_bud)
    Spinner flowerBud;
    @BindView(R.id.edt_flower_bud)
    AutoClearEditText edtFlowerBud;
    @BindView(R.id.btn_flower_bud)
    Button btnFlowerBud;
    @BindView(R.id.lateral_bud)
    Spinner lateralBud;
    @BindView(R.id.edt_lateral_bud)
    AutoClearEditText edtLateralBud;
    @BindView(R.id.btn_lateral_bud)
    Button btnLateralBud;
    @BindView(R.id.outer_leaf_length)
    Spinner outerLeafLength;
    @BindView(R.id.edt_outer_leaf_length)
    AutoClearEditText edtOuterLeafLength;
    @BindView(R.id.btn_outer_leaf_length)
    Button btnOuterLeafLength;
    @BindView(R.id.outer_leaf_width)
    Spinner outerLeafWidth;
    @BindView(R.id.edt_outer_leaf_width)
    AutoClearEditText edtOuterLeafWidth;
    @BindView(R.id.btn_outer_leaf_width)
    Button btnOuterLeafWidth;
    @BindView(R.id.outer_leaf_shape)
    Spinner outerLeafShape;
    @BindView(R.id.edt_outer_leaf_shape)
    AutoClearEditText edtOuterLeafShape;
    @BindView(R.id.btn_outer_leaf_shape)
    Button btnOuterLeafShape;
    @BindView(R.id.outer_leaf_keel_color)
    Spinner outerLeafKeelColor;
    @BindView(R.id.edt_outer_leaf_keel_color)
    AutoClearEditText edtOuterLeafKeelColor;
    @BindView(R.id.btn_outer_leaf_keel_color)
    Button btnOuterLeafKeelColor;
    @BindView(R.id.outer_leaf_keel_thickness)
    Spinner outerLeafKeelThickness;
    @BindView(R.id.edt_outer_leaf_keel_thickness)
    AutoClearEditText edtOuterLeafKeelThickness;
    @BindView(R.id.btn_outer_leaf_keel_thickness)
    Button btnOuterLeafKeelThickness;
    @BindView(R.id.outer_leaf_keel_length)
    Spinner outerLeafKeelLength;
    @BindView(R.id.edt_outer_leaf_keel_length)
    AutoClearEditText edtOuterLeafKeelLength;
    @BindView(R.id.btn_outer_leaf_keel_length)
    Button btnOuterLeafKeelLength;
    @BindView(R.id.outer_leaf_keel_width)
    Spinner outerLeafKeelWidth;
    @BindView(R.id.edt_outer_leaf_keel_width)
    AutoClearEditText edtOuterLeafKeelWidth;
    @BindView(R.id.btn_outer_leaf_keel_width)
    Button btnOuterLeafKeelWidth;
    @BindView(R.id.leaf_keel_shape)
    Spinner leafKeelShape;
    @BindView(R.id.edt_leaf_keel_shape)
    AutoClearEditText edtLeafKeelShape;
    @BindView(R.id.btn_leaf_keel_shape)
    Button btnLeafKeelShape;
    @BindView(R.id.outer_leaf_count)
    Spinner outerLeafCount;
    @BindView(R.id.edt_outer_leaf_count)
    AutoClearEditText edtOuterLeafCount;
    @BindView(R.id.btn_outer_leaf_count)
    Button btnOuterLeafCount;
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
                case R.id.plant_shape:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.plant_shape).length - 1, edtPlantShape);
                    break;
                case R.id.is_knot:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.is_knot).length - 1, edtIsKnot);
                    break;
                case R.id.flower_bud:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.flower_bud).length - 1, edtFlowerBud);
                    break;
                case R.id.lateral_bud:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.lateral_bud).length - 1, edtLateralBud);
                    break;
                case R.id.outer_leaf_shape:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.outer_leaf_shape).length - 1, edtOuterLeafShape);
                    break;
                case R.id.outer_leaf_keel_color:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.outer_leaf_keel_color).length - 1, edtOuterLeafKeelColor);
                    break;
                case R.id.outer_leaf_keel_thickness:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.outer_leaf_keel_thickness).length - 1, edtOuterLeafKeelThickness);
                    break;
                case R.id.leaf_keel_shape:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.leaf_keel_shape).length - 1, edtLeafKeelShape);
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
            case R.id.btn_plant_shape:
                showBottomHelpDialog(self, getFragmentManager(), token, getResources().getString(R.string.info_harvest_plant_shape));
                break;
            case R.id.btn_plant_height:
                showBottomHelpDialog(self, getFragmentManager(), token, getResources().getString(R.string.info_harvest_plant_height));
                break;
            case R.id.btn_development_degree:
                showBottomHelpDialog(self, getFragmentManager(), token, getResources().getString(R.string.info_harvest_development_degree));
                break;
            case R.id.btn_is_knot:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_is_knot));
                break;
            case R.id.btn_flower_bud:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_flower_bud));
                break;
            case R.id.btn_lateral_bud:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_lateral_bud));
                break;
            case R.id.btn_outer_leaf_length:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_outer_leaf_length));
                break;
            case R.id.btn_outer_leaf_width:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_outer_leaf_width));
                break;
            case R.id.btn_outer_leaf_shape:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_outer_leaf_shape));
                break;
            case R.id.btn_outer_leaf_keel_color:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_outer_leaf_keel_color));
                break;
            case R.id.btn_outer_leaf_keel_thickness:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_outer_leaf_keel_thickness));
                break;
            case R.id.btn_outer_leaf_keel_length:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_outer_leaf_keel_length));
                break;
            case R.id.btn_outer_leaf_keel_width:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_outer_leaf_keel_width));
                break;
            case R.id.btn_leaf_keel_shape:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_leaf_keel_shape));
                break;
            case R.id.btn_outer_leaf_count:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_outer_leaf_count));
                break;
            default:
                break;
        }
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
            showDialog(SURVEY_PERIOD_HARVEST);

        }
    };

    public static HeadingPeriodFragment newInstance() {
        return new HeadingPeriodFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_heading_period, container, false);
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
//                initData(surveyPeriod);
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
        plantShape.setOnItemSelectedListener(onItemSelectedListener);
        isKnot.setOnItemSelectedListener(onItemSelectedListener);
        flowerBud.setOnItemSelectedListener(onItemSelectedListener);
        lateralBud.setOnItemSelectedListener(onItemSelectedListener);
        outerLeafShape.setOnItemSelectedListener(onItemSelectedListener);
        outerLeafKeelColor.setOnItemSelectedListener(onItemSelectedListener);
        outerLeafKeelThickness.setOnItemSelectedListener(onItemSelectedListener);
        leafKeelShape.setOnItemSelectedListener(onItemSelectedListener);
        //显示底部提示弹出框
        btnPlantShape.setOnClickListener(helpClickListener);
        btnPlantHeight.setOnClickListener(helpClickListener);
        btnDevelopmentDegree.setOnClickListener(helpClickListener);
        btnIsKnot.setOnClickListener(helpClickListener);
        btnFlowerBud.setOnClickListener(helpClickListener);
        btnLateralBud.setOnClickListener(helpClickListener);
        btnOuterLeafLength.setOnClickListener(helpClickListener);
        btnOuterLeafWidth.setOnClickListener(helpClickListener);
        btnOuterLeafShape.setOnClickListener(helpClickListener);
        btnOuterLeafKeelColor.setOnClickListener(helpClickListener);
        btnOuterLeafKeelThickness.setOnClickListener(helpClickListener);
        btnOuterLeafKeelLength.setOnClickListener(helpClickListener);
        btnOuterLeafKeelWidth.setOnClickListener(helpClickListener);
        btnLeafKeelShape.setOnClickListener(helpClickListener);
        btnOuterLeafCount.setOnClickListener(helpClickListener);

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


    private String getPeriodData() {
        //结球期
        JsonObject jsonObject = getBasicInfoData();
        String plantShapeString = plantShape.getSelectedItem().toString() + SEPARATOR + edtPlantShape.getText();
        String plantHeightString = plantHeight.getSelectedItem().toString() + SEPARATOR + edtPlantHeight.getText();
        String developmentDegreeString = developmentDegree.getSelectedItem().toString() + SEPARATOR + edtDevelopmentDegree.getText();
        String isKnotString = isKnot.getSelectedItem().toString() + SEPARATOR + edtIsKnot.getText();
        String flowerBudString = flowerBud.getSelectedItem().toString() + SEPARATOR + edtFlowerBud.getText();
        String lateralBudString = lateralBud.getSelectedItem().toString() + SEPARATOR + edtLateralBud.getText();
        String outerLeafLengthString = outerLeafLength.getSelectedItem().toString() + SEPARATOR + edtOuterLeafLength.getText();
        String outerLeafWidthString = outerLeafWidth.getSelectedItem().toString() + SEPARATOR + edtOuterLeafWidth.getText();
        String outerLeafShapeString = outerLeafShape.getSelectedItem().toString() + SEPARATOR + edtOuterLeafShape.getText();
        String outerLeafKeelColorString = outerLeafKeelColor.getSelectedItem().toString() + SEPARATOR + edtOuterLeafKeelColor.getText();
        String outerLeafKeelThicknessString = outerLeafKeelThickness.getSelectedItem().toString() + SEPARATOR + edtOuterLeafKeelThickness.getText();
        String outerLeafKeelLengthString = outerLeafKeelLength.getSelectedItem().toString() + SEPARATOR + edtOuterLeafKeelLength.getText();
        String outerLeafKeelWidthString = outerLeafKeelWidth.getSelectedItem().toString() + SEPARATOR + edtOuterLeafKeelWidth.getText();
        String leafKeelShapeString = leafKeelShape.getSelectedItem().toString() + SEPARATOR + edtLeafKeelShape.getText();
        String outerLeafCountString = outerLeafCount.getSelectedItem().toString() + SEPARATOR + edtOuterLeafCount.getText();

        //额外属性
        String extraAttributeString = "";
        String extraRemarkString = "";
        if (extraAttribute != null) {
            extraAttributeString = extraAttribute.getContent();
            Toast.makeText(self, extraAttributeString, Toast.LENGTH_SHORT).show();
        }
        if (extraRemark != null) {
            extraRemarkString = extraRemark.getContent();
        }

        jsonObject.addProperty("plantType", plantShapeString);
        jsonObject.addProperty("plantHeight", plantHeightString);
        jsonObject.addProperty("developmentDegree", developmentDegreeString);
        jsonObject.addProperty("isBall", isKnotString);
        jsonObject.addProperty("bud", flowerBudString);
        jsonObject.addProperty("lateralBud", lateralBudString);
        jsonObject.addProperty("outerLeafLength", outerLeafLengthString);
        jsonObject.addProperty("outerLeafWidth", outerLeafWidthString);
        jsonObject.addProperty("outerLeafShape", outerLeafShapeString);
        jsonObject.addProperty("colorOfMiddleRib", outerLeafKeelColorString);
        jsonObject.addProperty("thicknessOfMiddleRib", outerLeafKeelThicknessString);
        jsonObject.addProperty("lengthOfMiddleRib", outerLeafKeelLengthString);
        jsonObject.addProperty("widthOfMiddleRib", outerLeafKeelWidthString);
        jsonObject.addProperty("middleRibShape", leafKeelShapeString);
        jsonObject.addProperty("numberOfOuterLeaves", outerLeafCountString);
//        jsonObject.addProperty("spare1", extraAttributeString);
//        jsonObject.addProperty("spare2", extraRemarkString);

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
    //添加额外属性
    private void addExtraAttribute(CountButton btnAdd, LinearLayout layout, String keyName) {
        btnAdd.subtractCount();
        ExtraAttributeView extraAttributeView = new ExtraAttributeView(self, ExtraAttributeView.TYPE_ATTRIBUTE, keyName);
        Button btnDelete = extraAttributeView.findViewById(R.id.btn_delete);
        switch (keyName) {
            case "spare1":
                extraAttributeView.setTitle(getString(R.string.obligate_attribute));
                extraAttribute = extraAttributeView;
                btnDelete.setOnClickListener(v1 -> {
                    extraAttribute = null;
                    extraAttributeView.removeAllViews();
                    btnAdd.addCount();
                });
                break;
            case "spare2":
                extraAttributeView.setTitle(getString(R.string.info_remark));
                extraRemark = extraAttributeView;
                btnDelete.setOnClickListener(v1 -> {
                    extraRemark = null;
                    extraAttributeView.removeAllViews();
                    btnAdd.addCount();
                });
                break;
            default:
                break;
        }
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
