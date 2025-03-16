package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionProyecto {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String USUARIO = "proyecto";
    private static final String CONTRASENA = "proyectoDB";

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
}
