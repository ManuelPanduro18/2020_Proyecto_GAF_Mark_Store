package com.manuel.mark_store.models;

public class TablaCarrito {
    private Integer idtablaC;
    private Integer nuevaCantidad;
    private String estado;
    private Integer idproductos;
    private Double nuevoValor;

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

    public Integer getIdproductos() {
        return idproductos;
    }

    public void setIdproductos(Integer idproductos) {
        this.idproductos = idproductos;
    }

    public Double getNuevoValor() {
        return nuevoValor;
    }

    public void setNuevoValor(Double nuevoValor) {
        this.nuevoValor = nuevoValor;
    }

    @Override
    public String toString() {
        return "TablaCarrito{" +
                "idtablaC=" + idtablaC +
                ", nuevaCantidad=" + nuevaCantidad +
                ", estado='" + estado + '\'' +
                ", idproductos=" + idproductos +
                ", nuevoValor=" + nuevoValor +
                '}';
    }
}
