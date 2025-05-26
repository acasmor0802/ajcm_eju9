package service
// Importacion de dependencias
import data.dao.PedidoDao
import model.Pedido
import java.sql.Connection

// Servicio para manejar operaciones relacionadas con pedidos.
class PedidoService() {
    private val dao = PedidoDao()
    // Crea un nuevo pedido con el ID del usuario y el precio total especificados.
    // Esta función construye un objeto y lo envía a DAO para insertarlo en la base de datos.
    fun crear(idUsuario: Int, precioTotal: Double) {
        dao.insertar(Pedido(precioTotal = precioTotal, idUsuario = idUsuario))
    }
}
