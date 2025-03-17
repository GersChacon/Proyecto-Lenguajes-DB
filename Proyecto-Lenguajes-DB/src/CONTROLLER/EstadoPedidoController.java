package CONTROLLER;

import SERVICE.EstadoPedidoService;

public class EstadoPedidoController {

    private EstadoPedidoService service = new EstadoPedidoService();

    public void insertarEstadoPedido(String nombre) {
        service.insertarEstadoPedido(nombre);
    }

    public void actualizarEstadoPedido(int idEstado, String nombre) {
        service.actualizarEstadoPedido(idEstado, nombre);
    }

    public void eliminarEstadoPedido(int idEstado) {
        service.eliminarEstadoPedido(idEstado);
    }
}
