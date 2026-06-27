package Modelo;

import java.io.Serializable;

public class Butaca implements Serializable {
    private static final long serialVersionUID = 1L;

    private char fila;
    private int numero;

    public Butaca(char fila, int numero) {
        this.fila = fila;
        this.numero = numero;
    }

    public char getFila() {return fila;}
    public int getNumero() {return numero;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Butaca butaca = (Butaca) o;
        return fila == butaca.fila && numero == butaca.numero;
    }

    @Override
    public int hashCode() {
        return 31 * fila + numero;
    }
}
