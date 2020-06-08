package com.example.grocery.UI.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.grocery.UI.fragment.CartFragment;
import com.example.grocery.UI.fragment.FavouriteFragment;
import com.example.grocery.UI.fragment.ProfileFragment;
import com.example.grocery.UI.fragment.StoreFragment;


public class FragmentAdapter extends FragmentPagerAdapter {
    private Context myContext;
    private int totalTabs;

    public FragmentAdapter(Context context, FragmentManager fragmentManager, int totalTabs) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.myContext = context;
        this.totalTabs = totalTabs;

    }



    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                StoreFragment storeFragment = new StoreFragment();
                return storeFragment;

            case 1:

                CartFragment cartFragment = new CartFragment();
                return cartFragment;
            case 2:

                FavouriteFragment favouriteFragment = new FavouriteFragment();
                return favouriteFragment;
            case 3:

                ProfileFragment profileFragment = new ProfileFragment();
                return profileFragment;
            default:
                return null;
        }
    }

    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }







}
