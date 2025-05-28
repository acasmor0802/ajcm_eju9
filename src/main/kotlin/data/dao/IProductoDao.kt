package data.dao

import model.Producto

interface IProductoDao {
    fun insertar(producto: Producto)
    fun eliminarPorPrecio(precio: Double?): Int
    fun actualizarPrecio(idProducto: Int, nuevoPrecio: Double): Int
}