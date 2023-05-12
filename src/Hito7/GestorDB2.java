package Hito7;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GestorDB2 {

    private Connection con;
    private Statement st;
    private ResultSet rs;

    static final String USER = "root";
    static final String PASSWORD = "zubiri";
    static final String URL = "jdbc:mysql://localhost:3306/hito6";

    public GestorDB2() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println("Conexión a la BD");
        } catch (Exception e) {
            System.out.println("Error en la conexión");
        }
    }
    public String[] select(String column, String table, String condition) {
        ArrayList<String> names = new ArrayList<>();
        String query = "SELECT " + column + " FROM " + table + " WHERE " + condition;
        if (condition == null) {
            query = "SELECT " + column + " FROM " + table;
        }
        try{
            st = con.createStatement();
            rs = st.executeQuery(query);
            while(rs.next()) {
                names.add(rs.getString(column));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String [] namesString = names.toArray(new String[0]);

        return namesString;
    }

    public void update(String condicion) {
        String consulta = "UPDATE pictures SET visits = visits + 1 WHERE " + condicion;
        if (condicion == null) {
            consulta = "UPDATE pictures SET visits = visits + 1 WHERE " + condicion;
        }

        try {
            st = con.createStatement();
            st.executeUpdate(consulta);
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public HashMap<String, String> createVisitsMap() {
        HashMap<String, String> visitsMap = new HashMap<>();
        String photographerId[] = select("PhotographerId","photographers", null);
        for (int i = 0; i < photographerId.length; i++) {
            String visitas = select("SUM(Visits)", "pictures", "Photographer = ' " + photographerId[i] + "'")[0];
            visitsMap.put(photographerId[i], visitas);
        }
        return visitsMap;
    }

    public void updatePhotographers(int minVisits) {
        HashMap<String, String> visitsMap = createVisitsMap();
        for (Map.Entry<String, String> map : visitsMap.entrySet()) {
            if (Integer.parseInt(map.getValue()) >= minVisits) {
                try {
                    st = con.createStatement();
                    st.executeUpdate("UPDATE photographers SET awarded = true WHERE PhotographerId = " + map.getKey());
                    st.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    st = con.createStatement();
                    st.executeUpdate("UPDATE photographers SET awarded = false WHERE PhotographerId = " + map.getKey());
                    st.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void deleteUnseenImg() {
        String [] nonAwardedPhotographers = select("PhotographerId","photographers", "awarded = false");
        System.out.println("Non awarded: " + Arrays.toString(nonAwardedPhotographers));
        String [] unseenImg = select("PictureId", "pictures","visits = 0 and Photographer IN (select PhotographerId from photographers where awarded = 0)");
        System.out.println("UnseenImg: " + Arrays.toString(unseenImg));

        if (unseenImg.length == 0) {
            JOptionPane.showMessageDialog(null,"No hay imagenes sin visitas", "Sin imagenes", JOptionPane.ERROR_MESSAGE);
        }

        JLabel img = new JLabel();

        for (int i = 0; i < unseenImg.length; i++) {

            String [] files = select("Archivo", "pictures", "pictureId = " + unseenImg[i]);
            String file = Arrays.toString(files);
            String fileSB = file.substring(1,file.length()-1);
            ImageIcon icono = new ImageIcon(fileSB);
            Image image = icono.getImage();
            Image scaledImg = image.getScaledInstance(500, 300, Image.SCALE_SMOOTH);
            icono = new ImageIcon(scaledImg);
            img.setIcon(icono);

            int confirm = JOptionPane.showConfirmDialog(null,img,"Eliminar imagen",JOptionPane.OK_CANCEL_OPTION);
            if (confirm == JOptionPane.OK_OPTION) {
                try {
                    st = con.createStatement();
                    st.executeUpdate("DELETE FROM pictures WHERE PictureId = " + unseenImg[i]);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        }

    }

    public static void main(String[] args) {
        GestorDB2 gestorDB = new GestorDB2();
    }

}
