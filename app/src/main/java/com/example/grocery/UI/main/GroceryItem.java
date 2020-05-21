package com.example.grocery.UI.main;

import android.net.Uri;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class GroceryItem {

    private StorageReference mStorageRef;
    private Uri uri=null;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    mStorageRef = FirebaseStorage.getInstance().getReference();
    DatabaseReference ref=  FirebaseDatabase.getInstance().getReference();

}
