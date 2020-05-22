package com.manuel.mark_store.models;

import java.util.Date;

public class Empresa {
    private Integer idEmpresa;
    private String nombres;
    private String descripcion;
    private String logo;
    private String ruc;
    private String telefono;
    private String telefono2;
    private String direccion;
    private Date fecha_cre;
    private String provincia;
    private String distrito;
    private String urbanizacion;
    private String referencias;
    private String banco;
    private String titular_cuenta;
    private String num_tarjeta;
    private String cc;
    private String cci;
    private Integer horaIni;
    private Integer horaFin;
    public Integer getHoraIni() {
        return horaIni;
    }

    public void setHoraIni(Integer horaIni) {
        this.horaIni = horaIni;
    }

    public Integer getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Integer horaFin) {
        this.horaFin = horaFin;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getUrbanizacion() {
        return urbanizacion;
    }

    public void setUrbanizacion(String urbanizacion) {
        this.urbanizacion = urbanizacion;
    }

    public String getReferencias() {
        return referencias;
    }

    public void setReferencias(String referencias) {
        this.referencias = referencias;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getTitular_cuenta() {
        return titular_cuenta;
    }

    public void setTitular_cuenta(String titular_cuenta) {
        this.titular_cuenta = titular_cuenta;
    }

    public String getNum_tarjeta() {
        return num_tarjeta;
    }

    public void setNum_tarjeta(String num_tarjeta) {
        this.num_tarjeta = num_tarjeta;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getCci() {
        return cci;
    }

    public void setCci(String cci) {
        this.cci = cci;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFecha_cre() {
        return fecha_cre;
    }

    public void setFecha_cre(Date fecha_cre) {
        this.fecha_cre = fecha_cre;
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "idEmpresa=" + idEmpresa +
                ", nombres='" + nombres + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", logo='" + logo + '\'' +
                ", ruc='" + ruc + '\'' +
                ", telefono='" + telefono + '\'' +
                ", telefono2='" + telefono2 + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fecha_cre=" + fecha_cre +
                ", provincia='" + provincia + '\'' +
                ", distrito='" + distrito + '\'' +
                ", urbanizacion='" + urbanizacion + '\'' +
                ", referencias='" + referencias + '\'' +
                ", banco='" + banco + '\'' +
                ", titular_cuenta='" + titular_cuenta + '\'' +
                ", num_tarjeta='" + num_tarjeta + '\'' +
                ", cc='" + cc + '\'' +
                ", cci='" + cci + '\'' +
                '}';
    }
}
