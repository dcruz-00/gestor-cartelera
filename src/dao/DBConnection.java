package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para manejar la conexión a la base de datos MySQL
 */
public class DBConnection {

    // URL de conexión a la base de datos MySQL. Servidor - puerto - nombre BD
    private static final String URL = "jdbc:mysql://localhost:3306/Cine_DB";

    // Usuario de la base de datos
    private static final String USER = "root";

    // Contraseña de la base de datos
    private static final String PASS = "password";

    /**
     * Obtiene una conexión activa a la base de datos MySQL
     *
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        try {
            //Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // DriverManager.getConnection() establece la conexión usando la URL, usuario y contraseña
            Connection conn = DriverManager.getConnection(URL, USER, PASS);

            System.out.println("Conexion establecida con la BD");
            return conn;

        } catch (ClassNotFoundException e) {
            System.out.println("Error: Driver MySQL no encontrado -> " + e.getMessage());

        } catch (SQLException e) {
            System.out.println("Error de conexión a la base de datos -> " + e.getMessage());
        }
        return null;
    }
}
