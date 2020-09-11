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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cabbage.R;
import com.example.cabbage.activity.PlusImageActivity;
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
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_SEEDLING;
import static com.example.cabbage.utils.UIUtils.checkIsValid;
import static com.example.cabbage.utils.UIUtils.selectPic;
import static com.example.cabbage.utils.UIUtils.setSelectionAndText;
import static com.example.cabbage.utils.UIUtils.setVisibilityOfUserDefined;
import static com.example.cabbage.utils.UIUtils.showBottomHelpDialog;
import static java.io.File.separator;

/**
 * Author:Kang
 * Date:2020/9/10
 * Description:种子收获期
 */
public class SeedlingPeriodFragment extends Fragment {

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
    @BindView(R.id.cotyledon_size)
    Spinner cotyledonSize;
    @BindView(R.id.edt_cotyledon_size)
    AutoClearEditText edtCotyledonSize;
    @BindView(R.id.btn_cotyledon_size)
    Button btnCotyledonSize;
    @BindView(R.id.btn_add_cotyledon_size)
    CountButton btnAddCotyledonSize;
    @BindView(R.id.layout_cotyledon_size)
    LinearLayout layoutCotyledonSize;
    @BindView(R.id.layout_repeated_cotyledon_size)
    LinearLayout layoutRepeatedCotyledonSize;
    @BindView(R.id.cotyledon_color)
    Spinner cotyledonColor;
    @BindView(R.id.img_cotyledon_color)
    RecyclerView imgCotyledonColor;
    @BindView(R.id.edt_cotyledon_color)
    AutoClearEditText edtCotyledonColor;
    @BindView(R.id.btn_cotyledon_color)
    Button btnCotyledonColor;
    @BindView(R.id.layout_cotyledon_color)
    LinearLayout layoutCotyledonColor;
    @BindView(R.id.layout_repeated_cotyledon_color)
    LinearLayout layoutRepeatedCotyledonColor;
    @BindView(R.id.cotyledon_count)
    Spinner cotyledonCount;
    @BindView(R.id.img_cotyledon_count)
    RecyclerView imgCotyledonCount;
    @BindView(R.id.edt_cotyledon_count)
    AutoClearEditText edtCotyledonCount;
    @BindView(R.id.btn_cotyledon_count)
    Button btnCotyledonCount;
    @BindView(R.id.layout_repeated_cotyledon_count)
    LinearLayout layoutRepeatedCotyledonCount;
    @BindView(R.id.cotyledon_shape)
    Spinner cotyledonShape;
    @BindView(R.id.img_cotyledon_shape)
    RecyclerView imgCotyledonShape;
    @BindView(R.id.edt_cotyledon_shape)
    AutoClearEditText edtCotyledonShape;
    @BindView(R.id.btn_cotyledon_shape)
    Button btnCotyledonShape;
    @BindView(R.id.layout_repeated_cotyledon_shape)
    LinearLayout layoutRepeatedCotyledonShape;
    @BindView(R.id.heart_leaf_color)
    Spinner heartLeafColor;
    @BindView(R.id.edt_heart_leaf_color)
    AutoClearEditText edtHeartLeafColor;
    @BindView(R.id.btn_heart_leaf_color)
    Button btnHeartLeafColor;
    @BindView(R.id.layout_repeated_heart_leaf_color)
    LinearLayout layoutRepeatedHeartLeafColor;
    @BindView(R.id.true_leaf_color)
    Spinner trueLeafColor;
    @BindView(R.id.edt_true_leaf_color)
    AutoClearEditText edtTrueLeafColor;
    @BindView(R.id.btn_true_leaf_color)
    Button btnTrueLeafColor;
    @BindView(R.id.layout_repeated_true_leaf_color)
    LinearLayout layoutRepeatedTrueLeafColor;
    @BindView(R.id.true_leaf_length)
    Spinner trueLeafLength;
    @BindView(R.id.edt_true_leaf_length)
    AutoClearEditText edtTrueLeafLength;
    @BindView(R.id.btn_true_leaf_length)
    Button btnTrueLeafLength;
    @BindView(R.id.btn_add_true_leaf_length)
    CountButton btnAddTrueLeafLength;
    @BindView(R.id.layout_repeated_true_leaf_length)
    LinearLayout layoutRepeatedTrueLeafLength;
    @BindView(R.id.true_leaf_width)
    Spinner trueLeafWidth;
    @BindView(R.id.edt_true_leaf_width)
    AutoClearEditText edtTrueLeafWidth;
    @BindView(R.id.btn_true_leaf_width)
    Button btnTrueLeafWidth;
    @BindView(R.id.btn_add_true_leaf_width)
    CountButton btnAddTrueLeafWidth;
    @BindView(R.id.layout_repeated_true_leaf_width)
    LinearLayout layoutRepeatedTrueLeafWidth;
    @BindView(R.id.layout_custom_attribute)
    LinearLayout layoutCustomAttribute;
    @BindView(R.id.btn_add_attribute)
    CountButton btnAddAttribute;
    @BindView(R.id.btn_add_remark)
    CountButton btnAddRemark;
    @BindView(R.id.btn_upload_data)
    Button btnUploadData;
    //必需数据
    private String materialId;
    private String materialType;
    private String plantId;
    private String investigatingTime;
    private int status = STATUS_NEW;
    private String surveyId;
    private String surveyPeriod=SURVEY_PERIOD_SEEDLING;
    private String token;
    private int userId;
    private String nickname;
    private Context self;
    private Unbinder unbinder;
    private ExtraAttributeView extraAttribute = null;//额外性状
    private ExtraAttributeView extraRemark = null;//额外备注
    //图片

