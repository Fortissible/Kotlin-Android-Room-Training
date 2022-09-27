package com.example.apitraining_reqresapiprofile

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("users")
    fun getUsersList(
        @Query("page") page: String,
        @Query("per_page") per_page:String
    ): Call<ListUserResponse>

    @GET("users/{userid}")
    fun getUserById(
        @Path("userid") userid:String
    ): Call<SearchUserResponse>

    @FormUrlEncoded
    @POST("users")
    fun uploadUser(
        @Field("name") name: String,
        @Field("job") job: String
     ): Call<UploadUserResponse>
}