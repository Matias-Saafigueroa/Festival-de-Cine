package ui;

import Excepciones.ButacaOcupadaException;
import Modelo.Entrada;
import servicios.FestivalSistemasService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;

public class FormularioVenta extends JInternalFrame{
    private JTextField txtDni, txtIdFuncion, txtFila, txtNumero;
    private JButton btnVender;

    public FormularioVenta() {
        super("Módulo de Venta de Entradas", true, true, true, true);
        setSize(400, 250);
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel(" Documento Espectador:"));
        txtDni = new JTextField(); add(txtDni);

        add(new JLabel(" ID Función:"));
        txtIdFuncion = new JTextField(); add(txtIdFuncion);

        add(new JLabel(" Fila Butaca (Letra):"));
        txtFila = new JTextField(); add(txtFila);

        add(new JLabel(" Número Asiento:"));
        txtNumero = new JTextField(); add(txtNumero);

        btnVender = new JButton("Procesar Venta");
        add(btnVender);

        btnVender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarTransaccion();
            }
        });
    }

    private void ejecutarTransaccion() {
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Desea confirmar la emisión de la entrada y facturar el monto base?",
                "Confirmación de Compra", JOptionPane.YES_NO_OPTION);

        if (confirmacion != JOptionPane.YES_OPTION) return;

        try {
            String dni = txtDni.getText().trim();

            if (dni.isEmpty() || txtIdFuncion.getText().isEmpty() || txtFila.getText().isEmpty() || txtNumero.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int idFunc = Integer.parseInt(txtIdFuncion.getText().trim());
            char fila = txtFila.getText().toUpperCase().trim().charAt(0);
            int numero = Integer.parseInt(txtNumero.getText().trim());

            FestivalSistemasService servicio = FestivalSistemasService.getInstancia();

            if (servicio.buscarEspectador(dni) == null) {
                servicio.registrarEspectador("Cliente Anonimo", "Mostrador", dni);
            }

            int idEntradaGen = (int) (Math.random() * 50000);

            Entrada ticket = servicio.venderEntrada(idEntradaGen, idFunc, fila, numero, dni, 1500.0);

            if (ticket != null) {
                String comprobante = "--- TICKET DIGITAL EMITIDO ---\n" +
                        "Entrada Nro: " + ticket.getIdEntrada() + "\n" +
                        "Película: " + ticket.getFuncion().getCopia().getPelicula().getTitulo() + "\n" +
                        "Ubicación: Fila " + fila + " - Asiento " + numero + "\n" +
                        "Monto Final: $" + ticket.calcularPrecioFinal();

                JOptionPane.showMessageDialog(this, comprobante, "Venta Exitosa", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error: No se encontró la Función ID " + idFunc + " en el sistema.", "Venta Fallida", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Campos numéricos (ID Función / Número de Asiento) incorrectos.", "Error de Formulario", JOptionPane.WARNING_MESSAGE);
        } catch (ButacaOcupadaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Localidad No Disponible", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
