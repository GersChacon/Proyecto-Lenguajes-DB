package CONTROLLER;

import SERVICE.DetallePagoService;
import java.util.List;
import MODEL.DetallePago;
import java.util.Date;

public class DetallePagoController {

    private DetallePagoService service = new DetallePagoService();

    public void insertarDetallePago(int idPago, int idMetodoPago, Integer idBanco,
            String numeroTarjeta, String nombreTitular,
            Date fechaExpiracion, String numeroTransferencia) {
        service.insertarDetallePago(idPago, idMetodoPago, idBanco,
                numeroTarjeta, nombreTitular,
                fechaExpiracion, numeroTransferencia);
    }

    public void actualizarDetallePago(int idDetallePago, int idPago, int idMetodoPago,
            Integer idBanco, String numeroTarjeta,
            String nombreTitular, Date fechaExpiracion,
            String numeroTransferencia) {
        service.actualizarDetallePago(idDetallePago, idPago, idMetodoPago,
                idBanco, numeroTarjeta, nombreTitular,
                fechaExpiracion, numeroTransferencia);
    }

    public void eliminarDetallePago(int idDetallePago) {
        service.eliminarDetallePago(idDetallePago);
    }

    public List<DetallePago> obtenerTodosLosDetallesPago() {
        return service.obtenerTodosLosDetallesPago();
    }
}
