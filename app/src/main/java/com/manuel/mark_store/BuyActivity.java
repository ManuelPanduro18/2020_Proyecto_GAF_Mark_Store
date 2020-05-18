package com.manuel.mark_store;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.manuel.mark_store.adapter.ProductosAdapter;
import com.manuel.mark_store.models.Producto;
import com.manuel.mark_store.service.ApiService;
import com.manuel.mark_store.service.ApiServiceGenerator;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyActivity extends AppCompatActivity {

    private static final String TAG = BuyActivity.class.getSimpleName();

    private Integer idproductos;

    private ImageView fotoPro;
    private TextView nombrePro2;
    private TextView descripcionPro2;
    private TextView precioPro2, cantidad, totalpro;
    int valor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        fotoPro = findViewById(R.id.foto_image);
        nombrePro2 = findViewById(R.id.nombre_pro2);
        descripcionPro2 = findViewById(R.id.descripcion_pro2);
        precioPro2 = findViewById(R.id.precio_pro2);
        cantidad = findViewById(R.id.cantidadPro);
        totalpro = findViewById(R.id.totalPro);


        idproductos = getIntent().getExtras().getInt("idpro");
        valor = getIntent().getExtras().getInt("valorEditText");

        initialize();
    }


    public void initialize(){
        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<Producto> call = service.ShowProductos(idproductos);
        call.enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        Producto producto = response.body();
                        Log.d(TAG, "producto: " + producto);

                        String url = ApiService.API_BASE_URL + "/productos/images/" + producto.getFoto();
                        Picasso.with(BuyActivity.this).load(url).into(fotoPro);

                        nombrePro2.setText(producto.getNombrePro());
                        descripcionPro2.setText(producto.getDescripcion());
                        precioPro2.setText("Precio: S/. " + producto.getPrecio());
                        cantidad.setText("Cantidad a llevar: "+valor);
                        totalpro.setText("Total de venta: S/. "+producto.getPrecio()*valor);
                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(BuyActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }
            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(BuyActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

}
