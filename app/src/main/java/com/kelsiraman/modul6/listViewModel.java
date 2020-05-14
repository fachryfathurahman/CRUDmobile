package com.kelsiraman.modul6;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kelsiraman.modul6.model.Result;
import com.kelsiraman.modul6.retrofit.APIService;
import com.kelsiraman.modul6.retrofit.RetrofitClient;
import com.kelsiraman.modul6.retrofit.response.ResponseDaftar;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class listViewModel extends ViewModel {
    private MutableLiveData<ResponseDaftar> listMutableLiveData;

    public MutableLiveData<ResponseDaftar> getListMutableLiveData(){
        listMutableLiveData = new MutableLiveData<>();

        loadList();

        return listMutableLiveData;
    }

    private void loadList() {
        Retrofit retrofit = RetrofitClient.getRetrofit();
        APIService service = retrofit.create(APIService.class);
        Call<ResponseDaftar> call =service.getListMahasiswa();
        call.enqueue(new Callback<ResponseDaftar>() {
            @Override
            public void onResponse(Call<ResponseDaftar> call, Response<ResponseDaftar> response) {
                listMutableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<ResponseDaftar> call, Throwable t) {
                if (t instanceof IOException) {
                    Log.e(TAG, "network error.");
                } else {
                    Log.e(TAG, "conversion error.");
                }

            }
        });
    }
}
