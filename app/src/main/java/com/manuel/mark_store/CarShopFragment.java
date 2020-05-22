package com.manuel.mark_store;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.manuel.mark_store.adapter.CarritosAdapter;
import com.manuel.mark_store.models.Carrito;
import com.manuel.mark_store.models.TablaCarrito;
import com.manuel.mark_store.service.ApiService;
import com.manuel.mark_store.service.ApiServiceGenerator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarShopFragment extends Fragment {

    private static final String TAG = CarShopFragment.class.getSimpleName();
    public static final int DIALOG_QUEST_CODE = 300;

    private RecyclerView carritosList;
    private TextView total;
    private MaterialRippleLayout riplecomprar;
    double totalmonto = 0;
    int totalcarrito = 0;
    Integer id_producto, id_empresa;
    String banco,titular,numero,cc,cci;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_carshop, container, false);

        total =(TextView)RootView.findViewById(R.id.totalCarrito);
        carritosList = (RecyclerView)RootView.findViewById(R.id.recyclerViewCarrito);
        carritosList.setLayoutManager(new LinearLayoutManager(getActivity()));
        carritosList.setAdapter(new CarritosAdapter());

        riplecomprar = RootView.findViewById(R.id.riplecomprar);
        riplecomprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogFullscreen();
            }
        });

        initialize();
        return RootView;
    }

    private void initialize() {

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<List<Carrito>> call = service.getCarritos();

        call.enqueue(new Callback<List<Carrito>>() {
            @Override
            public void onResponse(Call<List<Carrito>> call, Response<List<Carrito>> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {
                        List<Carrito> carritos = response.body();
                        Log.d(TAG, "carritos: " + carritos);

                        for (int i = 0; i<carritos.size(); i++){
                            totalmonto = totalmonto + carritos.get(i).getNuevoValor();
                            totalcarrito= totalcarrito + carritos.get(i).getTotal();
                            id_producto = carritos.get(i).getProducto().getIdproductos();
                            id_empresa = carritos.get(i).getProducto().getEmpresa().getIdEmpresa();
                            banco = carritos.get(i).getProducto().getEmpresa().getBanco();
                            titular = carritos.get(i).getProducto().getEmpresa().getTitular_cuenta();
                            numero = carritos.get(i).getProducto().getEmpresa().getNum_tarjeta();
                            cc = carritos.get(i).getProducto().getEmpresa().getCc();
                            cci = carritos.get(i).getProducto().getEmpresa().getCci();
                        }

                        total.setText("S/."+Double.toString(totalmonto));

                        CarritosAdapter adapter = (CarritosAdapter) carritosList.getAdapter();
                        adapter.setCarritos(carritos);
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
            public void onFailure(Call<List<Carrito>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

    private void showDialogFullscreen() {
        Bundle bundle = new Bundle();
        FragmentManager fragmentManager = getFragmentManager();
        DialogPaymentFragment newFragment = new DialogPaymentFragment();
        newFragment.setRequestCode(DIALOG_QUEST_CODE);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        bundle.putDouble("totalmonto", totalmonto);
        bundle.putInt("id_producto", id_producto);
        bundle.putInt("totalcarrito", totalcarrito);
        bundle.putInt("id_empresa",id_empresa);
        bundle.putString("titular_cuenta",titular);
        bundle.putString("banco",banco);
        bundle.putString("num_tarjeta",numero);
        bundle.putString("cc",cc);
        bundle.putString("cci",cci);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        newFragment.setArguments(bundle);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
    }

    /*private void showDialogFullscreenContaSinDire() {
        Bundle bundle = new Bundle();
        FragmentManager fragmentManager = getFragmentManager();
        DialogPayContadoFragment newFragment = new DialogPayContadoFragment();
        newFragment.setRequestCode(DIALOG_QUEST_CODE);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        bundle.putString("edtrecojo", String.valueOf(edtrecojo.getText()));
        bundle.putString("edtpago", String.valueOf(edtpago.getText()));
        bundle.putDouble("totalmonto", totalmonto);
        bundle.putInt("totalcarrito", totalcarrito);
        bundle.putInt("id_producto", id_producto);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        newFragment.setArguments(bundle);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
    }

    private void showDialogFullscreenConta() {
        Bundle bundle = new Bundle();
        FragmentManager fragmentManager = getFragmentManager();
        DialogPayContado2Fragment newFragment = new DialogPayContado2Fragment();
        newFragment.setRequestCode(DIALOG_QUEST_CODE);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        bundle.putString("edtrecojo", String.valueOf(edtrecojo.getText()));
        bundle.putString("edtpago", String.valueOf(edtpago.getText()));
        bundle.putDouble("totalmonto", totalmonto);
        bundle.putInt("totalcarrito", totalcarrito);
        bundle.putInt("id_producto", id_producto);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        newFragment.setArguments(bundle);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
    }

    private void showDialogFullscreenDepoSinDire() {
        Bundle bundle = new Bundle();
        FragmentManager fragmentManager = getFragmentManager();
        DialogPayDeposFragment newFragment = new DialogPayDeposFragment();
        newFragment.setRequestCode(DIALOG_QUEST_CODE);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        bundle.putString("edtrecojo", String.valueOf(edtrecojo.getText()));
        bundle.putString("edtpago", String.valueOf(edtpago.getText()));
        bundle.putDouble("totalmonto", totalmonto);
        bundle.putInt("totalcarrito", totalcarrito);
        bundle.putInt("id_producto", id_producto);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        newFragment.setArguments(bundle);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
    }

    private void showDialogFullscreenDepo() {
        Bundle bundle = new Bundle();
        FragmentManager fragmentManager = getFragmentManager();
        DialogPayDepos2Fragment newFragment = new DialogPayDepos2Fragment();
        newFragment.setRequestCode(DIALOG_QUEST_CODE);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        bundle.putString("edtrecojo", String.valueOf(edtrecojo.getText()));
        bundle.putString("edtpago", String.valueOf(edtpago.getText()));
        bundle.putDouble("totalmonto", totalmonto);
        bundle.putInt("totalcarrito", totalcarrito);
        bundle.putInt("id_producto", id_producto);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        newFragment.setArguments(bundle);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
    }*/
}
