package com.example.cabbage.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.example.cabbage.R;
import com.example.cabbage.adapter.ImageAdapter;
import com.example.cabbage.adapter.SingleImageAdapter;
import com.example.cabbage.data.ObjectBox;
import com.example.cabbage.data.SurveyData;
import com.example.cabbage.network.HelpInfo;
import com.example.cabbage.network.HttpRequest;
import com.example.cabbage.network.NormalInfo;
import com.example.cabbage.network.PhotoInfo;
import com.example.cabbage.network.ResultInfo;
import com.example.cabbage.network.SurveyInfo;
import com.example.cabbage.utils.ARouterPaths;
import com.example.cabbage.utils.MainConstant;
import com.example.cabbage.utils.PictureResultCode;
import com.example.cabbage.utils.PictureSelectorConfig;
import com.example.cabbage.view.CustomAttributeView;
import com.example.cabbage.view.InfoItemBar;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.JsonObject;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.objectbox.Box;

import static com.example.cabbage.utils.BasicUtil.watchOnlineLargePhoto;
import static com.example.cabbage.utils.ImageUtils.getImageThumbnail;

@Route(path = ARouterPaths.SURVEY_ACTIVITY)
public class SurveyActivity extends AppCompatActivity {

    // 页面的状态
    public static final int STATUS_NEW = 0;    // 新建
    public static final int STATUS_READ = 1;   // 只读
    public static final int STATUS_WRITE = 2;  // 修改
    // 观测时期
    public static final String SURVEY_PERIOD_GERMINATION = "发芽期";
    public static final String SURVEY_PERIOD_SEEDLING = "幼苗期";
    public static final String SURVEY_PERIOD_ROSETTE = "莲座期";
    // 拍照
    private static final int TAKE_PHOTO_COTYLEDON_COLOR = 10;
    private static final int TAKE_PHOTO_COTYLEDON_COUNT = 11;
    private static final int TAKE_PHOTO_COTYLEDON_SHAPE = 12;
    // 相册
    private static final int SELECT_PHOTO_COTYLEDON_COLOR = 100;
    private static final int SELECT_PHOTO_COTYLEDON_COUNT = 101;
    private static final int SELECT_PHOTO_COTYLEDON_SHAPE = 102;
    private static final String separator = "/";
    @Autowired(name = "materialId")
    public String materialId = "";
    @Autowired(name = "materialType")
    public String materialType = "";
    @Autowired(name = "plantId")
    public String plantId;
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
    // 基本信息
    EditText editMaterialId;
    EditText editMaterialType;
    EditText editPlantId;
    LinearLayout layoutCustomAttribute1;
    Button btnAddAttribute1;
    Button btnAddRemark1;
    LinearLayout layoutCustomAttribute2;
    Button btnAddAttribute2;
    Button btnAddRemark2;
    LinearLayout layoutCustomAttribute3;
    Button btnAddAttribute3;
    Button btnAddRemark3;
    LinearLayout layoutCustomAttribute4;
    Button btnAddAttribute4;
    Button btnAddRemark4;
    // 性状
    // 发芽期
    EditText editGerminationRate;
    Button btnGerminationRate;
    // 幼苗期
    Spinner spnCotyledonSize;
    Button btnCotyledonSize;
    Spinner spnCotyledonColor;
    Button btnCotyledonColor;
    Spinner spnCotyledonCount;
    EditText edtCotyledonCount;
    Button btnCotyledonCount;
    Spinner spnCotyledonShape;
    Button btnCotyledonShape;
    Spinner spnHeartLeafColor;
    Button btnHeartLeafColor;
    Spinner spnTrueLeafColor;
    Button btnTrueLeafColor;
    Spinner spnTrueLeafLength;
    EditText edtTrueLeafLength;
    Button btnTrueLeafLength;
    Spinner spnTrueLeafWidth;
    EditText edtTrueLeafWidth;
    Button btnTrueLeafWidth;
    Box<SurveyData> surveyDataBox;
    private HashMap<String, ArrayList<String>> photosInGermination = new HashMap<>();
    private HashMap<String, ArrayList<String>> photosInSeedling = new HashMap<>();
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
    private LinearLayout layoutRepeatedCotyledonColor;
    private LinearLayout layoutRepeatedCotyledonCount;
    private LinearLayout layoutRepeatedCotyledonShape;
    private LinearLayout layoutRepeatedHeartLeafColor;
    private LinearLayout layoutRepeatedTrueLeafColor;
    private LinearLayout layoutRepeatedTrueLeafLength;
    private LinearLayout layoutRepeatedTrueLeafWidth;
    private Button btnAddCotyledonSize;
    private Button btnAddCotyledonColor;
    private Button btnAddCotyledonCount;
    private Button btnAddCotyledonShape;
    private Button btnAddHeartLeafColor;
    private Button btnAddTrueLeafColor;
    private Button btnAddTrueLeafLength;
    private Button btnAddTrueLeafWidth;
    // 莲座期
    private Spinner spnPlantShape;
    private EditText editPlantShape;
    private Button btnPlantShape;
    private Spinner spnPlantHeight;
    private EditText editPlantHeight;
    private Button btnPlantHeight;
    private Spinner spnDevelopmentDegree;
    private EditText editDevelopmentDegree;
    private Button btnDevelopmentDegree;
    private EditText edtLeafCount;
    private Button btnLeafCount;
    private EditText edtSoftLeafThickness;
    private Button btnSoftLeafThickness;
    private Spinner spnLeafLength;
    private EditText editLeafLength;
    private Button btnLeafLength;
    private Spinner spnLeafWidth;
    private EditText editLeafWidth;
    private Button btnLeafWidth;
    private Spinner spnLeafShape;
    private EditText editLeafShape;
    private Button btnLeafShape;
    private Spinner spnLeafColor;
    private EditText editLeafColor;
    private Button btnLeafColor;
    private Spinner spnLeafLuster;
    private EditText editLeafLuster;
    private Button btnLeafLuster;
    private Spinner spnLeafFuzz;
    private EditText editLeafFuzz;
    private Button btnLeafFuzz;
    private Spinner spnLeafMarginUndulance;
    private EditText editLeafMarginUndulance;
    private Button btnLeafMarginUndulance;
    private Spinner spnLeafMarginSawtooth;
    private EditText editLeafMarginSawtooth;
    private Button btnLeafMarginSawtooth;
    private Spinner spnLeafSmoothness;
    private EditText editLeafSmoothness;
    private Button btnLeafSmoothness;
    private Spinner spnLeafProtuberance;
    private EditText editLeafProtuberance;
    private Button btnLeafProtuberance;
    private Spinner spnLeafVeinLivingness;
    private EditText editLeafVeinLivingness;
    private Button btnLeafVeinLivingness;
    private Spinner spnLeafKeelLivingness;
    private EditText editLeafKeelLivingness;
    private Button btnLeafKeelLivingness;
    private Spinner spnLeafCurliness;
    private EditText editLeafCurliness;
    private Button btnLeafCurliness;
    private Spinner spnLeafCurlinessPart;
    private EditText editLeafCurlinessPart;
    private Button btnLeafCurlinessPart;
    private Spinner spnLeafTexture;
    private EditText editLeafTexture;
    private Button btnLeafTexture;
    private HashMap<String, ArrayList<String>> photosInRosette = new HashMap<>();
    private GridView imgRosettePeriod;
    private ImageAdapter mRosettePeriodAdapter;
    private ArrayList<String> mRosettePeriodImgList = new ArrayList<>();
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
                default:
                    break;
            }
        }
    };
    private String token;
    View.OnClickListener helpClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_germination_rate:
                    showHelpDialog(context.getResources().getString(R.string.info_germination_rate));
                    break;
                case R.id.btn_cotyledon_size:
                    showHelpDialog(context.getResources().getString(R.string.info_cotyledon_size));
                    break;
                case R.id.btn_cotyledon_color:
                    showHelpDialog(context.getResources().getString(R.string.info_cotyledon_color));
                    break;
                case R.id.btn_cotyledon_count:
                    showHelpDialog(context.getResources().getString(R.string.info_cotyledon_count));
                    break;
                case R.id.btn_cotyledon_shape:
                    showHelpDialog(context.getResources().getString(R.string.info_cotyledon_shape));
                    break;
                case R.id.btn_heart_leaf_color:
                    showHelpDialog(context.getResources().getString(R.string.info_heart_leaf_color));
                    break;
                case R.id.btn_true_leaf_color:
                    showHelpDialog(context.getResources().getString(R.string.info_true_leaf_color));
                    break;
                case R.id.btn_true_leaf_length:
                    showHelpDialog(context.getResources().getString(R.string.info_true_leaf_length));
                    break;
                case R.id.btn_true_leaf_width:
                    showHelpDialog(context.getResources().getString(R.string.info_true_leaf_width));
                    break;
                case R.id.btn_plant_shape:
                    showHelpDialog(context.getResources().getString(R.string.info_plant_shape));
                    break;
                case R.id.btn_plant_height:
                    showHelpDialog(context.getResources().getString(R.string.info_plant_height));
                    break;
                case R.id.btn_development_degree:
                    showHelpDialog(context.getResources().getString(R.string.info_development_degree));
                    break;
                case R.id.btn_leaf_count:
                    showHelpDialog(context.getResources().getString(R.string.info_leaf_count));
                    break;
                case R.id.btn_soft_leaf_thickness:
                    showHelpDialog(context.getResources().getString(R.string.info_soft_leaf_thickness));
                    break;
                case R.id.btn_leaf_length:
                    showHelpDialog(context.getResources().getString(R.string.info_leaf_length));
                    break;
                case R.id.btn_leaf_width:
                    showHelpDialog(context.getResources().getString(R.string.info_leaf_width));
                    break;
                case R.id.btn_leaf_shape:
                    showHelpDialog(context.getResources().getString(R.string.info_leaf_shape));
                    break;
                case R.id.btn_leaf_color:
                    showHelpDialog(context.getResources().getString(R.string.info_leaf_color));
                    break;
                case R.id.btn_leaf_luster:
                    showHelpDialog(context.getResources().getString(R.string.info_leaf_luster));
                    break;
                case R.id.btn_leaf_fuzz:
                    showHelpDialog(context.getResources().getString(R.string.info_leaf_fuzz));
                    break;
                case R.id.btn_leaf_margin_undulance:
                    showHelpDialog(context.getResources().getString(R.string.info_leaf_margin_undulance));
                    break;
                case R.id.btn_leaf_margin_sawtooth:
                    showHelpDialog(context.getResources().getString(R.string.info_leaf_margin_sawtooth));
                    break;
                case R.id.btn_leaf_smoothness:
                    showHelpDialog(context.getResources().getString(R.string.info_leaf_smoothness));
                    break;
                case R.id.btn_leaf_protuberance:
                    showHelpDialog(context.getResources().getString(R.string.info_leaf_protuberance));
                    break;
                case R.id.btn_leaf_vein_livingness:
                    showHelpDialog(context.getResources().getString(R.string.info_leaf_vein_livingness));
                    break;
                case R.id.btn_leaf_keel_livingness:
                    showHelpDialog(context.getResources().getString(R.string.info_leaf_keel_livingness));
                    break;
                case R.id.btn_leaf_curliness:
                    showHelpDialog(context.getResources().getString(R.string.info_leaf_curliness));
                    break;
                case R.id.btn_leaf_curliness_part:
                    showHelpDialog(context.getResources().getString(R.string.info_leaf_curliness_part));
                    break;
                case R.id.btn_leaf_texture:
                    showHelpDialog(context.getResources().getString(R.string.info_leaf_texture));
                    break;
            }
        }
    };
    private int userId;
    private String nickname;
    // 图片路径
    private String pathCotyledonColor;
    private Uri imageUriCotyledonColor;
    private String pathCotyledonCount;
    private Uri imageUriCotyledonCount;
    private String pathCotyledonShape;
    private Uri imageUriCotyledonShape;
    private Map<String, Map<String, String>> map;
    private Map<String, String> imgPathMap1 = new HashMap<>();
    private Map<String, String> imgPathMap2 = new HashMap<>();
    private Map<String, String> imgPathMap3 = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Fresco.initialize(this);
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

        if (status != STATUS_NEW) {
            initView(false);
            initBasicInfo(plantId);
            initData(surveyPeriod);
            initPictures(surveyPeriod);
        } else {
            initView(true);
            initBasicInfo("");
        }

    }

    private void initToolBar() {
        titleText.setText(getText(R.string.species_data_pick));
        leftOneButton.setBackgroundResource(R.mipmap.ic_back);
        rightOneButton.setBackgroundResource(R.mipmap.ic_homepage);
        rightTwoButton.setBackgroundResource(R.mipmap.ic_no_save);

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

        rightTwoLayout.setVisibility(View.INVISIBLE);
    }

    private void initView(boolean isEditable) {
        View basicInfoLayout = LayoutInflater.from(context).inflate(R.layout.item_basicinfo, null);
        InfoItemBar itemBar = new InfoItemBar(context, getString(R.string.item_bar_basic));
        itemBar.addView(basicInfoLayout);
        itemBar.setShow(true);
        itemBar.setVisibilitySubmit(false);
        mainArea.addView(itemBar);

        editMaterialId = basicInfoLayout.findViewById(R.id.edt_material_id);
        editMaterialType = basicInfoLayout.findViewById(R.id.edt_material_type);
        editPlantId = basicInfoLayout.findViewById(R.id.edt_plant_id);

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
            if (!checkIsValid()) {
                Toast.makeText(context, "请检查必填项！", Toast.LENGTH_SHORT).show();
            } else {
                showDialog(SURVEY_PERIOD_GERMINATION);
            }
        });

        //子叶期View
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
        btnCotyledonSize = seedlingPeriodLayout.findViewById(R.id.btn_cotyledon_size);
        btnAddCotyledonSize = seedlingPeriodLayout.findViewById(R.id.btn_add_cotyledon_size);
        layoutRepeatedCotyledonSize = seedlingPeriodLayout.findViewById(R.id.layout_repeated_cotyledon_size);
        addRepeatedAttribute(btnAddCotyledonSize, layoutRepeatedCotyledonSize, "子叶大小");
        btnCotyledonSize.setOnClickListener(helpClickListener);

        spnCotyledonColor = seedlingPeriodLayout.findViewById(R.id.cotyledon_color);
        btnCotyledonColor = seedlingPeriodLayout.findViewById(R.id.btn_cotyledon_color);
        btnAddCotyledonColor = seedlingPeriodLayout.findViewById(R.id.btn_add_cotyledon_color);
        layoutRepeatedCotyledonColor = seedlingPeriodLayout.findViewById(R.id.layout_repeated_cotyledon_color);
        addRepeatedAttribute(btnAddCotyledonColor, layoutRepeatedCotyledonColor, "子叶颜色");
        btnCotyledonColor.setOnClickListener(helpClickListener);

        spnCotyledonCount = seedlingPeriodLayout.findViewById(R.id.cotyledon_count);
        edtCotyledonCount = seedlingPeriodLayout.findViewById(R.id.edt_cotyledon_count);
        btnCotyledonCount = seedlingPeriodLayout.findViewById(R.id.btn_cotyledon_count);
        btnAddCotyledonCount = seedlingPeriodLayout.findViewById(R.id.btn_add_cotyledon_count);
        layoutRepeatedCotyledonCount = seedlingPeriodLayout.findViewById(R.id.layout_repeated_cotyledon_count);
        addRepeatedAttribute(btnAddCotyledonCount, layoutRepeatedCotyledonCount, "子叶数目");
        btnCotyledonCount.setOnClickListener(helpClickListener);

        spnCotyledonShape = seedlingPeriodLayout.findViewById(R.id.cotyledon_shape);
        btnCotyledonShape = seedlingPeriodLayout.findViewById(R.id.btn_cotyledon_shape);
        btnAddCotyledonShape = seedlingPeriodLayout.findViewById(R.id.btn_add_cotyledon_shape);
        layoutRepeatedCotyledonShape = seedlingPeriodLayout.findViewById(R.id.layout_repeated_cotyledon_shape);
        addRepeatedAttribute(btnAddCotyledonShape, layoutRepeatedCotyledonShape, "子叶形状");
        btnCotyledonShape.setOnClickListener(helpClickListener);

        spnHeartLeafColor = seedlingPeriodLayout.findViewById(R.id.heart_leaf_color);
        btnHeartLeafColor = seedlingPeriodLayout.findViewById(R.id.btn_heart_leaf_color);
        btnAddHeartLeafColor = seedlingPeriodLayout.findViewById(R.id.btn_add_heart_leaf_color);
        layoutRepeatedHeartLeafColor = seedlingPeriodLayout.findViewById(R.id.layout_repeated_heart_leaf_color);
        addRepeatedAttribute(btnAddHeartLeafColor, layoutRepeatedHeartLeafColor, "心叶叶色");
        btnHeartLeafColor.setOnClickListener(helpClickListener);

        spnTrueLeafColor = seedlingPeriodLayout.findViewById(R.id.true_leaf_color);
        btnTrueLeafColor = seedlingPeriodLayout.findViewById(R.id.btn_true_leaf_color);
        btnAddTrueLeafColor = seedlingPeriodLayout.findViewById(R.id.btn_add_true_leaf_color);
        layoutRepeatedTrueLeafColor = seedlingPeriodLayout.findViewById(R.id.layout_repeated_true_leaf_color);
        addRepeatedAttribute(btnAddTrueLeafColor, layoutRepeatedTrueLeafColor, "真叶叶色");
        btnTrueLeafColor.setOnClickListener(helpClickListener);

        spnTrueLeafLength = seedlingPeriodLayout.findViewById(R.id.true_leaf_length);
        edtTrueLeafLength = seedlingPeriodLayout.findViewById(R.id.edt_true_leaf_length);
        btnTrueLeafLength = seedlingPeriodLayout.findViewById(R.id.btn_true_leaf_length);
        btnAddTrueLeafLength = seedlingPeriodLayout.findViewById(R.id.btn_add_true_leaf_length);
        layoutRepeatedTrueLeafLength = seedlingPeriodLayout.findViewById(R.id.layout_repeated_true_leaf_length);
        addRepeatedAttribute(btnAddTrueLeafLength, layoutRepeatedTrueLeafLength, "真叶长度");
        btnTrueLeafLength.setOnClickListener(helpClickListener);

        spnTrueLeafWidth = seedlingPeriodLayout.findViewById(R.id.true_leaf_width);
        edtTrueLeafWidth = seedlingPeriodLayout.findViewById(R.id.edt_true_leaf_width);
        btnTrueLeafWidth = seedlingPeriodLayout.findViewById(R.id.btn_true_leaf_width);
        btnAddTrueLeafWidth = seedlingPeriodLayout.findViewById(R.id.btn_add_true_leaf_width);
        layoutRepeatedTrueLeafWidth = seedlingPeriodLayout.findViewById(R.id.layout_repeated_true_leaf_width);
        addRepeatedAttribute(btnAddTrueLeafWidth, layoutRepeatedTrueLeafWidth, "真叶宽度");
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
        imgCotyledonColor.setLayoutManager(mCotyledonColorManager);
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


        seedlingPeriodItemBar.setSubmitListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkIsValid()) {
                    Toast.makeText(context, "请检查必填项！", Toast.LENGTH_SHORT).show();
                } else {
                    showDialog(SURVEY_PERIOD_SEEDLING);
                }
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
        btnPlantShape = rosettePeriodLayout.findViewById(R.id.btn_plant_shape);
        btnPlantShape.setOnClickListener(helpClickListener);

        spnPlantHeight = rosettePeriodLayout.findViewById(R.id.plant_height);
        btnPlantHeight = rosettePeriodLayout.findViewById(R.id.btn_plant_height);
        btnPlantHeight.setOnClickListener(helpClickListener);

        spnDevelopmentDegree = rosettePeriodLayout.findViewById(R.id.development_degree);
        btnDevelopmentDegree = rosettePeriodLayout.findViewById(R.id.btn_development_degree);
        btnDevelopmentDegree.setOnClickListener(helpClickListener);

        edtLeafCount = rosettePeriodLayout.findViewById(R.id.edt_leaf_count);
        btnLeafCount = rosettePeriodLayout.findViewById(R.id.btn_leaf_count);
        btnLeafCount.setOnClickListener(helpClickListener);

        edtSoftLeafThickness = rosettePeriodLayout.findViewById(R.id.edt_soft_leaf_thickness);
        btnSoftLeafThickness = rosettePeriodLayout.findViewById(R.id.btn_soft_leaf_thickness);
        btnSoftLeafThickness.setOnClickListener(helpClickListener);

        spnLeafLength = rosettePeriodLayout.findViewById(R.id.leaf_length);
        btnLeafLength = rosettePeriodLayout.findViewById(R.id.btn_leaf_length);
        btnLeafLength.setOnClickListener(helpClickListener);

        spnLeafWidth = rosettePeriodLayout.findViewById(R.id.leaf_width);
        btnLeafWidth = rosettePeriodLayout.findViewById(R.id.btn_leaf_width);
        btnLeafWidth.setOnClickListener(helpClickListener);

        spnLeafShape = rosettePeriodLayout.findViewById(R.id.leaf_shape);
        btnLeafShape = rosettePeriodLayout.findViewById(R.id.btn_leaf_shape);
        btnLeafShape.setOnClickListener(helpClickListener);

        spnLeafColor = rosettePeriodLayout.findViewById(R.id.leaf_color);
        btnLeafColor = rosettePeriodLayout.findViewById(R.id.btn_leaf_color);
        btnLeafColor.setOnClickListener(helpClickListener);

        spnLeafLuster = rosettePeriodLayout.findViewById(R.id.leaf_luster);
        btnLeafLuster = rosettePeriodLayout.findViewById(R.id.btn_leaf_luster);
        btnLeafLuster.setOnClickListener(helpClickListener);

        spnLeafFuzz = rosettePeriodLayout.findViewById(R.id.leaf_fuzz);
        btnLeafFuzz = rosettePeriodLayout.findViewById(R.id.btn_leaf_fuzz);
        btnLeafFuzz.setOnClickListener(helpClickListener);

        spnLeafMarginUndulance = rosettePeriodLayout.findViewById(R.id.leaf_margin_undulance);
        btnLeafMarginUndulance = rosettePeriodLayout.findViewById(R.id.btn_leaf_margin_undulance);
        btnLeafMarginUndulance.setOnClickListener(helpClickListener);

        spnLeafMarginSawtooth = rosettePeriodLayout.findViewById(R.id.leaf_margin_sawtooth);
        btnLeafMarginSawtooth = rosettePeriodLayout.findViewById(R.id.btn_leaf_margin_sawtooth);
        btnLeafMarginSawtooth.setOnClickListener(helpClickListener);

        spnLeafSmoothness = rosettePeriodLayout.findViewById(R.id.leaf_smoothness);
        btnLeafSmoothness = rosettePeriodLayout.findViewById(R.id.btn_leaf_smoothness);
        btnLeafSmoothness.setOnClickListener(helpClickListener);

        spnLeafProtuberance = rosettePeriodLayout.findViewById(R.id.leaf_protuberance);
        btnLeafProtuberance = rosettePeriodLayout.findViewById(R.id.btn_leaf_protuberance);
        btnLeafProtuberance.setOnClickListener(helpClickListener);

        spnLeafVeinLivingness = rosettePeriodLayout.findViewById(R.id.leaf_vein_livingness);
        btnLeafVeinLivingness = rosettePeriodLayout.findViewById(R.id.btn_leaf_vein_livingness);
        btnLeafVeinLivingness.setOnClickListener(helpClickListener);

        spnLeafKeelLivingness = rosettePeriodLayout.findViewById(R.id.leaf_keel_livingness);
        btnLeafKeelLivingness = rosettePeriodLayout.findViewById(R.id.btn_leaf_keel_livingness);
        btnLeafKeelLivingness.setOnClickListener(helpClickListener);

        spnLeafCurliness = rosettePeriodLayout.findViewById(R.id.leaf_curliness);
        btnLeafCurliness = rosettePeriodLayout.findViewById(R.id.btn_leaf_curliness);
        btnLeafCurliness.setOnClickListener(helpClickListener);

        spnLeafCurlinessPart = rosettePeriodLayout.findViewById(R.id.leaf_curliness_part);
        btnLeafCurlinessPart = rosettePeriodLayout.findViewById(R.id.btn_leaf_curliness_part);
        btnLeafCurlinessPart.setOnClickListener(helpClickListener);

        spnLeafTexture = rosettePeriodLayout.findViewById(R.id.leaf_texture);
        btnLeafTexture = rosettePeriodLayout.findViewById(R.id.btn_leaf_texture);
        btnLeafTexture.setOnClickListener(helpClickListener);

        rosettePeriodItemBar.setSubmitListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkIsValid()) {
                    Toast.makeText(context, "请检查必填项！", Toast.LENGTH_SHORT).show();
                } else {
                    showDialog(SURVEY_PERIOD_ROSETTE);
                }
            }
        });

        //添加莲座期总图片
        imgRosettePeriod = findViewById(R.id.img_rosette_period);
        mRosettePeriodAdapter = new ImageAdapter(context, mRosettePeriodImgList);
        imgRosettePeriod.setAdapter(mRosettePeriodAdapter);
        imgRosettePeriod.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
            }
        });

        //添加备注和自定义属性
        layoutCustomAttribute1 = findViewById(R.id.layout_custom_attribute1);
        btnAddRemark1 = findViewById(R.id.btn_add_remark1);
        btnAddAttribute1 = findViewById(R.id.btn_add_attribute1);
        addExtraAttribute(btnAddAttribute1, btnAddRemark1, layoutCustomAttribute1);

        layoutCustomAttribute2 = findViewById(R.id.layout_custom_attribute2);
        btnAddRemark2 = findViewById(R.id.btn_add_remark2);
        btnAddAttribute2 = findViewById(R.id.btn_add_attribute2);
        addExtraAttribute(btnAddAttribute2, btnAddRemark2, layoutCustomAttribute2);

        layoutCustomAttribute3 = findViewById(R.id.layout_custom_attribute3);
        btnAddRemark3 = findViewById(R.id.btn_add_remark3);
        btnAddAttribute3 = findViewById(R.id.btn_add_attribute3);
        addExtraAttribute(btnAddAttribute3, btnAddRemark3, layoutCustomAttribute3);

        layoutCustomAttribute4 = findViewById(R.id.layout_custom_attribute4);
        btnAddRemark4 = findViewById(R.id.btn_add_remark4);
        btnAddAttribute4 = findViewById(R.id.btn_add_attribute4);
        addExtraAttribute(btnAddAttribute4, btnAddRemark4, layoutCustomAttribute4);
    }

    //添加重复属性
    private void addRepeatedAttribute(Button button, LinearLayout layout, String attributeName) {
        button.setOnClickListener(v -> {
            CustomAttributeView customAttributeView = new CustomAttributeView(context, 1, attributeName);
            Button btnDelete = customAttributeView.findViewById(R.id.btn_delete);
            btnDelete.setOnClickListener(v1 -> {
                customAttributeView.removeAllViews();
            });
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            layout.addView(customAttributeView);
        });
    }

    //添加额外属性和备注
    private void addExtraAttribute(Button btnAddAttribute, Button btnAddRemark, LinearLayout layout) {
        btnAddAttribute.setOnClickListener(v -> {
            CustomAttributeView customAttributeView = new CustomAttributeView(context, 1);
            Button btnDelete = customAttributeView.findViewById(R.id.btn_delete);
            btnDelete.setOnClickListener(v1 -> {
                customAttributeView.removeAllViews();
            });
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            layout.addView(customAttributeView);
        });
        btnAddRemark.setOnClickListener(v -> {
            CustomAttributeView customAttributeView = new CustomAttributeView(context, 0);
            Button btnDelete = customAttributeView.findViewById(R.id.btn_delete);
            btnDelete.setOnClickListener(v1 -> {
                customAttributeView.removeAllViews();
            });
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            layout.addView(customAttributeView);
        });
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

    // 展示帮助对话框
    private void showHelpDialog(String specificCharacter) {
        // 获取数据
        HttpRequest.getMeasurementBySpecificCharacter(token, specificCharacter, new HttpRequest.IHelpCallback() {
            @Override
            public void onResponse(HelpInfo helpInfo) {
                String measurementBasis = helpInfo.data.measurementBasis;
                String observationMethod = helpInfo.data.observationMethod;
                String helpText = "测量标准：" + measurementBasis + "\n\n" + "观测方法：" + observationMethod;
                // 展示对话框
                final SweetAlertDialog sDialog = new SweetAlertDialog(context)
                        .setTitleText(context.getResources().getString(R.string.survey_help))
                        .setContentText(helpText);
                sDialog.show();
                TextView tv = sDialog.findViewById(R.id.content_text);
                tv.setTextSize(13f);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    // 校验已有数据是否合法
    private boolean checkIsValid() {
        if (TextUtils.isEmpty(editPlantId.getText())) {
            return false;
        } else {
            return true;
        }
    }

    private void showDialog(String surveyPeriod) {
        final SweetAlertDialog saveDialog = new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
                .setContentText(getString(R.string.upload_data_tip))
                .setConfirmText("确定")
                .setCancelText("取消")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        uploadPeriodData(surveyPeriod);
                        sweetAlertDialog.dismissWithAnimation();
                    }
                });
        saveDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
            }
        });
        saveDialog.show();
    }

    // 初始化基本数据
    private void initBasicInfo(String plantId) {
        // TODO
        // 展示基本信息
        commitInfo.setText(context.getResources().getString(R.string.info_nickname) + nickname);
        editMaterialId.setText(materialId);
        editMaterialType.setText(materialType);
        editPlantId.setText(plantId);
    }

    // 初始化本地数据库数据
    private void initLocalData() {
        // 查询本地数据
        SurveyData initData = surveyDataBox.get(Long.parseLong(materialId));
        if (initData == null) {
            return;
        }
        setSelection(spnCotyledonSize, initData.cotyledonSize);
        setSelection(spnCotyledonColor, initData.cotyledonColor);
        setSelection(spnCotyledonCount, initData.cotyledonCount);
        setSelection(spnCotyledonShape, initData.cotyledonShape);
        setSelection(spnHeartLeafColor, initData.heartLeafColor);
        setSelection(spnTrueLeafColor, initData.trueLeafColor);
        setSelection(spnTrueLeafLength, initData.trueLeafLength);
        setSelection(spnTrueLeafWidth, initData.trueLeafWidth);

    }

    // 初始化网络数据（文本数据）
    private void initData(String surveyPeriod) {
        // TODO
        // 网络请求具体数据
        HttpRequest.getSurveyDataDetailBySurveyId(token, surveyPeriod, surveyId, new HttpRequest.ISurveyCallback() {
            @Override
            public void onResponse(SurveyInfo surveyInfo) {
                updateUI(surveyPeriod, surveyInfo);
            }

            @Override
            public void onFailure() {
                runOnUiThread(() -> {
                    Toast.makeText(context, "网络请求失败", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    // 更新页面中特定时期的数据
    private void updateUI(String surveyPeriod, SurveyInfo surveyInfo) {
        switch (surveyPeriod) {
            case SURVEY_PERIOD_GERMINATION:
                editGerminationRate.setText(surveyInfo.data.germinationRate);
                break;
            case SURVEY_PERIOD_SEEDLING:
                setSelection(spnCotyledonSize, surveyInfo.data.cotyledonSize);
                setSelection(spnCotyledonColor, surveyInfo.data.cotyledonColor);
                String[] dataCotyledonCount = surveyInfo.data.cotyledonNumber.split(separator);
                setSelection(spnCotyledonCount, dataCotyledonCount[0]);
                edtCotyledonCount.setText(dataCotyledonCount[1]);
                setSelection(spnCotyledonShape, surveyInfo.data.cotyledonShape);
                setSelection(spnHeartLeafColor, surveyInfo.data.colorOfHeartLeaf);
                setSelection(spnTrueLeafColor, surveyInfo.data.trueLeafColor);
                String[] dataTrueLeafLength = surveyInfo.data.trueLeafLength.split(separator);
                setSelection(spnTrueLeafLength, dataTrueLeafLength[0]);
                edtTrueLeafLength.setText(dataTrueLeafLength[1]);
                String[] dataTrueLeafWidth = surveyInfo.data.trueLeafWidth.split(separator);
                setSelection(spnTrueLeafWidth, dataTrueLeafWidth[0]);
                edtTrueLeafWidth.setText(dataTrueLeafWidth[1]);
                break;
            case SURVEY_PERIOD_ROSETTE:
                setSelection(spnPlantShape, surveyInfo.data.plantType);
                setSelection(spnPlantHeight, surveyInfo.data.plantHeight);
                setSelection(spnDevelopmentDegree, surveyInfo.data.developmentDegree);
                edtLeafCount.setText(surveyInfo.data.numberOfLeaves);
                edtSoftLeafThickness.setText(surveyInfo.data.thicknessOfSoftLeaf);
                setSelection(spnLeafLength, surveyInfo.data.bladeLength);
                setSelection(spnLeafWidth, surveyInfo.data.bladeWidth);
                setSelection(spnLeafShape, surveyInfo.data.leafShape);
                setSelection(spnLeafColor, surveyInfo.data.leafColor);
                setSelection(spnLeafLuster, surveyInfo.data.leafLuster);
                setSelection(spnLeafFuzz, surveyInfo.data.leafFluff);
                setSelection(spnLeafMarginUndulance, surveyInfo.data.leafMarginWavy);
                setSelection(spnLeafMarginSawtooth, surveyInfo.data.leafMarginSerrate);
                setSelection(spnLeafSmoothness, surveyInfo.data.bladeSmooth);
                setSelection(spnLeafProtuberance, surveyInfo.data.sizeOfVesicles);
                setSelection(spnLeafVeinLivingness, surveyInfo.data.freshnessOfLeafVein);
                setSelection(spnLeafKeelLivingness, surveyInfo.data.brightnessOfMiddleRib);
                setSelection(spnLeafCurliness, surveyInfo.data.leafCurl);
                setSelection(spnLeafCurlinessPart, surveyInfo.data.leafCurlPart);
                setSelection(spnLeafTexture, surveyInfo.data.leafTexture);
                break;
            default:
                break;
        }
    }

    private void setSelection(Spinner spinner, String data) {
        if (TextUtils.isEmpty(data)) {
            return;
        }
        SpinnerAdapter spinnerAdapter = spinner.getAdapter();
        for (int j = 0; j < spinnerAdapter.getCount(); j++) {
            if (spinnerAdapter.getItem(j).toString().equals(data)) {
                spinner.setSelection(j, true);
            } else if (j == spinnerAdapter.getCount() - 1) {

            }
        }
    }

    // 初始化图片
    private void initPictures(String surveyPeriod) {
        // TODO
        // 获取图片url
        Map<String, ImageView> imageMap = initImageMap(surveyPeriod);

        for (String specCharacter : imageMap.keySet()) {
            HttpRequest.getPhoto(token, surveyId, specCharacter, new HttpRequest.IPhotoCallback() {
                @Override
                public void onResponse(PhotoInfo photoInfo) {
                    String url = photoInfo.data.url;
                    ImageView iv = imageMap.get(specCharacter);
                    if (iv != null) {
                        Glide.with(context).load(url).thumbnail(0.1f).into(iv);
                        iv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                watchOnlineLargePhoto(context, Uri.parse(url), specCharacter);
                            }
                        });
                    }
                }

                @Override
                public void onFailure() {
                    Toast.makeText(context, "图片加载失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private Map<String, ImageView> initImageMap(String surveyPeriod) {
        Map<String, ImageView> map = new HashMap<>();
        switch (surveyPeriod) {
            case SURVEY_PERIOD_GERMINATION:
                break;
            case SURVEY_PERIOD_SEEDLING:
                //TODO联网加载数据库内已调查图片
//                map.put(getResources().getString(R.string.info_cotyledon_color), ivCotyledonColor);
//                map.put(getResources().getString(R.string.info_cotyledon_count), ivCotyledonCount);
//                map.put(getResources().getString(R.string.info_cotyledon_shape), ivCotyledonShape);
                break;
            case SURVEY_PERIOD_ROSETTE:
                break;
            default:
                break;
        }
        return map;
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

    // 根据时期，更新服务器数据
    private void uploadPeriodData(String surveyPeriod) {
        try {
            String mPeriodData = getPeriodData(surveyPeriod);
            Log.d("surveyPeriod", surveyPeriod);
            Log.d("mPeriodData", mPeriodData);
            HttpRequest.requestAddSurveyData(token, surveyPeriod, mPeriodData, new HttpRequest.IResultCallback() {
                @Override
                public void onResponse(ResultInfo resultInfo) {
                    if (resultInfo.code == 200 && resultInfo.message.equals("操作成功")) {
                        String surveyId = resultInfo.data.observationId;
                        uploadPics(surveyPeriod, surveyId);
                        Toast.makeText(context, "更新成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "更新失败", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure() {
                    Toast.makeText(context, "更新失败", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getPeriodData(String surveyPeriod) {
        // TODO
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
//        jsonObject.addProperty("investigating_time", getCurrentTime());
//        jsonObject.addProperty("investigator", nickname);
        jsonObject.addProperty("userId", userId);

        return jsonObject;
    }

    private String getGerminationPeriodData() {
        JsonObject jsonObject = getBasicInfoData();

        String germinationRate = editGerminationRate.getText().toString();
        jsonObject.addProperty("germinationRate", germinationRate);

        return jsonObject.toString();
    }

    private String getSeedlingPeriodData() {
        JsonObject jsonObject = getBasicInfoData();

        String cotyledonSize = spnCotyledonSize.getSelectedItem().toString();
        String cotyledonColor = spnCotyledonColor.getSelectedItem().toString();
        String cotyledonCount = spnCotyledonCount.getSelectedItem().toString() + separator + edtCotyledonCount.getText();
        String cotyledonShape = spnCotyledonShape.getSelectedItem().toString();
        String heartLeafColor = spnHeartLeafColor.getSelectedItem().toString();
        String trueLeafColor = spnTrueLeafColor.getSelectedItem().toString();
        String trueLeafLength = spnTrueLeafLength.getSelectedItem().toString() + separator + edtTrueLeafLength.getText();
        String trueLeafWidth = spnTrueLeafWidth.getSelectedItem().toString() + separator + edtTrueLeafWidth.getText();

        jsonObject.addProperty("cotyledonSize", cotyledonSize);
        jsonObject.addProperty("cotyledonColor", cotyledonColor);
        jsonObject.addProperty("cotyledonNumber", cotyledonCount);
        jsonObject.addProperty("cotyledonShape", cotyledonShape);
        jsonObject.addProperty("colorOfHeartLeaf", heartLeafColor);
        jsonObject.addProperty("trueLeafColor", trueLeafColor);
        jsonObject.addProperty("trueLeafLength", trueLeafLength);
        jsonObject.addProperty("trueLeafWidth", trueLeafWidth);

        return jsonObject.toString();
    }

    private String getRosettePeriodData() {
        JsonObject jsonObject = getBasicInfoData();

        String plantId = editPlantId.getText().toString();
        String plantShape = spnPlantShape.getSelectedItem().toString();
        String plantHeight = spnPlantHeight.getSelectedItem().toString();
        String developmentDegree = spnDevelopmentDegree.getSelectedItem().toString();
        String leafCount = edtLeafCount.getText().toString();
        String softLeafThickness = edtSoftLeafThickness.getText().toString();
        String leafLength = spnLeafLength.getSelectedItem().toString();
        String leafWidth = spnLeafWidth.getSelectedItem().toString();
        String leafShape = spnLeafShape.getSelectedItem().toString();
        String leafColor = spnLeafColor.getSelectedItem().toString();
        String leafLuster = spnLeafLuster.getSelectedItem().toString();
        String leafFuzz = spnLeafFuzz.getSelectedItem().toString();
        String leafMarginUndulance = spnLeafMarginUndulance.getSelectedItem().toString();
        String leafMarginSawtooth = spnLeafMarginSawtooth.getSelectedItem().toString();
        String leafSmoothness = spnLeafSmoothness.getSelectedItem().toString();
        String leafProtuberance = spnLeafProtuberance.getSelectedItem().toString();
        String leafVeinLivingness = spnLeafVeinLivingness.getSelectedItem().toString();
        String leafKeelLivingness = spnLeafKeelLivingness.getSelectedItem().toString();
        String leafCurliness = spnLeafCurliness.getSelectedItem().toString();
        String leafCurlinessPart = spnLeafCurlinessPart.getSelectedItem().toString();
        String leafTexture = spnLeafTexture.getSelectedItem().toString();

        jsonObject.addProperty("material_type", materialType);
        jsonObject.addProperty("material_number", materialId);
        jsonObject.addProperty("plant_number", plantId);
        jsonObject.addProperty("investigating_time", getCurrentTime());
        jsonObject.addProperty("investigator", nickname);
        jsonObject.addProperty("plant_type", plantShape);
        jsonObject.addProperty("plant_height", plantHeight);
        jsonObject.addProperty("development_degree", developmentDegree);
        jsonObject.addProperty("number_of_leaves", leafCount);
        jsonObject.addProperty("thickness_of_soft_leaf", softLeafThickness);
        jsonObject.addProperty("blade_length", leafLength);
        jsonObject.addProperty("blade_width", leafWidth);
        jsonObject.addProperty("leaf_shape", leafShape);
        jsonObject.addProperty("leaf_color", leafColor);
        jsonObject.addProperty("leaf_luster", leafLuster);
        jsonObject.addProperty("leaf_fluff", leafFuzz);
        jsonObject.addProperty("leaf_margin_wavy", leafMarginUndulance);
        jsonObject.addProperty("leaf_margin_serrate", leafMarginSawtooth);
        jsonObject.addProperty("blade_smooth", leafSmoothness);
        jsonObject.addProperty("size_of_vesicles", leafProtuberance);
        jsonObject.addProperty("freshness_of_leaf_vein", leafVeinLivingness);
        jsonObject.addProperty("brightness_of_middle_rib", leafKeelLivingness);
        jsonObject.addProperty("leaf_curl", leafCurliness);
        jsonObject.addProperty("leaf_curl_part", leafCurlinessPart);
        jsonObject.addProperty("leaf_texture", leafTexture);

        return jsonObject.toString();
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
//        Map<String, String> imgPathMap;
//        switch (surveyPeriod) {
//            case SURVEY_PERIOD_GERMINATION:
//                imgPathMap = imgPathMap1;
//                break;
//            case SURVEY_PERIOD_SEEDLING:
//                imgPathMap = imgPathMap2;
//                break;
//            case SURVEY_PERIOD_ROSETTE:
//                imgPathMap = imgPathMap3;
//                break;
//            default:
//                imgPathMap = new HashMap<>();
//                break;
//        }
//        for (String specCharacter : imgPathMap.keySet()) {
//            String imgPath = imgPathMap.get(specCharacter);
//            if (TextUtils.isEmpty(imgPath)) {
//                continue;
//            }
//            HttpRequest.uploadPicture(token, surveyPeriod, surveyId, specCharacter, imgPath, new HttpRequest.INormalCallback() {
//                @Override
//                public void onResponse(NormalInfo normalInfo) {
//
//                }
//
//                @Override
//                public void onFailure() {
//
//                }
//            });
//        }

    }

    private void selectPhotoFromAlbum(int selectType) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.setType("image/*");
            startActivityForResult(intent, selectType);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
//            case TAKE_PHOTO_COTYLEDON_COLOR:
//                onResultOfPhoto(resultCode, pathCotyledonColor, ivCotyledonColor);
//                break;
//            case SELECT_PHOTO_COTYLEDON_COLOR:
//                if (data != null) {
//                    imageUriCotyledonColor = data.getData();
//                    pathCotyledonColor = getRealPathFromUri(context, imageUriCotyledonColor);
//                    String imgPath = getRealPathFromUri(context, imageUriCotyledonColor);
//                    imgPathMap2.put(getResources().getString(R.string.info_cotyledon_color), imgPath);
//                    //Log.d("Uriiiii2", imageUriColor + " || " + pathColor);
//                    if (imageUriCotyledonColor != null) {
//                        Bitmap bit = null;
//
//                        bit = getImageThumbnail(pathCotyledonColor, 50, 50);
//
//                        ivCotyledonColor.setImageBitmap(bit);
//                    }
//                }
//                break;
//            case TAKE_PHOTO_COTYLEDON_COUNT:
//                onResultOfPhoto(resultCode, pathCotyledonCount, ivCotyledonCount);
//                break;
//            case SELECT_PHOTO_COTYLEDON_COUNT:
//                if (data != null) {
//                    imageUriCotyledonCount = data.getData();
//                    pathCotyledonCount = getRealPathFromUri(context, imageUriCotyledonCount);
//                    String imgPath = getRealPathFromUri(context, imageUriCotyledonCount);
//                    imgPathMap2.put(getResources().getString(R.string.info_cotyledon_count), imgPath);
//                    //Log.d("Uriiiii2", imageUriColor + " || " + pathColor);
//                    if (imageUriCotyledonCount != null) {
//                        Bitmap bit = null;
//
//                        bit = getImageThumbnail(pathCotyledonCount, 50, 50);
//
//                        ivCotyledonCount.setImageBitmap(bit);
//                    }
//                }
//                break;
//            case TAKE_PHOTO_COTYLEDON_SHAPE:
//                onResultOfPhoto(resultCode, pathCotyledonShape, ivCotyledonShape);
//                break;
//            case SELECT_PHOTO_COTYLEDON_SHAPE:
//                if (data != null) {
//                    imageUriCotyledonShape = data.getData();
//                    pathCotyledonShape = getRealPathFromUri(context, imageUriCotyledonShape);
//                    String imgPath = getRealPathFromUri(context, imageUriCotyledonShape);
//                    imgPathMap2.put(getResources().getString(R.string.info_cotyledon_shape), imgPath);
//                    //Log.d("Uriiiii2", imageUriColor + " || " + pathColor);
//                    if (imageUriCotyledonShape != null) {
//                        Bitmap bit = null;
//
//                        bit = getImageThumbnail(pathCotyledonShape, 50, 50);
//
//                        ivCotyledonShape.setImageBitmap(bit);
//                    }
//                }
//                break;
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

    private void onResultOfPhoto(int resultCode, String path, ImageView ivShowPicture) {
        if (resultCode == RESULT_OK) {
            //在应用中显示图片
            Bitmap bitmap = null;
//            try {
//                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
            bitmap = getImageThumbnail(path, 50, 50);
            ivShowPicture.setImageBitmap(bitmap);
        }
    }
}
