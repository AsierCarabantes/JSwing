package Hito7;

import Hito6.GestorDB;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Photography extends JFrame {

    private JComboBox<String> photographers;
    private JXDatePicker datePicker;
    private JList<String> pictureNames;
    private JLabel pictureLabel;
    GestorDB2 gestorDb;
    private Connection con;

    public Photography() {

        //MAIN FRAME
        JFrame frame = new JFrame("Photography");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(900, 500));
        frame.setLayout(new GridLayout(3,2,5,5));

        //PANELS
        JPanel photographersPanel = new JPanel();
        JPanel datePanel = new JPanel();
        JPanel listPanel = new JPanel();
        JPanel imagePanel = new JPanel();

        //BUTTONS
        JButton premioButton = new JButton("PREMIAR");
        premioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int minVisits = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el número mínimo de visitas para ser premiado"));
                gestorDb.updatePhotographers(minVisits);
            }
        });

        JButton eliminarButton = new JButton("ELIMINAR");
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gestorDb.deleteUnseenImg();
            }
        });



        //PHOTOGRAPHERS
        gestorDb = new GestorDB2();
        photographers = new JComboBox<>();
        String[] names = gestorDb.select("Nombre", "photographers", null);
        for (String nombre : names) {
            photographers.addItem(nombre);
        }
        photographers.setPreferredSize(new Dimension(150, 20));
        photographersPanel.add(new JLabel("Photographer: "));
        photographersPanel.add(photographers);

        //DATE PICKER
        datePicker = new JXDatePicker();
        datePanel.add(new JLabel("Photos after: "));
        datePanel.add(datePicker);
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
        frame.add(datePanel);
        frame.add(listPanel);
        frame.add(imagePanel);
        frame.add(premioButton);
        frame.add(eliminarButton);
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
        photographers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Date fecha = datePicker.getDate();

                if (fecha == null) {
                    String name = photographers.getSelectedItem().toString();
                    String [] titulos = gestorDb.select("Title", "pictures","Photographer IN (SELECT PhotographerId FROM photographers WHERE nombre = '" + name + "')");

                    pictureNames.setListData(titulos);

                } else {
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

                    String fechaFormateada = formato.format(fecha);

                    String name = photographers.getSelectedItem().toString();
                    String [] titulos = gestorDb.select("Title", "pictures","PhotographerId IN (SELECT PhotographerId FROM photographers WHERE nombre = '" + name + "' AND fecha > '" + fechaFormateada + "')");

                    pictureNames.setListData(titulos);

                }

            }
        });

        datePicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date fecha = datePicker.getDate();

                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

                String fechaFormateada = formato.format(fecha);
                String name = photographers.getSelectedItem().toString();
                String [] titulos = gestorDb.select("Title", "pictures","Photographer IN (SELECT PhotographerId FROM photographers WHERE nombre = '" + name + "' AND fecha > '" + fechaFormateada + "')");


                pictureNames.setListData(titulos);
            }
        });


        // Configurar el comportamiento de la lista de imágenes
        pictureNames.addListSelectionListener(new ListSelectionListener() {
            @Override
             public void valueChanged(ListSelectionEvent e) {
                 if (e.getValueIsAdjusting()) {
                     return;
                 }
                 String titulo = pictureNames.getSelectedValue();
                 String src = Arrays.toString(gestorDb.select("Archivo", "pictures","title = '" + titulo + "'"));
                 String file = src.substring(1,src.length()-1);

                 ImageIcon icono = new ImageIcon(file);
                 int labelWidth = pictureLabel.getWidth();
                 int labelHeight = pictureLabel.getHeight();
                 Image img = icono.getImage();
                 Image scaledImg = img.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
                 icono = new ImageIcon(scaledImg);
                 pictureLabel.setIcon(icono);

                 gestorDb.update("title = '" + titulo + "'");
             }
         }
        );

        frame.setVisible(true);
    }
}
