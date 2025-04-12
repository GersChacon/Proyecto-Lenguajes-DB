package CONTROLLER;

import SERVICE.InventarioService;
import java.util.List;
import MODEL.Inventario;

public class InventarioController {

    private InventarioService service = new InventarioService();

    public void insertarMovimiento(int idProducto, String tipoMovimiento,
            double cantidadKg, Integer idDetallePedido) {
        service.insertarMovimiento(idProducto, tipoMovimiento, cantidadKg, idDetallePedido);
    }

    public void actualizarMovimiento(int idMovimiento, int idProducto, String tipoMovimiento,
            double cantidadKg, Integer idDetallePedido) {
        service.actualizarMovimiento(idMovimiento, idProducto, tipoMovimiento, cantidadKg, idDetallePedido);
    }

    public void eliminarMovimiento(int idMovimiento) {
        service.eliminarMovimiento(idMovimiento);
    }

    public List<Inventario> obtenerTodosLosMovimientos() {
        return service.obtenerTodosLosMovimientos();
    }
}
