package com.example.sozluk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signin extends AppCompatActivity {

    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private Button button;
    private String userName;
    private String userPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        editText1=findViewById(R.id.editText7);
        //editText2=findViewById(R.id.editText7);
        editText3=findViewById(R.id.editText6);
        button=findViewById(R.id.button3);

        mAuth = FirebaseAuth.getInstance();




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName = editText1.getText().toString();
                userPassword = editText3.getText().toString();
                if(userName.isEmpty() || userPassword.isEmpty()){

                    Toast.makeText(getApplicationContext(),"Lütfen gerekli alanları doldurunuz!",Toast.LENGTH_SHORT).show();

                }else{

                    registerFunc();
                }


            }
        });

    }
    private void registerFunc() {

        mAuth.createUserWithEmailAndPassword(userName,userPassword)
                .addOnCompleteListener(Signin.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent= new Intent(Signin.this,MainActivity.class);

                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }
}
