package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPrincipal extends JFrame {
    private JDesktopPane desktopPane;

    public VentanaPrincipal() {
        setTitle("Sistema de Gestión de Festival de Cine - UADE");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        desktopPane = new JDesktopPane();
        desktopPane.setBackground(Color.DARK_GRAY);
        setContentPane(desktopPane);

        setJMenuBar(crearBarraMenu());

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                confirmarSalida();
            }
        });
    }

    private JMenuBar crearBarraMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                confirmarSalida();
            }
        });
        menuArchivo.add(itemSalir);

        JMenu menuFestival = new JMenu("Festival y Operaciones");

        JMenuItem itemSede = new JMenuItem("Registrar Sede y Sala");
        JMenuItem itemRegistrarPeli = new JMenuItem("Registrar Película y Director");
        JMenuItem itemProgramar = new JMenuItem("Programar Función");
        JMenuItem itemVender = new JMenuItem("Venta de Entradas (Crítico)");

        itemSede.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                FormularioSede formSede = new FormularioSede();
                desktopPane.add(formSede);
                formSede.setVisible(true);
            }
        });

        itemRegistrarPeli.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                FormularioPelicula formPeli = new FormularioPelicula();
                desktopPane.add(formPeli);
                formPeli.setVisible(true);
            }
        });

        itemProgramar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                FormularioFuncion form = new FormularioFuncion();
                desktopPane.add(form);
                form.setVisible(true);
            }
        });

        itemVender.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                FormularioVenta form = new FormularioVenta();
                desktopPane.add(form);
                form.setVisible(true);
            }
        });

        menuFestival.add(itemSede);
        menuFestival.add(itemRegistrarPeli);
        menuFestival.add(itemProgramar);
        menuFestival.add(itemVender);

        menuBar.add(menuArchivo);
        menuBar.add(menuFestival);

        return menuBar;
    }

    private void confirmarSalida() {
        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de que desea salir del sistema?\nLos datos actuales se encuentran persistidos automáticamente.",
                "Confirmar Salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
