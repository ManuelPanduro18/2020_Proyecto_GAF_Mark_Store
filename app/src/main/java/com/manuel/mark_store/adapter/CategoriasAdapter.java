package com.manuel.mark_store.adapter;

import android.content.Context;
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
import com.manuel.mark_store.ProductFragment;
import com.manuel.mark_store.R;
import com.manuel.mark_store.models.Categoria;
import com.manuel.mark_store.service.ApiService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoriasAdapter extends RecyclerView.Adapter<CategoriasAdapter.ViewHolder>{

    private List<Categoria>categorias;
    private Context context;
    public CategoriasAdapter(){
        this.categorias = new ArrayList<>();
    }

    public void setCategorias(List<Categoria>categorias){
        this.categorias = categorias;
    }

    @Override
    public int getItemCount() {
        return this.categorias.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoriasAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Categoria categoria = this.categorias.get(position);

        holder.nombreCa.setText(categoria.getNombreCa());
        holder.descripcioCa.setText(categoria.getDescripcion());

        String url = ApiService.API_BASE_URL + "/categorias/images/" + categoria.getFoto();
        Picasso.with(holder.itemView.getContext()).load(url).into(holder.fotoImageCa);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Fragment fragment = new ProductFragment();
                FragmentManager fm = ((AppCompatActivity) holder.itemView.getContext()).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                bundle.putInt("IDCa", categoria.getIdCategoria());
                bundle.putString("IDempresa", categoria.getEmpresa().getNombres());
                fragment.setArguments(bundle);
                ft.replace(R.id.fragment_container, fragment).addToBackStack(null)
                        .commit();
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView fotoImageCa;
        public TextView nombreCa;
        public TextView descripcioCa;
        public ViewHolder(View itemView){
            super(itemView);
            fotoImageCa = itemView.findViewById(R.id.imageCa);
            nombreCa = itemView.findViewById(R.id.nombreCa);
            descripcioCa = itemView.findViewById(R.id.descripcionCa);
        }
    }

}