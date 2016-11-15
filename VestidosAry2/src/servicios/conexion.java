package servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class conexion {

//    private static String ip = "vestidosAry.mssql.somee.com";
//    private static String db = "vestidosAry";
//    private static String usuario = "csar1143_SQLLogin_1";
//    private static String contrasena = "312agkee9m";

    private static String ip = "localhost";
    private static String db = "vestidosaryam";
    private static String usuario = "sa";
    private static String contrasena = "princesaazteka"; 
     
    private static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String url = "jdbc:sqlserver://" + ip + ":1433;databaseName=" + db;
      
    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName(driver);
            System.out.println("se conecto");
             System.out.println("se conecto");
        } catch (Exception e) {
            System.out.println("no conecto");
            e.getMessage();
        }
        try {
            con = DriverManager.getConnection(url, usuario, contrasena);
            System.out.println("conectado");
             System.out.println("conectado " + url);
        } catch (Exception e) {
            e.getMessage();
            System.out.println("no conecta");
            System.out.println("no conecta " + url);

        }
        return con;
    }
}
