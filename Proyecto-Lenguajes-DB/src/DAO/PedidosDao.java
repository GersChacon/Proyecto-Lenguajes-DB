package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import MODEL.Pedidos;
import DB.ConexionProyecto;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class PedidosDao {

    public void insertarPedido(int idCliente, int idEstado, double montoPagado) {
        String sql = "{call InsertarPedido(?, ?, ?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idCliente);
            stmt.setInt(2, idEstado);
            stmt.setDouble(3, montoPagado);
            stmt.execute();

            System.out.println("Pedido insertado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarPedido(int idPedido, int idCliente, int idEstado, double montoPagado) {
        String sql = "{call ActualizarPedido(?, ?, ?, ?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idPedido);
            stmt.setInt(2, idCliente);
            stmt.setInt(3, idEstado);
            stmt.setDouble(4, montoPagado);
            stmt.execute();

            System.out.println("Pedido actualizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarPedido(int idPedido) {
        String sql = "{call EliminarPedido(?)}";

        try (Connection conn = ConexionProyecto.obtenerConexion(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idPedido);
            stmt.execute();

            System.out.println("Pedido eliminado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Pedidos> obtenerTodosLosPedidos() {
        List<Pedidos> lista = new ArrayList<>();
        String sql = "SELECT id_pedido, id_cliente, id_estado, fecha_pedido, monto_pagado FROM VistaPedidos";

        try (Connection conn = ConexionProyecto.obtenerConexion(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pedidos pedido = new Pedidos();
                pedido.setIdPedido(rs.getInt("id_pedido"));
                pedido.setIdCliente(rs.getInt("id_cliente"));
                pedido.setIdEstado(rs.getInt("id_estado"));
                pedido.setFechaPedido(rs.getString("fecha_pedido"));
                pedido.setMontoPagado(rs.getDouble("monto_pagado"));
                lista.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
