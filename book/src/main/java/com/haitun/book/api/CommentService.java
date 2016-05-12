package com.haitun.book.api;

import com.haitun.book.model.BaseResponse;
import com.haitun.book.model.Comment;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by 笨笨 on 2016/5/1.
 */
public interface CommentService {

    @GET("comment/bookid/{id}")
    Observable<BaseResponse<List<Comment>>> getCommentList(@Path("id") int id);

    @FormUrlEncoded
    @POST("comment/add")
    Observable<BaseResponse> addComment(@Field("content") String content,@Field("bookid") int bookId);
}
