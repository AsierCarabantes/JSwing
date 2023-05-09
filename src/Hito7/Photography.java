package Hito7;

import Hito6.GestorDB;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Photography extends JFrame {

    private JComboBox<String> photographers;
    private JXDatePicker datePicker;
    private JList<String> pictureNames;
    private JLabel pictureLabel;
    GestorDB gestorDb;

    public Photography() {

        //MAIN FRAME
        JFrame frame = new JFrame("Photography");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(900, 500));
        frame.setLayout(new BorderLayout());

        //PANELS
        JPanel buttonsPanel = new JPanel();
        JPanel photographersPanel = new JPanel();
        JPanel datePanel = new JPanel();
        JPanel listPanel = new JPanel();
        JPanel imagePanel = new JPanel();

        //BUTTONS
        JButton awardButton = new JButton("AWARD");
        JButton removeButton = new JButton("REMOVE");
        buttonsPanel.add(awardButton, BorderLayout.NORTH);
        buttonsPanel.add(removeButton, BorderLayout.NORTH);

        //PHOTOGRAPHERS
        gestorDb = new GestorDB();
        photographers = new JComboBox<>();
        String[] names = gestorDb.select("Nombre", "photographers", null);
        for (String nombre : names) {
            photographers.addItem(nombre);
        }
        photographers.setPreferredSize(new Dimension(150, 2));
        photographersPanel.add(new JLabel("Photographer: "));
        photographersPanel.add(photographers, BorderLayout.CENTER);

        //DATE PICKER
        datePicker = new JXDatePicker();
        datePanel.add(new JLabel("Photos after: "));
        datePanel.add(datePicker,BorderLayout.CENTER);
        datePicker.setPreferredSize(new Dimension(150, 25));

        //LIST
        pictureNames = new JList<>();
        pictureNames.setPreferredSize(new Dimension(300, 200));
        listPanel.add(new JScrollPane(pictureNames));

        pictureLabel = new JLabel();
        pictureLabel.setHorizontalAlignment(JLabel.CENTER);
        pictureLabel.setPreferredSize(new Dimension(300, 200));
        imagePanel.add(pictureLabel);

        //ADD FRAME
        frame.add(photographersPanel);
        frame.add(imagePanel);
        frame.add(datePanel);
        frame.add(buttonsPanel);
        frame.pack();


        photographers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date fecha = datePicker.getDate();
                if (fecha == null) {
                    String name = photographers.getSelectedItem().toString();
                    String[] titulos = gestorDb.select("Title", "pictures", "Photographer IN (SELECT PhotographerId FROM photographers WHERE Nombre = '" + name + "')");

                    pictureNames.setListData(titulos);
                } else {
                    // Crear un objeto SimpleDateFormat con el formato deseado
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

                    // Formatear la fecha como un String en el formato deseado
                    String fechaFormateada = formato.format(fecha);
                    String name = photographers.getSelectedItem().toString();
                    String[] titulos = gestorDb.select("Title", "pictures", "Photographer IN (SELECT PhotographerId FROM photographers WHERE Nombre = '" + name + "' AND Fecha > '" + fechaFormateada + "')");

                    pictureNames.setListData(titulos);
                }
            }
        });

        datePicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener la fecha seleccionada del datePicker
                Date fecha = datePicker.getDate();

                // Crear un objeto SimpleDateFormat con el formato deseado
                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

                // Formatear la fecha como un String en el formato deseado
                String fechaFormateada = formato.format(fecha);
                String name = photographers.getSelectedItem().toString();
                String[] titulos = gestorDb.select("Title", "pictures", "Photographer IN (SELECT PhotographerId FROM photographers WHERE nombre = '" + name + "' AND Fecha > '" + fechaFormateada + "')");

                pictureNames.setListData(titulos);
            }
        });

        // Configurar el comportamiento de la lista de imágenes
           pictureNames.addListSelectionListener(new ListSelectionListener() {
           @Override
           public void valueChanged(ListSelectionEvent e) {
               String titulo = pictureNames.getSelectedValue();
               String src = Arrays.toString(gestorDb.select("Archivo", "pictures", "Title = '" + titulo + "'"));
               String file = src.substring(1, src.length() - 1);

               ImageIcon icono = new ImageIcon(file);
               int labelWidth = pictureLabel.getWidth();
               int labelHeight = pictureLabel.getHeight();
               Image img = icono.getImage();
               Image scaledImg = img.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
               icono = new ImageIcon(scaledImg);
               pictureLabel.setIcon(icono);
           }
           });
           frame.setVisible(true);
    }
}