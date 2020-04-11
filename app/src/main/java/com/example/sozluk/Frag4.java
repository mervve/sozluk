package com.example.sozluk;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class Frag4 extends Fragment {

    private EditText editText;
    private EditText editText2;
    private Button button;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference  databaseReference = FirebaseDatabase.getInstance().getReference("kelimeler");
    private Word2 word;
    private ArrayList<Word2> words= new ArrayList<Word2>();
    private int count;
    private Boolean flag = false;
    private String user;

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

                count=words.size();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_frag4, container,false);
        editText=view.findViewById(R.id.editText5);
        editText2=view.findViewById(R.id.editText4);
        button=view.findViewById(R.id.button);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        user= firebaseUser.getUid();




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                add();
            }
        });


        return view;


    }
    private void add(){

        String str1= String.valueOf(editText.getText());
        String str2= String.valueOf(editText2.getText());

        String key= databaseReference.push().getKey();


        if(words.isEmpty()){
            Word2 word1 = new Word2(key, str1, str2, user, false);
            //databaseReferenceCustomers.child(id).setValue(word1);
            databaseReference.child(key).setValue(word1);

            Toast.makeText(getContext(),"kelime eklendi!",Toast.LENGTH_SHORT).show();
        }
        else {
            for (int i = 0; i < words.size(); i++) {

                if (words.get(i).getName().equals(str1)  && words.get(i).getMean().equals(str2) ) {

                    flag = true;
                }
            }

            if (flag == false) {

                Word2 word1 = new Word2(key, str1, str2, user, false);
                databaseReference.child(key).setValue(word1);
            }
            else Toast.makeText(getContext(),"kelime zaten var",Toast.LENGTH_SHORT).show();

        }

    }
}
