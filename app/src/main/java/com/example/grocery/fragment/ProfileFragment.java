package com.example.grocery.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocery.R;
import com.example.grocery.prevalent.Prevalent;
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

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private CircleImageView profileImage;

    private DatabaseReference userReference;
    private TextView email_txv;
    private TextView name_txv;
    private TextView phone_txv;
    private Uri  uri;
    private String myUri;
    final StorageReference storageReference= FirebaseStorage.getInstance().getReference("Users");
    final String email = Prevalent.userEmail;;

    public ProfileFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.fragment_profile, container, false);

          email_txv= view.findViewById(R.id.email_txv);
          name_txv= view.findViewById(R.id.name_txv);
          phone_txv= view.findViewById(R.id.phone_txv);

        profileImage=view.findViewById(R.id.profile_image);
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
                    if (dataSnapshot.child("uri").exists()){

                        String imgage=dataSnapshot.child("uri").getValue().toString();
                         fname=dataSnapshot.child("fname").getValue().toString();
                         lname=dataSnapshot.child("lname").getValue().toString();
                         email=dataSnapshot.child("email").getValue().toString();
                         mobile=dataSnapshot.child("mobile").getValue().toString();
//                        String address=dataSnapshot.child("address").getValue().toString();
                        Picasso.get().load(imgage).into(profileImage);
                        email_txv.setText(email);
                        phone_txv.setText(mobile);
                        name_txv.setText(fname+" "+lname);

                    }
                    else {

                             fname = dataSnapshot.child("fname").getValue().toString();
                             lname=dataSnapshot.child("lname").getValue().toString();
                             email=dataSnapshot.child("email").getValue().toString();
                             mobile=dataSnapshot.child("mobile").getValue().toString();

                            email_txv.setText(email);
                            phone_txv.setText(mobile);
                            name_txv.setText(fname+" "+lname);

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


        return view;

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

                            String url = task.getResult().toString();
                            Upload_user_data(url);
                        }
                        else{
                            Toast.makeText(getContext(), "can't Get url of the image  ", Toast.LENGTH_LONG).show();

                        }
                    }
                });

    }

    private void Upload_user_data(String url) {
        userReference.child("uri")
                .setValue(url)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "uploading image isSuccessful", Toast.LENGTH_LONG).show();



                        } else {
                            Toast.makeText(getContext(), "Network Error: please try again...", Toast.LENGTH_LONG).show();


                        }
                    }
                });

    }


}
