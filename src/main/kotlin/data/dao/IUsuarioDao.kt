package data.dao

import model.Usuario

interface IUsuarioDao {
    fun insertar(usuario: Usuario)
    fun eliminarPorNombre(nombre: String): Int
}