package com.haitun.book.api;

import com.haitun.book.model.BaseResponse;
import com.haitun.book.model.User;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by 笨笨 on 2016/4/27.
 */
public interface UserService {

    @GET("user/{id}")
    Observable<BaseResponse<User>> getUser(@Path("id")String id);

    @FormUrlEncoded
    @POST("user/login")
    Observable<BaseResponse> login(@Field("id") String email, @Field("name") String password);
}
