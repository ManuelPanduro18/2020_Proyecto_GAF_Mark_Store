package com.manuel.mark_store.models;

public class HistoriaClinica {
    private Integer idHistoriaClinica;
    private String temperatura;

    public Integer getIdHistoriaClinica() {
        return idHistoriaClinica;
    }

    public void setIdHistoriaClinica(Integer idHistoriaClinica) {
        this.idHistoriaClinica = idHistoriaClinica;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    @Override
    public String toString() {
        return "HistoriaClinica{" +
                "idHistoriaClinica=" + idHistoriaClinica +
                ", temperatura='" + temperatura + '\'' +
                '}';
    }
}
