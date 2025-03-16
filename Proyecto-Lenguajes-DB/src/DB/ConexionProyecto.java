package DB;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionProyecto {

    private Connection conn = null;
    private String url, user, pass;

    public ConexionProyecto() {
        conectar();
    }

    private void conectar() {
        try {
            Class.forName("oracle.jdbc.OracleDriver"); //Driver BD
            url = "jdbc:oracle:thin:@localhost:1521:orcl";
            user = "proyecto";
            pass = "proyectoDB";
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Conectado!");
        } catch (Exception e) {
            System.out.println("Error, no se pudo conectar");
        }
    }

    public void desconectar() {
        try {
            conn.close();
            System.out.println("Desconectado!");
        } catch (Exception e) {
            System.out.println("Error, no se pudo desconectar");
        }
    }
}
