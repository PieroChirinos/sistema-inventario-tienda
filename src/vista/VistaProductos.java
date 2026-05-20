package vista;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import modelo.Producto;

public class VistaProductos {

    private Stage ventana;
    private TableView<Producto> tablaProductos;
    private TextField txtBuscar;

    private Button botonAgregar;
    private Button botonEditar;
    private Button botonEliminar;
    private Button botonRefrescar;
    private Button botonEntradaStock;
    private Button botonSalidaStock;
    private Button botonRegistrarVenta;

    public VistaProductos() {
        ventana = new Stage();
        ventana.setTitle("Gestionar Productos");

        // Contenedor principal de la ventana
        BorderPane raiz = new BorderPane();

        // Barra superior: buscador y botones principales
        VBox barraSuperior = new VBox(10);
        barraSuperior.setPadding(new Insets(10));

        HBox buscadorBox = new HBox(10);
        buscadorBox.setAlignment(Pos.CENTER_LEFT);

        Label lblBuscar = new Label("Buscar:");
        txtBuscar = new TextField();
        txtBuscar.setPromptText("Nombre o categoría...");
        txtBuscar.setPrefWidth(300);

        buscadorBox.getChildren().addAll(lblBuscar, txtBuscar);

        HBox botonesPrincipales = new HBox(8);
        botonesPrincipales.setAlignment(Pos.CENTER_LEFT);

        botonAgregar = new Button("Agregar Producto");
        botonEditar = new Button("Editar");
        botonEliminar = new Button("Eliminar");
        botonRefrescar = new Button("Refrescar");

        botonesPrincipales.getChildren().addAll(
                botonAgregar,
                botonEditar,
                botonEliminar,
                botonRefrescar
        );

        barraSuperior.getChildren().addAll(buscadorBox, botonesPrincipales);

        // Barra inferior: acciones de stock y venta
        HBox barraInferior = new HBox(15);
        barraInferior.setPadding(new Insets(10));
        barraInferior.setAlignment(Pos.CENTER);

        HBox izquierda = new HBox(10);
        botonEntradaStock = new Button("Entrada de Stock");
        botonSalidaStock = new Button("Salida de Stock");
        izquierda.getChildren().addAll(botonEntradaStock, botonSalidaStock);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        botonRegistrarVenta = new Button("Registrar Venta");

        barraInferior.getChildren().addAll(izquierda, spacer, botonRegistrarVenta);

        // Tabla donde se mostrarán los productos
        tablaProductos = new TableView<>();

        TableColumn<Producto, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Producto, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Producto, String> colCategoria = new TableColumn<>("Categoria");
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        TableColumn<Producto, Double> colPrecioCompra = new TableColumn<>("Precio Compra");
        colPrecioCompra.setCellValueFactory(new PropertyValueFactory<>("precioCompra"));

        TableColumn<Producto, Double> colPrecioVenta = new TableColumn<>("Precio Venta");
        colPrecioVenta.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));

        TableColumn<Producto, Integer> colStock = new TableColumn<>("Stock");
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        tablaProductos.getColumns().addAll(
                colId,
                colNombre,
                colCategoria,
                colPrecioCompra,
                colPrecioVenta,
                colStock
        );

        // Distribución de los elementos en la ventana
        raiz.setTop(barraSuperior);
        raiz.setCenter(tablaProductos);
        raiz.setBottom(barraInferior);

        Scene escena = new Scene(raiz, 1150, 650);
        ventana.setScene(escena);
    }

    public void mostrar() {
        ventana.show();
    }

    // Getters usados por el controlador para acceder a los componentes
    public TableView<Producto> getTablaProductos() { return tablaProductos; }
    public TextField getTxtBuscar() { return txtBuscar; }

    public Button getBotonAgregar() { return botonAgregar; }
    public Button getBotonEditar() { return botonEditar; }
    public Button getBotonEliminar() { return botonEliminar; }
    public Button getBotonRefrescar() { return botonRefrescar; }
    public Button getBotonEntradaStock() { return botonEntradaStock; }
    public Button getBotonSalidaStock() { return botonSalidaStock; }
    public Button getBotonRegistrarVenta() { return botonRegistrarVenta; }
}