package com.manuel.mark_store;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.manuel.mark_store.models.DetallePedido2;
import com.manuel.mark_store.models.Pedido;
import com.manuel.mark_store.models.ResponseMessage;
import com.manuel.mark_store.service.ApiService;
import com.manuel.mark_store.service.ApiServiceGenerator;
import com.squareup.picasso.Picasso;

import java.security.PrivateKey;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DialogDetallePedidoFragment extends DialogFragment {
    public DialogPaymentFragment.CallbackResult callbackResult;

    private static final String TAG = DialogDetallePedidoFragment.class.getSimpleName();

    public void setOnCallbackResult(final DialogPaymentFragment.CallbackResult callbackResult) {
        this.callbackResult = callbackResult;
    }
    private int request_code = 0;
    private View root_view;
    public TextView edtrecojo, edtpago, edtid, txtTotalDetalle, txtFechaDetalle, txtCantidadDetalle, txtVueltoDetalle;
    private Button btFinalizar;
    Integer totalCarritoDetalle, idproducto;
    Integer id_pedido;
    Integer cantidad;
    Integer id_producto;

    //PAra email
    String correo;
    String contraseña;

    Session session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root_view = inflater.inflate(R.layout.dialog_detallepedido, container, false);
        ((ImageButton) root_view.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        correo ="edsonpanduroch@gmail.com";
        contraseña = "Manuel10102000";

        edtid = root_view.findViewById(R.id.txtidpedido);
        edtrecojo = root_view.findViewById(R.id.txtdetallerocojo);
        edtpago = root_view.findViewById(R.id.txtdetallepago);

        txtTotalDetalle = root_view.findViewById(R.id.txtTotalDetalle);
        txtFechaDetalle = root_view.findViewById(R.id.txtFechaDetalle);
        txtCantidadDetalle = root_view.findViewById(R.id.txtCantidadDetalle);
        txtVueltoDetalle = root_view.findViewById(R.id.txtVueltoDetalle);

        totalCarritoDetalle= getArguments().getInt("cantidadcarrito");
        Log.e(TAG,"cantidadCarritoDetalle: "+totalCarritoDetalle);

        idproducto= getArguments().getInt("producto_id");
        Log.e(TAG,"productoidDetalle: "+idproducto);

        btFinalizar = root_view.findViewById(R.id.bt_finalizarDetalle);
        btFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                id_pedido = Integer.parseInt(edtid.getText().toString());
                cantidad = Integer.parseInt(txtCantidadDetalle.getText().toString());
                id_producto = idproducto;
                ApiService service = ApiServiceGenerator.createService(ApiService.class);
                Call<ResponseMessage> call = service.crearDetalle(cantidad, id_producto,id_pedido);

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
                                Intent intent = new Intent(getContext(), MenuActivity.class);
                                startActivity(intent);
                                getActivity().finish();

                                mandarMail();

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

        initialize();
        return root_view;
    }

    public void mandarMail(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Properties properties = new Properties();
        properties.put("mail.smtp.host","smtp.googlemail.com");
        properties.put("mail.smtp.socketFactory.port","465");
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.port","465");

        try{
            session=Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(correo,contraseña);
                }
            });

            if (session!=null){
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(correo));
                message.setSubject("Recibo de pedido");
                message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("pandurochota9@gmail.com"));
                message.setContent("Tu numero de pedido es: "+id_pedido+
                                        "\nTotal de compra: "+txtTotalDetalle.getText().toString()+
                                        "\nForma de pago: "+edtpago.getText().toString()+
                                        "\nTipo de entrega: "+edtrecojo.getText().toString(),"text/html; charset=utf-8");
                Transport.send(message);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void initialize(){
        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<List<Pedido>>call=service.getPedidos();
        call.enqueue(new Callback<List<Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        List<Pedido> pedidos = response.body();
                        Log.d(TAG, "pedidos: " + pedidos);

                        for (int i = 0; i<pedidos.size();i++){
                            edtid.setText(Integer.toString(pedidos.get(i).getIdPedido()));
                            edtpago.setText(pedidos.get(i).getTipo_pago());
                            edtrecojo.setText(pedidos.get(i).getTipo_pedido());
                            txtTotalDetalle.setText("S/."+pedidos.get(i).getTotal().toString());
                            txtCantidadDetalle.setText(String.valueOf(totalCarritoDetalle));

                            String fechaDetalle = pedidos.get(i).getFechaEntrega();
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                Date date2 = format.parse(fechaDetalle);
                                DateFormat format2 = new SimpleDateFormat("d MMM yyyy");
                                String detalleFe = format2.format(date2);
                                txtFechaDetalle.setText(detalleFe);
                            }catch (ParseException e){
                                e.printStackTrace();
                            }
                            txtVueltoDetalle.setText("S/."+pedidos.get(i).getVuelto());
                        }


                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }
            }

            @Override
            public void onFailure(Call<List<Pedido>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
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
}
