package com.example.grocery.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.grocery.R;
import com.example.grocery.UI.main.CartAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {
private RecyclerView recyclerView;
private CartAdapter adaptor;
    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.fragment_cart, container, false);
        // Inflate the layout for this fragment
        recyclerView=view.findViewById(R.id.recycler_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adaptor = new CartAdapter();
        recyclerView.setAdapter(adaptor);
        return view;
    }

}
