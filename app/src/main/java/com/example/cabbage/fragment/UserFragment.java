package com.example.cabbage.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.cabbage.R;
import com.example.cabbage.utils.ARouterPaths;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class UserFragment extends Fragment implements View.OnClickListener{

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

    public static UserFragment newInstance() {
        UserFragment fragment = new UserFragment();
        return fragment;
    }

    private View view;
    private Context self;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);
        self = getContext();
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        imgAvatar.setImageResource(R.mipmap.ic_user_avatar);
        txtNickname.setText("测试账号");
        txtRole.setText("实验员");
        txtHistory.setOnClickListener(this);
        txtPw.setOnClickListener(this);
        txtLogout.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_history:
                ARouter.getInstance().build(ARouterPaths.HISTORY_ACTIVITY).navigation();
                break;
            case R.id.txt_pw:
                ARouter.getInstance().build(ARouterPaths.PWD_ACTIVITY).navigation();
                break;
            case R.id.txt_logout:
                ARouter.getInstance().build(ARouterPaths.LOGIN_ACTIVITY).navigation();
                getActivity().finish();
                break;
        }
    }
}
