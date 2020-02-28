package com.example.sozluk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class Frag1 extends Fragment {
    private RecyclerView rv;
    private CardAdapter4 adapter;
    private EditText editText;
    private ImageButton imageButton;
    private FloatingActionButton fab;

    private SharedPreferences sp;
    private SharedPreferences.Editor e;
    private ArrayList<Word> words;
    private ArrayList<Word> wordArrayList;
    private WordData worddata;
    private Bundle bundle;
    private DbConnection db;




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
        db= new DbConnection(getActivity());
        dbCopy();

        sp= getActivity().getSharedPreferences("checked",Context.MODE_PRIVATE);
        e= sp.edit();



        words = new ArrayList<>() ;

        wordArrayList = new WordData().AllWords(db);

        adapter= new CardAdapter4(getActivity(),wordArrayList);
        rv.setAdapter(adapter);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.show();
                String ing=editText.getText().toString();
                for(int i=0;i<wordArrayList.size();i++){

                    if(ing.equals(wordArrayList.get(i).getName())){
                        words.add(wordArrayList.get(i));
                    }
                }
                adapter= new CardAdapter4(getActivity(),words);
                rv.setAdapter(adapter);


                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String id = sp.getString("str",null);
                        worddata= new WordData();
                        /*ArrayList<String> array= new ArrayList<>();
                        if(id.contains(",")) {
                            array = (ArrayList<String>) Arrays.asList(id.split(","));
                        }*/

                        ArrayList<String> array =new ArrayList<>(Arrays.asList(id.split(","))) ;
                        for(int i=0;i<array.size();i++){
                            String msg=worddata.InsertWordToList2(db,Integer.valueOf(array.get(i)));
                            Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
                        }
                        ArrayList<Word> wrd= new ArrayList<>();
                        wrd=worddata.AllWords2(db);
                        Log.e("mylist",Integer.toString(wrd.size()));

                        fab.hide();



                    }
                });

            }
        });



        return view;
    }
    public void dbCopy(){
        DatabaseCopyHelper helper = new DatabaseCopyHelper(getActivity())  ;
        try {
            helper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        helper.openDataBase();

    }


}
