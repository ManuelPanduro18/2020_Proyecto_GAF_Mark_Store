package com.manuel.mark_store.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.manuel.mark_store.CategoryFragment;
import com.manuel.mark_store.R;
import com.manuel.mark_store.models.Empresa;
import com.manuel.mark_store.service.ApiService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class EmpresasAdapter extends RecyclerView.Adapter<EmpresasAdapter.ViewHolder> {

    private List<Empresa> empresas;

    public EmpresasAdapter(){
        this.empresas = new ArrayList<>();
    }
    Context context;

    public void setEmpresas(List<Empresa> empresas){
        this.empresas = empresas;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView fotoImage;
        public TextView nombreText;
        public TextView fechaText;

        public ViewHolder(View itemView) {
            super(itemView);
            fotoImage = itemView.findViewById(R.id.image);
            nombreText = itemView.findViewById(R.id.nombre);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empresa, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {

        final Empresa empresa = this.empresas.get(position);

        viewHolder.nombreText.setText(empresa.getNombres());

        String url = ApiService.API_BASE_URL + "/empresas/images/" + empresa.getLogo();
        Picasso.with(viewHolder.itemView.getContext()).load(url).into(viewHolder.fotoImage);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Fragment fragment = new CategoryFragment();
                FragmentManager fm = ((AppCompatActivity) viewHolder.itemView.getContext()).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                bundle.putInt("ID", empresa.getIdEmpresa());
                bundle.putString("IDnom", empresa.getNombres());
                fragment.setArguments(bundle);
                ft.add(R.id.fragment_container, fragment).addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.empresas.size();
    }

}

