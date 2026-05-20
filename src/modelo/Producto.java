package modelo;

public class Producto {

    // Atributos que representan los datos de un producto
    private int id;
    private String nombre;
    private String categoria;
    private double precioCompra;
    private double precioVenta;
    private int stock;
    private String proveedor;

    public Producto() {}

    // Constructor para crear un producto con todos sus datos
    public Producto(int id, String nombre, String categoria, double precioCompra, 
                    double precioVenta, int stock, String proveedor) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.stock = stock;
        this.proveedor = proveedor;
    }

    // Getters y setters para acceder y modificar los datos del producto
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public double getPrecioCompra() { return precioCompra; }
    public void setPrecioCompra(double precioCompra) { this.precioCompra = precioCompra; }

    public double getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(double precioVenta) { this.precioVenta = precioVenta; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getProveedor() { return proveedor; }
    public void setProveedor(String proveedor) { this.proveedor = proveedor; }
}