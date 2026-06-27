package Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sala implements Serializable {
    private static final long serialVersionUID = 1L;

    private int numero;
    private int capacidad;
    private String tipoTecnologia;
    private List<Butaca> butacas;

    public Sala(int numero, int capacidad, String tipoTecnologia) {
        this.numero = numero;
        this.capacidad = capacidad;
        this.tipoTecnologia = tipoTecnologia;
        this.butacas = new ArrayList<Butaca>();
        generarButacas();
    }

    private void generarButacas() {
        char fila = 'A';
        int asientosCreados = 0;
        while (asientosCreados < capacidad) {
            for (int num = 1; num <= 10 && asientosCreados <capacidad; num++) {
                butacas.add(new Butaca(fila, num));
                asientosCreados++;
            }
            fila++;
        }
    }
    public int getNumero() {return numero;}
    public int getCapacidad() {return capacidad;}
    public String getTipoTecnologia() {return tipoTecnologia;}
    public List<Butaca> getButacas() {return butacas;}
}
