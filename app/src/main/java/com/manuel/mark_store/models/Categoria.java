package com.manuel.mark_store.models;

public class Categoria {
    private Integer idCategoria;
    private String nombreCa;
    private String descripcion;
    private Usuario usuario;
    private Empresa empresa;
    private String foto;

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCa() {
        return nombreCa;
    }

    public void setNombreCa(String nombreCa) {
        this.nombreCa = nombreCa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "idCategoria=" + idCategoria +
                ", nombreCa='" + nombreCa + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", usuario=" + usuario +
                ", empresa=" + empresa +
                ", foto='" + foto + '\'' +
                '}';
    }
}
