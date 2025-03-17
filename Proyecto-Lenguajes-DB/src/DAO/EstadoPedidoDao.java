package DAO;

import DB.ConexionProyecto;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class EstadoPedidoDao {

    public void insertarEstadoPedido(String nombre) {
        String sql = "{call InsertarEstadoPedido(?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, nombre);
            stmt.execute();

            System.out.println("Estado de pedido insertado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarEstadoPedido(int idEstado, String nombre) {
        String sql = "{call ActualizarEstadoPedido(?, ?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idEstado);
            stmt.setString(2, nombre);
            stmt.execute();

            System.out.println("Estado de pedido actualizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarEstadoPedido(int idEstado) {
        String sql = "{call EliminarEstadoPedido(?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idEstado);
            stmt.execute();

            System.out.println("Estado de pedido eliminado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
