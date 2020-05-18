package com.manuel.mark_store.models;

import java.util.Date;

public class Pedido {
    private Integer idPedido;
    private String fecha_ped;
    private String moneda;
    private Cliente cliente;
    private Double total;
    private String estado_ped;
    private String tipo_pago;
    private String direccion;
    private String fechaEntrega;
    private Repartidor repartidor;
    private String encargado;
    private Integer telf_encarg;
    private String tipo_pedido;
    private Double vuelto;
    private Integer digitos;

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public String getFecha_ped() {
        return fecha_ped;
    }

    public void setFecha_ped(String fecha_ped) {
        this.fecha_ped = fecha_ped;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getEstado_ped() {
        return estado_ped;
    }

    public void setEstado_ped(String estado_ped) {
        this.estado_ped = estado_ped;
    }

    public String getTipo_pago() {
        return tipo_pago;
    }

    public void setTipo_pago(String tipo_pago) {
        this.tipo_pago = tipo_pago;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Repartidor getRepartidor() {
        return repartidor;
    }

    public void setRepartidor(Repartidor repartidor) {
        this.repartidor = repartidor;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public Integer getTelf_encarg() {
        return telf_encarg;
    }

    public void setTelf_encarg(Integer telf_encarg) {
        this.telf_encarg = telf_encarg;
    }

    public String getTipo_pedido() {
        return tipo_pedido;
    }

    public void setTipo_pedido(String tipo_pedido) {
        this.tipo_pedido = tipo_pedido;
    }

    public Double getVuelto() {
        return vuelto;
    }

    public void setVuelto(Double vuelto) {
        this.vuelto = vuelto;
    }

    public Integer getDigitos() {
        return digitos;
    }

    public void setDigitos(Integer digitos) {
        this.digitos = digitos;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido +
                ", fecha_ped='" + fecha_ped + '\'' +
                ", moneda='" + moneda + '\'' +
                ", cliente=" + cliente +
                ", total=" + total +
                ", estado_ped='" + estado_ped + '\'' +
                ", tipo_pago='" + tipo_pago + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fechaEntrega='" + fechaEntrega + '\'' +
                ", repartidor=" + repartidor +
                ", encargado='" + encargado + '\'' +
                ", telf_encarg=" + telf_encarg +
                ", tipo_pedido='" + tipo_pedido + '\'' +
                ", vuelto=" + vuelto +
                ", digitos=" + digitos +
                '}';
    }
}
