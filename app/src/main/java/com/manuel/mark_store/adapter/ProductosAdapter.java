package com.manuel.mark_store.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.manuel.mark_store.BuyActivity;
import com.manuel.mark_store.R;
import com.manuel.mark_store.models.Producto;
import com.manuel.mark_store.models.ResponseMessage;
import com.manuel.mark_store.service.ApiService;
import com.manuel.mark_store.service.ApiServiceGenerator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ViewHolder> {

    public List<Producto>productos;
    private static final String TAG = "PRODUCTOFRAGMENT";
    String cantidad;


    public ProductosAdapter(){
        this.productos = new ArrayList<>();
    }

    public void setProductos(List<Producto>productos){
        this.productos = productos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imagePro;
        public TextView nombrePro;
        public TextView precioPro;
        public TextView stockPro;
        public EditText cantidadProducto;
        private Button btnAgregarC;

        public ViewHolder(View itemView){
            super(itemView);
            imagePro = itemView.findViewById(R.id.imagePro);
            nombrePro = itemView.findViewById(R.id.nombrePro);
            precioPro = itemView.findViewById(R.id.precioPro);
            stockPro = itemView.findViewById(R.id.stockPro);
            cantidadProducto = itemView.findViewById(R.id.cantidadProducto);
            btnAgregarC = itemView.findViewById(R.id.btnAgregarCarrito);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductosAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Producto producto = this.productos.get(position);
        String c;
        holder.nombrePro.setText(producto.getNombrePro());
        holder.precioPro.setText(producto.getPrecio().toString());
        holder.stockPro.setText(producto.getStock().toString());


        String url = ApiService.API_BASE_URL + "/productos/images/" + producto.getFoto();
        Picasso.with(holder.itemView.getContext()).load(url).into(holder.imagePro);

        holder.btnAgregarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (holder.cantidadProducto.getText().toString().isEmpty()){
                    Toast.makeText(v.getContext(), "Ingrese a una cantidad", Toast.LENGTH_SHORT).show();
                }else{
                    if (Integer.parseInt(holder.cantidadProducto.getText().toString()) <= producto.getStock()){
                        /*Intent intent = new Intent(holder.itemView.getContext(), BuyActivity.class);
                        intent.putExtra("idpro", producto.getIdproductos());
                        intent.putExtra("valorEditText", Integer.parseInt(holder.cantidadProducto.getText().toString()));
                        holder.itemView.getContext().startActivity(intent);*/
                        Integer idproductos = producto.getIdproductos();
                        Integer nuevaCantidad = Integer.parseInt(holder.cantidadProducto.getText().toString());
                        Double nuevoValor = Integer.parseInt(holder.cantidadProducto.getText().toString()) * producto.getPrecio();
                        ApiService service = ApiServiceGenerator.createService(ApiService.class);
                        Call<ResponseMessage> call = service.crearProducto(nuevaCantidad,idproductos, nuevoValor);
                        call.enqueue(new Callback<ResponseMessage>() {
                            @Override
                            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                                try {

                                    int statusCode = response.code();
                                    Log.d(TAG, "HTTP status code: " + statusCode);

                                    if (response.isSuccessful()) {

                                        ResponseMessage responseMessage = response.body();
                                        Log.d(TAG, "responseMessage: " + responseMessage);

                                        Toast.makeText(v.getContext(), responseMessage.getMessage(), Toast.LENGTH_LONG).show();

                                    } else {
                                        Log.e(TAG, "onError: " + response.errorBody().string());
                                        throw new Exception("Error en el servicio");
                                    }

                                } catch (Throwable t) {
                                    try {
                                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                                        Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                                    } catch (Throwable x) {
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                                Log.e(TAG, "onFailure: " + t.toString());
                                Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                            }

                        });

                    }else{
                        Toast.makeText(v.getContext(), "Supera el stock, ingrese una menor cantidad", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.productos.size();
    }
}
