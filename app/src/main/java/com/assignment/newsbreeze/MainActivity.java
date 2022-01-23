package com.assignment.newsbreeze;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment.newsbreeze.adapter.HomeListAdapter;
import com.assignment.newsbreeze.helper.Constants;
import com.assignment.newsbreeze.viewmodel.HomeViewModel;

public class MainActivity extends AppCompatActivity {
    HomeListAdapter adapter;
    HomeViewModel homeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setList();

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.init();
        homeViewModel.getNewsItemsLiveData().observe(this, articles -> adapter.setItems(articles));
    }

    private void setList() {
        RecyclerView recyclerView = findViewById(R.id.newsList);
        adapter = new HomeListAdapter(this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
    }

    public void openDetailFragment(int position) {

        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.POSITION, position);
        bundle.putSerializable(Constants.DATA, homeViewModel.getNewsItemsLiveData().getValue().get(position));
        //add item data
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame, fragment).addToBackStack("").commit();


    }

    public void saveItem(int position) {

    }
}