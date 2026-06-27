package Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Seccion implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombreSeccion;
    private List<Pelicula> peliculas;

    public Seccion(String nombreSeccion) {
        this.nombreSeccion = nombreSeccion;
        this.peliculas = new ArrayList<>();
    }

    public void agregarPelicula(Pelicula pelicula) {
        this.peliculas.add(pelicula);
    }

    public String getNombreSeccion() {
        return nombreSeccion;
    }
    public List<Pelicula> getPeliculas() {
        return peliculas;
    }
}
