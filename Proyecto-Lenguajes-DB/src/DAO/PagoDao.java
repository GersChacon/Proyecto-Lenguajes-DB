package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import MODEL.Pago;
import DB.ConexionProyecto;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import oracle.jdbc.OracleTypes;

public class PagoDao {

    public void insertarPago(int idPedido, double monto, String estadoPago) {
        String sql = "{call InsertarPago(?, ?, ?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idPedido);
            stmt.setDouble(2, monto);
            stmt.setString(3, estadoPago);
            stmt.execute();

            System.out.println("Pago insertado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarPago(int idPago, int idPedido, double monto, String estadoPago) {
        String sql = "{call ActualizarPago(?, ?, ?, ?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idPago);
            stmt.setInt(2, idPedido);
            stmt.setDouble(3, monto);
            stmt.setString(4, estadoPago);
            stmt.execute();

            System.out.println("Pago actualizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarPago(int idPago) {
        String sql = "{call EliminarPago(?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idPago);
            stmt.execute();

            System.out.println("Pago eliminado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Pago> obtenerTodosLosPagos() {
        List<Pago> lista = new ArrayList<>();
        String sql = "{call ObtenerTodosPagos(?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
                while (rs.next()) {
                    Pago pago = new Pago();
                    pago.setIdPago(rs.getInt("id_pago"));
                    pago.setIdPedido(rs.getInt("id_pedido"));
                    pago.setMonto(rs.getDouble("monto"));
                    pago.setFechaPago(rs.getDate("fecha_pago"));
                    pago.setEstadoPago(rs.getString("estado_pago"));
                    lista.add(pago);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
