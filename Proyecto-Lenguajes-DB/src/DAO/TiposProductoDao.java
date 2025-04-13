package DAO;

import MODEL.TiposProducto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DB.ConexionProyecto;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.OracleTypes;

public class TiposProductoDao {

    public void insertarTipoProducto(int idCategoria, String nombre) {
        String sql = "{call InsertarTipoProducto(?, ?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idCategoria);
            stmt.setString(2, nombre);
            stmt.execute();

            System.out.println("Tipo de producto insertado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarTipoProducto(int idTipo, int idCategoria, String nombre) {
        String sql = "{call ActualizarTipoProducto(?, ?, ?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idTipo);
            stmt.setInt(2, idCategoria);
            stmt.setString(3, nombre);
            stmt.execute();

            System.out.println("Tipo de producto actualizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarTipoProducto(int idTipo) {
        String sql = "{call EliminarTipoProducto(?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idTipo);
            stmt.execute();

            System.out.println("Tipo de producto eliminado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<TiposProducto> obtenerTodosLosTiposProducto() {
        List<TiposProducto> tipos = new ArrayList<>();
        String sql = "{call ObtenerTodosTiposProducto(?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
                while (rs.next()) {
                    TiposProducto tipo = new TiposProducto();
                    tipo.setIdTipo(rs.getInt("id_tipo"));
                    tipo.setIdCategoria(rs.getInt("id_categoria"));
                    tipo.setNombre(rs.getString("nombre"));
                    tipos.add(tipo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tipos;
    }
}
