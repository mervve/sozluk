package com.example.sozluk;

import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;


public class Frag1_1 extends Fragment {
    private RecyclerView rv;
    private CardAdapter4_4 adapter;
    private EditText editText;
    private ImageButton imageButton;
    private FloatingActionButton fab;

    private SharedPreferences sp;
    private SharedPreferences.Editor e;
    private ArrayList<Word2> words2;
    private ArrayList<Word2> wordArrayList;
    private Word2data worddata;
    private Bundle bundle;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser= mAuth.getCurrentUser();

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("kelimeler");
    private DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("kullanici_kelime");
    private ArrayList<Word2> words= new ArrayList<Word2>();
    private ArrayList<User_word> user_words= new ArrayList<>();

    @Override
    public void onStart(){
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                words.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Word2 word= new Word2();
                    word.setMean(postSnapshot.child("mean").getValue(String.class)) ;
                    word.setName(postSnapshot.child("name").getValue(String.class));
                    word.setControlled(postSnapshot.child("controlled").getValue(Boolean.class));
                    word.setAdded_by(postSnapshot.child("added_by").getValue(String.class));
                    word.setKey(postSnapshot.child("key").getValue(String.class));
                    words.add(word);
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                user_words.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    User_word user_word= new User_word();
                    user_word.setMean(postSnapshot.child("mean").getValue(String.class)) ;
                    user_word.setName(postSnapshot.child("name").getValue(String.class));
                    user_word.setKey(postSnapshot.child("key").getValue(String.class));
                    user_word.setId(postSnapshot.child("id").getValue(String.class));
                    user_word.setDate(postSnapshot.child("date").getValue(Long.class));
                    user_word.setLevel(postSnapshot.child("level").getValue(Integer.class));

                    if(user_word.getId().equals(firebaseUser.getUid())){
                        user_words.add(user_word);
                    }

                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view =inflater.inflate(R.layout.fragment_frag1, container,false);
        rv= (RecyclerView)view.findViewById(R.id.rv);
        editText=view.findViewById(R.id.editText);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        imageButton=view.findViewById(R.id.imageButton);
        fab=view.findViewById(R.id.floatingActionButton4);
        fab.hide();


        sp= getActivity().getSharedPreferences("checked",Context.MODE_PRIVATE);
        e= sp.edit();



        words2 = new ArrayList<>() ;




        adapter= new CardAdapter4_4(getActivity(),words);
        rv.setAdapter(adapter);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.show();
                String ing=editText.getText().toString();
                for(int i=0;i<words.size();i++){

                    if(ing.equals(words.get(i).getName())){
                        words2.add(words.get(i));
                    }
                }
                adapter= new CardAdapter4_4(getActivity(),words2);
                rv.setAdapter(adapter);


                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String id = sp.getString("str",null);
                        worddata= new Word2data();


                        ArrayList<String> array =new ArrayList<>(Arrays.asList(id.split(","))) ;
                        for(int i=0;i<array.size();i++){

                            String msg=worddata.InsertWordToList(array.get(i),user_words,words);
                            Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();

                        }

                        ArrayList<Word2> wrd= new ArrayList<>();
                        wrd=words;


                        fab.hide();



                    }
                });

            }
        });



        return view;
    }




}
