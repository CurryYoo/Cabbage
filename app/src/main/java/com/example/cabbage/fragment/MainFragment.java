package com.example.cabbage.fragment;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.example.cabbage.R;
import com.example.cabbage.adapter.LastMaterialAdapter;
import com.example.cabbage.data.DataHelper;
import com.example.cabbage.data.MaterialSuggestion;
import com.example.cabbage.network.HttpRequest;
import com.example.cabbage.network.MaterialData;
import com.example.cabbage.network.MaterialInfo;
import com.example.cabbage.network.SurveyInfo;
import com.example.cabbage.utils.ARouterPaths;
import com.example.cabbage.utils.NetworkUtils;
import com.example.plant_analysis.AnalysisActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import timber.log.Timber;

import static android.content.Context.MODE_PRIVATE;
import static android.text.Html.FROM_HTML_MODE_COMPACT;
import static com.example.cabbage.utils.StaticVariable.STATUS_CACHE;
import static com.example.cabbage.utils.StaticVariable.STATUS_COPY;
import static com.example.cabbage.utils.StaticVariable.STATUS_NEW;
import static java.util.concurrent.Executors.newSingleThreadExecutor;

/**
 * @author Kang
 * @date 2020/9/22
 * MainFragment
 */
public class MainFragment extends Fragment {
    private static String URL = "http://47.93.117.9/";
    @BindView(R.id.search_view)
    FloatingSearchView searchView;
    @BindView(R.id.btn_paste_data)
    LinearLayout btnPasteData;
    @BindView(R.id.btn_add_material)
    LinearLayout btnAddMaterial;
    @BindView(R.id.btn_cache_data)
    LinearLayout btnCacheData;
    @BindView(R.id.recycler_view_last)
    RecyclerView recyclerViewLast;
    @BindView(R.id.btn_web)
    LinearLayout btnWeb;
    @BindView(R.id.btn_analysis)
    FloatingActionButton btnAnalysis;
    @OnClick(R.id.btn_analysis)
    void analyse() {
        startActivity(new Intent(getContext(), AnalysisActivity.class));
    }

    private Context self;
    private Unbinder unbinder;
    private String token;
    /**
     * 存储最近三个材料信息
     */
    private SharedPreferences sp;
    /**
     * 使用MaterialData仅仅作为容器，和其中变量无对应
     */
    private List<MaterialData> lastList = new ArrayList<>();
    private LastMaterialAdapter lastMaterialAdapter;

    private static ExecutorService executorService = newSingleThreadExecutor();
    private Handler handler = new Handler();

    private View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.btn_paste_data:
                //获取剪贴板数据
                ClipboardManager cm = (ClipboardManager) Objects.requireNonNull(getActivity()).getSystemService(Context.CLIPBOARD_SERVICE);

