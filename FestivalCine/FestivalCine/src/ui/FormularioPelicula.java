package ui;

import servicios.FestivalSistemasService;
import javax.swing.*;
import java.awt.*;

public class FormularioPelicula extends JInternalFrame {

    private JTextField txtDniDirector, txtNombreDirector, txtApellidoDirector;
    private JTextField txtIdPeli, txtTitulo, txtDuracion, txtGenero;
    private JButton btnRegistrar;

    public FormularioPelicula() {
        super("Registrar Nueva Película y Director", true, true, true, true);
        setSize(450, 350);
        setLayout(new GridLayout(8, 2, 10, 10));

        add(new JLabel(" DNI Director:"));
        txtDniDirector = new JTextField(); add(txtDniDirector);

        add(new JLabel(" Nombre Director:"));
        txtNombreDirector = new JTextField(); add(txtNombreDirector);

        add(new JLabel(" Apellido Director:"));
        txtApellidoDirector = new JTextField(); add(txtApellidoDirector);

        add(new JLabel(" ID Película (Numérico):"));
        txtIdPeli = new JTextField(); add(txtIdPeli);

        add(new JLabel(" Título Película:"));
        txtTitulo = new JTextField(); add(txtTitulo);

        add(new JLabel(" Duración (Minutos):"));
        txtDuracion = new JTextField(); add(txtDuracion);

        add(new JLabel(" Género:"));
        txtGenero = new JTextField(); add(txtGenero);

        btnRegistrar = new JButton("Registrar Todo");
        add(btnRegistrar);

        // Evento del botón
        btnRegistrar.addActionListener(e -> ejecutarRegistro());
    }

    private void ejecutarRegistro() {
        try {
            String dniDir = txtDniDirector.getText();
            String nomDir = txtNombreDirector.getText();
            String apeDir = txtApellidoDirector.getText();

            int idPeli = Integer.parseInt(txtIdPeli.getText());
            String titulo = txtTitulo.getText();
            int duracion = Integer.parseInt(txtDuracion.getText());
            String genero = txtGenero.getText();

            FestivalSistemasService servicio = FestivalSistemasService.getInstancia();

            servicio.registrarDirector(nomDir, apeDir, dniDir);

            servicio.registrarPelicula(idPeli, titulo, duracion, genero, dniDir);

            JOptionPane.showMessageDialog(this, "Director y Película registrados exitosamente.\nYa podés usar el ID " + idPeli + " para programar funciones.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, valide que el ID de película y la Duración sean números válidos.", "Error de Datos", JOptionPane.WARNING_MESSAGE);
        }
    }
}