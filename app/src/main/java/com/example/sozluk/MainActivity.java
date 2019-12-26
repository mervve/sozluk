package com.example.sozluk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottom_nav;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottom_nav= findViewById(R.id.bottom_nav) ;


        getSupportFragmentManager().beginTransaction().add(R.id.fragTutucu, new Frag1()).commit();


        bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                Intent intent;
                if (menuItem.getItemId()==R.id.manuel){

                        fragment= new Frag1();

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragTutucu,fragment).commit();
                }

                if (menuItem.getItemId()==R.id.photo){
                    intent = new Intent(MainActivity.this,fabclass.class);
                    startActivity(intent);

                }
                if (menuItem.getItemId()==R.id.training){

                    fragment= new Frag3();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragTutucu,fragment).commit();
                }


                return true;

            }
        });


    }


}
