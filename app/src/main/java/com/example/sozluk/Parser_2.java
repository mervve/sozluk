package com.example.sozluk;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class Parser_2 extends AppCompatActivity {
    private RecyclerView rv;
    private RelativeLayout rl;
    private FloatingActionButton fab;
    private RvAdapter_2 adapter3;
    private String item;
    private ArrayList<String> get;
    private Word2data wordData;

    private Intent intent;

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("kelimeler");
    private ArrayList<Word2> words= new ArrayList<Word2>();
    private DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("kullanici_kelime");

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
                    user_word.setLevel(postSnapshot.child("level").getValue(int.class));
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parser_design);


        ArrayList<String> array = (ArrayList<String>) getIntent().getSerializableExtra("array");

        fab=findViewById(R.id.floatingActionButton3);
        rv= findViewById(R.id.rv3);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rl=findViewById(R.id.rl3);
        array= Parsing(array);

        array=Control(array);


        adapter3= new RvAdapter_2(array);
        rv.setAdapter(adapter3);
        enableSwipeToDeleteAndUndo();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get = adapter3.getData();
                wordData= new Word2data();
                String msg;

                for(int i=0;i<get.size();i++){

                    msg=wordData.InsertWordToList(get.get(i),user_words,words);

                }

                intent = new Intent(Parser_2.this,MainActivity.class);
                startActivity(intent);

            }
        });
    }

    private ArrayList<String> Parsing(ArrayList<String> array){

        ArrayList<String> array2 = new ArrayList<String>();
        String[] str;
        //str=array.get(0).split("([ ]|[.]|[,])+");
        str=array.get(0).split("[\\s.,]+");

        for(int i=0; i<str.length;i++){

            array2.add(str[i]);
        }


        return array2;
    }
    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final int position = viewHolder.getAdapterPosition();
                //final String item = adapter3.getData().get(position);
                item = adapter3.getData().get(position);

                adapter3.removeItem(position);

                Snackbar snackbar = Snackbar
                        .make(rl, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        adapter3.restoreItem(item, position);
                        rv.scrollToPosition(position);
                    }
                });
                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(rv);
    }

    private ArrayList<String> Control(ArrayList<String> array){

        Set<String> set = new HashSet<String>(array);
        array.clear();
        array.addAll(set);


        return array;
    }



}
