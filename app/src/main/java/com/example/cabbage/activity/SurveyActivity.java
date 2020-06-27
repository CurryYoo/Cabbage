package com.example.cabbage.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.cabbage.R;
import com.example.cabbage.data.ObjectBox;
import com.example.cabbage.data.SurveyData;
import com.example.cabbage.network.HttpRequest;
import com.example.cabbage.network.NormalInfo;
import com.example.cabbage.network.SurveyInfo;
import com.example.cabbage.utils.ARouterPaths;
import com.example.cabbage.view.InfoItemBar;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.objectbox.Box;

@Route(path = ARouterPaths.SURVEY_ACTIVITY)
public class SurveyActivity extends AppCompatActivity {

    @BindView(R.id.tool_bar)
    LinearLayout toolBar;
    @BindView(R.id.commit_info)
    TextView commitInfo;
    @BindView(R.id.main_area)
    LinearLayout mainArea;
    @BindView(R.id.left_one_button)
    ImageView leftOneButton;
    @BindView(R.id.left_one_layout)
    LinearLayout leftOneLayout;
    @BindView(R.id.left_two_button)
    ImageView leftTwoButton;
    @BindView(R.id.left_two_layout)
    LinearLayout leftTwoLayout;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.right_two_button)
    ImageView rightTwoButton;
    @BindView(R.id.right_two_layout)
    LinearLayout rightTwoLayout;
    @BindView(R.id.right_one_button)
    ImageView rightOneButton;
    @BindView(R.id.right_one_layout)
    LinearLayout rightOneLayout;

    // 基本信息
    EditText editMaterialId;
    EditText editMaterialType;
    EditText editPlantId;

    // 性状
    // 发芽期
    EditText editGerminationRate;
    Button btnGerminationRate;
    // 幼苗期
    Spinner spnCotyledonSize;
    EditText editCotyledonSize;
    Button btnCotyledonSize;
    Spinner spnCotyledonColor;
    EditText editCotyledonColor;
    Button btnCotyledonColor;
    Spinner spnCotyledonCount;
    EditText editCotyledonCount;
    Button btnCotyledonCount;
    Spinner spnCotyledonShape;
    EditText editCotyledonShape;
    Button btnCotyledonShape;
    Spinner spnHeartLeafColor;
    EditText editHeartLeafColor;
    Button btnHeartLeafColor;
    Spinner spnTrueLeafColor;
    EditText editTrueLeafColor;
    Button btnTrueLeafColor;
    Spinner spnTrueLeafLength;
    EditText editTrueLeafLength;
    Button btnTrueLeafLength;
    Spinner spnTrueLeafWidth;
    EditText editTrueLeafWidth;
    Button btnTrueLeafWidth;

    private Context context = this;

    Box<SurveyData> surveyDataBox;

    private String token;
    private int userId;
    private String nickname;

    // 页面的状态
    public static final int STATUS_NEW = 0;    // 新建
    public static final int STATUS_READ = 1;   // 只读
    public static final int STATUS_WRITE = 2;  // 修改

    @Autowired
    public String materialId = "";

    @Autowired
    public String materialType = "";

    @Autowired
    public int status = STATUS_NEW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        ButterKnife.bind(this);

        ARouter.getInstance().inject(this);

        surveyDataBox = ObjectBox.get().boxFor(SurveyData.class);

        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        token = sp.getString("token", "");
        userId = sp.getInt("userId", 1);
        nickname = sp.getString("nickname", "");

        initToolBar();
        initView();
        initBasicInfo();

        if (status != STATUS_NEW) {
            initData("");
            rightTwoLayout.setVisibility(View.GONE);
        }

    }

    private void initToolBar() {
        titleText.setText(getText(R.string.species_data_pick));
        leftOneButton.setBackgroundResource(R.drawable.left_back);
        rightOneButton.setBackgroundResource(R.drawable.homepage);
        rightTwoButton.setBackgroundResource(R.drawable.no_save);

        leftOneLayout.setBackgroundResource(R.drawable.selector_trans_button);
        rightOneLayout.setBackgroundResource(R.drawable.selector_trans_button);
        rightTwoLayout.setBackgroundResource(R.drawable.selector_trans_button);

        leftOneLayout.setOnClickListener(toolBarOnClickListener);
        rightOneLayout.setOnClickListener(toolBarOnClickListener);
        rightTwoLayout.setOnClickListener(toolBarOnClickListener);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            leftOneLayout.setTooltipText(getResources().getText(R.string.back_left));
            rightOneLayout.setTooltipText(getResources().getText(R.string.home_page));
            rightTwoLayout.setTooltipText(getResources().getText(R.string.save_data));
        }
    }

    View.OnClickListener toolBarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.left_one_layout:
                    finish();
                    break;
                case R.id.right_one_layout:
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.right_two_layout:
                    final SweetAlertDialog saveDialog = new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
                            .setContentText(getString(R.string.save_data_tip))
                            .setConfirmText("确定")
                            .setCancelText("取消")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
