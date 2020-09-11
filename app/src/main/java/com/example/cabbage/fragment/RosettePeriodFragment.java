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
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cabbage.R;
import com.example.cabbage.activity.PlusImageActivity;
import com.example.cabbage.adapter.ImageAdapter;
import com.example.cabbage.adapter.SingleImageAdapter;
import com.example.cabbage.network.HttpRequest;
import com.example.cabbage.network.NormalInfo;
import com.example.cabbage.network.PhotoListInfo;
import com.example.cabbage.network.ResultInfo;
import com.example.cabbage.network.SurveyInfo;
import com.example.cabbage.utils.MainConstant;
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

import static com.example.cabbage.utils.BasicUtil.showDatePickerDialog;
import static com.example.cabbage.utils.StaticVariable.COUNT_EXTRA;
import static com.example.cabbage.utils.StaticVariable.STATUS_COPY;
import static com.example.cabbage.utils.StaticVariable.STATUS_NEW;
import static com.example.cabbage.utils.StaticVariable.STATUS_READ;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_ROSETTE;
import static com.example.cabbage.utils.UIUtils.checkIsValid;
import static com.example.cabbage.utils.UIUtils.selectPic;
import static com.example.cabbage.utils.UIUtils.setSelectionAndText;
import static com.example.cabbage.utils.UIUtils.setVisibilityOfUserDefined;
import static com.example.cabbage.utils.UIUtils.showBottomHelpDialog;
import static java.io.File.separator;

/**
 * Author:Kang
 * Date:2020/9/10
 * Description:莲座期
 */
