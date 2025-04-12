package SERVICE;

import DAO.MetodoPagoDao;
import java.util.List;
import MODEL.MetodoPago;

public class MetodoPagoService {

    private MetodoPagoDao metodoPagoDao = new MetodoPagoDao();

    public void insertarMetodoPago(String nombre) {
        metodoPagoDao.insertarMetodoPago(nombre);
    }

    public void actualizarMetodoPago(int idMetodoPago, String nombre) {
        metodoPagoDao.actualizarMetodoPago(idMetodoPago, nombre);
    }

    public void eliminarMetodoPago(int idMetodoPago) {
        metodoPagoDao.eliminarMetodoPago(idMetodoPago);
    }

    public List<MetodoPago> obtenerTodosLosMetodosPago() {
        return metodoPagoDao.obtenerTodosLosMetodosPago();
    }
}
