package SERVICE;

import DAO.PedidosDao;
import java.util.List;
import MODEL.Pedidos;

public class PedidosService {

    private PedidosDao pedidosDao = new PedidosDao();

    public void insertarPedido(int idCliente, int idEstado, double montoPagado) {
        pedidosDao.insertarPedido(idCliente, idEstado, montoPagado);
    }

    public void actualizarPedido(int idPedido, int idCliente, int idEstado, double montoPagado) {
        pedidosDao.actualizarPedido(idPedido, idCliente, idEstado, montoPagado);
    }

    public void eliminarPedido(int idPedido) {
        pedidosDao.eliminarPedido(idPedido);
    }

    public List<Pedidos> obtenerTodosLosPedidos() {
        return pedidosDao.obtenerTodosLosPedidos();
    }
}
