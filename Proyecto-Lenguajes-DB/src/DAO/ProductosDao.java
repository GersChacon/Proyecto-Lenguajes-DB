package DAO;

import DB.ConexionProyecto;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class ProductosDao {

    public void insertarProducto(int idTipo, int idProveedor, String nombre, double precioKg, double stockKg) {
        String sql = "{call InsertarProducto(?, ?, ?, ?, ?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idTipo);
            stmt.setInt(2, idProveedor);
            stmt.setString(3, nombre);
            stmt.setDouble(4, precioKg);
            stmt.setDouble(5, stockKg);
            stmt.execute();

            System.out.println("Producto insertado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarProducto(int idProducto, int idTipo, int idProveedor, String nombre, double precioKg, double stockKg) {
        String sql = "{call ActualizarProducto(?, ?, ?, ?, ?, ?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idProducto);
            stmt.setInt(2, idTipo);
            stmt.setInt(3, idProveedor);
            stmt.setString(4, nombre);
            stmt.setDouble(5, precioKg);
            stmt.setDouble(6, stockKg);
            stmt.execute();

            System.out.println("Producto actualizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarProducto(int idProducto) {
        String sql = "{call EliminarProducto(?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idProducto);
            stmt.execute();

            System.out.println("Producto eliminado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
