# Sistema de Inventario - Tienda Electrónica

## Descripción

Sistema desarrollado en JavaFX para gestionar el módulo de inventarios de una tienda electrónica. Permite registrar, editar, eliminar y buscar productos, además de controlar entradas, salidas de stock, ventas y reportes.

## Tecnologías utilizadas

- Java JDK 21
- JavaFX SDK 21.0.11 LTS
- Eclipse IDE
- MySQL Server 8.0
- MySQL Workbench
- MySQL Connector/J
- JDBC

## Requisitos para ejecutar el sistema

Antes de ejecutar el proyecto, se debe tener instalado:

1. JDK 21.
2. Eclipse IDE.
3. JavaFX SDK 21.0.11 LTS.
4. MySQL Server 8.0.
5. MySQL Connector/J.

## Configuración de JavaFX en Eclipse

1. Clic derecho sobre el proyecto.
2. Ir a **Build Path → Configure Build Path**.
3. Entrar a **Libraries**.
4. Seleccionar **Add External JARs**.
5. Agregar todos los `.jar` de la carpeta:

```text
C:\javafx-sdk-21.0.11\lib
```

Luego configurar los argumentos de ejecución:

1. Ir a **Run → Run Configurations**.
2. Seleccionar la clase `AplicacionPrincipal`.
3. Entrar a la pestaña **Arguments**.
4. En **VM arguments** colocar:

```text
--module-path "C:\javafx-sdk-21.0.11\lib" --add-modules javafx.controls,javafx.fxml
```

Si JavaFX está en otra ubicación, se debe cambiar la ruta.

## Configuración de MySQL

En MySQL Workbench crear la base de datos:

```sql
CREATE DATABASE tienda_electronica;

USE tienda_electronica;

CREATE TABLE productos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(100),
    precioCompra DOUBLE,
    precioVenta DOUBLE,
    stock INT DEFAULT 0,
    proveedor VARCHAR(100)
);

CREATE TABLE compras (
    id INT PRIMARY KEY AUTO_INCREMENT,
    fecha VARCHAR(50) NOT NULL,
    proveedor VARCHAR(100) NOT NULL
);

CREATE TABLE detalle_compra (
    id INT PRIMARY KEY AUTO_INCREMENT,
    idCompra INT,
    idProducto INT,
    cantidad INT,
    precioCompra DOUBLE,
    FOREIGN KEY (idCompra) REFERENCES compras(id),
    FOREIGN KEY (idProducto) REFERENCES productos(id)
);
```

## Configuración de MySQL Connector/J en Eclipse

1. Descargar **MySQL Connector/J**.
2. Seleccionar la opción **Platform Independent**.
3. Descomprimir el archivo descargado.
4. Buscar el archivo:

```text
mysql-connector-j-8.0.46.jar
```

5. En Eclipse hacer clic derecho sobre el proyecto.
6. Ir a **Build Path → Configure Build Path**.
7. Entrar a **Libraries**.
8. Seleccionar **Classpath**.
9. Clic en **Add External JARs**.
10. Agregar el archivo `mysql-connector-j-8.0.46.jar`.
11. Clic en **Apply and Close**.

## Configuración de la conexión

En la clase `ConexionBD.java`, configurar los datos de conexión:

```java
private static final String URL = "jdbc:mysql://localhost:3306/tienda_electronica?useSSL=false&serverTimezone=UTC";
private static final String USUARIO = "root";
private static final String PASSWORD = "CAMBIAR_AQUI";
```

Se debe reemplazar `CAMBIAR_AQUI` por la contraseña configurada en MySQL.

Si la base de datos está en otro equipo o servidor, cambiar `localhost` por la IP del servidor:

```java
private static final String URL = "jdbc:mysql://192.168.1.50:3306/tienda_electronica?useSSL=false&serverTimezone=UTC";
```

## Ejecución del sistema

Para ejecutar correctamente:

1. Verificar que MySQL Server esté iniciado.
2. Verificar que la base de datos `tienda_electronica` exista.
3. Verificar que JavaFX esté agregado al Build Path.
4. Verificar que MySQL Connector/J esté agregado al Classpath.
5. Ejecutar la clase:

```text
AplicacionPrincipal.java
```

Con la opción:

```text
Run As → Java Application
```

## Estructura del proyecto

```text
src
│
├── AplicacionPrincipal.java
│
├── controlador
│   ├── ControladorPrincipal.java
│   ├── ControladorProductos.java
│   └── ControladorReportes.java
│
├── modelo
│   ├── ConexionBD.java
│   ├── Producto.java
│   └── ProductoDAO.java
│
└── vista
    ├── VistaPrincipal.java
    ├── VistaProductos.java
    └── VistaReportes.java
```

## Consideración para sucursales

Para que varias sucursales usen la misma base de datos, MySQL debe estar instalado en un servidor central.  
En cada computadora cliente se debe configurar la URL de conexión usando la IP del servidor.

Ejemplo:

```java
jdbc:mysql://192.168.1.50:3306/tienda_electronica
```

De esta forma, todas las sucursales trabajan con la misma base de datos centralizada.

## Errores comunes

### Error de JavaFX

```text
JavaFX runtime components are missing
```

Solución: revisar que los **VM arguments** de JavaFX estén configurados correctamente.

### Error de compatibilidad JavaFX

```text
Unsupported major.minor version 68.0
```

Solución: usar **JavaFX 21.0.11 LTS** en lugar de JavaFX 26.

### Error de MySQL

```text
No suitable driver found for jdbc:mysql
```

Solución: agregar `mysql-connector-j-8.0.46.jar` al **Classpath** del proyecto.

### Error de contraseña

```text
Access denied for user 'root'@'localhost'
```

Solución: revisar el usuario y contraseña en `ConexionBD.java`.
