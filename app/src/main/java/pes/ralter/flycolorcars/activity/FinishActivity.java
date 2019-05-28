package pes.ralter.flycolorcars.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import pes.ralter.flycolorcars.util.MyKeyword;
import pes.ralter.flycolorcars.R;
import pes.ralter.flycolorcars.util.TinhDiem;

public class FinishActivity extends AppCompatActivity {

    private String difficult;
    private long point;
    private TextView tvPoint;
    private TextView tvFinish;
    private Button btnReplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        initComps();
        initData();
        setData();
        if (difficult.equals(MyKeyword.DIFFICULT_HARD) && point < 25) {
            Toast.makeText(this, "Mức khó. Điểm < 25 là điểm kém", Toast.LENGTH_LONG).show();
        } else if (difficult.equals(MyKeyword.DIFFICULT_EASY) && point < 100) {
            Toast.makeText(this, "Mức dễ. Điểm < 100 là điểm kém", Toast.LENGTH_LONG).show();
        }

    }

    private void setData() {
        tvPoint.setText(point + "");
        tvFinish.setText(TinhDiem.getLevel(point, difficult));
        btnReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FinishActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void initData() {

        Intent intent = getIntent();
        point = intent.getLongExtra(MyKeyword.POINT, 0);
        difficult = intent.getStringExtra(MyKeyword.DIFFICULT);
    }

    private void initComps() {
        tvPoint = findViewById(R.id.tvPointFinish);
        tvFinish = findViewById(R.id.tvFinish);
        btnReplay = findViewById(R.id.btnReplay);
    }


}
