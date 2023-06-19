import java.io.File

data class Panaderia(
    val id: Int,
    val nombre: String,
    val ubicacion: String,
    val esCafeteria: Boolean,
    val arriendo: Double
)

data class Pan(
    val id: Int,
    val idPanaderia: Int,
    val nombre: String,
    val origen: String,
    val esDulce: Boolean,
    val precio: Double,
    val stock: Int
)

class PanaderiaManager {
    private val file = File("panaderias.txt")

    init {
        if (!file.exists()) {
            file.createNewFile()
        }
    }

    fun create(panaderia: Panaderia) {
        file.appendText("${panaderia.id},${panaderia.nombre},${panaderia.ubicacion},${panaderia.esCafeteria},${panaderia.arriendo}\n")
    }

    fun readAll(): List<Panaderia> {
        return file.readLines().map { line ->
            val properties = line.split(",")
            Panaderia(
                properties[0].toInt(),
                properties[1],
                properties[2],
                properties[3].toBoolean(),
                properties[4].toDouble()
            )
        }
    }

    fun update(panaderia: Panaderia) {
        val lines = file.readLines()
        file.writeText("")
        lines.forEach { line ->
            val properties = line.split(",")
            if (properties[0].toInt() == panaderia.id) {
                file.appendText("${panaderia.id},${panaderia.nombre},${panaderia.ubicacion},${panaderia.esCafeteria},${panaderia.arriendo}\n")
            } else {
                file.appendText("$line\n")
            }
        }
    }

    fun delete(id: Int) {
        val lines = file.readLines()
        file.writeText("")
        lines.forEach { line ->
            val properties = line.split(",")
            if (properties[0].toInt() != id) {
                file.appendText("$line\n")
            }
        }
    }
}

class PanManager {
    private val file = File("panes.txt")

    init {
        if (!file.exists()) {
            file.createNewFile()
        }
    }

    fun create(pan: Pan) {
        file.appendText("${pan.id},${pan.idPanaderia},${pan.nombre},${pan.origen},${pan.esDulce},${pan.precio},${pan.stock}\n")
    }

    fun readAll(): List<Pan> {
        return file.readLines().map { line ->
            val properties = line.split(",")
            Pan(
                properties[0].toInt(),
                properties[1].toInt(),
                properties[2],
                properties[3],
                properties[4].toBoolean(),
                properties[5].toDouble(),
                properties[6].toInt()
            )
        }
    }

    fun update(pan: Pan) {
        val lines = file.readLines()
        file.writeText("")
        lines.forEach { line ->
            val properties = line.split(",")
            if (properties[0].toInt() == pan.id) {
                file.appendText("${pan.id},${pan.idPanaderia},${pan.nombre},${pan.origen},${pan.esDulce},${pan.precio},${pan.stock}\n")
            } else {
                file.appendText("$line\n")
            }
        }
    }

    fun delete(id: Int) {
        val lines = file.readLines()
        file.writeText("")
        lines.forEach { line ->
            val properties = line.split(",")
            if (properties[0].toInt() != id) {
                file.appendText("$line\n")
            }
        }
    }
}

fun String.toBooleanOrNull(): Boolean? {
    return when (this.toLowerCase()) {
        "true" -> true
        "false" -> false
        else -> null
    }
}

