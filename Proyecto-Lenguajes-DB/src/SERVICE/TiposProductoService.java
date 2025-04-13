package SERVICE;

import DAO.TiposProductoDao;
import MODEL.TiposProducto;
import java.util.List;

public class TiposProductoService {

    private TiposProductoDao tiposProductoDao = new TiposProductoDao();

    public void insertarTipoProducto(int idCategoria, String nombre) {
        tiposProductoDao.insertarTipoProducto(idCategoria, nombre);
    }

    public void actualizarTipoProducto(int idTipo, int idCategoria, String nombre) {
        tiposProductoDao.actualizarTipoProducto(idTipo, idCategoria, nombre);
    }

    public void eliminarTipoProducto(int idTipo) {
        tiposProductoDao.eliminarTipoProducto(idTipo);
    }

    public List<TiposProducto> obtenerTodosLosTiposProducto() {
        return tiposProductoDao.obtenerTodosLosTiposProducto();
    }
}
