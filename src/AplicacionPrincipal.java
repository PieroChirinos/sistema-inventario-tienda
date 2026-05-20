import controlador.ControladorPrincipal;
import vista.VistaPrincipal;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AplicacionPrincipal extends Application {

    @Override
    public void start(Stage escenario) {
        // Crea la vista principal y su controlador
        VistaPrincipal vista = new VistaPrincipal();
        ControladorPrincipal controlador = new ControladorPrincipal(vista);

        // Conecta la vista con el controlador
        vista.setControlador(controlador);

        // Crea la escena principal de la aplicación
        Scene escena = new Scene(vista.obtenerRaiz(), 1100, 700);

        escenario.setTitle("Tienda Electrónica - Compra y Venta");
        escenario.setScene(escena);
        escenario.setMinWidth(1100);
        escenario.setMinHeight(700);
        escenario.show();
    }

    // Punto de inicio de la aplicación JavaFX
    public static void main(String[] args) {
        launch(args);
    }
}