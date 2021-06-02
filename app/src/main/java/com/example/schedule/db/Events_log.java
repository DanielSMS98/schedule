package com.example.schedule.db;

public class Events_log {

    private int id;
    private String evento;
    private String fecha;
    private String descripcion;

    public Events_log() {    }//Constructor-vacio

    public Events_log(int id, String evento, String fecha, String descripcion) {
        this.id = id;
        this.evento = evento;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }//Constructor

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}//class
