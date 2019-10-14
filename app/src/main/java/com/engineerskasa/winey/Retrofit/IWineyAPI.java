package com.engineerskasa.winey.Retrofit;

import com.engineerskasa.winey.Model.Banner;
import com.engineerskasa.winey.Model.Category;
import com.engineerskasa.winey.Model.CheckUserResponse;
import com.engineerskasa.winey.Model.Drink;
import com.engineerskasa.winey.Model.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IWineyAPI {
    @FormUrlEncoded
    @POST("checkUser.php")
    Call<CheckUserResponse> checkUserExist(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("register.php")
    Call<User> registerNewUser(@Field("phone") String phone,
                               @Field("name") String name,
                               @Field("birthdate") String birthdate,
                               @Field("address") String address);

    @FormUrlEncoded
    @POST("getUser.php")
    Call<User> fetchUserInfo(@Field("phone") String phone);

    @GET("getBanner.php")
    Observable<List<Banner>> getBanners();

    @GET("getMenu.php")
    Observable<List<Category>> getMenu();

    @FormUrlEncoded
    @POST("getDrink.php")
    Observable<List<Drink>> getDrink(@Field("menuid") String menuID);
}
