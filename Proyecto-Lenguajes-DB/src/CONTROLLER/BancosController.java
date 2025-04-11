package CONTROLLER;

import SERVICE.BancosService;
import java.util.List;
import MODEL.Bancos;

public class BancosController {

    private BancosService service = new BancosService();

    public void insertarBanco(String nombre, String direccion, String telefono, String email) {
        service.insertarBanco(nombre, direccion, telefono, email);
    }

    public void actualizarBanco(int idBanco, String nombre, String direccion, String telefono, String email) {
        service.actualizarBanco(idBanco, nombre, direccion, telefono, email);
    }

    public void eliminarBanco(int idBanco) {
        service.eliminarBanco(idBanco);
    }

    public List<Bancos> obtenerTodosLosBancos() {
        return service.obtenerTodosLosBancos();
    }
}
