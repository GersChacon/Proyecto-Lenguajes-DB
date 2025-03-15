package DB;

import java.sql.Connection;

public class ConexionProyecto {

    private Connection conn = null;
    private String url, user, pass;

    public ConexionProyecto() {
        conectar();
    }

    private void conectar() {
        try {
            Class.forName(""); //Driver BD
            url = "";
            user = "proyecto";
            pass = "clave";
//            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Conectado!");
        } catch (Exception e) {
            System.out.println("Error, no se pudo conectar");
        }
    }

    public void desconectar() {
        try {
            conn.close();
        } catch (Exception e) {
            System.out.println("Error, no se pudo desconectar");
        }
    }
}
