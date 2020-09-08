package com.example.cabbage.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cabbage.R;
import com.example.cabbage.view.AutoClearEditText;
import com.example.cabbage.view.CountButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.cabbage.utils.BasicUtil.showDatePickerDialog;
import static com.example.cabbage.utils.StaticVariable.STATUS_COPY;
import static com.example.cabbage.utils.StaticVariable.STATUS_NEW;
import static com.example.cabbage.utils.StaticVariable.STATUS_READ;

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
    EditText edtPlantHeight;
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
    @BindView(R.id.layout_custom_attribute3)
    LinearLayout layoutCustomAttribute3;
    @BindView(R.id.btn_add_attribute_heading)
    CountButton btnAddAttributeHeading;
    @BindView(R.id.btn_add_remark_heading)
    CountButton btnAddRemarkHeading;
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
//                initView(true);
                initBasicInfo("");
                break;
            case STATUS_READ:
//                initView(false);
//                initMaps();
                initBasicInfo(plantId);
//                initData(surveyPeriod);
//                initPictures();
                break;
            case STATUS_COPY:
//                initView(true);
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
