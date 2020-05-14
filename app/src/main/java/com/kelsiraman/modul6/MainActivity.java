package com.kelsiraman.modul6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kelsiraman.modul6.retrofit.APIService;
import com.kelsiraman.modul6.retrofit.RetrofitClient;
import com.kelsiraman.modul6.retrofit.response.ResponseTambah;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtNim;
    EditText edtNama;
    EditText edtJurusan;
    EditText edtEmail;
    Button btnTambahMahasiswa;
    Button btnDaftarMahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        edtNim = findViewById(R.id.edtNim);
        edtNama = findViewById(R.id.edtNama);
        edtJurusan = findViewById(R.id.edtJurusan);
        edtEmail = findViewById(R.id.edtEmail);
        btnTambahMahasiswa = findViewById(R.id.btnTambah);
        btnTambahMahasiswa.setOnClickListener(this);
        btnDaftarMahasiswa = findViewById(R.id.btnDaftar);
        btnDaftarMahasiswa.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnTambah:
                addMahasiswa();
                break;
            case R.id.btnDaftar:
                Intent i = new Intent(this,DaftarMahasiswa.class);
                startActivity(i);
        }
    }

    private void addMahasiswa() {
        String nim = edtNim.getText().toString().trim();
        String nama = edtNama.getText().toString();
        String jurusan = edtJurusan.getText().toString();
        String email = edtEmail.getText().toString();

        if(nim.isEmpty()&&nama.isEmpty()&&jurusan.isEmpty()&&email.isEmpty()){
            Toast.makeText(this, "tidak boleh kosong", Toast.LENGTH_SHORT).show();
        }else {
            APIService service = RetrofitClient.getRetrofit().create(APIService.class);
            Call<ResponseTambah> callTambah = service.addMahasiswa(nim,nama,jurusan,email);
            Log.d("TAG", "onResponse: ConfigurationListener::" + callTambah.request().url());
            callTambah.enqueue(new Callback<ResponseTambah>() {
                @Override
                public void onResponse(Call<ResponseTambah> call, Response<ResponseTambah> response) {
                    Toast.makeText(MainActivity.this, "berhasil menambahkan data", Toast.LENGTH_SHORT).show();
                    Log.d("TAG", "onResponse: ConfigurationListener::berhasil");
                    edtNim.setText("");
                    edtNama.setText("");
                    edtJurusan.setText("");
                    edtEmail.setText("");

                }

                @Override
                public void onFailure(Call<ResponseTambah> call, Throwable t) {
                    if (t instanceof IOException) {
                        Log.e(TAG, "network error.");
                        Toast.makeText(MainActivity.this, "jaringan error", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e(TAG, "conversion error.");
                    }
                }
            });
        }

    }
}
