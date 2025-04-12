package CONTROLLER;

import SERVICE.DetallePedidoService;
import java.util.List;
import MODEL.DetallePedido;

public class DetallePedidoController {

    private DetallePedidoService service = new DetallePedidoService();

    public void insertarDetalle(int idPedido, int idProducto, double cantidadKg, double precioUnitario) {
        service.insertarDetalle(idPedido, idProducto, cantidadKg, precioUnitario);
    }

    public void actualizarDetalle(int idDetalle, int idPedido, int idProducto,
            double cantidadKg, double precioUnitario) {
        service.actualizarDetalle(idDetalle, idPedido, idProducto, cantidadKg, precioUnitario);
    }

    public void eliminarDetalle(int idDetalle) {
        service.eliminarDetalle(idDetalle);
    }

    public List<DetallePedido> obtenerTodosLosDetalles() {
        return service.obtenerTodosLosDetalles();
    }
}
