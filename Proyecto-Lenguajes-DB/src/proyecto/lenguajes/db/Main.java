package proyecto.lenguajes.db;

import DB.ConexionProyecto;

public class Main {

    public static void main(String[] args) {
        ConexionProyecto conn = new ConexionProyecto();
        conn.desconectar();
    }

}
