package com.kelsiraman.modul6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kelsiraman.modul6.model.Result;
import com.kelsiraman.modul6.retrofit.APIService;
import com.kelsiraman.modul6.retrofit.RetrofitClient;
import com.kelsiraman.modul6.retrofit.response.ResponseTambah;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class updateMahasiswa extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA = "EXTRA";
    public static final String LIST = "LIST";
    ArrayList<Result> listMahasiswa= new ArrayList<>();
    Result model;
    TextView nim, nama, jurusan, email;
    Button btnUpdate, btnHapus;
    String id;
    String prevNim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mahasiswa);
        model = getIntent().getParcelableExtra(EXTRA);
        listMahasiswa = getIntent().getParcelableArrayListExtra(LIST);
        id = model.getId();
        setView();

    }

    private void setView() {

        nim =findViewById(R.id.edtNimUp);
        nim.setText(model.getNim());
        nama = findViewById(R.id.edtNamaUp);
        nama.setText(model.getNama());
        jurusan = findViewById(R.id.edtJurusanUp);
        jurusan.setText(model.getJurusan());
        email = findViewById(R.id.edtEmailUp);
        email.setText(model.getEmail());
        btnUpdate = findViewById(R.id.btnUp);
        btnUpdate.setOnClickListener(this);
        btnHapus = findViewById(R.id.btnHapus);
        btnHapus.setOnClickListener(this);
        prevNim = nim.getText().toString();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnUp:
                update();
                break;
            case R.id.btnHapus:
                delete();
                break;
        }
    }

    private void delete() {
        APIService service = RetrofitClient.getRetrofit().create(APIService.class);
        Call<ResponseTambah> callDelete = service.deleteMahasiswa(id);
        callDelete.enqueue(new Callback<ResponseTambah>() {
            @Override
            public void onResponse(Call<ResponseTambah> call, Response<ResponseTambah> response) {
                Toast.makeText(updateMahasiswa.this, "berhasil menghapus data", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(updateMahasiswa.this,DaftarMahasiswa.class));
                finish();
            }

            @Override
            public void onFailure(Call<ResponseTambah> call, Throwable t) {
                Toast.makeText(updateMahasiswa.this, "gagal menghapus data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void update() {
        APIService service = RetrofitClient.getRetrofit().create(APIService.class);
        String nimS = nim.getText().toString().trim();
        String namas = nama.getText().toString();
        String jurusans = jurusan.getText().toString();
        String emails = email.getText().toString();

        int already=0;
        for (Result daftar :
                listMahasiswa) {
            if (daftar.getNim().equals(nimS)&&!prevNim.equals(nimS)){
                already++;
            }
        }

        if (already>0){
            Toast.makeText(this, "Nim Sudah didaftarkan", Toast.LENGTH_SHORT).show();
        }else {
            Call<ResponseTambah> callUpdate = service.updateMahasiswa(id,nimS,namas,jurusans,emails);
            callUpdate.enqueue(new Callback<ResponseTambah>() {
                @Override
                public void onResponse(Call<ResponseTambah> call, Response<ResponseTambah> response) {
                    Toast.makeText(updateMahasiswa.this, "berhasil merubah data", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(updateMahasiswa.this,DaftarMahasiswa.class));
                    finish();
                }

                @Override
                public void onFailure(Call<ResponseTambah> call, Throwable t) {
                    Toast.makeText(updateMahasiswa.this, "gagal merubah data", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
