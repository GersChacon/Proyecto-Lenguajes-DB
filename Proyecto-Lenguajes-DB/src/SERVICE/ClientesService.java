package SERVICE;

import DAO.ClientesDao;
import MODEL.Clientes;
import java.util.List;

public class ClientesService {

    private ClientesDao clientesDao = new ClientesDao();

    public void insertarCliente(String nombre, String direccion, String telefono, String email) {
        clientesDao.insertarCliente(nombre, direccion, telefono, email);
    }

    public void actualizarCliente(int idCliente, String nombre, String direccion, String telefono, String email) {
        clientesDao.actualizarCliente(idCliente, nombre, direccion, telefono, email);
    }

    public void eliminarCliente(int idCliente) {
        clientesDao.eliminarCliente(idCliente);
    }

    public List<Clientes> obtenerTodosLosClientes() {
        return clientesDao.obtenerTodosLosClientes();
    }
}
