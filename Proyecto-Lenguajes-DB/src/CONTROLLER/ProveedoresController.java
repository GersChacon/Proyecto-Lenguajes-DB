package CONTROLLER;

import SERVICE.ProveedoresService;

public class ProveedoresController {

    private ProveedoresService service = new ProveedoresService();

    public void insertarProveedor(String nombre, String telefono, String email, String direccion) {
        service.insertarProveedor(nombre, telefono, email, direccion);
    }

    public void actualizarProveedor(int idProveedor, String nombre, String telefono, String email, String direccion) {
        service.actualizarProveedor(idProveedor, nombre, telefono, email, direccion);
    }

    public void eliminarProveedor(int idProveedor) {
        service.eliminarProveedor(idProveedor);
    }
}
