package com.example.cabbage.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.cabbage.R;
import com.example.cabbage.adapter.HistoryAdapter;
import com.example.cabbage.network.HistoryInfo;
import com.example.cabbage.network.HttpRequest;
import com.example.cabbage.utils.ARouterPaths;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.cabbage.utils.StaticVariable.STATUS_READ;


@Route(path = ARouterPaths.HISTORY_ACTIVITY)
public class HistoryActivity extends AppCompatActivity implements OnClickListener {

    @BindView(R.id.left_one_button)
    ImageView leftOneButton;
    @BindView(R.id.left_one_layout)
    LinearLayout leftOneLayout;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.recyclerView_history)
    RecyclerView recyclerViewHistory;

    private HistoryAdapter historyAdapter;
    private List<HistoryInfo.data> data;

    private String token;
    private Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);

        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        token = sp.getString("token", "");

        initData();
        initView();
    }

    private void initData() {

        HttpRequest.getHistorySurveyData(token, new HttpRequest.IHistoryCallback() {
            @Override
            public void onResponse(HistoryInfo historyInfo) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerViewHistory.setLayoutManager(linearLayoutManager);

                historyAdapter = new HistoryAdapter(R.layout.item_history, historyInfo.data.list);
                historyAdapter.setOnItemClickListener((adapter, view, position) -> ARouter.getInstance()
                        .build(ARouterPaths.SURVEY_ACTIVITY2)
                        .withString("surveyId", historyInfo.data.list.get(position).getObservationId())
                        .withString("materialId", historyInfo.data.list.get(position).getMaterialNumber())
                        .withString("materialType", historyInfo.data.list.get(position).getMaterialType())
                        .withString("plantId", historyInfo.data.list.get(position).getPlantNumber())
                        .withString("investigatingTime", historyInfo.data.list.get(position).getInvestigatingTime())
                        .withString("surveyPeriod", historyInfo.data.list.get(position).getObsPeriod())
                        .withInt("status", STATUS_READ)
                        .navigation());
                historyAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    Intent dataIntent = new Intent();
                    // 创建普通字符型ClipData
                    dataIntent.putExtra("surveyId", historyInfo.data.list.get(position).getObservationId());
                    dataIntent.putExtra("surveyPeriod", historyInfo.data.list.get(position).getObsPeriod());
                    dataIntent.putExtra("materialId", historyInfo.data.list.get(position).getMaterialNumber());
                    dataIntent.putExtra("materialType", historyInfo.data.list.get(position).getMaterialType());
                    ClipData mClipData1 = ClipData.newIntent("copyData", dataIntent);
                    // 将ClipData内容放到系统剪贴板里
                    cm.setPrimaryClip(mClipData1);
                    Toast.makeText(getApplicationContext(), R.string.copy_data_success, Toast.LENGTH_SHORT).show();
                });
                recyclerViewHistory.setAdapter(historyAdapter);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void initView() {
        leftOneButton.setImageResource(R.mipmap.ic_back);
        titleText.setText("采集历史");

        leftOneLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_one_layout:
                finish();
                break;
        }
    }
}
