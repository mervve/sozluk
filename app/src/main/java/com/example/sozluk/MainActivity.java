package com.example.sozluk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;



import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottom_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        bottom_nav= findViewById(R.id.bottom_nav);
        getSupportFragmentManager().beginTransaction().add(R.id.fragTutucu, new Frag1());

        bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()){
                    case R.id.manuel:
                        fragment= new Frag1();
                        return true;
                    case R.id.photo:
                        fragment= new Frag2();
                        return true;
                    case R.id.training:
                        fragment= new Frag3();
                        return true;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragTutucu,fragment);
                return true;

            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
