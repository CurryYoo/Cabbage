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
import com.example.cabbage.view.CustomAttributeView;
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
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_HEADING;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_SEEDLING;
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
    @BindView(R.id.layout_custom_attribute_heading)
    LinearLayout layoutCustomAttributeHeading;
    @BindView(R.id.btn_add_attribute_heading)
    CountButton btnAddAttributeHeading;
    @BindView(R.id.btn_add_remark_heading)
    CountButton btnAddRemarkHeading;
    @BindView(R.id.btn_upload_data_heading)
    Button btnUploadDataHeading;
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
    @BindView(R.id.btn_harvest_delay_time_late)
    Button btnHarvestDelayTimeLate;
    @BindView(R.id.layout_custom_attribute_harvest)
    LinearLayout layoutCustomAttributeHarvest;
    @BindView(R.id.btn_add_attribute_harvest)
    CountButton btnAddAttributeHarvest;
    @BindView(R.id.btn_add_remark_harvest)
    CountButton btnAddRemarkHarvest;
    @BindView(R.id.btn_upload_data_harvest)
    Button btnUploadDataHarvest;
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
    View.OnClickListener submitClickListener = v -> {
        if (checkIsValid(edtPlantId)) {
            Toast.makeText(self, R.string.check_required, Toast.LENGTH_SHORT).show();
        } else {
            switch (v.getId()) {
                case R.id.btn_upload_data_heading:
                    showDialog(SURVEY_PERIOD_HEADING);
                    break;
                case R.id.btn_upload_data_harvest:
                    showDialog(SURVEY_PERIOD_HARVEST);
                    break;
                default:
                    break;
            }
        }
    };
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

        //额外性状和备注
        btnAddAttributeHeading.setCount(1);
        btnAddRemarkHeading.setCount(1);
        btnAddAttributeHarvest.setCount(1);
        btnAddRemarkHarvest.setCount(1);
        addExtraAttributeListener(btnAddAttributeHeading, layoutCustomAttributeHeading, "spare", SURVEY_PERIOD_SEEDLING);
        addRemarkAttributeListener(btnAddRemarkHeading, layoutCustomAttributeHeading, "spare", SURVEY_PERIOD_SEEDLING);
        addExtraAttributeListener(btnAddAttributeHarvest, layoutCustomAttributeHarvest, "spare", SURVEY_PERIOD_SEEDLING);
        addRemarkAttributeListener(btnAddRemarkHarvest, layoutCustomAttributeHarvest, "spare", SURVEY_PERIOD_SEEDLING);
        //提交按钮
        if (editable) {
            btnUploadDataHeading.setOnClickListener(submitClickListener);
            btnUploadDataHarvest.setOnClickListener(submitClickListener);
        } else {
            btnUploadDataHeading.setVisibility(View.GONE);
            btnUploadDataHarvest.setVisibility(View.GONE);
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
            String mPeriodData = getPeriodData(surveyPeriod);
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

    private String getPeriodData(String surveyPeriod) {
        switch (surveyPeriod) {
            case SURVEY_PERIOD_HEADING:
                return getHeadingPeriodData();
            case SURVEY_PERIOD_HARVEST:
                return getHarvestPeriodData();
            default:
                return "";
        }
    }

    private String getHeadingPeriodData() {
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

        return jsonObject.toString();
    }

    private String getHarvestPeriodData() {
        //成熟期
        JsonObject jsonObject = getBasicInfoData();

        String strobilusHeadClosureTypeString = strobilusHeadClosureType.getSelectedItem().toString() + SEPARATOR + edtStrobilusHeadClosureType.getText();
        String strobilusHeadConjugationTypeString = strobilusHeadConjugationType.getSelectedItem().toString() + SEPARATOR + edtStrobilusHeadConjugationType.getText();
        String strobilusHeadShapeString = strobilusHeadShape.getSelectedItem().toString() + SEPARATOR + edtStrobilusHeadShape.getText();
        String strobilusTopColorString = strobilusTopColor.getSelectedItem().toString() + SEPARATOR + edtStrobilusTopColor.getText();
        String pstrobilusTopGreenDegreeString = strobilusTopGreenDegree.getSelectedItem().toString() + SEPARATOR + edtStrobilusTopGreenDegree.getText();
        String strobilusShapeString = strobilusShape.getSelectedItem().toString() + SEPARATOR + edtStrobilusShape.getText();
        String strobilusHeightString = strobilusHeight.getSelectedItem().toString() + SEPARATOR + edtStrobilusHeight.getText();
        String strobilusWidthString = strobilusWidth.getSelectedItem().toString() + SEPARATOR + edtStrobilusWidth.getText();
        String StrobilusMidWidthString = edtStrobilusMidWidth.getText().toString();
        String StrobilusEndWidthString = edtStrobilusEndWidth.getText().toString();
        String strobilusCompactionString = strobilusCompaction.getSelectedItem().toString() + SEPARATOR + edtStrobilusCompaction.getText();
        String strobilusInnerColorString = strobilusInnerColor.getSelectedItem().toString() + SEPARATOR + edtStrobilusInnerColor.getText();
        String strobilusLeafCountString = strobilusLeafCount.getSelectedItem().toString() + SEPARATOR + edtStrobilusLeafCount.getText();
        String softLeafRateString = softLeafRate.getSelectedItem().toString() + SEPARATOR + edtSoftLeafRate.getText();
        String strobilusMassString = strobilusMass.getSelectedItem().toString() + SEPARATOR + edtStrobilusMass.getText();
        String cleanVegRateString = cleanVegRate.getSelectedItem().toString() + SEPARATOR + edtCleanVegRate.getText();
        String centerShapeString = centerShape.getSelectedItem().toString() + SEPARATOR + edtCenterShape.getText();
        String centerLengthString = centerLength.getSelectedItem().toString() + SEPARATOR + edtCenterLength.getText();
        String harvestTypeString = harvestType.getSelectedItem().toString() + SEPARATOR + edtHarvestType.getText();
        String harvestDelayTimeEarlyString = harvestDelayTimeEarly.getSelectedItem().toString() + SEPARATOR + edtHarvestDelayTimeEarly.getText();
        String harvestDelayTimeLateString = harvestDelayTimeLate.getSelectedItem().toString() + SEPARATOR + edtHarvestDelayTimeLate.getText();

        jsonObject.addProperty("ballTopClosedType", strobilusHeadClosureTypeString);
        jsonObject.addProperty("ballTopHoldType", strobilusHeadConjugationTypeString);
        jsonObject.addProperty("ballTopShape", strobilusHeadShapeString);
        jsonObject.addProperty("upperLeafBulbColor", strobilusTopColorString);
        jsonObject.addProperty("greenDegreeOfUpperLeafBall", pstrobilusTopGreenDegreeString);
        jsonObject.addProperty("leafBallShape", strobilusShapeString);
        jsonObject.addProperty("leafBallHeight", strobilusHeightString);
        jsonObject.addProperty("leafBallWidth", strobilusWidthString);
        jsonObject.addProperty("leafBallMiddleWidth", StrobilusMidWidthString);
        jsonObject.addProperty("leafBulbEndWidth", StrobilusEndWidthString);
        jsonObject.addProperty("leafBallCompactness", strobilusCompactionString);
        jsonObject.addProperty("innerColorOfLeafBall", strobilusInnerColorString);
        jsonObject.addProperty("numberOfBulbs", strobilusLeafCountString);
        jsonObject.addProperty("softLeafRate", softLeafRateString);
        jsonObject.addProperty("leafBallWeight", strobilusMassString);
        jsonObject.addProperty("netVegetableRate", cleanVegRateString);
        jsonObject.addProperty("centerColumnShape", centerShapeString);
        jsonObject.addProperty("centerColumnLength", centerLengthString);
        jsonObject.addProperty("maturity", harvestTypeString);
        jsonObject.addProperty("extendedHarvestPeriodEarly", harvestDelayTimeEarlyString);
        jsonObject.addProperty("extendedHarvestPeriodMidLate", harvestDelayTimeLateString);

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
    private void addExtraAttributeListener(CountButton btnAddAttribute, LinearLayout layout, String keyName, String surveyPeriod) {
        btnAddAttribute.setOnClickListener(v -> {
            btnAddAttribute.subtractCount();
            CustomAttributeView customAttributeView = new CustomAttributeView(self, 1, keyName);
            Button btnDelete = customAttributeView.findViewById(R.id.btn_delete);
            btnDelete.setOnClickListener(v1 -> {
                customAttributeView.removeAllViews();
                btnAddAttribute.addCount();
            });
            layout.addView(customAttributeView);
            addToAttributeList(surveyPeriod, customAttributeView);
        });
    }


    //添加备注
    private void addRemarkAttributeListener(CountButton btnAddRemark, LinearLayout layout, String keyName, String surveyPeriod) {
        btnAddRemark.setOnClickListener(v -> {
            btnAddRemark.subtractCount();
            CustomAttributeView customAttributeView = new CustomAttributeView(self, 0, keyName);
            Button btnDelete = customAttributeView.findViewById(R.id.btn_delete);
            btnDelete.setOnClickListener(v1 -> {
                customAttributeView.removeAllViews();
                btnAddRemark.addCount();
            });
            layout.addView(customAttributeView);
            addToAttributeList(surveyPeriod, customAttributeView);
        });
    }


    //增加新的view到对应时期到list中
    private void addToAttributeList(String surveyPeriod, CustomAttributeView customAttributeView) {
        switch (surveyPeriod) {
            case SURVEY_PERIOD_HEADING:
                //TODO
                break;
            case SURVEY_PERIOD_HARVEST:
                //TODO
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
