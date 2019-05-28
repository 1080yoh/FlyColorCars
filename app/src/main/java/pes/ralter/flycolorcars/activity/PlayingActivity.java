package pes.ralter.flycolorcars.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import pes.ralter.flycolorcars.util.MyKeyword;
import pes.ralter.flycolorcars.R;
import pes.ralter.flycolorcars.dialog.DialogPoint;
import pes.ralter.flycolorcars.listener.CarTouchListener;
import pes.ralter.flycolorcars.model.Car;

public class PlayingActivity extends AppCompatActivity {

    private RelativeLayout state_0;
    private RelativeLayout state_1;
    private RelativeLayout state_2;
    private RelativeLayout state_3;
    private RelativeLayout state_4;
    private ImageView imgCar_0;
    private ImageView imgCar_1;
    private ImageView imgCar_2;
    private ImageView imgCar_3;
    private ImageView imgCar_4;
    private TextView tvTimeCount;
    private TextView tvPoint;

    private ImageView hourGlass;
    private Animation animRotate;

    private ArrayList<Car> lstCars;
    private ArrayList<Car> lstFullCars;

    private final int LIMIT_TIME_EASY = 15;
    private final int LIMIT_TIME_HARD = 5;

    private int limit_time;

    private Car currentCar;
    private int idLayoutHaveResult;

    private CountDownTimer countDownTimer;
    private long timeWhenFinish;
    private long currentPoint;

