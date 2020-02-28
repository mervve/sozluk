package com.example.sozluk;

import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class DbConnection extends SQLiteOpenHelper implements Serializable {

    public DbConnection (@Nullable Context context) {

        super(context, "dictionary.db", null, 27);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS \"Words\" (\n" +
                "\t\"Id\"\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "\t\"Name\"\tTEXT,\n" +
                "\t\"Mean\"\tTEXT\n" +
                ");");
        /*db.execSQL("CREATE TABLE IF NOT EXISTS \"MyList\" (\n" +
                "\t\"Id\"\tINTEGER NOT NULL,\n" +
                "\t\"Name\"\tTEXT,\n" +
                "\t\"Mean\"\tTEXT,\n" +
                "\tFOREIGN KEY(\"Id\") REFERENCES \"Words\"(\"Id\"),\n" +
                "\tPRIMARY KEY(\"Id\")\n" +
                ");");*/
        db.execSQL("CREATE TABLE \"MyList\" (\n" +
                "\t\"Id\"\tINTEGER NOT NULL,\n" +
                "\t\"Name\"\tTEXT,\n" +
                "\t\"Mean\"\tTEXT,\n" +
                "\tFOREIGN KEY(\"Id\") REFERENCES \"Words\"(\"Id\")\n" +
                ");");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Words");
        db.execSQL("DROP TABLE IF EXISTS MyList");
        onCreate(db);
        //bunlari create e tasi
        db.execSQL("INSERT INTO Words (Name,Mean)\n" +
                "VALUES('a','b');");
        db.execSQL("INSERT INTO Words (Name,Mean)\n" +
                "VALUES('a','f');");
        db.execSQL("INSERT INTO Words (Name,Mean)\n" +
                "VALUES('c','d');");
        db.execSQL("INSERT INTO Words (Name,Mean)\n" +
                "VALUES('and','ve');");
        db.execSQL("INSERT INTO Words (Name,Mean)\n" +
                "VALUES('similar','benzer');");
    }
}
