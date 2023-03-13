package Hito1;

import javax.swing.*;
import java.awt.*;

public class ventana extends JFrame{

    public ventana() {
        JFrame frame = new JFrame("Try yourself");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setLayout(new BorderLayout());

        //Panel Norte
        JPanel panelNorte = new JPanel();
        panelNorte.setLayout(new FlowLayout());

        JCheckBox checkbox1 = new JCheckBox("Katniss");
        panelNorte.add(checkbox1);

        JCheckBox checkbox2 = new JCheckBox("Peeta");
        panelNorte.add(checkbox2);

        frame.add(panelNorte, BorderLayout.NORTH);

        //Panel Este
        JPanel panelEste = new JPanel();
        panelEste.setSize(250,0);

        JRadioButton radio1 = new JRadioButton("OPT 1",true);
        JRadioButton radio2 = new JRadioButton("OPT 2");
        JRadioButton radio3 = new JRadioButton("OPT 3");

        JRadioButton[] radioButtons = {radio1,radio2,radio3};
        ButtonGroup rBGroup = new ButtonGroup();

        panelEste.add(Box.createVerticalGlue());
        for (JRadioButton radioButton : radioButtons) {
            rBGroup.add(radioButton);
            panelEste.add(radioButton);
        }
        panelEste.add(Box.createVerticalGlue());
        frame.add(panelEste, BorderLayout.EAST);
        panelEste.setLayout(new BoxLayout(panelEste,BoxLayout.Y_AXIS));

        //Panel Sur
        JPanel panelSur = new JPanel();
        panelSur.setLayout(new BoxLayout(panelSur,BoxLayout.X_AXIS));
        panelSur.setSize(0,50);

        JButton boton1 = new JButton("But 1");
        JButton boton2 = new JButton("But 2");
        panelSur.add(boton1);
        panelSur.add(boton2);

        frame.add(panelSur, BorderLayout.SOUTH);

        //Panel Centro
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new GridLayout(2,2));

        for (int i = 0; i < 4; i++) {
            JLabel serre7 = new JLabel(new ImageIcon("C:\\Users\\ik012982i9\\Desktop\\RETO\\P\\serre7.png"));
            panelCentro.add(serre7);
        }

        frame.add(panelCentro,BorderLayout.CENTER);
        frame.setVisible(true);
        frame.pack();
    }

}
