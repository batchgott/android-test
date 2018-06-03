package com.evelope.events.apiModel.remote;

import com.evelope.events.apiModel.SPicturepath;
import com.evelope.events.apiModel.SUser;
import com.evelope.events.database.Picture;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface HttpService {


    @Headers("Content-Type: application/json")
    @POST("register")
    @FormUrlEncoded
    Call<SUser> createUser(@Field("lastname") String lastname,
                           @Field("firstname") String firstname,
                           @Field("username") String username,
                           @Field("password") String password,
                           @Field("description") String description,
                           @Field("email") String email,
                           @Field("phonenumber") String phonenumber,
                           @Field("picturepath_id")SPicturepath picturepath);
}
