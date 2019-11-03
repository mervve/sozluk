package com.example.sozluk;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import java.util.ArrayList;
import java.util.List;


public class Frag3 extends Fragment {
    private RecyclerView rv;
    private CardAdapter2 adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_frag3, container,false);
        rv= (RecyclerView)view.findViewById(R.id.rv2);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        ArrayList<String> words = new ArrayList<>() ;
        words.add("a");
        words.add("b");
        words.add("c");
        words.add("d");
        words.add("x");
        words.add("y");


        ArrayList<String> means = new ArrayList<>() ;
        means.add("mean1");
        means.add("mean2");
        means.add("mean3");
        means.add("mean4");
        means.add("mean5");
        means.add("mean6");

        adapter= new CardAdapter2(getActivity(),words,means);
        rv.setAdapter(adapter);



        return view;
    }


}
