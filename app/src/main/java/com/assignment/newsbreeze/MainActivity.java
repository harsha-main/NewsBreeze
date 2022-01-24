package com.assignment.newsbreeze;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment.newsbreeze.adapter.HomeListAdapter;
import com.assignment.newsbreeze.helper.Constants;
import com.assignment.newsbreeze.viewmodel.HomeViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    HomeListAdapter adapter;


    HomeViewModel homeViewModel;
    ProgressBar progress;
    RecyclerView recyclerView;
    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress = findViewById(R.id.progressbar);
        setList();
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.init();
        homeViewModel.getNewsItemsLiveData().observe(this, articles -> {
            if (articles.size() > 0) progress.setVisibility(View.GONE);
            adapter.setItems(articles);
        });
        homeViewModel.getProgressVisible().observe(this, visible -> {
            if(visible)progress.setVisibility(View.VISIBLE);
        });
        setSearch();
    }

    private void setSearch() {
        TextInputEditText input = findViewById(R.id.inputsearch);

        input.setOnEditorActionListener((v, actionId, event) -> {
            String s = v.getText().toString();
            homeViewModel.createRequest(s + "");
            hideKeyboard();
            return true;
        });

    }

    void hideKeyboard() {
        if (imm == null)
            imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        try {
            imm.hideSoftInputFromWindow(recyclerView.getWindowToken(), 0);
        } catch (Exception ignored) {
        }
    }

    private void setList() {
        recyclerView = findViewById(R.id.newsList);
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
        homeViewModel.addSaveItem(position);
    }

    public HomeViewModel getHomeViewModel() {
        return homeViewModel;
    }
}