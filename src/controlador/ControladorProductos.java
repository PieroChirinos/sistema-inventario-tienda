package controlador;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import modelo.Producto;
import modelo.ProductoDAO;
import vista.VistaProductos;

import java.util.List;
import java.util.stream.Collectors;

public class ControladorProductos {

    private final VistaProductos vista;
    private final ProductoDAO dao;

    public ControladorProductos(VistaProductos vista) {
        this.vista = vista;
        this.dao = new ProductoDAO();

        configurarBotones();
        cargarProductosEnTabla();
        configurarBuscador();
    }

    // Asigna las acciones principales a los botones de la vista
    private void configurarBotones() {
        vista.getBotonAgregar().setOnAction(e -> agregarProducto());
        vista.getBotonEditar().setOnAction(e -> editarProducto());
        vista.getBotonEliminar().setOnAction(e -> eliminarProducto());
        vista.getBotonRefrescar().setOnAction(e -> cargarProductosEnTabla());
        vista.getBotonEntradaStock().setOnAction(e -> entradaDeStock());
        vista.getBotonSalidaStock().setOnAction(e -> salidaDeStock());
        vista.getBotonRegistrarVenta().setOnAction(e -> registrarVenta());
    }

    // Permite filtrar productos mientras se escribe en el buscador
    private void configurarBuscador() {
        vista.getTxtBuscar().textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarProductos(newValue);
        });
    }

    // Carga todos los productos registrados en la tabla
    private void cargarProductosEnTabla() {
        List<Producto> todos = dao.listarTodos();
        vista.getTablaProductos().getItems().clear();
        vista.getTablaProductos().getItems().addAll(todos);
    }

    private void filtrarProductos(String texto) {
        String busqueda = texto.toLowerCase().trim();
        List<Producto> todos = dao.listarTodos();

        List<Producto> filtrados = todos.stream()
                .filter(p -> p.getNombre().toLowerCase().contains(busqueda) ||
                             p.getCategoria().toLowerCase().contains(busqueda))
                .collect(Collectors.toList());

        vista.getTablaProductos().getItems().clear();
        vista.getTablaProductos().getItems().addAll(filtrados);
    }

    // Abre el formulario para registrar un nuevo producto
    private void agregarProducto() {
        mostrarFormulario(null);
    }

    // Abre el formulario con los datos del producto seleccionado
    private void editarProducto() {
        Producto seleccionado = vista.getTablaProductos().getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarMensaje("Advertencia", "Debes seleccionar un producto para editar");
            return;
        }

        mostrarFormulario(seleccionado);
    }

    // Elimina un producto previa confirmación del usuario
    private void eliminarProducto() {
        Producto seleccionado = vista.getTablaProductos().getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarMensaje("Advertencia", "Debes seleccionar un producto para eliminar");
            return;
        }

        Alert confirmacion = new Alert(AlertType.CONFIRMATION);
        confirmacion.setTitle("Eliminar Producto");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Estás seguro de eliminar el producto: " + seleccionado.getNombre() + "?");

        confirmacion.showAndWait().ifPresent(respuesta -> {
            if (respuesta == javafx.scene.control.ButtonType.OK) {
                if (dao.eliminar(seleccionado.getId())) {
                    mostrarMensaje("Éxito", "Producto eliminado correctamente");
                    cargarProductosEnTabla();
                } else {
                    mostrarMensaje("Error", "No se pudo eliminar el producto");
                }
            }
        });
    }

    // Registra una entrada de stock para el producto seleccionado
    private void entradaDeStock() {
        Producto seleccionado = vista.getTablaProductos().getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarMensaje("Advertencia", "Debes seleccionar un producto para agregar stock");
            return;
        }

        mostrarVentanaCantidad(seleccionado, true);
    }

    // Registra una salida de stock para el producto seleccionado
    private void salidaDeStock() {
        Producto seleccionado = vista.getTablaProductos().getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarMensaje("Advertencia", "Debes seleccionar un producto para reducir stock");
            return;
        }

        mostrarVentanaCantidad(seleccionado, false);
    }

    // Inicia el proceso de venta de un producto seleccionado
    private void registrarVenta() {
        Producto seleccionado = vista.getTablaProductos().getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarMensaje("Advertencia", "Debes seleccionar un producto para registrar la venta");
            return;
        }

        mostrarFormularioVenta(seleccionado);
    }

    // Ventana para registrar una venta y calcular el total automáticamente
    private void mostrarFormularioVenta(Producto producto) {
        Stage ventana = new Stage();
        ventana.setTitle("Registrar Venta");
        ventana.initModality(Modality.APPLICATION_MODAL);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Producto:"), 0, 0);
        grid.add(new Label(producto.getNombre()), 1, 0);

        grid.add(new Label("Precio de Venta:"), 0, 1);
        grid.add(new Label(String.valueOf(producto.getPrecioVenta())), 1, 1);

        grid.add(new Label("Stock actual:"), 0, 2);
        grid.add(new Label(String.valueOf(producto.getStock())), 1, 2);

        grid.add(new Label("Cantidad a vender:"), 0, 3);
        TextField txtCantidad = new TextField();
        grid.add(txtCantidad, 1, 3);

        Label lblTotal = new Label("Total: 0.00");
        grid.add(lblTotal, 1, 4);

        txtCantidad.textProperty().addListener((obs, old, nuevo) -> {
            try {
                int cantidad = Integer.parseInt(nuevo);
                double total = cantidad * producto.getPrecioVenta();
                lblTotal.setText("Total: " + String.format("%.2f", total));
            } catch (Exception ex) {
                lblTotal.setText("Total: 0.00");
            }
        });

        Button btnRegistrar = new Button("Registrar Venta");
        grid.add(btnRegistrar, 1, 5);

        btnRegistrar.setOnAction(e -> {
            try {
                int cantidad = Integer.parseInt(txtCantidad.getText().trim());

                if (cantidad <= 0) {
                    mostrarMensaje("Error", "La cantidad debe ser mayor a 0");
                    return;
                }

                if (cantidad > producto.getStock()) {
                    mostrarMensaje("Error", "No hay suficiente stock para esta venta");
                    return;
                }

                boolean resultado = dao.disminuirStock(producto.getId(), cantidad);

                if (resultado) {
                    double total = cantidad * producto.getPrecioVenta();
                    mostrarMensaje("Éxito", "Venta registrada correctamente\nTotal: " + String.format("%.2f", total));
                    ventana.close();
                    cargarProductosEnTabla();
                } else {
                    mostrarMensaje("Error", "No se pudo registrar la venta");
                }

            } catch (NumberFormatException ex) {
                mostrarMensaje("Error", "La cantidad debe ser un número entero");
            }
        });

        Scene escena = new Scene(grid, 420, 280);
        ventana.setScene(escena);
        ventana.show();
    }

    // Ventana reutilizable para aumentar o disminuir stock
    private void mostrarVentanaCantidad(Producto producto, boolean esEntrada) {
        Stage ventana = new Stage();
        ventana.setTitle(esEntrada ? "Entrada de Stock" : "Salida de Stock");
        ventana.initModality(Modality.APPLICATION_MODAL);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Producto:"), 0, 0);
        grid.add(new Label(producto.getNombre()), 1, 0);

        grid.add(new Label("Stock actual:"), 0, 1);
        grid.add(new Label(String.valueOf(producto.getStock())), 1, 1);

        grid.add(new Label("Cantidad:"), 0, 2);
        TextField txtCantidad = new TextField();
        grid.add(txtCantidad, 1, 2);

        Button btnConfirmar = new Button(esEntrada ? "Agregar Stock" : "Reducir Stock");
        grid.add(btnConfirmar, 1, 3);

        btnConfirmar.setOnAction(e -> {
            try {
                int cantidad = Integer.parseInt(txtCantidad.getText().trim());

                if (cantidad <= 0) {
                    mostrarMensaje("Error", "La cantidad debe ser mayor a 0");
                    return;
                }

                boolean resultado;

                if (esEntrada) {
                    resultado = dao.aumentarStock(producto.getId(), cantidad);
                } else {
                    resultado = dao.disminuirStock(producto.getId(), cantidad);
                }

                if (resultado) {
                    mostrarMensaje("Éxito", esEntrada ? "Stock agregado correctamente" : "Stock reducido correctamente");
                    ventana.close();
                    cargarProductosEnTabla();
                } else {
                    mostrarMensaje("Error", esEntrada ? "No se pudo agregar stock" : "No hay suficiente stock o error al reducir");
                }

            } catch (NumberFormatException ex) {
                mostrarMensaje("Error", "La cantidad debe ser un número entero");
            }
        });

        Scene escena = new Scene(grid, 400, 220);
        ventana.setScene(escena);
        ventana.show();
    }

    // Formulario usado tanto para agregar como para editar productos
    private void mostrarFormulario(Producto productoExistente) {
        Stage ventana = new Stage();
        ventana.setTitle(productoExistente == null ? "Agregar Nuevo Producto" : "Editar Producto");
        ventana.initModality(Modality.APPLICATION_MODAL);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Nombre:"), 0, 0);
        TextField txtNombre = new TextField();
        grid.add(txtNombre, 1, 0);

        grid.add(new Label("Categoría:"), 0, 1);
        TextField txtCategoria = new TextField();
        grid.add(txtCategoria, 1, 1);

        grid.add(new Label("Precio Compra:"), 0, 2);
        TextField txtPrecioCompra = new TextField();
        grid.add(txtPrecioCompra, 1, 2);

        grid.add(new Label("Precio Venta:"), 0, 3);
        TextField txtPrecioVenta = new TextField();
        grid.add(txtPrecioVenta, 1, 3);

        grid.add(new Label("Stock:"), 0, 4);
        TextField txtStock = new TextField();
        grid.add(txtStock, 1, 4);

        grid.add(new Label("Proveedor:"), 0, 5);
        TextField txtProveedor = new TextField();
        grid.add(txtProveedor, 1, 5);

        Button btnGuardar = new Button(productoExistente == null ? "Guardar Producto" : "Actualizar Producto");
        grid.add(btnGuardar, 1, 6);

        if (productoExistente != null) {
            txtNombre.setText(productoExistente.getNombre());
            txtCategoria.setText(productoExistente.getCategoria());
            txtPrecioCompra.setText(String.valueOf(productoExistente.getPrecioCompra()));
            txtPrecioVenta.setText(String.valueOf(productoExistente.getPrecioVenta()));
            txtStock.setText(String.valueOf(productoExistente.getStock()));
            txtProveedor.setText(productoExistente.getProveedor());
        }

        btnGuardar.setOnAction(e -> {
            String nombre = txtNombre.getText().trim();

            if (nombre.isEmpty()) {
                mostrarMensaje("Error", "El nombre del producto es obligatorio");
                return;
            }

            double precioCompra;
            double precioVenta;
            int stock;

            try {
                precioCompra = Double.parseDouble(txtPrecioCompra.getText().trim());
                precioVenta = Double.parseDouble(txtPrecioVenta.getText().trim());
                stock = Integer.parseInt(txtStock.getText().trim());
            } catch (NumberFormatException ex) {
                mostrarMensaje("Error", "Los campos de precio y stock deben ser números");
                return;
            }

            if (precioCompra <= 0 || precioVenta <= 0) {
                mostrarMensaje("Error", "Los precios deben ser mayores a 0");
                return;
            }

            if (precioVenta < precioCompra) {
                mostrarMensaje("Error", "El precio de venta no puede ser menor que el precio de compra");
                return;
            }

            if (stock < 0) {
                mostrarMensaje("Error", "El stock no puede ser negativo");
                return;
            }

            Producto p = productoExistente != null ? productoExistente : new Producto();
            p.setNombre(nombre);
            p.setCategoria(txtCategoria.getText().trim());
            p.setPrecioCompra(precioCompra);
            p.setPrecioVenta(precioVenta);
            p.setStock(stock);
            p.setProveedor(txtProveedor.getText().trim());

            boolean resultado = productoExistente == null ? dao.agregar(p) : dao.actualizar(p);

            if (resultado) {
                mostrarMensaje("Éxito", productoExistente == null ? "Producto agregado correctamente" : "Producto actualizado correctamente");
                ventana.close();
                cargarProductosEnTabla();
            } else {
                mostrarMensaje("Error", "No se pudo guardar el producto");
            }
        });

        Scene escena = new Scene(grid, 450, 380);
        ventana.setScene(escena);
        ventana.show();
    }

    // Muestra mensajes de información, advertencia o error al usuario
    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}