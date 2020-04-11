package com.example.sozluk;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class Scroll extends AppCompatActivity {

    private Button button;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("alarm");
    private ArrayList<Alarm> alarms= new ArrayList<>();
    Alarm alarm = new Alarm();



    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
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



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll);

        button = findViewById(R.id.button8);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();//
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);//Güncel saati aldık
                int minute = mcurrentTime.get(Calendar.MINUTE);//Güncel dakikayı aldık
                TimePickerDialog timePicker; //Time Picker referansımızı oluşturduk

                timePicker = new TimePickerDialog(Scroll.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        setClock(selectedHour,selectedMinute);

                    }
                }, hour, minute, true);//true 24 saatli sistem için
                timePicker.setTitle("Saat Seçiniz");
                timePicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ayarla", timePicker);
                timePicker.setButton(DatePickerDialog.BUTTON_NEGATIVE, "İptal", timePicker);

                timePicker.show();
            }
        });
    }

    public  void setClock( int hour,  int minute){
        Boolean flag=false;

        for(int i=0;i<alarms.size();i++){
            if(alarms.get(i).getUser().equals(firebaseUser.getUid())){
                flag=true;
                databaseReference.child(alarms.get(i).getPushKey()).child("hour").setValue(hour);
                databaseReference.child(alarms.get(i).getPushKey()).child("min").setValue(minute);

            }
        }

    if(!flag){
    String pushkey= databaseReference.push().getKey();
    Log.e("push",pushkey);
    Alarm alarm = new Alarm(hour,minute,firebaseUser.getUid(),pushkey);
    databaseReference.child(pushkey).setValue(alarm);}


    }

}
