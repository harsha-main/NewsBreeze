package com.assignment.newsbreeze.model;


import static com.assignment.newsbreeze.helper.Constants.newsKey;

import com.assignment.newsbreeze.MainActivity;
import com.assignment.newsbreeze.model.data.DataModel;

import java.util.ArrayList;

import retrofit2.Call;

public class ModelHelper {

    static ModelHelper helper;

    private ModelHelper() {

    }

    //preferred singleton as it could potentially consume a lot of resources
    public static ModelHelper getInstance() {
        if (helper != null) return helper;
        helper = new ModelHelper();
        return helper;
    }

    public Call<DataModel> getRetrofitCall( ) {
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);


        return apiInterface.doGetListResources("in",newsKey);

    }
}
