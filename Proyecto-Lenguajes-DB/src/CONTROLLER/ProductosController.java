package CONTROLLER;

import SERVICE.ProductosService;

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
}
