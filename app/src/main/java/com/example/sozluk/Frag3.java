package com.example.sozluk;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Frag3 extends Fragment {
    private RecyclerView rv;
    private CardAdapter2 adapter;
    private DbConnection db;
    private ArrayList<Word> wordArrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_frag3, container,false);
        db= new DbConnection(getActivity());
        dbCopy();
        rv= (RecyclerView)view.findViewById(R.id.rv2);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));


        wordArrayList = new WordData().AllWords2(db);

        adapter= new CardAdapter2(getActivity(),wordArrayList);
        rv.setAdapter(adapter);



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
