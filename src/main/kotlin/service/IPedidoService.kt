package service

interface IPedidoService {
    fun crear(idUsuario: Int, precioTotal: Double)
    fun eliminarConLineas(idPedido: Int)
}