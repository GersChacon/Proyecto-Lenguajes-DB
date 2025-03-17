package SERVICE;

import DAO.ProveedoresDao;

public class ProveedoresService {

    private ProveedoresDao proveedoresDao = new ProveedoresDao();

    public void insertarProveedor(String nombre, String telefono, String email, String direccion) {
        proveedoresDao.insertarProveedor(nombre, telefono, email, direccion);
    }

    public void actualizarProveedor(int idProveedor, String nombre, String telefono, String email, String direccion) {
        proveedoresDao.actualizarProveedor(idProveedor, nombre, telefono, email, direccion);
    }

    public void eliminarProveedor(int idProveedor) {
        proveedoresDao.eliminarProveedor(idProveedor);
    }
}