fun main() {
    val panaderiaManager = PanaderiaManager()
    val panManager = PanManager()

    while (true) {
        println("============== MENÚ ==============")
        println("============ PANADERÍA ===========")
        println("1. Crear panadería")
        println("2. Leer panaderías")
        println("3. Actualizar panadería")
        println("4. Eliminar panadería")
        println("============== PANES =============")
        println("5. Crear pan")
        println("6. Leer panes")
        println("7. Actualizar pan")
        println("8. Eliminar pan")
        println("==================================")
        println("9. Salir")
        println("==================================")
        print("Escoja una opción: ")

        when (readLine()?.toIntOrNull()) {
            1 -> {
                println("Ingrese los datos de la panadería:")
                print("[ID]: ")
                val id = readLine()?.toIntOrNull() ?: continue
                print("[Nombre]: ")
                val nombre = readLine() ?: continue
                print("[Ubicación]: ")
                val ubicacion = readLine() ?: continue
                print("[¿Es una cafetería? (true/false)]: ")
                val esCafeteria = readLine()?.toBooleanOrNull() ?: continue
                print("[Arriendo]: ")
                val arriendo = readLine()?.toDoubleOrNull() ?: continue

                val panaderia = Panaderia(id, nombre, ubicacion, esCafeteria, arriendo)
                panaderiaManager.create(panaderia)
                println("Panadería creada correctamente.")
            }
            2 -> {
                val panaderias = panaderiaManager.readAll()
                if (panaderias.isNotEmpty()) {
                    println("Lista de panaderías:")
                    panaderias.forEach { panaderia ->
                        println("[ID]: ${panaderia.id}")
                        println("[Nombre]: ${panaderia.nombre}")
                        println("[Ubicación]: ${panaderia.ubicacion}")
                        println("[Es cafetería]: ${panaderia.esCafeteria}")
                        println("[Arriendo]: ${panaderia.arriendo}")
                        println("--------------------------")
                        println()
                    }
                } else {
                    println("No hay panaderías registradas.")
                }
            }
            3 -> {
                println("Ingrese el ID de la panadería que desea actualizar:")
                val id = readLine()?.toIntOrNull() ?: continue
                val panaderias = panaderiaManager.readAll()
                val panaderia = panaderias.find { it.id == id }
                if (panaderia != null) {
                    println("Ingrese los nuevos datos de la panadería:")
                    print("[Nombre] (${panaderia.nombre}): ")
                    val nombre = readLine() ?: continue
                    print("[Ubicación] (${panaderia.ubicacion}): ")
                    val ubicacion = readLine() ?: continue
                    print("[¿Es una cafetería?(true/false)] (${panaderia.esCafeteria}): ")
                    val esCafeteria = readLine()?.toBooleanOrNull() ?: continue
                    print("[Arriendo] (${panaderia.arriendo}): ")
                    val arriendo = readLine()?.toDoubleOrNull() ?: continue

                    val updatedPanaderia = Panaderia(id, nombre, ubicacion, esCafeteria, arriendo)
                    panaderiaManager.update(updatedPanaderia)
                    println("Panadería actualizada correctamente.")
                } else {
                    println("No se encontró una panadería con el ID especificado.")
                }
            }
            4 -> {
                println("Ingrese el ID de la panadería que desea eliminar:")
                val id = readLine()?.toIntOrNull() ?: continue
                panaderiaManager.delete(id)
                println("Panadería eliminada correctamente.")
            }
            5 -> {
                println("Ingrese los datos del pan:")
                print("[ID]: ")
                val id = readLine()?.toIntOrNull() ?: continue
                print("[ID de la panadería]: ")
                val idPanaderia = readLine()?.toIntOrNull() ?: continue
                print("[Nombre]: ")
                val nombre = readLine() ?: continue
                print("[Origen]: ")
                val origen = readLine() ?: continue
                print("[¿Es de dulce? (true/false)]: ")
                val esDulce = readLine()?.toBooleanOrNull() ?: continue
                print("[Precio]: ")
                val precio = readLine()?.toDoubleOrNull() ?: continue
                print("[Stock]: ")
                val stock = readLine()?.toIntOrNull() ?: continue

                val pan = Pan(id, idPanaderia, nombre, origen, esDulce, precio, stock)
                panManager.create(pan)
                println("Pan creado correctamente.")
            }
            6 -> {
                val panes = panManager.readAll()
                if (panes.isNotEmpty()) {
                    println("Lista de panes:")
                    panes.forEach { pan ->
                        println("[ID]: ${pan.id}")
                        println("[ID de la panadería]: ${pan.idPanaderia}")
                        println("[Nombre]: ${pan.nombre}")
                        println("[Origen]: ${pan.origen}")
                        println("[Es de dulce]: ${pan.esDulce}")
                        println("[Precio]: ${pan.precio}")
                        println("[Stock]: ${pan.stock}")
                        println("--------------------------")
                        println()
                    }
                } else {
                    println("No hay panes registrados.")
                }
            }
            7 -> {
                println("Ingrese el ID del pan que desea actualizar:")
                val id = readLine()?.toIntOrNull() ?: continue
                val panes = panManager.readAll()
                val pan = panes.find { it.id == id }
                if (pan != null) {
                    println("Ingrese los nuevos datos del pan:")
                    print("[ID de la panadería] (${pan.idPanaderia}): ")
                    val idPanaderia = readLine()?.toIntOrNull() ?: continue
                    print("[Nombre] (${pan.nombre}): ")
                    val nombre = readLine() ?: continue
                    print("[Origen] (${pan.origen}): ")
                    val origen = readLine() ?: continue
                    print("[¿Es de dulce? (true/false)] (${pan.esDulce}): ")
                    val esDulce = readLine()?.toBooleanOrNull() ?: continue
                    print("[Precio] (${pan.precio}): ")
                    val precio = readLine()?.toDoubleOrNull() ?: continue
                    print("[Stock] (${pan.stock}): ")
                    val stock = readLine()?.toIntOrNull() ?: continue

                    val updatedPan = Pan(id, idPanaderia, nombre, origen, esDulce, precio, stock)
                    panManager.update(updatedPan)
                    println("Pan actualizado correctamente.")
                } else {
                    println("No se encontró un pan con el ID especificado.")
                }
            }
            8 -> {
                println("Ingrese el ID del pan que desea eliminar:")
                val id = readLine()?.toIntOrNull() ?: continue
                panManager.delete(id)
                println("Pan eliminado correctamente.")
            }
            9 -> {
                println("Saliendo del programa...")
                return
            }
            else -> {
                println("Opción no válida.")
            }
        }

        println()
    }
}
