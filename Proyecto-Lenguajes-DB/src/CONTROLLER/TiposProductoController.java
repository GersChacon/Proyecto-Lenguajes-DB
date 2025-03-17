package CONTROLLER;

import SERVICE.TiposProductoService;

public class TiposProductoController {

    private TiposProductoService service = new TiposProductoService();

    public void insertarTipoProducto(int idCategoria, String nombre) {
        service.insertarTipoProducto(idCategoria, nombre);
    }

    public void actualizarTipoProducto(int idTipo, int idCategoria, String nombre) {
        service.actualizarTipoProducto(idTipo, idCategoria, nombre);
    }

    public void eliminarTipoProducto(int idTipo) {
        service.eliminarTipoProducto(idTipo);
    }
}
