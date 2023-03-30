package Hito4;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        String password = JOptionPane.showInputDialog(null,"Input password:","Entrada",JOptionPane.INFORMATION_MESSAGE);
        if (password.equals("damocles")) {
            ventana ventana = new ventana();
        }else {
            JOptionPane.showMessageDialog(null,"Wrong password","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
}