public class RosettePeriodFragment extends Fragment {

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
    @BindView(R.id.img_rosette_period)
    GridView imgRosettePeriod;
    @BindView(R.id.plant_shape)
    Spinner plantShape;
    @BindView(R.id.edt_plant_shape)
    AutoClearEditText edtPlantShape;
    @BindView(R.id.btn_plant_shape)
    Button btnPlantShape;
    @BindView(R.id.layout_repeated_true_leaf_color)
    LinearLayout layoutRepeatedTrueLeafColor;
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
    @BindView(R.id.edt_leaf_count)
    EditText edtLeafCount;
    @BindView(R.id.btn_leaf_count)
    Button btnLeafCount;
    @BindView(R.id.edt_soft_leaf_thickness)
    EditText edtSoftLeafThickness;
    @BindView(R.id.btn_soft_leaf_thickness)
    Button btnSoftLeafThickness;
    @BindView(R.id.leaf_length)
    Spinner leafLength;
    @BindView(R.id.edt_leaf_length)
    AutoClearEditText edtLeafLength;
    @BindView(R.id.btn_leaf_length)
    Button btnLeafLength;
    @BindView(R.id.leaf_width)
    Spinner leafWidth;
    @BindView(R.id.edt_leaf_width)
    AutoClearEditText edtLeafWidth;
    @BindView(R.id.btn_leaf_width)
    Button btnLeafWidth;
    @BindView(R.id.leaf_shape)
    Spinner leafShape;
    @BindView(R.id.edt_leaf_shape)
    AutoClearEditText edtLeafShape;
    @BindView(R.id.btn_leaf_shape)
    Button btnLeafShape;
    @BindView(R.id.leaf_color)
    Spinner leafColor;
    @BindView(R.id.edt_leaf_color)
    AutoClearEditText edtLeafColor;
    @BindView(R.id.btn_leaf_color)
    Button btnLeafColor;
    @BindView(R.id.leaf_luster)
    Spinner leafLuster;
    @BindView(R.id.edt_leaf_luster)
    AutoClearEditText edtLeafLuster;
    @BindView(R.id.btn_leaf_luster)
    Button btnLeafLuster;
    @BindView(R.id.leaf_fuzz)
    Spinner leafFuzz;
    @BindView(R.id.edt_leaf_fuzz)
    AutoClearEditText edtLeafFuzz;
    @BindView(R.id.btn_leaf_fuzz)
    Button btnLeafFuzz;
    @BindView(R.id.leaf_margin_undulance)
    Spinner leafMarginUndulance;
    @BindView(R.id.edt_leaf_margin_undulance)
    AutoClearEditText edtLeafMarginUndulance;
    @BindView(R.id.btn_leaf_margin_undulance)
    Button btnLeafMarginUndulance;
    @BindView(R.id.leaf_margin_sawtooth)
    Spinner leafMarginSawtooth;
    @BindView(R.id.edt_leaf_margin_sawtooth)
    AutoClearEditText edtLeafMarginSawtooth;
    @BindView(R.id.btn_leaf_margin_sawtooth)
    Button btnLeafMarginSawtooth;
    @BindView(R.id.leaf_smoothness)
    Spinner leafSmoothness;
    @BindView(R.id.edt_leaf_smoothness)
    AutoClearEditText edtLeafSmoothness;
    @BindView(R.id.btn_leaf_smoothness)
    Button btnLeafSmoothness;
    @BindView(R.id.leaf_protuberance)
    Spinner leafProtuberance;
    @BindView(R.id.edt_leaf_protuberance)
    AutoClearEditText edtLeafProtuberance;
    @BindView(R.id.btn_leaf_protuberance)
    Button btnLeafProtuberance;
    @BindView(R.id.leaf_vein_livingness)
    Spinner leafVeinLivingness;
    @BindView(R.id.edt_leaf_vein_livingness)
    AutoClearEditText edtLeafVeinLivingness;
    @BindView(R.id.btn_leaf_vein_livingness)
    Button btnLeafVeinLivingness;
    @BindView(R.id.leaf_keel_livingness)
    Spinner leafKeelLivingness;
    @BindView(R.id.edt_leaf_keel_livingness)
    AutoClearEditText edtLeafKeelLivingness;
    @BindView(R.id.btn_leaf_keel_livingness)
    Button btnLeafKeelLivingness;
    @BindView(R.id.leaf_curliness)
    Spinner leafCurliness;
    @BindView(R.id.edt_leaf_curliness)
    AutoClearEditText edtLeafCurliness;
    @BindView(R.id.btn_leaf_curliness)
    Button btnLeafCurliness;
    @BindView(R.id.leaf_curliness_part)
    Spinner leafCurlinessPart;
    @BindView(R.id.edt_leaf_curliness_part)
    AutoClearEditText edtLeafCurlinessPart;
    @BindView(R.id.btn_leaf_curliness_part)
    Button btnLeafCurlinessPart;
    @BindView(R.id.leaf_texture)
    Spinner leafTexture;
    @BindView(R.id.edt_leaf_texture)
    AutoClearEditText edtLeafTexture;
    @BindView(R.id.btn_leaf_texture)
    Button btnLeafTexture;
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
    private String surveyPeriod=SURVEY_PERIOD_ROSETTE;
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
                case R.id.leaf_shape:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.leaf_shape).length - 1, edtLeafShape);
                    break;
                case R.id.leaf_color:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.leaf_color).length - 1, edtLeafColor);
                    break;
                case R.id.leaf_luster:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.leaf_luster).length - 1, edtLeafLuster);
                    break;
                case R.id.leaf_fuzz:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.leaf_fuzz).length - 1, edtLeafFuzz);
                    break;
                case R.id.leaf_margin_undulance:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.leaf_margin_undulance).length - 1, edtLeafMarginUndulance);
                    break;
                case R.id.leaf_margin_sawtooth:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.leaf_margin_sawtooth).length - 1, edtLeafMarginSawtooth);
                    break;
                case R.id.leaf_smoothness:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.leaf_smoothness).length - 1, edtLeafSmoothness);
                    break;
                case R.id.leaf_protuberance:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.leaf_protuberance).length - 1, edtLeafProtuberance);
                    break;
                case R.id.leaf_vein_livingness:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.leaf_vein_livingness).length - 1, edtLeafVeinLivingness);
                    break;
                case R.id.leaf_keel_livingness:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.leaf_keel_livingness).length - 1, edtLeafKeelLivingness);
                    break;
                case R.id.leaf_curliness:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.leaf_curliness).length - 1, edtLeafCurliness);
                    break;
                case R.id.leaf_curliness_part:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.leaf_curliness_part).length - 1, edtLeafCurlinessPart);
                    break;
                case R.id.leaf_texture:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.leaf_texture).length - 1, edtLeafTexture);
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
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_rosette_plant_shape));
                break;
            case R.id.btn_plant_height:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_rosette_plant_height));
                break;
            case R.id.btn_development_degree:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_rosette_development_degree));
                break;
            case R.id.btn_leaf_count:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_leaf_count));
                break;
            case R.id.btn_soft_leaf_thickness:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_soft_leaf_thickness));
                break;
            case R.id.btn_leaf_length:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_leaf_length));
                break;
            case R.id.btn_leaf_width:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_leaf_width));
                break;
            case R.id.btn_leaf_shape:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_leaf_shape));
                break;
            case R.id.btn_leaf_color:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_leaf_color));
                break;
            case R.id.btn_leaf_luster:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_leaf_luster));
                break;
            case R.id.btn_leaf_fuzz:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_leaf_fuzz));
                break;
            case R.id.btn_leaf_margin_undulance:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_leaf_margin_undulance));
                break;
            case R.id.btn_leaf_margin_sawtooth:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_leaf_margin_sawtooth));
                break;
            case R.id.btn_leaf_smoothness:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_leaf_smoothness));
                break;
            case R.id.btn_leaf_protuberance:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_leaf_protuberance));
                break;
            case R.id.btn_leaf_vein_livingness:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_leaf_vein_livingness));
                break;
            case R.id.btn_leaf_keel_livingness:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_leaf_keel_livingness));
                break;
            case R.id.btn_leaf_curliness:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_leaf_curliness));
                break;
            case R.id.btn_leaf_curliness_part:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_leaf_curliness_part));
                break;
            case R.id.btn_leaf_texture:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_leaf_texture));
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
            showDialog();
        }
    };

    public static RosettePeriodFragment newInstance() {
        return new RosettePeriodFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rosette_period, container, false);
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
        //清除view，防止重复加载
        imgRosettePeriod.removeAllViewsInLayout();
        imgRosettePeriod.postInvalidate();
        layoutCustomAttribute.removeAllViews();

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
                initMaps();
                initBasicInfo("");
                initData();
                break;
            default:
                break;
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

    private void initView(boolean editable) {
        //显示用户自定义填空
        plantShape.setOnItemSelectedListener(onItemSelectedListener);
        leafShape.setOnItemSelectedListener(onItemSelectedListener);
        leafColor.setOnItemSelectedListener(onItemSelectedListener);
        leafLuster.setOnItemSelectedListener(onItemSelectedListener);
        leafFuzz.setOnItemSelectedListener(onItemSelectedListener);
        leafMarginUndulance.setOnItemSelectedListener(onItemSelectedListener);
        leafMarginSawtooth.setOnItemSelectedListener(onItemSelectedListener);
        leafSmoothness.setOnItemSelectedListener(onItemSelectedListener);
        leafProtuberance.setOnItemSelectedListener(onItemSelectedListener);
        leafVeinLivingness.setOnItemSelectedListener(onItemSelectedListener);
        leafKeelLivingness.setOnItemSelectedListener(onItemSelectedListener);
        leafCurliness.setOnItemSelectedListener(onItemSelectedListener);
        leafCurlinessPart.setOnItemSelectedListener(onItemSelectedListener);
        leafTexture.setOnItemSelectedListener(onItemSelectedListener);
        //显示底部提示弹出框
        btnPlantShape.setOnClickListener(helpClickListener);
        btnPlantHeight.setOnClickListener(helpClickListener);
        btnDevelopmentDegree.setOnClickListener(helpClickListener);
        btnLeafCount.setOnClickListener(helpClickListener);
        btnSoftLeafThickness.setOnClickListener(helpClickListener);
        btnLeafLength.setOnClickListener(helpClickListener);
        btnLeafWidth.setOnClickListener(helpClickListener);
        btnLeafShape.setOnClickListener(helpClickListener);
        btnLeafColor.setOnClickListener(helpClickListener);
        btnLeafLuster.setOnClickListener(helpClickListener);
        btnLeafFuzz.setOnClickListener(helpClickListener);
        btnLeafMarginUndulance.setOnClickListener(helpClickListener);
        btnLeafMarginSawtooth.setOnClickListener(helpClickListener);
        btnLeafSmoothness.setOnClickListener(helpClickListener);
        btnLeafProtuberance.setOnClickListener(helpClickListener);
        btnLeafVeinLivingness.setOnClickListener(helpClickListener);
        btnLeafKeelLivingness.setOnClickListener(helpClickListener);
        btnLeafCurliness.setOnClickListener(helpClickListener);
        btnLeafCurlinessPart.setOnClickListener(helpClickListener);
        btnLeafTexture.setOnClickListener(helpClickListener);

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


        //添加莲座期总图片
        imgAdapter = new ImageAdapter(self, imgList);
        imgRosettePeriod.setAdapter(imgAdapter);
        imgRosettePeriod.setOnItemClickListener((parent, view, position, id) -> {
            if (position == parent.getChildCount() - 1) {
                //如果“增加按钮形状的”图片的位置是最后一张，且添加了的图片的数量不超过MainConstant.MAX_SELECT_PIC_NUM张，才能点击
                if (imgList.size() == MainConstant.MAX_SELECT_PIC_NUM) {
                    //最多添加MainConstant.MAX_SELECT_PIC_NUM张图片
                    viewPluImg(position, PictureResultCode.ROSETTE_PERIOD);
                } else {
                    //添加凭证图片
                    selectPic(getActivity(), MainConstant.MAX_SELECT_PIC_NUM - imgList.size(), PictureResultCode.ROSETTE_PERIOD);
                }
            } else {
                viewPluImg(position, PictureResultCode.ROSETTE_PERIOD);
            }
        });
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
                        uploadPics(resultInfo.data.observationId);
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

        String plantShapeString = plantShape.getSelectedItem().toString() + separator + edtPlantShape.getText();
        String plantHeightString = plantHeight.getSelectedItem().toString() + separator + edtPlantHeight.getText();
        String developmentDegreeString = developmentDegree.getSelectedItem().toString() + separator + edtDevelopmentDegree.getText();
        String leafCountString = edtLeafCount.getText().toString();
        String softLeafThicknessString = edtSoftLeafThickness.getText().toString();
        String leafLengthString = leafLength.getSelectedItem().toString() + separator + edtLeafLength.getText();
        String leafWidthString = leafWidth.getSelectedItem().toString() + separator + edtLeafWidth.getText();
        String leafShapeString = leafShape.getSelectedItem().toString() + separator + edtLeafShape.getText();
        String leafColorString = leafColor.getSelectedItem().toString() + separator + edtLeafColor.getText();
        String leafLusterString = leafLuster.getSelectedItem().toString() + separator + edtLeafLuster.getText();
        String leafFuzzString = leafFuzz.getSelectedItem().toString() + separator + edtLeafFuzz.getText();
        String leafMarginUndulanceString = leafMarginUndulance.getSelectedItem().toString() + separator + edtLeafMarginUndulance.getText();
        String leafMarginSawtoothString = leafMarginSawtooth.getSelectedItem().toString() + separator + edtLeafMarginSawtooth.getText();
        String leafSmoothnessString = leafSmoothness.getSelectedItem().toString() + separator + edtLeafSmoothness.getText();
        String leafProtuberanceString = leafProtuberance.getSelectedItem().toString() + separator + edtLeafProtuberance.getText();
        String leafVeinLivingnessString = leafVeinLivingness.getSelectedItem().toString() + separator + edtLeafVeinLivingness.getText();
        String leafKeelLivingnessString = leafKeelLivingness.getSelectedItem().toString() + separator + edtLeafKeelLivingness.getText();
        String leafCurlinessString = leafCurliness.getSelectedItem().toString() + separator + edtLeafCurliness.getText();
        String leafCurlinessPartString = leafCurlinessPart.getSelectedItem().toString() + separator + edtLeafCurlinessPart.getText();
        String leafTextureString = leafTexture.getSelectedItem().toString() + separator + edtLeafTexture.getText();

        //额外属性
        String extraAttributeData = "";
        String extraRemarkData = "";
        if (extraAttribute != null) {
            extraAttributeData = extraAttribute.getContent();
        }
        if (extraRemark != null) {
            extraRemarkData = extraRemark.getContent();
        }

        jsonObject.addProperty("plantType", plantShapeString);
        jsonObject.addProperty("plantHeight", plantHeightString);
        jsonObject.addProperty("developmentDegree", developmentDegreeString);
        jsonObject.addProperty("numberOfLeaves", leafCountString);
        jsonObject.addProperty("thicknessOfSoftLeaf", softLeafThicknessString);
        jsonObject.addProperty("bladeLength", leafLengthString);
        jsonObject.addProperty("bladeWidth", leafWidthString);
        jsonObject.addProperty("leafShape", leafShapeString);
        jsonObject.addProperty("leafColor", leafColorString);
        jsonObject.addProperty("leafLuster", leafLusterString);
        jsonObject.addProperty("leafFluff", leafFuzzString);
        jsonObject.addProperty("leafMarginWavy", leafMarginUndulanceString);
        jsonObject.addProperty("leafMarginSerrate", leafMarginSawtoothString);
        jsonObject.addProperty("bladeSmooth", leafSmoothnessString);
        jsonObject.addProperty("sizeOfVesicles", leafProtuberanceString);
        jsonObject.addProperty("freshnessOfLeafVein", leafVeinLivingnessString);
        jsonObject.addProperty("brightnessOfMiddleRib", leafKeelLivingnessString);
        jsonObject.addProperty("leafCurl", leafCurlinessString);
        jsonObject.addProperty("leafCurlPart", leafCurlinessPartString);
        jsonObject.addProperty("leafTexture", leafTextureString);

        //TODO 莲座期额外属性有bug
//        jsonObject.addProperty("spare1", extraAttributeString);
//        jsonObject.addProperty("spare2", extraRemarkString);

        return jsonObject.toString();
    }

    // 更新上传图片
    private void uploadPics( String surveyId) {
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
        setSelectionAndText(plantShape, edtPlantShape, surveyInfo.data.plantType);
        setSelectionAndText(plantHeight, edtPlantHeight, surveyInfo.data.plantHeight);
        setSelectionAndText(developmentDegree, edtDevelopmentDegree, surveyInfo.data.developmentDegree);
        edtLeafCount.setText(surveyInfo.data.numberOfLeaves);
        edtSoftLeafThickness.setText(surveyInfo.data.thicknessOfSoftLeaf);
        setSelectionAndText(leafLength, edtLeafLength, surveyInfo.data.bladeLength);
        setSelectionAndText(leafWidth, edtLeafWidth, surveyInfo.data.bladeWidth);
        setSelectionAndText(leafShape, edtLeafShape, surveyInfo.data.leafShape);
        setSelectionAndText(leafColor, edtLeafColor, surveyInfo.data.leafColor);
        setSelectionAndText(leafLuster, edtLeafLuster, surveyInfo.data.leafLuster);
        setSelectionAndText(leafFuzz, edtLeafFuzz, surveyInfo.data.leafFluff);
        setSelectionAndText(leafMarginUndulance, edtLeafMarginUndulance, surveyInfo.data.leafMarginWavy);
        setSelectionAndText(leafMarginSawtooth, edtLeafMarginSawtooth, surveyInfo.data.leafMarginSerrate);
        setSelectionAndText(leafSmoothness, edtLeafSmoothness, surveyInfo.data.bladeSmooth);
        setSelectionAndText(leafProtuberance, edtLeafProtuberance, surveyInfo.data.sizeOfVesicles);
        setSelectionAndText(leafVeinLivingness, edtLeafVeinLivingness, surveyInfo.data.freshnessOfLeafVein);
        setSelectionAndText(leafKeelLivingness, edtLeafKeelLivingness, surveyInfo.data.brightnessOfMiddleRib);
        setSelectionAndText(leafCurliness, edtLeafCurliness, surveyInfo.data.leafCurl);
        setSelectionAndText(leafCurlinessPart, edtLeafCurlinessPart, surveyInfo.data.leafCurlPart);
        setSelectionAndText(leafTexture, edtLeafTexture, surveyInfo.data.leafTexture);

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
        switch (resultCode) {
            case PictureResultCode.ROSETTE_PERIOD:
                intent.putStringArrayListExtra(MainConstant.IMG_LIST, imgList);
                break;
            default:
                break;
        }
        intent.putExtra(MainConstant.POSITION, position);
        startActivityForResult(intent, resultCode);
    }

    // 处理选择的照片的地址
    private void refreshAdapter(List<LocalMedia> picList, int requestCode) {
        switch (requestCode) {
            case PictureResultCode.ROSETTE_PERIOD:
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

    private void initMaps() {
        imgHashMap.put("common", imgList);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PictureResultCode.ROSETTE_PERIOD:
                if (resultCode == MainConstant.RESULT_CODE_VIEW_IMG) {
                    //查看大图页面删除了图片
                    ArrayList<String> toDeletePicList = data.getStringArrayListExtra(MainConstant.IMG_LIST); //要删除的图片的集合
                    imgList.clear();
                    imgList.addAll(toDeletePicList);
                    imgAdapter.notifyDataSetChanged();
                } else {
                    refreshAdapter(PictureSelector.obtainMultipleResult(data), PictureResultCode.ROSETTE_PERIOD);
                }
                imgHashMap.put("common", imgList);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
