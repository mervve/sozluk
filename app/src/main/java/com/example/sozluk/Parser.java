package com.example.sozluk;


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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Parser extends AppCompatActivity {
    private RecyclerView rv;
    private RelativeLayout rl;
    private FloatingActionButton fab;
    //private CardAdapter3 adapter3;
    private RvAdapter adapter3;
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

        adapter3= new RvAdapter(array);
        rv.setAdapter(adapter3);
        enableSwipeToDeleteAndUndo();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //database'e kayıt işlemi

                //sonra mainactiivity e dönüş
            }
        });
    }

    private ArrayList<String> Parsing(ArrayList<String> array){

        ArrayList<String> array2 = new ArrayList<String>();
        String[] str;
        str=array.get(0).split("([ ]|[.]|[,])+");
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
                final String item = adapter3.getData().get(position);

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

      /*  for(int i=0;i<array.size();i++){

            for (int y=i+1;y<array.size();y++){

                if (array.get(i).equals(array.get(y))){
                    array.remove(i);
                    i--;
                }


            }
            }*/
        Set <String> set = new HashSet<String>(array);
        array.clear();
        array.addAll(set);







        return array;
    }


}
