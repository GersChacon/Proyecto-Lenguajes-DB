package MODEL;

import java.util.Date;

public class Inventario {

    private int idMovimiento;
    private int idProducto;
    private String tipoMovimiento;
    private double cantidadKg;
    private Date fechaMovimiento;
    private Integer idDetallePedido;

    public Inventario() {
    }

    public Inventario(int idMovimiento, int idProducto, String tipoMovimiento,
            double cantidadKg, Date fechaMovimiento, Integer idDetallePedido) {
        this.idMovimiento = idMovimiento;
        this.idProducto = idProducto;
        this.tipoMovimiento = tipoMovimiento;
        this.cantidadKg = cantidadKg;
        this.fechaMovimiento = fechaMovimiento;
        this.idDetallePedido = idDetallePedido;
    }

    // Getters y Setters
    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public double getCantidadKg() {
        return cantidadKg;
    }

    public void setCantidadKg(double cantidadKg) {
        this.cantidadKg = cantidadKg;
    }

    public Date getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(Date fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public Integer getIdDetallePedido() {
        return idDetallePedido;
    }

    public void setIdDetallePedido(Integer idDetallePedido) {
        this.idDetallePedido = idDetallePedido;
    }
}
