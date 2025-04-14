package SERVICE;

import DAO.CategoriasDao;
import MODEL.Categorias;
import java.util.List;

public class CategoriasService {

    private CategoriasDao categoriasDao = new CategoriasDao();

    public void insertarCategoria(String nombre) {
        categoriasDao.insertarCategoria(nombre);
    }

    public void actualizarCategoria(int idCategoria, String nombre) {
        categoriasDao.actualizarCategoria(idCategoria, nombre);
    }

    public void eliminarCategoria(int idCategoria) {
        categoriasDao.eliminarCategoria(idCategoria);
    }

    public List<Categorias> obtenerTodasLasCategorias() {
        return categoriasDao.obtenerTodasLasCategorias();
    }

}
