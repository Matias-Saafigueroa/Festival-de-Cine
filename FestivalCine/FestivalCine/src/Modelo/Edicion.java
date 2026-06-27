package Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Edicion implements Serializable {
    private static final long serialVersionUID = 1L;

    private int numeroEdicion;
    private int anio;
    private String ciudad;
    private List<Seccion> secciones;
    private List<Sede>  sedes ;

    public Edicion(int numeroEdicion, int anio, String ciudad) {
        this.numeroEdicion = numeroEdicion;
        this.anio = anio;
        this.ciudad = ciudad;
        this.secciones = new ArrayList<>();
        this.sedes = new ArrayList<>();
    }
    public void agregarSeccion(Seccion seccion){
        this.secciones.add(seccion);
    }
    public void agregarSede(Sede sede){
        this.sedes.add(sede);
    }
    public int getNumeroEdicion() {
        return numeroEdicion;
    }
    public int getAnio() {
        return anio;
    }
    public String getCiudad() {
        return ciudad;
    }
    public List<Seccion> getSecciones() {
        return secciones;
    }
    public List<Sede> getSedes() {
        return sedes;
    }
}
