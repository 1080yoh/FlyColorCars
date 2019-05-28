package pes.ralter.flycolorcars.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import pes.ralter.flycolorcars.util.MyKeyword;
import pes.ralter.flycolorcars.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnEasy;
    private Button btnHard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComps();
        addListener();
    }

    private void addListener() {
        btnEasy.setOnClickListener(this);
        btnHard.setOnClickListener(this);
    }


    private void initComps() {
        btnEasy = findViewById(R.id.btnEasy);
        btnHard = findViewById(R.id.btnHard);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnEasy:
                Intent intentEasy = new Intent(MainActivity.this, PlayingActivity.class);
                intentEasy.putExtra(MyKeyword.DIFFICULT, MyKeyword.DIFFICULT_EASY);
                startActivity(intentEasy);
                finish();
                break;
            case R.id.btnHard:
                Intent intentHard = new Intent(MainActivity.this, PlayingActivity.class);
                intentHard.putExtra(MyKeyword.DIFFICULT, MyKeyword.DIFFICULT_HARD);
                startActivity(intentHard);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
