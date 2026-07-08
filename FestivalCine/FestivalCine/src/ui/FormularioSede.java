package ui;


import servicios.FestivalSistemasService;
import javax.swing.*;
import java.awt.*;

public class FormularioSede extends JInternalFrame {

    private JTextField txtIdSede, txtNombreSede, txtDireccion;
    private JTextField txtNumSala, txtCapacidad, txtTecnologia;
    private JButton btnRegistrar;

    public FormularioSede() {
        super("Registrar Sede y Sala Cinematográfica", true, true, true, true);
        setSize(450, 320);
        setLayout(new GridLayout(7, 2, 10, 10));

        add(new JLabel(" ID Sede (Numérico):"));
        txtIdSede = new JTextField(); add(txtIdSede);

        add(new JLabel(" Nombre Sede (ej: Hoyts Abasto):"));
        txtNombreSede = new JTextField(); add(txtNombreSede);

        add(new JLabel(" Dirección:"));
        txtDireccion = new JTextField(); add(txtDireccion);

        add(new JLabel(" Número de Sala (Numérico):"));
        txtNumSala = new JTextField(); add(txtNumSala);

        add(new JLabel(" Capacidad de Asientos:"));
        txtCapacidad = new JTextField(); add(txtCapacidad);

        add(new JLabel(" Tecnología (Regular / IMAX / 3D):"));
        txtTecnologia = new JTextField(); add(txtTecnologia);

        btnRegistrar = new JButton("Registrar Infraestructura");
        add(btnRegistrar);

        btnRegistrar.addActionListener(e -> ejecutarRegistroInfr());
    }

    private void ejecutarRegistroInfr() {
        try {
            if (txtIdSede.getText().isEmpty() || txtNombreSede.getText().isEmpty() || txtNumSala.getText().isEmpty() || txtCapacidad.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos de infraestructura.", "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int idSede = Integer.parseInt(txtIdSede.getText().trim());
            String nombre = txtNombreSede.getText().trim();
            String direccion = txtDireccion.getText().trim();

            int numSala = Integer.parseInt(txtNumSala.getText().trim());
            int capacidad = Integer.parseInt(txtCapacidad.getText().trim());
            String tecnologia = txtTecnologia.getText().trim().toUpperCase();
            FestivalSistemasService servicio = FestivalSistemasService.getInstancia();

            servicio.registrarSede(idSede, nombre, direccion);
            servicio.registrarSalaEnSede(idSede, numSala, capacidad, tecnologia);

            JOptionPane.showMessageDialog(this, "Sede " + idSede + " y Sala " + numSala + " (" + tecnologia + ") creadas correctamente.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Asegúrese de que el ID Sede, Número de Sala y Capacidad sean valores numéricos.", "Error en Datos", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar la infraestructura:\n" + ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
