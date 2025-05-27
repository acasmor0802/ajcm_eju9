# EJERCICIOS U9

## Enunciado original

- Consultas con manejo de errores¶
- Mostrar todas las líneas de pedido del pedido con ID = 1.
- Mostrar la suma del importe total de los pedidos realizados por el usuario «Ataulfo Rodríguez».
- Mostrar los nombres de los usuarios que hayan comprado un «Abanico».

### Inserciones a realizar desde Kotlin:
#### Pista: los id son autoincrementales, por lo que no hace falta incluirlos en la inserción. Se asignan automáticamente.

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

- `crear(nombre: String, email: String)`
    - Crea un usuario en la base de datos con los datos indicados.

- `obtenerUsuariosQueCompraron(producto: String): List<String>`
    - Devuelve una lista de nombres de usuarios que han comprado el producto especificado.

#### **ProductoService**

- `crear(nombre: String, precio: Double, stock: Int)`
    - Crea un producto en la base de datos con los datos proporcionados.

#### **PedidoService**

- `crear(idUsuario: Int, precioTotal: Double)`
    - Añade un nuevo pedido a la base de datos para el usuario indicado y el importe total.

- `obtenerTotalGastadoPor(nombreUsuario: String): Double`
    - Devuelve la suma de los importes de todos los pedidos realizados por un usuario.

#### **LineaPedidoService**

- `crear(idPedido: Int, idProducto: Int, cantidad: Int, precio: Double)`
    - Crea e inserta una línea de pedido en la base de datos.

- `obtenerLineasDePedido(idPedido: Int): List<LineaPedido>`
    - Devuelve la lista de líneas de pedido asociadas a un pedido concreto.

---

### Ejemplo de uso en el Main

El flujo principal inserta todos los datos requeridos y realiza varias consultas:

```kotlin
val usuarioService = UsuarioService()
val productoService = ProductoService()
val pedidoService = PedidoService()
val lineaPedidoService = LineaPedidoService()

// Creación de usuarios, productos, pedidos y líneas de pedido:
usuarioService.crear("Facundo Pérez", "facuper@mail.com")
// ...

// Consultas y resultados:
lineaPedidoService.obtenerLineasDePedido(1).forEach { println(it) } // Muestra las líneas de pedido del pedido con ID 1

val total = pedidoService.obtenerTotalGastadoPor("Ataulfo Rodríguez")
println("Total: $total €") // Muestra el total gastado por un usuario dado

usuarioService.obtenerUsuariosQueCompraron("Abanico").forEach { println(it) } // Muestra los usuarios que compraron 'Abanico'
```

---

### Modelos

La capa Model representa la estructura de los datos, ejemplo:

```kotlin
data class LineaPedido(val idPedido: Int, val idProducto: Int, val cantidad: Int, val precio: Double)
```

---

### Manejo de errores

Toda la funcionalidad principal se ejecuta en un bloque `try/catch` externo. En caso de error en la base de datos o en alguna operación, se muestra un mensaje y se realiza el rollback.

---

### Resumen de métodos y servicios

- **UsuarioService**:
    - Crear usuario
    - Obtener usuarios que compraron cierto producto

- **ProductoService**:
    - Crear producto

- **PedidoService**:
    - Crear pedido
    - Obtener total gastado por usuario

- **LineaPedidoService**:
    - Crear línea de pedido
    - Obtener líneas de pedido por ID

---

```