package CONTROLLER;

import MODEL.Productos;
import SERVICE.ProductosService;
import java.util.List;

public class ProductosController {

    private ProductosService service = new ProductosService();

    public void insertarProducto(int idTipo, int idProveedor, String nombre, double precioKg, double stockKg) {
        service.insertarProducto(idTipo, idProveedor, nombre, precioKg, stockKg);
    }

    public void actualizarProducto(int idProducto, int idTipo, int idProveedor, String nombre, double precioKg, double stockKg) {
        service.actualizarProducto(idProducto, idTipo, idProveedor, nombre, precioKg, stockKg);
    }

    public void eliminarProducto(int idProducto) {
        service.eliminarProducto(idProducto);
    }

    public List<Productos> obtenerTodosLosProductos() {
        return service.obtenerTodosLosProductos();
    }
}
