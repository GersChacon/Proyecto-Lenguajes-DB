package DAO;

import MODEL.Proveedores;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DB.ConexionProyecto;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.OracleTypes;

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

    public List<Proveedores> obtenerTodosLosProveedores() {
        List<Proveedores> proveedores = new ArrayList<>();
        String sql = "{call ObtenerTodosProveedores(?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
                while (rs.next()) {
                    Proveedores proveedor = new Proveedores();
                    proveedor.setIdProveedor(rs.getInt("id_proveedor"));
                    proveedor.setNombre(rs.getString("nombre"));
                    proveedor.setTelefono(rs.getString("telefono"));
                    proveedor.setEmail(rs.getString("email"));
                    proveedor.setDireccion(rs.getString("direccion"));
                    proveedores.add(proveedor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proveedores;
    }
}
