package vista;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import modelo.Producto;

public class VistaReportes {

    private Stage ventana;
    private TableView<Producto> tablaReportes;
    private Label lblTotalProductos;
    private Label lblValorInventario;
    private Label lblProductosBajoStock;
    private Button botonCerrar;

    public VistaReportes() {
        ventana = new Stage();
        ventana.setTitle("Reportes y Estadísticas");

        // Contenedor principal de la ventana
        BorderPane raiz = new BorderPane();

        // Sección superior con el resumen general del inventario
        VBox resumen = new VBox(10);
        resumen.setPadding(new Insets(15));
        resumen.setAlignment(Pos.CENTER_LEFT);

        lblTotalProductos = new Label("Total de productos: 0");
        lblValorInventario = new Label("Valor total del inventario: $0.00");
        lblProductosBajoStock = new Label("Productos con stock bajo: 0");

        resumen.getChildren().addAll(
                lblTotalProductos,
                lblValorInventario,
                lblProductosBajoStock
        );

        // Tabla donde se mostrarán los productos del reporte
        tablaReportes = new TableView<>();

        TableColumn<Producto, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Producto, String> colCategoria = new TableColumn<>("Categoría");
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        TableColumn<Producto, Integer> colStock = new TableColumn<>("Stock");
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        TableColumn<Producto, Double> colPrecioVenta = new TableColumn<>("Precio Venta");
        colPrecioVenta.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));

        tablaReportes.getColumns().addAll(
                colNombre,
                colCategoria,
                colStock,
                colPrecioVenta
        );

        // Botón inferior para cerrar la ventana
        HBox botones = new HBox(10);
        botones.setPadding(new Insets(10));
        botones.setAlignment(Pos.CENTER);

        botonCerrar = new Button("Cerrar");
        botones.getChildren().add(botonCerrar);

        // Distribución de los elementos en la ventana
        raiz.setTop(resumen);
        raiz.setCenter(tablaReportes);
        raiz.setBottom(botones);

        Scene escena = new Scene(raiz, 900, 600);
        ventana.setScene(escena);
    }

    public void mostrar() {
        ventana.show();
    }

    // Getters usados por el controlador para acceder a los componentes
    public TableView<Producto> getTablaReportes() { return tablaReportes; }
    public Label getLblTotalProductos() { return lblTotalProductos; }
    public Label getLblValorInventario() { return lblValorInventario; }
    public Label getLblProductosBajoStock() { return lblProductosBajoStock; }
    public Button getBotonCerrar() { return botonCerrar; }
}