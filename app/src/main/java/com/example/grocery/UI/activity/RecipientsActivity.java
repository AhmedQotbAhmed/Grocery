package com.example.grocery.UI.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocery.R;
import com.example.grocery.model.User;
import com.example.grocery.prevalent.Prevalent;
import com.example.grocery.subactivity.ChangePassword;
import com.example.grocery.subactivity.CheckoutActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecipientsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private CircleImageView profileImage;
    private String[] category_str = { "Male", "Female"};
    private DatabaseReference userReference;
    private EditText email_txv;
    private EditText fname_txv;
    private EditText lname_txv;
    private EditText address_txv;
    private EditText phone_txv;
    private String url;
    private String password;
    private Uri uri;
    final StorageReference storageReference= FirebaseStorage.getInstance().getReference("Users");
    final String email = Prevalent.userEmail;
    private String gander;
    private Button update_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipients);
        Spinner spin = findViewById(R.id.gander_re);
        ArrayAdapter<String> aa = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,category_str);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        spin.setOnItemSelectedListener(this);
        email_txv= findViewById(R.id.email_txv_re);
        fname_txv= findViewById(R.id.fname_re);
        lname_txv= findViewById(R.id.lname_re);
        phone_txv= findViewById(R.id.phone_txv_re);
        address_txv= findViewById(R.id.address_re);
        update_btn=(Button) findViewById(R.id.Update_re);

        profileImage=findViewById(R.id.profile_image_re);
        userReference = FirebaseDatabase.getInstance().getReference().child("Users").child(email).child("profile_inf");
        // Inflate the layout for this fragment


        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String fname;
                    String lname;
                    String email;
                    String mobile;
                    String address;

                    if (dataSnapshot.child("uri").exists()&&dataSnapshot.child("address").exists()){

                        String imgage=dataSnapshot.child("uri").getValue().toString();
                        fname=dataSnapshot.child("fname").getValue().toString();
                        lname=dataSnapshot.child("lname").getValue().toString();
                        email=dataSnapshot.child("email").getValue().toString();
                        mobile=dataSnapshot.child("mobile").getValue().toString();
                        address=dataSnapshot.child("address").getValue().toString();
                        password=dataSnapshot.child("password").getValue().toString();
                        Picasso.get().load(imgage).into(profileImage);
                        email_txv.setText(email);
                        phone_txv.setText(mobile);
                        fname_txv.setText(fname);
                        lname_txv.setText(lname);
                        address_txv.setText(address);





                    }
                    else {
                        password=dataSnapshot.child("password").getValue().toString();
                        fname = dataSnapshot.child("fname").getValue().toString();
                        lname=dataSnapshot.child("lname").getValue().toString();
                        email=dataSnapshot.child("email").getValue().toString();
                        mobile=dataSnapshot.child("mobile").getValue().toString();

                        email_txv.setText(email);
                        phone_txv.setText(mobile);
                        fname_txv.setText(fname);
                        lname_txv.setText(lname);

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();

            }
        });



//
//        update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!url.isEmpty()&&!gander.isEmpty()) {
//                    Upload_user_data();
//                    startActivity(new Intent(RecipientsActivity.this,HomeActivity.class));
//
//                }
//
//            }
//        });

        update_btn.setOnClickListener(this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {

            uri = data.getData();
            profileImage.setImageURI(uri);
            uploadImage_And_PostData();


        }
    }



    private void selectImage() {
        storageReference.child(email).delete();
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, 1);
    }



    private void uploadImage_And_PostData() {


        storageReference.child(email).putFile(uri)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask
                            .TaskSnapshot> task) {
                        if (task.isSuccessful()) {
//                        the link of image mean  uid = productName
                            getImageUrl();

                        }
                        else{

                        }
                    }
                });
    }

    private void getImageUrl() {
        storageReference.child(email).getDownloadUrl()
                .addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {

                             url = task.getResult().toString();

                        }
                        else{
//                            Toast.makeText(RecipientsActivity.this, "can't Get url of the image  ", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }




    private void Upload_user_data() {
        String fname;
        String lname;
        String email;
        String mobile;
        String address;


        fname=fname_txv.getText().toString();
        lname=lname_txv.getText().toString();
        email=email_txv.getText().toString();
        mobile=phone_txv.getText().toString();
        address=address_txv.getText().toString();
        User user=new User(fname,lname,email,password,mobile,address,gander);

        userReference
                .setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
//                            Toast.makeText(RecipientsActivity.this, "uploading image isSuccessful", Toast.LENGTH_LONG).show();



                        } else {
//                            Toast.makeText(RecipientsActivity.this, "Network Error: please try again...", Toast.LENGTH_LONG).show();


                        }
                    }
                });
        userReference.child("uri").setValue(url);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        gander = category_str[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.Update_re)
        {
            if (!gander.isEmpty()) {
                    Upload_user_data();
                    startActivity(new Intent(RecipientsActivity.this, CheckoutActivity.class));
            }else{

            }
        }
    }
}