    private String difficult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);

        Intent intent = getIntent();
        difficult = intent.getStringExtra(MyKeyword.DIFFICULT);


        initComps();
        addListener();
        initDataCars();

        startNextQuest();

    }

    private void startNextQuest() {
        if (lstCars.size() == 5)
            noQuestionsElse();
        else {
            // lấy câu hỏi & đáp án
            Collections.shuffle(lstCars);
            currentCar = lstCars.get(0);
            lstCars.remove(currentCar);
            int idResultCar = currentCar.getIdFlyColored();
            int idQuestionCar = currentCar.getIdColored();

            imgCar_0.setImageResource(idQuestionCar);
            imgCar_0.setVisibility(View.VISIBLE);

            // tạo câu trả lời sai
            lstFullCars.remove(currentCar);
            Collections.shuffle(lstFullCars);
            ArrayList<Integer> lstQuestion = new ArrayList<>();
            lstQuestion.add(idResultCar);
            lstQuestion.add(lstFullCars.get(0).getIdFlyColored());
            lstQuestion.add(lstFullCars.get(1).getIdFlyColored());
            lstQuestion.add(lstFullCars.get(2).getIdFlyColored());
            lstFullCars.add(currentCar);

            Collections.shuffle(lstQuestion);
            imgCar_1.setImageResource(lstQuestion.get(0));
            imgCar_2.setImageResource(lstQuestion.get(1));
            imgCar_3.setImageResource(lstQuestion.get(2));
            imgCar_4.setImageResource(lstQuestion.get(3));

            // Lấy id layout có đáp án
            if (lstQuestion.get(0) == idResultCar) {
                idLayoutHaveResult = state_1.getId();
            } else if (lstQuestion.get(1) == idResultCar) {
                idLayoutHaveResult = state_2.getId();
            } else if (lstQuestion.get(2) == idResultCar) {
                idLayoutHaveResult = state_3.getId();
            } else if (lstQuestion.get(3) == idResultCar) {
                idLayoutHaveResult = state_4.getId();
            }

            currentPoint += timeWhenFinish;
            Log.w("point", "setted");
            tvPoint.setText(currentPoint + "");
            countDownTimer.start();
        }
    }

    private void chooseRightAnswer() {
        Toast.makeText(PlayingActivity.this, "Gê quá!", Toast.LENGTH_SHORT).show();
        countDownTimer.cancel();

        startNextQuest();
    }

    private void chooseWrongAnswer() {
        Toast.makeText(PlayingActivity.this, "Oh! Chọn sai rồi!", Toast.LENGTH_SHORT).show();
    }

    private void timeOut() {
        openDialogPoint();
    }

    private void noQuestionsElse() {
        openDialogPoint();
    }

    private void openDialogPoint() {
        Bundle bundle = new Bundle();
        bundle.putLong(MyKeyword.POINT, currentPoint);
        bundle.putString(MyKeyword.DIFFICULT, getDifficult());

        DialogPoint dialogPoint = new DialogPoint(this, bundle);
        dialogPoint.show();
    }

    class StateDragListener implements View.OnDragListener {

        Drawable previousBackground;

        @Override
        public boolean onDrag(View v, DragEvent event) {

            ImageView imgCar = (ImageView) event.getLocalState();

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_ENTERED:
                    previousBackground = v.getBackground();
                    v.setBackgroundResource(R.drawable.enter_shape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackground(previousBackground);
                    break;
                case DragEvent.ACTION_DROP:
                    RelativeLayout layoutDrop = (RelativeLayout) v;
                    layoutDrop.setBackground(previousBackground);
                    if (v.getId() == idLayoutHaveResult) {
                        ((ImageView) layoutDrop.getChildAt(0)).setImageResource(currentCar.getIdColored());
                        chooseRightAnswer();
                    } else {
                        imgCar.setVisibility(View.VISIBLE);
                        chooseWrongAnswer();
                    }
            }
            return true;
        }
    }

    private void initComps() {
        state_0 = findViewById(R.id.state_0);
        state_1 = findViewById(R.id.state_1);
        state_2 = findViewById(R.id.state_2);
        state_3 = findViewById(R.id.state_3);
        state_4 = findViewById(R.id.state_4);

        imgCar_0 = findViewById(R.id.imgCar_0);
        imgCar_1 = findViewById(R.id.imgCar_1);
        imgCar_2 = findViewById(R.id.imgCar_2);
        imgCar_3 = findViewById(R.id.imgCar_3);
        imgCar_4 = findViewById(R.id.imgCar_4);

        tvTimeCount = findViewById(R.id.tvTimeCount);
        tvPoint = findViewById(R.id.tvPoint);

        hourGlass = findViewById(R.id.hourGlass);
        animRotate = AnimationUtils.loadAnimation(this, R.anim.rotate_hour_glass);
        //hourGlass.startAnimation(animRotate);

        if (difficult.equals(MyKeyword.DIFFICULT_EASY))
            limit_time = LIMIT_TIME_EASY;
        else
            limit_time = LIMIT_TIME_HARD;

        currentPoint = 0;
        timeWhenFinish = 0;

        countDownTimer = new CountDownTimer(limit_time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTimeCount.setText((millisUntilFinished / 1000) + "");
                timeWhenFinish = millisUntilFinished / 1000;
            }

            @Override
            public void onFinish() {
                tvTimeCount.setText(0 + "");
                Toast.makeText(getApplicationContext(), "Oh! Time out!", Toast.LENGTH_SHORT).show();
                timeOut();
            }
        };
    }

    private void initDataCars() {
        lstFullCars = new ArrayList<>();
        lstFullCars.add(new Car(R.drawable.car_0, R.drawable.car_0_0));
        lstFullCars.add(new Car(R.drawable.car_1, R.drawable.car_1_0));
        lstFullCars.add(new Car(R.drawable.car_2, R.drawable.car_2_0));
        lstFullCars.add(new Car(R.drawable.car_3, R.drawable.car_3_0));
        lstFullCars.add(new Car(R.drawable.car_4, R.drawable.car_4_0));
        lstFullCars.add(new Car(R.drawable.car_5, R.drawable.car_5_0));
        lstFullCars.add(new Car(R.drawable.car_6, R.drawable.car_6_0));
        lstFullCars.add(new Car(R.drawable.car_7, R.drawable.car_7_0));
        lstFullCars.add(new Car(R.drawable.car_8, R.drawable.car_8_0));
        lstFullCars.add(new Car(R.drawable.car_9, R.drawable.car_9_0));
        lstFullCars.add(new Car(R.drawable.car_10, R.drawable.car_10_0));
        lstFullCars.add(new Car(R.drawable.car_11, R.drawable.car_11_0));
        lstFullCars.add(new Car(R.drawable.car_12, R.drawable.car_12_0));
        lstFullCars.add(new Car(R.drawable.car_13, R.drawable.car_13_0));
        lstFullCars.add(new Car(R.drawable.car_14, R.drawable.car_14_0));

        lstCars = new ArrayList<>(lstFullCars);
    }

    private void addListener() {
        state_0.setOnDragListener(new StateDragListener());
        state_1.setOnDragListener(new StateDragListener());
        state_2.setOnDragListener(new StateDragListener());
        state_3.setOnDragListener(new StateDragListener());
        state_4.setOnDragListener(new StateDragListener());

        imgCar_0.setOnTouchListener(new CarTouchListener());
    }

    private String getDifficult(){
        return difficult;
    }

}
