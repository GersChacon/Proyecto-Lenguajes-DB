package MODEL;

public class Pedidos {

    private int idPedido;
    private int idCliente;
    private int idEstado;
    private String fechaPedido;
    private double montoPagado;

    public Pedidos() {
    }

    public Pedidos(int idPedido, int idCliente, int idEstado, String fechaPedido, double montoPagado) {
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.idEstado = idEstado;
        this.fechaPedido = fechaPedido;
        this.montoPagado = montoPagado;
    }

    // Getters y Setters
    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public double getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(double montoPagado) {
        this.montoPagado = montoPagado;
    }
}
