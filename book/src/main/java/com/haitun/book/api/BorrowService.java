package com.haitun.book.api;

import com.haitun.book.model.BaseResponse;
import com.haitun.book.model.Borrow;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by 笨笨 on 2016/4/29.
 */
public interface BorrowService {

    @FormUrlEncoded
    @POST("borrows")
    Observable<BaseResponse<List<Borrow>>> getBorrowList(@Field("sid") String sid);
}
