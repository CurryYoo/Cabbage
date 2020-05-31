package com.example.cabbage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cabbage.view.InfoItemBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SurveyActivity extends AppCompatActivity {

//    @BindView(R.id.left_one_button)
//    ImageView leftOneButton;
//    @BindView(R.id.left_one_layout)
//    LinearLayout leftOneLayout;
//    @BindView(R.id.left_two_button)
//    ImageView leftTwoButton;
//    @BindView(R.id.left_two_layout)
//    LinearLayout leftTwoLayout;
//    @BindView(R.id.title_text)
//    TextView titleText;
//    @BindView(R.id.right_two_button)
//    ImageView rightTwoButton;
//    @BindView(R.id.right_two_layout)
//    LinearLayout rightTwoLayout;
//    @BindView(R.id.right_one_button)
//    ImageView rightOneButton;
//    @BindView(R.id.right_one_layout)
//    LinearLayout rightOneLayout;
//    @BindView(R.id.title_bar)
//    LinearLayout titleBar;
    @BindView(R.id.commit_info)
    TextView commitInfo;
    @BindView(R.id.main_area)
    LinearLayout mainArea;

    private Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        ButterKnife.bind(this);

        initView();

    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.item_basicinfo, null);
        InfoItemBar itemBar = new InfoItemBar(context, getString(R.string.item_bar_basic));
        itemBar.addView(view);
        itemBar.setShow(true);
        mainArea.addView(itemBar);
    }
}
