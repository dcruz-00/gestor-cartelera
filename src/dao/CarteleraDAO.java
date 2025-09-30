package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Cartelera;

/**
 * DAO - Data Access Object para la entidad Cartelera Se encarga de todas las
 * operaciones con la base de datos relacionadas con Cartelera Implementa el
 * patrón DAO que separa la lógica de acceso a datos del resto de la aplicación
 */
public class CarteleraDAO {

    /**
     * Crea un nuevo registro de película en la base de datos
     *
     * @param c
     * @return
     */
    public boolean crear(Cartelera c) {
        String sql = "INSERT INTO Cartelera (titulo, director, anio, duracion, genero) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            // Reemplaza los ? de la consulta SQL con los valores del objeto Cartelera
            ps.setString(1, c.getTitulo());
            ps.setString(2, c.getDirector());
            ps.setInt(3, c.getAnio());
            ps.setInt(4, c.getDuracion());
            ps.setString(5, c.getGenero());

            // Ejecuta la inserción y retorna true si se afectó 1 fila
            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            System.out.println("Error crear(): " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista todas las peliculas registradas en la base de datos.
     *
     * @return
     */
    public List<Cartelera> listar() {
        List<Cartelera> out = new ArrayList<>();

        String sql = "SELECT id, titulo, director, anio, duracion, genero FROM Cartelera ORDER BY id";

        try (Connection conn = DBConnection.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            // Recorre el ResultSet y crea objetos Cartelera con los datos de cada fila
            while (rs.next()) {
                out.add(new Cartelera(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("director"),
                        rs.getInt("anio"),
                        rs.getInt("duracion"),
                        rs.getString("genero")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error listar(): " + e.getMessage());
        }
        return out;
    }

    /**
     * Busca películas cuto título contenga el patrón especificado
     *
     * @param patron
     * @return
     */
    public List<Cartelera> buscarPorNombre(String patron) {
        List<Cartelera> out = new ArrayList<>();

        String sql = "SELECT id, titulo, director, anio, duracion, genero FROM Cartelera WHERE LOWER(titulo) LIKE ? ORDER BY id";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            // Sustituye el ? con el patrón de búsqueda
            ps.setString(1, "%" + patron + "%");

            try (ResultSet rs = ps.executeQuery()) {
                // Recorre los resultados y crea objetos Cartelera
                while (rs.next()) {
                    out.add(new Cartelera(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("director"),
                            rs.getInt("anio"),
                            rs.getInt("duracion"),
                            rs.getString("genero")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error buscarPorNombre(): " + e.getMessage());
        }
        return out;
    }

    public Cartelera buscarPorId(Integer id) {
        String sql = "SELECT * FROM Cartelera WHERE id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Cartelera(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("director"),
                            rs.getInt("anio"),
                            rs.getInt("duracion"),
                            rs.getString("genero")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error buscarPorId(): " + e.getMessage());
        }

        return null;
    }

    /**
     * Actualiza los datos de una película existente en la base de datos
     *
     * @param c
     * @return
     */
    public boolean actualizar(Cartelera c) {
        String sql = "UPDATE Cartelera SET titulo = ?, director = ?, anio = ?, duracion = ?, genero = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            // Establece los nuevos valores para la actualización 
            ps.setString(1, c.getTitulo());
            ps.setString(2, c.getDirector());
            ps.setInt(3, c.getAnio());
            ps.setInt(4, c.getDuracion());
            ps.setString(5, c.getGenero());
            ps.setInt(6, c.getId());

            // Ejecuta la actualización
            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            System.out.println("Error actualizar(): " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina una película de base de datos usando su ID
     *
     * @param id
     * @return
     */
    public String eliminar(int id) {
        String sqlSelect = "SELECT * FROM cartelera WHERE id = ?";
        String sqlDelete = "DELETE FROM cartelera WHERE id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement psSelect = conn.prepareStatement(sqlSelect)) {

            psSelect.setInt(1, id);
            try (ResultSet rs = psSelect.executeQuery()) {
                if (!rs.next()) {
                    return "La película con ID " + id + " no existe en la base de datos.";
                }
            }

            // Si existe, procedemos a eliminar
            try (PreparedStatement psDelete = conn.prepareStatement(sqlDelete)) {
                psDelete.setInt(1, id);
                int filas = psDelete.executeUpdate();

                if (filas > 0) {
                    return "Película eliminada correctamente.";
                } else {
                    return "No se pudo eliminar la película.";
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al eliminar la película: " + e.getMessage();
        }
    }
}