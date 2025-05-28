package service
// Importacion de dependencias
import data.dao.IProductoDao
import model.Producto

// Servicio para manejar operaciones relacionadas con productos.
class ProductoService(private val productoDao: IProductoDao) : IProductoService {
    // Crea un nuevo producto con nombre, precio y stock especificados.
    // Esta función construye un objeto y lo envía a DAO para insertarlo en la base de datos.
    override fun crear(nombre: String, precio: Double, stock: Int) {
        productoDao.insertar(Producto(nombre = nombre, precio = precio, stock = stock))
    }

    /**
     * Elimina todos los productos que tengan exactamente ese precio.
     * @return número de filas eliminadas.
     */

    override fun eliminarPorPrecio(precio: Double): Int {
        return productoDao.eliminarPorPrecio(precio)
    }

    /**
     * Actualiza el precio de un producto llamando a la capa DAO.
     * @param idProducto Id del producto a modificar.
     * @param nuevoPrecio Nuevo precio a asignar.
     */
    override fun actualizarPrecioProducto(idProducto: Int, nuevoPrecio: Double) {
        productoDao.actualizarPrecio(idProducto, nuevoPrecio)
    }

}
