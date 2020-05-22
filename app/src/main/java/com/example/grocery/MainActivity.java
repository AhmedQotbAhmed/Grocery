package com.example.grocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocery.model.User;
import com.example.grocery.prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pedromassango.doubleclick.DoubleClick;
import com.pedromassango.doubleclick.DoubleClickListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button SignUp;
    private Button SignIn;
    private TextView frg_btn;
    private LinearLayout signIn_Content;
    private EditText email_edt;
    private EditText password_edt;
    private ProgressDialog loadingBar;
    private CheckBox chBx_rememberMe;
    private final DatabaseReference rootRef= FirebaseDatabase.getInstance().getReference();
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
        chBx_rememberMe=findViewById(R.id.chBx_RememberMe);

        SignUp.setOnClickListener(this);
        frg_btn.setOnClickListener(this);

        Paper.init(this);

        String userEmailKey=Paper.book().read(Prevalent.userEmailKey);
        String userPasswordKey=Paper.book().read(Prevalent.userPasswordKey);

        if (userEmailKey!=null&&userPasswordKey!=null){
            if(!userEmailKey.isEmpty()&&!userPasswordKey.isEmpty()){
                AllowAccessToAccount( userEmailKey,userPasswordKey);

            }
        // doubleClick is
        //"A android library lo handle double click on android Views components. You just need to call it on your view
        // in  https://github.com/pedromassango/doubleClick imp "

        SignIn.setOnClickListener( new DoubleClick(new DoubleClickListener() {
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
                LoginUser();}
                else {
                    email_edt.setError("Email is required");
                    password_edt.setError("password is required");

                }



            }
        })  );




        }





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

            else

                loadingBar.setTitle("Login Account");
                loadingBar.setMessage("Please wait, while we are checking the credentials");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                AllowAccessToAccount(email_Str, password_Str);


        }
    }

    private void AllowAccessToAccount(final String email_str, final String password_str) {
        if (chBx_rememberMe.isChecked()){
            Paper.book().write(Prevalent.userEmailKey,email_str);
            Paper.book().write(Prevalent.userPasswordKey,password_str);
        }



        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            final String email= (email_str.replace("@","-")).replace(".","_");
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Users").child(email).exists()){
                    User userData= dataSnapshot.child("Users").child(email).getValue(User.class);
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
                if (!dataSnapshot.child("Admin").child(email).exists()&&email.isEmpty()) {
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
                startActivity( new Intent(this,SignUpActivity.class));
                break;


            case R.id.forgotPass_btn:

                break;
        }
    }

    public void logout(){
        Paper.book().destroy();

    }
}