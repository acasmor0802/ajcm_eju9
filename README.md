# EJERCICIOS U9

## Enunciado original


### Esquema de Base de Datos

#### Creación de tablas

```sql
CREATE TABLE Usuario (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(255) NOT NULL,
  email VARCHAR(255) UNIQUE
);

CREATE TABLE Producto (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(255) NOT NULL,
  precio DECIMAL NOT NULL,
  stock INT NOT NULL
);

CREATE TABLE Pedido (
  id INT AUTO_INCREMENT PRIMARY KEY,
  precioTotal DECIMAL NOT NULL,
  idUsuario INT,
  FOREIGN KEY (idUsuario) REFERENCES Usuario(id)
);

CREATE TABLE LineaPedido (
  id INT AUTO_INCREMENT PRIMARY KEY,
  cantidad INT NOT NULL,
  precio DECIMAL NOT NULL,
  idPedido INT,
  idProducto INT,
  FOREIGN KEY (idPedido) REFERENCES Pedido(id),
  FOREIGN KEY (idProducto) REFERENCES Producto(id)
);
```
### Inserciones a realizar desde Kotlin:¶
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

## Resolución del ejercicio:
### Para hacer este ejercicio hice:

 - Una rama del ejercicio anterior.
 - Crear model con los tipos de datos que se van a añadir en la bbdd.
 - Actualizar el Main, para añadir las llamadas a los service, y de los service una llamada a los Dao.

### Main
En el main, creo una connexion, para hacer un `autoCommit = false` para hacer un `rollback` en el caso en el que salga mal el transcurso del programa.

```
fun main() {
    try {
        Database.getConnection().use { conn: Connection? ->
            conn?.autoCommit = false

            // Aquí se llama a AppDatabase para abrir una conexion
            try {
                val usuarioService = UsuarioService()
                val productoService = ProductoService()
                val pedidoService = PedidoService()
                val lineaPedidoService = LineaPedidoService()

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

                conn?.commit()
                println("Datos insertados correctamente.")

            } catch (e: Exception) {
                try {
                    conn?.rollback()
                } catch (e: Exception) {
                    println("Error en el Rollback")
                }
                println("Error en la ejecución: ${e.message}")
            }
        }
    } catch (e: Exception) {
        println("Error: ${e.message}")
    }
}
```

### Model
La capa Model sirve como la representación de los datos

```
package model

data class LineaPedido(val idPedido: Int, val idProducto: Int, val cantidad: Int, val precio: Double)
```

### Service
La capa Service actúa como intermediario entre el controlador y el Dao.

```
class LineaPedidoService() {
    val lpd = LineaPedidoDao()
    // Crea una nueva línea de pedido con el ID del pedido, ID del producto, cantidad y precio especificados.
    // Esta función construye un objeto y lo envía a DAO para insertarlo en la base de datos.
    fun crear(idPedido: Int, idProducto: Int, cantidad: Int, precio: Double) {
        lpd.insertar(LineaPedido(cantidad = cantidad, precio = precio, idPedido = idPedido, idProducto = idProducto))
    }
}
```

### Dao
Es el encargado de realizar todas las operaciones directas con la base de datos.

```
class LineaPedidoDao() {
    // Esta función prepara y ejecuta un SQL con los datos del objeto.
    fun insertar(lp: LineaPedido) {
        try{
            Database.getConnection().use { conn ->
                val sql = "INSERT INTO LineaPedido (cantidad, precio, idPedido, idProducto) VALUES (?, ?, ?, ?)"
                conn.prepareStatement(sql).use { stmt ->
                    stmt.setInt(1, lp.cantidad)
                    stmt.setDouble(2, lp.precio)
                    stmt.setInt(3, lp.idPedido)
                    stmt.setInt(4, lp.idProducto)
                    stmt.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            throw SQLException("Error al insertar Linea de pedido")
        }
    }
}
```
