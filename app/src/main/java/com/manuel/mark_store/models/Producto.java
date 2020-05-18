package com.manuel.mark_store.models;

import java.util.Date;

public class Producto {
    private Integer idproductos;
    private String codigo;
    private String nombrePro;
    private String descripcion;
    private Integer stock;
    private Empresa empresa;
    private String marca;
    private String foto;
    private Categoria categoria;
    private Date fecha_cre;
    private Usuario usuario;
    private Double precio;
    private String estado_producto;

    public Integer getIdproductos() {
        return idproductos;
    }

    public void setIdproductos(Integer idproductos) {
        this.idproductos = idproductos;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombrePro() {
        return nombrePro;
    }

    public void setNombrePro(String nombrePro) {
        this.nombrePro = nombrePro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Date getFecha_cre() {
        return fecha_cre;
    }

    public void setFecha_cre(Date fecha_cre) {
        this.fecha_cre = fecha_cre;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getEstado_producto() {
        return estado_producto;
    }

    public void setEstado_producto(String estado_producto) {
        this.estado_producto = estado_producto;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "idproductos=" + idproductos +
                ", codigo='" + codigo + '\'' +
                ", nombrePro='" + nombrePro + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", stock=" + stock +
                ", empresa=" + empresa +
                ", marca='" + marca + '\'' +
                ", foto='" + foto + '\'' +
                ", categoria=" + categoria +
                ", fecha_cre=" + fecha_cre +
                ", usuario=" + usuario +
                ", precio=" + precio +
                ", estado_producto='" + estado_producto + '\'' +
                '}';
    }
}