                if (cm.getPrimaryClip() != null && cm.getPrimaryClip().getDescription().getLabel() != null && "copyData".equals(cm.getPrimaryClip().getDescription().getLabel().toString())) {
                    Intent dataIntent = Objects.requireNonNull(cm.getPrimaryClip()).getItemAt(0).getIntent();
                    Toast.makeText(getContext(), R.string.paste_data_success, Toast.LENGTH_SHORT).show();
                    ARouter.getInstance().build(ARouterPaths.SURVEY_ACTIVITY)
                            .withString("surveyId", dataIntent.getStringExtra("surveyId"))
                            .withString("surveyPeriod", dataIntent.getStringExtra("surveyPeriod"))
                            .withString("materialId", dataIntent.getStringExtra("materialId"))
                            .withString("materialType", dataIntent.getStringExtra("materialType"))
                            .withInt("status", STATUS_COPY)
                            .navigation();
                } else {
                    Toast.makeText(getContext(), R.string.data_no_get, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_add_material:
                ARouter.getInstance().build(ARouterPaths.ADD_MATERIAL_ACTIVITY).navigation();
                break;
            case R.id.btn_cache_data:
                boolean hasCache = sp.getBoolean("hasCache", false);
                if (hasCache) {
                    executorService.execute(() -> {
                        //访问文件数据
                        File file = new File(getContext().getCacheDir(), "cache.txt");
                        String cacheData = "";
                        try {
                            FileInputStream inputStream = new FileInputStream(file);
                            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                            cacheData = reader.readLine();
                            inputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // ui
                        String finalCacheData = cacheData;
                        handler.post(() -> {
                            ARouter.getInstance().build(ARouterPaths.SURVEY_ACTIVITY)
                                    .withInt("status", STATUS_CACHE)
                                    .withString("cacheData", finalCacheData)
                                    .navigation();
                        });
                    });
                } else {
                    Toast.makeText(getContext(), R.string.has_no_cache, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_web:
                Uri uri = Uri.parse(URL);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            default:
                break;
        }
    };

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        self = getActivity().getApplicationContext();
        unbinder = ButterKnife.bind(this, view);

        sp = self.getSharedPreferences("userInfo", MODE_PRIVATE);
        token = sp.getString("token", "");
        btnPasteData.setOnClickListener(onClickListener);
        btnCacheData.setOnClickListener(onClickListener);
        btnAddMaterial.setOnClickListener(onClickListener);
        btnWeb.setOnClickListener(onClickListener);

        initView();
        return view;
    }

    private void initView() {
        //加载最近三次采集材料编号
        lastMaterialAdapter = new LastMaterialAdapter(self, R.layout.item_last_material, lastList);
        lastMaterialAdapter.setOnItemClickListener((adapter, view, position) -> ARouter.getInstance().build(ARouterPaths.SURVEY_ACTIVITY)
                .withString("materialId", lastList.get(position).materialNumber)
                .withString("materialType", lastList.get(position).materialType)
                .withString("surveyPeriod", lastList.get(position).season)
                .withInt("status", STATUS_NEW)
                .navigation());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(self, RecyclerView.VERTICAL, false);
        recyclerViewLast.setLayoutManager(linearLayoutManager);
        recyclerViewLast.setAdapter(lastMaterialAdapter);
        searchView.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_float_search, null));

        searchView.setOnQueryChangeListener((oldQuery, newQuery) -> {
            if (!"".equals(oldQuery) && "".equals(newQuery)) {
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

        //获取焦点时弹出搜索建议
        searchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
                // 网络请求数据
                if (NetworkUtils.isNetworkConnected(getContext())) {
                    HttpRequest.requestSearch(token, searchView.getQuery(), new HttpRequest.IMaterialCallback() {
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

            @Override
            public void onFocusCleared() {

            }
        });
        searchView.setOnBindSuggestionCallback((suggestionView, leftIcon, textView, item, itemPosition) -> {
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
                    searchView.clearFocus();
                }
            }

            @Override
            public void onSearchAction(String currentQuery) {
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initLastMaterial();
    }

    private void initLastMaterial() {
        lastList.clear();
        JsonObject jsonObject = new JsonObject();
        SharedPreferences sp = self.getSharedPreferences("lastMaterial", MODE_PRIVATE);

        JsonObject jsonObjectOld = new JsonParser().parse(sp.getString("lastMaterial", jsonObject.toString())).getAsJsonObject();
        if (jsonObjectOld.get("lastMaterialNumber1") != null) {
            MaterialData materialData = new MaterialData();
            materialData.materialNumber = jsonObjectOld.get("lastMaterialNumber1").getAsString();
            materialData.materialType = jsonObjectOld.get("lastMaterialType1").getAsString();
            materialData.year = jsonObjectOld.get("lastMaterialTime1").getAsString();
            materialData.season = jsonObjectOld.get("lastMaterialPeriod1").getAsString();
            lastList.add(materialData);
        }
        if (jsonObjectOld.get("lastMaterialNumber2") != null) {
            MaterialData materialData = new MaterialData();
            materialData.materialNumber = jsonObjectOld.get("lastMaterialNumber2").getAsString();
            materialData.materialType = jsonObjectOld.get("lastMaterialType2").getAsString();
            materialData.year = jsonObjectOld.get("lastMaterialTime2").getAsString();
            materialData.season = jsonObjectOld.get("lastMaterialPeriod2").getAsString();
            lastList.add(materialData);
        }
        if (jsonObjectOld.get("lastMaterialNumber3") != null) {
            MaterialData materialData = new MaterialData();
            materialData.materialNumber = jsonObjectOld.get("lastMaterialNumber3").getAsString();
            materialData.materialType = jsonObjectOld.get("lastMaterialType3").getAsString();
            materialData.year = jsonObjectOld.get("lastMaterialTime3").getAsString();
            materialData.season = jsonObjectOld.get("lastMaterialPeriod3").getAsString();
            lastList.add(materialData);
        }
        Timber.tag("kang").d(jsonObjectOld.toString());
        Collections.reverse(lastList);
        lastMaterialAdapter.notifyDataSetChanged();
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
