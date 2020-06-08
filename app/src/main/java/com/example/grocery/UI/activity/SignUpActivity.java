package com.example.grocery.UI.activity;

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

import com.example.grocery.R;
import com.example.grocery.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.grocery.UI.activity.MainActivity.isValid;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText Fname ,Lname,email,password, rePassword,mobile_num;
    private Button SignUp;
    private ProgressDialog loadingBar;
    final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        loadingBar=new ProgressDialog(this);
        Fname=findViewById(R.id.fname);
        Lname=findViewById(R.id.lname);
        email=findViewById(R.id.email_signUp);
        password=findViewById(R.id.pass_signUp);
        rePassword =findViewById(R.id.repass_signUp);
        mobile_num=findViewById(R.id.mobile_signUp);
        SignUp=findViewById(R.id.sign_up_btn_act);
        SignUp.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_up_btn_act:
                CreateAccount();

                break;

        }
    }
    //first step to check the requirement to Create Account
    private void CreateAccount() {
        String Fname_Str = Fname.getText().toString();
        String Lname_Str = Lname.getText().toString();
        String email_Str = email.getText().toString().toLowerCase();
        String password_Str = password.getText().toString();
        String rePassword_Str = rePassword.getText().toString();
        String mobile_Str = mobile_num.getText().toString();

        if (isValid(email_Str)) {

            if (password_Str.length() < 8) {
                password.setError(" Minimum length of Password is should be 8 ");
                password.requestFocus();

            } else if (email_Str.isEmpty()) {
                email.setError("Email is required");
                email.requestFocus();

            } else if (password_Str.isEmpty() || rePassword_Str.isEmpty()) {
                password.setError("Password is required");
                rePassword.setError("Password is required");
                password.requestFocus();

            } else if (!password_Str.equals(rePassword_Str)) {
                rePassword.setError("Password doesn't match");
                password.setError("Password doesn't match");
                rePassword.requestFocus();

            } else if (Lname_Str.isEmpty()) {
                Lname.setError("Lname is required");
                Lname.requestFocus();

            } else if (Fname_Str.isEmpty()) {
                Fname.setError("Fname is required");
                Fname.requestFocus();

            } else if (mobile_Str.isEmpty()) {
                mobile_num.setError("mobile is required");
                mobile_num.requestFocus();

            } else if (!Patterns.EMAIL_ADDRESS.matcher(email_Str).matches()) {
                email.setError(" please Enter a valid email");
                email.requestFocus();

            } else {

                loadingBar.setTitle("Create Account");
                loadingBar.setMessage("Please wait, while we are checking the credentials");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                validationEmail(Fname_Str, Lname_Str, email_Str, password_Str, mobile_Str);
            }


        }
        else {


                email.setError("Email is not valid");



        }


    }


    //validation the Email to crate a new one
    private void validationEmail(final String fname_str, final String lname_str, final String email_str, final String password_str, final String mobile_str) {

        final String email= (email_str.replace("@","-")).replace(".","_");

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.child("Users").child(email).child("profile_inf").exists()){
                    User user=new User();
                    user.setFname(fname_str);
                    user.setLname(lname_str);
                    user.setEmail(email_str);
                    user.setPassword(password_str);
                    user.setMobile(mobile_str);

                    rootRef.child("Users").child(email).child("profile_inf").setValue(user)
                            .addOnCompleteListener(new OnCompleteListener<Void>(){
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(SignUpActivity.this, "Congratulations," +
                                                "your account has been created", Toast.LENGTH_LONG).show();

                                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                        loadingBar.dismiss();
                                    }
                                    else{

                                        Toast.makeText(SignUpActivity.this,"Network Error: please try again...",Toast.LENGTH_LONG).show();
                                        loadingBar.dismiss();

                                    }
                                }
                            });
                }
                else {

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
