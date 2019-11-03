package com.example.sozluk;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;



public class Frag1 extends Fragment {
    private RecyclerView rv;
    private CardAdapter adapter;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      View view =inflater.inflate(R.layout.fragment_frag1, container,false);
        rv= (RecyclerView)view.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        ArrayList<String> words = new ArrayList<>() ;
        words.add("x");
        words.add("x");
        words.add("x");
        words.add("x");
        words.add("x");
        words.add("x");
        words.add("x");
        words.add("x");
        words.add("x");
        words.add("x");
        words.add("x");
        adapter= new CardAdapter(getActivity(),words);
        rv.setAdapter(adapter);



        // Inflate the layout for this fragment
        return view;
    }


}
