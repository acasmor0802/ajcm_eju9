package data.dao

import model.Usuario

interface IUsuarioDao {
    fun insertar(usuario: Usuario)
    fun eliminarPorNombre(nombre: String): Int
    fun obtenerUsuariosQueCompraronProducto(nombreProducto: String): List<String>
}