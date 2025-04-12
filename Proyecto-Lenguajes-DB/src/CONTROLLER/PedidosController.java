package CONTROLLER;

import SERVICE.PedidosService;
import java.util.List;
import MODEL.Pedidos;

public class PedidosController {

    private PedidosService service = new PedidosService();

    public void insertarPedido(int idCliente, int idEstado, double montoPagado) {
        service.insertarPedido(idCliente, idEstado, montoPagado);
    }

    public void actualizarPedido(int idPedido, int idCliente, int idEstado, double montoPagado) {
        service.actualizarPedido(idPedido, idCliente, idEstado, montoPagado);
    }

    public void eliminarPedido(int idPedido) {
        service.eliminarPedido(idPedido);
    }

    public List<Pedidos> obtenerTodosLosPedidos() {
        return service.obtenerTodosLosPedidos();
    }
}
