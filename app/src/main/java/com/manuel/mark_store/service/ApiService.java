package com.manuel.mark_store.service;

import com.manuel.mark_store.models.Carrito;
import com.manuel.mark_store.models.Categoria;
import com.manuel.mark_store.models.Cliente;
import com.manuel.mark_store.models.DetallePedido2;
import com.manuel.mark_store.models.Empresa;
import com.manuel.mark_store.models.Pedido;
import com.manuel.mark_store.models.Producto;
import com.manuel.mark_store.models.ResponseMessage;
import com.manuel.mark_store.models.TablaCarrito;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    String API_BASE_URL = "http://192.168.137.10:8080";

    @GET("/empresas")
    Call<List<Empresa>> getEmpresas();

    @GET("/empresas/{idEmpresa}")
    Call<List<Empresa>>getEmpresas2(@Path("idEmpresa")Integer idEmpresa);

    @GET("/categoriaE/{empresa_id}")
    Call<List<Categoria>>showCategoria(@Path("empresa_id") Integer empresa_id);

    @GET("/productosC/{id_categoria}")
    Call<List<Producto>>showProductoC(@Path("id_categoria") Integer id_categoria);

    @GET("/productosE/{id_empresa}")
    Call<List<Producto>>showProductoE(@Path("id_empresa") Integer id_empresa);

    @GET("/productos/{idproductos}")
    Call<Producto>ShowProductos(@Path("idproductos") Integer idproductos);

    @FormUrlEncoded
    @POST("/carrito")
    Call<ResponseMessage>crearProducto(@Field("nuevaCantidad")Integer nuevaCantidad,
                                       @Field("idproductos")Integer idproductos,
                                       @Field("nuevoValor") Double nuevoValor);

    @GET("/carrito/list")
    Call<List<Carrito>>getCarritos();

    @GET("/pedido/lista")
    Call<List<Pedido>>getPedidos();

    @GET("/cliente/lista")
    Call<List<Cliente>>getClientes();

    @GET("/detalle/lista")
    Call<List<DetallePedido2>>getDetalles();

    @DELETE("/carrito/{idtablaC}")
    Call<ResponseMessage>destroyCarrito(@Path("idtablaC")Integer idtablaC);

    @FormUrlEncoded
    @POST("/pedido")
    Call<ResponseMessage>crearPedido(@Field("fecha_ped")String fecha_ped,
                                      @Field("total")Double total,
                                      @Field("tipo_pago")String tipo_pago,
                                      @Field("tipo_pedido")String tipo_pedido,
                                      @Field("direccion")String direccion,
                                      @Field("fechaEntrega")String fechaEntrega,
                                      @Field("encargado")String encargado,
                                      @Field("telf_encarg")Integer telf_encarg,
                                      @Field("vuelto")Double vuelto,
                                      @Field("digitos")Integer digitos,
                                     @Field("medio_pago")String medio_pago);

    @FormUrlEncoded
    @POST("/detalle")
    Call<ResponseMessage>crearDetalle(@Field("cantidad")Integer cantidad,
                                      @Field("id_producto")Integer id_producto,
                                      @Field("id_pedido")Integer id_pedido);



}
