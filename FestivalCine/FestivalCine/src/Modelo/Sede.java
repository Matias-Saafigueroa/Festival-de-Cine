package Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sede implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idSede;
    private String nombre;
    private String direccion;
    private List<Sala> salas;


    public Sede(int idSede,  String nombre, String direccion) {
        this.idSede = idSede;
        this.nombre = nombre;
        this.direccion = direccion;
        this.salas = new ArrayList<Sala>();
    }
    public void agregarSala(Sala sala){
        this.salas.add(sala);
    }
    //Getters and Setters

    public int getIdSede() {
        return idSede;
    }

    public void setIdSede(int idSede) {
        this.idSede = idSede;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Sala> getSalas() {
        return salas;
    }

    public void setSalas(List<Sala> salas) {
        this.salas = salas;
    }
}
