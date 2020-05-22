package com.example.grocery.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.grocery.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment {


    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment View view=  inflater.inflate(R.layout.fragment_cart, container, false);
        //        // Inflate the layout for this fragment
        //        recyclerView=view.findViewById(R.id.recycler_cart);
        //        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //
        //        adaptor = new CartAdapter();
        //        recyclerView.setAdapter(adaptor);
        //        return view;

        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

}
