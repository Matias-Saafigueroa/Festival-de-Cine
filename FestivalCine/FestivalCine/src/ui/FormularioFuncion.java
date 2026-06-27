package ui;

import Modelo.Pelicula;
import servicios.FestivalSistemasService;
import Excepciones.FuncionSuperpuestaException;
import Modelo.CopiaPelicula;
import Excepciones.FormatoIncompatibleException;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class FormularioFuncion extends JInternalFrame {
    private JTextField txtIdFuncion, txtIdSede, txtNumSala, txtIdPeli, txtFormato, txtHorario;
    private JButton btnProgramar;

    public FormularioFuncion() {
        super("Programar Nueva Funcion", true, true, true, true);
        setSize(400, 300);
        setLayout(new GridLayout(7, 2, 10, 10));

        add(new JLabel("ID Funcion:"));
        txtIdFuncion = new JTextField(); add(txtIdFuncion);

        add(new JLabel("ID Sede:"));
        txtIdSede = new JTextField(); add(txtIdSede);

        add(new JLabel("Numero Sala:"));
        txtNumSala = new JTextField(); add(txtNumSala);

        add(new JLabel("ID Pelicula:"));
        txtIdPeli = new JTextField(); add(txtIdPeli);

        add(new JLabel("Formato de Copia (Regular/IMAX):"));
        txtFormato = new JTextField(); add(txtFormato);

        add(new JLabel("Horario (hh:mm):"));
        txtHorario = new JTextField(); add(txtHorario);

        btnProgramar = new JButton("Programar Funcion");
        add(btnProgramar);

        btnProgramar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                ejecutarProgramacion();
            }
        });
    }

    private void ejecutarProgramacion() {
        try {
            int idFunc = Integer.parseInt(txtIdFuncion.getText());
            int idSede = Integer.parseInt(txtIdSede.getText());
            int numSala = Integer.parseInt(txtNumSala.getText());
            int idPeli = Integer.parseInt(txtIdPeli.getText());
            String formato = txtFormato.getText();
            String horario = txtHorario.getText();

            FestivalSistemasService servicio = FestivalSistemasService.getInstancia();
            Pelicula p = servicio.buscarPelicula(idPeli);

            if (p == null) {
                JOptionPane.showMessageDialog(this, "La pelicula especifica no existe en la base de datos.", "Error de Validacion", JOptionPane.ERROR_MESSAGE);
                return;
            }

            CopiaPelicula copia = new CopiaPelicula("COP-" + idFunc, p, formato, "Castellano", true, true);

            servicio.progrmarFuncion(idFunc, new Date(), horario, idSede, numSala, copia);

            JOptionPane.showMessageDialog(this, "Función incorporada exitosamente al cronograma.", "Operación Exitosa", JOptionPane.INFORMATION_MESSAGE);
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, revise que los campos numéricos contengan valores válidos.", "Entrada Inválida", JOptionPane.WARNING_MESSAGE);
        } catch (FuncionSuperpuestaException | FormatoIncompatibleException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Conflicto de Logística", JOptionPane.ERROR_MESSAGE);
        }
    }
}
