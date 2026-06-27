========================================================================
                SISTEMA DE GESTIÓN DE FESTIVAL DE CINE
========================================================================

------------------------------------------------------------------------
1. DESCRIPCIÓN GENERAL
------------------------------------------------------------------------
Este proyecto consiste en la implementación de un sistema de software integral 
para la gestión de un Festival Internacional de Cine, migrado en esta última 
etapa a un entorno interactivo visual desarrollado con la librería Java Swing.

El sistema permite administrar de punta a punta el ciclo del festival: 
el registro de ediciones, secciones, películas, el factor humano (elenco de 
actores y directores), la infraestructura física de sedes y salas, la 
programación de funciones (con validaciones lógicas), la venta automatizada 
de entradas a espectadores con mapa dinámico de butacas y el posterior 
proceso de calificación de jurados para determinar premios por sección.

------------------------------------------------------------------------
2. ARQUITECTURA DEL SISTEMA Y ORGANIZACIÓN DE PAQUETES
------------------------------------------------------------------------
El desarrollo mantiene estrictamente una arquitectura basada en bajo 
acoplamiento y alta cohesión mediante la separación de responsabilidades 
en paquetes independientes:

* ar.edu.uade.festival.modelo: 
  Contiene las entidades puras del dominio encapsuladas, modelando el 
  comportamiento lógico innato de los objetos y sus colecciones.
  
* ar.edu.uade.festival.servicios: 
  Implementa la capa controladora del negocio centralizada mediante el 
  patrón Singleton (FestivalSistemasService). Procesa la lógica algorítmica 
  y aplica validaciones de dominio sin intervenir en la visualización.

* ar.edu.uade.festival.persistencia: 
  Mecanismo transparente e independiente de lectura/escritura de flujos 
  binarios en el disco (.dat), aislando la lógica de negocio de la 
  infraestructura de almacenamiento.

* ar.edu.uade.festival.excepciones: 
  Centraliza las excepciones personalizadas del negocio para un 
  tratamiento robusto y seguro de errores.

* ar.edu.uade.festival.ui: 
  Capa exclusiva de presentación desarrollada en Java Swing. Se encarga 
  únicamente de la captura de eventos gráficos y el renderizado visual, 
  delegando cualquier proceso lógico al Service.

* ar.edu.uade.festival.app: 
  Punto de entrada ejecutable que inicializa el sistema bajo el hilo 
  seguro de despacho de eventos de Swing (EDT).

------------------------------------------------------------------------
3. DECISIONES RELEVANTES DE IMPLEMENTACIÓN
------------------------------------------------------------------------
* Desacoplamiento Absoluto (Capa GUI - Lógica): 
  Siguiendo las directrices de la cátedra, ninguna ventana, formulario 
  o botón ejecuta lógica matemática, validaciones de dominio o búsquedas 
  manuales. La UI actúa puramente como un puente estético que invoca 
  métodos atómicos de la capa de servicios.

* Persistencia Transparente: 
  Se reutilizó de forma íntegra el motor de persistencia por serialización. 
  La lectura de archivos se ejecuta al instanciarse el servicio, y los 
  cambios se salvan automáticamente tras cada mutación del estado del 
  sistema, garantizando que el usuario no deba forzar un guardado manual.

* Robustez ante Fallos: 
  La UI intercepta de forma gráfica las excepciones "checked" personalizadas 
  lanzadas por la lógica de negocio. Esto evita cuelgues o interrupciones 
  abruptas y comunica al operador los errores mediante cuadros de diálogo 
  claros (JOptionPane.ERROR_MESSAGE).

------------------------------------------------------------------------
4. REQUERIMIENTO DE EXTENSIÓN ORIGINAL (IMPLEMENTADO)
------------------------------------------------------------------------
* Propuesta: Extensión de Logística de Formatos y Red de Sedes Múltiples.
* Problema que resuelve: El festival base asume una única locación y salas 
  genéricas. Nuestra extensión añade las clases 'Sede' y 'CopiaPelicula' 
  para permitir sedes físicas independientes distribuidas geográficamente. 
  A su vez, restringe la programación controlando tecnológicamente que 
  formatos especiales de cintas (ej: IMAX) requieran de forma obligatoria 
  salas equipadas con dicha infraestructura, impidiendo errores de logística.
* Integración: Se acopló de manera natural al flujo de "Programar Función", 
  añadiendo una regla de validación cruzada y su correspondiente excepción.

------------------------------------------------------------------------
5. MEJORAS COMPLEMENTARIAS SWING INCORPORADAS
------------------------------------------------------------------------
Para cumplir con los requerimientos de la Etapa 3, se seleccionaron y 
diseñaron las siguientes dos optimizaciones de interfaz:

1. Ventanas Internas mediante JDesktopPane y JInternalFrame: 
   La aplicación implementa una interfaz de múltiples documentos (MDI). 
   La ventana principal actúa como marco contenedor y los formularios se 
   despliegan dentro del espacio de trabajo como subventanas flotantes, 
   organizando la navegación de forma unificada.

2. Diálogos de Confirmación ante Operaciones Críticas: 
   Se configuró un interceptor en el botón de salida de la aplicación y 
   en el procesamiento financiero de tickets que solicita confirmación 
   explícita al usuario mediante cuadros de opción (JOptionPane.YES_NO_OPTION), 
   resguardando la integridad de la sesión ante clics accidentales.

------------------------------------------------------------------------
6. INSTRUCCIONES DE EJECUCIÓN
------------------------------------------------------------------------
1. Descomprimir el archivo entregable.
2. Importar el proyecto en cualquier IDE compatible con Java SE 8 o superior.
3. Ejecutar la clase principal: ar.edu.uade.festival.app.Main.
4. Al abrirse la ventana principal, interactuar mediante los menús superiores.
