package service

interface ILineaPedidoService {
    fun crear(idPedido: Int, idProducto: Int, cantidad: Int, precio: Double)
}