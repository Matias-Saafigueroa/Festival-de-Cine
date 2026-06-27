package Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pelicula implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idPelicula;
    private String titulo;
    private int duracion;
    private String genero;
    private Director director;
    private List<Actor> elenco;
    private List<Evaluacion> evaluaciones;

    public Pelicula(int idPelicula, String titulo, int duracion, String genero, Director director) {
        this.idPelicula = idPelicula;
        this.titulo = titulo;
        this.duracion = duracion;
        this.genero = genero;
        this.director = director;
        this.elenco = new ArrayList<>();
        this.evaluaciones = new ArrayList<>();
    }

    public void agregarActor(Actor actor) {
        this.elenco.add(actor);
    }

    public void agregarEvaluacion(Evaluacion evaluacion) {
        this.evaluaciones.add(evaluacion);
    }

    public double calcularPuntajePromedio() {
        if (evaluaciones.isEmpty()) {
            return 0.0;
        }
        double suma = 0.0;
        for (Evaluacion e : evaluaciones){
            suma += e.getPuntaje();
        }
        return suma / evaluaciones.size();
    }
    public int getIdPelicula() {
        return idPelicula;
    }
    public String getTitulo() {
        return titulo;
    }
    public int getDuracion() {
        return duracion;
    }
    public String getGenero() {
        return genero;
    }
    public Director getDirector() {
        return director;
    }
    public List<Actor> getElenco() {
        return elenco;
    }
    public List<Evaluacion> getEvaluaciones() {
        return evaluaciones;
    }
}
