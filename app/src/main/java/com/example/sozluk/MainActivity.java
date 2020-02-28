package com.example.sozluk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;



import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;



public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottom_nav;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottom_nav= findViewById(R.id.bottom_nav) ;
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Sozluk");

        setSupportActionBar(toolbar);



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
                if (menuItem.getItemId()==R.id.add){

                    fragment= new Frag4();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragTutucu,fragment).commit();
                }

                return true;

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;

        if(item.getItemId()==R.id.profile){
            intent = new Intent(MainActivity.this,Profile.class);
            startActivity(intent);
        }
        if(item.getItemId()==R.id.logout){
            intent = new Intent(MainActivity.this,Logout.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
