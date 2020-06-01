package com.example.cabbage.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.example.cabbage.R;
import com.example.cabbage.utils.ARouterPaths;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = ARouterPaths.HISTORY_ACTIVITY)
public class HistoryActivity extends AppCompatActivity {

    @BindView(R.id.search_view)
    FloatingSearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        searchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction(String currentQuery) {
                if (currentQuery.equals("1")) {
                    ARouter.getInstance().build(ARouterPaths.SURVEY_ACTIVITY).navigation();
                } else {
                    ARouter.getInstance().build(ARouterPaths.SURVEY_ACTIVITY).navigation();
                }
            }
        });
    }
}
