package com.example.cabbage.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.cabbage.R;
import com.example.cabbage.network.HttpRequest;
import com.example.cabbage.network.UserInfo;
import com.example.cabbage.utils.ARouterPaths;
import com.example.cabbage.view.InfoItemBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

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

    //性状
    @BindView(R.id.cotyledon_size)
    Spinner cotyledonSize;
    @BindView(R.id.edit_cotyledon_size)
    EditText editCotyledonSize;
    @BindView(R.id.btn_cotyledon_size)
    Button btnCotyledonSize;
    @BindView(R.id.cotyledon_color)
    Spinner cotyledonColor;
    @BindView(R.id.edit_cotyledon_color)
    EditText editCotyledonColor;
    @BindView(R.id.btn_cotyledon_color)
    Button btnCotyledonColor;
    @BindView(R.id.cotyledon_count)
    Spinner cotyledonCount;
    @BindView(R.id.edit_cotyledon_count)
    EditText editCotyledonCount;
    @BindView(R.id.btn_cotyledon_count)
    Button btnCotyledonCount;
    @BindView(R.id.cotyledon_shape)
    Spinner cotyledonShape;
    @BindView(R.id.edit_cotyledon_shape)
    EditText editCotyledonShape;
    @BindView(R.id.btn_cotyledon_shape)
    Button btnCotyledonShape;
    @BindView(R.id.heart_leaf_color)
    Spinner heartLeafColor;
    @BindView(R.id.edit_heart_leaf_color)
    EditText editHeartLeafColor;
    @BindView(R.id.btn_heart_leaf_color)
    Button btnHeartLeafColor;
    @BindView(R.id.true_leaf_color)
    Spinner trueLeafColor;
    @BindView(R.id.edit_true_leaf_color)
    EditText editTrueLeafColor;
    @BindView(R.id.btn_true_leaf_color)
    Button btnTrueLeafColor;
    @BindView(R.id.true_leaf_length)
    Spinner trueLeafLength;
    @BindView(R.id.edit_true_leaf_length)
    EditText editTrueLeafLength;
    @BindView(R.id.btn_true_leaf_length)
    Button btnTrueLeafLength;
    @BindView(R.id.true_leaf_width)
    Spinner trueLeafWidth;
    @BindView(R.id.edit_true_leaf_width)
    EditText editTrueLeafWidth;
    @BindView(R.id.btn_true_leaf_width)
    Button btnTrueLeafWidth;

    private Context context = this;

    @Autowired
    public String speciesId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        ButterKnife.bind(this);

        ARouter.getInstance().inject(this);

        initToolBar();
        initView();
        initData();

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

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.item_basicinfo, null);
        InfoItemBar itemBar = new InfoItemBar(context, getString(R.string.item_bar_basic));
        itemBar.addView(view);
        itemBar.setShow(true);
        mainArea.addView(itemBar);

        View seedlingPeriodLayout = LayoutInflater.from(context).inflate(R.layout.item_seedling_period, null);
        InfoItemBar seedlingPeriodItemBar = new InfoItemBar(context, getResources().getString(R.string.title_seedling_period));
        seedlingPeriodItemBar.addView(seedlingPeriodLayout);
        seedlingPeriodItemBar.setShow(true);
        mainArea.addView(seedlingPeriodItemBar);
    }

    private void initData() {
        // 网络请求具体数据
        HttpRequest.requestSpeciesData(speciesId, "", new HttpRequest.IUserInfoCallback() {
            @Override
            public void onResponse(UserInfo userInfo) {

            }

            @Override
            public void onFailure() {

            }
        });
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
                                    updateData();
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

    private boolean updateData() {


        return false;
    }

    private void getData() {
        cotyledonSize.getSelectedItem().toString();
    }

}
