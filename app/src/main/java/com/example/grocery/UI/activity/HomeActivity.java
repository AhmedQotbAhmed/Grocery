package com.example.grocery.UI.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.grocery.R;
import com.example.grocery.UI.adapter.FragmentAdapter;
import com.example.grocery.prevalent.Prevalent;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {
    long size = 0;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        tabLayout = findViewById(R.id.tabLayout_home);
        viewPager = findViewById(R.id.viewPager_home);
        tabLayout.setTabTextColors(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorAccent));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final FragmentAdapter adapter = new FragmentAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed


    }

    @Override
    protected void onStart() {

        super.onStart();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        String email = Prevalent.userEmail;
        reference.child("Users").child(email).child("Cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                size = dataSnapshot.getChildrenCount();
                change_cart_num(size);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    void change_cart_num(long size) {

        BadgeDrawable badge = tabLayout.getTabAt(1).getOrCreateBadge();
        badge.setVisible(true);
        badge.setNumber((int) size);
    }


}
