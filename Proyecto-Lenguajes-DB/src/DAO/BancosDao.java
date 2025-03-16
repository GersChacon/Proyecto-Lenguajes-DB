package DAO;

import MODEL.Bancos;
import DB.ConexionProyecto;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class BancosDao {

    public void insertarBanco(String nombre, String direccion, String telefono, String email) {
        String sql = "{call InsertarBanco(?, ?, ?, ?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, nombre);
            stmt.setString(2, direccion);
            stmt.setString(3, telefono);
            stmt.setString(4, email);
            stmt.execute();

            System.out.println("Bancos insertado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarBanco(int idBancos, String nombre, String direccion, String telefono, String email) {
        String sql = "{call ActualizarBanco(?, ?, ?, ?, ?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idBancos);
            stmt.setString(2, nombre);
            stmt.setString(3, direccion);
            stmt.setString(4, telefono);
            stmt.setString(5, email);
            stmt.execute();

            System.out.println("Bancos actualizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarBanco(int idBancos) {
        String sql = "{call EliminarBanco(?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idBancos);
            stmt.execute();

            System.out.println("Bancos eliminado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
