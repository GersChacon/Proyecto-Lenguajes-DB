package CONTROLLER;

import SERVICE.CategoriasService;

public class CategoriasController {

    private CategoriasService service = new CategoriasService();

    public void insertarCategoria(String nombre) {
        service.insertarCategoria(nombre);
    }

    public void actualizarCategoria(int idCategoria, String nombre) {
        service.actualizarCategoria(idCategoria, nombre);
    }

    public void eliminarCategoria(int idCategoria) {
        service.eliminarCategoria(idCategoria);
    }
}
