package com.assignment.newsbreeze.model;


import static com.assignment.newsbreeze.helper.Constants.newsKey;

import com.assignment.newsbreeze.model.data.DataModel;

import retrofit2.Call;

public class ModelHelper {
    APIInterface apiInterface;
    static ModelHelper helper;

    private ModelHelper() {
        apiInterface = APIClient.getClient().create(APIInterface.class);

    }

    //preferred singleton as it could potentially consume a lot of resources
    public static ModelHelper getInstance() {
        if (helper != null) return helper;
        helper = new ModelHelper();
        return helper;
    }

    public Call<DataModel> getBreakingNewsRequest() {

        return apiInterface.getBreakingNewsItems("in", newsKey);

    }
    public Call<DataModel> getSearchRequest(String search) {

        return apiInterface.getSearchNewsItems(search, newsKey);

    }
    public Call<DataModel> getSearchNewsRequest(String string) {

        return apiInterface.getSearchNewsItems(string, newsKey);

    }
}
