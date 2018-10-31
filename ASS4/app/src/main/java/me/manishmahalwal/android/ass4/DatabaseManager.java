package me.manishmahalwal.android.ass4;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Belal on 10/20/2017.
 * Reference: https://www.simplifiedcoding.net/sqliteopenhelper-tutorial/
 */

//The class is extending SQLiteOpenHelper
public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SensorDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME1 = "accelerometer";
    private static final String TABLE_NAME2 = "gyroscope";
    private static final String TABLE_NAME3 = "orientation";
    private static final String TABLE_NAME4 = "gps";
    private static final String TABLE_NAME5 = "proximity";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "data";
    private static final String COLUMN_TIME = "mtimestamp";

    DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql1 = "CREATE TABLE " + TABLE_NAME1 + " (\n" +
                "    " + COLUMN_ID + " INTEGER NOT NULL CONSTRAINT employees_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    " + COLUMN_NAME + " varchar(200) NOT NULL,\n" +
                "    " + COLUMN_TIME + " varchar(200) NOT NULL\n" + ");";

        String sql2 = "CREATE TABLE " + TABLE_NAME2 + " (\n" +
                "    " + COLUMN_ID + " INTEGER NOT NULL CONSTRAINT employees_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    " + COLUMN_NAME + " varchar(200) NOT NULL,\n" +
                "    " + COLUMN_TIME + " varchar(200) NOT NULL\n" + ");";

        String sql3 = "CREATE TABLE " + TABLE_NAME3 + " (\n" +
                "    " + COLUMN_ID + " INTEGER NOT NULL CONSTRAINT employees_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    " + COLUMN_NAME + " varchar(200) NOT NULL,\n" +
                "    " + COLUMN_TIME + " varchar(200) NOT NULL\n" + ");";

        String sql4 = "CREATE TABLE " + TABLE_NAME4 + " (\n" +
                "    " + COLUMN_ID + " INTEGER NOT NULL CONSTRAINT employees_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    " + COLUMN_NAME + " varchar(200) NOT NULL,\n" +
                "    " + COLUMN_TIME + " varchar(200) NOT NULL\n" + ");";

        String sql5 = "CREATE TABLE " + TABLE_NAME5 + " (\n" +
                "    " + COLUMN_ID + " INTEGER NOT NULL CONSTRAINT employees_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    " + COLUMN_NAME + " varchar(200) NOT NULL,\n" +
                "    " + COLUMN_TIME + " varchar(200) NOT NULL\n" + ");";

        sqLiteDatabase.execSQL(sql1);
        sqLiteDatabase.execSQL(sql2);
        sqLiteDatabase.execSQL(sql3);
        sqLiteDatabase.execSQL(sql4);
        sqLiteDatabase.execSQL(sql5);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String sql1 = "DROP TABLE IF EXISTS " + TABLE_NAME1 + ";";
        sqLiteDatabase.execSQL(sql1);
        onCreate(sqLiteDatabase);

        String sql2 = "DROP TABLE IF EXISTS " + TABLE_NAME1 + ";";
        sqLiteDatabase.execSQL(sql2);
        onCreate(sqLiteDatabase);

        String sql3 = "DROP TABLE IF EXISTS " + TABLE_NAME1 + ";";
        sqLiteDatabase.execSQL(sql3);
        onCreate(sqLiteDatabase);

        String sql4 = "DROP TABLE IF EXISTS " + TABLE_NAME1 + ";";
        sqLiteDatabase.execSQL(sql4);
        onCreate(sqLiteDatabase);

        String sql5 = "DROP TABLE IF EXISTS " + TABLE_NAME1 + ";";
        sqLiteDatabase.execSQL(sql5);
        onCreate(sqLiteDatabase);
    }

    boolean addSensorData(String data, String timestamp, String tableName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, data);
        contentValues.put(COLUMN_TIME, timestamp);
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(tableName, null, contentValues) != -1;
    }
}