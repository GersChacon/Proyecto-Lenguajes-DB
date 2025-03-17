package DAO;

import DB.ConexionProyecto;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class CategoriasDao {

    public void insertarCategoria(String nombre) {
        String sql = "{call InsertarCategoria(?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, nombre);
            stmt.execute();

            System.out.println("Categoria insertada correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarCategoria(int idCategoria, String nombre) {
        String sql = "{call ActualizarCategoria(?, ?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idCategoria);
            stmt.setString(2, nombre);
            stmt.execute();

            System.out.println("Categoria actualizada correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarCategoria(int idCategoria) {
        String sql = "{call EliminarCategoria(?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idCategoria);
            stmt.execute();

            System.out.println("Categoria eliminada correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
