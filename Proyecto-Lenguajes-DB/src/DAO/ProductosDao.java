package DAO;

import MODEL.Productos;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DB.ConexionProyecto;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.OracleTypes;

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

    public List<Productos> obtenerTodosLosProductos() {
        List<Productos> productos = new ArrayList<>();
        String sql = "{call ObtenerTodosProductos(?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
                while (rs.next()) {
                    Productos producto = new Productos();
                    producto.setIdProducto(rs.getInt("id_producto"));
                    producto.setIdTipo(rs.getInt("id_tipo"));
                    producto.setIdProveedor(rs.getInt("id_proveedor"));
                    producto.setNombre(rs.getString("nombre"));
                    producto.setPrecioKg(rs.getDouble("precio_kg"));
                    producto.setStockKg(rs.getDouble("stock_kg"));
                    productos.add(producto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }
}
