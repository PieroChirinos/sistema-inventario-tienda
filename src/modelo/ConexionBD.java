package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/tienda_electronica?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "nombre01";

    private static Connection conexion;

    public static Connection obtenerConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                System.err.println("No se encontró el driver de MySQL");
                e.printStackTrace();
            }

            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("Conectado a MySQL - tienda_electronica");
            crearTablas();
        }
        return conexion;
    }

    private static void crearTablas() {
        String sqlProductos = """
            CREATE TABLE IF NOT EXISTS productos (
                id INT PRIMARY KEY AUTO_INCREMENT,
                nombre VARCHAR(100) NOT NULL,
                categoria VARCHAR(100),
                precioCompra DOUBLE,
                precioVenta DOUBLE,
                stock INT DEFAULT 0,
                proveedor VARCHAR(100)
            );
        """;

        String sqlCompras = """
            CREATE TABLE IF NOT EXISTS compras (
                id INT PRIMARY KEY AUTO_INCREMENT,
                fecha VARCHAR(50) NOT NULL,
                proveedor VARCHAR(100) NOT NULL
            );
        """;

        String sqlDetalleCompra = """
            CREATE TABLE IF NOT EXISTS detalle_compra (
                id INT PRIMARY KEY AUTO_INCREMENT,
                idCompra INT,
                idProducto INT,
                cantidad INT,
                precioCompra DOUBLE,
                FOREIGN KEY (idCompra) REFERENCES compras(id),
                FOREIGN KEY (idProducto) REFERENCES productos(id)
            );
        """;

        try (Statement stmt = conexion.createStatement()) {
            stmt.execute(sqlProductos);
            stmt.execute(sqlCompras);
            stmt.execute(sqlDetalleCompra);
            System.out.println("Tablas verificadas correctamente");
        } catch (SQLException e) {
            System.err.println("Error al crear tablas: " + e.getMessage());
        }
    }

    public static void cerrar() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión a MySQL cerrada correctamente");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}