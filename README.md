# EJERCICIOS U9

## Enunciado original

#### Ejercicio 5: Modificaciones con manejo de errores¶
- Modifica el precio del producto «Abanico» para ponerlo en oferta (por ejemplo, 120 €).
- Modifica la línea de pedido con id = 3:
- Cambia el producto a "Abanico" (id = 2)
- Cambia el precio al doble del precio actual del "Abanico".
### Inserciones a realizar desde Kotlin:

```sql
-- Usuarios
INSERT INTO Usuario (nombre, email) VALUES
  ('Facundo Pérez', 'facuper@mail.com'),
  ('Ataulfo Rodríguez', 'ataurod@mail.com'),
  ('Cornelio Ramírez', 'Cornram@mail.com');

-- Productos
INSERT INTO Producto (nombre, precio, stock) VALUES
  ('Ventilador', 10, 2),
  ('Abanico', 150, 47),
  ('Estufa', 24.99, 1);

-- Pedidos
INSERT INTO Pedido (idUsuario, precioTotal) VALUES
  (2, 160),
  (1, 20),
  (2, 150);

-- Líneas de pedido
INSERT INTO LineaPedido (idPedido, idProducto, cantidad, precio) VALUES
  (1, 1, 1, 10),
  (1, 2, 1, 150),
  (2, 1, 2, 20),
  (3, 2, 1, 150);
```

## Resolución del ejercicio

### Estructura y funciones principales

- **Model**: Representa los datos principales del dominio.
- **Dao**: Encargados del acceso directo a la base de datos.
- **Service**: Capa intermedia que encapsula la lógica de negocio usando los DAO.
- **Main**: Gestiona la conexión y secuencia global del programa.

### Funcionalidades implementadas

#### **LineaPedidoService**
- Proporciona un método para actualizar una línea de pedido específica, modificando el producto y el precio asignados.
- En caso de intentar eliminar un usuario inexistente, se lanzará un error para un manejo controlado.
```
    /**
     * Actualiza la línea de pedido con id dado, para cambiar producto y precio.
     * @param idLinea Id de la línea a modificar.
     * @param idProducto Nuevo id de producto para la línea.
     * @param nuevoPrecio Nuevo precio a asignar.
     */
    override fun actualizarLineaPedido(idLinea: Int, idProducto: Int, nuevoPrecio: Double) {
        dao.actualizarLineaPorId(idLinea, idProducto, nuevoPrecio)
    }
```
#### **ProductoService**
- Permite actualizar el precio de un producto dado, por ejemplo para poner un producto "en oferta".
```
    /**
     * Actualiza el precio de un producto llamando a la capa DAO.
     * @param idProducto Id del producto a modificar.
     * @param nuevoPrecio Nuevo precio a asignar.
     */
    override fun actualizarPrecioProducto(idProducto: Int, nuevoPrecio: Double) {
        dao.actualizarPrecio(idProducto, nuevoPrecio)
    }
```

### Ejemplo de uso en el Main

El siguiente ejemplo muestra cómo utilizar los servicios desde `main` para:
- Insertar registros (usuarios, productos, pedidos y líneas de pedido).
- Realizar las modificaciones solicitadas por el ejercicio (poner un producto en oferta, modificar una línea de pedido).
- Manejar transacciones y control de errores para hacer commit sólo si todo va bien y rollback en caso contrario.

