package me.phil.dartsscore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;

public class APIPlayer {

    private static final String TAG = "DATABASE";
    public static boolean RESET= false;
    public DBPlayer database;

    public APIPlayer(Context context) {
        if(database==null)
            database = new DBPlayer(context);
    }


    public long createPlayer(String name){
        if(queryPlayer(name).size()>0) {
            Log.d(TAG, "createPlayer: COULD NOT CREATE Player\tName: "+name+" already exists");
            return -1;
        }
        SQLiteDatabase db=database.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(DBPlayer.PLAYER_COL_NAME,name);
        values.put(DBPlayer.PLAYER_COL_GAMES,0);
        values.put(DBPlayer.PLAYER_COL_WON,0);
        values.put(DBPlayer.PLAYER_COL_LEGS,0);
        values.put(DBPlayer.PLAYER_COL_LEGS_WON,0);
        values.put(DBPlayer.PLAYER_COL_AVG,0.0);
        values.put(DBPlayer.PLAYER_COL_DOUBLES,0.0);
        values.put(DBPlayer.PLAYER_COL_BEST_FINISH,0);
        values.put(DBPlayer.PLAYER_COL_HIGHEST_SCORE,0);
        long newRowId=db.insert(DBPlayer.PLAYER_TABLE,null,values);
        db.close();
        Log.d(TAG, "createPlayer: CREATED Player\tName: "+name);
        return newRowId;
    }
    public boolean resetAll(){
        SQLiteDatabase db=database.getWritableDatabase();
        database.onUpgrade(db,0,0);
        return true;
    }
    public ArrayList<Player> queryAllPlayers(){
        return queryPlayer("%");
    }
    public ArrayList<Player> queryPlayer(String name){
        SQLiteDatabase db=database.getReadableDatabase();
        String[] projection={
                BaseColumns._ID,
                DBPlayer.PLAYER_COL_NAME,
                DBPlayer.PLAYER_COL_GAMES,
                DBPlayer.PLAYER_COL_WON,
                DBPlayer.PLAYER_COL_LEGS,
                DBPlayer.PLAYER_COL_LEGS_WON,
                DBPlayer.PLAYER_COL_AVG,
                DBPlayer.PLAYER_COL_DOUBLES,
                DBPlayer.PLAYER_COL_BEST_FINISH,
                DBPlayer.PLAYER_COL_HIGHEST_SCORE
        };
        String selection= DBPlayer.PLAYER_COL_NAME+" LIKE ?";
        String[] args={name};
        Cursor c= db.query(DBPlayer.PLAYER_TABLE,projection,selection,args,null,null,null);
        ArrayList<Player> players=new ArrayList<>();
        while(c.moveToNext()){
            players.add(new Player(0,c.getString(c.getColumnIndexOrThrow(DBPlayer.PLAYER_COL_NAME)), 0, 0, 0,
                    c.getDouble(c.getColumnIndexOrThrow(DBPlayer.PLAYER_COL_AVG)),
                    c.getDouble(c.getColumnIndexOrThrow(DBPlayer.PLAYER_COL_DOUBLES)),
                    c.getInt(c.getColumnIndexOrThrow(DBPlayer.PLAYER_COL_BEST_FINISH)),
                    c.getInt(c.getColumnIndexOrThrow(DBPlayer.PLAYER_COL_GAMES)),
                    c.getInt(c.getColumnIndexOrThrow(DBPlayer.PLAYER_COL_WON)),
                    c.getInt(c.getColumnIndexOrThrow(DBPlayer.PLAYER_COL_HIGHEST_SCORE)),
                    c.getInt(c.getColumnIndexOrThrow(DBPlayer.PLAYER_COL_LEGS)),
                    c.getInt(c.getColumnIndexOrThrow(DBPlayer.PLAYER_COL_LEGS_WON))
            ));
        }
        c.close();
        db.close();

        return players;
    }
    public boolean updatePlayer(String name,double avg, double doubles, int games, int won,int bestFinish ){
        SQLiteDatabase db=database.getWritableDatabase();
        ContentValues args = new ContentValues();
        Player player=queryPlayer("name").get(0);
        args.put(DBPlayer.PLAYER_COL_GAMES, (player.matches +games));
        args.put(DBPlayer.PLAYER_COL_WON, (player.won+won));
        args.put(DBPlayer.PLAYER_COL_AVG, (player.avg+avg)/2.0);
        args.put(DBPlayer.PLAYER_COL_DOUBLES, (player.doubles+doubles)/2.0);
        if(player.bestFinish<bestFinish)
            args.put(DBPlayer.PLAYER_COL_BEST_FINISH, bestFinish);
        int rows=db.update(DBPlayer.PLAYER_TABLE,args,DBPlayer.PLAYER_COL_NAME+" = ?",new String[]{name});
        db.close();
        return rows==1;
    }
    public boolean updatePlayer(Player player){
        Player playerOld=queryPlayer(player.name).get(0);
        SQLiteDatabase db=database.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(DBPlayer.PLAYER_COL_GAMES, player.matches);
        args.put(DBPlayer.PLAYER_COL_WON, player.won);
        args.put(DBPlayer.PLAYER_COL_AVG, player.avg);
        args.put(DBPlayer.PLAYER_COL_DOUBLES, player.doubles);
        args.put(DBPlayer.PLAYER_COL_HIGHEST_SCORE,player.highestScore);
        args.put(DBPlayer.PLAYER_COL_LEGS,player.legsPlayed);
        args.put(DBPlayer.PLAYER_COL_LEGS_WON,player.legsWon);
        //if(playerOld.bestFinish<player.bestFinish)
        args.put(DBPlayer.PLAYER_COL_BEST_FINISH, player.bestFinish);
        int rows=db.update(DBPlayer.PLAYER_TABLE,args,DBPlayer.PLAYER_COL_NAME+" = ?",new String[]{player.name});
        db.close();
        return rows==1;
    }
}
