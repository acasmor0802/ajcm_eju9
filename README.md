# EJERCICIOS U9

## Enunciado original

#### Eliminaciones con manejo de errores
- Elimina al usuario "Cornelio Ramírez".
- Elimina el producto con un precio de 24,99 €.
- Elimina el pedido con id igual a 3, asegurándote de eliminar primero sus líneas de pedido si existieran.

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

#### **UsuarioService**
- Este metodo se encarga de eliminar a los usuarios, en caso en el que no exista dicho usuario, lanzaría un error.
```
    /**
     * Elimina un usuario por nombre.
     * @throws IllegalArgumentException si no existe ningún usuario con ese nombre.
     */
    override fun eliminarPorNombre(nombre: String) {
        val filas = dao.eliminarPorNombre(nombre)
        if (filas == 0) {
            throw IllegalArgumentException("No existe ningún usuario con nombre \"$nombre\"")
        }
    }
```
#### **ProductoService**
- Elimina los productos que tengan el mismo precio al ingresado en el main.
```
    /**
     * Elimina todos los productos que tengan exactamente ese precio.
     * @return número de filas eliminadas.
     */

    override fun eliminarPorPrecio(precio: Double): Int {
        return dao.eliminarPorPrecio(precio)
    }
```
#### **PedidoService**
- En este metodo se eliminan todas las lineas de pedido relacionadas con un pedido y luego el pedido con ese id.
- Si ocurre el error `if (pedidosBorrados == 0)` se lanza un exception. 
```
/**
* Elimina todas las líneas de pedido asociadas al pedido y luego el pedido mismo.
* Lanza IllegalArgumentException si no existe ningún pedido con ese id.
*/
override fun eliminarConLineas(idPedido: Int) {
lineaDao.eliminarPorPedido(idPedido)

        // Borrar el pedido y chequear cuántos se eliminaron
        val pedidosBorrados = dao.eliminarPorId(idPedido)
        if (pedidosBorrados == 0) {
            throw IllegalArgumentException("No existe ningún pedido con id=$idPedido")
        }
    }
```

### Ejemplo de uso en el Main

El siguiente ejemplo muestra cómo utilizar los servicios desde `main` para insertar registros y realizar las operaciones de borrado solicitadas, todo dentro de un bloque de transacción y con manejo de errores:

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

                // Ejercicio 4: Eliminaciones con manejo de errores
                println("=== Ejercicio 4: Eliminaciones ===")

                // 1. Eliminar usuario "Cornelio Ramírez"

                usuarioService.eliminarPorNombre("Cornelio Ramírez")
                println("Usuario 'Cornelio Ramírez' eliminado.")

                // 2. Eliminar producto con precio 24.99
                val eliminadosProd = productoService.eliminarPorPrecio(25.0)
                if (eliminadosProd > 0) {
                    println("Producto(s) con precio 24.99 eliminado(s): $eliminadosProd")
                } else {
                    println("No se encontró ningún producto con precio 24.99.")
                }

                // 3. Eliminar pedido id=3 (primero sus líneas)

                pedidoService.eliminarConLineas(3)
                println("Pedido id=3 y sus líneas eliminados.")

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
1. Inserción de datos de usuarios, productos, pedidos y líneas de pedido.
2. Eliminación controlada según el enunciado (usuario, producto a un precio dado y pedido junto a sus líneas).
3. Transacciones y manejo de errores.

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