package com.example.cabbage.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.cabbage.R;
import com.example.cabbage.activity.LanguageActivity;
import com.example.cabbage.activity.MainActivity;
import com.example.cabbage.utils.ARouterPaths;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;

/**
 * Author:created by Kang on 2020/9/9
 * Email:zyk970512@163.com
 * Annotation:用户中心页面
 */
public class UserFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.img_avatar)
    RoundedImageView imgAvatar;
    @BindView(R.id.txt_nickname)
    TextView txtNickname;
    @BindView(R.id.txt_role)
    TextView txtRole;
    @BindView(R.id.txt_history)
    TextView txtHistory;
    @BindView(R.id.txt_pw)
    TextView txtPw;
    @BindView(R.id.txt_logout)
    TextView txtLogout;
    @BindView(R.id.txt_language)
    TextView txtLanguage;
    @BindView(R.id.txt_clear_cache)
    TextView txtClearCache;
    @BindView(R.id.txt_quit)
    TextView txtQuit;

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    private View view;
    private Context self;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);
        self = getActivity().getApplicationContext();
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        SharedPreferences sp = self.getSharedPreferences("userInfo", MODE_PRIVATE);
        String nickname = sp.getString("username", getString(R.string.user_role));
        String headImgUrl = sp.getString("headImgUrl", ""); //暂时没有数据

        if (!TextUtils.isEmpty(headImgUrl)) {
            //加载网络图片
        } else {
            imgAvatar.setImageResource(R.mipmap.ic_user_avatar);
        }
        txtNickname.setText(nickname);
        txtRole.setText(getString(R.string.user_role));
        txtHistory.setOnClickListener(this);
        txtPw.setOnClickListener(this);
        txtLanguage.setOnClickListener(this);
        txtClearCache.setOnClickListener(this);
        txtLogout.setOnClickListener(this);
        txtQuit.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_history:
                ARouter.getInstance().build(ARouterPaths.HISTORY_ACTIVITY).navigation();
                break;
            case R.id.txt_pw:
                ARouter.getInstance().build(ARouterPaths.PWD_ACTIVITY).navigation();
                break;
            case R.id.txt_language:
                LanguageActivity.enter(getContext());
                break;
            case R.id.txt_clear_cache:
                SharedPreferences sp2 = self.getSharedPreferences("lastMaterial", MODE_PRIVATE);
                SharedPreferences.Editor editor2 = sp2.edit();
                editor2.clear();
                editor2.apply();
                Toast.makeText(self, R.string.clear_cache_success, Toast.LENGTH_SHORT).show();
                MainActivity.reStart(self);
                break;
            case R.id.txt_logout:
                SharedPreferences sp = self.getSharedPreferences("userInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.apply();
                ARouter.getInstance().build(ARouterPaths.LOGIN_ACTIVITY).navigation();
                Objects.requireNonNull(getActivity()).finish();
                break;
            case R.id.txt_quit:
                Objects.requireNonNull(getActivity()).finish();
                break;
        }
    }

}
