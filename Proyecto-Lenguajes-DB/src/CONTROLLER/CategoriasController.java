package CONTROLLER;

import MODEL.Categorias;
import SERVICE.CategoriasService;
import java.util.List;

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

    public List<Categorias> obtenerTodasLasCategorias() {
        return service.obtenerTodasLasCategorias();
    }

}
