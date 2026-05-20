package controlador;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import modelo.Producto;
import modelo.ProductoDAO;
import vista.VistaReportes;

public class ControladorReportes {

    private final VistaReportes vista;
    private final ProductoDAO dao;

    public ControladorReportes(VistaReportes vista) {
        this.vista = vista;
        this.dao = new ProductoDAO();

        configurarBotones();
        cargarReportes();
    }

    // Configura la acción del botón cerrar
    private void configurarBotones() {
        vista.getBotonCerrar().setOnAction(e -> 
            vista.getBotonCerrar().getScene().getWindow().hide()
        );
    }

    // Carga los productos en la tabla y calcula las estadísticas del inventario
    private void cargarReportes() {
        var lista = dao.listarTodos();

        vista.getTablaReportes().getItems().clear();
        vista.getTablaReportes().getItems().addAll(lista);

        int totalProductos = lista.size();

        double valorInventario = lista.stream()
                .mapToDouble(p -> p.getStock() * p.getPrecioVenta())
                .sum();

        long bajoStock = lista.stream()
                .filter(p -> p.getStock() < 5)
                .count();

        // Muestra los resultados calculados en la vista
        vista.getLblTotalProductos().setText("Total de productos: " + totalProductos);
        vista.getLblValorInventario().setText(String.format("Valor total del inventario: $%.2f", valorInventario));
        vista.getLblProductosBajoStock().setText("Productos con stock bajo: " + bajoStock);
    }
}