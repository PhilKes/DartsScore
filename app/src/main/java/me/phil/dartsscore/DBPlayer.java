package me.phil.dartsscore;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBPlayer extends SQLiteOpenHelper {

    public static final String DB_NAME="players.db";
    public static final int DB_VERSION=2;

    public DBPlayer(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }
    public static final String PLAYER_TABLE="player",
            PLAYER_COL_NAME="name",
            PLAYER_COL_GAMES="games",
            PLAYER_COL_WON="won",
            PLAYER_COL_AVG="average",
            PLAYER_COL_DOUBLES="doubles",
            PLAYER_COL_BEST_FINISH="bestFinish",
            PLAYER_COL_HIGHEST_SCORE="highestScore",
            PLAYER_COL_LEGS="legs",
            PLAYER_COL_LEGS_WON="legsWon";

    private static final String SQL_CREATE_TABLE_PLAYER ="CREATE TABLE "+PLAYER_TABLE +" ("
            + BaseColumns._ID+" INTEGER PRIMARY KEY,"
            +PLAYER_COL_NAME+" TEXT,"
            +PLAYER_COL_GAMES+" INTEGER,"
            +PLAYER_COL_LEGS+" INTEGER,"
            +PLAYER_COL_LEGS_WON+" INTEGER,"
            +PLAYER_COL_WON+" INTEGER,"
            +PLAYER_COL_AVG+" DOUBLE,"
            +PLAYER_COL_DOUBLES+" DOUBLE,"
            +PLAYER_COL_BEST_FINISH+" INTEGER,"
            +PLAYER_COL_HIGHEST_SCORE+" INTEGER);";

    private static final String SQL_DELETE_TABLE="DROP TABLE IF EXISTS "+PLAYER_TABLE;
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_DELETE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_PLAYER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int newVersion) {
        //onCreate(sqLiteDatabase);
        //db.execSQL("ALTER TABLE "+DBPlayer.PLAYER_TABLE+" ADD COLUMN "+DBPlayer.PLAYER_COL_LEGS_+" INTEGER DEFAULT 0");
        //db.execSQL("ALTER TABLE "+DBPlayer.PLAYER_TABLE+" ADD COLUMN "+DBPlayer.PLAYER_COL_LEGS_WON+" INTEGER DEFAULT 0");
    }
}
