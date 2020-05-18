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
import com.manuel.mark_store.adapter.ProductosAdapter;
import com.manuel.mark_store.models.Categoria;
import com.manuel.mark_store.models.Producto;
import com.manuel.mark_store.service.ApiService;
import com.manuel.mark_store.service.ApiServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductFragment extends Fragment {
    private static final String TAG = CategoryFragment.class.getSimpleName();

    private RecyclerView productoslist;
    private Integer id_categoria;
    private String empresa_Nom;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_product, container, false);

        productoslist = (RecyclerView)RootView.findViewById(R.id.recyclerViewPro);
        productoslist.setLayoutManager(new LinearLayoutManager(getActivity()));
        productoslist.setAdapter(new ProductosAdapter());

        id_categoria = getArguments().getInt("IDCa");
        Log.e(TAG,"Categoria_id: "+id_categoria);

        empresa_Nom = getArguments().getString("IDempresa");
        Log.e(TAG,"nombre_empre_product: "+empresa_Nom);

        Toolbar toolbar = (Toolbar) RootView.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Productos de "+empresa_Nom);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        initialize();

        return RootView;
    }
    public void initialize(){
        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<List<Producto>> call = service.showProductoC(id_categoria);

        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        List<Producto> productos = response.body();
                        Log.d(TAG, "productos: " + productos);

                        ProductosAdapter adapter = (ProductosAdapter) productoslist.getAdapter();
                        adapter.setProductos(productos);
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
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }
}
