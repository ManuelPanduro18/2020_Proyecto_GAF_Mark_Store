package com.manuel.mark_store.models;

public class Carrito {
    private Integer idtablaC;
    private Integer nuevaCantidad;
    private String estado;
    private Producto producto;
    private Double nuevoValor;
    private Integer total;

    public Integer getIdtablaC() {
        return idtablaC;
    }

    public void setIdtablaC(Integer idtablaC) {
        this.idtablaC = idtablaC;
    }

    public Integer getNuevaCantidad() {
        return nuevaCantidad;
    }

    public void setNuevaCantidad(Integer nuevaCantidad) {
        this.nuevaCantidad = nuevaCantidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Double getNuevoValor() {
        return nuevoValor;
    }

    public void setNuevoValor(Double nuevoValor) {
        this.nuevoValor = nuevoValor;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Carrito{" +
                "idtablaC=" + idtablaC +
                ", nuevaCantidad=" + nuevaCantidad +
                ", estado='" + estado + '\'' +
                ", producto=" + producto +
                ", nuevoValor=" + nuevoValor +
                ", total=" + total +
                '}';
    }
}
