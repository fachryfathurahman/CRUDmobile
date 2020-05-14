package com.kelsiraman.modul6.recycleview;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kelsiraman.modul6.R;
import com.kelsiraman.modul6.model.Result;
import com.kelsiraman.modul6.updateMahasiswa;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.HolderMahasiswa> {

    private ArrayList<Result> listMahasiswa = new ArrayList<>();
    private Context mContext;

    public void setData(ArrayList<Result> listMahasiswa, Context context){
        this.listMahasiswa=listMahasiswa;
        mContext = context;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecycleViewAdapter.HolderMahasiswa onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle,parent,false);
        return new HolderMahasiswa(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapter.HolderMahasiswa holder, int position) {
        Result mahasiswa = listMahasiswa.get(position);
        holder.bind(mahasiswa);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return listMahasiswa.size();
    }

    public class HolderMahasiswa extends RecyclerView.ViewHolder {
        ImageView foto;
        TextView nama;
        TextView nim;
        TextView jurusan;
        ConstraintLayout layout;
        public HolderMahasiswa(@NonNull View itemView) {
            super(itemView);
            foto= itemView.findViewById(R.id.imgMahasiswa);
            nama = itemView.findViewById(R.id.lsNama);
            nim = itemView.findViewById(R.id.lsNim);
            jurusan = itemView.findViewById(R.id.lsJurusan);
            layout = itemView.findViewById(R.id.cdItem);
        }

        void bind(final Result mahasiswa){
            String urlNim = mahasiswa.getNim();
            Log.d(TAG, "bind: "+urlNim);
            String tahun = urlNim.substring(0,4);
            urlNim="https://krs.umm.ac.id/Poto/"+tahun+"/"+urlNim+".JPG";
            nama.setText(mahasiswa.getNama());
            nim.setText(mahasiswa.getNim());
            jurusan.setText(mahasiswa.getJurusan());
            Glide.with(mContext)
                    .load(urlNim)
                    .into(foto);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, updateMahasiswa.class);
                    intent.putExtra(updateMahasiswa.EXTRA,mahasiswa);
                    intent.putExtra(updateMahasiswa.LIST,listMahasiswa);
                    mContext.startActivity(intent);
                }
            });


        }
    }
}
