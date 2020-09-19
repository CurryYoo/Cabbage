package com.example.cabbage.fragment;

import android.content.Context;
import android.content.Intent;
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
import com.example.cabbage.activity.PlusImageActivity;
import com.example.cabbage.activity.SurveyActivity;
import com.example.cabbage.adapter.ImageAdapter;
import com.example.cabbage.adapter.SingleImageAdapter;
import com.example.cabbage.network.HttpRequest;
import com.example.cabbage.network.NormalInfo;
import com.example.cabbage.network.PhotoListInfo;
import com.example.cabbage.network.ResultInfo;
import com.example.cabbage.network.SurveyInfo;
import com.example.cabbage.utils.MainConstant;
import com.example.cabbage.utils.MyGridView;
import com.example.cabbage.utils.PictureResultCode;
import com.example.cabbage.view.AutoClearEditText;
import com.example.cabbage.view.CountButton;
import com.example.cabbage.view.ExtraAttributeView;
import com.google.gson.JsonObject;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.cabbage.utils.StaticVariable.COUNT_EXTRA;
import static com.example.cabbage.utils.StaticVariable.SEPARATOR;
import static com.example.cabbage.utils.StaticVariable.STATUS_COPY;
import static com.example.cabbage.utils.StaticVariable.STATUS_NEW;
import static com.example.cabbage.utils.StaticVariable.STATUS_READ;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_HEADING;
import static com.example.cabbage.utils.UIUtils.checkIsValid;
import static com.example.cabbage.utils.UIUtils.getSystemTime;
import static com.example.cabbage.utils.UIUtils.selectPic;
import static com.example.cabbage.utils.UIUtils.setSelectionAndText;
import static com.example.cabbage.utils.UIUtils.setVisibilityOfUserDefined;
import static com.example.cabbage.utils.UIUtils.showBottomHelpDialog;

