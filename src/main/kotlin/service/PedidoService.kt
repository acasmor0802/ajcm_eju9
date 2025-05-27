package service
// Importacion de dependencias
import data.dao.ILineaPedidoDao
import data.dao.IPedidoDao
import data.dao.LineaPedidoDao
import data.dao.PedidoDao
import model.Pedido
import java.sql.Connection

/**
 * Servicio para manejar operaciones relacionadas con pedidos.
 */
class PedidoService() : IPedidoService {
    private val dao: IPedidoDao = PedidoDao()
    private val lineaDao: ILineaPedidoDao = LineaPedidoDao()

    /**
     * Crea un nuevo pedido para el usuario indicado con el precio total dado.
     */
    override fun crear(idUsuario: Int, precioTotal: Double) {
        dao.insertar(Pedido(precioTotal = precioTotal, idUsuario = idUsuario))
    }

    /**
     * Elimina todas las líneas de pedido asociadas al pedido y luego el pedido mismo.
     * Lanza IllegalArgumentException si no existe ningún pedido con ese id.
     */
    override fun eliminarConLineas(idPedido: Int) {
        lineaDao.eliminarPorPedido(idPedido)

        // Borrar el pedido y chequear cuántos se eliminaron
        val pedidosBorrados = dao.eliminarPorId(idPedido)
        if (pedidosBorrados == 0) {
            throw IllegalArgumentException("No existe ningún pedido con id=$idPedido")
        }
    }

    override fun obtenerTotalGastadoPor(nombreUsuario: String): Double {
        return dao.obtenerTotalGastadoPorUsuario(nombreUsuario)
    }
}
