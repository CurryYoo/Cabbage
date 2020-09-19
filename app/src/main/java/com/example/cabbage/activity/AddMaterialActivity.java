package com.example.cabbage.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.cabbage.R;
import com.example.cabbage.base.BaseActivity;
import com.example.cabbage.network.HttpRequest;
import com.example.cabbage.network.MaterialData;
import com.example.cabbage.network.MaterialInfo;
import com.example.cabbage.network.MaterialNumberInfo;
import com.example.cabbage.utils.ARouterPaths;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.cabbage.utils.UIUtils.checkIsValid;

/**
 * Author:Kang
 * Date:2020/9/14
 * Description:
 */
@Route(path = ARouterPaths.ADD_MATERIAL_ACTIVITY)
public class AddMaterialActivity extends BaseActivity {

    @BindView(R.id.left_one_button)
    ImageView leftOneButton;
    @BindView(R.id.left_one_layout)
    LinearLayout leftOneLayout;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.edt_material_number)
    EditText edtMaterialNumber;
    @BindView(R.id.edt_material_type)
    EditText edtMaterialType;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.edt_material_year)
    EditText edtMaterialYear;
    @BindView(R.id.edt_material_season)
    EditText edtMaterialSeason;
    @BindView(R.id.edt_material_origin)
    EditText edtMaterialOrigin;
    @BindView(R.id.edt_material_feature)
    EditText edtMaterialFeature;
    @BindView(R.id.edt_material_oddLeeds)
    EditText edtMaterialOddLeeds;
    @BindView(R.id.edt_material_experiment)
    EditText edtMaterialExperiment;
    @BindView(R.id.edt_material_paternal)
    EditText edtMaterialPaternal;

    private Context self;
    private int userId;
    private String token;

    View.OnClickListener toolBarOnClickListener = v -> {
        switch (v.getId()) {
            case R.id.left_one_layout:
                finish();
            default:
                break;
        }
    };
    View.OnClickListener onClickListener = v -> {
        if (checkIsValid(edtMaterialNumber) || checkIsValid(edtMaterialType)) {
            Toast.makeText(this, R.string.check_required, Toast.LENGTH_SHORT).show();
        } else {
            queryMaterialByNumber();
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_material);
        ButterKnife.bind(this);

        self = getApplicationContext();
        //验证用户
        SharedPreferences sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        token = sp.getString("token", "");
        userId = sp.getInt("userId", 1);

        initToolbar();
        initView();
    }

    private void initToolbar() {
        leftOneButton.setBackgroundResource(R.mipmap.ic_back);
        leftOneLayout.setBackgroundResource(R.drawable.selector_trans_button);
        titleText.setText(R.string.add_material);
        leftOneLayout.setOnClickListener(toolBarOnClickListener);
    }

    private void initView() {
        btnConfirm.setOnClickListener(onClickListener);
    }

    private void queryMaterialByNumber() {
        try {
            HttpRequest.queryMaterialByNumber(token, edtMaterialNumber.getText().toString(), new HttpRequest.IMaterialNumberCallback() {
                @Override
                public void onResponse(MaterialNumberInfo materialNumberInfo) {
                    if (materialNumberInfo.data == null) {
                        uploadMaterialNumber();
                    } else {
                        Toast.makeText(self, R.string.material_number_repeated, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure() {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void uploadMaterialNumber() {
        try {
            MaterialData materialData = new MaterialData();
            materialData.userId = userId;
            materialData.materialNumber = edtMaterialNumber.getText().toString();
            materialData.materialType = edtMaterialType.getText().toString();
            materialData.year = edtMaterialYear.getText().toString();
            materialData.season=edtMaterialSeason.getText().toString();
            materialData.origin=edtMaterialOrigin.getText().toString();
            materialData.feature=edtMaterialFeature.getText().toString();
            materialData.oddLeeds=edtMaterialOddLeeds.getText().toString();
            materialData.paternal=edtMaterialPaternal.getText().toString();
            HttpRequest.uploadMaterial(token, materialData.toString(), new HttpRequest.IMaterialCallback() {
                @Override
                public void onResponse(MaterialInfo materialInfo) {
                    if (materialInfo.code == 200) {
                        Toast.makeText(self, R.string.update_success, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(self, materialInfo.toString(), Toast.LENGTH_SHORT).show();
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


}
