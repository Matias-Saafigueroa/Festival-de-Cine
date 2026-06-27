package servicios;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import Excepciones.ButacaOcupadaException;
import Excepciones.FormatoIncompatibleException;
import Excepciones.FuncionSuperpuestaException;
import Modelo.*;
import Persistencia.PersistenciaManager;

public class FestivalSistemasService {
    private static FestivalSistemasService instancia;

    private List<Edicion> ediciones;
    private List<Pelicula> peliculas;
    private List<Director> directores;
    private List<Sede> sedes;
    private List<Actor> actores;
    private List<Espectador> espectadores;
    private List<Funcion> funciones;

    private static final String Path_EDICIONES = "ediciones.dat";
    private static final String Path_PELICULAS = "peliculas.dat";
    private static final String Path_DIRECTORES = "directores.dat";
    private static final String Path_SEDES = "sedes.dat";
    private static final String Path_ACTORES = "actores.dat";
    private static final String Path_ESPECTADORES = "espectadores.dat";
    private static final String Path_FUNCIONES = "funciones.dat";

    private FestivalSistemasService() {
        this.ediciones = new ArrayList<>();
        this.peliculas = new ArrayList<>();
        this.directores = new ArrayList<>();
        this.sedes = new ArrayList<>();
        this.actores = new ArrayList<>();
        this.espectadores = new ArrayList<>();
        this.funciones = new ArrayList<>();

        cargarDatos();
    }

    public static FestivalSistemasService getInstancia() {
        if (instancia == null) {
            instancia = new FestivalSistemasService();
        }
        return instancia;
    }

    public void registrarEnEdicion(int numero, int anio, String ciudad) {
        Edicion nueva = new Edicion(numero, anio, ciudad);
        ediciones.add(nueva);
        guardarDatos();
    }

    public void crearSeccionEnEdicion(int numEdicion, String nombreSeccion) {
        Edicion ed = buscarEdicion(numEdicion);
        if (ed != null) {
            ed.agregarSeccion(new Seccion(nombreSeccion));
            guardarDatos();
        }
    }

    public void registrarSede(int idSede, String nombre, String direccion) {
        Sede nueva = new Sede(idSede, nombre, direccion);
        sedes.add(nueva);
        guardarDatos();
    }

    public void registrarSalaEnSede(int idSede, int numero, int capacidad, String tecnologia) {
        Sede s = buscarSede(idSede);
        if (s != null) {
            s.agregarSala(new Sala(numero, capacidad, tecnologia));
            guardarDatos();
        }
    }

    public void registrarDirector(String nombre, String apellido, String documento) {
        directores.add(new Director(nombre, apellido, documento));
        guardarDatos();
    }

    public void registrarPelicula(int id, String titulo, int duracion, String genero, String docDirector) {
        Director dir = buscarDirector(docDirector);
        if (dir != null) {
            peliculas.add(new Pelicula(id, titulo, duracion, genero, dir));
            guardarDatos();
        }
    }

    public void asociarActorAPelicula(int idPeli, String docActor) {
        Pelicula p = buscarPelicula(idPeli);
        Actor a = buscarActor(docActor);
        if (p != null && a != null) {
            p.agregarActor(a);
            guardarDatos();
        }
    }

    public void asociarPeliculaASeccion(int numEdicion, String nombreSeccion, int idPeli) {
        Edicion ed = buscarEdicion(numEdicion);
        Pelicula p = buscarPelicula(idPeli);
        if (ed != null && p != null) {
            for (Seccion sec : ed.getSecciones()) {
                if (sec.getNombreSeccion().equalsIgnoreCase(nombreSeccion)) {
                    sec.agregarPelicula(p);
                    guardarDatos();
                    break;
                }
            }
        }
    }

    public void progrmarFuncion(int idFuncion, Date fecha, String horario, int idSede, int numSala, CopiaPelicula copia)
            throws FuncionSuperpuestaException, FormatoIncompatibleException {
        Sede sede = buscarSede(idSede);
        if (sede == null) return;

        Sala sala = null;
        for (Sala s : sede.getSalas()) {
            if (s.getNumero() == numSala) {
                sala = s;
                break;
            }
        }
        if (sala == null) return;

        for (Funcion f : funciones) {
            if (f.getSala().getNumero() == numSala && f.getFecha().equals(fecha) && f.getHorario().equals(horario)) {
                throw new FuncionSuperpuestaException("Error: Ya existe una funcion programada en la Sala "+ numSala + " el " + fecha + " a las " + horario);
            }
        }

        if (!sala.getTipoTecnologia().equalsIgnoreCase(copia.getFormato())) {
            throw new FormatoIncompatibleException("Error de logistica: No se puede reproducir una copia formato " + copia.getFormato() +
                    " en una sala con tecnologia " + sala.getTipoTecnologia());
        }

        Funcion nuevaFuncion = new Funcion(idFuncion, fecha, horario, sala, copia);
        funciones.add(nuevaFuncion);
        guardarDatos();
    }

    public void registrarEspectador(String nombre, String apellido, String documento) {
        espectadores.add(new Espectador(nombre, apellido, documento));
        guardarDatos();
    }

