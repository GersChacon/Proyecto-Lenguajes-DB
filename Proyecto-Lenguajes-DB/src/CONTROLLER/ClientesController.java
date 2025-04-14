package CONTROLLER;

import MODEL.Clientes;
import SERVICE.ClientesService;
import java.util.List;

public class ClientesController {

    private ClientesService service = new ClientesService();

    public void insertarCliente(String nombre, String direccion, String telefono, String email) {
        service.insertarCliente(nombre, direccion, telefono, email);
    }

    public void actualizarCliente(int idCliente, String nombre, String direccion, String telefono, String email) {
        service.actualizarCliente(idCliente, nombre, direccion, telefono, email);
    }

    public void eliminarCliente(int idCliente) {
        service.eliminarCliente(idCliente);
    }

    public List<Clientes> obtenerTodosLosClientes() {
        return service.obtenerTodosLosClientes();
    }
}
