package SERVICE;

import DAO.DetallePagoDao;
import java.util.List;
import MODEL.DetallePago;
import java.util.Date;

public class DetallePagoService {

    private DetallePagoDao detallePagoDao = new DetallePagoDao();

    public void insertarDetallePago(int idPago, int idMetodoPago, Integer idBanco,
            String numeroTarjeta, String nombreTitular,
            Date fechaExpiracion, String numeroTransferencia) {
        detallePagoDao.insertarDetallePago(idPago, idMetodoPago, idBanco,
                numeroTarjeta, nombreTitular,
                fechaExpiracion, numeroTransferencia);
    }

    public void actualizarDetallePago(int idDetallePago, int idPago, int idMetodoPago,
            Integer idBanco, String numeroTarjeta,
            String nombreTitular, Date fechaExpiracion,
            String numeroTransferencia) {
        detallePagoDao.actualizarDetallePago(idDetallePago, idPago, idMetodoPago,
                idBanco, numeroTarjeta, nombreTitular,
                fechaExpiracion, numeroTransferencia);
    }

    public void eliminarDetallePago(int idDetallePago) {
        detallePagoDao.eliminarDetallePago(idDetallePago);
    }

    public List<DetallePago> obtenerTodosLosDetallesPago() {
        return detallePagoDao.obtenerTodosLosDetallesPago();
    }
}
