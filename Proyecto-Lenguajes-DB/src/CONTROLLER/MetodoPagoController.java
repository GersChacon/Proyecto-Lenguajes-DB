package CONTROLLER;

import SERVICE.MetodoPagoService;
import java.util.List;
import MODEL.MetodoPago;

public class MetodoPagoController {

    private MetodoPagoService service = new MetodoPagoService();

    public void insertarMetodoPago(String nombre) {
        service.insertarMetodoPago(nombre);
    }

    public void actualizarMetodoPago(int idMetodoPago, String nombre) {
        service.actualizarMetodoPago(idMetodoPago, nombre);
    }

    public void eliminarMetodoPago(int idMetodoPago) {
        service.eliminarMetodoPago(idMetodoPago);
    }

    public List<MetodoPago> obtenerTodosLosMetodosPago() {
        return service.obtenerTodosLosMetodosPago();
    }
}
