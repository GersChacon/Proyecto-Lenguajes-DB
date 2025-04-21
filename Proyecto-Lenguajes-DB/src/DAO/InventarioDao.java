package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import MODEL.Inventario;
import DB.ConexionProyecto;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import oracle.jdbc.OracleTypes;

public class InventarioDao {

    public void insertarMovimiento(int idProducto, String tipoMovimiento,
            double cantidadKg, Integer idDetallePedido) {
        String sql = "{call InsertarInventario(?, ?, ?, ?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idProducto);
            stmt.setString(2, tipoMovimiento);
            stmt.setDouble(3, cantidadKg);
            if (idDetallePedido != null) {
                stmt.setInt(4, idDetallePedido);
            } else {
                stmt.setNull(4, Types.INTEGER);
            }
            stmt.execute();

            System.out.println("Movimiento de inventario insertado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarMovimiento(int idMovimiento, int idProducto, String tipoMovimiento,
            double cantidadKg, Integer idDetallePedido) {
        String sql = "{call ActualizarInventario(?, ?, ?, ?, ?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idMovimiento);
            stmt.setInt(2, idProducto);
            stmt.setString(3, tipoMovimiento);
            stmt.setDouble(4, cantidadKg);
            if (idDetallePedido != null) {
                stmt.setInt(5, idDetallePedido);
            } else {
                stmt.setNull(5, Types.INTEGER);
            }
            stmt.execute();

            System.out.println("Movimiento de inventario actualizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarMovimiento(int idMovimiento) {
        String sql = "{call EliminarInventario(?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idMovimiento);
            stmt.execute();

            System.out.println("Movimiento de inventario eliminado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Inventario> obtenerTodosLosMovimientos() {
        List<Inventario> lista = new ArrayList<>();
        String sql = "{call ObtenerTodosMovimientosInventario(?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
                while (rs.next()) {
                    Inventario movimiento = new Inventario();
                    movimiento.setIdMovimiento(rs.getInt("id_movimiento"));
                    movimiento.setIdProducto(rs.getInt("id_producto"));
                    movimiento.setTipoMovimiento(rs.getString("tipo_movimiento"));
                    movimiento.setCantidadKg(rs.getDouble("cantidad_kg"));
                    movimiento.setFechaMovimiento(rs.getDate("fecha_movimiento"));
                    int detalle = rs.getInt("id_detalle_pedido");
                    movimiento.setIdDetallePedido(rs.wasNull() ? null : detalle);
                    lista.add(movimiento);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
