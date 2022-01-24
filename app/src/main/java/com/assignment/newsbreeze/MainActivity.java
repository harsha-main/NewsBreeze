package com.assignment.newsbreeze;

import static com.assignment.newsbreeze.helper.SavedItemHelper.setBookmark;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment.newsbreeze.adapter.HomeListAdapter;
import com.assignment.newsbreeze.helper.Constants;
import com.assignment.newsbreeze.model.data.Article;
import com.assignment.newsbreeze.viewmodel.HomeViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    private HomeListAdapter adapter;

    private HomeViewModel homeViewModel;
    private ProgressBar progress;
    private RecyclerView recyclerView;
    private InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress = findViewById(R.id.progressbar);
        findViewById(R.id.saved_items).setOnClickListener(v -> openSavedItemsFragment());
        setList();
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.init();
        homeViewModel.getNewsItemsLiveData().observe(this, articles -> {
            if (articles.size() > 0) progress.setVisibility(View.GONE);
            adapter.setItems(articles);
        });
        homeViewModel.getProgressVisible().observe(this, visible -> {
            if (visible) progress.setVisibility(View.VISIBLE);
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

    public void openSavedItemsFragment() {

        SavedItemFragment fragment = new SavedItemFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame, fragment).addToBackStack("").commit();


    }
    public void openDetailFragment(Article article, int position) {

        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.POSITION, position);
        bundle.putSerializable(Constants.DATA, article);
        //add item data
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame, fragment).addToBackStack("").commit();


    }

    public void saveItem(ImageView bookmark, int position) {
        homeViewModel.addSaveItem(position);
        setBookmark(bookmark, true);
    }

    public void saveItem(ImageView bookmark, int position, boolean fromFrag) {
        saveItem(bookmark, position);
        if (fromFrag)
            adapter.notifyItemChanged(position);
    }

    public HomeViewModel getHomeViewModel() {
        return homeViewModel;
    }
}