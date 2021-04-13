package com.example.map.network;


import com.example.map.model.Model_ReverseItem;
import com.example.map.model.Search;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    Api.Companion Compation = Companion.$$Instance;

    @GET("reverse?format=json")
    Call<Model_ReverseItem> reverse(@Query("lat") Double lat, @Query("lon") Double lon);

    @GET("search?format=json")
    Call<List<Search>> search(@Query("q") String q);

    final class Companion {
        static final Api.Companion $$Instance;
        public final Api invoke() {
            return (new Retrofit.Builder())
                    .baseUrl("https://nominatim.openstreetmap.org/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(Api.class);


        }
        static {
            $$Instance = new Companion();
        }

    }


}
