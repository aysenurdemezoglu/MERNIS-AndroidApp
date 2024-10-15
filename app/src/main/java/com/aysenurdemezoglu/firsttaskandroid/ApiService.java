package com.aysenurdemezoglu.firsttaskandroid;

import com.aysenurdemezoglu.firsttaskandroid.model.Person;
import com.aysenurdemezoglu.firsttaskandroid.model.PersonRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
  @POST("api/tcController/checkUser")
    Call<Person> checkUser(@Body Person person);
}