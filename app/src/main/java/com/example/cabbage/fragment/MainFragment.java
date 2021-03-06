package com.example.cabbage.fragment;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.example.cabbage.R;
import com.example.cabbage.data.DataHelper;
import com.example.cabbage.data.MaterialSuggestion;
import com.example.cabbage.network.HttpRequest;
import com.example.cabbage.network.MaterialInfo;
import com.example.cabbage.utils.ARouterPaths;
import com.example.cabbage.utils.NetworkUtils;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.cabbage.activity.SurveyActivity.STATUS_COPY;
import static com.example.cabbage.activity.SurveyActivity.STATUS_NEW;

public class MainFragment extends Fragment {
    @BindView(R.id.search_view)
    FloatingSearchView searchView;
    @BindView(R.id.btn_paste_data)
    LinearLayout btnPasteData;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    private View view;
    private Context self;
    private Unbinder unbinder;

    private String token;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        self = getContext();
        unbinder = ButterKnife.bind(this, view);

        SharedPreferences sp = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        token = sp.getString("token", "");
        btnPasteData.setOnClickListener(v -> {
            ClipboardManager cm = (ClipboardManager) Objects.requireNonNull(getActivity()).getSystemService(Context.CLIPBOARD_SERVICE);
            Intent dataIntent = Objects.requireNonNull(cm.getPrimaryClip()).getItemAt(0).getIntent();
            if (dataIntent != null) {
                String surveyId = dataIntent.getStringExtra("surveyId");
                String surveyPeriod = dataIntent.getStringExtra("surveyPeriod");
                String materialId = dataIntent.getStringExtra("materialId");
                String materialType = dataIntent.getStringExtra("materialType");
                Toast.makeText(getContext(), R.string.paste_data_success, Toast.LENGTH_SHORT).show();
                ARouter.getInstance().build(ARouterPaths.SURVEY_ACTIVITY)
                        .withString("surveyId", surveyId)
                        .withString("surveyPeriod", surveyPeriod)
                        .withString("materialId", materialId)
                        .withString("materialType", materialType)
                        .withInt("status", STATUS_COPY)
                        .navigation();
            } else {
                Toast.makeText(getContext(), R.string.data_no_get, Toast.LENGTH_SHORT).show();
            }
        });

        initView();
        return view;
    }

    private void initView() {
        searchView.setOnQueryChangeListener((oldQuery, newQuery) -> {
            if (!oldQuery.equals("") && newQuery.equals("")) {
                searchView.clearSuggestions();
            } else {
                // 网络请求数据
                if (NetworkUtils.isNetworkConnected(getContext())) {
                    HttpRequest.requestSearch(token, newQuery, new HttpRequest.IMaterialCallback() {
                        @Override
                        public void onResponse(MaterialInfo materialInfo) {
                            searchView.hideProgress();
                            if (materialInfo.code == 200 && materialInfo.message.equals(getString(R.string.option_success))) {
                                List<MaterialSuggestion> newSuggestion = DataHelper.toSuggestionList(materialInfo.data.list);
                                searchView.swapSuggestions(newSuggestion);
                            } else {
                                Toast.makeText(getContext(), R.string.query_success, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure() {
                            Toast.makeText(getContext(), R.string.query_fail, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.network_wrong), Toast.LENGTH_SHORT).show();
                }
            }
        });

        searchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
            @Override
            public void onBindSuggestion(View suggestionView, ImageView leftIcon, TextView textView, SearchSuggestion item, int itemPosition) {
                if (item instanceof MaterialSuggestion) {
                    MaterialSuggestion materialSuggestion = (MaterialSuggestion) item;
                    String textColor = "#000000";
                    String textLight = "#787878";
                    textView.setTextColor(Color.parseColor(textColor));
                    String text = materialSuggestion.getBody()
                            .replaceFirst(searchView.getQuery(),
                                    "<font color=\"" + textLight + "\">" + searchView.getQuery() + "</font>");
                    textView.setText(Html.fromHtml(text));
                }
            }
        });

        searchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                // 选择搜索框下的建议项
                if (searchSuggestion instanceof MaterialSuggestion) {
                    MaterialSuggestion materialSuggestion = (MaterialSuggestion) searchSuggestion;
                    String materialId = materialSuggestion.getMaterialId();
                    String materialType = materialSuggestion.getMaterialType();
                    ARouter.getInstance().build(ARouterPaths.SURVEY_ACTIVITY)
                            .withString("materialId", materialId)
                            .withString("materialType", materialType)
                            .withInt("status", STATUS_NEW)
                            .navigation();
                }
            }

            @Override
            public void onSearchAction(String currentQuery) {

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
        HttpRequest.requestSearch(token, input, new HttpRequest.IMaterialCallback() {
            @Override
            public void onResponse(MaterialInfo materialInfo) {
                if (materialInfo.code == 200 && materialInfo.message.equals(getString(R.string.query_success))) {

                } else {
                    Toast.makeText(getContext(), R.string.query_fail, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure() {

            }
        });

        // 根据返回结果跳转页面
        ARouter.getInstance().build(ARouterPaths.SURVEY_ACTIVITY).withString("speciesId", input).navigation();
    }
}
