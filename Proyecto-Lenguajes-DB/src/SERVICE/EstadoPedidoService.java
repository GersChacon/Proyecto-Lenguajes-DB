package SERVICE;

import DAO.EstadoPedidoDao;

public class EstadoPedidoService {

    private EstadoPedidoDao estadosPedidoDao = new EstadoPedidoDao();

    public void insertarEstadoPedido(String nombre) {
        estadosPedidoDao.insertarEstadoPedido(nombre);
    }

    public void actualizarEstadoPedido(int idEstado, String nombre) {
        estadosPedidoDao.actualizarEstadoPedido(idEstado, nombre);
    }

    public void eliminarEstadoPedido(int idEstado) {
        estadosPedidoDao.eliminarEstadoPedido(idEstado);
    }
}
