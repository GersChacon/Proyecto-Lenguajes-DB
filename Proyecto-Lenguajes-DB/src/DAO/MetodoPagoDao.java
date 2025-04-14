package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import MODEL.MetodoPago;
import DB.ConexionProyecto;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.OracleTypes;

public class MetodoPagoDao {

    public void insertarMetodoPago(String nombre) {
        String sql = "{call InsertarMetodoPago(?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, nombre);
            stmt.execute();

            System.out.println("Método de pago insertado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarMetodoPago(int idMetodoPago, String nombre) {
        String sql = "{call ActualizarMetodoPago(?, ?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idMetodoPago);
            stmt.setString(2, nombre);
            stmt.execute();

            System.out.println("Método de pago actualizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarMetodoPago(int idMetodoPago) {
        String sql = "{call EliminarMetodoPago(?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idMetodoPago);
            stmt.execute();

            System.out.println("Método de pago eliminado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<MetodoPago> obtenerTodosLosMetodosPago() {
        List<MetodoPago> lista = new ArrayList<>();
        String sql = "{call ObtenerTodosMetodosPago(?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(1)) {
                while (rs.next()) {
                    MetodoPago metodo = new MetodoPago();
                    metodo.setIdMetodoPago(rs.getInt("id_metodo_pago"));
                    metodo.setNombre(rs.getString("nombre"));
                    lista.add(metodo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
