package Modelo;

import java.io.Serializable;

public class Entrada implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idEntrada;
    private Funcion funcion;
    private Butaca butaca;
    private Espectador espectador;
    private double precioBase;

    public Entrada(int idEntrada, Funcion funcion, Butaca butaca, Espectador espectador, double precioBase) {
        this.idEntrada = idEntrada;
        this.funcion = funcion;
        this.butaca = butaca;
        this.espectador = espectador;
        this.precioBase = precioBase;
    }

    public double calcularPrecioFinal() {
        return this.precioBase;
    }

    // Getters y Setters
    public int getIdEntrada()
        { return idEntrada; }
    public Funcion getFuncion()
        { return funcion; }
    public Butaca getButaca()
        { return butaca; }
    public Espectador getEspectador()
        { return espectador; }
    public double getPrecioBase()
        { return precioBase; }
}
