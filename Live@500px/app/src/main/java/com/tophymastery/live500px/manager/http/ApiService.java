package com.tophymastery.live500px.manager.http;

import com.tophymastery.live500px.dao.PhotoItemCollectionDao;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by tophy on 14/2/2016.
 */
public interface ApiService {
    @POST("list")
    Call<PhotoItemCollectionDao> loadPhotoList();
}
