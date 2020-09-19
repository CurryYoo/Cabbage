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
import com.example.cabbage.utils.ARouterPaths;
import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

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

    private Context self;
    private int userId;
    private String token;

    private String mUrl = "http://47.93.117.9/";//"http://47.93.117.9/"
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
            uploadMaterialNumber();
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
        titleText.setText(R.string.back_end_management_system);
        leftOneLayout.setOnClickListener(toolBarOnClickListener);
    }

    private void initView() {
        btnConfirm.setOnClickListener(onClickListener);
    }

    private void uploadMaterialNumber() {
        try {

//            JsonObject jsonObject = new JsonObject();
////            jsonObject.addProperty("material_number", edtMaterialNumber.getText().toString());
////            jsonObject.addProperty("material_type", edtMaterialType.getText().toString());
////            String materialData = jsonObject.toString();

            MaterialData materialData=new MaterialData();
            materialData.userId=userId;
            materialData.materialNumber=edtMaterialNumber.getText().toString();
            materialData.materialType=edtMaterialType.getText().toString();
            Timber.d(materialData.toString());
            HttpRequest.uploadMaterial(token, materialData.toString(), new HttpRequest.IMaterialCallback() {
                @Override
                public void onResponse(MaterialInfo materialInfo) {
//                    if (materialInfo.code == 200) {
//                        Toast.makeText(self, R.string.update_success, Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(self, R.string.update_fail, Toast.LENGTH_SHORT).show();
//                    }
//                    Toast.makeText(self, materialInfo.toString(), Toast.LENGTH_SHORT).show();
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
