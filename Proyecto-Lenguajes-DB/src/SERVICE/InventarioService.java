package SERVICE;

import DAO.InventarioDao;
import java.util.List;
import MODEL.Inventario;

public class InventarioService {

    private InventarioDao inventarioDao = new InventarioDao();

    public void insertarMovimiento(int idProducto, String tipoMovimiento,
            double cantidadKg, Integer idDetallePedido) {
        inventarioDao.insertarMovimiento(idProducto, tipoMovimiento, cantidadKg, idDetallePedido);
    }

    public void actualizarMovimiento(int idMovimiento, int idProducto, String tipoMovimiento,
            double cantidadKg, Integer idDetallePedido) {
        inventarioDao.actualizarMovimiento(idMovimiento, idProducto, tipoMovimiento, cantidadKg, idDetallePedido);
    }

    public void eliminarMovimiento(int idMovimiento) {
        inventarioDao.eliminarMovimiento(idMovimiento);
    }

    public List<Inventario> obtenerTodosLosMovimientos() {
        return inventarioDao.obtenerTodosLosMovimientos();
    }
}
