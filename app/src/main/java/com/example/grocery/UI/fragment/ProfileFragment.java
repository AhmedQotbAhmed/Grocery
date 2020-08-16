package com.example.grocery.UI.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.grocery.R;
import com.example.grocery.UI.activity.LoginActivity;
import com.example.grocery.UI.activity.RecipientsActivity;
import com.example.grocery.prevalent.Prevalent;
import com.example.grocery.subactivity.ChangePassword;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    final StorageReference storageReference = FirebaseStorage.getInstance().getReference("Users");
    final String email = Prevalent.userEmail;
    private CircleImageView profileImage;
    private DatabaseReference userReference;
    private TextView email_txv;
    private TextView recipients_prof;
    private TextView name_txv;
    private TextView logout_prof;
    private TextView change_password;
    private TextView phone_txv;
    private Uri uri;
    private AlertDialog alertDialog;

    public ProfileFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        recipients_prof = view.findViewById(R.id.recipients_prof);
        change_password = view.findViewById(R.id.change_password_prof);
        logout_prof = view.findViewById(R.id.logout_prof);
        email_txv = view.findViewById(R.id.email_txv);
        name_txv = view.findViewById(R.id.name_txv);
        phone_txv = view.findViewById(R.id.phone_txv);

        profileImage = view.findViewById(R.id.profile_image);
        userReference = FirebaseDatabase.getInstance().getReference().child("Users").child(email).child("profile_inf");
        // Inflate the layout for this fragment

        logout_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                 alertDialog = new AlertDialog.Builder(getContext()).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("  Are you sure, you want Logout");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences sp;

                                sp = getContext().getSharedPreferences("userLogin", MODE_PRIVATE);
                                sp.edit().remove("Unm").apply();
                                sp.edit().remove("Psw").apply();
                                startActivity(new Intent(getContext(), LoginActivity.class));
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();


            }
        });
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String fname;
                    String lname;
                    String email;
                    String mobile;
                    if (dataSnapshot.child("uri").exists()) {

                        String imgage = dataSnapshot.child("uri").getValue().toString();
                        fname = dataSnapshot.child("fname").getValue().toString();
                        lname = dataSnapshot.child("lname").getValue().toString();
                        email = dataSnapshot.child("email").getValue().toString();
                        mobile = dataSnapshot.child("mobile").getValue().toString();
//                        String address=dataSnapshot.child("address").getValue().toString();
                        Picasso.get().load(imgage).into(profileImage);
                        email_txv.setText(email);
                        phone_txv.setText(mobile);
                        name_txv.setText(fname + " " + lname);

                    } else {

                        fname = dataSnapshot.child("fname").getValue().toString();
                        lname = dataSnapshot.child("lname").getValue().toString();
                        email = dataSnapshot.child("email").getValue().toString();
                        mobile = dataSnapshot.child("mobile").getValue().toString();

                        email_txv.setText(email);
                        phone_txv.setText(mobile);
                        name_txv.setText(fname + " " + lname);

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ChangePassword.class));


            }
        });


        recipients_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), RecipientsActivity.class));

            }
        });


        return view;

    }






}
