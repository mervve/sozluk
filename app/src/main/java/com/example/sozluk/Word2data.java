package com.example.sozluk;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Word2data extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("kelimeler");
    private Word2 word;
    private ArrayList<Word2> words= new ArrayList<Word2>();
    private int count;
    private Boolean flag = false;
    private String user;







    public String InsertWordToList(String kelime, ArrayList<User_word> allWords, ArrayList<Word2> allWords2){
        DatabaseReference databaseReference3= FirebaseDatabase.getInstance().getReference("kullanici_kelime");
        Word2 yeni= new Word2();
        User_word yeni2= new User_word();

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser(); // authenticated user




        String msg;
        Boolean bool = ifExists(kelime, allWords);

        if(bool){
            msg= "kelime zaten var";
        }
        else {
            yeni = FindWord(kelime, allWords2);

            if(yeni!=null){

                String key= databaseReference3.push().getKey();


                yeni2.setId(firebaseUser.getUid());
                yeni2.setMean(yeni.getMean());
                yeni2.setName(yeni.getName());
                yeni2.setKey(key);

                databaseReference3.child(key).setValue(yeni2);
                databaseReference3.child(key).child("date").setValue(ServerValue.TIMESTAMP);
                Log.e("timestamp", String.valueOf(ServerValue.TIMESTAMP));
                databaseReference3.child(key).child("level").setValue(0);



                msg="kelime eklendi";
            }
            else msg="kelime databasede kayıtlı değil";



        }

        return msg;

    }
    public Word2 FindWord( String kelime,ArrayList<Word2> allWords ){

        Word2 w=null;


        ArrayList<Word2> array = allWords;

        for(int i=0;i<array.size();i++){

            if(kelime.equals(array.get(i).getKey())){
                w=array.get(i);

            }
        }
        if(w!=null){
            return w;}
        else return null;
    }

    public Boolean ifExists (String kelime,ArrayList<User_word> allWords) {

        User_word w=null;
        ArrayList<User_word> array= allWords;
        for(int i=0;i<array.size();i++){
            if (kelime.equals(array.get(i).getKey())){

                w=array.get(i);

            }
        }

        if(w!=null) return true;
        else return false;
    }

    public void updateDate(Long date, User_word user_word){
        DatabaseReference databaseReference2 ;
        //Log.e("key", user_word.getKey());
        databaseReference2 = FirebaseDatabase.getInstance().getReference("kullanici_kelime").child(user_word.getKey());
        databaseReference2.child("date").setValue(date);



        //databaseReference.child(user_word.getKey()).child("date").setValue(date);
    }

    public void updateDate2(User_word user_word){
        DatabaseReference databaseReference2 ;
        databaseReference2 = FirebaseDatabase.getInstance().getReference("kullanici_kelime").child(user_word.getKey());
        databaseReference2.child("date").setValue(ServerValue.TIMESTAMP);
        ;

    }

    public void updateLevel(User_word user_word){
        DatabaseReference databaseReference2 ;
        databaseReference2 = FirebaseDatabase.getInstance().getReference("kullanici_kelime").child(user_word.getKey());


        switch (user_word.getLevel())
        {
            case 0:
                //databaseReference.child(user_word.getKey()).child("level").setValue(1);
                databaseReference2.child("level").setValue(1);

                break;
            case 1:
                databaseReference2.child("level").setValue(2);

                break;
            case 2:
                databaseReference2.child("level").setValue(3);

                break;
            case 3:
                databaseReference2.child("level").setValue(4);

                break;
            case 4:
                databaseReference2.child("level").setValue(5);

                break;
            case 5:
                databaseReference2.child("level").setValue(6);

                break;
            case 6:
                databaseReference2.child("level").setValue(7);

                break;
            case 7:
                databaseReference2.child("level").setValue(8);

                break;
        }
    }

    public void updateLevel2(User_word user_word){
        DatabaseReference databaseReference2 ;
        databaseReference2 = FirebaseDatabase.getInstance().getReference("kullanici_kelime").child(user_word.getKey());
        databaseReference2.child("level").setValue(0);


    }
    public long calculateTime(User_word user_word){
        long time=0;


        switch (user_word.getLevel())
        {
            case 0:

                time=user_word.getDate()+4*60*60;
                break;
            case 1:
                time=user_word.getDate()+6*4*60*60;
                break;
            case 2:
                time=user_word.getDate()+6*6*4*60*60;
                break;
            case 3:
                time=user_word.getDate()+2*6*6*4*60*60;
                break;
            case 4:
                time=user_word.getDate()+2*2*6*6*4*60*60;
                break;
            case 5:
                time=user_word.getDate()+2*2*2*6*6*4*60*60;
                break;
            case 6:
                time=user_word.getDate()+4*2*2*6*6*4*60*60;
                break;
            case 7:
                time=user_word.getDate()+5*2*2*2*6*6*4*60*60;
                break;
        }


        return time;
    }



}
