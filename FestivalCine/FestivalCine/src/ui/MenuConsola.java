package ui;

import Excepciones.ButacaOcupadaException;
import Excepciones.FormatoIncompatibleException;
import Excepciones.FuncionSuperpuestaException;
import Modelo.CopiaPelicula;
import Modelo.Entrada;
import Modelo.Pelicula;
import servicios.FestivalSistemasService;

import java.util.Date;
import java.util.Scanner;

public class MenuConsola {

    private FestivalSistemasService servicio;
    private Scanner scanner;

    public MenuConsola() {
        this.servicio = FestivalSistemasService.getInstancia();
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n=== SISTEMA DE GESTION DE FESTIVAL DE CINE ===");
            System.out.println("1. Registrar Edicion del Festival");
            System.out.println("2. Registrar Sede y Sala");
            System.out.println("3. Registrar Director, Actor y Pelicula");
            System.out.println("4. Programar Funcion");
            System.out.println("5. Registrar Espectador y Vender Entrada");
            System.out.println("6. Registrar Evaluacion de Jurado");
            System.out.println("7. Determinar Pelicula Ganadora de Seccion");
            System.out.println("0. Salir del Sistema");
            System.out.print("Selecciona una opcion: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1: menuRegistrarEdicion(); break;
                    case 2: menuInfraestructura();  break;
                    case 3: menuContenido(); break;
                    case 4: menuProgramarFuncion(); break;
                    case 5: menuVentaEntradas(); break;
                    case 6: menuEvaluacion(); break;
                    case 7: menuGanador(); break;
                    case 0: System.out.println("Saliendo y guardando datos... ¡Muchas gracias!"); break;
                    default: System.out.println("Opcion invalida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor ingrese un numero valido!");
            }
        }
    }

    private void menuRegistrarEdicion() {
        System.out.println("Numero de edicion: ");
        int num = Integer.parseInt(scanner.nextLine());
        System.out.println("AÑO: ");
        int anio = Integer.parseInt(scanner.nextLine());
        System.out.println("Ciudad Sede:");
        String ciudad = scanner.nextLine();

        servicio.registrarEnEdicion(num, anio, ciudad);
        System.out.println("Ingrese nombre de una Seccion para esta edicion: ");
        String seccion = scanner.nextLine();
        servicio.crearSeccionEnEdicion(num, seccion);

        System.out.println("Edicion y Seccion creadas con exito.");
    }

    private void menuInfraestructura() {
        System.out.println("ID de Sede: ");
        int idSede = Integer.parseInt(scanner.nextLine());
        System.out.println("Nombre de la Sede: ");
        String nombre = scanner.nextLine();
        System.out.println("Direccion: ");
        String dir = scanner.nextLine();
        servicio.registrarSede(idSede, nombre, dir);

        System.out.println("Sede registrada, ahora agreguemos una sala.");
        System.out.println("Numero de Sala: ");
        int numSala = Integer.parseInt(scanner.nextLine());
        System.out.println("Capacidad de butacas: ");
        int cap = Integer.parseInt(scanner.nextLine());
        System.out.println("Tecnologia de la sala (Regular/Imax/3D): ");
        String tec = scanner.nextLine();

        servicio.registrarSalaEnSede(idSede, numSala, cap, tec);
        System.out.println("Sala asociada correctamente a la Sede.");
    }

    private void menuContenido() {
        System.out.println("DNI del Director: ");
        String docDir = scanner.nextLine();
        System.out.println("Nombre del Director: ");
        String nomDir = scanner.nextLine();
        System.out.println("Apellido del Director: ");
        String apeDir = scanner.nextLine();
        servicio.registrarDirector(nomDir, apeDir, docDir);

        System.out.println("ID de Pelicula:");
        int idPeli = Integer.parseInt(scanner.nextLine());
        System.out.println("Titulo de la Pelicula: ");
        String titulo = scanner.nextLine();
        System.out.println("Duracion (minutos): ");
        int duracion = Integer.parseInt(scanner.nextLine());
        System.out.println("Genero: ");
        String genero = scanner.nextLine();

        servicio.registrarPelicula(idPeli, titulo, duracion, genero, docDir);
        System.out.println("Director y Pelicula registrados exitosamente.");
    }

    private void menuProgramarFuncion() {
        System.out.print("ID de la nueva Funcion: ");
        int idFunc = Integer.parseInt(scanner.nextLine());
        System.out.print("ID de la Sede: ");
        int idSede = Integer.parseInt(scanner.nextLine());
        System.out.print("Numero de Sala: ");
        int numSala = Integer.parseInt(scanner.nextLine());
        System.out.print("ID de la Pelicula a proyectar: ");
        int idPeli = Integer.parseInt(scanner.nextLine());

        Pelicula p = servicio.buscarPelicula(idPeli);
        if (p == null) {
            System.out.print("Error: La Pelicula no existe!.");
            return;
        }

        System.out.print("Formato de la Copia Pelicula disponible (Regular/Imax/3D): ");
        String formatoCopia = scanner.nextLine();

        CopiaPelicula copia = new CopiaPelicula("COP-" + idFunc, p, formatoCopia, "Español", true, true);

        System.out.print("Horario de la funcion: ");
        String horario = scanner.nextLine();

        try {
            servicio.progrmarFuncion(idFunc, new Date(), horario, idSede, numSala, copia);
            System.out.println("Funcion programado correctamente en el sistema.");
        } catch (FuncionSuperpuestaException | FormatoIncompatibleException e) {
            System.out.println(e.getMessage());
        }
    }

    private void menuVentaEntradas() {
        System.out.print("DNI del Espectador: ");
        String dni = scanner.nextLine();
        System.out.print("Nombre: ");
        String nom = scanner.nextLine();
        System.out.print("Apellido: ");
        String ape = scanner.nextLine();
        servicio.registrarEspectador(nom, ape, dni);

        System.out.print("Ingrese ID de la Funcion a la que desea asistir: ");
        int idFunc = Integer.parseInt(scanner.nextLine());
        System.out.print("Fila del asiento (Letra Mayuscula): ");
        char fila = scanner.nextLine().charAt(0);
        System.out.print("Numero de asiento: ");
        int numAsiento = Integer.parseInt(scanner.nextLine());

        try {
            int idEntrada = (int) (Math.random() * 10000);
            Entrada e = servicio.venderEntrada(idEntrada, idFunc, fila, numAsiento, dni, 1500.0);
            if (e != null) {
                System.out.println("\n--- TICKET EMITIDO CON EXITO ---");
                System.out.println("Entrada Nro: " + e.getIdEntrada());
                System.out.print("Pelicula: " + e.getFuncion().getCopia().getPelicula().getTitulo());
                System.out.println(" | Formato: " + e.getFuncion().getCopia().getFormato());
                System.out.println("Sala: " + e.getFuncion().getSala().getTipoTecnologia() + " | Asiento: " + fila + numAsiento);
                System.out.println("Monto abonado: $" + e.calcularPrecioFinal());
            } else {
                System.out.println("Error: No se pudo procesar la venta, verifique los datos");
            }
        } catch (ButacaOcupadaException e) {
            System.out.println(e.getMessage());
        }
    }

    private void menuEvaluacion() {
        System.out.print("ID de Película a evaluar: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("DNI del Jurado: ");
        String dni = scanner.nextLine();
        System.out.print("Puntaje (1 al 10): ");
        int puntos = Integer.parseInt(scanner.nextLine());
        System.out.print("Comentario: ");
        String comentario = scanner.nextLine();

        servicio.registrarEvalucion(dni, id, puntos, comentario);
        System.out.println("Evaluación guardada correctamente.");
    }

    private void menuGanador() {
        System.out.print("Número de Edición: ");
        int ed = Integer.parseInt(scanner.nextLine());
        System.out.print("Nombre de la Sección (ej: Competencia Oficial): ");
        String sec = scanner.nextLine();

        Pelicula ganadora = servicio.determinarGanadorSeccion(ed, sec);
        if (ganadora != null) {
            System.out.println("La película ganadora de la sección " + sec + " es: "
                    + ganadora.getTitulo() + " (Puntaje Promedio: " + ganadora.calcularPuntajePromedio() + ")");
        } else {
            System.out.println("No se encontraron películas evaluadas en esa sección.");
        }
    }
}
