package Hito7;

import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;

public class Photography extends JFrame {

    private JComboBox<String> photographers;
    private JXDatePicker datePicker;
    public Photography() {

        //MAIN FRAME
        JFrame frame = new JFrame("Photography");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(900,500));
        frame.setLayout(new BorderLayout());

        //PANELS
        JPanel buttonsPanel = new JPanel();
        JPanel photographersPanel = new JPanel();

        //BUTTONS
        JButton awardButton = new JButton("AWARD");
        JButton removeButton = new JButton("REMOVE");
        buttonsPanel.add(awardButton,BorderLayout.NORTH);
        buttonsPanel.add(removeButton,BorderLayout.NORTH);

        //PHOTOGRAPHERS
        photographers = new JComboBox<>();
        photographers.setPreferredSize(new Dimension(150,2));
        photographersPanel.add(new JLabel("Photographer: "));
        photographersPanel.add(photographers,BorderLayout.CENTER);

        //ADD FRAME
        frame.add(photographersPanel);
        frame.add(buttonsPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
