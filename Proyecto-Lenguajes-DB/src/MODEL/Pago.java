package MODEL;

import java.util.Date;

public class Pago {

    private int idPago;
    private int idPedido;
    private double monto;
    private Date fechaPago;
    private String estadoPago;

    public Pago() {
    }

    public Pago(int idPago, int idPedido, double monto, Date fechaPago, String estadoPago) {
        this.idPago = idPago;
        this.idPedido = idPedido;
        this.monto = monto;
        this.fechaPago = fechaPago;
        this.estadoPago = estadoPago;
    }

    // Getters y Setters
    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }
}
