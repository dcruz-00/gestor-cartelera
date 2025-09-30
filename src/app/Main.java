package app;

import javax.swing.SwingUtilities;
import view.MainFrame;
import dao.DBConnection;
import java.sql.Connection;

/**
 * Clase principal que contiene el método main()
 *
 * Inicializa la interfaz gráfica de manera segura y verifica la conexión a la
 * base de datos
 */
public class Main {

    public static void main(String[] args) {
        //Verifica conexión a la BD
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null) {
                System.out.println("Conexion confirmada con la base de datos Cine_DB.");
            } else {
                System.out.println("No se pudo establecer conexión con la base de datos Cine_DB.");
            }
        } catch (Exception e) {
            System.out.println("Error durante la verificacion de la conexion: " + e.getMessage());
        }

        SwingUtilities.invokeLater(MainFrame::new);
    }
}
