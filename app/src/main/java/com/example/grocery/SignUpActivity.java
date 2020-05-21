package com.example.grocery;

import android.app.ProgressDialog;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText Fname ,Lname,email,password,repassword,mobile_num;
    private Button SignUp;
    private FirebaseDatabase database ;
    private ProgressDialog loadingBar;
    final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        loadingBar=new ProgressDialog(this);
        database = FirebaseDatabase.getInstance();
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
                CreateAccont();

                break;

        }
    }

    private void CreateAccont() {
        String Fname_Str = Fname.getText().toString();
        String Lname_Str = Lname.getText().toString();
        String email_Str = email.getText().toString();
        String password_Str = password.getText().toString();
        String repassword_Str = repassword.getText().toString();
        String mobile_Str = mobile_num.getText().toString();
        boolean validation=true;

        if (password_Str.length() < 8) {
            password.setError(" Minimum length of Password is should be 8 ");
            password.requestFocus();
            validation=false;
        }
        else if (email_Str.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            validation=false;
        }
        else  if (password_Str.isEmpty() || repassword_Str.isEmpty()) {
            password.setError("Password is required");
            repassword.setError("Password is required");
            password.requestFocus();
            validation=false;
        }
        else if (!password_Str.equals(repassword_Str)) {
            repassword.setError("Password doesn't match");
            password.setError("Password doesn't match");
            repassword.requestFocus();
            validation=false;
        }
        else if (Lname_Str.isEmpty()) {
            Lname.setError("Lname is required");
            Lname.requestFocus();
            validation=false;
        }
        else if (Fname_Str.isEmpty()) {
            Fname.setError("Fname is required");
            Fname.requestFocus();
            validation=false;
        }
        else  if (mobile_Str.isEmpty()) {
            mobile_num.setError("mobile is required");
            mobile_num.requestFocus();
            validation=false;
        }
        else    if (!Patterns.EMAIL_ADDRESS.matcher(email_Str).matches()) {
            email.setError(" please Enter a valid email");
            email.requestFocus();
            validation=false;
             }

        else {
            Log.e("hello","hello");
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            validationEmail(Fname_Str,Lname_Str,email_Str,password_Str,mobile_Str);
        }





    }

    private void validationEmail(final String fname_str, final String lname_str, final String email_str, final String password_str, final String mobile_str) {

        final String email= (email_str.replace("@","-")).replace(".","_");
        Log.e("validationEmail","hello");
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.child("Users").child(email).exists()){
                    HashMap<String,Object> userDataMap =new HashMap<>();
                    userDataMap.put("fname",fname_str);
                    userDataMap.put("lname",lname_str);
                    userDataMap.put("email",email_str);
                    userDataMap.put("password",password_str);
                    userDataMap.put("mobile",mobile_str);
                    rootRef.child("Users").child(email).updateChildren(userDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>(){
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(SignUpActivity.this, "Congratulations," +
                                                "your account has been created", Toast.LENGTH_LONG).show();
                                        Log.e("errrror","hello");
                                        startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                                        loadingBar.dismiss();
                                    }
                                    else{
                                        Log.e("else","else");
                                        Toast.makeText(SignUpActivity.this,"Network Error: please try again...",Toast.LENGTH_LONG).show();
                                        loadingBar.dismiss();

                                    }
                                }
                            });
                }
                else {
                    Log.e("already","already");
                    Toast.makeText(SignUpActivity.this,"this "+email_str+"already exists",Toast.LENGTH_LONG).show();
                    loadingBar.dismiss();
                    Toast.makeText(SignUpActivity.this,"Please try anther email",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("sign up onCancelled",databaseError.toString());

            }
        });
    }

}
