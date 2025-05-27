package service

interface ILineaPedidoService {
    fun crear(idPedido: Int, idProducto: Int, cantidad: Int, precio: Double)
    fun actualizarLineaPedido(idLinea: Int, idProducto: Int, nuevoPrecio: Double)
}