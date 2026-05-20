package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    // Lista todos los productos registrados en la base de datos
    public List<Producto> listarTodos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos ORDER BY nombre";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setCategoria(rs.getString("categoria"));
                p.setPrecioCompra(rs.getDouble("precioCompra"));
                p.setPrecioVenta(rs.getDouble("precioVenta"));
                p.setStock(rs.getInt("stock"));
                p.setProveedor(rs.getString("proveedor"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Inserta un nuevo producto en la base de datos
    public boolean agregar(Producto p) {
        String sql = "INSERT INTO productos (nombre, categoria, precioCompra, precioVenta, stock, proveedor) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNombre());
            stmt.setString(2, p.getCategoria());
            stmt.setDouble(3, p.getPrecioCompra());
            stmt.setDouble(4, p.getPrecioVenta());
            stmt.setInt(5, p.getStock());
            stmt.setString(6, p.getProveedor());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Actualiza los datos de un producto existente
    public boolean actualizar(Producto p) {
        String sql = "UPDATE productos SET nombre=?, categoria=?, precioCompra=?, precioVenta=?, stock=?, proveedor=? WHERE id=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNombre());
            stmt.setString(2, p.getCategoria());
            stmt.setDouble(3, p.getPrecioCompra());
            stmt.setDouble(4, p.getPrecioVenta());
            stmt.setInt(5, p.getStock());
            stmt.setString(6, p.getProveedor());
            stmt.setInt(7, p.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Elimina un producto según su ID
    public boolean eliminar(int id) {
        String sql = "DELETE FROM productos WHERE id=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Aumenta el stock de un producto
    public boolean aumentarStock(int id, int cantidad) {
        String sql = "UPDATE productos SET stock = stock + ? WHERE id = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cantidad);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Disminuye el stock solo si existe suficiente cantidad disponible
    public boolean disminuirStock(int id, int cantidad) {
        String sql = "UPDATE productos SET stock = stock - ? WHERE id = ? AND stock >= ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cantidad);
            stmt.setInt(2, id);
            stmt.setInt(3, cantidad);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}