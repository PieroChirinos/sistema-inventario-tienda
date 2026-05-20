package controlador;

import vista.VistaPrincipal;
import vista.VistaProductos;
import vista.VistaReportes;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import modelo.ConexionBD;

public class ControladorPrincipal {

    private final VistaPrincipal vista;

    public ControladorPrincipal(VistaPrincipal vista) {
        this.vista = vista;
    }

    // Abre la ventana de gestión de productos y conecta su controlador
    public void abrirGestionProductos() {
        VistaProductos vistaProductos = new VistaProductos();
        new ControladorProductos(vistaProductos);
        vistaProductos.mostrar();
    }

    // Abre la ventana de reportes y conecta su controlador
    public void abrirReportes() {
        VistaReportes vistaReportes = new VistaReportes();
        new ControladorReportes(vistaReportes);
        vistaReportes.mostrar();
    }

    // Muestra una confirmación antes de cerrar la aplicación
    public void salirAplicacion() {
        Alert confirmacion = new Alert(AlertType.CONFIRMATION);
        confirmacion.setTitle("Salir");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Estás seguro de que deseas salir de la aplicación?");

        confirmacion.showAndWait().ifPresent(respuesta -> {
            if (respuesta == ButtonType.OK) {
                // Cierra la conexión con la base de datos antes de salir
                ConexionBD.cerrar();
                System.exit(0);
            }
        });
    }
}