/**
 * Author:Kang
 * Date:2020/9/10
 * Description:结球期
 */
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
    @BindView(R.id.edt_location)
    EditText edtLocation;
    @BindView(R.id.img_grid_view)
    MyGridView imgGridView;
    private Context self;
    private Unbinder unbinder;
    //必需数据
    private String materialId;
    private String materialType;
    private String plantId;
    private String investigatingTime;
    private int status = STATUS_NEW;
    private String surveyId;
    private String surveyPeriod = SURVEY_PERIOD_HEADING;
    private String token;
    private int userId;
    private String nickname;
    private ExtraAttributeView extraAttribute = null;//额外性状
    private ExtraAttributeView extraRemark = null;//额外备注

    //图片
    private ImageAdapter imgAdapter;
    private ArrayList<String> imgList = new ArrayList<>();
    private HashMap<String, SingleImageAdapter> imgAdapters = new HashMap<>();
    private HashMap<String, ArrayList<String>> imgHashMap = new HashMap<>();

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

    public static HeadingPeriodFragment newInstance(String materialId
            , String materialType
            , String plantId
            , String investigatingTime
            , String surveyId
            , int status) {
        HeadingPeriodFragment newInstance = new HeadingPeriodFragment();
        Bundle bundle = new Bundle();
        bundle.putString("materialId", materialId);
        bundle.putString("materialType", materialType);
        bundle.putString("plantId", plantId);
        bundle.putString("investigatingTime", investigatingTime);
        bundle.putString("surveyId", surveyId);
        newInstance.setArguments(bundle);
        bundle.putInt("status", status);
        return newInstance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_heading_period, container, false);
        self = getActivity().getApplicationContext();
        unbinder = ButterKnife.bind(this, view);
        //验证用户
        SharedPreferences sp = self.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        token = sp.getString("token", "");
        userId = sp.getInt("userId", 1);
        nickname = sp.getString("username", "");

        //newInstance传递必需数据
        Bundle bundle = getArguments();
        materialId = bundle.getString("materialId");
        materialType = bundle.getString("materialType");
        plantId = bundle.getString("plantId");
        investigatingTime = bundle.getString("investigatingTime");
        surveyId = bundle.getString("surveyId");
        status = bundle.getInt("status", STATUS_NEW);

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
                initMaps();
                initBasicInfo(plantId);
                initData();
                initPictures();
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
        //图片
        imgAdapter = new ImageAdapter(self, imgList);
        imgGridView.setAdapter(imgAdapter);
        imgGridView.setOnItemClickListener((parent, view, position, id) -> {
            if (position == parent.getChildCount() - 1) {
                //如果“增加按钮形状的”图片的位置是最后一张，且添加了的图片的数量不超过MainConstant.MAX_SELECT_PIC_NUM张，才能点击
                if (imgList.size() == MainConstant.MAX_SELECT_PIC_NUM) {
                    //最多添加MainConstant.MAX_SELECT_PIC_NUM张图片
                    viewPluImg(position, PictureResultCode.IMG_HEADING);
                } else {
                    //添加凭证图片
                    selectPic(getActivity(), MainConstant.MAX_SELECT_PIC_NUM - imgList.size(), PictureResultCode.IMG_HEADING);
                }
            } else {
                viewPluImg(position, PictureResultCode.IMG_HEADING);
            }
        });
    }

    // 初始化基本数据
    private void initBasicInfo(String plantId) {
        // 展示基本信息
        edtMaterialId.setText(materialId);
        edtMaterialType.setText(materialType);
        edtPlantId.setText(plantId);
        edtInvestigatingTime.setText(investigatingTime);
        edtInvestigatingTime.setOnClickListener(v -> edtInvestigatingTime.setText(getSystemTime()));
        edtInvestigator.setText(nickname);

    }

    //弹出是否上传dialog
    private void showDialog() {
        final SweetAlertDialog saveDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
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
                        uploadPics(resultInfo.data.observationId);
                        SurveyActivity surveyActivity= (SurveyActivity) getActivity();
                        surveyActivity.initLastMaterial(surveyPeriod);
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
        String plantShapeData = plantShape.getSelectedItem().toString() + SEPARATOR + edtPlantShape.getText();
        String plantHeightData = plantHeight.getSelectedItem().toString() + SEPARATOR + edtPlantHeight.getText();
        String developmentDegreeData = developmentDegree.getSelectedItem().toString() + SEPARATOR + edtDevelopmentDegree.getText();
        String isKnotData = isKnot.getSelectedItem().toString() + SEPARATOR + edtIsKnot.getText();
        String flowerBudData = flowerBud.getSelectedItem().toString() + SEPARATOR + edtFlowerBud.getText();
        String lateralBudData = lateralBud.getSelectedItem().toString() + SEPARATOR + edtLateralBud.getText();
        String outerLeafLengthData = outerLeafLength.getSelectedItem().toString() + SEPARATOR + edtOuterLeafLength.getText();
        String outerLeafWidthData = outerLeafWidth.getSelectedItem().toString() + SEPARATOR + edtOuterLeafWidth.getText();
        String outerLeafShapeData = outerLeafShape.getSelectedItem().toString() + SEPARATOR + edtOuterLeafShape.getText();
        String outerLeafKeelColorData = outerLeafKeelColor.getSelectedItem().toString() + SEPARATOR + edtOuterLeafKeelColor.getText();
        String outerLeafKeelThicknessData = outerLeafKeelThickness.getSelectedItem().toString() + SEPARATOR + edtOuterLeafKeelThickness.getText();
        String outerLeafKeelLengthData = outerLeafKeelLength.getSelectedItem().toString() + SEPARATOR + edtOuterLeafKeelLength.getText();
        String outerLeafKeelWidthData = outerLeafKeelWidth.getSelectedItem().toString() + SEPARATOR + edtOuterLeafKeelWidth.getText();
        String leafKeelShapeData = leafKeelShape.getSelectedItem().toString() + SEPARATOR + edtLeafKeelShape.getText();
        String outerLeafCountData = outerLeafCount.getSelectedItem().toString() + SEPARATOR + edtOuterLeafCount.getText();

        //额外属性
        String extraAttributeData = "";
        String extraRemarkData = "";
        if (extraAttribute != null) {
            extraAttributeData = extraAttribute.getContent();
        }
        if (extraRemark != null) {
            extraRemarkData = extraRemark.getContent();
        }

        jsonObject.addProperty("plantType", plantShapeData);
        jsonObject.addProperty("plantHeight", plantHeightData);
        jsonObject.addProperty("developmentDegree", developmentDegreeData);
        jsonObject.addProperty("isBall", isKnotData);
        jsonObject.addProperty("bud", flowerBudData);
        jsonObject.addProperty("lateralBud", lateralBudData);
        jsonObject.addProperty("outerLeafLength", outerLeafLengthData);
        jsonObject.addProperty("outerLeafWidth", outerLeafWidthData);
        jsonObject.addProperty("outerLeafShape", outerLeafShapeData);
        jsonObject.addProperty("colorOfMiddleRib", outerLeafKeelColorData);
        jsonObject.addProperty("thicknessOfMiddleRib", outerLeafKeelThicknessData);
        jsonObject.addProperty("lengthOfMiddleRib", outerLeafKeelLengthData);
        jsonObject.addProperty("widthOfMiddleRib", outerLeafKeelWidthData);
        jsonObject.addProperty("middleRibShape", leafKeelShapeData);
        jsonObject.addProperty("numberOfOuterLeaves", outerLeafCountData);
        jsonObject.addProperty("spare1", extraAttributeData);
        jsonObject.addProperty("spare2", extraRemarkData);

        return jsonObject.toString();
    }

    private JsonObject getBasicInfoData() {
        String plantId = edtPlantId.getText().toString();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("materialType", materialType);
        jsonObject.addProperty("materialNumber", materialId);
        jsonObject.addProperty("plantNumber", plantId);
        jsonObject.addProperty("investigatingTime", getSystemTime());
        jsonObject.addProperty("location", edtLocation.getText().toString());
        jsonObject.addProperty("investigator", nickname);
        jsonObject.addProperty("userId", userId);

        return jsonObject;
    }

    // 更新上传图片
    private void uploadPics(String surveyId) {
        Map<String, ArrayList<String>> imageMap;
        imageMap = imgHashMap;
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
        edtLocation.setText(surveyInfo.data.location);
        setSelectionAndText(plantShape, edtPlantShape, surveyInfo.data.plantType);
        setSelectionAndText(plantHeight, edtPlantHeight, surveyInfo.data.plantHeight);
        setSelectionAndText(developmentDegree, edtDevelopmentDegree, surveyInfo.data.developmentDegree);
        setSelectionAndText(isKnot, edtIsKnot, surveyInfo.data.isBall);
        setSelectionAndText(flowerBud, edtFlowerBud, surveyInfo.data.bud);
        setSelectionAndText(lateralBud, edtLateralBud, surveyInfo.data.lateralBud);
        setSelectionAndText(outerLeafLength, edtOuterLeafLength, surveyInfo.data.outerLeafLength);
        setSelectionAndText(outerLeafWidth, edtOuterLeafLength, surveyInfo.data.outerLeafWidth);
        setSelectionAndText(outerLeafShape, edtOuterLeafShape, surveyInfo.data.outerLeafShape);
        setSelectionAndText(outerLeafKeelColor, edtOuterLeafKeelColor, surveyInfo.data.colorOfMiddleRib);
        setSelectionAndText(outerLeafKeelThickness, edtOuterLeafKeelThickness, surveyInfo.data.thicknessOfMiddleRib);
        setSelectionAndText(outerLeafKeelLength, edtOuterLeafKeelLength, surveyInfo.data.lengthOfMiddleRib);
        setSelectionAndText(outerLeafKeelWidth, edtOuterLeafKeelWidth, surveyInfo.data.lengthOfMiddleRib);
        setSelectionAndText(leafKeelShape, edtLeafKeelShape, surveyInfo.data.widthOfMiddleRib);
        setSelectionAndText(outerLeafCount, edtOuterLeafShape, surveyInfo.data.middleRibShape);

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

    // 初始化图片
    private void initPictures() {
        // 获取图片url
        List<String> picList = new ArrayList<>(Arrays.asList("common"));
        for (String specCharacter : picList) {
            HttpRequest.getPhoto(token, surveyId, specCharacter, new HttpRequest.IPhotoListCallback() {
                @Override
                public void onResponse(PhotoListInfo photoListInfo) {
                    List<PhotoListInfo.data> photoList = photoListInfo.data;
                    for (PhotoListInfo.data photo : photoList) {
                        String url = photo.url;

                        Map<String, ArrayList<String>> imageMap;
                        Map<String, SingleImageAdapter> adapterMap;
                        ImageAdapter commonAdapter;
                        imageMap = imgHashMap;
                        adapterMap = imgAdapters;
                        commonAdapter = imgAdapter;
                        if (imageMap.get(specCharacter) != null) {
                            imageMap.get(specCharacter).add(url);
                        }
                        if (adapterMap.get(specCharacter) != null) {
                            adapterMap.get(specCharacter).notifyDataSetChanged();
                        }
                        if (commonAdapter != null) {
                            commonAdapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onFailure() {

                }
            });
        }
    }


    //查看大图
    private void viewPluImg(int position, int resultCode) {
        Intent intent = new Intent(self, PlusImageActivity.class);
        intent.putStringArrayListExtra(MainConstant.IMG_LIST, imgList);
        intent.putExtra(MainConstant.POSITION, position);
        startActivityForResult(intent, resultCode);
    }

    private void initMaps() {
        imgHashMap.put("common", imgList);
    }
    // 处理选择的照片的地址
    private void refreshAdapter(List<LocalMedia> picList, int requestCode) {
        switch (requestCode) {
            case PictureResultCode.IMG_HEADING:
                for (LocalMedia localMedia : picList) {
                    //被压缩后的图片路径
                    if (localMedia.isCompressed()) {
                        String compressPath = localMedia.getCompressPath(); //压缩后的图片路径
                        imgList.add(compressPath);
                        imgAdapter.notifyDataSetChanged();
                    }
                }
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureResultCode.IMG_HEADING) {
            if (resultCode == MainConstant.RESULT_CODE_VIEW_IMG) {
                //查看大图页面删除了图片
                ArrayList<String> toDeletePicList = data.getStringArrayListExtra(MainConstant.IMG_LIST); //要删除的图片的集合
                imgList.clear();
                imgList.addAll(toDeletePicList);
                imgAdapter.notifyDataSetChanged();
            } else {
                refreshAdapter(PictureSelector.obtainMultipleResult(data), PictureResultCode.IMG_HEADING);
            }
            imgHashMap.put("common", imgList);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
