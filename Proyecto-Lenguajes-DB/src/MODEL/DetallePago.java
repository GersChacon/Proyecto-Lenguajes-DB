package MODEL;

import java.util.Date;

public class DetallePago {

    private int idDetallePago;
    private int idPago;
    private int idMetodoPago;
    private Integer idBanco;
    private String numeroTarjeta;
    private String nombreTitular;
    private Date fechaExpiracion;
    private String numeroTransferencia;

    public DetallePago() {
    }

    public DetallePago(int idDetallePago, int idPago, int idMetodoPago, Integer idBanco,
            String numeroTarjeta, String nombreTitular, Date fechaExpiracion,
            String numeroTransferencia) {
        this.idDetallePago = idDetallePago;
        this.idPago = idPago;
        this.idMetodoPago = idMetodoPago;
        this.idBanco = idBanco;
        this.numeroTarjeta = numeroTarjeta;
        this.nombreTitular = nombreTitular;
        this.fechaExpiracion = fechaExpiracion;
        this.numeroTransferencia = numeroTransferencia;
    }

    // Getters y Setters
    public int getIdDetallePago() {
        return idDetallePago;
    }

    public void setIdDetallePago(int idDetallePago) {
        this.idDetallePago = idDetallePago;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public int getIdMetodoPago() {
        return idMetodoPago;
    }

    public void setIdMetodoPago(int idMetodoPago) {
        this.idMetodoPago = idMetodoPago;
    }

    public Integer getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(Integer idBanco) {
        this.idBanco = idBanco;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getNombreTitular() {
        return nombreTitular;
    }

    public void setNombreTitular(String nombreTitular) {
        this.nombreTitular = nombreTitular;
    }

    public Date getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(Date fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getNumeroTransferencia() {
        return numeroTransferencia;
    }

    public void setNumeroTransferencia(String numeroTransferencia) {
        this.numeroTransferencia = numeroTransferencia;
    }
}
