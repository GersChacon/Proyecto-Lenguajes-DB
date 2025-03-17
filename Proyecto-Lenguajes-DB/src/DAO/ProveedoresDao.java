package DAO;

import DB.ConexionProyecto;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class ProveedoresDao {

    public void insertarProveedor(String nombre, String telefono, String email, String direccion) {
        String sql = "{call InsertarProveedor(?, ?, ?, ?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, nombre);
            stmt.setString(2, telefono);
            stmt.setString(3, email);
            stmt.setString(4, direccion);
            stmt.execute();

            System.out.println("Proveedor insertado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarProveedor(int idProveedor, String nombre, String telefono, String email, String direccion) {
        String sql = "{call ActualizarProveedor(?, ?, ?, ?, ?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idProveedor);
            stmt.setString(2, nombre);
            stmt.setString(3, telefono);
            stmt.setString(4, email);
            stmt.setString(5, direccion);
            stmt.execute();

            System.out.println("Proveedor actualizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarProveedor(int idProveedor) {
        String sql = "{call EliminarProveedor(?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idProveedor);
            stmt.execute();

            System.out.println("Proveedor eliminado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
