package com.assignment.newsbreeze.adapter;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment.newsbreeze.MainActivity;
import com.assignment.newsbreeze.R;
import com.assignment.newsbreeze.helper.Constants;
import com.assignment.newsbreeze.model.data.Article;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SavedListAdapter extends RecyclerView.Adapter<SavedListAdapter.HomeItemHolder> {
    List<Article> savedArticles;
    RequestOptions requestOptions;

    SimpleDateFormat inputDatePattern, expectedDatePattern;
    MainActivity activity;

    public SavedListAdapter(MainActivity activity) {
        this.activity = activity;
        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder);
        requestOptions.error(R.drawable.error);

        inputDatePattern = new SimpleDateFormat(Constants.datePattern);

        expectedDatePattern = new SimpleDateFormat(Constants.expectedDatePattern);
    }

    @NonNull
    @Override
    public HomeItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeItemHolder holder, int position) {
        Article item = savedArticles.get(position);
        Glide.with(holder.thumb.getContext()).setDefaultRequestOptions(requestOptions).load(item.getUrlToImage()).into(holder.thumb);

        String dtStart = item.getPublishedAt();
        Date date = null;
        try {
            date = inputDatePattern.parse(dtStart);
        } catch (Exception e) {

        }
        holder.date.setText(expectedDatePattern.format(date));
        holder.title.setText(item.getTitle());
        holder.layout.setOnClickListener(v -> activity.openDetailFragment(savedArticles.get(position), position));
        String author=item.getAuthor();
        author=TextUtils.isEmpty(author)?"":author;
        holder.author.setText(". "+ author);
    }


    @Override
    public int getItemCount() {
        return savedArticles.size();
    }

    public void setItems(List<Article> articles) {
        this.savedArticles = articles;
        notifyDataSetChanged();
    }

    class HomeItemHolder extends RecyclerView.ViewHolder {
        ImageView thumb;
        TextView title, date,author;
        View layout;

        public HomeItemHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.author);
            layout = itemView.findViewById(R.id.saved_item_layout);
            thumb = itemView.findViewById(R.id.thumb);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);

        }
    }
}
