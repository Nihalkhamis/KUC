package com.deleny.kuc.utils;

import com.deleny.kuc.model.AboutUsModel;
import com.deleny.kuc.model.ContactUsModel;
import com.deleny.kuc.model.ContractsModel;
import com.deleny.kuc.model.LoginModel;
import com.deleny.kuc.model.NewsModel;
import com.deleny.kuc.model.PendingRequestModel;
import com.deleny.kuc.model.RequestModel;
import com.deleny.kuc.model.SendTokenModel;
import com.deleny.kuc.model.ServicesModel;
import com.deleny.kuc.model.SocialmediaModel;
import com.deleny.kuc.model.VersionModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class Connectors {

   public interface connectionService{
       public String baseUrl = "http://miskadev-001-site8.itempurl.com/";


       @GET("login/check")
       Call<LoginModel> login(
               @Query("Username") String Username,
               @Query("Password") String Password
       );

       @GET("login/GetUser")
       Call<LoginModel> checkUser(
               @Query("ID") String ID
       );

     //  @FormUrlEncoded
     @POST("Token/Insert")
       Call<SendTokenModel> sendToken(
         @Query("ID") String ID,
         @Query("Token") String Token
       );

       @GET("blog/GetByID")
       Call<ArrayList<NewsModel>> getBlogs(
               @Query("ID") int ID
       );

       @GET("app/getaboutus")
       Call<AboutUsModel> getAboutUs(
       );

       @GET("Services/GetAll")
       Call<ArrayList<ServicesModel>> getServices(
       );

       @GET("Version/check")
       Call<VersionModel> getLastVersion(
               @Query("Section") int Section
       );

       @GET("Partnership/getall")
       Call <ArrayList<ContractsModel>> getContracts(
       );

       @GET("social/get")
       Call <ArrayList<SocialmediaModel>> getSocialmedia(
       );

       @GET("Contact/get")
       Call <ArrayList<ContactUsModel>> getContactUs(
       );

       @GET("Request/GetPendingRequest")
       Call <PendingRequestModel> getRequest(
               @Query("userid") String userid
       );

       @GET("Request")
       Call <ArrayList<RequestModel>> getAllRequest(
               @Query("userid") String userid,
               @Query("MaxRequestID") int MaxRequestID

       );

   }
}
