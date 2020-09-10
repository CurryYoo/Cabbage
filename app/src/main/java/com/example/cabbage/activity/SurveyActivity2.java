package com.example.cabbage.activity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.cabbage.R;
import com.example.cabbage.adapter.ImageAdapter;
import com.example.cabbage.adapter.SingleImageAdapter;
import com.example.cabbage.data.ObjectBox;
import com.example.cabbage.data.SurveyData;
import com.example.cabbage.network.HelpInfo;
import com.example.cabbage.network.HttpRequest;
import com.example.cabbage.network.NormalInfo;
import com.example.cabbage.network.PhotoListInfo;
import com.example.cabbage.network.ResultInfo;
import com.example.cabbage.network.SurveyInfo;
import com.example.cabbage.utils.ARouterPaths;
import com.example.cabbage.utils.MainConstant;
import com.example.cabbage.utils.PictureResultCode;
import com.example.cabbage.utils.PictureSelectorConfig;
import com.example.cabbage.utils.UIUtils;
import com.example.cabbage.view.ExtraAttributeView;
import com.example.cabbage.view.InfoBottomDialog;
import com.example.cabbage.view.InfoItemBar;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.JsonObject;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.objectbox.Box;

import static com.example.cabbage.utils.BasicUtil.showDatePickerDialog;
import static com.example.cabbage.utils.StaticVariable.STATUS_COPY;
import static com.example.cabbage.utils.StaticVariable.STATUS_NEW;
import static com.example.cabbage.utils.StaticVariable.STATUS_READ;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_GERMINATION;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_ROSETTE;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_SEEDLING;
import static com.example.cabbage.utils.StaticVariable.SEPARATOR_2;
import static com.example.cabbage.utils.UIUtils.setSelectionAndText;
import static com.example.cabbage.utils.UIUtils.setVisibilityOfUserDefined;
import static java.io.File.separator;

/**
 * Author:created by Kang on 2020/9/9
 * Email:zyk970512@163.com
 * Annotation:调查页面（旧），目前历史记录页面会跳转到此页面
 */
@Route(path = ARouterPaths.SURVEY_ACTIVITY2)
public class SurveyActivity2 extends AppCompatActivity {

    @Autowired(name = "materialId")
    public String materialId = "";
    @Autowired(name = "materialType")
    public String materialType = "";
    @Autowired(name = "plantId")
    public String plantId;
    @Autowired(name = "investigatingTime")
    public String investigatingTime;
    @Autowired
    public int status = STATUS_NEW;
    @Autowired(name = "surveyId")
    public String surveyId;
    @Autowired(name = "surveyPeriod")
    public String surveyPeriod = SURVEY_PERIOD_GERMINATION;

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
    TextView tvInvestigatingTime;
    // 基本信息
    private EditText editMaterialId;
    private EditText editMaterialType;
    private EditText editPlantId;

    private LinearLayout layoutCustomAttribute1;
    private Button btnAddAttribute1;
    private Button btnAddRemark1;
    private LinearLayout layoutCustomAttribute2;
    private Button btnAddAttribute2;
    private Button btnAddRemark2;
    private LinearLayout layoutCustomAttribute3;
    private Button btnAddAttribute3;
    private Button btnAddRemark3;
    // 性状
    // 发芽期
    private EditText editGerminationRate;
    private Button btnGerminationRate;
    // 幼苗期
    private Spinner spnCotyledonSize;
    private Button btnCotyledonSize;
    private Box<SurveyData> surveyDataBox;
    private Spinner spnCotyledonColor;
    private Button btnCotyledonColor;
    private Spinner spnCotyledonCount;
    private Button btnCotyledonCount;
    private Spinner spnCotyledonShape;
    private Button btnCotyledonShape;
    private Spinner spnHeartLeafColor;
    private Button btnHeartLeafColor;
    private Spinner spnTrueLeafColor;
    private Button btnTrueLeafColor;
    private Spinner spnTrueLeafLength;
    private Button btnTrueLeafLength;
    private Spinner spnTrueLeafWidth;
    private Button btnTrueLeafWidth;

    //幼苗期数值
    private EditText edtCotyledonCount;
    private EditText edtTrueLeafLength;
    private EditText edtTrueLeafWidth;

    //幼苗期自定义属性
    private EditText edtCotyledonSize;
    private EditText edtCotyledonColor;
    private EditText edtCotyledonShape;
    private EditText edtHeartLeafColor;
    private EditText edtTrueLeafColor;

    private HashMap<String, ArrayList<String>> photosInGermination = new HashMap<>();
    private HashMap<String, SingleImageAdapter> adaptersInGermination = new HashMap<>();
    private HashMap<String, ArrayList<String>> photosInSeedling = new HashMap<>();
    private HashMap<String, SingleImageAdapter> adaptersInSeedling = new HashMap<>();
    private RecyclerView imgCotyledonColor;
    private SingleImageAdapter mCotyledonColorAdapter;
    private ArrayList<String> mCotyledonColorImgList = new ArrayList<>();
    private RecyclerView imgCotyledonCount;
    private SingleImageAdapter mCotyledonCountAdapter;
    private ArrayList<String> mCotyledonCountImgList = new ArrayList<>();
    private RecyclerView imgCotyledonShape;
    private SingleImageAdapter mCotyledonShapeAdapter;
    private ArrayList<String> mCotyledonShapeImgList = new ArrayList<>();

