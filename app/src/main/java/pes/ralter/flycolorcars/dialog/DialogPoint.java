package pes.ralter.flycolorcars.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import pes.ralter.flycolorcars.activity.FinishActivity;
import pes.ralter.flycolorcars.activity.MainActivity;
import pes.ralter.flycolorcars.activity.PlayingActivity;
import pes.ralter.flycolorcars.dao.ScoreDAO;
import pes.ralter.flycolorcars.model.Score;
import pes.ralter.flycolorcars.util.MyKeyword;
import pes.ralter.flycolorcars.R;

public class DialogPoint extends Dialog {

    private final String difficult;
    private TextView txtPopupPoint;
    private TextView txtPopupHighPoint;
    private TextView btnPopupExit;
    private TextView btnPopupNewGame;
    private PlayingActivity playingActivity;
    private TextView tvDiemCuaBan;
    private TextView tvDiemCaoTruoc;

    private long point;

    Bundle bundle;
    private long highestScore;

    public DialogPoint(@NonNull Context context, Bundle bundle) {
        super(context);
        this.playingActivity = (PlayingActivity) context;
        this.bundle = bundle;
        setContentView(R.layout.dialog_point);


        difficult = bundle.getString(MyKeyword.DIFFICULT, MyKeyword.DIFFICULT_EASY);
        this.point = bundle.getLong(MyKeyword.POINT, 0);
        highestScore = new ScoreDAO(playingActivity).getHighestScore(difficult);

        initComps();

        saveData();
        setText();
    }

    private void saveData() {
        new ScoreDAO(playingActivity).insertNewScore(new Score((int) point, difficult));
    }

    private void initComps() {
        txtPopupPoint = findViewById(R.id.popupPoint);
        txtPopupHighPoint = findViewById(R.id.popupHighPoint);
        btnPopupExit = findViewById(R.id.btnPopupExit);
        btnPopupNewGame = findViewById(R.id.btnPopupNewGame);
        tvDiemCaoTruoc = findViewById(R.id.tv_diemcaotruoc);
        tvDiemCuaBan = findViewById(R.id.tv_diemcuaban);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void setText() {

        txtPopupPoint.setText(point + "");
        txtPopupHighPoint.setText(highestScore + "");

        if (point > highestScore){
            tvDiemCuaBan.setText("Điểm cao mới");
            tvDiemCaoTruoc.setText("Điểm cao trước");
        }else{
            tvDiemCuaBan.setText("Điểm của bạn");
            tvDiemCaoTruoc.setText("Điểm cao");
        }

        btnPopupExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

                Intent intentFinishActivity = new Intent(getContext(), FinishActivity.class);
                intentFinishActivity.putExtra(MyKeyword.POINT, point);
                intentFinishActivity.putExtra(MyKeyword.DIFFICULT, difficult);

                playingActivity.startActivity(intentFinishActivity);
                playingActivity.finish();

            }
        });

        btnPopupNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMainMenu = new Intent(getContext(), MainActivity.class);
                playingActivity.startActivity(intentMainMenu);
                playingActivity.finish();
            }
        });
    }
}
