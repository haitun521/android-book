package com.haitun.book.api;

import com.haitun.book.model.BaseResponse;
import com.haitun.book.model.Book;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by 笨笨 on 2016/4/27.
 */
public interface ReviewService {

    @GET("review/get/{userid}")
    Observable<BaseResponse<List<Book>>> getReviewsByUserId(@Path("userid") String userid);

    @FormUrlEncoded
    @POST("review/delete")
    Observable<BaseResponse> delete(@Field("id") int id);
}
