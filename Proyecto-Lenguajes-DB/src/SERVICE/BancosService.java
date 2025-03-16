package SERVICE;

import DAO.BancosDao;

public class BancosService {

    private BancosDao bancosDao = new BancosDao();

    public void insertarBanco(String nombre, String direccion, String telefono, String email) {
        bancosDao.insertarBanco(nombre, direccion, telefono, email);
    }

    public void actualizarBanco(int idBanco, String nombre, String direccion, String telefono, String email) {
        bancosDao.actualizarBanco(idBanco, nombre, direccion, telefono, email);
    }

    public void eliminarBanco(int idBanco) {
        bancosDao.eliminarBanco(idBanco);
    }
}
