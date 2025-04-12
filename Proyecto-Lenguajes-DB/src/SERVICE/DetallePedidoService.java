package SERVICE;

import DAO.DetallePedidoDao;
import java.util.List;
import MODEL.DetallePedido;

public class DetallePedidoService {

    private DetallePedidoDao detallePedidoDao = new DetallePedidoDao();

    public void insertarDetalle(int idPedido, int idProducto, double cantidadKg, double precioUnitario) {
        detallePedidoDao.insertarDetalle(idPedido, idProducto, cantidadKg, precioUnitario);
    }

    public void actualizarDetalle(int idDetalle, int idPedido, int idProducto,
            double cantidadKg, double precioUnitario) {
        detallePedidoDao.actualizarDetalle(idDetalle, idPedido, idProducto, cantidadKg, precioUnitario);
    }

    public void eliminarDetalle(int idDetalle) {
        detallePedidoDao.eliminarDetalle(idDetalle);
    }

    public List<DetallePedido> obtenerTodosLosDetalles() {
        return detallePedidoDao.obtenerTodosLosDetalles();
    }
}
