package com.manuel.mark_store;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.manuel.mark_store.models.ResponseMessage;
import com.manuel.mark_store.service.ApiService;
import com.manuel.mark_store.service.ApiServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DialogPayDeposFragment extends DialogFragment {
    public DialogPaymentFragment.CallbackResult callbackResult;

    private static final String TAG = DialogPaymentFragment.class.getSimpleName();
    public static final int DIALOG_QUEST_CODE = 300;

    public void setOnCallbackResult(final DialogPaymentFragment.CallbackResult callbackResult) {
        this.callbackResult = callbackResult;
    }

    private int request_code = 0;
    private View root_view;
    private EditText txtrecojo, edtEncargado, edtTelefono;
    private EditText txtpago, txttotal, edtdireccion, edtCantidadCarrito;
    private String tiprecojo, tippago;
    private Button btnContinuarPedido;
    private Double total;
    String encargado;
    Integer celular, cantidadCarrito, id_producto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root_view = inflater.inflate(R.layout.dialog_payment_deposito, container, false);
        ((ImageButton) root_view.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        txtrecojo = root_view.findViewById(R.id.txtRecojo);
        txtpago = root_view.findViewById(R.id.txtPago);
        txttotal = root_view.findViewById(R.id.txttotal);
        btnContinuarPedido = root_view.findViewById(R.id.bt_continuarPedido);
        edtdireccion = root_view.findViewById(R.id.edtDireccionPedido);
        edtEncargado = root_view.findViewById(R.id.edtEncargadoPedido);
        edtTelefono = root_view.findViewById(R.id.edtCelularencargadoPedido);
        edtCantidadCarrito = root_view.findViewById(R.id.edtCantidadCarrito);

        tiprecojo = getArguments().getString("edtrecojo");
        Log.e(TAG,"tipo de recojo: "+tiprecojo);

        tippago = getArguments().getString("edtpago");
        Log.e(TAG,"forma de pago: "+tippago);

        total = getArguments().getDouble("totalmonto");
        Log.e(TAG,"total: "+total);

        cantidadCarrito= getArguments().getInt("totalcarrito");
        Log.e(TAG,"cantidadCarrito: "+cantidadCarrito);

        id_producto= getArguments().getInt("id_producto");
        Log.e(TAG,"id del producto: "+id_producto);

        //Enviar valores a textView
        txtrecojo.setText(tiprecojo);
        txtpago.setText(tippago);
        txttotal.setText(Double.toString(total));

        btnContinuarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(edtdireccion.getText().toString().isEmpty()){
                    Toast.makeText(v.getContext(), "Dirección es requerida", Toast.LENGTH_SHORT).show();
                }else{
                    /*java.util.Date d = new java.util.Date();
                    java.sql.Date date2 = new java.sql.Date(d.getTime());
                    java.sql.Date date3 = new java.sql.Date(d.getTime());*/

                    java.util.Date dt = new java.util.Date();

                    java.text.SimpleDateFormat sdf =
                            new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    String currentTime = sdf.format(dt);

                    Double total = Double.parseDouble(txttotal.getText().toString());
                    String pago = txtpago.getText().toString();
                    String recojo = txtrecojo.getText().toString();
                    String direccion = edtdireccion.getText().toString();
                    if (edtEncargado.getText().toString().isEmpty()){
                        encargado = "Ninguno";
                    }else {
                        encargado = edtEncargado.getText().toString();
                    }
                    if (edtTelefono.getText().toString().isEmpty()){
                        celular = 0;
                    }else{
                        celular = Integer.parseInt(edtTelefono.getText().toString());
                    }

                    /*ApiService service = ApiServiceGenerator.createService(ApiService.class);
                    Call<ResponseMessage> call = service.crearPedido(currentTime, total, pago,recojo,direccion,currentTime,encargado,celular);
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
                                    showDialogFullscreen();

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

                    });*/
                }
            }
        });

        return root_view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public void setRequestCode(int request_code) {
        this.request_code = request_code;
    }

    public interface CallbackResult {
        void sendResult(int requestCode);
    }
    private void showDialogFullscreen() {
        Bundle bundle = new Bundle();
        FragmentManager fragmentManager = getFragmentManager();
        DialogDetallePedidoFragment newFragment = new DialogDetallePedidoFragment();
        newFragment.setRequestCode(DIALOG_QUEST_CODE);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        bundle.putInt("cantidadcarrito", cantidadCarrito);
        bundle.putInt("producto_id", id_producto);
        newFragment.setArguments(bundle);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
    }
}
