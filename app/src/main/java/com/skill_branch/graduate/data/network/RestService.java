package com.skill_branch.graduate.data.network;

import com.skill_branch.graduate.data.managers.ConstantManager;
import com.skill_branch.graduate.data.network.res.PhotoCardRes;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface RestService {

    @GET("photocard/list")
    Observable<Response<List<PhotoCardRes>>> getPhotoCardResObs(@Header(ConstantManager.HEADER_IF_MODIFIED_SINCE)String lastEntityUpdate, @Query("limit") int limit, @Query("offset")int offset);

//    @GET("products")
//    Observable<Response<List<ProductRes>>> getProductResObs(@Header(ConstantManager.HEADER_IF_MODIFIED_SINCE)String lastEntityUpdate);
//
//
//    @POST("products/{product_id}/comments")
//    Observable<CommentRes> sendComment(@Path("product_id") String productId, @Body CommentRes commentRes);
//
//    @Multipart
//    @POST("avatar")
//    Observable<AvatarUrlRes> uploadUserAvatar(@Part MultipartBody.Part file);
//
//    @POST("login")
//    Observable<Response<UserRes>> loginUser(@Body UserLoginReq userLoginReq);
}
