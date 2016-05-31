package edu.scu.cheryl.photonotes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper  extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;

    public static final String DATABASE_NAME = "Photo_Notes";
    public static final String COLUMN1 = "ID";
    public static final String COLUMN2 = "caption";
    public static final String COLUMN3= "filename";

    public DBHelper(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_NOTE = "CREATE TABLE " + DATABASE_NAME  + "("
                +COLUMN1 + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + COLUMN2 + " TEXT,"
                + COLUMN3 + " TEXT )";


        db.execSQL(CREATE_TABLE_NOTE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);

        // Create tables again
        onCreate(db);

    }

}
