package com.example.grocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocery.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pedromassango.doubleclick.DoubleClick;
import com.pedromassango.doubleclick.DoubleClickListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button SignUp;
    private Button SignIn;
    private TextView frg_btn;
    private LinearLayout signIn_Content;
    private EditText email_edt;
    private EditText password_edt;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email_edt=findViewById(R.id.email_signIn);
        password_edt=findViewById(R.id.pass_signIn);
        SignIn=findViewById(R.id.sign_in_btn);
        SignUp=findViewById(R.id.sign_up_btn);
        frg_btn=findViewById(R.id.forgotPass_btn);
        signIn_Content=findViewById(R.id.sign_in_content);
        loadingBar=new ProgressDialog(this);



        SignUp.setOnClickListener(this);
        frg_btn.setOnClickListener(this);
       // doubleClick is
        //"A android library lo handle double click on android Views components. You just need to call it on your view
        // in  https://github.com/pedromassango/doubleClick imp "
        SignIn.setOnClickListener( new DoubleClick(new DoubleClickListener() {
            @Override
            public void onSingleClick(View view) {
                signIn_Content.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),"DoubleClick to SignIn ",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDoubleClick(View view) {
                signIn_Content.setVisibility(View.INVISIBLE);
                LoginUser();
                startActivity( new Intent(MainActivity.this,SignUpActivity.class));

            }
        })  );



    }

    private void LoginUser() {

        String email_Str = email_edt.getText().toString();
        String password_Str = password_edt.getText().toString();

        boolean validation=true;

        if (password_Str.length() < 8) {
            password_edt.setError(" Minimum length of Password is should be 8 ");
            password_edt.requestFocus();
            validation=false;
        }
        if (email_Str.isEmpty()) {
            email_edt.setError("Email is required");
            email_edt.requestFocus();
            validation=false;
        }

        if (validation){

            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            AllowAccessToAccount(email_Str,password_Str);

        }

    }

    private void AllowAccessToAccount(final String email_str, final String password_str) {

        final DatabaseReference rootRef;
        rootRef= FirebaseDatabase.getInstance().getReference();
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Users").child(email_str).exists()){
                    User userData= dataSnapshot.child("Users").child(email_str).getValue(User.class);
                    if(userData.getEmail().equals(email_str)){
                        if(userData.getPassword().equals(password_str)){
                            loadingBar.dismiss();
                            startActivity(new Intent(MainActivity.this,HomeActivity.class));
                        }
                        else {
                            loadingBar.dismiss();
                            email_edt.setError("password is incorrect");
                        }

                    }



                }
                else {
                    loadingBar.dismiss();
                    email_edt.setError("Email do not exists");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_up_btn:
                startActivity( new Intent(this,SignUpActivity.class));
                break;


            case R.id.forgotPass_btn:

                break;
        }
    }
}