package vista;

import controlador.ControladorPrincipal;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class VistaPrincipal {

    private final BorderPane raiz;
    private Button botonProductos;
    private Button botonReportes;
    private Button botonSalir;

    public VistaPrincipal() {
        // Contenedor principal de la vista
        raiz = new BorderPane();

        // Cabecera de la aplicación
        VBox cabecera = new VBox();
        cabecera.setStyle("-fx-background-color: #2C3E50;");
        cabecera.setPadding(new Insets(20, 30, 20, 30));

        Label titulo = new Label("Tienda Electrónica");
        titulo.setStyle("-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold;");

        Label subtitulo = new Label("Sistema de Gestion de Inventario y Ventas");
        subtitulo.setStyle("-fx-text-fill: #BDC3C7; -fx-font-size: 16px;");

        cabecera.getChildren().addAll(titulo, subtitulo);

        // Zona central con los botones principales del sistema
        VBox centro = new VBox(25);
        centro.setAlignment(Pos.CENTER);
        centro.setPadding(new Insets(100, 0, 0, 0));
        centro.setStyle("-fx-background-color: #ECF0F1;");

        botonProductos = crearBotonGrande("Gestionar Productos ", "#3498DB");
        botonReportes = crearBotonGrande("Ver Reportes y Estadísticas", "#8E44AD");
        botonSalir = crearBotonGrande("Salir", "#E74C3C");

        centro.getChildren().addAll(botonProductos, botonReportes, botonSalir);

        raiz.setTop(cabecera);
        raiz.setCenter(centro);
    }

    // Método reutilizable para crear botones grandes con estilo
    private Button crearBotonGrande(String texto, String color) {
        Button boton = new Button(texto);
        boton.setPrefWidth(520);
        boton.setPrefHeight(80);
        boton.setStyle("-fx-font-size: 20px; -fx-background-color: " + color + "; -fx-text-fill: white;");
        return boton;
    }

    public BorderPane obtenerRaiz() {
        return raiz;
    }

    // Asigna las acciones de los botones mediante el controlador
    public void setControlador(ControladorPrincipal controlador) {
        botonProductos.setOnAction(e -> controlador.abrirGestionProductos());
        botonReportes.setOnAction(e -> controlador.abrirReportes());
        botonSalir.setOnAction(e -> controlador.salirAplicacion());
    }
}