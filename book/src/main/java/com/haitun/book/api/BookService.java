package com.haitun.book.api;

import com.haitun.book.model.BaseResponse;
import com.haitun.book.model.Book;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 笨笨 on 2016/4/30.
 */
public interface BookService {

    @GET("book/get")
    Observable<BaseResponse<List<Book>>> getBookList(@Query("currentPage") int currentPage, @Query("pageSize") int pageSize);

    @GET("book/{id}")
    Observable<BaseResponse<Book>> getBook(@Path("id") int id);

    @GET("book/name/{name}")
    Observable<BaseResponse<List<Book>>> searchByName(@Path("name") String name);

    @GET("book/category/{name}")
    Observable<BaseResponse<List<Book>>> searchByCategory(@Path("name") String name);

    @GET("book/isbn/{isbn}")
    Observable<BaseResponse<Book>> getBookByISBN(@Path("isbn") String isbn);

    @FormUrlEncoded
    @POST("review/add")
    Observable<BaseResponse> addReview(@Field("userid") String userId,@Field("bookid") int id);

    @FormUrlEncoded
    @POST("book/recommend")
    Observable<BaseResponse<List<Book>>> getRecommendBooks(@Field("userid") String userid);
}
