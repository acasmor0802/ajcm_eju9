package service

interface IUsuarioService {
    fun crear(nombre: String, email: String)
    fun eliminarPorNombre(nombre: String)
    fun obtenerUsuariosQueCompraron(producto: String): List<String>
}