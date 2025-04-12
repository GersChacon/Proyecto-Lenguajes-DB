package CONTROLLER;

import SERVICE.PagoService;
import java.util.List;
import MODEL.Pago;

public class PagoController {

    private PagoService service = new PagoService();

    public void insertarPago(int idPedido, double monto, String estadoPago) {
        service.insertarPago(idPedido, monto, estadoPago);
    }

    public void actualizarPago(int idPago, int idPedido, double monto, String estadoPago) {
        service.actualizarPago(idPago, idPedido, monto, estadoPago);
    }

    public void eliminarPago(int idPago) {
        service.eliminarPago(idPago);
    }

    public List<Pago> obtenerTodosLosPagos() {
        return service.obtenerTodosLosPagos();
    }
}
