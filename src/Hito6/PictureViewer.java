package Hito6;

import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static javax.xml.xpath.XPathFactory.newInstance;

public class PictureViewer extends JFrame {
    private JComboBox<String> photographers;
    private JXDatePicker datePicker;
    private JList<String> pictureNames;
    private JLabel pictureLabel;

    private Connection conn;
    GestorDB gestorDb;
    public PictureViewer(){

        JFrame frame = new JFrame("Photography");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900,500);
        frame.setLayout(new GridLayout(2,2,5,5));

        //JComboBox
        JPanel panelComboBox = new JPanel();
        gestorDb = new GestorDB();
        photographers = new JComboBox<>();
        String [] names = gestorDb.select("Nombre","photographers",null);
        for (String nombre : names) {
            photographers.addItem(nombre);
        }
        photographers.setPreferredSize(new Dimension(150,25));
        panelComboBox.add(new JLabel("Photographer: "));
        panelComboBox.add(photographers);

        //Date
        JPanel panelDate = new JPanel();
        datePicker = new JXDatePicker();
        panelDate.add(new JLabel("Photos after: "));
        panelDate.add(datePicker);
        datePicker.setPreferredSize(new Dimension(150,25));

        panelDate.add(datePicker);

        //List
        JPanel panelList = new JPanel();
        pictureNames = new JList<>();
        pictureNames.setPreferredSize(new Dimension(300,200));
        panelList.add(new JScrollPane(pictureNames));

        JPanel panelImage = new JPanel();
        pictureLabel = new JLabel();
        pictureLabel.setHorizontalAlignment(JLabel.CENTER);
        pictureLabel.setPreferredSize(new Dimension(300, 200));
        panelImage.add(pictureLabel);

        frame.add(panelComboBox);
        frame.add(panelDate);
        frame.add(panelList);
        frame.add(panelImage);
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
            String [] titulos = gestorDb.select("Title", "pictures","Photographer IN (SELECT PhotographerId FROM photographers WHERE nombre = '" + name + "' AND Fecha > '" + fechaFormateada + "')");


            pictureNames.setListData(titulos);
        }
    });


    // Configurar el comportamiento de la lista de imágenes
        pictureNames.addListSelectionListener(new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            String titulo = pictureNames.getSelectedValue();
            String src = Arrays.toString(gestorDb.select("Archivo", "pictures","Title = '" + titulo + "'"));
            String file = src.substring(1,src.length()-1);

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