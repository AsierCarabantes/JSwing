package Hito6;

import java.sql.*;
import java.util.ArrayList;

public class GestorDB {

    private Connection con;
    private Statement st;
    private ResultSet rs;

    static final String USER = "root";
    static final String PASSWORD = "zubiri";
    static final String URL = "jdbc:mysql://localhost:3306/hito6";

    public GestorDB() {

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

    public static void main(String[] args) {
        GestorDB gestorDB = new GestorDB();
    }

}
