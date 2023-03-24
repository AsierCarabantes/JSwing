package Hito2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ventana extends JFrame implements ActionListener, ItemListener {

private JComboBox<String> archivos;
private JTextArea textoArchivo;
    public ventana() {
        JFrame frame = new JFrame("Test Events: Files");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setLayout(new BorderLayout());


        //Paneles
        JPanel PanelComboBox = new JPanel();
        JPanel PanelTextArea = new JPanel();
        JPanel panel3 = new JPanel();

        //ComboBox
        archivos = new JComboBox<>(new String[]{"Elige una opción", "python.txt", "c.txt", "java.txt"});
        archivos.addItemListener(this);
        archivos.setPreferredSize(new Dimension(350, 25));
        PanelComboBox.add(archivos);

        //Botones
        JButton botonClear = new JButton("Clear");
        botonClear.addActionListener(this);
        JButton botonClose = new JButton("Close");
        botonClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        PanelComboBox.add(botonClear);
        panel3.add(botonClose, BorderLayout.SOUTH);

        //Texto
        textoArchivo = new JTextArea();
        textoArchivo.setPreferredSize(new Dimension(400, 400));
        textoArchivo.setEditable(false);
        PanelTextArea.add(textoArchivo);
        JScrollPane scrollPane = new JScrollPane(textoArchivo);
        frame.add(scrollPane);

        //Paneles
        frame.add(PanelComboBox, BorderLayout.WEST);
        frame.add(PanelTextArea, BorderLayout.EAST);
        frame.add(panel3, BorderLayout.SOUTH);

        frame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        textoArchivo.setText("");
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        // Cargar el archivo seleccionado en el JTextArea
        String fileName = (String) archivos.getSelectedItem();
        if (!fileName.equals("Elige una opción")) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader("src\\Hito2\\" + fileName));
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    sb.append('\n');
                }
                textoArchivo.setText(sb.toString());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al abrir el archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
