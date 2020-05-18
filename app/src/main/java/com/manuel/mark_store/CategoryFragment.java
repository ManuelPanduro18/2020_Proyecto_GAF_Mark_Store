package com.manuel.mark_store;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.manuel.mark_store.adapter.CategoriasAdapter;
import com.manuel.mark_store.models.Categoria;
import com.manuel.mark_store.models.Producto;
import com.manuel.mark_store.service.ApiService;
import com.manuel.mark_store.service.ApiServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {

    private static final String TAG = CategoryFragment.class.getSimpleName();

    private RecyclerView categoriasList, productosList;
    private Integer empresa_id, id_categoria;
    private String nombre_empresa;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_category, container, false);

        categoriasList = (RecyclerView) RootView.findViewById(R.id.recyclerViewCa);
        categoriasList.setLayoutManager(new LinearLayoutManager(getActivity()));
        categoriasList.setAdapter(new CategoriasAdapter());

        //empresa_id = getActivity().getIntent().getExtras().getInt("ID");
        empresa_id = getArguments().getInt("ID");
        Log.e(TAG, "empresa_id:" + empresa_id);

        nombre_empresa = getArguments().getString("IDnom");
        Log.e(TAG,"nombre_empresa:"+ nombre_empresa);

        /*id_categoria = getArguments().getInt("IDCa");
        Log.e(TAG, "id_categoria: "+id_categoria);*/

        //Animando
        /*RecyclerView recyclerView = (RecyclerView)RootView.findViewById(R.id.recyclerViewCa);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));*/

        Categoria categoria = new Categoria();

        Toolbar toolbar = (Toolbar) RootView.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Categorías de "+nombre_empresa);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        initialize();
        return RootView;
    }
    private void initialize() {

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<List<Categoria>> call = service.showCategoria(empresa_id);

        call.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        List<Categoria> categorias = response.body();
                        Log.d(TAG, "categorias: " + categorias);

                        CategoriasAdapter adapter = (CategoriasAdapter) categoriasList.getAdapter();
                        adapter.setCategorias(categorias);
                        adapter.notifyDataSetChanged();

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

}
