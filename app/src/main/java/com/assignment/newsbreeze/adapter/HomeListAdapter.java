package com.assignment.newsbreeze.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.HomeItemHolder> {
    List<Article> articles;
    RequestOptions requestOptions;

    SimpleDateFormat inputDatePattern, expectedDatePattern;
    MainActivity activity;

    public HomeListAdapter(MainActivity activity) {
        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder);
        requestOptions.error(R.drawable.error);

        inputDatePattern = new SimpleDateFormat(Constants.datePattern);

        expectedDatePattern = new SimpleDateFormat(Constants.expectedDatePattern);
        this.activity = activity;
    }

    @NonNull
    @Override
    public HomeItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeItemHolder holder, int position) {
        Article item = articles.get(position);
        Glide.with(holder.thumb.getContext()).setDefaultRequestOptions(requestOptions).load(item.getUrlToImage()).into(holder.thumb);

        String dtStart = item.getPublishedAt();
        Date date = null;
        try {
            date = inputDatePattern.parse(dtStart);
        } catch (Exception e) {

        }
        holder.date.setText(expectedDatePattern.format(date));
        holder.title.setText(item.getTitle());
        holder.desc.setText(item.getDescription());
        holder.read.setOnClickListener(v -> activity.openDetailFragment(position));
        holder.save.setOnClickListener(v -> {
            activity.saveItem(position);
            setBookmark(holder.bookmark, true);
        });
        List<Article> savedItems = activity.getHomeViewModel().getSavedItems().getValue();
        boolean isSaved = false;
        if (savedItems != null)
            for (Article article : savedItems) {
                if (item.getTitle().equals(article.getTitle())) {
                    isSaved = true;
                    break;
                }
            }
        setBookmark(holder.bookmark, isSaved);
    }

    void setBookmark(ImageView view, boolean enabled) {
        view.setImageResource(enabled ? R.drawable.bookmark_filled : R.drawable.bookmark);
        view.setBackgroundResource(enabled ? R.drawable.bookmark_background_selected : R.drawable.bookmark_background);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setItems(List<Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    class HomeItemHolder extends RecyclerView.ViewHolder {
        ImageView thumb, bookmark;
        TextView title, desc, date;
        Button read, save;

        public HomeItemHolder(@NonNull View itemView) {
            super(itemView);
            thumb = itemView.findViewById(R.id.thumb);
            bookmark = itemView.findViewById(R.id.bookmark);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);
            read = itemView.findViewById(R.id.read);
            save = itemView.findViewById(R.id.save);

        }
    }
}
