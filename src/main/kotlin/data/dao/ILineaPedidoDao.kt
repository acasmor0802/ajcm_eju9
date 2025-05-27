package data.dao

import model.LineaPedido

interface ILineaPedidoDao {
    fun insertar(lp: LineaPedido)
    fun eliminarPorPedido(idPedido: Int): Int
    fun actualizarLineaPorId(idLinea: Int, idProducto: Int, nuevoPrecio: Double): Int
}