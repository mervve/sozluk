package com.example.sozluk;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class WordData {

     private MainActivity activity;

    public ArrayList<Word> AllWords(DbConnection data){

        ArrayList<Word> WordArraylist = new ArrayList<>();

        SQLiteDatabase db= data.getWritableDatabase();


        Cursor c = db.rawQuery("SELECT * FROM Words",null);


        while (c.moveToNext()){
            Word w = new Word(c.getInt(c.getColumnIndex("Id"))
                    ,c.getString(c.getColumnIndex("Name"))
                    ,c.getString(c.getColumnIndex("Mean")));
            WordArraylist.add(w);
        }

        c.close();
        db.close();

        return WordArraylist;
    }
    public ArrayList<Word> AllWords2(DbConnection data){

        ArrayList<Word> WordArraylist = new ArrayList<>();

        SQLiteDatabase db= data.getWritableDatabase();


        Cursor c = db.rawQuery("SELECT * FROM MyList",null);


        while (c.moveToNext()){
            Word w = new Word(c.getInt(c.getColumnIndex("Id"))
                    ,c.getString(c.getColumnIndex("Name"))
                    ,c.getString(c.getColumnIndex("Mean")));
            WordArraylist.add(w);
        }
        c.close();
        db.close();

        return WordArraylist;
    }
    public ArrayList<Word> AllWords3(DbConnection data, SQLiteDatabase db){

        ArrayList<Word> WordArraylist = new ArrayList<>();


        Cursor c = db.rawQuery("SELECT * FROM Words",null);


        while (c.moveToNext()){
            Word w = new Word(c.getInt(c.getColumnIndex("Id"))
                    ,c.getString(c.getColumnIndex("Name"))
                    ,c.getString(c.getColumnIndex("Mean")));
            WordArraylist.add(w);
        }

        c.close();


        return WordArraylist;
    }
    public ArrayList<Word> AllWords4(DbConnection data, SQLiteDatabase db){

        ArrayList<Word> WordArraylist = new ArrayList<>();


        Cursor c = db.rawQuery("SELECT * FROM MyList",null);


        while (c.moveToNext()){
            Word w = new Word(c.getInt(c.getColumnIndex("Id"))
                    ,c.getString(c.getColumnIndex("Name"))
                    ,c.getString(c.getColumnIndex("Mean")));
            WordArraylist.add(w);
        }
        c.close();


        return WordArraylist;
    }
    public String InsertWordToList(DbConnection data,String kelime){


        Word yeni=null;
        SQLiteDatabase db= data.getWritableDatabase();

        ContentValues values= new ContentValues();

        String msg;
        Boolean bool = ifExists(data,kelime,db);
        if(bool){
            msg= "kelime zaten var";
        }
        else {
            yeni = FindWord(data,kelime, db);

            if(yeni!=null){
                Log.e("yeni id",Integer.toString(yeni.getId()));
                values.put("Id", yeni.getId());
                values.put("Name", yeni.getName());
                values.put("Mean", yeni.getMean());


                db.insertOrThrow("MyList", null, values);
                msg="kelime eklendi";
            }
            else msg="kelime databasede kayırlı değil";



        }
        db.close();

        return msg;

    }


    public Word FindWord( DbConnection data,String kelime, SQLiteDatabase db){

        Word w=null;


        ArrayList<Word> array = AllWords3(data,db);

        for(int i=0;i<array.size();i++){

            if(kelime.equals(array.get(i).getName())){
                w=array.get(i);

            }
        }
            if(w!=null){
            return w;}
            else return null;
        }


    public Boolean ifExists (DbConnection data, String kelime, SQLiteDatabase db) {

        Word w=null;
        ArrayList<Word> array= AllWords4(data,db);
        for(int i=0;i<array.size();i++){
            if (kelime.equals(array.get(i).getName())){
                w=array.get(i);

            }
        }

        if(w!=null) return true;
        else return false;
    }

    public String InsertWordToList2(DbConnection data,int id) {


        Word yeni= new Word();
        SQLiteDatabase db= data.getWritableDatabase();


        ContentValues values= new ContentValues();

        String msg;
        Boolean bool = ifExists2(id,db);
        if(bool){
            msg= "kelime zaten var";
        }
        else {
            yeni = FindWord2(id, db);

            if(yeni!=null){
                values.put("Id", yeni.getId());
                values.put("Name", yeni.getName());
                values.put("Mean", yeni.getMean());


                db.insertOrThrow("MyList", null, values);
                msg="kelime eklendi";
            }
            else msg="kelime databasede kayıtlı değil";



        }
        db.close();
        return msg;
    }
    public Word FindWord2( int id,SQLiteDatabase db) {

        Word w= new Word();

        Cursor c = db.rawQuery("SELECT * FROM Words WHERE Words.Id==id",null);


        if(c!=null){

        while (c.moveToNext()){
            w = new Word(c.getInt(c.getColumnIndex("Id"))
                    ,c.getString(c.getColumnIndex("Name"))
                    ,c.getString(c.getColumnIndex("Mean")));
        }
        c.close();
        return w;
        }
        else return null;
    }

    public Boolean ifExists2 ( int id,SQLiteDatabase db) {

        Word w= new Word();
        Cursor c = db.rawQuery("SELECT * FROM Mylist WHERE MyList.Id==id",null);


        while (c.moveToNext()){
            w = new Word(c.getInt(c.getColumnIndex("Id"))
                    ,c.getString(c.getColumnIndex("Name"))
                    ,c.getString(c.getColumnIndex("Mean")));

        }
        c.close();

        if(w!=null) return true;
        else return false;
    }


}
