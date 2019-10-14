package com.engineerskasa.winey.Util;

import com.engineerskasa.winey.Model.Category;
import com.engineerskasa.winey.Model.User;
import com.engineerskasa.winey.Retrofit.IWineyAPI;
import com.engineerskasa.winey.Retrofit.RetrofitClient;

import retrofit2.Retrofit;

public class Common {
    private static final String BASE_URL = "http://api.engineerskasa.com/dwom/";

    public static User currentUser = null;
    public static Category currentCategory = null;

    public static IWineyAPI getAPI() {
        return RetrofitClient.getClient(BASE_URL).create(IWineyAPI.class);
    }
}
