package com.kelsiraman.modul6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kelsiraman.modul6.model.Result;
import com.kelsiraman.modul6.recycleview.RecycleViewAdapter;
import com.kelsiraman.modul6.retrofit.response.ResponseDaftar;

import java.util.ArrayList;

public class DaftarMahasiswa extends AppCompatActivity {
    RecycleViewAdapter adapter;
    ArrayList<Result> listMahasiswa = new ArrayList<>();
    listViewModel mahasiswaViewModel;
    TextView noData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_mahasiswa);

        noData = findViewById(R.id.noData);
        RecyclerView rvMahasiswa = findViewById(R.id.rvMahasiswa);
        rvMahasiswa.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecycleViewAdapter();
        rvMahasiswa.setAdapter(adapter);


        mahasiswaViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(listViewModel.class);

        mahasiswaViewModel.getListMutableLiveData().observe(DaftarMahasiswa.this, new Observer<ResponseDaftar>() {
            @Override
            public void onChanged(ResponseDaftar responseDaftar) {
                listMahasiswa.clear();
                listMahasiswa.addAll(responseDaftar.getResult());
                if (listMahasiswa.size()!=0){
                    adapter.setData(listMahasiswa, DaftarMahasiswa.this);
                    noData.setVisibility(View.INVISIBLE);
                }
                else {
                    noData.setVisibility(View.VISIBLE);
                }
            }
        });
    }

}
