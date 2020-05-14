package com.kelsiraman.modul6.retrofit;

import com.kelsiraman.modul6.retrofit.response.ResponseDaftar;
import com.kelsiraman.modul6.retrofit.response.ResponseTambah;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {

    @FormUrlEncoded
    @POST("insert.php")
    Call<ResponseTambah> addMahasiswa(
            @Field("nim") String nim,
            @Field("nama") String nama,
            @Field("jurusan") String jurusan,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("update.php")
    Call<ResponseTambah> updateMahasiswa(
            @Field("id") String id,
            @Field("nim") String nim,
            @Field("nama") String nama,
            @Field("jurusan") String jurusan,
            @Field("email") String email
    );

    @GET("read.php")
    Call<ResponseDaftar> getListMahasiswa();

    @GET("delete.php")
    Call<ResponseTambah> deleteMahasiswa(@Query("id") String id);

}