//                                    updateData();
//                                    updateDataLocally();
//                                    editor.putBoolean("update_pick_data", true);
//                                    editor.apply();
                                }
                            });
                    saveDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    });
                    saveDialog.show();
                    break;
                default:
                    break;
            }
        }
    };

    private void initView() {
        View basicInfoLayout = LayoutInflater.from(context).inflate(R.layout.item_basicinfo, null);
        InfoItemBar itemBar = new InfoItemBar(context, getString(R.string.item_bar_basic));
        itemBar.addView(basicInfoLayout);
        itemBar.setShow(true);
        mainArea.addView(itemBar);

        editMaterialId = basicInfoLayout.findViewById(R.id.edt_material_id);
        editMaterialType = basicInfoLayout.findViewById(R.id.edt_material_type);
        editPlantId = basicInfoLayout.findViewById(R.id.edt_plant_id);

        View germinationPeriodLayout = LayoutInflater.from(context).inflate(R.layout.item_germination_period, null);
        InfoItemBar germinationPeriodItemBar = new InfoItemBar(context, getResources().getString(R.string.title_germination_period));
        germinationPeriodItemBar.addView(germinationPeriodLayout);
        germinationPeriodItemBar.setShow(true);
        mainArea.addView(germinationPeriodItemBar);

        editGerminationRate = germinationPeriodLayout.findViewById(R.id.edt_germination_rate);
        btnGerminationRate = germinationPeriodLayout.findViewById(R.id.btn_germination_rate);

        View seedlingPeriodLayout = LayoutInflater.from(context).inflate(R.layout.item_seedling_period, null);
        InfoItemBar seedlingPeriodItemBar = new InfoItemBar(context, getResources().getString(R.string.title_seedling_period));
        seedlingPeriodItemBar.addView(seedlingPeriodLayout);
        seedlingPeriodItemBar.setShow(true);
        mainArea.addView(seedlingPeriodItemBar);

        spnCotyledonSize = seedlingPeriodLayout.findViewById(R.id.cotyledon_size);
        editCotyledonSize = seedlingPeriodLayout.findViewById(R.id.edit_cotyledon_size);
        btnCotyledonSize = seedlingPeriodLayout.findViewById(R.id.btn_cotyledon_size);
        spnCotyledonColor = seedlingPeriodLayout.findViewById(R.id.cotyledon_color);
        editCotyledonColor = seedlingPeriodLayout.findViewById(R.id.edit_cotyledon_color);
        btnCotyledonColor = seedlingPeriodLayout.findViewById(R.id.btn_cotyledon_color);
        spnCotyledonCount = seedlingPeriodLayout.findViewById(R.id.cotyledon_count);
        editCotyledonCount = seedlingPeriodLayout.findViewById(R.id.edit_cotyledon_count);
        btnCotyledonCount = seedlingPeriodLayout.findViewById(R.id.btn_cotyledon_count);
        spnCotyledonShape = seedlingPeriodLayout.findViewById(R.id.cotyledon_shape);
        editCotyledonShape = seedlingPeriodLayout.findViewById(R.id.edit_cotyledon_shape);
        btnCotyledonShape = seedlingPeriodLayout.findViewById(R.id.btn_cotyledon_shape);
        spnHeartLeafColor = seedlingPeriodLayout.findViewById(R.id.heart_leaf_color);
        editHeartLeafColor = seedlingPeriodLayout.findViewById(R.id.edit_heart_leaf_color);
        btnHeartLeafColor = seedlingPeriodLayout.findViewById(R.id.btn_heart_leaf_color);
        spnTrueLeafColor = seedlingPeriodLayout.findViewById(R.id.true_leaf_color);
        editTrueLeafColor = seedlingPeriodLayout.findViewById(R.id.edit_true_leaf_color);
        btnTrueLeafColor = seedlingPeriodLayout.findViewById(R.id.btn_true_leaf_color);
        spnTrueLeafLength = seedlingPeriodLayout.findViewById(R.id.true_leaf_length);
        editTrueLeafLength = seedlingPeriodLayout.findViewById(R.id.edit_true_leaf_length);
        btnTrueLeafLength = seedlingPeriodLayout.findViewById(R.id.btn_true_leaf_length);
        spnTrueLeafWidth = seedlingPeriodLayout.findViewById(R.id.true_leaf_width);
        editTrueLeafWidth = seedlingPeriodLayout.findViewById(R.id.edit_true_leaf_width);
        btnTrueLeafWidth = seedlingPeriodLayout.findViewById(R.id.btn_true_leaf_width);

        View rosettePeriodLayout = LayoutInflater.from(context).inflate(R.layout.item_rosette_period, null);
        InfoItemBar rosettePeriodItemBar = new InfoItemBar(context, getResources().getString(R.string.title_rosette_period));
        rosettePeriodItemBar.addView(rosettePeriodLayout);
        rosettePeriodItemBar.setShow(true);
        mainArea.addView(rosettePeriodItemBar);


    }

    // 初始化基本数据
    private void initBasicInfo() {
        // 展示基本信息
        editMaterialId.setText(materialId);
    }

    // 初始化本地数据库数据
    private void initLocalData() {
        // 查询本地数据
        SurveyData initData = surveyDataBox.get(Long.parseLong(materialId));
        if (initData == null) {
            return;
        }
        String cotyledonSize = initData.cotyledonSize;
        SpinnerAdapter cotyledonSizeAdapter = spnCotyledonSize.getAdapter();
        for (int j = 0; j < cotyledonSizeAdapter.getCount(); j++) {
            if (cotyledonSize.equals(cotyledonSizeAdapter.getItem(j).toString())) {
                spnCotyledonSize.setSelection(j, true);
                break;
            } else if (j == cotyledonSizeAdapter.getCount() - 1) {
                editCotyledonSize.setText(cotyledonSize);
            }
        }
        String cotyledonColor = initData.cotyledonColor;
        SpinnerAdapter cotyledonColorAdapter = spnCotyledonColor.getAdapter();
        for (int j = 0; j < cotyledonColorAdapter.getCount(); j++) {
            if (cotyledonColor.equals(cotyledonColorAdapter.getItem(j).toString())) {
                spnCotyledonColor.setSelection(j, true);
                break;
            } else if (j == cotyledonColorAdapter.getCount() - 1) {
                editCotyledonColor.setText(cotyledonColor);
            }
        }
        String cotyledonCount = initData.cotyledonCount;
        SpinnerAdapter cotyledonCountAdapter = spnCotyledonCount.getAdapter();
        for (int j = 0; j < cotyledonCountAdapter.getCount(); j++) {
            if (cotyledonCount.equals(cotyledonCountAdapter.getItem(j).toString())) {
                spnCotyledonCount.setSelection(j, true);
                break;
            } else if (j == cotyledonCountAdapter.getCount() - 1) {
                editCotyledonCount.setText(cotyledonCount);
            }
        }
        String cotyledonShape = initData.cotyledonShape;
        SpinnerAdapter cotyledonShapeAdapter = spnCotyledonShape.getAdapter();
        for (int j = 0; j < cotyledonShapeAdapter.getCount(); j++) {
            if (cotyledonShape.equals(cotyledonShapeAdapter.getItem(j).toString())) {
                spnCotyledonShape.setSelection(j, true);
                break;
            } else if (j == cotyledonShapeAdapter.getCount() - 1) {
                editCotyledonShape.setText(cotyledonShape);
            }
        }
        String heartLeafColor = initData.heartLeafColor;
        SpinnerAdapter heartLeafColorAdapter = spnHeartLeafColor.getAdapter();
        for (int j = 0; j < heartLeafColorAdapter.getCount(); j++) {
            if (heartLeafColor.equals(heartLeafColorAdapter.getItem(j).toString())) {
                spnHeartLeafColor.setSelection(j, true);
                break;
            } else if (j == heartLeafColorAdapter.getCount() - 1) {
                editHeartLeafColor.setText(heartLeafColor);
            }
        }
        String trueLeafColor = initData.trueLeafColor;
        SpinnerAdapter trueLeafColorAdapter = spnTrueLeafColor.getAdapter();
        for (int j = 0; j < trueLeafColorAdapter.getCount(); j++) {
            if (trueLeafColor.equals(trueLeafColorAdapter.getItem(j).toString())) {
                spnTrueLeafColor.setSelection(j, true);
                break;
            } else if (j == trueLeafColorAdapter.getCount() - 1) {
                editTrueLeafColor.setText(trueLeafColor);
            }
        }
        String trueLeafLength = initData.trueLeafLength;
        SpinnerAdapter trueLeafLengthAdapter = spnTrueLeafLength.getAdapter();
        for (int j = 0; j < trueLeafLengthAdapter.getCount(); j++) {
            if (trueLeafLength.equals(trueLeafLengthAdapter.getItem(j).toString())) {
                spnTrueLeafLength.setSelection(j, true);
                break;
            } else if (j == trueLeafLengthAdapter.getCount() - 1) {
                editTrueLeafLength.setText(trueLeafLength);
            }
        }
        String trueLeafWidth = initData.trueLeafWidth;
        SpinnerAdapter trueLeafWidthAdapter = spnTrueLeafWidth.getAdapter();
        for (int j = 0; j < trueLeafWidthAdapter.getCount(); j++) {
            if (trueLeafWidth.equals(trueLeafWidthAdapter.getItem(j).toString())) {
                spnTrueLeafWidth.setSelection(j, true);
                break;
            } else if (j == trueLeafWidthAdapter.getCount() - 1) {
                editTrueLeafWidth.setText(trueLeafWidth);
            }
        }

    }

    // 初始化网络数据
    private void initData(String surveyId) {
        // TODO
        // 网络请求具体数据
        HttpRequest.getSurveyDataDetailBySurveyId(token, "", "", new HttpRequest.ISurveyCallback() {
            @Override
            public void onResponse(SurveyInfo surveyInfo) {

            }

            @Override
            public void onFailure() {

            }
        });
    }

    // 更新本地数据库
    private boolean updateLocalData(String surveyId) {
        try {
            SurveyData surveyData = new SurveyData();
            surveyData.surveyId = Long.parseLong(surveyId);
            surveyData.cotyledonSize = spnCotyledonSize.getSelectedItem().toString();
            surveyData.cotyledonColor = spnCotyledonColor.getSelectedItem().toString();
            surveyData.cotyledonCount = spnCotyledonCount.getSelectedItem().toString();
            surveyData.cotyledonShape = spnCotyledonShape.getSelectedItem().toString();
            surveyData.heartLeafColor = spnHeartLeafColor.getSelectedItem().toString();
            surveyData.trueLeafColor = spnTrueLeafColor.getSelectedItem().toString();
            surveyData.trueLeafLength = spnTrueLeafLength.getSelectedItem().toString();
            surveyData.trueLeafWidth = spnTrueLeafWidth.getSelectedItem().toString();
            surveyDataBox.put(surveyData);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 更新服务器数据
    private boolean updateData(String surveyId) {
        // TODO
        try {
            // 发芽期
            HttpRequest.requestAddSurveyData(token, "发芽期", "", new HttpRequest.INormalCallback() {
                @Override
                public void onResponse(NormalInfo normalInfo) {

                }

                @Override
                public void onFailure() {

                }
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String getSeedlingPeriodData() {
        String plantId = editPlantId.getText().toString();
        String cotyledonSize = spnCotyledonSize.getSelectedItem().toString();
        String cotyledonColor = spnCotyledonColor.getSelectedItem().toString();
        String cotyledonCount = spnCotyledonCount.getSelectedItem().toString();
        String cotyledonShape = spnCotyledonShape.getSelectedItem().toString();
        String heartLeafColor = spnHeartLeafColor.getSelectedItem().toString();
        String trueLeafColor = spnTrueLeafColor.getSelectedItem().toString();
        String trueLeafLength = spnTrueLeafLength.getSelectedItem().toString();
        String trueLeafWidth = spnTrueLeafWidth.getSelectedItem().toString();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("material_type", materialType);
        jsonObject.addProperty("material_number", materialId);
        jsonObject.addProperty("plant_number", plantId);
        jsonObject.addProperty("investigating_time", getCurrentTime());
        jsonObject.addProperty("investigator", nickname);
        jsonObject.addProperty("cotyledon_size", cotyledonSize);
        jsonObject.addProperty("cotyledon_color", cotyledonColor);
        jsonObject.addProperty("cotyledon_number", cotyledonCount);
        jsonObject.addProperty("cotyledon_shape", cotyledonShape);
        jsonObject.addProperty("color_of_heart_leaf", heartLeafColor);
        jsonObject.addProperty("true_leaf_color", trueLeafColor);
        jsonObject.addProperty("true_leaf_length", trueLeafLength);
        jsonObject.addProperty("true_leaf_width", trueLeafWidth);
        jsonObject.addProperty("user_id", userId);

        return jsonObject.toString();
    }

    private String getCurrentTime() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

}
