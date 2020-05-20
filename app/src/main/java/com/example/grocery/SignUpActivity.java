package com.example.grocery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;




public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText Fname ,Lname,email,password,repassword,mobile_num;
    private Button SignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        Fname=findViewById(R.id.fname);
        Lname=findViewById(R.id.lname);
        email=findViewById(R.id.email_signUp);
        password=findViewById(R.id.pass_signUp);
        repassword=findViewById(R.id.repass_signUp);
        mobile_num=findViewById(R.id.mobile_signUp);
        SignUp=findViewById(R.id.sign_up_btn_act);
        SignUp.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_up_btn_act:
                    startActivity( new Intent(this,MainActivity.class));
                break;

        }
    }
}
