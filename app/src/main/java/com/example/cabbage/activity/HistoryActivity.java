package com.example.cabbage.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.cabbage.R;
import com.example.cabbage.adapter.HistoryAdapter;
import com.example.cabbage.entity.HistoryEntity;
import com.example.cabbage.utils.ARouterPaths;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    private List<HistoryEntity> data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        leftOneButton.setImageResource(R.mipmap.ic_back);
        titleText.setText("采集历史");

        data = new ArrayList<>();
        HistoryEntity historyEntity;
        for (int i = 0; i < 20; i++) {
            historyEntity = new HistoryEntity();
            historyEntity.setID("test_" + i);
            data.add(historyEntity);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewHistory.setLayoutManager(linearLayoutManager);

        historyAdapter = new HistoryAdapter(R.layout.item_history, data);
        recyclerViewHistory.setAdapter(historyAdapter);

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
