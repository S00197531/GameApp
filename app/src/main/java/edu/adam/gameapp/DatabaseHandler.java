package edu.adam.gameapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper
{

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="S00189902-FinalProject";
    private static final String TABLE_HI_SCORES = "hi_scores";
    private static final String KEY_SCORE_ID ="score_id";
    private static final String KEY_PLAYER_NAME="player_name";
    private static final String KEY_GAME_DATE = "game_date";
    private static final String KEY_SCORE = "score";

    public DatabaseHandler(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_HISCORES_TABLE = " CREATE TABLE "+TABLE_HI_SCORES+"("+
                KEY_SCORE_ID+" INTEGER PRIMARY KEY,"+
                KEY_GAME_DATE+" TEXT NOT NULL,"+
                KEY_PLAYER_NAME+ " TEXT NOT NULL,"+
                KEY_SCORE+" INTEGER NOT NULL"+
                ")";
        db.execSQL(CREATE_HISCORES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_HI_SCORES);
        onCreate(db);
    }

    public void emptyHiScores()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_HI_SCORES);

        onCreate(db);
    }

    void addHiScore(HiScore hiScore)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_GAME_DATE,hiScore.getGdate());
        values.put(KEY_PLAYER_NAME,hiScore.getpName());
        values.put(KEY_SCORE,hiScore.getScore());

        db.insert(TABLE_HI_SCORES,null,values);
        db.close();
    }
    HiScore getHiScore(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =db.query(TABLE_HI_SCORES,new String[]{
                KEY_SCORE_ID,
                KEY_GAME_DATE,
                KEY_PLAYER_NAME,
                KEY_SCORE
        },KEY_SCORE_ID+"=?",
                new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor != null)
            cursor.moveToFirst();

        HiScore hiScore = new HiScore(Integer.parseInt(
           cursor.getString(0)),
           cursor.getString(1),
           cursor.getString(2),
                    cursor.getInt(3));

        return hiScore;
    }

    public List<HiScore> getAllHiScores()
    {
        List<HiScore> hiScoreList = new ArrayList<HiScore>();

        String selectQuery = "SELECT *FROM "+ TABLE_HI_SCORES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst())
        {
            do {
               HiScore hiScore = new HiScore();
               hiScore.setIdscore(Integer.parseInt(cursor.getString(0)));
               hiScore.setGdate(cursor.getString(1));
               hiScore.setpName(cursor.getString(2));
               hiScore.setScore(cursor.getInt(3));

               hiScoreList.add(hiScore);
            }while(cursor.moveToNext());
        }
        return hiScoreList;
    }
    public int updateHiScore(HiScore hiScore)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PLAYER_NAME,hiScore.getpName());
        values.put(KEY_GAME_DATE,hiScore.getGdate());
        values.put(KEY_SCORE,hiScore.getScore());

        return db.update(TABLE_HI_SCORES,values,KEY_SCORE_ID+" = ?",
                new String[]{String.valueOf(hiScore.getIdScore())});
    }

    public void deleteHiScore(HiScore hiScore)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HI_SCORES,KEY_SCORE_ID+" = ?",
                new String[]{String.valueOf(hiScore.getIdScore())});
        db.close();
    }
    public List<HiScore> getTopFiveScores()
    {
        List<HiScore> hiScoreList = new ArrayList<HiScore>();

        String selectQuery = "SELECT * FROM " + TABLE_HI_SCORES+
                " ORDER BY SCORE DESC "+
                " LIMIT 5";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst())
        {
            do{
                HiScore hiScore = new HiScore();
                hiScore.setIdscore(Integer.parseInt(cursor.getString(0)));
                hiScore.setGdate(cursor.getString(1));
                hiScore.setpName(cursor.getString(2));
                hiScore.setScore(cursor.getInt(3));

                hiScoreList.add(hiScore);
            }while (cursor.moveToNext());
        }
        return hiScoreList;
    }





}
