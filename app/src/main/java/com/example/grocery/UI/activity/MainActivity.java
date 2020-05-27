package com.example.grocery.UI.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocery.R;
import com.example.grocery.model.User;
import com.example.grocery.prevalent.Prevalent;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pedromassango.doubleclick.DoubleClick;
import com.pedromassango.doubleclick.DoubleClickListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout signIn_Content;
    private EditText email_edt;
    private EditText password_edt;
    private ProgressDialog loadingBar;
    private CheckBox chBx_rememberMe;
    private SharedPreferences sp;
    private SharedPreferences.Editor Ed;
    private  DatabaseReference rootRef= FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        sp=getSharedPreferences("userLogin", MODE_PRIVATE);
        Ed=sp.edit();
        email_edt=findViewById(R.id.email_signIn);
        password_edt=findViewById(R.id.pass_signIn);
        Button signIn = findViewById(R.id.sign_in_btn);
        Button signUp = findViewById(R.id.sign_up_btn);
        TextView frg_btn = findViewById(R.id.forgotPass_btn);
        signIn_Content=findViewById(R.id.sign_in_content);
        loadingBar=new ProgressDialog(this);
        chBx_rememberMe=findViewById(R.id.chBx_RememberMe);


        SharedPreferences sp1=this.getSharedPreferences("userLogin", MODE_PRIVATE);

        String unm=sp1.getString("Unm", null);
        String pass = sp1.getString("Psw", null);

        if (unm!=null&&pass!=null){
            if(!unm.isEmpty()&&!pass.isEmpty()){
                loadingBar.setTitle("Login Account");
                loadingBar.setMessage("Please wait, while we are checking the credentials");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                AllowAccessToAccount( unm,pass);

            }
        }
        // doubleClick is
        //"A android library lo handle double click on android Views components. You just need to call it on your view
        // in  https://github.com/pedromassango/doubleClick imp "
        signUp.setOnClickListener(this);
        frg_btn.setOnClickListener(this);

        signIn.setOnClickListener( new DoubleClick(new DoubleClickListener() {
            @Override
            public void onSingleClick(View view) {
                signIn_Content.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),"Double Click to SignIn ",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDoubleClick(View view) {
                String email_Str = email_edt.getText().toString();
                String password_Str = password_edt.getText().toString();
                if (!email_Str.isEmpty()&&!password_Str.isEmpty()){
                signIn_Content.setVisibility(View.INVISIBLE);
                LoginUser();
                }
                else {
                    email_edt.setError("Email is required");
                    password_edt.setError("password is required");

                }

            }
        })  );




        }



    private void LoginUser() {

        String email_Str = email_edt.getText().toString();
        String password_Str = password_edt.getText().toString();


        if(email_edt!=null &&password_edt!=null) {

            if (password_Str.length() < 8) {
                password_edt.setError(" Minimum length of Password is should be 8 ");
                password_edt.requestFocus();
            }
            else if (email_Str.isEmpty()) {
                email_edt.setError("Email is required");
                email_edt.requestFocus();

            }

            else{

                loadingBar.setTitle("Login Account");
                loadingBar.setMessage("Please wait, while we are checking the credentials");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                AllowAccessToAccount(email_Str, password_Str);
            }
        }
    }

    private void AllowAccessToAccount(final String email_str, final String password_str) {
        if (chBx_rememberMe.isChecked()){
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            Ed.putString("Unm",email_str );
            Ed.putString("Psw",password_str);
            Ed.commit();}



        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            final String email= (email_str.replace("@","-")).replace(".","_");
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Users").child(email).child("profile_inf").exists()){
                    User userData= dataSnapshot.child("Users").child(email).child("profile_inf").getValue(User.class);
                    if(userData.getEmail().equals(email_str)){
                        if(userData.getPassword().equals(password_str)){
                            loadingBar.dismiss();
                            Prevalent.currentOnlineUser =userData;
                            Prevalent.userEmail=email;
                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        }
                        else {
                            loadingBar.dismiss();
                            password_edt.setError("password is incorrect");
                        }

                    }



                }
                else if (email_edt.getText().toString().isEmpty()){
                    loadingBar.dismiss();

                }
                else {
                    loadingBar.dismiss();
                    email_edt.setError("Email do not exists");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(" logIn onCancelled",databaseError.toString());
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_up_btn:
                startActivity( new Intent(this, SignUpActivity.class));
                break;


            case R.id.forgotPass_btn:

                break;
        }
    }




    @Override
    public void onBackPressed() {

    }
}