    private HashMap<String, ArrayList<String>> imgHashMap = new HashMap<>();
    private HashMap<String, SingleImageAdapter> imgAdapter = new HashMap<>();
    private SingleImageAdapter mCotyledonColorAdapter;
    private ArrayList<String> mCotyledonColorImgList = new ArrayList<>();
    private SingleImageAdapter mCotyledonCountAdapter;
    private ArrayList<String> mCotyledonCountImgList = new ArrayList<>();
    private SingleImageAdapter mCotyledonShapeAdapter;
    private ArrayList<String> mCotyledonShapeImgList = new ArrayList<>();

    //spinner选择监听，选择其他是，显示自定义填空
    Spinner.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()) {
                case R.id.cotyledon_size:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.cotyledon_size).length - 1, edtCotyledonSize);
                    break;
                case R.id.cotyledon_color:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.cotyledon_color).length - 1, edtCotyledonColor);
                    break;
                case R.id.cotyledon_shape:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.cotyledon_shape).length - 1, edtCotyledonShape);
                    break;
                case R.id.heart_leaf_color:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.heart_leaf_color).length - 1, edtHeartLeafColor);
                    break;
                case R.id.true_leaf_color:
                    setVisibilityOfUserDefined(position, getResources().getStringArray(R.array.true_leaf_color).length - 1, edtTrueLeafColor);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

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
    View.OnClickListener helpClickListener = v -> {
        switch (v.getId()) {
            case R.id.btn_germination_rate:
                showBottomHelpDialog(self, getFragmentManager(), token, getResources().getString(R.string.info_germination_rate));
                break;
            case R.id.btn_cotyledon_size:
                showBottomHelpDialog(self, getFragmentManager(), token, getResources().getString(R.string.info_cotyledon_size));
                break;
            case R.id.btn_cotyledon_color:
                showBottomHelpDialog(self, getFragmentManager(), token, getResources().getString(R.string.info_cotyledon_color));
                break;
            case R.id.btn_cotyledon_count:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_cotyledon_count));
                break;
            case R.id.btn_cotyledon_shape:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_cotyledon_shape));
                break;
            case R.id.btn_heart_leaf_color:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_heart_leaf_color));
                break;
            case R.id.btn_true_leaf_color:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_true_leaf_color));
                break;
            case R.id.btn_true_leaf_length:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_true_leaf_length));
                break;
            case R.id.btn_true_leaf_width:
                showBottomHelpDialog(self, getFragmentManager(), token, self.getResources().getString(R.string.info_true_leaf_width));
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

    public static SeedlingPeriodFragment newInstance() {
        return new SeedlingPeriodFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seedling_period, container, false);
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
        mCotyledonColorImgList.clear();
        mCotyledonCountImgList.clear();
        mCotyledonShapeImgList.clear();
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
                initMaps();
                initBasicInfo("");
                initData();
                break;
            default:
                break;
        }
    }
    private void initView(boolean editable) {

        cotyledonSize.setOnItemSelectedListener(onItemSelectedListener);
        cotyledonColor.setOnItemSelectedListener(onItemSelectedListener);
        cotyledonShape.setOnItemSelectedListener(onItemSelectedListener);
        heartLeafColor.setOnItemSelectedListener(onItemSelectedListener);
        trueLeafColor.setOnItemSelectedListener(onItemSelectedListener);


        btnCotyledonSize.setOnClickListener(helpClickListener);
        btnCotyledonColor.setOnClickListener(helpClickListener);
        btnCotyledonCount.setOnClickListener(helpClickListener);
        btnCotyledonShape.setOnClickListener(helpClickListener);
        btnHeartLeafColor.setOnClickListener(helpClickListener);
        btnTrueLeafColor.setOnClickListener(helpClickListener);
        btnTrueLeafLength.setOnClickListener(helpClickListener);
        btnTrueLeafWidth.setOnClickListener(helpClickListener);

//        addRepeatedAttributeListener(btnAddCotyledonSize, layoutRepeatedCotyledonSize, getString(R.string.info_cotyledon_size), "cotyledonSize", SURVEY_PERIOD_SEEDLING);
//        addRepeatedAttributeListener(btnAddTrueLeafLength, layoutRepeatedTrueLeafLength, getString(R.string.info_true_leaf_length), "trueLeafLength", SURVEY_PERIOD_SEEDLING);
//        addRepeatedAttributeListener(btnAddTrueLeafWidth, layoutRepeatedTrueLeafWidth, getString(R.string.info_true_leaf_width), "trueLeafWidth", SURVEY_PERIOD_SEEDLING);


        //添加子叶颜色图片
        LinearLayoutManager mCotyledonColorManager = new LinearLayoutManager(self,
                LinearLayoutManager.HORIZONTAL, false);
        imgCotyledonColor.setLayoutManager(mCotyledonColorManager);
        mCotyledonColorAdapter = new SingleImageAdapter(self, mCotyledonColorImgList);
        mCotyledonColorAdapter.setOnItemClickListener((view, position) -> {
            if (position == mCotyledonColorAdapter.getItemCount() - 1) {
                //如果“增加按钮形状的”图片的位置是最后一张，且添加了的图片的数量不超过MainConstant.MAX_SELECT_PIC_NUM张，才能点击
                if (mCotyledonColorImgList.size() == MainConstant.MAX_SINGLE_PIC_NUM) {
                    //最多添加MainConstant.MAX_SELECT_PIC_NUM张图片
                    viewPluImg(position, PictureResultCode.COTYLEDON_COLOR);
                } else {
                    //添加凭证图片
                    selectPic(getActivity(), MainConstant.MAX_SINGLE_PIC_NUM - mCotyledonColorImgList.size(), PictureResultCode.COTYLEDON_COLOR);
                }
            } else {
                viewPluImg(position, PictureResultCode.COTYLEDON_COLOR);
            }
        });
        imgCotyledonColor.setAdapter(mCotyledonColorAdapter);

        //添加子叶数目图片
        LinearLayoutManager mCotyledonCountManager = new LinearLayoutManager(self,
                LinearLayoutManager.HORIZONTAL, false);
        imgCotyledonCount.setLayoutManager(mCotyledonCountManager);
        mCotyledonCountAdapter = new SingleImageAdapter(self, mCotyledonCountImgList);
        mCotyledonCountAdapter.setOnItemClickListener(new SingleImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == mCotyledonCountAdapter.getItemCount() - 1) {
                    //如果“增加按钮形状的”图片的位置是最后一张，且添加了的图片的数量不超过MainConstant.MAX_SELECT_PIC_NUM张，才能点击
                    if (mCotyledonCountImgList.size() == MainConstant.MAX_SINGLE_PIC_NUM) {
                        //最多添加MainConstant.MAX_SELECT_PIC_NUM张图片
                        viewPluImg(position, PictureResultCode.COTYLEDON_COUNT);
                    } else {
                        //添加凭证图片
                        selectPic(getActivity(), MainConstant.MAX_SINGLE_PIC_NUM - mCotyledonCountImgList.size(), PictureResultCode.COTYLEDON_COUNT);
                    }
                } else {
                    viewPluImg(position, PictureResultCode.COTYLEDON_COUNT);
                }
            }
        });
        imgCotyledonCount.setAdapter(mCotyledonCountAdapter);

        //添加子叶形状图片
        LinearLayoutManager mCotyledonShapeManager = new LinearLayoutManager(self,
                LinearLayoutManager.HORIZONTAL, false);
        imgCotyledonShape.setLayoutManager(mCotyledonShapeManager);
        mCotyledonShapeAdapter = new SingleImageAdapter(self, mCotyledonShapeImgList);
        mCotyledonShapeAdapter.setOnItemClickListener(new SingleImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == mCotyledonShapeAdapter.getItemCount() - 1) {
                    //如果“增加按钮形状的”图片的位置是最后一张，且添加了的图片的数量不超过MainConstant.MAX_SELECT_PIC_NUM张，才能点击
                    if (mCotyledonShapeImgList.size() == MainConstant.MAX_SINGLE_PIC_NUM) {
                        //最多添加MainConstant.MAX_SELECT_PIC_NUM张图片
                        viewPluImg(position, PictureResultCode.COTYLEDON_SHAPE);
                    } else {
                        //添加凭证图片
                        selectPic(getActivity(), MainConstant.MAX_SINGLE_PIC_NUM - mCotyledonShapeImgList.size(), PictureResultCode.COTYLEDON_SHAPE);
                    }
                } else {
                    viewPluImg(position, PictureResultCode.COTYLEDON_SHAPE);
                }
            }
        });
        imgCotyledonShape.setAdapter(mCotyledonShapeAdapter);

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
        JsonObject jsonObject = getBasicInfoData();

        String cotyledonSizeData = cotyledonSize.getSelectedItem().toString()
                + separator
                + edtCotyledonSize.getText();
        String cotyledonColorData = cotyledonColor.getSelectedItem().toString() + separator + edtCotyledonColor.getText();
        String cotyledonCountData = cotyledonCount.getSelectedItem().toString() + separator + edtCotyledonCount.getText();
        String cotyledonShapeData = cotyledonShape.getSelectedItem().toString() + separator + edtCotyledonShape.getText();
        String heartLeafColorData = heartLeafColor.getSelectedItem().toString() + separator + edtHeartLeafColor.getText();
        String trueLeafColorData = trueLeafColor.getSelectedItem().toString() + separator + edtTrueLeafColor.getText();
        String trueLeafLengthData = trueLeafLength.getSelectedItem().toString()
                + separator
                + edtTrueLeafLength.getText();
        String trueLeafWidthData = trueLeafWidth.getSelectedItem().toString() + separator + edtTrueLeafWidth.getText();

        //额外属性
        String extraAttributeData = "";
        String extraRemarkData = "";
        if (extraAttribute != null) {
            extraAttributeData = extraAttribute.getContent();
        }
        if (extraRemark != null) {
            extraRemarkData = extraRemark.getContent();
        }

        jsonObject.addProperty("cotyledonSize", cotyledonSizeData);
        jsonObject.addProperty("cotyledonColor", cotyledonColorData);
        jsonObject.addProperty("cotyledonNumber", cotyledonCountData);
        jsonObject.addProperty("cotyledonShape", cotyledonShapeData);
        jsonObject.addProperty("colorOfHeartLeaf", heartLeafColorData);
        jsonObject.addProperty("trueLeafColor", trueLeafColorData);
        jsonObject.addProperty("trueLeafLength", trueLeafLengthData);
        jsonObject.addProperty("trueLeafWidth", trueLeafWidthData);
        jsonObject.addProperty("spare1", extraAttributeData);
        jsonObject.addProperty("spare2", extraRemarkData);
        return jsonObject.toString();
    }

    private void initMaps() {
        imgHashMap.put(self.getResources().getString(R.string.info_cotyledon_color), mCotyledonColorImgList);
        imgHashMap.put(self.getResources().getString(R.string.info_cotyledon_count), mCotyledonCountImgList);
        imgHashMap.put(self.getResources().getString(R.string.info_cotyledon_shape), mCotyledonShapeImgList);
        imgAdapter.put(self.getResources().getString(R.string.info_cotyledon_color), mCotyledonColorAdapter);
        imgAdapter.put(self.getResources().getString(R.string.info_cotyledon_count), mCotyledonCountAdapter);
        imgAdapter.put(self.getResources().getString(R.string.info_cotyledon_shape), mCotyledonShapeAdapter);
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
        setSelectionAndText(cotyledonSize, edtCotyledonSize, surveyInfo.data.cotyledonSize);
        setSelectionAndText(cotyledonColor, edtCotyledonColor, surveyInfo.data.cotyledonColor);
        setSelectionAndText(cotyledonCount, edtCotyledonCount, surveyInfo.data.cotyledonNumber);
        setSelectionAndText(cotyledonShape, edtCotyledonShape, surveyInfo.data.cotyledonShape);
        setSelectionAndText(heartLeafColor, edtHeartLeafColor, surveyInfo.data.colorOfHeartLeaf);
        setSelectionAndText(trueLeafColor, edtTrueLeafColor, surveyInfo.data.trueLeafColor);
        setSelectionAndText(trueLeafLength, edtTrueLeafLength, surveyInfo.data.trueLeafLength);
        setSelectionAndText(trueLeafWidth, edtTrueLeafWidth, surveyInfo.data.trueLeafWidth);
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
        List<String> picList = new ArrayList<>(Arrays.asList(getResources().getString(R.string.info_cotyledon_color), getResources().getString(R.string.info_cotyledon_count), getResources().getString(R.string.info_cotyledon_shape)));
        for (String specCharacter : picList) {
            HttpRequest.getPhoto(token, surveyId, specCharacter, new HttpRequest.IPhotoListCallback() {

                @Override
                public void onResponse(PhotoListInfo photoListInfo) {
                    List<PhotoListInfo.data> photoList = photoListInfo.data;
                    for (PhotoListInfo.data photo : photoList) {
                        String url = photo.url;
                        Map<String, ArrayList<String>> imageMap;
                        Map<String, SingleImageAdapter> adapterMap;
                        imageMap = imgHashMap;
                        adapterMap = imgAdapter;
                        if (imageMap.get(specCharacter) != null) {
                            imageMap.get(specCharacter).add(url);
                        }
                        if (adapterMap.get(specCharacter) != null) {
                            adapterMap.get(specCharacter).notifyDataSetChanged();
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
            case PictureResultCode.COTYLEDON_COLOR:
                intent.putStringArrayListExtra(MainConstant.IMG_LIST, mCotyledonColorImgList);
                break;
            case PictureResultCode.COTYLEDON_COUNT:
                intent.putStringArrayListExtra(MainConstant.IMG_LIST, mCotyledonCountImgList);
                break;
            case PictureResultCode.COTYLEDON_SHAPE:
                intent.putStringArrayListExtra(MainConstant.IMG_LIST, mCotyledonShapeImgList);
                break;
        }
        intent.putExtra(MainConstant.POSITION, position);
        startActivityForResult(intent, resultCode);
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

    // 处理选择的照片的地址
    private void refreshAdapter(List<LocalMedia> picList, int requestCode) {
        switch (requestCode) {
            case PictureResultCode.COTYLEDON_COLOR:
                for (LocalMedia localMedia : picList) {
                    //被压缩后的图片路径
                    if (localMedia.isCompressed()) {
                        String compressPath = localMedia.getCompressPath(); //压缩后的图片路径
                        mCotyledonColorImgList.add(compressPath);
                        mCotyledonColorAdapter.notifyDataSetChanged();
                    }
                }
                break;
            case PictureResultCode.COTYLEDON_COUNT:
                for (LocalMedia localMedia : picList) {
                    //被压缩后的图片路径
                    if (localMedia.isCompressed()) {
                        String compressPath = localMedia.getCompressPath(); //压缩后的图片路径
                        mCotyledonCountImgList.add(compressPath);
                        mCotyledonCountAdapter.notifyDataSetChanged();
                    }
                }
                break;
            case PictureResultCode.COTYLEDON_SHAPE:
                for (LocalMedia localMedia : picList) {
                    //被压缩后的图片路径
                    if (localMedia.isCompressed()) {
                        String compressPath = localMedia.getCompressPath(); //压缩后的图片路径
                        mCotyledonShapeImgList.add(compressPath);
                        mCotyledonShapeAdapter.notifyDataSetChanged();
                    }
                }
                break;
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PictureResultCode.COTYLEDON_COLOR:
                if (resultCode == MainConstant.RESULT_CODE_VIEW_IMG) {
                    //查看大图页面删除了图片
                    ArrayList<String> toDeletePicList = data.getStringArrayListExtra(MainConstant.IMG_LIST); //要删除的图片的集合
                    mCotyledonColorImgList.clear();
                    mCotyledonColorImgList.addAll(toDeletePicList);
                    mCotyledonColorAdapter.notifyDataSetChanged();
                } else {
                    // 图片选择结果回调
                    refreshAdapter(PictureSelector.obtainMultipleResult(data), PictureResultCode.COTYLEDON_COLOR);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                }
                imgHashMap.put(self.getResources().getString(R.string.info_cotyledon_color), mCotyledonColorImgList);
                break;
            case PictureResultCode.COTYLEDON_COUNT:
                if (resultCode == MainConstant.RESULT_CODE_VIEW_IMG) {
                    //查看大图页面删除了图片
                    ArrayList<String> toDeletePicList = data.getStringArrayListExtra(MainConstant.IMG_LIST); //要删除的图片的集合
                    mCotyledonCountImgList.clear();
                    mCotyledonCountImgList.addAll(toDeletePicList);
                    mCotyledonCountAdapter.notifyDataSetChanged();
                } else {
                    refreshAdapter(PictureSelector.obtainMultipleResult(data), PictureResultCode.COTYLEDON_COUNT);
                }
                imgHashMap.put(self.getResources().getString(R.string.info_cotyledon_count), mCotyledonCountImgList);
                break;
            case PictureResultCode.COTYLEDON_SHAPE:
                if (resultCode == MainConstant.RESULT_CODE_VIEW_IMG) {
                    //查看大图页面删除了图片
                    ArrayList<String> toDeletePicList = data.getStringArrayListExtra(MainConstant.IMG_LIST); //要删除的图片的集合
                    mCotyledonShapeImgList.clear();
                    mCotyledonShapeImgList.addAll(toDeletePicList);
                    mCotyledonShapeAdapter.notifyDataSetChanged();
                } else {
                    refreshAdapter(PictureSelector.obtainMultipleResult(data), PictureResultCode.COTYLEDON_SHAPE);
                }
                imgHashMap.put(self.getResources().getString(R.string.info_cotyledon_shape), mCotyledonShapeImgList);
                break;
        }
    }
}
