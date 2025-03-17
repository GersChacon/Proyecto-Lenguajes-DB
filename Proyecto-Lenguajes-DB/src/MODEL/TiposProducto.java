package MODEL;

public class TiposProducto {

    private int idTipo;
    private int idCategoria;
    private String nombre;

    public TiposProducto() {
    }

    public TiposProducto(int idTipo, int idCategoria, String nombre) {
        this.idTipo = idTipo;
        this.idCategoria = idCategoria;
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
