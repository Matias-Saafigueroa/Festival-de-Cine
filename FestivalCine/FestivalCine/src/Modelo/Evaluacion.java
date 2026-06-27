package Modelo;

import java.io.Serializable;

public class Evaluacion implements Serializable {
    private static final long serialVersionUID = 1L;

    private Jurado jurado;
    private int puntaje;
    private String comentario;

    public Evaluacion(Jurado jurado, int puntaje, String comentario) {
        this.jurado = jurado;
        this.puntaje = puntaje;
        this.comentario = comentario;
    }

    public Jurado getJurado() {
        return jurado;
    }
    public int getPuntaje() {
        return puntaje;
    }
    public String getComentario() {
        return comentario;
    }
}