    //重复属性添加按钮
    private LinearLayout layoutRepeatedCotyledonSize;
    private LinearLayout layoutRepeatedTrueLeafLength;
    private LinearLayout layoutRepeatedTrueLeafWidth;
    private Button btnAddCotyledonSize;
    private Button btnAddTrueLeafLength;
    private Button btnAddTrueLeafWidth;
    // 莲座期
    private Spinner spnPlantShape;
    private Button btnPlantShape;
    private Spinner spnPlantHeight;
    private Button btnPlantHeight;
    private Spinner spnDevelopmentDegree;
    private Button btnDevelopmentDegree;
    private EditText edtLeafCount;
    private Button btnLeafCount;
    private EditText edtSoftLeafThickness;
    private Button btnSoftLeafThickness;
    private Spinner spnLeafLength;
    private Button btnLeafLength;
    private Spinner spnLeafWidth;
    private Button btnLeafWidth;
    private Spinner spnLeafShape;
    private Button btnLeafShape;
    private Spinner spnLeafColor;
    private Button btnLeafColor;
    private Spinner spnLeafLuster;
    private Button btnLeafLuster;
    private Spinner spnLeafFuzz;
    private Button btnLeafFuzz;
    private Spinner spnLeafMarginUndulance;
    private Button btnLeafMarginUndulance;
    private Spinner spnLeafMarginSawtooth;
    private Button btnLeafMarginSawtooth;
    private Spinner spnLeafSmoothness;
    private Button btnLeafSmoothness;
    private Spinner spnLeafProtuberance;
    private Button btnLeafProtuberance;
    private Spinner spnLeafVeinLivingness;
    private Button btnLeafVeinLivingness;
    private Spinner spnLeafKeelLivingness;
    private Button btnLeafKeelLivingness;
    private Spinner spnLeafCurliness;
    private Button btnLeafCurliness;
    private Spinner spnLeafCurlinessPart;
    private Button btnLeafCurlinessPart;
    private Spinner spnLeafTexture;
    private Button btnLeafTexture;
    //莲座期数值
    private EditText edtPlantHeight;
    private EditText edtDevelopmentDegree;
    private EditText edtLeafLength;
    private EditText edtLeafWidth;
    //莲座期自定义属性
    private EditText edtPlantShape;
    private EditText edtLeafShape;
    private EditText edtLeafColor;
    private EditText edtLeafLuster;
    private EditText edtLeafFuzz;
    private EditText edtLeafMarginUndulance;
    private EditText edtLeafMarginSawtooth;
    private EditText edtLeafSmoothness;
    private EditText edtLeafProtuberance;
    private EditText edtLeafVeinLivingness;
    private EditText edtLeafKeelLivingness;
    private EditText edtLeafCurliness;
    private EditText edtLeafCurlinessPart;
    private EditText edtLeafTexture;
    //spinner选择监听，选择其他是，显示自定义填空
    Spinner.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()) {
                //幼苗期
                case R.id.cotyledon_size:
                    setVisibilityOfUserDefined(position, 3, edtCotyledonSize);
                    break;
                case R.id.cotyledon_color:
                    setVisibilityOfUserDefined(position, 6, edtCotyledonColor);
                    break;
                case R.id.cotyledon_shape:
                    setVisibilityOfUserDefined(position, 2, edtCotyledonShape);
                    break;
                case R.id.heart_leaf_color:
                    setVisibilityOfUserDefined(position, 8, edtHeartLeafColor);
                    break;
                case R.id.true_leaf_color:
                    setVisibilityOfUserDefined(position, 8, edtTrueLeafColor);
                    break;
                //莲座期自定义
                case R.id.plant_shape:
                    setVisibilityOfUserDefined(position, 3, edtPlantShape);
                    break;
                case R.id.leaf_shape:
                    setVisibilityOfUserDefined(position, 5, edtLeafShape);
                    break;
                case R.id.leaf_color:
                    setVisibilityOfUserDefined(position, 7, edtLeafColor);
                    break;
                case R.id.leaf_luster:
                    setVisibilityOfUserDefined(position, 3, edtLeafLuster);
                    break;
                case R.id.leaf_fuzz:
                    setVisibilityOfUserDefined(position, 4, edtLeafFuzz);
                    break;
                case R.id.leaf_margin_undulance:
                    setVisibilityOfUserDefined(position, 4, edtLeafMarginUndulance);
                    break;
                case R.id.leaf_margin_sawtooth:
                    setVisibilityOfUserDefined(position, 4, edtLeafMarginSawtooth);
                    break;
                case R.id.leaf_smoothness:
                    setVisibilityOfUserDefined(position, 5, edtLeafSmoothness);
                    break;
                case R.id.leaf_protuberance:
                    setVisibilityOfUserDefined(position, 4, edtLeafProtuberance);
                    break;
                case R.id.leaf_vein_livingness:
                    setVisibilityOfUserDefined(position, 3, edtLeafVeinLivingness);
                    break;
                case R.id.leaf_keel_livingness:
                    setVisibilityOfUserDefined(position, 2, edtLeafKeelLivingness);
                    break;
                case R.id.leaf_curliness:
                    setVisibilityOfUserDefined(position, 4, edtLeafCurliness);
                    break;
                case R.id.leaf_curliness_part:
                    setVisibilityOfUserDefined(position, 4, edtLeafCurlinessPart);
                    break;
                case R.id.leaf_texture:
                    setVisibilityOfUserDefined(position, 3, edtLeafTexture);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private HashMap<String, ArrayList<String>> photosInRosette = new HashMap<>();
    private HashMap<String, SingleImageAdapter> adaptersInRosette = new HashMap<>();
    private GridView imgRosettePeriod;
    private ImageAdapter mRosettePeriodAdapter;
    private ArrayList<String> mRosettePeriodImgList = new ArrayList<>();
    private List<ExtraAttributeView> mGerminationExtraList = new ArrayList<>();
    private List<ExtraAttributeView> mSeedlingExtraList = new ArrayList<>();
    private List<ExtraAttributeView> mRosetteExtraList = new ArrayList<>();
    private HashMap<String, Integer> limitTag = new HashMap<>();//限制重复属性和额外属性
    private HashMap<String, ExtraAttributeView> mGerminationExtraHashMap = new HashMap<>();
    private HashMap<String, ExtraAttributeView> mSeedlingExtraHashMap = new HashMap<>();
    private HashMap<String, ExtraAttributeView> mRosetteExtraHashMap = new HashMap<>();
    private Context context = this;
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
                    if (status == STATUS_READ) {
                        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        Intent dataIntent = new Intent();
                        // 创建普通字符型ClipData
                        dataIntent.putExtra("surveyId", surveyId);
                        dataIntent.putExtra("surveyPeriod", surveyPeriod);
                        dataIntent.putExtra("materialId", materialId);
                        dataIntent.putExtra("materialType", materialType);
                        ClipData mClipData1 = ClipData.newIntent("copyData", dataIntent);
                        // 将ClipData内容放到系统剪贴板里
                        cm.setPrimaryClip(mClipData1);
                        Toast.makeText(getApplicationContext(), R.string.copy_data_success, Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }
    };
    private String token;
    View.OnClickListener helpClickListener = v -> {
        switch (v.getId()) {
            case R.id.btn_germination_rate:
//                showBottomHelpDialog(context.getResources().getString(R.string.info_germination_rate));
                UIUtils.showBottomHelpDialog(context, getSupportFragmentManager(), token, context.getResources().getString(R.string.info_germination_rate));
                break;
            case R.id.btn_cotyledon_size:
                showBottomHelpDialog(context.getResources().getString(R.string.info_cotyledon_size));
                break;
            case R.id.btn_cotyledon_color:
                showBottomHelpDialog(context.getResources().getString(R.string.info_cotyledon_color));
                break;
            case R.id.btn_cotyledon_count:
                showBottomHelpDialog(context.getResources().getString(R.string.info_cotyledon_count));
                break;
            case R.id.btn_cotyledon_shape:
                showBottomHelpDialog(context.getResources().getString(R.string.info_cotyledon_shape));
                break;
            case R.id.btn_heart_leaf_color:
                showBottomHelpDialog(context.getResources().getString(R.string.info_heart_leaf_color));
                break;
            case R.id.btn_true_leaf_color:
                showBottomHelpDialog(context.getResources().getString(R.string.info_true_leaf_color));
                break;
            case R.id.btn_true_leaf_length:
                showBottomHelpDialog(context.getResources().getString(R.string.info_true_leaf_length));
                break;
            case R.id.btn_true_leaf_width:
                showBottomHelpDialog(context.getResources().getString(R.string.info_true_leaf_width));
                break;
            case R.id.btn_plant_shape:
                showBottomHelpDialog(context.getResources().getString(R.string.info_rosette_plant_shape));
                break;
            case R.id.btn_plant_height:
                showBottomHelpDialog(context.getResources().getString(R.string.info_rosette_plant_height));
                break;
            case R.id.btn_development_degree:
                showBottomHelpDialog(context.getResources().getString(R.string.info_rosette_development_degree));
                break;
            case R.id.btn_leaf_count:
                showBottomHelpDialog(context.getResources().getString(R.string.info_leaf_count));
                break;
            case R.id.btn_soft_leaf_thickness:
                showBottomHelpDialog(context.getResources().getString(R.string.info_soft_leaf_thickness));
                break;
            case R.id.btn_leaf_length:
                showBottomHelpDialog(context.getResources().getString(R.string.info_leaf_length));
                break;
            case R.id.btn_leaf_width:
                showBottomHelpDialog(context.getResources().getString(R.string.info_leaf_width));
                break;
            case R.id.btn_leaf_shape:
                showBottomHelpDialog(context.getResources().getString(R.string.info_leaf_shape));
                break;
            case R.id.btn_leaf_color:
                showBottomHelpDialog(context.getResources().getString(R.string.info_leaf_color));
                break;
            case R.id.btn_leaf_luster:
                showBottomHelpDialog(context.getResources().getString(R.string.info_leaf_luster));
                break;
            case R.id.btn_leaf_fuzz:
                showBottomHelpDialog(context.getResources().getString(R.string.info_leaf_fuzz));
                break;
            case R.id.btn_leaf_margin_undulance:
                showBottomHelpDialog(context.getResources().getString(R.string.info_leaf_margin_undulance));
                break;
            case R.id.btn_leaf_margin_sawtooth:
                showBottomHelpDialog(context.getResources().getString(R.string.info_leaf_margin_sawtooth));
                break;
            case R.id.btn_leaf_smoothness:
                showBottomHelpDialog(context.getResources().getString(R.string.info_leaf_smoothness));
                break;
            case R.id.btn_leaf_protuberance:
                showBottomHelpDialog(context.getResources().getString(R.string.info_leaf_protuberance));
                break;
            case R.id.btn_leaf_vein_livingness:
                showBottomHelpDialog(context.getResources().getString(R.string.info_leaf_vein_livingness));
                break;
            case R.id.btn_leaf_keel_livingness:
                showBottomHelpDialog(context.getResources().getString(R.string.info_leaf_keel_livingness));
                break;
            case R.id.btn_leaf_curliness:
                showBottomHelpDialog(context.getResources().getString(R.string.info_leaf_curliness));
                break;
            case R.id.btn_leaf_curliness_part:
                showBottomHelpDialog(context.getResources().getString(R.string.info_leaf_curliness_part));
                break;
            case R.id.btn_leaf_texture:
                showBottomHelpDialog(context.getResources().getString(R.string.info_leaf_texture));
                break;
        }
    };
    private int userId;
    private String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Fresco.initialize(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey2);
        ButterKnife.bind(this);

        ARouter.getInstance().inject(this);

        surveyDataBox = ObjectBox.get().boxFor(SurveyData.class);

        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        token = sp.getString("token", "");
        userId = sp.getInt("userId", 1);
        nickname = sp.getString("nickname", "");

        initToolBar();


        switch (status) {
            case STATUS_NEW:
                initView(true);
                initBasicInfo("");
                break;
            case STATUS_READ:
                initView(false);
                initMaps();
                initBasicInfo(plantId);
                initData(surveyPeriod);
                initPictures(surveyPeriod);
                break;
            case STATUS_COPY:
                initView(true);
                initMaps();
                initBasicInfo("");
                initData(surveyPeriod);
                //复制粘贴暂不支持图片
                break;
            default:
                break;
        }
    }

    private void initToolBar() {
        titleText.setText(getText(R.string.species_data_pick));
        leftOneButton.setBackgroundResource(R.mipmap.ic_back);
        rightOneButton.setBackgroundResource(R.mipmap.ic_homepage);
        if (status == STATUS_READ) {
            rightTwoButton.setBackgroundResource(R.mipmap.ic_copy);
            rightTwoLayout.setOnClickListener(toolBarOnClickListener);
            rightTwoLayout.setBackgroundResource(R.drawable.selector_trans_button);
        }

        leftOneLayout.setBackgroundResource(R.drawable.selector_trans_button);
        rightOneLayout.setBackgroundResource(R.drawable.selector_trans_button);

        leftOneLayout.setOnClickListener(toolBarOnClickListener);
        rightOneLayout.setOnClickListener(toolBarOnClickListener);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            leftOneLayout.setTooltipText(getResources().getText(R.string.back_left));
            rightOneLayout.setTooltipText(getResources().getText(R.string.home_page));
            rightTwoLayout.setTooltipText(getResources().getText(R.string.save_data));
        }
    }

    private void initView(boolean isEditable) {
        View basicInfoLayout = LayoutInflater.from(context).inflate(R.layout.item_basic_info, null);
        InfoItemBar itemBar = new InfoItemBar(context, getString(R.string.item_bar_basic));
        itemBar.addView(basicInfoLayout);
        itemBar.setShow(true);
        itemBar.setVisibilitySubmit(false);
        mainArea.addView(itemBar);

        editMaterialId = basicInfoLayout.findViewById(R.id.edt_material_id);
        editMaterialType = basicInfoLayout.findViewById(R.id.edt_material_type);
        editPlantId = basicInfoLayout.findViewById(R.id.edt_plant_id);
        tvInvestigatingTime = basicInfoLayout.findViewById(R.id.edt_investigating_time);

        //发芽期View
        View germinationPeriodLayout = LayoutInflater.from(context).inflate(R.layout.item_germination_period, null);
        InfoItemBar germinationPeriodItemBar = new InfoItemBar(context, getResources().getString(R.string.title_germination_period));
        germinationPeriodItemBar.addView(germinationPeriodLayout);
        if (!TextUtils.isEmpty(surveyPeriod)) {
            germinationPeriodItemBar.setShow(surveyPeriod.equals(SURVEY_PERIOD_GERMINATION));
        } else {
            germinationPeriodItemBar.setShow(true);
        }
        germinationPeriodItemBar.setVisibilitySubmit(isEditable);
        mainArea.addView(germinationPeriodItemBar);

        editGerminationRate = germinationPeriodLayout.findViewById(R.id.edt_germination_rate);
        btnGerminationRate = germinationPeriodLayout.findViewById(R.id.btn_germination_rate);
        btnGerminationRate.setOnClickListener(helpClickListener);

        germinationPeriodItemBar.setSubmitListener(v -> {
            if (checkIsValid()) {
                Toast.makeText(context, R.string.check_required, Toast.LENGTH_SHORT).show();
            } else {
                showDialog(SURVEY_PERIOD_GERMINATION);
            }
        });

        //幼苗期View
        View seedlingPeriodLayout = LayoutInflater.from(context).inflate(R.layout.item_seedling_period, null);
        InfoItemBar seedlingPeriodItemBar = new InfoItemBar(context, getResources().getString(R.string.title_seedling_period));
        seedlingPeriodItemBar.addView(seedlingPeriodLayout);
        if (!TextUtils.isEmpty(surveyPeriod)) {
            seedlingPeriodItemBar.setShow(surveyPeriod.equals(SURVEY_PERIOD_SEEDLING));
        } else {
            seedlingPeriodItemBar.setShow(true);
        }
        seedlingPeriodItemBar.setVisibilitySubmit(isEditable);
        mainArea.addView(seedlingPeriodItemBar);

        spnCotyledonSize = seedlingPeriodLayout.findViewById(R.id.cotyledon_size);
        edtCotyledonSize = seedlingPeriodLayout.findViewById(R.id.edt_cotyledon_size);
        btnCotyledonSize = seedlingPeriodLayout.findViewById(R.id.btn_cotyledon_size);
        btnAddCotyledonSize = seedlingPeriodLayout.findViewById(R.id.btn_add_cotyledon_size);
        layoutRepeatedCotyledonSize = seedlingPeriodLayout.findViewById(R.id.layout_repeated_cotyledon_size);
        spnCotyledonSize.setOnItemSelectedListener(onItemSelectedListener);
        addRepeatedAttributeListener(btnAddCotyledonSize, layoutRepeatedCotyledonSize, getString(R.string.info_cotyledon_size), "cotyledonSize", SURVEY_PERIOD_SEEDLING);
        btnCotyledonSize.setOnClickListener(helpClickListener);

        spnCotyledonColor = seedlingPeriodLayout.findViewById(R.id.cotyledon_color);
        edtCotyledonColor = seedlingPeriodLayout.findViewById(R.id.edt_cotyledon_color);
        btnCotyledonColor = seedlingPeriodLayout.findViewById(R.id.btn_cotyledon_color);
//        btnAddCotyledonColor = seedlingPeriodLayout.findViewById(R.id.btn_add_cotyledon_color);
//        layoutRepeatedCotyledonColor = seedlingPeriodLayout.findViewById(R.id.layout_repeated_cotyledon_color);
        spnCotyledonColor.setOnItemSelectedListener(onItemSelectedListener);
        btnCotyledonColor.setOnClickListener(helpClickListener);

        spnCotyledonCount = seedlingPeriodLayout.findViewById(R.id.cotyledon_count);
        edtCotyledonCount = seedlingPeriodLayout.findViewById(R.id.edt_cotyledon_count);
        btnCotyledonCount = seedlingPeriodLayout.findViewById(R.id.btn_cotyledon_count);
//        btnAddCotyledonCount = seedlingPeriodLayout.findViewById(R.id.btn_add_cotyledon_count);
//        layoutRepeatedCotyledonCount = seedlingPeriodLayout.findViewById(R.id.layout_repeated_cotyledon_count);
        btnCotyledonCount.setOnClickListener(helpClickListener);

        spnCotyledonShape = seedlingPeriodLayout.findViewById(R.id.cotyledon_shape);
        edtCotyledonShape = seedlingPeriodLayout.findViewById(R.id.edt_cotyledon_shape);
        btnCotyledonShape = seedlingPeriodLayout.findViewById(R.id.btn_cotyledon_shape);
//        btnAddCotyledonShape = seedlingPeriodLayout.findViewById(R.id.btn_add_cotyledon_shape);
//        layoutRepeatedCotyledonShape = seedlingPeriodLayout.findViewById(R.id.layout_repeated_cotyledon_shape);
        spnCotyledonShape.setOnItemSelectedListener(onItemSelectedListener);
        btnCotyledonShape.setOnClickListener(helpClickListener);

        spnHeartLeafColor = seedlingPeriodLayout.findViewById(R.id.heart_leaf_color);
        edtHeartLeafColor = seedlingPeriodLayout.findViewById(R.id.edt_heart_leaf_color);
        btnHeartLeafColor = seedlingPeriodLayout.findViewById(R.id.btn_heart_leaf_color);
//        btnAddHeartLeafColor = seedlingPeriodLayout.findViewById(R.id.btn_add_heart_leaf_color);
//        layoutRepeatedHeartLeafColor = seedlingPeriodLayout.findViewById(R.id.layout_repeated_heart_leaf_color);
        spnHeartLeafColor.setOnItemSelectedListener(onItemSelectedListener);
        btnHeartLeafColor.setOnClickListener(helpClickListener);

        spnTrueLeafColor = seedlingPeriodLayout.findViewById(R.id.true_leaf_color);
        edtTrueLeafColor = seedlingPeriodLayout.findViewById(R.id.edt_true_leaf_color);
        btnTrueLeafColor = seedlingPeriodLayout.findViewById(R.id.btn_true_leaf_color);
//        btnAddTrueLeafColor = seedlingPeriodLayout.findViewById(R.id.btn_add_true_leaf_color);
//        layoutRepeatedTrueLeafColor = seedlingPeriodLayout.findViewById(R.id.layout_repeated_true_leaf_color);
        spnTrueLeafColor.setOnItemSelectedListener(onItemSelectedListener);
        btnTrueLeafColor.setOnClickListener(helpClickListener);

        spnTrueLeafLength = seedlingPeriodLayout.findViewById(R.id.true_leaf_length);
        edtTrueLeafLength = seedlingPeriodLayout.findViewById(R.id.edt_true_leaf_length);
        btnTrueLeafLength = seedlingPeriodLayout.findViewById(R.id.btn_true_leaf_length);
        btnAddTrueLeafLength = seedlingPeriodLayout.findViewById(R.id.btn_add_true_leaf_length);
        layoutRepeatedTrueLeafLength = seedlingPeriodLayout.findViewById(R.id.layout_repeated_true_leaf_length);
        addRepeatedAttributeListener(btnAddTrueLeafLength, layoutRepeatedTrueLeafLength, getString(R.string.info_true_leaf_length), "trueLeafLength", SURVEY_PERIOD_SEEDLING);
        btnTrueLeafLength.setOnClickListener(helpClickListener);

        spnTrueLeafWidth = seedlingPeriodLayout.findViewById(R.id.true_leaf_width);
        edtTrueLeafWidth = seedlingPeriodLayout.findViewById(R.id.edt_true_leaf_width);
        btnTrueLeafWidth = seedlingPeriodLayout.findViewById(R.id.btn_true_leaf_width);
        btnAddTrueLeafWidth = seedlingPeriodLayout.findViewById(R.id.btn_add_true_leaf_width);
        layoutRepeatedTrueLeafWidth = seedlingPeriodLayout.findViewById(R.id.layout_repeated_true_leaf_width);
        addRepeatedAttributeListener(btnAddTrueLeafWidth, layoutRepeatedTrueLeafWidth, getString(R.string.info_true_leaf_width), "trueLeafWidth", SURVEY_PERIOD_SEEDLING);
        btnTrueLeafWidth.setOnClickListener(helpClickListener);


        //添加子叶颜色图片
        imgCotyledonColor = findViewById(R.id.img_cotyledon_color);
        LinearLayoutManager mCotyledonColorManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        imgCotyledonColor.setLayoutManager(mCotyledonColorManager);
        mCotyledonColorAdapter = new SingleImageAdapter(context, mCotyledonColorImgList);
        mCotyledonColorAdapter.setOnItemClickListener(new SingleImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == mCotyledonColorAdapter.getItemCount() - 1) {
                    //如果“增加按钮形状的”图片的位置是最后一张，且添加了的图片的数量不超过MainConstant.MAX_SELECT_PIC_NUM张，才能点击
                    if (mCotyledonColorImgList.size() == MainConstant.MAX_SINGLE_PIC_NUM) {
                        //最多添加MainConstant.MAX_SELECT_PIC_NUM张图片
                        viewPluImg(position, PictureResultCode.COTYLEDON_COLOR);
                    } else {
                        //添加凭证图片
                        selectPic(MainConstant.MAX_SINGLE_PIC_NUM - mCotyledonColorImgList.size(), PictureResultCode.COTYLEDON_COLOR);
                    }
                } else {
                    viewPluImg(position, PictureResultCode.COTYLEDON_COLOR);
                }
            }
        });
        imgCotyledonColor.setAdapter(mCotyledonColorAdapter);

        //添加子叶数目图片
        imgCotyledonCount = findViewById(R.id.img_cotyledon_count);
        LinearLayoutManager mCotyledonCountManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        imgCotyledonCount.setLayoutManager(mCotyledonCountManager);
        mCotyledonCountAdapter = new SingleImageAdapter(context, mCotyledonCountImgList);
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
                        selectPic(MainConstant.MAX_SINGLE_PIC_NUM - mCotyledonCountImgList.size(), PictureResultCode.COTYLEDON_COUNT);
                    }
                } else {
                    viewPluImg(position, PictureResultCode.COTYLEDON_COUNT);
                }
            }
        });
        imgCotyledonCount.setAdapter(mCotyledonCountAdapter);

        //添加子叶形状图片
        imgCotyledonShape = findViewById(R.id.img_cotyledon_shape);
        LinearLayoutManager mCotyledonShapeManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        imgCotyledonShape.setLayoutManager(mCotyledonShapeManager);
        mCotyledonShapeAdapter = new SingleImageAdapter(context, mCotyledonShapeImgList);
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
                        selectPic(MainConstant.MAX_SINGLE_PIC_NUM - mCotyledonShapeImgList.size(), PictureResultCode.COTYLEDON_SHAPE);
                    }
                } else {
                    viewPluImg(position, PictureResultCode.COTYLEDON_SHAPE);
                }
            }
        });
        imgCotyledonShape.setAdapter(mCotyledonShapeAdapter);


        seedlingPeriodItemBar.setSubmitListener(v -> {
            if (checkIsValid()) {
                Toast.makeText(context, R.string.check_required, Toast.LENGTH_SHORT).show();
            } else {
                showDialog(SURVEY_PERIOD_SEEDLING);
            }
        });

        //莲座期View
        View rosettePeriodLayout = LayoutInflater.from(context).inflate(R.layout.item_rosette_period, null);
        InfoItemBar rosettePeriodItemBar = new InfoItemBar(context, getResources().getString(R.string.title_rosette_period));
        rosettePeriodItemBar.addView(rosettePeriodLayout);
        if (!TextUtils.isEmpty(surveyPeriod)) {
            rosettePeriodItemBar.setShow(surveyPeriod.equals(SURVEY_PERIOD_ROSETTE));
        } else {
            rosettePeriodItemBar.setShow(true);
        }
        rosettePeriodItemBar.setVisibilitySubmit(isEditable);
        mainArea.addView(rosettePeriodItemBar);

        spnPlantShape = rosettePeriodLayout.findViewById(R.id.plant_shape);
        edtPlantShape = rosettePeriodLayout.findViewById(R.id.edt_plant_shape);
        btnPlantShape = rosettePeriodLayout.findViewById(R.id.btn_plant_shape);
        spnPlantShape.setOnItemSelectedListener(onItemSelectedListener);
        btnPlantShape.setOnClickListener(helpClickListener);

        spnPlantHeight = rosettePeriodLayout.findViewById(R.id.plant_height);
        edtPlantHeight = rosettePeriodLayout.findViewById(R.id.edt_plant_height);
        btnPlantHeight = rosettePeriodLayout.findViewById(R.id.btn_plant_height);
        btnPlantHeight.setOnClickListener(helpClickListener);

        spnDevelopmentDegree = rosettePeriodLayout.findViewById(R.id.development_degree);
        edtDevelopmentDegree = rosettePeriodLayout.findViewById(R.id.edt_development_degree);
        btnDevelopmentDegree = rosettePeriodLayout.findViewById(R.id.btn_development_degree);
        btnDevelopmentDegree.setOnClickListener(helpClickListener);

        edtLeafCount = rosettePeriodLayout.findViewById(R.id.edt_leaf_count);
        btnLeafCount = rosettePeriodLayout.findViewById(R.id.btn_leaf_count);
        btnLeafCount.setOnClickListener(helpClickListener);

        edtSoftLeafThickness = rosettePeriodLayout.findViewById(R.id.edt_soft_leaf_thickness);
        btnSoftLeafThickness = rosettePeriodLayout.findViewById(R.id.btn_soft_leaf_thickness);
        btnSoftLeafThickness.setOnClickListener(helpClickListener);

        spnLeafLength = rosettePeriodLayout.findViewById(R.id.leaf_length);
        edtLeafLength = rosettePeriodLayout.findViewById(R.id.edt_leaf_length);
        btnLeafLength = rosettePeriodLayout.findViewById(R.id.btn_leaf_length);
        btnLeafLength.setOnClickListener(helpClickListener);

        spnLeafWidth = rosettePeriodLayout.findViewById(R.id.leaf_width);
        edtLeafWidth = rosettePeriodLayout.findViewById(R.id.edt_leaf_width);
        btnLeafWidth = rosettePeriodLayout.findViewById(R.id.btn_leaf_width);
        btnLeafWidth.setOnClickListener(helpClickListener);

        spnLeafShape = rosettePeriodLayout.findViewById(R.id.leaf_shape);
        edtLeafShape = rosettePeriodLayout.findViewById(R.id.edt_leaf_shape);
        btnLeafShape = rosettePeriodLayout.findViewById(R.id.btn_leaf_shape);
        spnLeafShape.setOnItemSelectedListener(onItemSelectedListener);
        btnLeafShape.setOnClickListener(helpClickListener);

        spnLeafColor = rosettePeriodLayout.findViewById(R.id.leaf_color);
        edtLeafColor = rosettePeriodLayout.findViewById(R.id.edt_leaf_color);
        btnLeafColor = rosettePeriodLayout.findViewById(R.id.btn_leaf_color);
        spnLeafColor.setOnItemSelectedListener(onItemSelectedListener);
        btnLeafColor.setOnClickListener(helpClickListener);

        spnLeafLuster = rosettePeriodLayout.findViewById(R.id.leaf_luster);
        edtLeafLuster = rosettePeriodLayout.findViewById(R.id.edt_leaf_luster);
        btnLeafLuster = rosettePeriodLayout.findViewById(R.id.btn_leaf_luster);
        spnLeafLuster.setOnItemSelectedListener(onItemSelectedListener);
        btnLeafLuster.setOnClickListener(helpClickListener);

        spnLeafFuzz = rosettePeriodLayout.findViewById(R.id.leaf_fuzz);
        edtLeafFuzz = rosettePeriodLayout.findViewById(R.id.edt_leaf_fuzz);
        btnLeafFuzz = rosettePeriodLayout.findViewById(R.id.btn_leaf_fuzz);
        spnLeafFuzz.setOnItemSelectedListener(onItemSelectedListener);
        btnLeafFuzz.setOnClickListener(helpClickListener);

        spnLeafMarginUndulance = rosettePeriodLayout.findViewById(R.id.leaf_margin_undulance);
        edtLeafMarginUndulance = rosettePeriodLayout.findViewById(R.id.edt_leaf_margin_undulance);
        btnLeafMarginUndulance = rosettePeriodLayout.findViewById(R.id.btn_leaf_margin_undulance);
        spnLeafMarginUndulance.setOnItemSelectedListener(onItemSelectedListener);
        btnLeafMarginUndulance.setOnClickListener(helpClickListener);

        spnLeafMarginSawtooth = rosettePeriodLayout.findViewById(R.id.leaf_margin_sawtooth);
        edtLeafMarginSawtooth = rosettePeriodLayout.findViewById(R.id.edt_leaf_margin_sawtooth);
        btnLeafMarginSawtooth = rosettePeriodLayout.findViewById(R.id.btn_leaf_margin_sawtooth);
        spnLeafMarginSawtooth.setOnItemSelectedListener(onItemSelectedListener);
        btnLeafMarginSawtooth.setOnClickListener(helpClickListener);

        spnLeafSmoothness = rosettePeriodLayout.findViewById(R.id.leaf_smoothness);
        edtLeafSmoothness = rosettePeriodLayout.findViewById(R.id.edt_leaf_smoothness);
        btnLeafSmoothness = rosettePeriodLayout.findViewById(R.id.btn_leaf_smoothness);
        spnLeafSmoothness.setOnItemSelectedListener(onItemSelectedListener);
        btnLeafSmoothness.setOnClickListener(helpClickListener);

        spnLeafProtuberance = rosettePeriodLayout.findViewById(R.id.leaf_protuberance);
        edtLeafProtuberance = rosettePeriodLayout.findViewById(R.id.edt_leaf_protuberance);
        btnLeafProtuberance = rosettePeriodLayout.findViewById(R.id.btn_leaf_protuberance);
        spnLeafProtuberance.setOnItemSelectedListener(onItemSelectedListener);
        btnLeafProtuberance.setOnClickListener(helpClickListener);

        spnLeafVeinLivingness = rosettePeriodLayout.findViewById(R.id.leaf_vein_livingness);
        edtLeafVeinLivingness = rosettePeriodLayout.findViewById(R.id.edt_leaf_vein_livingness);
        btnLeafVeinLivingness = rosettePeriodLayout.findViewById(R.id.btn_leaf_vein_livingness);
        spnLeafVeinLivingness.setOnItemSelectedListener(onItemSelectedListener);
        btnLeafVeinLivingness.setOnClickListener(helpClickListener);

        spnLeafKeelLivingness = rosettePeriodLayout.findViewById(R.id.leaf_keel_livingness);
        edtLeafKeelLivingness = rosettePeriodLayout.findViewById(R.id.edt_leaf_keel_livingness);
        btnLeafKeelLivingness = rosettePeriodLayout.findViewById(R.id.btn_leaf_keel_livingness);
        spnLeafKeelLivingness.setOnItemSelectedListener(onItemSelectedListener);
        btnLeafKeelLivingness.setOnClickListener(helpClickListener);

        spnLeafCurliness = rosettePeriodLayout.findViewById(R.id.leaf_curliness);
        edtLeafCurliness = rosettePeriodLayout.findViewById(R.id.edt_leaf_curliness);
        btnLeafCurliness = rosettePeriodLayout.findViewById(R.id.btn_leaf_curliness);
        spnLeafCurliness.setOnItemSelectedListener(onItemSelectedListener);
        btnLeafCurliness.setOnClickListener(helpClickListener);

        spnLeafCurlinessPart = rosettePeriodLayout.findViewById(R.id.leaf_curliness_part);
        edtLeafCurlinessPart = rosettePeriodLayout.findViewById(R.id.edt_leaf_curliness_part);
        btnLeafCurlinessPart = rosettePeriodLayout.findViewById(R.id.btn_leaf_curliness_part);
        spnLeafCurlinessPart.setOnItemSelectedListener(onItemSelectedListener);
        btnLeafCurlinessPart.setOnClickListener(helpClickListener);

        spnLeafTexture = rosettePeriodLayout.findViewById(R.id.leaf_texture);
        edtLeafTexture = rosettePeriodLayout.findViewById(R.id.edt_leaf_texture);
        btnLeafTexture = rosettePeriodLayout.findViewById(R.id.btn_leaf_texture);
        spnLeafTexture.setOnItemSelectedListener(onItemSelectedListener);
        btnLeafTexture.setOnClickListener(helpClickListener);

        rosettePeriodItemBar.setSubmitListener(v -> {
            if (checkIsValid()) {
                Toast.makeText(context, "请检查必填项！", Toast.LENGTH_SHORT).show();
            } else {
                showDialog(SURVEY_PERIOD_ROSETTE);
            }
        });

        //添加莲座期总图片
        imgRosettePeriod = findViewById(R.id.img_rosette_period);
        mRosettePeriodAdapter = new ImageAdapter(context, mRosettePeriodImgList);
        imgRosettePeriod.setAdapter(mRosettePeriodAdapter);
        imgRosettePeriod.setOnItemClickListener((parent, view, position, id) -> {
            if (position == parent.getChildCount() - 1) {
                //如果“增加按钮形状的”图片的位置是最后一张，且添加了的图片的数量不超过MainConstant.MAX_SELECT_PIC_NUM张，才能点击
                if (mRosettePeriodImgList.size() == MainConstant.MAX_SELECT_PIC_NUM) {
                    //最多添加MainConstant.MAX_SELECT_PIC_NUM张图片
                    viewPluImg(position, PictureResultCode.ROSETTE_PERIOD);
                } else {
                    //添加凭证图片
                    selectPic(MainConstant.MAX_SELECT_PIC_NUM - mRosettePeriodImgList.size(), PictureResultCode.ROSETTE_PERIOD);
                }
            } else {
                viewPluImg(position, PictureResultCode.ROSETTE_PERIOD);
            }
        });

        //发芽期
        layoutCustomAttribute1 = findViewById(R.id.layout_custom_attribute);
        btnAddRemark1 = findViewById(R.id.btn_add_remark);
        btnAddAttribute1 = findViewById(R.id.btn_add_attribute);
        addExtraAttributeListener(btnAddAttribute1, layoutCustomAttribute1, "spare", SURVEY_PERIOD_GERMINATION);
        addRemarkAttributeListener(btnAddRemark1, layoutCustomAttribute1, "spare", SURVEY_PERIOD_GERMINATION);

        //幼苗期
        layoutCustomAttribute2 = findViewById(R.id.layout_custom_attribute);
        btnAddRemark2 = findViewById(R.id.btn_add_remark);
        btnAddAttribute2 = findViewById(R.id.btn_add_attribute);
        addExtraAttributeListener(btnAddAttribute2, layoutCustomAttribute2, "spare", SURVEY_PERIOD_SEEDLING);
        addRemarkAttributeListener(btnAddRemark2, layoutCustomAttribute2, "spare", SURVEY_PERIOD_SEEDLING);

        //莲座期
        layoutCustomAttribute3 = findViewById(R.id.layout_custom_attribute);
        btnAddRemark3 = findViewById(R.id.btn_add_remark);
        btnAddAttribute3 = findViewById(R.id.btn_add_attribute);
        addExtraAttributeListener(btnAddAttribute3, layoutCustomAttribute3, "spare", SURVEY_PERIOD_ROSETTE);
        addRemarkAttributeListener(btnAddRemark3, layoutCustomAttribute3, "spare", SURVEY_PERIOD_ROSETTE);
    }

    //添加重复属性
    private void addRepeatedAttributeListener(Button button, LinearLayout layout, String attributeName, String keyName, String surveyPeriod) {
        button.setOnClickListener(v -> {
            ExtraAttributeView extraAttributeView = new ExtraAttributeView(context, 1, attributeName, keyName);
            Button btnDelete = extraAttributeView.findViewById(R.id.btn_delete);
            btnDelete.setOnClickListener(v1 -> {
                extraAttributeView.removeAllViews();
            });
            layout.addView(extraAttributeView);
            addToAttributeList(surveyPeriod, extraAttributeView);
        });
    }

    //添加额外属性
    private void addExtraAttributeListener(Button btnAddAttribute, LinearLayout layout, String keyName, String surveyPeriod) {
        btnAddAttribute.setOnClickListener(v -> {
            ExtraAttributeView extraAttributeView = new ExtraAttributeView(context, 1, keyName);
            Button btnDelete = extraAttributeView.findViewById(R.id.btn_delete);
            btnDelete.setOnClickListener(v1 -> {
                extraAttributeView.removeAllViews();
            });
            layout.addView(extraAttributeView);
            addToAttributeList(surveyPeriod, extraAttributeView);
        });
    }

    //添加备注
    private void addRemarkAttributeListener(Button btnAddRemark, LinearLayout layout, String keyName, String surveyPeriod) {
        btnAddRemark.setOnClickListener(v -> {
            ExtraAttributeView extraAttributeView = new ExtraAttributeView(context, 0, keyName);
            Button btnDelete = extraAttributeView.findViewById(R.id.btn_delete);
            btnDelete.setOnClickListener(v1 -> {
                extraAttributeView.removeAllViews();
            });
            layout.addView(extraAttributeView);
            addToAttributeList(surveyPeriod, extraAttributeView);
        });
    }

    //增加新的view到对应时期到list中
    private void addToAttributeList(String surveyPeriod, ExtraAttributeView extraAttributeView) {
        switch (surveyPeriod) {
            case SURVEY_PERIOD_GERMINATION:
                mGerminationExtraList.add(extraAttributeView);
                break;
            case SURVEY_PERIOD_SEEDLING:
//                mSeedlingExtraList.add(customAttributeView);
                mSeedlingExtraHashMap.put(extraAttributeView.getKeyName(), extraAttributeView);
                break;
            case SURVEY_PERIOD_ROSETTE:
                mRosetteExtraList.add(extraAttributeView);
                break;
            default:
                break;
        }
    }

    private void removeAttribute(String surveyPeriod, ExtraAttributeView extraAttributeView) {
        switch (surveyPeriod) {
            case SURVEY_PERIOD_GERMINATION:
                mGerminationExtraList.remove(extraAttributeView);
                break;
            case SURVEY_PERIOD_SEEDLING:
                mSeedlingExtraList.remove(extraAttributeView);
                break;
            case SURVEY_PERIOD_ROSETTE:
                mRosetteExtraList.remove(extraAttributeView);
                break;
            default:
                break;
        }
    }

    //查看大图
    private void viewPluImg(int position, int resultCode) {
        Intent intent = new Intent(this, PlusImageActivity.class);
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
            case PictureResultCode.ROSETTE_PERIOD:
                intent.putStringArrayListExtra(MainConstant.IMG_LIST, mRosettePeriodImgList);
                break;
        }
        intent.putExtra(MainConstant.POSITION, position);
        startActivityForResult(intent, resultCode);
    }

    private void selectPic(int maxTotal, int resultCode) {
        PictureSelectorConfig.initMultiConfig(this, maxTotal, resultCode);
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
            case PictureResultCode.ROSETTE_PERIOD:
                for (LocalMedia localMedia : picList) {
                    //被压缩后的图片路径
                    if (localMedia.isCompressed()) {
                        String compressPath = localMedia.getCompressPath(); //压缩后的图片路径
                        mRosettePeriodImgList.add(compressPath);
                        mRosettePeriodAdapter.notifyDataSetChanged();
                    }
                }
                break;
        }

    }

    //底部帮助对话框
    private void showBottomHelpDialog(String specificCharacter) {
        // 获取数据
        HttpRequest.getMeasurementBySpecificCharacter(token, specificCharacter, new HttpRequest.IHelpCallback() {
            @Override
            public void onResponse(HelpInfo helpInfo) {
                String measurementBasis = "";
                String observationMethod = "";
                if (helpInfo.data != null) {
                    measurementBasis = helpInfo.data.measurementBasis;
                    observationMethod = helpInfo.data.observationMethod;
                }
                String helpText = getString(R.string.measure_standard) + measurementBasis + "\n\n" + getString(R.string.measure_method) + observationMethod;
                InfoBottomDialog dialog = new InfoBottomDialog();
                dialog.setTxtInfo(helpText);
                dialog.show(getSupportFragmentManager());
            }

            @Override
            public void onFailure() {

            }
        });
    }

