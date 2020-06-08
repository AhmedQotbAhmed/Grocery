package com.example.grocery.UI.fragment;


import android.app.SearchManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocery.R;
import com.example.grocery.UI.adapter.StoreAdapter;
import com.example.grocery.UI.adapter.ViewAllAdapter;
import com.example.grocery.model.Category;
import com.example.grocery.model.Products;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import static android.content.Context.SEARCH_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class StoreFragment extends Fragment {


    StoreAdapter adaptor;
    private List<Products> list;
    private DatabaseReference postReference;
    private SearchView searchView;
    private RecyclerView recyclerView;

    public StoreFragment() {
        // Required empty public constructor
    }

//    private CartAdapter adaptor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_store, container, false);
        // Inflate the layout for this fragment
        //   "Fruit", "Vegetables",  "Other"

        // for all references

        postReference = FirebaseDatabase.getInstance().getReference().child("products");


        recyclerView = view.findViewById(R.id.recycler_store_Fruit);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        setHasOptionsMenu(true);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


        // GET THE Data from fireBase

        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(postReference, Category.class).build();

        adaptor = new StoreAdapter(options);
        recyclerView.setAdapter(adaptor);
        adaptor.startListening();

/////////////////////////////////////////////////////

    }


    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        super.onCreateOptionsMenu(menu, inflater);

        SearchManager searchManager = (SearchManager) getContext().getSystemService(SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setBackgroundColor((0xFFF));
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        setHasOptionsMenu(true);
        searchView.setQueryHint("Search Product");


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                ViewAllAdapter viewAllAdapter = new ViewAllAdapter(adaptor.getFilter(newText));
                recyclerView.setAdapter(viewAllAdapter);
                return false;
            }


        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(adaptor);
                adaptor.startListening();
                return false;
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adaptor);
        adaptor.startListening();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
