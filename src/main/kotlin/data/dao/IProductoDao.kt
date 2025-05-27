package data.dao

import model.Producto

interface IProductoDao {
    fun insertar(producto: Producto)
    fun eliminarPorPrecio(precio: Double): Int
}