//    //判断是否显示用户自定义
//    private void setVisibilityOfUserDefined(int positionSelect, int positionOther, EditText userDefined) {
//        if (positionSelect == positionOther) {
//            userDefined.setVisibility(View.VISIBLE);
//        } else {
//            userDefined.setVisibility(View.GONE);
//        }
//    }

    // 校验已有数据是否合法
    private boolean checkIsValid() {
        return TextUtils.isEmpty(editPlantId.getText());
    }

    private void showDialog(String surveyPeriod) {
        final SweetAlertDialog saveDialog = new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
                .setContentText(getString(R.string.upload_data_tip))
                .setConfirmText(getString(R.string.confirm))
                .setCancelText(getString(R.string.cancel))
                .setConfirmClickListener(sweetAlertDialog -> {
                    uploadPeriodData(surveyPeriod);
                    sweetAlertDialog.dismissWithAnimation();
                });
        saveDialog.setCancelClickListener(SweetAlertDialog::dismissWithAnimation);
        saveDialog.show();
    }

    private void initMaps() {
        photosInSeedling.put(context.getResources().getString(R.string.info_cotyledon_color), mCotyledonColorImgList);
        photosInSeedling.put(context.getResources().getString(R.string.info_cotyledon_count), mCotyledonCountImgList);
        photosInSeedling.put(context.getResources().getString(R.string.info_cotyledon_shape), mCotyledonShapeImgList);
        adaptersInSeedling.put(context.getResources().getString(R.string.info_cotyledon_color), mCotyledonColorAdapter);
        adaptersInSeedling.put(context.getResources().getString(R.string.info_cotyledon_count), mCotyledonCountAdapter);
        adaptersInSeedling.put(context.getResources().getString(R.string.info_cotyledon_shape), mCotyledonShapeAdapter);
        photosInRosette.put("common", mRosettePeriodImgList);

    }

    // 初始化基本数据
    @SuppressLint("SetTextI18n")
    private void initBasicInfo(String plantId) {
        // TODO
        // 展示基本信息
        commitInfo.setText(context.getResources().getString(R.string.info_nickname) + nickname);
        editMaterialId.setText(materialId);
        editMaterialType.setText(materialType);
        editPlantId.setText(plantId);
        tvInvestigatingTime.setText(investigatingTime);
        tvInvestigatingTime.setOnClickListener(v -> {
            showDatePickerDialog(context, tvInvestigatingTime);
        });
    }

    // 初始化网络数据（文本数据）
    private void initData(String surveyPeriod) {
        // 网络请求具体数据
        HttpRequest.getSurveyDataDetailBySurveyId(token, surveyPeriod, surveyId, new HttpRequest.ISurveyCallback() {
            @Override
            public void onResponse(SurveyInfo surveyInfo) {
                updateUI(surveyPeriod, surveyInfo);
            }

            @Override
            public void onFailure() {
                runOnUiThread(() -> {
                    Toast.makeText(context, R.string.network_request_wrong, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    // 更新页面中特定时期的数据
    private void updateUI(String surveyPeriod, SurveyInfo surveyInfo) {
        switch (surveyPeriod) {
            case SURVEY_PERIOD_GERMINATION:
                editGerminationRate.setText(surveyInfo.data.germinationRate);
                updateExtraView(layoutCustomAttribute1, getString(R.string.obligate_attribute), "spare", surveyInfo.data.spare1, surveyPeriod);
                updateExtraView(layoutCustomAttribute1, getString(R.string.info_remark), "spare", surveyInfo.data.spare2, surveyPeriod);
                break;
            case SURVEY_PERIOD_SEEDLING:
                setSelectionAndText(spnCotyledonSize, edtCotyledonSize, surveyInfo.data.cotyledonSize);
//                updateExtraView(layoutRepeatedCotyledonSize, getString(R.string.info_cotyledon_size), "cotyledonSize", surveyInfo.data.cotyledonSize2, surveyPeriod);
//                updateExtraView(layoutRepeatedCotyledonSize, getString(R.string.info_cotyledon_size), "cotyledonSize", surveyInfo.data.cotyledonSize3, surveyPeriod);
                setSelectionAndText(spnCotyledonColor, edtCotyledonColor, surveyInfo.data.cotyledonColor);
                setSelectionAndText(spnCotyledonCount, edtCotyledonCount, surveyInfo.data.cotyledonNumber);
                setSelectionAndText(spnCotyledonShape, edtCotyledonShape, surveyInfo.data.cotyledonShape);
                setSelectionAndText(spnHeartLeafColor, edtHeartLeafColor, surveyInfo.data.colorOfHeartLeaf);
                setSelectionAndText(spnTrueLeafColor, edtTrueLeafColor, surveyInfo.data.trueLeafColor);
                setSelectionAndText(spnTrueLeafLength, edtTrueLeafLength, surveyInfo.data.trueLeafLength);
//                updateExtraView(layoutRepeatedTrueLeafLength, getString(R.string.info_true_leaf_length), "trueLeafLength", surveyInfo.data.trueLeafLength2, surveyPeriod);
//                updateExtraView(layoutRepeatedTrueLeafLength, getString(R.string.info_true_leaf_length), "trueLeafLength", surveyInfo.data.trueLeafLength3, surveyPeriod);
                setSelectionAndText(spnTrueLeafWidth, edtTrueLeafWidth, surveyInfo.data.trueLeafWidth);
//                updateExtraView(layoutRepeatedTrueLeafWidth, getString(R.string.info_true_leaf_width), "trueLeafWidth", surveyInfo.data.trueLeafWidth2, surveyPeriod);
//                updateExtraView(layoutRepeatedTrueLeafWidth, getString(R.string.info_true_leaf_width), "trueLeafWidth", surveyInfo.data.trueLeafWidth3, surveyPeriod);
                updateExtraView(layoutCustomAttribute2, getString(R.string.obligate_attribute), "spare", surveyInfo.data.spare1, surveyPeriod);
                updateExtraView(layoutCustomAttribute2, getString(R.string.info_remark), "spare", surveyInfo.data.spare2, surveyPeriod);
                break;
            case SURVEY_PERIOD_ROSETTE:
                setSelectionAndText(spnPlantShape, edtPlantShape, surveyInfo.data.plantType);
                setSelectionAndText(spnPlantHeight, edtPlantHeight, surveyInfo.data.plantHeight);
                setSelectionAndText(spnDevelopmentDegree, edtDevelopmentDegree, surveyInfo.data.developmentDegree);
                edtLeafCount.setText(surveyInfo.data.numberOfLeaves);
                edtSoftLeafThickness.setText(surveyInfo.data.thicknessOfSoftLeaf);
                setSelectionAndText(spnLeafLength, edtLeafLength, surveyInfo.data.bladeLength);
                setSelectionAndText(spnLeafWidth, edtLeafWidth, surveyInfo.data.bladeWidth);
                setSelectionAndText(spnLeafShape, edtLeafShape, surveyInfo.data.leafShape);
                setSelectionAndText(spnLeafColor, edtLeafColor, surveyInfo.data.leafColor);
                setSelectionAndText(spnLeafLuster, edtLeafLuster, surveyInfo.data.leafLuster);
                setSelectionAndText(spnLeafFuzz, edtLeafFuzz, surveyInfo.data.leafFluff);
                setSelectionAndText(spnLeafMarginUndulance, edtLeafMarginUndulance, surveyInfo.data.leafMarginWavy);
                setSelectionAndText(spnLeafMarginSawtooth, edtLeafMarginSawtooth, surveyInfo.data.leafMarginSerrate);
                setSelectionAndText(spnLeafSmoothness, edtLeafSmoothness, surveyInfo.data.bladeSmooth);
                setSelectionAndText(spnLeafProtuberance, edtLeafProtuberance, surveyInfo.data.sizeOfVesicles);
                setSelectionAndText(spnLeafVeinLivingness, edtLeafVeinLivingness, surveyInfo.data.freshnessOfLeafVein);
                setSelectionAndText(spnLeafKeelLivingness, edtLeafKeelLivingness, surveyInfo.data.brightnessOfMiddleRib);
                setSelectionAndText(spnLeafCurliness, edtLeafCurliness, surveyInfo.data.leafCurl);
                setSelectionAndText(spnLeafCurlinessPart, edtLeafCurlinessPart, surveyInfo.data.leafCurlPart);
                setSelectionAndText(spnLeafTexture, edtLeafTexture, surveyInfo.data.leafTexture);
                updateExtraView(layoutCustomAttribute3, getString(R.string.obligate_attribute), "spare", surveyInfo.data.spare1, surveyPeriod);
                updateExtraView(layoutCustomAttribute3, getString(R.string.info_remark), "spare", surveyInfo.data.spare2, surveyPeriod);
                break;
            default:
                break;
        }
    }

    private void updateExtraView(LinearLayout layout, String title, String keyName, String value, String surveyPeriod) {
        if (!TextUtils.isEmpty(value)) {
            initAttributeView(layout, title, keyName, value, surveyPeriod);
        }
    }

    private void initAttributeView(LinearLayout layout, String title, String keyName, String value, String surveyPeriod) {
        ExtraAttributeView extraAttributeView = new ExtraAttributeView(context, 1, keyName);
        extraAttributeView.setTitle(title);
        extraAttributeView.setContent(value);
        Button btnDelete = extraAttributeView.findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(v1 -> {
            extraAttributeView.removeAllViews();
        });
        extraAttributeView.hideDeleteBtn();

        layout.addView(extraAttributeView);
        addToAttributeList(surveyPeriod, extraAttributeView);
    }

    // 初始化图片
    private void initPictures(String surveyPeriod) {
        // 获取图片url
        List<String> picList = new ArrayList<>(Arrays.asList(getResources().getString(R.string.info_cotyledon_color), getResources().getString(R.string.info_cotyledon_count), getResources().getString(R.string.info_cotyledon_shape), "common"));
        for (String specCharacter : picList) {
            HttpRequest.getPhoto(token, surveyId, specCharacter, new HttpRequest.IPhotoListCallback() {

                @Override
                public void onResponse(PhotoListInfo photoListInfo) {
                    List<PhotoListInfo.data> photoList = photoListInfo.data;
                    for (PhotoListInfo.data photo : photoList) {
                        String url = photo.url;
                        String surveyPeriod = photo.obsPeriod;

                        Map<String, ArrayList<String>> imageMap;
                        Map<String, SingleImageAdapter> adapterMap;
                        ImageAdapter commonAdapter;
                        switch (surveyPeriod) {
                            case SURVEY_PERIOD_GERMINATION:
                                imageMap = photosInGermination;
                                adapterMap = adaptersInGermination;
                                commonAdapter = null;
                                break;
                            case SURVEY_PERIOD_SEEDLING:
                                imageMap = photosInSeedling;
                                adapterMap = adaptersInSeedling;
                                commonAdapter = null;
                                break;
                            case SURVEY_PERIOD_ROSETTE:
                                imageMap = photosInRosette;
                                adapterMap = adaptersInRosette;
                                commonAdapter = mRosettePeriodAdapter;
                                break;
                            default:
                                imageMap = new HashMap<>();
                                adapterMap = new HashMap<>();
                                commonAdapter = null;
                                break;
                        }
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
                        Toast.makeText(context, R.string.update_success, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, R.string.update_fail, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure() {
                    Toast.makeText(context, R.string.update_fail, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getPeriodData(String surveyPeriod) {
        switch (surveyPeriod) {
            case SURVEY_PERIOD_GERMINATION:
                return getGerminationPeriodData();
            case SURVEY_PERIOD_SEEDLING:
                return getSeedlingPeriodData();
            case SURVEY_PERIOD_ROSETTE:
                return getRosettePeriodData();
            default:
                return "";
        }
    }

    private JsonObject getBasicInfoData() {
        String plantId = editPlantId.getText().toString();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("materialType", materialType);
        jsonObject.addProperty("materialNumber", materialId);
        jsonObject.addProperty("plantNumber", plantId);
        jsonObject.addProperty("investigatingTime", tvInvestigatingTime.getText().toString());
        jsonObject.addProperty("investigator", nickname);
        jsonObject.addProperty("userId", userId);

        return jsonObject;
    }

    private String getGerminationPeriodData() {
        JsonObject jsonObject = getBasicInfoData();

        String germinationRate = editGerminationRate.getText().toString();
        jsonObject.addProperty("germinationRate", germinationRate);

        Map<String, Integer> map = new HashMap<>();
        map.put("spare", 0);

        //增加额外属性
        for (ExtraAttributeView extraAttributeView : mGerminationExtraList) {
            String finalKey = getFinalKey(map, extraAttributeView.getKeyName());
            if (!TextUtils.isEmpty(finalKey)) {
                jsonObject.addProperty(finalKey, extraAttributeView.getContent());
            }
        }
        return jsonObject.toString();
    }

    private String getSeedlingPeriodData() {
        JsonObject jsonObject = getBasicInfoData();

        String cotyledonSize = spnCotyledonSize.getSelectedItem().toString()
                + separator
                + edtCotyledonSize.getText()
                + SEPARATOR_2;
        String cotyledonColor = spnCotyledonColor.getSelectedItem().toString() + separator + edtCotyledonColor.getText();
        String cotyledonCount = spnCotyledonCount.getSelectedItem().toString() + separator + edtCotyledonCount.getText();
        String cotyledonShape = spnCotyledonShape.getSelectedItem().toString() + separator + edtCotyledonShape.getText();
        String heartLeafColor = spnHeartLeafColor.getSelectedItem().toString() + separator + edtHeartLeafColor.getText();
        String trueLeafColor = spnTrueLeafColor.getSelectedItem().toString() + separator + edtTrueLeafColor.getText();
        String trueLeafLength = spnTrueLeafLength.getSelectedItem().toString()
                + separator
                + edtTrueLeafLength.getText();
        String trueLeafWidth = spnTrueLeafWidth.getSelectedItem().toString() + separator + edtTrueLeafWidth.getText();

        jsonObject.addProperty("cotyledonSize", cotyledonSize);
        jsonObject.addProperty("cotyledonColor", cotyledonColor);
        jsonObject.addProperty("cotyledonNumber", cotyledonCount);
        jsonObject.addProperty("cotyledonShape", cotyledonShape);
        jsonObject.addProperty("colorOfHeartLeaf", heartLeafColor);
        jsonObject.addProperty("trueLeafColor", trueLeafColor);
        jsonObject.addProperty("trueLeafLength", trueLeafLength);
        jsonObject.addProperty("trueLeafWidth", trueLeafWidth);

//        Map<String, Integer> map = new HashMap<>();
////        map.put("cotyledonSize", 1);
////        map.put("trueLeafLength", 1);
////        map.put("trueLeafWidth", 1);
//        map.put("spare", 0);
//
//        //增加额外属性
//        for (CustomAttributeView customAttributeView : mSeedlingExtraList) {
//            String finalKey = getFinalKey(map, customAttributeView.getKeyName());
//            if (!TextUtils.isEmpty(finalKey)) {
//                jsonObject.addProperty(finalKey, customAttributeView.getContent());
//            }
//        }

        return jsonObject.toString();
    }

    private String getRosettePeriodData() {
        JsonObject jsonObject = getBasicInfoData();

        String plantShape = spnPlantShape.getSelectedItem().toString() + separator + edtPlantShape.getText();
        String plantHeight = spnPlantHeight.getSelectedItem().toString() + separator + edtPlantHeight.getText();
        String developmentDegree = spnDevelopmentDegree.getSelectedItem().toString() + separator + edtDevelopmentDegree.getText();
        String leafCount = edtLeafCount.getText().toString();
        String softLeafThickness = edtSoftLeafThickness.getText().toString();
        String leafLength = spnLeafLength.getSelectedItem().toString() + separator + edtLeafLength.getText();
        String leafWidth = spnLeafWidth.getSelectedItem().toString() + separator + edtLeafWidth.getText();
        String leafShape = spnLeafShape.getSelectedItem().toString() + separator + edtLeafShape.getText();
        String leafColor = spnLeafColor.getSelectedItem().toString() + separator + edtLeafColor.getText();
        String leafLuster = spnLeafLuster.getSelectedItem().toString() + separator + edtLeafLuster.getText();
        String leafFuzz = spnLeafFuzz.getSelectedItem().toString() + separator + edtLeafFuzz.getText();
        String leafMarginUndulance = spnLeafMarginUndulance.getSelectedItem().toString() + separator + edtLeafMarginUndulance.getText();
        String leafMarginSawtooth = spnLeafMarginSawtooth.getSelectedItem().toString() + separator + edtLeafMarginSawtooth.getText();
        String leafSmoothness = spnLeafSmoothness.getSelectedItem().toString() + separator + edtLeafSmoothness.getText();
        String leafProtuberance = spnLeafProtuberance.getSelectedItem().toString() + separator + edtLeafProtuberance.getText();
        String leafVeinLivingness = spnLeafVeinLivingness.getSelectedItem().toString() + separator + edtLeafVeinLivingness.getText();
        String leafKeelLivingness = spnLeafKeelLivingness.getSelectedItem().toString() + separator + edtLeafKeelLivingness.getText();
        String leafCurliness = spnLeafCurliness.getSelectedItem().toString() + separator + edtLeafCurliness.getText();
        String leafCurlinessPart = spnLeafCurlinessPart.getSelectedItem().toString() + separator + edtLeafCurlinessPart.getText();
        String leafTexture = spnLeafTexture.getSelectedItem().toString() + separator + edtLeafTexture.getText();

        jsonObject.addProperty("plantType", plantShape);
        jsonObject.addProperty("plantHeight", plantHeight);
        jsonObject.addProperty("developmentDegree", developmentDegree);
        jsonObject.addProperty("numberOfLeaves", leafCount);
        jsonObject.addProperty("thicknessOfSoftLeaf", softLeafThickness);
        jsonObject.addProperty("bladeLength", leafLength);
        jsonObject.addProperty("bladeWidth", leafWidth);
        jsonObject.addProperty("leafShape", leafShape);
        jsonObject.addProperty("leafColor", leafColor);
        jsonObject.addProperty("leafLuster", leafLuster);
        jsonObject.addProperty("leafFluff", leafFuzz);
        jsonObject.addProperty("leafMarginWavy", leafMarginUndulance);
        jsonObject.addProperty("leafMarginSerrate", leafMarginSawtooth);
        jsonObject.addProperty("bladeSmooth", leafSmoothness);
        jsonObject.addProperty("sizeOfVesicles", leafProtuberance);
        jsonObject.addProperty("freshnessOfLeafVein", leafVeinLivingness);
        jsonObject.addProperty("brightnessOfMiddleRib", leafKeelLivingness);
        jsonObject.addProperty("leafCurl", leafCurliness);
        jsonObject.addProperty("leafCurlPart", leafCurlinessPart);
        jsonObject.addProperty("leafTexture", leafTexture);

        return jsonObject.toString();
    }

    private String getFinalKey(Map<String, Integer> map, String keyName) {
        if (map.containsKey(keyName) && map.get(keyName) != null) {
            int num = map.get(keyName) + 1;
            map.put(keyName, num);
            return keyName + num;
        } else {
            return "";
        }
    }

    private String getCurrentTime() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    // 更新上传图片
    private void uploadPics(String surveyPeriod, String surveyId) {
        Map<String, ArrayList<String>> imageMap;
        switch (surveyPeriod) {
            case SURVEY_PERIOD_GERMINATION:
                imageMap = photosInGermination;
                break;
            case SURVEY_PERIOD_SEEDLING:
                imageMap = photosInSeedling;
                break;
            case SURVEY_PERIOD_ROSETTE:
                imageMap = photosInRosette;
                break;
            default:
                imageMap = new HashMap<>();
                break;
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
                photosInSeedling.put(context.getResources().getString(R.string.info_cotyledon_color), mCotyledonColorImgList);
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
                photosInSeedling.put(context.getResources().getString(R.string.info_cotyledon_count), mCotyledonCountImgList);
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
                photosInSeedling.put(context.getResources().getString(R.string.info_cotyledon_shape), mCotyledonShapeImgList);
                break;
            case PictureResultCode.ROSETTE_PERIOD:
                if (resultCode == MainConstant.RESULT_CODE_VIEW_IMG) {
                    //查看大图页面删除了图片
                    ArrayList<String> toDeletePicList = data.getStringArrayListExtra(MainConstant.IMG_LIST); //要删除的图片的集合
                    mRosettePeriodImgList.clear();
                    mRosettePeriodImgList.addAll(toDeletePicList);
                    mRosettePeriodAdapter.notifyDataSetChanged();
                } else {
                    refreshAdapter(PictureSelector.obtainMultipleResult(data), PictureResultCode.ROSETTE_PERIOD);
                }
                photosInRosette.put("common", mRosettePeriodImgList);
                break;

        }
    }
}
