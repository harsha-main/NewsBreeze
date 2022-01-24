package com.assignment.newsbreeze.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.assignment.newsbreeze.helper.Constants;
import com.assignment.newsbreeze.model.ModelHelper;
import com.assignment.newsbreeze.model.data.Article;
import com.assignment.newsbreeze.model.data.DataModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<Article>> newsItemsLiveData;
    private MutableLiveData<Boolean> progressVisible=new MutableLiveData<>();

    private MutableLiveData<List<Article>> savedItems;
    Call<DataModel> searchRequest;

    public MutableLiveData<List<Article>> getNewsItemsLiveData() {
        return newsItemsLiveData;
    }

    public MutableLiveData<Boolean> getProgressVisible() {
        return progressVisible;
    }

    public void init() {
        if (newsItemsLiveData == null) {
            newsItemsLiveData = new MutableLiveData<>();
            ArrayList<Article> items = new ArrayList<>();
            newsItemsLiveData.setValue(items);
        }
        if (savedItems == null) {
            savedItems = new MutableLiveData<>();
            ArrayList<Article> items = new ArrayList<>();
            savedItems.setValue(items);
        }
        ModelHelper helper = ModelHelper.getInstance();
        Call<DataModel> call = helper.getBreakingNewsRequest();
        call.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                setNewsItemsLiveData(response.body());
            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {
                newsItemsLiveData.setValue(new ArrayList<>());
            }
        });
    }

    public void createRequest(String s) {

        ModelHelper helper = ModelHelper.getInstance();
        if (searchRequest != null) searchRequest.cancel();
        searchRequest = helper.getSearchRequest(s);
        progressVisible.setValue(true);
        Log.e(Constants.tag, "search started");
        searchRequest.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                setNewsItemsLiveData(response.body());
            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {
                progressVisible.setValue(false);
            }
        });
    }

    private void setNewsItemsLiveData(DataModel dataModel) {

        newsItemsLiveData.setValue(dataModel.getArticles());

    }

    public MutableLiveData<List<Article>> getSavedItems() {
        return savedItems;
    }

    public void addSaveItem(int position) {

        List<Article> list = savedItems.getValue();
        list.add(newsItemsLiveData.getValue().get(position));
        savedItems.setValue(list);
    }

}
