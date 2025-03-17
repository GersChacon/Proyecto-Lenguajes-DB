package MODEL;

public class EstadoPedido {

    private int idEstado;
    private String nombre;

    public EstadoPedido() {
    }

    public EstadoPedido(int idEstado, String nombre) {
        this.idEstado = idEstado;
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
