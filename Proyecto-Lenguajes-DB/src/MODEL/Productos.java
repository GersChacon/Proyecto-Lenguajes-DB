package MODEL;

public class Productos {

    private int idProducto;
    private int idTipo;
    private int idProveedor;
    private String nombre;
    private double precioKg;
    private double stockKg;

    public Productos() {
    }

    public Productos(int idProducto, int idTipo, int idProveedor, String nombre, double precioKg, double stockKg) {
        this.idProducto = idProducto;
        this.idTipo = idTipo;
        this.idProveedor = idProveedor;
        this.nombre = nombre;
        this.precioKg = precioKg;
        this.stockKg = stockKg;
    }

    // Getters y Setters
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecioKg() {
        return precioKg;
    }

    public void setPrecioKg(double precioKg) {
        this.precioKg = precioKg;
    }

    public double getStockKg() {
        return stockKg;
    }

    public void setStockKg(double stockKg) {
        this.stockKg = stockKg;
    }
}
