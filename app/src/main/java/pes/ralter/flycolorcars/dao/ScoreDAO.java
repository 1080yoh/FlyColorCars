package pes.ralter.flycolorcars.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import pes.ralter.flycolorcars.database.SQLiteFlyColorCar;
import pes.ralter.flycolorcars.model.Score;

public class ScoreDAO {
    private final String tableName = SQLiteFlyColorCar.TABLE_SCORE;
    private final String COLUMN_ID = SQLiteFlyColorCar.COL_ID;
    private final String COLUMN_SCORE = SQLiteFlyColorCar.COL_SCORE;
    private final String COLUMN_DIFFICULT = SQLiteFlyColorCar.COL_DIFFICULT;

    private Context mContext;
    SQLiteDatabase mDB;

    public ScoreDAO(Context mContext) {
        this.mContext = mContext;
        mDB = new SQLiteFlyColorCar(mContext).getWritableDatabase();
    }

    public void insertNewScore(Score score) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SCORE, score.getScore());
        contentValues.put(COLUMN_DIFFICULT, score.getDifficult());

        mDB.insert(tableName, null, contentValues);
    }

    public void deleteScore(Score score) {
        mDB.delete(tableName, "id = ?", new String[]{String.valueOf(score.getId())});
    }

    /**
     * Nếu không có gì thì size() == 0
     *
     * @return a list of Scores
     */
    public ArrayList<Score> readScores() {
        ArrayList<Score> lstScore = new ArrayList<>();

        String sql = "select * from " + tableName;

        Cursor cursor = mDB.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            int score = cursor.getInt(cursor.getColumnIndex(COLUMN_SCORE));
            String difficult = cursor.getString(cursor.getColumnIndex(COLUMN_DIFFICULT));
            lstScore.add(new Score(id, score, difficult));
        }
        return lstScore;
    }

    public long getHighestScore(String difficult) {
        String sql = "select * from " + tableName + " where " + COLUMN_DIFFICULT + " = " + difficult +
                " order by " + COLUMN_SCORE + " DESC";

        Cursor cursor = mDB.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            return cursor.getInt(cursor.getColumnIndex(COLUMN_SCORE));
        } else
            return 0;
    }
}
