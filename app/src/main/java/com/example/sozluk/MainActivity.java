package com.example.sozluk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottom_nav;
    private Toolbar toolbar;
    private ArrayList<User_word> user_words= new ArrayList<>();
    private DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("kullanici_kelime");
    private DatabaseReference databaseReference3 = FirebaseDatabase.getInstance().getReference("alarm");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser= mAuth.getCurrentUser();
    Alarm alarm = new Alarm();
    private ArrayList<Alarm> alarms= new ArrayList<>();


    public void onStart() {
        super.onStart();

        /*databaseReference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    user_words.clear();

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                        User_word user_word= new User_word();
                        user_word.setMean(postSnapshot.child("mean").getValue(String.class)) ;
                        user_word.setName(postSnapshot.child("name").getValue(String.class));
                        user_word.setKey(postSnapshot.child("key").getValue(String.class));
                        user_word.setKey(postSnapshot.child("id").getValue(String.class));
                        user_word.setDate(postSnapshot.child("date").getValue(Long.class));
                        user_word.setLevel(postSnapshot.child("level").getValue(int.class));
                        user_words.add(user_word);
                    }



                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                alarms.clear();


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    alarm.setHour(postSnapshot.child("hour").getValue(int.class));
                    alarm.setMin(postSnapshot.child("min").getValue(int.class));
                    alarm.setPushKey(postSnapshot.child("pushKey").getValue(String.class));
                    alarm.setUser(postSnapshot.child("user").getValue(String.class));
                    alarms.add(alarm);
                }
                Log.e("alarm size", String.valueOf(alarms.size()));
                for(int i=0;i<alarms.size();i++){
                    if(alarms.get(i).getUser().equals(firebaseUser.getUid())){
                        Log.e("bildirim","calisiyor");
                        Bildirim(alarms.get(i).getHour(),alarms.get(i).getMin());
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottom_nav= findViewById(R.id.bottom_nav) ;
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Sozluk");




        setSupportActionBar(toolbar);

        Fragment frag=null;
        if(internetKontrol()){ //internet kontrol methodu çağırılıyor
            frag= new Frag1_1();

            getSupportFragmentManager().beginTransaction().replace(R.id.fragTutucu,frag).commit();
        }else{
            frag= new Frag1();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragTutucu,frag).commit();
        }




        bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                Intent intent;

                if (menuItem.getItemId()==R.id.manuel){

                    if(internetKontrol()){ //internet kontrol methodu çağırılıyor
                        fragment= new Frag1_1();

                        getSupportFragmentManager().beginTransaction().replace(R.id.fragTutucu,fragment).commit();
                    }else{
                        fragment= new Frag1();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragTutucu,fragment).commit();
                    }


                }

                if (menuItem.getItemId()==R.id.photo){
                    if(internetKontrol()){ //internet kontrol methodu çağırılıyor
                        intent = new Intent(MainActivity.this,fabclass.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), "İnternet Yok!", Toast.LENGTH_LONG).show();
                    }



                }
                if (menuItem.getItemId()==R.id.training){

                    if(internetKontrol()){ //internet kontrol methodu çağırılıyor
                        fragment= new Frag3_3_3();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragTutucu,fragment).commit();
                    }else{
                        Toast.makeText(getApplicationContext(), "İnternet Yok!", Toast.LENGTH_LONG).show();
                    }
                }
                if (menuItem.getItemId()==R.id.add){

                    if(internetKontrol()){ //internet kontrol methodu çağırılıyor
                        fragment= new Frag4();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragTutucu,fragment).commit();
                    }else{
                        Toast.makeText(getApplicationContext(), "İnternet Yok!", Toast.LENGTH_LONG).show();
                    }


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

    protected boolean internetKontrol() { //interneti kontrol eden method
        // TODO Auto-generated method stub
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public void Bildirim(int hour,int min){



        Calendar calendar = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 00);



        Intent broadcastIntent =
                new Intent(MainActivity.this,MyReceiver.class);



        PendingIntent gidilecekBroadcast = PendingIntent.getBroadcast(this
                ,0
                ,broadcastIntent
                ,PendingIntent.FLAG_UPDATE_CURRENT);


        AlarmManager alarmManager =
                (AlarmManager) getSystemService(Context.ALARM_SERVICE);


        alarmManager.set(AlarmManager.RTC,calendar.getTimeInMillis(),gidilecekBroadcast);


    }
}
