package com.example.cabbage.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.example.cabbage.R;
import com.example.cabbage.network.HttpRequest;
import com.example.cabbage.network.UserInfo;
import com.example.cabbage.utils.ARouterPaths;
import com.example.cabbage.utils.NetworkUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainFragment extends Fragment {
    @BindView(R.id.search_view)
    FloatingSearchView searchView;
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }
    private View view;
    private Context self;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        self = getContext();
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {

        searchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                // 选择搜索框下的建议项
            }

            @Override
            public void onSearchAction(String currentQuery) {
                // 点击搜索
                if (NetworkUtils.isNetworkConnected(getContext())) {
                    searchAction(currentQuery);
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.network_wrong), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void searchAction(String input) {
        // 网络请求数据
        String speciesId = "";
        HttpRequest.requestSearch(speciesId, new HttpRequest.IUserInfoCallback() {
            @Override
            public void onResponse(UserInfo userInfo) {

            }

            @Override
            public void onFailure() {

            }
        });

        // 根据返回结果跳转页面
        if (input.equals("1")) {
            ARouter.getInstance().build(ARouterPaths.SURVEY_ACTIVITY).withString("speciesId", "").navigation();
        } else {
            ARouter.getInstance().build(ARouterPaths.SURVEY_ACTIVITY).navigation();
        }
    }
}