    public Entrada venderEntrada(int idEntrada, int idFuncion, char fila, int numero, String docEspectador, double precioBase)
            throws ButacaOcupadaException {

        Funcion func = buscarFuncion(idFuncion);
        Espectador esp = buscarEspectador(docEspectador);

        if (func == null || esp == null) {
            return null;
        }

        if (!func.verificarDisponibilidad(fila, numero)) {
            throw new ButacaOcupadaException("La butaca " + fila + numero + " ya esta ocupada para esta funcion.");
        }

        Butaca b = func.obtenerButaca(fila, numero);
        if (b == null) {
            return null;
        }

        // Genera la entrada
        Entrada nuevaEntrada = new Entrada(idEntrada, func, b, esp, precioBase);
        func.ocuparButaca(b, nuevaEntrada);

        guardarDatos();
        return nuevaEntrada;
    }

    public void registrarJurado(String nombre, String apellido, String documento){
        actores.add(new Jurado(nombre, apellido, documento));
        guardarDatos();
    }

    public void registrarEvalucion(String docJurado, int idPeli, int puntaje, String comentario){
        Pelicula p = buscarPelicula(idPeli);
        Jurado j = new Jurado("Jurado", "Anonimo", docJurado);

        if (p != null && puntaje >= 1 && puntaje <= 10) {
            Evaluacion eval = new Evaluacion(j, puntaje, comentario);
            p.agregarEvaluacion(eval);
            guardarDatos();
        }
    }

    public Pelicula determinarGanadorSeccion(int numEdicion, String nombreSeccion) {
        Edicion ed = buscarEdicion(numEdicion);
        if (ed == null) return null;

        Seccion seccionBuscada = null;
        for (Seccion seccion : ed.getSecciones()) {
            if (seccion.getNombreSeccion().equalsIgnoreCase(nombreSeccion)) {
                seccionBuscada = seccion;
                break;
            }
        }
        if (seccionBuscada == null || seccionBuscada.getPeliculas().isEmpty()) return null;

        Pelicula ganadora = seccionBuscada.getPeliculas().get(0);
        for (Pelicula p : seccionBuscada.getPeliculas()) {
            if (p.calcularPuntajePromedio() > ganadora.calcularPuntajePromedio()) {
                ganadora = p;
            }
        }
        return ganadora;
    }

    public Edicion buscarEdicion(int NUM) {
        for (Edicion e : ediciones) if (e.getNumeroEdicion() == NUM) return e;
        return null;
    }

    public Sede buscarSede(int id) {
        for (Sede s : sedes) if (s.getIdSede() == id) return s;
        return null;
    }

    public Director buscarDirector(String doc) {
        for (Director d : directores) if (d.getDocumento().equals(doc)) return d;
        return null;
    }

    public Actor buscarActor(String doc) {
        for (Actor a : actores) if (a.getDocumento().equals(doc)) return a;
        return null;
    }

    public Pelicula buscarPelicula(int id) {
        for (Pelicula p : peliculas) if (p.getIdPelicula() == id) return p;
        return null;
    }

    public Funcion buscarFuncion(int id) {
        for (Funcion f : funciones) if (f.getIdFuncion() == id) return f;
        return null;
    }

    public Espectador buscarEspectador(String doc) {
        for (Espectador e : espectadores) if (e.getDocumento().equals(doc)) return e;
        return null;
    }

    public List<Funcion> getFunciones() {
        return funciones;
    }
    public List<Pelicula> getPeliculas() {
        return peliculas;
    }
    public List<Sede> getSedes() {
        return sedes;
    }
    public List<Edicion> getEdiciones() {
        return ediciones;
    }

    private void guardarDatos() {
        try {
            PersistenciaManager.guardar(Path_EDICIONES, ediciones);
            PersistenciaManager.guardar(Path_PELICULAS, peliculas);
            PersistenciaManager.guardar(Path_DIRECTORES, directores);
            PersistenciaManager.guardar(Path_ACTORES, actores);
            PersistenciaManager.guardar(Path_ESPECTADORES, espectadores);
            PersistenciaManager.guardar(Path_SEDES, sedes);
            PersistenciaManager.guardar(Path_FUNCIONES, funciones);
        } catch (IOException e) {
            System.err.println("Error al guardar datos de la persistencia: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void cargarDatos() {
        try {
            ediciones = (List<Edicion>) PersistenciaManager.cargar(Path_EDICIONES);
            peliculas = (List<Pelicula>) PersistenciaManager.cargar(Path_PELICULAS);
            directores = (List<Director>) PersistenciaManager.cargar(Path_DIRECTORES);
            actores = (List<Actor>) PersistenciaManager.cargar(Path_ACTORES);
            espectadores = (List<Espectador>) PersistenciaManager.cargar(Path_ESPECTADORES);
            sedes = (List<Sede>) PersistenciaManager.cargar(Path_SEDES);
            funciones = (List<Funcion>) PersistenciaManager.cargar(Path_FUNCIONES);
        } catch (FileNotFoundException e) {

        } catch (Exception e){
            System.err.println("Error al recuperar los datos guardados: " + e.getMessage());
        }
    }
}