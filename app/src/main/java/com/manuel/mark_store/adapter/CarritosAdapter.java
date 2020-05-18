package com.manuel.mark_store.adapter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.manuel.mark_store.R;
import com.manuel.mark_store.models.Carrito;
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

public class CarritosAdapter extends RecyclerView.Adapter<CarritosAdapter.ViewHolder> {
    private static final String TAG = CarritosAdapter.class.getSimpleName();

    public List<Carrito> carritos;

    public CarritosAdapter(){
        this.carritos = new ArrayList<>();
    }

    public void setCarritos(List<Carrito> carritos){
        this.carritos=carritos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView fotoproducto;
        public TextView txtIdproductos;
        public TextView txtEstado;
        public TextView txtCantidad;
        public TextView txtnuevoValor;
        public ImageButton eliminarCarrito;

        public ViewHolder(View itemView) {
            super(itemView);
            txtIdproductos = itemView.findViewById(R.id.txtproductocarrito);
            txtEstado = itemView.findViewById(R.id.txtEstado);
            txtCantidad = itemView.findViewById(R.id.txtCantidadCarrito);
            txtnuevoValor = itemView.findViewById(R.id.txtnuevoValorCarrito);
            fotoproducto = itemView.findViewById(R.id.fotoproductocarrito);
            eliminarCarrito = itemView.findViewById(R.id.eliminarCarrito);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carshop, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        final Carrito carrito = this.carritos.get(position);

        viewHolder.txtIdproductos.setText(carrito.getProducto().getNombrePro());
        viewHolder.txtEstado.setText(carrito.getEstado());
        viewHolder.txtCantidad.setText(carrito.getNuevaCantidad().toString());
        viewHolder.txtnuevoValor.setText("S/."+String.valueOf(carrito.getProducto().getPrecio()));
        String url = ApiService.API_BASE_URL + "/productos/images/" + carrito.getProducto().getFoto();
        Picasso.with(viewHolder.itemView.getContext()).load(url).into(viewHolder.fotoproducto);
        viewHolder.eliminarCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ApiService service = ApiServiceGenerator.createService(ApiService.class);
                Call<ResponseMessage> call = service.destroyCarrito(carrito.getIdtablaC());
                call.enqueue(new Callback<ResponseMessage>() {
                    @Override
                    public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                        try {

                            int statusCode = response.code();
                            Log.d(TAG, "HTTP status code: " + statusCode);

                            if (response.isSuccessful()) {

                                ResponseMessage responseMessage = response.body();
                                Log.d(TAG, "responseMessage: " + responseMessage);
                                carritos.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position,carritos.size());

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
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.carritos.size();
    }
}
