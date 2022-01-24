package com.assignment.newsbreeze;

import static com.assignment.newsbreeze.helper.SavedItemHelper.setBookmark;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.assignment.newsbreeze.helper.Constants;
import com.assignment.newsbreeze.helper.SavedItemHelper;
import com.assignment.newsbreeze.model.data.Article;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class DetailFragment extends Fragment {
    Article data;
    int position;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        position = bundle.getInt(Constants.POSITION);
        data = (Article) bundle.getSerializable(Constants.DATA);
        view.findViewById(R.id.back).setOnClickListener(v -> getActivity().onBackPressed());
        TextView title = view.findViewById(R.id.detail_title);
        title.setText(data.getTitle());
        TextView date = view.findViewById(R.id.date);
        date.setText(data.getPublishedAt());
        TextView author = view.findViewById(R.id.author);

        author.setText(data.getAuthor());
        TextView content = view.findViewById(R.id.content);
        content.setText(data.getContent());
        ImageView image = view.findViewById(R.id.fullImage);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder);
        requestOptions.error(R.drawable.error);
        Glide.with(view.getContext()).setDefaultRequestOptions(requestOptions).load(data.getUrlToImage()).into(image);
        MainActivity activity = (MainActivity) getActivity();
        ImageView bookmark = view.findViewById(R.id.bookmark);
        Button save = view.findViewById(R.id.save);
        save.setOnClickListener(v -> {
            activity.saveItem(bookmark,position,true);
        });
        boolean isSaved = SavedItemHelper.isPresent(activity.getHomeViewModel().getSavedItems().getValue(), data);
        SavedItemHelper.setBookmark(bookmark, isSaved);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.detail_screen, container, false);

        return root;
    }
}
