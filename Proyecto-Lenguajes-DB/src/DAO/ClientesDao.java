package DAO;

import DB.ConexionProyecto;
import MODEL.Clientes;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class ClientesDao {

    public void insertarCliente(String nombre, String direccion, String telefono, String email) {
        String sql = "{call InsertarCliente(?, ?, ?, ?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, nombre);
            stmt.setString(2, direccion);
            stmt.setString(3, telefono);
            stmt.setString(4, email);
            stmt.execute();

            System.out.println("Cliente insertado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarCliente(int idCliente, String nombre, String direccion, String telefono, String email) {
        String sql = "{call ActualizarCliente(?, ?, ?, ?, ?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idCliente);
            stmt.setString(2, nombre);
            stmt.setString(3, direccion);
            stmt.setString(4, telefono);
            stmt.setString(5, email);
            stmt.execute();

            System.out.println("Cliente actualizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarCliente(int idCliente) {
        String sql = "{call EliminarCliente(?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idCliente);
            stmt.execute();

            System.out.println("Cliente eliminado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Clientes> obtenerTodosLosClientes() {
        List<Clientes> lista = new ArrayList<>();
        String sql = "{call ObtenerTodosLosClientes(?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            ResultSet rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                Clientes cliente = new Clientes();
                cliente.setIdCliente(rs.getInt("id_cliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setEmail(rs.getString("email"));
                lista.add(cliente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

}
