package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import MODEL.DetallePago;
import DB.ConexionProyecto;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class DetallePagoDao {

    public void insertarDetallePago(int idPago, int idMetodoPago, Integer idBanco,
            String numeroTarjeta, String nombreTitular,
            Date fechaExpiracion, String numeroTransferencia) {
        String sql = "{call InsertarDetallePago(?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idPago);
            stmt.setInt(2, idMetodoPago);
            if (idBanco != null) {
                stmt.setInt(3, idBanco);
            } else {
                stmt.setNull(3, Types.INTEGER);
            }
            stmt.setString(4, numeroTarjeta);
            stmt.setString(5, nombreTitular);
            if (fechaExpiracion != null) {
                stmt.setDate(6, new java.sql.Date(fechaExpiracion.getTime()));
            } else {
                stmt.setNull(6, Types.DATE);
            }
            stmt.setString(7, numeroTransferencia);
            stmt.execute();

            System.out.println("Detalle de pago insertado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarDetallePago(int idDetallePago, int idPago, int idMetodoPago,
            Integer idBanco, String numeroTarjeta,
            String nombreTitular, Date fechaExpiracion,
            String numeroTransferencia) {
        String sql = "{call ActualizarDetallePago(?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idDetallePago);
            stmt.setInt(2, idPago);
            stmt.setInt(3, idMetodoPago);
            if (idBanco != null) {
                stmt.setInt(4, idBanco);
            } else {
                stmt.setNull(4, Types.INTEGER);
            }
            stmt.setString(5, numeroTarjeta);
            stmt.setString(6, nombreTitular);
            if (fechaExpiracion != null) {
                stmt.setDate(7, new java.sql.Date(fechaExpiracion.getTime()));
            } else {
                stmt.setNull(7, Types.DATE);
            }
            stmt.setString(8, numeroTransferencia);
            stmt.execute();

            System.out.println("Detalle de pago actualizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarDetallePago(int idDetallePago) {
        String sql = "{call EliminarDetallePago(?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idDetallePago);
            stmt.execute();

            System.out.println("Detalle de pago eliminado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<DetallePago> obtenerTodosLosDetallesPago() {
        List<DetallePago> lista = new ArrayList<>();
        String sql = "SELECT id_detalle_pago, id_pago, id_metodo_pago, id_banco, "
                + "numero_tarjeta, nombre_titular, fecha_expiracion, numero_transferencia "
                + "FROM VistaDetallesPago";

        try (Connection conn = ConexionProyecto.obtenerConexion(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                DetallePago detalle = new DetallePago();
                detalle.setIdDetallePago(rs.getInt("id_detalle_pago"));
                detalle.setIdPago(rs.getInt("id_pago"));
                detalle.setIdMetodoPago(rs.getInt("id_metodo_pago"));
                int banco = rs.getInt("id_banco");
                detalle.setIdBanco(rs.wasNull() ? null : banco);
                detalle.setNumeroTarjeta(rs.getString("numero_tarjeta"));
                detalle.setNombreTitular(rs.getString("nombre_titular"));
                detalle.setFechaExpiracion(rs.getDate("fecha_expiracion"));
                detalle.setNumeroTransferencia(rs.getString("numero_transferencia"));
                lista.add(detalle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
