package service
// Importacion de dependencias
import data.dao.LineaPedidoDao
import data.dao.PedidoDao
import model.Pedido
import model.LineaPedido
import java.sql.Connection

class PedidoService() {
    private val dao = PedidoDao()

    //Crea un nuevo pedido asociado a un usuario con un precio total especificado.
     fun crear(idUsuario: Int, precioTotal: Double) {
        val pedido = Pedido(precioTotal = precioTotal, idUsuario = idUsuario)
        dao.insertar(pedido)
    }
    // Calcula el total gastado por un usuario dado su nombre.
     fun obtenerTotalGastadoPor(nombreUsuario: String): Double {
        return dao.obtenerTotalGastadoPorUsuario(nombreUsuario)
    }
}
