package Hito4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ventana extends JFrame implements ActionListener {

    private JComboBox<String> photos;
    JTextField text;
    JLabel fotoPanel;
    JComboBox fotolist;
    JCheckBox checkBox;
    public ventana() {


        JFrame frame = new JFrame("Swing - Example 2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setLayout(new FlowLayout(15,15,15));

        //panel principal
        JPanel principal=new JPanel();
        principal.setLayout(new BoxLayout(principal, BoxLayout.Y_AXIS));

        //foto
        JPanel panelImagen = new JPanel();
        panelImagen.setLayout(new FlowLayout(10,10,10));
        fotoPanel = new JLabel(new ImageIcon("src\\Hito4\\images\\houses.jpg"));
        fotoPanel.setPreferredSize(new Dimension(800,800));
        panelImagen.add(fotoPanel);

        frame.add(fotoPanel,BorderLayout.CENTER);

        //comboBox
        JPanel panelComboBox = new JPanel();
        panelComboBox.setLayout(new FlowLayout(10,10,10));
        String[] fotos = {"houses.jpg","mbappe.jpg","messichiquito.jpg"};
        fotolist = new JComboBox<>(fotos);
        fotolist.addActionListener(new ComboListener());
        panelComboBox.add(fotolist,BorderLayout.CENTER);

        frame.add(panelComboBox,BorderLayout.NORTH);

        //checkBox
        JPanel panelCheckBox = new JPanel();
        checkBox = new JCheckBox("Save your comment");
        checkBox.setSelected(true);
        panelCheckBox.add(checkBox,BorderLayout.SOUTH);
        frame.add(panelCheckBox);

        //button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(10,10,10));
        JButton saveButton = new JButton("SAVE");
        buttonPanel.add(saveButton,BorderLayout.CENTER);
        saveButton.addActionListener(this);
        frame.add(buttonPanel,BorderLayout.SOUTH);

        //textfield
        JPanel textField = new JPanel();
        textField.setLayout(new FlowLayout(10,10,10));
        text = new JTextField();
        text.setPreferredSize(new Dimension(150,20));
        textField.add(text,BorderLayout.SOUTH);

        frame.add(textField);

        principal.add(panelComboBox);
        principal.add(fotoPanel);
        principal.add(textField);
        principal.add(checkBox);
        principal.add(buttonPanel);
        frame.add(principal);
        frame.pack();
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                JOptionPane.showMessageDialog(null,"¡Adiós!");

                dispose();
            }
        });

    }
    public void load_combo(){
        String img = fotolist.getSelectedItem().toString();
        String sele ="src\\Hito4\\images" + File.separator + img;
        fotoPanel.setIcon(new ImageIcon(sele));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name= fotolist.getSelectedItem().toString();
        String comment = text.getText();
        boolean selected = checkBox.isSelected();
        BufferedWriter text = null;
        String sele ="src\\Hito4" + File.separator + name;
        try {
            text=new BufferedWriter(new FileWriter(sele+".txt" , true) );
            if (selected){
                text.write("name :" + name + " comment :" +comment + "\n");
            }
            else {
                text.write(name+ "\n");
            }

        }catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        finally {
            try {
                if (text != null) {
                    text.close();
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (selected){

        }
    }
    class ComboListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            load_combo();
        }
    }
}
