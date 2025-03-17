package SERVICE;

import DAO.ProductosDao;

public class ProductosService {

    private ProductosDao productosDao = new ProductosDao();

    public void insertarProducto(int idTipo, int idProveedor, String nombre, double precioKg, double stockKg) {
        productosDao.insertarProducto(idTipo, idProveedor, nombre, precioKg, stockKg);
    }

    public void actualizarProducto(int idProducto, int idTipo, int idProveedor, String nombre, double precioKg, double stockKg) {
        productosDao.actualizarProducto(idProducto, idTipo, idProveedor, nombre, precioKg, stockKg);
    }

    public void eliminarProducto(int idProducto) {
        productosDao.eliminarProducto(idProducto);
    }
}
