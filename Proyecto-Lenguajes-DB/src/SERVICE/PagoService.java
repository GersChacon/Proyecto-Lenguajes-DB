package SERVICE;

import DAO.PagoDao;
import java.util.List;
import MODEL.Pago;

public class PagoService {

    private PagoDao pagoDao = new PagoDao();

    public void insertarPago(int idPedido, double monto, String estadoPago) {
        pagoDao.insertarPago(idPedido, monto, estadoPago);
    }

    public void actualizarPago(int idPago, int idPedido, double monto, String estadoPago) {
        pagoDao.actualizarPago(idPago, idPedido, monto, estadoPago);
    }

    public void eliminarPago(int idPago) {
        pagoDao.eliminarPago(idPago);
    }

    public List<Pago> obtenerTodosLosPagos() {
        return pagoDao.obtenerTodosLosPagos();
    }
}
