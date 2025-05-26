package service
// Importacion de dependencias
import data.dao.ProductoDao
import model.Producto

// Servicio para manejar operaciones relacionadas con productos.
class ProductoService() {
    private val pr = ProductoDao()
    // Crea un nuevo producto con nombre, precio y stock especificados.
    // Esta función construye un objeto y lo envía a DAO para insertarlo en la base de datos.
    fun crear(nombre: String, precio: Double, stock: Int) {
        pr.insertar(Producto(nombre = nombre, precio = precio, stock = stock))
    }
}
