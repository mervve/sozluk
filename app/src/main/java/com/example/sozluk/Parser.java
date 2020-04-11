package com.example.sozluk;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Parser extends AppCompatActivity {
    private RecyclerView rv;
    private RelativeLayout rl;
    private FloatingActionButton fab;
    private RvAdapter adapter3;
    private String item;
    private ArrayList<String> get;
    private WordData wordData;
    private DbConnection db;
    private Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parser_design);
        db= new DbConnection(this);
        dbCopy();

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
                get = adapter3.getData();
                wordData= new WordData();
                String msg;

                for(int i=0;i<get.size();i++){

                    msg=wordData.InsertWordToList(db,get.get(i));

                }
                db.close();
                intent = new Intent(Parser.this,MainActivity.class);
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

        Set <String> set = new HashSet<String>(array);
        array.clear();
        array.addAll(set);


        return array;
    }

    public void dbCopy(){
        DatabaseCopyHelper helper = new DatabaseCopyHelper(this)  ;
        try {
            helper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        helper.openDataBase();

    }

}
