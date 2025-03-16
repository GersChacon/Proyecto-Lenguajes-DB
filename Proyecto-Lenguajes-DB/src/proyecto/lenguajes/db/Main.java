package proyecto.lenguajes.db;

import CONTROLLER.BancosController;
import DB.ConexionProyecto;

public class Main {

    public static void main(String[] args) {
        BancosController controlador = new BancosController();

        // Insertar un banco
        //controlador.insertarBanco("Banco Central", "Av. Principal 123", "2222-3333", "contacto@bancocentral.com");

        // Actualizar un banco
        //controlador.actualizarBanco(1, "Banco Actualizado", "Nueva Dirección 456", "4444-5555", "nuevo@banco.com");

        // Eliminar un banco
        controlador.eliminarBanco(1);
    }
}
