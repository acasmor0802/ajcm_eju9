import app.Controlador
import com.zaxxer.hikari.HikariDataSource
import data.Database
import data.dao.LineaPedidoDao
import data.dao.PedidoDao
import data.dao.ProductoDao
import data.dao.UsuarioDao
import data.db.DataSourceFactory
import service.*
import ui.Consola
import java.sql.SQLException

fun main() {
    val consola = Consola()

    val dataSource = try {
        DataSourceFactory.create()
    } catch (e: Exception) {
        consola.mostrarError("Problema al crear DataSource: ${e.message}")
        return
    }

    consola.mostrar("DataSource creado correctamente.", true)

    val servicioLinea = LineaPedidoService(LineaPedidoDao(dataSource))
    val servicioPedido = PedidoService(PedidoDao(dataSource), LineaPedidoDao(dataSource))
    val servicioProducto = ProductoService(ProductoDao(dataSource))
    val servicioUsuario = UsuarioService(UsuarioDao(dataSource))

    consola.mostrar("Servicios creados correctamente.", true)

    val controlador = Controlador(
        servicioLinea,
        servicioPedido,
        servicioProducto,
        servicioUsuario,
        consola, // Usar la misma consola
        dataSource
    )

    consola.mostrar("Controlador creado. Iniciando...", true)

    controlador.iniciar()

    if (dataSource is HikariDataSource) {
        try {
            dataSource.close()
        } catch (e: Exception) {
            consola.mostrarError("Problemas al cerrar el DataSource: ${e.message}")
        }
    }
}