package com.example.cabbage.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.example.cabbage.R;
import com.example.cabbage.data.ObjectBox;
import com.example.cabbage.data.SurveyData;
import com.example.cabbage.network.HttpRequest;
import com.example.cabbage.network.NormalInfo;
import com.example.cabbage.network.SurveyInfo;
import com.example.cabbage.utils.ARouterPaths;
import com.example.cabbage.view.InfoItemBar;
import com.google.gson.JsonObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.objectbox.Box;

import static com.example.cabbage.utils.ImageUtils.getImageThumbnail;

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
    private ImageButton ibCotyledonColor;
    private Button btnSelectFromAlbumCotyledonColor;
    private ImageView ivCotyledonColor;
    Button btnCotyledonColor;
    Spinner spnCotyledonCount;
    Button btnCotyledonCount;
    Spinner spnCotyledonShape;
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

    private Context context = this;

    Box<SurveyData> surveyDataBox;

    private String token;
    private int userId;
    private String nickname;

    // 页面的状态
    public static final int STATUS_NEW = 0;    // 新建
    public static final int STATUS_READ = 1;   // 只读
    public static final int STATUS_WRITE = 2;  // 修改

    private static final int TAKE_PHOTO_COTYLEDON_COLOR = 10;

    private static final String SURVEY_PERIOD_GERMINATION = "发芽期";
    private static final String SURVEY_PERIOD_SEEDLING = "幼苗期";
    private static final String SURVEY_PERIOD_ROSETTE = "莲座期";

    private String pathColor;
    private Uri imageUriColor;

    @Autowired
    public String materialId = "";

    @Autowired
    public String materialType = "";

    @Autowired
    public int status = STATUS_NEW;

    @Autowired(name="observationId")
    public String surveyId;

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

        if (status != STATUS_NEW) {
            initView(false);
            initBasicInfo();
            initData("");
        } else {
            initView(true);
            initBasicInfo();
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

        rightTwoLayout.setVisibility(View.GONE);
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

        View germinationPeriodLayout = LayoutInflater.from(context).inflate(R.layout.item_germination_period, null);
        InfoItemBar germinationPeriodItemBar = new InfoItemBar(context, getResources().getString(R.string.title_germination_period));
        germinationPeriodItemBar.addView(germinationPeriodLayout);
        germinationPeriodItemBar.setShow(true);
        germinationPeriodItemBar.setVisibilitySubmit(isEditable);
        mainArea.addView(germinationPeriodItemBar);

        editGerminationRate = germinationPeriodLayout.findViewById(R.id.edt_germination_rate);
        btnGerminationRate = germinationPeriodLayout.findViewById(R.id.btn_germination_rate);

        germinationPeriodItemBar.setSubmitListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPeriodData(SURVEY_PERIOD_GERMINATION);
            }
        });

        View seedlingPeriodLayout = LayoutInflater.from(context).inflate(R.layout.item_seedling_period, null);
        InfoItemBar seedlingPeriodItemBar = new InfoItemBar(context, getResources().getString(R.string.title_seedling_period));
        seedlingPeriodItemBar.addView(seedlingPeriodLayout);
        seedlingPeriodItemBar.setShow(true);
        seedlingPeriodItemBar.setVisibilitySubmit(isEditable);
        mainArea.addView(seedlingPeriodItemBar);

        spnCotyledonSize = seedlingPeriodLayout.findViewById(R.id.cotyledon_size);
        editCotyledonSize = seedlingPeriodLayout.findViewById(R.id.edit_cotyledon_size);
        btnCotyledonSize = seedlingPeriodLayout.findViewById(R.id.btn_cotyledon_size);

        spnCotyledonColor = seedlingPeriodLayout.findViewById(R.id.cotyledon_color);
        ibCotyledonColor = seedlingPeriodLayout.findViewById(R.id.ib_cotyledon_color);
        ibCotyledonColor.setOnClickListener(photosClickListener);
        btnSelectFromAlbumCotyledonColor = seedlingPeriodLayout.findViewById(R.id.btn_select_from_album_cotyledon_color);
        ivCotyledonColor = seedlingPeriodLayout.findViewById(R.id.iv_cotyledon_color);

        btnCotyledonColor = seedlingPeriodLayout.findViewById(R.id.btn_cotyledon_color);
        spnCotyledonCount = seedlingPeriodLayout.findViewById(R.id.cotyledon_count);
        btnCotyledonCount = seedlingPeriodLayout.findViewById(R.id.btn_cotyledon_count);
        spnCotyledonShape = seedlingPeriodLayout.findViewById(R.id.cotyledon_shape);
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

        seedlingPeriodItemBar.setSubmitListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPeriodData(SURVEY_PERIOD_SEEDLING);
            }
        });

        View rosettePeriodLayout = LayoutInflater.from(context).inflate(R.layout.item_rosette_period, null);
        InfoItemBar rosettePeriodItemBar = new InfoItemBar(context, getResources().getString(R.string.title_rosette_period));
        rosettePeriodItemBar.addView(rosettePeriodLayout);
        rosettePeriodItemBar.setShow(true);
        rosettePeriodItemBar.setVisibilitySubmit(isEditable);
        mainArea.addView(rosettePeriodItemBar);

        spnPlantShape = rosettePeriodLayout.findViewById(R.id.plant_shape);
        editPlantShape = rosettePeriodLayout.findViewById(R.id.edit_plant_shape);
        btnPlantShape = rosettePeriodLayout.findViewById(R.id.btn_plant_shape);
        spnPlantHeight = rosettePeriodLayout.findViewById(R.id.plant_height);
        editPlantHeight = rosettePeriodLayout.findViewById(R.id.edit_plant_height);
        btnPlantHeight = rosettePeriodLayout.findViewById(R.id.btn_plant_height);
        spnDevelopmentDegree = rosettePeriodLayout.findViewById(R.id.development_degree);
        editDevelopmentDegree = rosettePeriodLayout.findViewById(R.id.edit_development_degree);
        btnDevelopmentDegree = rosettePeriodLayout.findViewById(R.id.btn_development_degree);
        edtLeafCount = rosettePeriodLayout.findViewById(R.id.edt_leaf_count);
        btnLeafCount = rosettePeriodLayout.findViewById(R.id.btn_leaf_count);
        edtSoftLeafThickness = rosettePeriodLayout.findViewById(R.id.edt_soft_leaf_thickness);
        btnSoftLeafThickness = rosettePeriodLayout.findViewById(R.id.btn_soft_leaf_thickness);
        spnLeafLength = rosettePeriodLayout.findViewById(R.id.leaf_length);
        editLeafLength = rosettePeriodLayout.findViewById(R.id.edit_leaf_length);
        btnLeafLength = rosettePeriodLayout.findViewById(R.id.btn_leaf_length);
        spnLeafWidth = rosettePeriodLayout.findViewById(R.id.leaf_width);
        editLeafWidth = rosettePeriodLayout.findViewById(R.id.edit_leaf_width);
        btnLeafWidth = rosettePeriodLayout.findViewById(R.id.btn_leaf_width);
        spnLeafShape = rosettePeriodLayout.findViewById(R.id.leaf_shape);
        editLeafShape = rosettePeriodLayout.findViewById(R.id.edit_leaf_shape);
        btnLeafShape = rosettePeriodLayout.findViewById(R.id.btn_leaf_shape);
        spnLeafColor = rosettePeriodLayout.findViewById(R.id.leaf_color);
        editLeafColor = rosettePeriodLayout.findViewById(R.id.edit_leaf_color);
        btnLeafColor = rosettePeriodLayout.findViewById(R.id.btn_leaf_color);
        spnLeafLuster = rosettePeriodLayout.findViewById(R.id.leaf_luster);
        editLeafLuster = rosettePeriodLayout.findViewById(R.id.edit_leaf_luster);
        btnLeafLuster = rosettePeriodLayout.findViewById(R.id.btn_leaf_luster);
        spnLeafFuzz = rosettePeriodLayout.findViewById(R.id.leaf_fuzz);
        editLeafFuzz = rosettePeriodLayout.findViewById(R.id.edit_leaf_fuzz);
        btnLeafFuzz = rosettePeriodLayout.findViewById(R.id.btn_leaf_fuzz);
        spnLeafMarginUndulance = rosettePeriodLayout.findViewById(R.id.leaf_margin_undulance);
        editLeafMarginUndulance = rosettePeriodLayout.findViewById(R.id.edit_leaf_margin_undulance);
        btnLeafMarginUndulance = rosettePeriodLayout.findViewById(R.id.btn_leaf_margin_undulance);
        spnLeafMarginSawtooth = rosettePeriodLayout.findViewById(R.id.leaf_margin_sawtooth);
        editLeafMarginSawtooth = rosettePeriodLayout.findViewById(R.id.edit_leaf_margin_sawtooth);
        btnLeafMarginSawtooth = rosettePeriodLayout.findViewById(R.id.btn_leaf_margin_sawtooth);
        spnLeafSmoothness = rosettePeriodLayout.findViewById(R.id.leaf_smoothness);
        editLeafSmoothness = rosettePeriodLayout.findViewById(R.id.edit_leaf_smoothness);
        btnLeafSmoothness = rosettePeriodLayout.findViewById(R.id.btn_leaf_smoothness);
        spnLeafProtuberance = rosettePeriodLayout.findViewById(R.id.leaf_protuberance);
        editLeafProtuberance = rosettePeriodLayout.findViewById(R.id.edit_leaf_protuberance);
        btnLeafProtuberance = rosettePeriodLayout.findViewById(R.id.btn_leaf_protuberance);
        spnLeafVeinLivingness = rosettePeriodLayout.findViewById(R.id.leaf_vein_livingness);
        editLeafVeinLivingness = rosettePeriodLayout.findViewById(R.id.edit_leaf_vein_livingness);
        btnLeafVeinLivingness = rosettePeriodLayout.findViewById(R.id.btn_leaf_vein_livingness);
        spnLeafKeelLivingness = rosettePeriodLayout.findViewById(R.id.leaf_keel_livingness);
        editLeafKeelLivingness = rosettePeriodLayout.findViewById(R.id.edit_leaf_keel_livingness);
        btnLeafKeelLivingness = rosettePeriodLayout.findViewById(R.id.btn_leaf_keel_livingness);
        spnLeafCurliness = rosettePeriodLayout.findViewById(R.id.leaf_curliness);
        editLeafCurliness = rosettePeriodLayout.findViewById(R.id.edit_leaf_curliness);
        btnLeafCurliness = rosettePeriodLayout.findViewById(R.id.btn_leaf_curliness);
        spnLeafCurlinessPart = rosettePeriodLayout.findViewById(R.id.leaf_curliness_part);
        editLeafCurlinessPart = rosettePeriodLayout.findViewById(R.id.edit_leaf_curliness_part);
        btnLeafCurlinessPart = rosettePeriodLayout.findViewById(R.id.btn_leaf_curliness_part);
        spnLeafTexture = rosettePeriodLayout.findViewById(R.id.leaf_texture);
        editLeafTexture = rosettePeriodLayout.findViewById(R.id.edit_leaf_texture);
        btnLeafTexture = rosettePeriodLayout.findViewById(R.id.btn_leaf_texture);

        rosettePeriodItemBar.setSubmitListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPeriodData(SURVEY_PERIOD_ROSETTE);
            }
        });
    }

    View.OnClickListener photosClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ib_cotyledon_color:
                    String fileNameString = System.currentTimeMillis() + ".jpg";
                    File outputImage = null;
                    outputImage = new File(getExternalCacheDir(), fileNameString);
                    pathColor = outputImage.getAbsolutePath();
                    try {
                        if (outputImage.exists()) {
                            outputImage.delete();
                        }
                        outputImage.createNewFile();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (Build.VERSION.SDK_INT >= 24) {
                        imageUriColor = FileProvider.getUriForFile(context,
                                "com.example.cabbage.fileprovider", outputImage);
                        Log.d("context", "onClick: img" + imageUriColor);
                    } else {
                        imageUriColor = Uri.fromFile(outputImage);
                    }
//                    Log.d("Uriiiiiii", pathColor + " || " + imageUriColor);
                    //启动相机程序
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriColor);
                    startActivityForResult(intent, TAKE_PHOTO_COTYLEDON_COLOR);
                    break;
            }
        }
    };

    // 初始化基本数据
    private void initBasicInfo() {
        // TODO
        // 展示基本信息
        editMaterialId.setText(materialId);
        editMaterialType.setText(materialType);

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

    private void updateUI(String surveyPeriod, SurveyInfo surveyInfo) {
        switch (surveyPeriod) {
            case SURVEY_PERIOD_GERMINATION:
                editGerminationRate.setText(surveyInfo.data.germinationRate);
                break;
            case SURVEY_PERIOD_SEEDLING:
                setSelection(spnCotyledonSize, surveyInfo.data.cotyledonSize);
                setSelection(spnCotyledonColor, surveyInfo.data.cotyledonColor);
                setSelection(spnCotyledonCount, surveyInfo.data.cotyledonNumber);
                setSelection(spnCotyledonShape, surveyInfo.data.cotyledonShape);
                setSelection(spnHeartLeafColor, surveyInfo.data.colorOfHeartLeaf);
                setSelection(spnTrueLeafColor, surveyInfo.data.trueLeafColor);
                setSelection(spnTrueLeafLength, surveyInfo.data.trueLeafLength);
                setSelection(spnTrueLeafWidth, surveyInfo.data.trueLeafWidth);
                break;
            case SURVEY_PERIOD_ROSETTE:
                // TODO
                setSelection(spnPlantShape, "");
                setSelection(spnPlantHeight, "");
                setSelection(spnDevelopmentDegree, "");
                edtLeafCount.setText("");
                edtSoftLeafThickness.setText("");
                setSelection(spnLeafLength, "");
                setSelection(spnLeafWidth, "");
                setSelection(spnLeafShape, "");
                setSelection(spnLeafColor, "");
                setSelection(spnLeafLuster, "");
                setSelection(spnLeafFuzz, "");
                setSelection(spnLeafMarginUndulance, "");
                setSelection(spnLeafMarginSawtooth, "");
                setSelection(spnLeafSmoothness, "");
                setSelection(spnLeafProtuberance, "");
                setSelection(spnLeafVeinLivingness, "");
                setSelection(spnLeafKeelLivingness, "");
                setSelection(spnLeafCurliness, "");
                setSelection(spnLeafCurlinessPart, "");
                setSelection(spnLeafTexture, "");
                break;
            default:
                break;
        }
    }

    private void setSelection(Spinner spinner, String data) {
        SpinnerAdapter spinnerAdapter = spinner.getAdapter();
        for (int j = 0; j < spinnerAdapter.getCount(); j++) {
            if (data.equals(spinnerAdapter.getItem(j).toString())) {
                spinner.setSelection(j, true);
            } else if (j == spinnerAdapter.getCount() - 1) {

            }
        }
    }

    // 初始化图片
    private void initPictures() {
        // TODO
        // 获取图片url
        String url = "";

        // 加载图片
        ImageView imageView = null;
        Glide.with(context).load(url).into(ivCotyledonColor);
    }

    private void loadImage(ImageView imageView, String url) {

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
    private boolean uploadPeriodData(String surveyPeriod) {
        try {
            String mPeriodData = getPeriodData(surveyPeriod);
            Log.d("surveyPeriod", surveyPeriod);
            Log.d("mPeriodData", mPeriodData);
            HttpRequest.requestAddSurveyData(token, surveyPeriod, mPeriodData, new HttpRequest.INormalCallback() {
                @Override
                public void onResponse(NormalInfo normalInfo) {
                    if (normalInfo.code == 200 && normalInfo.message.equals("操作成功")) {
                        Log.d("thread:" + context.toString(), "" + (Looper.getMainLooper() == Looper.myLooper()));
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
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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
        String cotyledonCount = spnCotyledonCount.getSelectedItem().toString();
        String cotyledonShape = spnCotyledonShape.getSelectedItem().toString();
        String heartLeafColor = spnHeartLeafColor.getSelectedItem().toString();
        String trueLeafColor = spnTrueLeafColor.getSelectedItem().toString();
        String trueLeafLength = spnTrueLeafLength.getSelectedItem().toString();
        String trueLeafWidth = spnTrueLeafWidth.getSelectedItem().toString();

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

    private void uploadPics() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO_COTYLEDON_COLOR:
                onResultOfPhoto(resultCode, pathColor, ivCotyledonColor);
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
