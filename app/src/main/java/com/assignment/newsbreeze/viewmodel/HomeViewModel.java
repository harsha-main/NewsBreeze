package com.assignment.newsbreeze.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.assignment.newsbreeze.MainActivity;
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

    public MutableLiveData<List<Article>> getNewsItemsLiveData() {
        return newsItemsLiveData;
    }

    public void init( ) {
        if (newsItemsLiveData == null) {
            newsItemsLiveData = new MutableLiveData<>();
            ArrayList<Article> items = new ArrayList<>();
            newsItemsLiveData.setValue(items);
        }
        ModelHelper helper = ModelHelper.getInstance();
        Call<DataModel> call = helper.getRetrofitCall();
        Log.e(Constants.tag, "data started");
        call.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                setNewsItemsLiveData(response.body());
            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {
                Log.e( Constants.tag, "data failed");
            }
        });
    }
    private void setNewsItemsLiveData(DataModel dataModel){

        newsItemsLiveData.setValue( dataModel.getArticles());

    }
}
