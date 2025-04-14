package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import MODEL.DetallePedido;
import DB.ConexionProyecto;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.OracleTypes;

public class DetallePedidoDao {

    public void insertarDetalle(int idPedido, int idProducto, double cantidadKg, double precioUnitario) {
        String sql = "{call InsertarDetallePedido(?, ?, ?, ?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idPedido);
            stmt.setInt(2, idProducto);
            stmt.setDouble(3, cantidadKg);
            stmt.setDouble(4, precioUnitario);
            stmt.execute();

            System.out.println("Detalle de pedido insertado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarDetalle(int idDetalle, int idPedido, int idProducto,
            double cantidadKg, double precioUnitario) {
        String sql = "{call ActualizarDetallePedido(?, ?, ?, ?, ?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idDetalle);
            stmt.setInt(2, idPedido);
            stmt.setInt(3, idProducto);
            stmt.setDouble(4, cantidadKg);
            stmt.setDouble(5, precioUnitario);
            stmt.execute();

            System.out.println("Detalle de pedido actualizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarDetalle(int idDetalle) {
        String sql = "{call EliminarDetallePedido(?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idDetalle);
            stmt.execute();

            System.out.println("Detalle de pedido eliminado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<DetallePedido> obtenerTodosLosDetalles() {
        List<DetallePedido> lista = new ArrayList<>();
        String sql = "{call ObtenerTodosDetallesPedido(?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
                while (rs.next()) {
                    DetallePedido detalle = new DetallePedido();
                    detalle.setIdDetalle(rs.getInt("id_detalle"));
                    detalle.setIdPedido(rs.getInt("id_pedido"));
                    detalle.setIdProducto(rs.getInt("id_producto"));
                    detalle.setCantidadKg(rs.getDouble("cantidad_kg"));
                    detalle.setPrecioUnitario(rs.getDouble("precio_unitario"));
                    detalle.setSubtotal(rs.getDouble("subtotal"));
                    lista.add(detalle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
