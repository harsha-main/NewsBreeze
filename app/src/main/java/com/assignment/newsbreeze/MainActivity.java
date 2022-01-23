package com.assignment.newsbreeze;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment.newsbreeze.adapter.HomeListAdapter;
import com.assignment.newsbreeze.model.data.Article;
import com.assignment.newsbreeze.viewmodel.HomeViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    HomeListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setList();

        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.init();
        homeViewModel.getNewsItemsLiveData().observe(this, articles -> adapter.setItems(articles));
    }

    private void setList() {
        RecyclerView recyclerView = findViewById(R.id.newsList);
          adapter = new HomeListAdapter();
        recyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
    }

}