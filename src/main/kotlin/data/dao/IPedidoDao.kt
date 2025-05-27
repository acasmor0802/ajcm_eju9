package data.dao

import model.Pedido

interface IPedidoDao {
    fun insertar(pedido: Pedido)
    fun eliminarPorId(id: Int): Int
    fun obtenerTotalGastadoPorUsuario(nombreUsuario: String): Double
}