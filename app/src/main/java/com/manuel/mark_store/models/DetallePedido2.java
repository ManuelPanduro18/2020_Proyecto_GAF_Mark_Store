package com.manuel.mark_store.models;

public class DetallePedido2 {
    private Integer id;
    private Integer cantidad;
    private Producto producto;
    private Pedido pedido;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public String toString() {
        return "DetallePedido2{" +
                "id=" + id +
                ", cantidad=" + cantidad +
                ", producto=" + producto +
                ", pedido=" + pedido +
                '}';
    }
}
