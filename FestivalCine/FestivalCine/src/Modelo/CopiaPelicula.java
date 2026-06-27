package Modelo;

import java.io.Serializable;

public class CopiaPelicula implements Serializable {
    private static final long serialVersionUID = 1L;

    private String idCopia;
    private Pelicula pelicula;
    private String formato;
    private String idioma;
    private boolean esSubtitulo;
    private boolean disponible;

    public CopiaPelicula(String idCopia, Pelicula pelicula, String formato, String idioma, boolean esSubtitulo, boolean disponible) {
        this.idCopia = idCopia;
        this.pelicula = pelicula;
        this.formato = formato;
        this.idioma = idioma;
        this.esSubtitulo = esSubtitulo;
        this.disponible = true;
    }
    //Getters and Setters
    public String getIdCopia() {
        return idCopia;
    }
    public Pelicula getPelicula() {
        return pelicula;
    }
    public String getFormato() {
        return formato;
    }
    public String getIdioma() {
        return idioma;
    }
    public boolean isEsSubtitulo() {
        return esSubtitulo;
    }
    public boolean isDisponible() {
        return disponible;
    }
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}
