package service

interface IProductoService {
    fun eliminarPorPrecio(precio: Double): Int
    fun crear(nombre: String, precio: Double, stock: Int)
    fun actualizarPrecioProducto(idProducto: Int, nuevoPrecio: Double)
}