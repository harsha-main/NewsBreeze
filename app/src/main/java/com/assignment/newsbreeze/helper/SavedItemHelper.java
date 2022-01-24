package com.assignment.newsbreeze.helper;

import android.widget.ImageView;

import com.assignment.newsbreeze.R;
import com.assignment.newsbreeze.model.data.Article;

import java.util.List;

public class SavedItemHelper {
    public static boolean isPresent(List<Article> savedItems, Article item) {
        if (savedItems != null)
            for (Article article : savedItems) {
                if (item.getTitle().equals(article.getTitle())) {
                    return true;
                }
            }
        return false;
    }
    public static void setBookmark(ImageView view, boolean enabled) {
        view.setImageResource(enabled ? R.drawable.bookmark_filled : R.drawable.bookmark);
        view.setBackgroundResource(enabled ? R.drawable.bookmark_background_selected : R.drawable.bookmark_background);
    }
}
