package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DB.ConexionProyecto;
import MODEL.Categorias;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.OracleTypes;

public class CategoriasDao {

    public void insertarCategoria(String nombre) {
        String sql = "{call InsertarCategoria(?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, nombre);
            stmt.execute();

            System.out.println("Categoria insertada correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarCategoria(int idCategoria, String nombre) {
        String sql = "{call ActualizarCategoria(?, ?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idCategoria);
            stmt.setString(2, nombre);
            stmt.execute();

            System.out.println("Categoria actualizada correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarCategoria(int idCategoria) {
        String sql = "{call EliminarCategoria(?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idCategoria);
            stmt.execute();

            System.out.println("Categoria eliminada correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Categorias> obtenerTodasLasCategorias() {
        List<Categorias> lista = new ArrayList<>();
        String sql = "{call ObtenerTodasLasCategorias(?)}"; // Asegúrate de tener este SP

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            ResultSet rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                Categorias cat = new Categorias();
                cat.setIdCategoria(rs.getInt("id_categoria")); // Asegúrate del nombre exacto de la columna
                cat.setNombre(rs.getString("nombre"));
                lista.add(cat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

}