```kotlin
fun main() {
    try {
        Database.getConnection().use { connection ->

            try {
                connection.autoCommit = false

                val usuarioService: IUsuarioService = UsuarioService()
                val productoService: IProductoService = ProductoService()
                val pedidoService: IPedidoService = PedidoService()
                val lineaPedidoService: ILineaPedidoService = LineaPedidoService()
                // Usuarios
                usuarioService.crear("Facundo Pérez", "facuper@mail.com")
                usuarioService.crear("Ataulfo Rodríguez", "ataurod@mail.com")
                usuarioService.crear("Cornelio Ramírez", "Cornram@mail.com")

                // Productos
                productoService.crear("Ventilador", 10.0, 2)
                productoService.crear("Abanico", 150.0, 47)
                productoService.crear("Estufa", 24.99, 1)

                // Pedidos
                pedidoService.crear(2, 160.0)
                pedidoService.crear(1, 20.0)
                pedidoService.crear(2, 150.0)

                // Líneas de Pedido
                lineaPedidoService.crear(1, 1, 1, 10.0)
                lineaPedidoService.crear(1, 2, 1, 150.0)
                lineaPedidoService.crear(2, 1, 2, 20.0)
                lineaPedidoService.crear(3, 2, 1, 150.0)

                // --- Aquí inicia la parte del nuevo ejercicio ---

                // 1. Poner producto "Abanico" (id=2) en oferta con precio 120.0
                productoService.actualizarPrecioProducto(idProducto = 2, nuevoPrecio = 120.0)
                println("Precio de producto 'Abanico' actualizado a 120.0 € (oferta)")

                // 2. Actualizar línea de pedido con id=3:
                // Cambiar producto a "Abanico" (id=2)
                // Cambiar precio al doble del precio actualizado de Abanico (2 * 120 = 240)
                lineaPedidoService.actualizarLineaPedido(idLinea = 3, idProducto = 2, nuevoPrecio = 240.0)
                println("Línea de pedido id=3 actualizada: producto cambiado a 'Abanico' y precio a 240.0 €")


                connection.commit()

            } catch (e: Exception) {
                try {
                    connection.rollback()
                    println("Error: ${e.message}")
                } catch (e: Exception) {
                    println("Error durante rollback")
                    println("Error original: ${e.message}")
                }
            }
        }
    } catch (e: SQLException) {
        println("Error: ${e.message}")
    }
}

```
1. Inserción de datos: usuarios, productos, pedidos y líneas de pedido.
2. Modificación controlada de productos y líneas de pedido según el enunciado.
3. Gestión de transacciones con commit y rollback para asegurar la integridad.
4. Manejo de errores para control y retroceso en caso de problemas durante la ejecución.


---

## Explicación del uso de interfaces

El diseño aplicó el **principio de programación orientada a interfaces** a través de la capa DAO (Data Access Object) y los servicios.  
Esto aporta varias ventajas: flexibilidad, facilidad para pruebas, menor acoplamiento y mayor mantenibilidad.

### ¿Cómo se ha aplicado?

- Se ha definido una **interfaz** para cada tipo de DAO. Ejemplo: `IUsuarioDao`, `IProductoDao`, `IPedidoDao`, `ILineaPedidoDao`. Cada una describe los métodos mínimos para operar sobre la base de datos para esa entidad.
- Las clases concretas (por ejemplo, `UsuarioDao`, `ProductoDao`, etc.) implementan esas interfaces, proporcionando la lógica real de acceso a la base de datos.
- En cada **servicio** (por ejemplo, `UsuarioService`), el atributo que referencia al DAO se declara con el **tipo de la interfaz**, no de la clase concreta:

```kotlin
private val dao: IUsuarioDao = UsuarioDao()
```
Así, el servicio depende de la interfaz y no de la implementación concreta.  
Esto permite, por ejemplo, cambiar fácilmente la implementación, usar un mock en tests, o aplicar patrones como inyección de dependencias.

### Beneficios de este enfoque

- **Facilidad para pruebas:** Puedes reemplazar el DAO real por un mock durante tests.
- **Mantenibilidad y flexibilidad:** Cambiar la clase de acceso a datos no obliga a modificar nada en el servicio.
- **Claridad en la arquitectura:** Se define de manera explícita el contrato mínimo que debe cumplir cualquier implementación DAO.

---
### Manejo de errores

Toda la funcionalidad principal se ejecuta en un bloque `try/catch` externo. En caso de error en la base de datos o en alguna operación, se muestra un mensaje y se realiza el rollback.

---