package com.example.grocery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pedromassango.doubleclick.DoubleClick;
import com.pedromassango.doubleclick.DoubleClickListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button SignUp;
    private Button SignIn;
    private TextView frg_btn;
    private LinearLayout signIn_Content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SignIn=findViewById(R.id.sign_in_btn);
        SignUp=findViewById(R.id.sign_up_btn);
        frg_btn=findViewById(R.id.forgotPass_btn);
        signIn_Content=findViewById(R.id.sign_in_content);



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
                startActivity( new Intent(MainActivity.this,SignUpActivity.class));

            }
        })  );



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