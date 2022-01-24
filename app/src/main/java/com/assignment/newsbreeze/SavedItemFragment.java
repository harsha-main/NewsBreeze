package com.assignment.newsbreeze;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment.newsbreeze.adapter.SavedListAdapter;
import com.assignment.newsbreeze.model.data.Article;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class SavedItemFragment extends Fragment {
    TextInputEditText input;
    MainActivity activity;
    InputMethodManager imm;
    RecyclerView savedList;
    SavedListAdapter savedListAdapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity= (MainActivity) getActivity();
        view.findViewById(R.id.back).setOnClickListener(v -> getActivity().onBackPressed());
          savedList = view.findViewById(R.id.savedList);
          savedListAdapter = new SavedListAdapter(activity);
        savedListAdapter.setItems(activity.getHomeViewModel().getSavedItems().getValue());
        savedList.setAdapter(savedListAdapter);
        savedList.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL, false));

        input = view.findViewById(R.id.inputsearch);
        setSearch();
    }

    private void setSearch() {

        input.setOnEditorActionListener((v, actionId, event) -> {
            String s = v.getText().toString();
            List<Article> list = activity.getHomeViewModel().searchFromList(s + "");
            savedListAdapter.setItems(list);
            hideKeyboard();
            return true;
        });

    }

    void hideKeyboard() {
        if (imm == null)
            imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        try {
            imm.hideSoftInputFromWindow(savedList.getWindowToken(), 0);
        } catch (Exception ignored) {
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.saveditem_screen, container, false);

        return root;
    }
}
