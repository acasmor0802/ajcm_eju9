package ui

class Consola : IEntradaSalida{

    override fun mostrarError(mensaje: String, saltoLinea: Boolean) {
        mostrar("# ERROR! => $mensaje", saltoLinea)
    }

    override fun mostrar(texto: String, saltoLinea: Boolean) {
        print("$texto${if (saltoLinea) "\n" else ""}")
    }

    override fun leer(prompt: String, saltoLinea: Boolean): String {
        if (prompt.isNotBlank()) mostrar(prompt, saltoLinea)
        return readln()
    }

    override fun limpiar(lineas: Int) {
        if (System.console() != null) {
            mostrar("\u001B[H\u001B[2J", false)
            System.out.flush()
        } else {
            repeat(20) { mostrar("") }
        }
    }

    override fun saltoLinea() {
        mostrar("")
    }

    override fun pausar(msj: String) {
        saltoLinea()
        leer(msj)
    }
}