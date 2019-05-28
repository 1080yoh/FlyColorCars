package pes.ralter.flycolorcars.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class SQLiteFlyColorCar extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "flycolorcar_database.sqlite";
    public static final String TABLE_SCORE = "Score";
    public static final String COL_ID = "id";
    public static final String COL_SCORE = "score";
    public static final String COL_DIFFICULT = "difficult";


    public SQLiteFlyColorCar(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Score (\n" +
                "    id        INTEGER     PRIMARY KEY AUTOINCREMENT,\n" +
                "    score     INT,\n" +
                "    difficult VARCHAR (5) \n" +
                ");\n";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
