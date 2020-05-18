package com.manuel.mark_store.models;

public class Repartidor {
    private Integer idRepartidor;
    private String nombreRe;
    private String apellidos;
    private Integer celular;
    private Integer dni;
    private Integer edad;
    private HistoriaClinica hcli;

    public Integer getIdRepartidor() {
        return idRepartidor;
    }

    public void setIdRepartidor(Integer idRepartidor) {
        this.idRepartidor = idRepartidor;
    }

    public String getNombreRe() {
        return nombreRe;
    }

    public void setNombreRe(String nombreRe) {
        this.nombreRe = nombreRe;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Integer getCelular() {
        return celular;
    }

    public void setCelular(Integer celular) {
        this.celular = celular;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public HistoriaClinica getHcli() {
        return hcli;
    }

    public void setHcli(HistoriaClinica hcli) {
        this.hcli = hcli;
    }

    @Override
    public String toString() {
        return "Repartidor{" +
                "idRepartidor=" + idRepartidor +
                ", nombreRe='" + nombreRe + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", celular=" + celular +
                ", dni=" + dni +
                ", edad=" + edad +
                ", hcli=" + hcli +
                '}';
    }
}
