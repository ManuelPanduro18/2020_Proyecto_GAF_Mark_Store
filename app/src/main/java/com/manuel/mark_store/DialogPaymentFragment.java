package com.manuel.mark_store;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.manuel.mark_store.models.Cliente;
import com.manuel.mark_store.models.ResponseMessage;
import com.manuel.mark_store.service.ApiService;
import com.manuel.mark_store.service.ApiServiceGenerator;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DialogPaymentFragment extends DialogFragment {
    public CallbackResult callbackResult;

    private static final String TAG = DialogPaymentFragment.class.getSimpleName();
    public static final int DIALOG_QUEST_CODE = 300;

    public void setOnCallbackResult(final CallbackResult callbackResult) {
        this.callbackResult = callbackResult;
    }

    private int request_code = 0;
    private View root_view;
    private EditText edtrecojo,edtdireccion,txttotal,edtDigitoTarjeta, edtContado,edtCredito;
    private EditText edtpago,edtfechapedido,edtMontopago,edtEncargado, edtTelefono;
    private TextView txtDireccionPedido,txtMontoPagoPedido,txtDigitoTarjetaPedido,txtContado;
    private Button btn_continuarpedido;
    Integer cantidadCarrito, id_producto;
    private Double total;
    String fecha1,fecha2,fecha3,fecha4,fecha5,fecha6,fecha7;
    String direccion;
    String currentTime;
    String encargado;
    String submitDate;
    Integer celular;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root_view = inflater.inflate(R.layout.dialog_payment, container, false);
        ((ImageButton) root_view.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        cantidadCarrito= getArguments().getInt("totalcarrito");
        Log.e(TAG,"cantidadCarrito: "+cantidadCarrito);

        id_producto= getArguments().getInt("id_producto");
        Log.e(TAG,"id del producto: "+id_producto);

        total = getArguments().getDouble("totalmonto");
        Log.e(TAG,"total: "+total);

        txttotal = root_view.findViewById(R.id.txttotal);
        edtdireccion = root_view.findViewById(R.id.edtDireccionPedido);
        edtMontopago = root_view.findViewById(R.id.edtMontopagodepedido);
        edtDigitoTarjeta = root_view.findViewById(R.id.edtDigitoTarjetaPedido);
        edtDigitoTarjeta.setVisibility(View.GONE);
        edtEncargado = root_view.findViewById(R.id.edtEncargadoPedido);
        edtTelefono = root_view.findViewById(R.id.edtCelularencargadoPedido);




        txtMontoPagoPedido = root_view.findViewById(R.id.txtMontopagoPedido);
        txtDireccionPedido = root_view.findViewById(R.id.txtDireccionPedido);
        //-----------------------------//
        txtDigitoTarjetaPedido = root_view.findViewById(R.id.txtDigitoTarjetaPedido);
        txtDigitoTarjetaPedido.setVisibility(View.GONE);

        txtContado = root_view.findViewById(R.id.txtContado);

        edtfechapedido = root_view.findViewById(R.id.edtFechaPedido);
        edtfechapedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDatePickerLight((EditText) v);
            }
        });

        edtrecojo = root_view.findViewById(R.id.et_recojo);
        edtrecojo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecojoDialog(v);
            }
        });

        edtContado = root_view.findViewById(R.id.et_contado);
        edtContado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContadoDialog(v);
            }
        });
        edtContado.setVisibility(View.GONE);

        edtCredito = root_view.findViewById(R.id.et_credito);
        edtCredito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreditoDialog(v);
            }
        });
        edtCredito.setVisibility(View.GONE);

        if (txtDireccionPedido.getVisibility() == View.VISIBLE){
            txtDireccionPedido.setVisibility(View.GONE);
        }
        if (edtdireccion.getVisibility() == View.VISIBLE){
            edtdireccion.setVisibility(View.GONE);
        }

        if (txtMontoPagoPedido.getVisibility() == View.VISIBLE){
            txtMontoPagoPedido.setVisibility(View.GONE);
        }

        if (edtMontopago.getVisibility() == View.VISIBLE){
            edtMontopago.setVisibility(View.GONE);
        }

        edtpago = root_view.findViewById(R.id.et_pago);
        edtpago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPagoDialog(v);
            }
        });

        txttotal.setText(Double.toString(total));

        /*//Fecha1
        Calendar calendar = Calendar.getInstance();
        calendar.setLenient(false);

        calendar.setTime(date); // Configuramos la fecha que se recibe
        calendar.add(calendar.DAY_OF_YEAR, 2);
        //------------------------------------------//

        //Fecha2
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setLenient(false);

        calendar2.setTime(date); // Configuramos la fecha que se recibe
        calendar2.add(calendar2.DAY_OF_YEAR, 3);
        //------------------------------------------//

        //Fecha3
        Calendar calendar3 = Calendar.getInstance();
        calendar3.setLenient(false);

        calendar3.setTime(date); // Configuramos la fecha que se recibe
        calendar3.add(calendar3.DAY_OF_YEAR, 4);
        //------------------------------------------//

        //Fecha4
        Calendar calendar4 = Calendar.getInstance();
        calendar4.setLenient(false);

        calendar4.setTime(date); // Configuramos la fecha que se recibe
        calendar4.add(calendar4.DAY_OF_YEAR, 5);
        //------------------------------------------//

        //Fecha5
        Calendar calendar5 = Calendar.getInstance();
        calendar5.setLenient(false);

        calendar5.setTime(date); // Configuramos la fecha que se recibe
        calendar5.add(calendar2.DAY_OF_YEAR, 6);
        //------------------------------------------//

        //Fecha6
        Calendar calendar6 = Calendar.getInstance();
        calendar6.setLenient(false);

        calendar6.setTime(date); // Configuramos la fecha que se recibe
        calendar6.add(calendar2.DAY_OF_YEAR, 7);
        //------------------------------------------//

        //Fecha7
        Calendar calendar7 = Calendar.getInstance();
        calendar2.setLenient(false);

        calendar7.setTime(date); // Configuramos la fecha que se recibe
        calendar7.add(calendar2.DAY_OF_YEAR, 8);
        //------------------------------------------//*

        /*fecha1 = dateFormat.format(calendar.getTime());
        fecha2 = dateFormat.format(calendar2.getTime());
        fecha3 = dateFormat.format(calendar3.getTime());
        fecha4 = dateFormat.format(calendar4.getTime());
        fecha5 = dateFormat.format(calendar5.getTime());
        fecha6 = dateFormat.format(calendar6.getTime());
        fecha7 = dateFormat.format(calendar7.getTime());*/

        btn_continuarpedido = root_view.findViewById(R.id.bt_continuarPedido);
        btn_continuarpedido.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(final View v) {
                if (edtrecojo.getText().toString().trim().equals("Recojo en tienda")){
                    direccion = "Santiago de Surco, Lima";
                }else {
                    direccion = edtdireccion.getText().toString();
                }
                if (edtrecojo.getText().toString().trim().equals("Escoger")){
                    Toast.makeText(v.getContext(), "Escoga otra forma de entrega", Toast.LENGTH_SHORT).show();
                }else {
                    if (edtpago.getText().toString().trim().equals("--Seleccionar--")){
                        Toast.makeText(v.getContext(), "Escoga una forma de pago", Toast.LENGTH_SHORT).show();
                    }else {
                        if (edtfechapedido.getText().toString().trim().equals("Seleccione la fecha")){
                            Toast.makeText(v.getContext(), "Se requiere una fecha para su pedido", Toast.LENGTH_SHORT).show();
                        }else{
                            Date date = new Date();

                            //Caso 1: obtener la hora y salida por pantalla con formato:
                            DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
                            //System.out.println("Hora: "+hourFormat.format(date));
                            //Caso 2: obtener la fecha y salida por pantalla con formato:
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            //System.out.println("Fecha: "+dateFormat.format(date));
                            DateFormat hourdateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                            currentTime = hourdateFormat.format(date);

                            Double total = Double.parseDouble(txttotal.getText().toString());
                            String pago = edtpago.getText().toString();
                            String recojo = edtrecojo.getText().toString();

                            String fecha_ped = edtfechapedido.getText().toString();
                            SimpleDateFormat format = new SimpleDateFormat("d MMM yyyy");
                            try {
                                Date date1 = format.parse(fecha_ped);
                                submitDate = hourdateFormat.format(date1);
                            }catch (ParseException e){
                                e.printStackTrace();
                            }

                            /*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm:ss");
                            LocalDate dateTime = LocalDate.parse(fecha_ped, formatter);
                            submitDate = hourdateFormat.format(dateTime);*/

                            /*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
                            LocalDate dateTime = LocalDate.parse(fecha_ped, formatter);

                            submitDate = dateFormat.format(dateTime);*/
                            /*try {
                                Date date2 = dateFormat.parse(fecha_ped);
                                submitDate = hourdateFormat.format(date2);

                            }catch (ParseException e){
                                e.printStackTrace();
                            }*/

                            Double vuelto;
                            Integer digitos;
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
                            if (edtDigitoTarjeta.getText().toString().isEmpty()){
                                digitos = 0;
                            }else {
                                digitos = Integer.parseInt(edtDigitoTarjeta.getText().toString());
                            }
                            if (edtMontopago.getText().toString().isEmpty()){
                                vuelto = 0.0;
                            }else {
                                vuelto=Double.parseDouble(edtMontopago.getText().toString()) - total;
                            }

                            ApiService service = ApiServiceGenerator.createService(ApiService.class);
                            Call<ResponseMessage> call = service.crearPedido(currentTime, total, pago,recojo,direccion,submitDate,encargado,celular,vuelto,digitos);
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

                            });
                        }


                    }
                }
            }
        });

        initialize();
        return root_view;
    }

    public void initialize(){
        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<List<Cliente>> call=service.getClientes();
        call.enqueue(new Callback<List<Cliente>>() {
            @Override
            public void onResponse(Call<List<Cliente>> call, Response<List<Cliente>> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        List<Cliente> clientes = response.body();
                        Log.d(TAG, "pedidos: " + clientes);

                        for (int i = 0; i<clientes.size();i++){
                            edtdireccion.setText(clientes.get(i).getDireccion());
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
            public void onFailure(Call<List<Cliente>> call, Throwable t) {
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

    private void showRecojoDialog(final View v) {
        final String[] array = new String[]{
                "Delivery", "Recojo en tienda"
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Tipo de entrega");
        builder.setSingleChoiceItems(array, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ((EditText) v).setText(array[i]);
                if (array[i]== "Delivery"){
                    edtdireccion.setVisibility(View.VISIBLE);
                    txtDireccionPedido.setVisibility(View.VISIBLE);
                }else if (array[i]=="Recojo en tienda"){
                    edtdireccion.setVisibility(View.GONE);
                    txtDireccionPedido.setVisibility(View.GONE);
                }
                dialogInterface.dismiss();
            }
        });

        /*builder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    edtdireccion.setVisibility(View.VISIBLE);
                }else {
                    edtdireccion.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                edtdireccion.setVisibility(View.GONE);
            }
        });*/
        builder.show();
    }
    /*private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edtfechapedido.setText(sdf.format(myCalendar.getTime()));
    }*/
    private void dialogDatePickerLight(final EditText bt) {
        Calendar cur_calender = Calendar.getInstance();
        DatePickerDialog datePicker = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "d MMM yyyy"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        //String date_ship_millis = DateFormat.getDateInstance().format(calendar.getTime());
                        ((TextView) root_view.findViewById(R.id.edtFechaPedido)).setText(sdf.format(calendar.getTime()));
                    }
                },
                cur_calender.get(Calendar.YEAR),
                cur_calender.get(Calendar.MONTH),
                cur_calender.get(Calendar.DAY_OF_MONTH)
        );
        //set dark light
        datePicker.setThemeDark(false);
        datePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
        datePicker.setMinDate(cur_calender);
        datePicker.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }

    private void showPagoDialog(final View v) {
        final String[] array = new String[]{
                "Contado","Crédito"
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Tipo de pago");
        builder.setSingleChoiceItems(array, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ((EditText) v).setText(array[i]);
                /*if (array[i]=="Efectivo"){
                    txtMontoPagoPedido.setVisibility(View.VISIBLE);
                    edtMontopago.setVisibility(View.VISIBLE);
                }else {
                    txtMontoPagoPedido.setVisibility(View.GONE);
                    edtMontopago.setVisibility(View.GONE);
                }
                if (array[i]=="Depósito en cuenta"){
                    txtBanco.setVisibility(View.VISIBLE);
                    txtTitulartarjeta.setVisibility(View.VISIBLE);
                    txtnombretitular.setVisibility(View.VISIBLE);
                    txtCC.setVisibility(View.VISIBLE);
                    txtCCNumero.setVisibility(View.VISIBLE);
                    txtCCI.setVisibility(View.VISIBLE);
                    txtCCINumero.setVisibility(View.VISIBLE);
                }else{
                    txtBanco.setVisibility(View.GONE);
                    txtTitulartarjeta.setVisibility(View.GONE);
                    txtnombretitular.setVisibility(View.GONE);
                    txtCC.setVisibility(View.GONE);
                    txtCCNumero.setVisibility(View.GONE);
                    txtCCI.setVisibility(View.GONE);
                    txtCCINumero.setVisibility(View.GONE);
                }
                if (array[i]=="Tarjeta Visa"){
                    txtDigitoTarjetaPedido.setVisibility(View.VISIBLE);
                    edtDigitoTarjeta.setVisibility(View.VISIBLE);
                }else {
                    txtDigitoTarjetaPedido.setVisibility(View.GONE);
                    edtDigitoTarjeta.setVisibility(View.GONE);
                }*/
                /*if (array[i]=="Tarjeta MasterCard"){
                    txtDigitoTarjetaPedido.setVisibility(View.VISIBLE);
                    edtDigitoTarjeta.setVisibility(View.VISIBLE);
                }else {
                    txtDigitoTarjetaPedido.setVisibility(View.GONE);
                    edtDigitoTarjeta.setVisibility(View.GONE);
                }
                if (array[i]=="Tarjeta American Express"){
                    txtDigitoTarjetaPedido.setVisibility(View.VISIBLE);
                    edtDigitoTarjeta.setVisibility(View.VISIBLE);
                }else {
                    txtDigitoTarjetaPedido.setVisibility(View.GONE);
                    edtDigitoTarjeta.setVisibility(View.GONE);
                }
                if (array[i]=="Tarjeta Débito"){
                    txtDigitoTarjetaPedido.setVisibility(View.VISIBLE);
                    edtDigitoTarjeta.setVisibility(View.VISIBLE);
                }else {
                    txtDigitoTarjetaPedido.setVisibility(View.GONE);
                    edtDigitoTarjeta.setVisibility(View.GONE);
                }*/
                if (array[i]=="Contado"){
                    edtContado.setVisibility(View.VISIBLE);
                }else{
                    edtContado.setVisibility(View.GONE);
                }
                if (array[i]=="Crédito"){
                    edtCredito.setVisibility(View.VISIBLE);
                    txtMontoPagoPedido.setVisibility(View.GONE);
                    edtMontopago.setVisibility(View.GONE);
                }else {
                    edtCredito.setVisibility(View.GONE);
                }

                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void showContadoDialog(final View v) {
        final String[] array = new String[]{
                "Efectivo","Transferencia de fondos","Tarjeta dédito","Tarjeta Crédito", "Depósito en cuenta","Otros medios de pago," +
                "Cheques","Giro","Transferencias0"
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Sección contado");
        builder.setSingleChoiceItems(array, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ((EditText) v).setText(array[i]);
                /*if (array[i]=="Efectivo"){
                    txtMontoPagoPedido.setVisibility(View.VISIBLE);
                    edtMontopago.setVisibility(View.VISIBLE);
                }else {
                    txtMontoPagoPedido.setVisibility(View.GONE);
                    edtMontopago.setVisibility(View.GONE);
                }
                if (array[i]=="Depósito en cuenta"){
                    txtBanco.setVisibility(View.VISIBLE);
                    txtTitulartarjeta.setVisibility(View.VISIBLE);
                    txtnombretitular.setVisibility(View.VISIBLE);
                    txtCC.setVisibility(View.VISIBLE);
                    txtCCNumero.setVisibility(View.VISIBLE);
                    txtCCI.setVisibility(View.VISIBLE);
                    txtCCINumero.setVisibility(View.VISIBLE);
                }else{
                    txtBanco.setVisibility(View.GONE);
                    txtTitulartarjeta.setVisibility(View.GONE);
                    txtnombretitular.setVisibility(View.GONE);
                    txtCC.setVisibility(View.GONE);
                    txtCCNumero.setVisibility(View.GONE);
                    txtCCI.setVisibility(View.GONE);
                    txtCCINumero.setVisibility(View.GONE);
                }
                if (array[i]=="Tarjeta Visa"){
                    txtDigitoTarjetaPedido.setVisibility(View.VISIBLE);
                    edtDigitoTarjeta.setVisibility(View.VISIBLE);
                }else {
                    txtDigitoTarjetaPedido.setVisibility(View.GONE);
                    edtDigitoTarjeta.setVisibility(View.GONE);
                }*/
                /*if (array[i]=="Tarjeta MasterCard"){
                    txtDigitoTarjetaPedido.setVisibility(View.VISIBLE);
                    edtDigitoTarjeta.setVisibility(View.VISIBLE);
                }else {
                    txtDigitoTarjetaPedido.setVisibility(View.GONE);
                    edtDigitoTarjeta.setVisibility(View.GONE);
                }
                if (array[i]=="Tarjeta American Express"){
                    txtDigitoTarjetaPedido.setVisibility(View.VISIBLE);
                    edtDigitoTarjeta.setVisibility(View.VISIBLE);
                }else {
                    txtDigitoTarjetaPedido.setVisibility(View.GONE);
                    edtDigitoTarjeta.setVisibility(View.GONE);
                }
                if (array[i]=="Tarjeta Débito"){
                    txtDigitoTarjetaPedido.setVisibility(View.VISIBLE);
                    edtDigitoTarjeta.setVisibility(View.VISIBLE);
                }else {
                    txtDigitoTarjetaPedido.setVisibility(View.GONE);
                    edtDigitoTarjeta.setVisibility(View.GONE);
                }*/
                if (array[i]=="Efectivo"){
                    txtMontoPagoPedido.setVisibility(View.VISIBLE);
                    edtMontopago.setVisibility(View.VISIBLE);
                }else {
                    txtMontoPagoPedido.setVisibility(View.GONE);
                    edtMontopago.setVisibility(View.GONE);
                }

                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void showCreditoDialog(final View v) {
        final String[] array = new String[]{
                "7 días","15 días","30 días"
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Sección crédito");
        builder.setSingleChoiceItems(array, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ((EditText) v).setText(array[i]);
                /*if (array[i]=="Efectivo"){
                    txtMontoPagoPedido.setVisibility(View.VISIBLE);
                    edtMontopago.setVisibility(View.VISIBLE);
                }else {
                    txtMontoPagoPedido.setVisibility(View.GONE);
                    edtMontopago.setVisibility(View.GONE);
                }
                if (array[i]=="Depósito en cuenta"){
                    txtBanco.setVisibility(View.VISIBLE);
                    txtTitulartarjeta.setVisibility(View.VISIBLE);
                    txtnombretitular.setVisibility(View.VISIBLE);
                    txtCC.setVisibility(View.VISIBLE);
                    txtCCNumero.setVisibility(View.VISIBLE);
                    txtCCI.setVisibility(View.VISIBLE);
                    txtCCINumero.setVisibility(View.VISIBLE);
                }else{
                    txtBanco.setVisibility(View.GONE);
                    txtTitulartarjeta.setVisibility(View.GONE);
                    txtnombretitular.setVisibility(View.GONE);
                    txtCC.setVisibility(View.GONE);
                    txtCCNumero.setVisibility(View.GONE);
                    txtCCI.setVisibility(View.GONE);
                    txtCCINumero.setVisibility(View.GONE);
                }
                if (array[i]=="Tarjeta Visa"){
                    txtDigitoTarjetaPedido.setVisibility(View.VISIBLE);
                    edtDigitoTarjeta.setVisibility(View.VISIBLE);
                }else {
                    txtDigitoTarjetaPedido.setVisibility(View.GONE);
                    edtDigitoTarjeta.setVisibility(View.GONE);
                }*/
                /*if (array[i]=="Tarjeta MasterCard"){
                    txtDigitoTarjetaPedido.setVisibility(View.VISIBLE);
                    edtDigitoTarjeta.setVisibility(View.VISIBLE);
                }else {
                    txtDigitoTarjetaPedido.setVisibility(View.GONE);
                    edtDigitoTarjeta.setVisibility(View.GONE);
                }
                if (array[i]=="Tarjeta American Express"){
                    txtDigitoTarjetaPedido.setVisibility(View.VISIBLE);
                    edtDigitoTarjeta.setVisibility(View.VISIBLE);
                }else {
                    txtDigitoTarjetaPedido.setVisibility(View.GONE);
                    edtDigitoTarjeta.setVisibility(View.GONE);
                }
                if (array[i]=="Tarjeta Débito"){
                    txtDigitoTarjetaPedido.setVisibility(View.VISIBLE);
                    edtDigitoTarjeta.setVisibility(View.VISIBLE);
                }else {
                    txtDigitoTarjetaPedido.setVisibility(View.GONE);
                    edtDigitoTarjeta.setVisibility(View.GONE);
                }*/

                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}
