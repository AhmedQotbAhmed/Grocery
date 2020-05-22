package com.example.grocery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.grocery.R;
import com.example.grocery.UI.main.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);




        tabLayout = findViewById(R.id.tabLayout_home);
        viewPager = findViewById(R.id.viewPager_home);
        tabLayout.setTabTextColors(getResources().getColor(R.color.colorPrimary),getResources().getColor(R.color.colorAccent));

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_stoe));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_cart));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_favorite));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_profile));
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
}
