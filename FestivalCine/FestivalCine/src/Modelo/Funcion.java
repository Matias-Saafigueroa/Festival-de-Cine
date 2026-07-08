package Modelo;

import Excepciones.ButacaOcupadaException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

public class Funcion implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idFuncion;
    private Date fecha;
    private String horario;
    private Sala sala;
    private CopiaPelicula copia;
    private List<Entrada> entradasVendidas;

    private Map<Butaca, Boolean> mapaButacasFuncion;

    public Funcion(int idFuncion, Date fecha, String horario, Sala sala, CopiaPelicula copia) {
        this.idFuncion = idFuncion;
        this.fecha = fecha;
        this.horario = horario;
        this.sala = sala;
        this.copia = copia;
        this.entradasVendidas = new ArrayList<>();
        this.mapaButacasFuncion = new HashMap<>();
        inicializarMapaButacas();
    }

    private void inicializarMapaButacas() {
        for (Butaca b : sala.getButacas()) {
            mapaButacasFuncion.put(b, false);
        }
    }

    public boolean verificarDisponibilidad(char fila, int numero) {
        for (Map.Entry<Butaca, Boolean> entry : mapaButacasFuncion.entrySet()) {
            Butaca b = entry.getKey();
            if (b.getFila() == fila && b.getNumero() == numero) {
                return !entry.getValue();
            }
        }
        return false;
    }

    public Butaca obtenerButaca(char fila, int numero) {
        for (Butaca b : mapaButacasFuncion.keySet()) {
            if (b.getFila() == fila && b.getNumero() == numero) {
                return b;
            }
        }
        return null;
    }


    public void ocuparButaca(Butaca butaca, Entrada entrada) throws ButacaOcupadaException {
        if (mapaButacasFuncion.get(butaca) == null || mapaButacasFuncion.get(butaca)) {
            throw new ButacaOcupadaException("La butaca " + butaca.getFila() + butaca.getNumero() + " ya está ocupada o no pertenece a la sala.");
        }
        mapaButacasFuncion.put(butaca, true); // Marcamos como ocupada
        entradasVendidas.add(entrada);
    }


    public double obtenerPorcentajeOcupacion() {
        if (sala.getCapacidad() == 0) return 0.0;
        return ((double) entradasVendidas.size() / sala.getCapacidad()) * 100;
    }

    public int getIdFuncion()
        { return idFuncion; }
    public Date getFecha()
        { return fecha; }
    public String getHorario()
        { return horario; }
    public Sala getSala()
        { return sala; }
    public CopiaPelicula getCopia()
        { return copia; }
    public List<Entrada> getEntradasVendidas()
        { return entradasVendidas; }
    public Map<Butaca, Boolean> getMapaButacasFuncion()
        { return mapaButacasFuncion; }

}
