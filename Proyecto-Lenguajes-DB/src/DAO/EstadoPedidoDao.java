package DAO;

import MODEL.EstadoPedido;
import DB.ConexionProyecto;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

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

    public List<EstadoPedido> obtenerTodosLosEstadosPedido() {
        List<EstadoPedido> estados = new ArrayList<>();
        String sql = "{call ObtenerEstadosPedido(?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
                while (rs.next()) {
                    EstadoPedido estado = new EstadoPedido();
                    estado.setIdEstado(rs.getInt("id_estado"));
                    estado.setNombre(rs.getString("nombre"));
                    estados.add(estado);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return estados;
    }

}
