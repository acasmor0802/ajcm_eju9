# EJERCICIOS U9

## Enunciado original

### Pool de conexiones con HikariCP¶
- Crea un programa en Kotlin usando HikariCP para insertar el siguiente usuario:
- nombre	email
- Reinaldo Girúndez	reingir@mail.com
- Realiza una consulta que muestre los pedidos realizados por "Facundo Pérez".

## Resolución del ejercicio

### Uso de HikariCP (DataSourceFactory.kt)
Para la conexión eficiente a la base de datos, se utiliza HikariCP, un pool de conexiones rápido y liviano.
- crea un configurado con:
    - URL JDBC para H2 embebido.
    - Usuario y contraseña para acceso.
    - Driver JDBC.
    - Tamaño máximo del pool (10 conexiones en este caso).

`DataSourceFactory``HikariDataSource`

Esta configuración permite reutilizar conexiones y mejora el rendimiento frente a abrir/cerrar conexiones repetidamente.
``` kotlin
fun create(): DataSource {
    val config = HikariConfig()
    config.jdbcUrl = "jdbc:h2:./db/eju9"
    config.username = "sa"
    config.password = ""
    config.driverClassName = "org.h2.Driver"
    config.maximumPoolSize = 10
    return HikariDataSource(config)
}
```
### Controlador (Controlador.kt)
El **Controlador** es el componente central que orquesta toda la interacción entre la lógica de negocio y la interfaz de usuario (consola). Su función principal es gestionar el ciclo de vida de la aplicación, presentando al usuario menús y leyendo sus opciones para ejecutar acciones concretas.
- Recibe las implementaciones de los servicios de usuario, producto, pedido y línea de pedido, que son las capas donde reside la lógica del negocio y acceso a datos.
- Gestiona la consola para mostrar textos, leer entradas y mostrar mensajes de error, proporcionando una experiencia interactiva.
- Controla el flujo principal con la variable , que mantiene la app ejecutándose mientras el usuario no decida salir. `curso`
- Implementa múltiples métodos privados que ejecutan las acciones concretas del menú, tales como crear usuarios, productos o pedidos, actualizar líneas, eliminar registros o consultar información.
- Cada método generalmente interactúa con un servicio específico y utiliza la consola para solicitar datos y mostrar resultados o errores al usuario.

Este diseño permite separar claramente la interacción con el usuario (UI) de la lógica de negocio, facilitando la mantenibilidad y extensibilidad del código. Además, al desacoplar los servicios y la consola mediante interfaces, es sencillo modificar o testear cada componente por separado.

### Consola (Consola.kt)
Implementa para manejar la entrada y salida de texto. `IEntradaSalida`
- `mostrar(texto, saltoLinea)` imprime texto en consola, opcional con salto de línea.
- `mostrarError(mensaje)` agrega un prefijo # ERROR para mensajes de error.
- `leer(prompt)` muestra un mensaje y lee entrada del usuario.
- `limpiar()` limpia pantalla dependiendo si está en terminal real o no.
- Métodos auxiliares como `pausar` (espera entrada para continuar).

Esta interfaz permite separar la lógica de UI de la implementación concreta de consola y facilita su reemplazo si se quisiera, por ejemplo, interfaz gráfica.
### Ejecución principal (Main.kt)
En `main()` se inicia el programa con el siguiente flujo:
1. Crear una instancia de consola para IO.
2. Crear el `DataSource` con HikariCP.
3. Instanciar los servicios de cada entidad, inyectando DAOs que a su vez usan el `DataSource`.
4. Crear el controlador pasándole todos los servicios, la consola y la fuente de datos.
5. Iniciar la aplicación con `controlador.iniciar()`.
6. Al final cerrar el pool de conexiones para liberar recursos.

Este enfoque modular separa responsabilidades claramente y facilita el mantenimiento y pruebas.
``` kotlin
fun main() {
    val consola = Consola()
    val dataSource = DataSourceFactory.create()

    val servicioLinea = LineaPedidoService(LineaPedidoDao(dataSource))
    val servicioPedido = PedidoService(PedidoDao(dataSource), LineaPedidoDao(dataSource))
    val servicioProducto = ProductoService(ProductoDao(dataSource))
    val servicioUsuario = UsuarioService(UsuarioDao(dataSource))

    val controlador = Controlador(servicioLinea, servicioPedido, servicioProducto, servicioUsuario, consola, dataSource)

    controlador.iniciar()

    if (dataSource is HikariDataSource) dataSource.close()
}
```
## Resumen
- : Núcleo controlador que orquesta la aplicación y usa la consola para interacción. **Controlador.kt**
- : Configura y crea el pool de conexiones HikariCP para conexión eficiente a BD. **DataSourceFactory.kt**
- : Implementa métodos para interactuar con usuario vía línea de comandos. **Consola.kt**
- : Monta todas partes y arranca el flujo principal. **Main.kt**
