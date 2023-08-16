import org.sqlite.SQLiteDataSource
import java.sql.Connection
import java.sql.DriverManager


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
    private val dataSource: SQLiteDataSource

    init {
        dataSource = SQLiteDataSource()
        dataSource.url = "jdbc:sqlite:panaderias.db"
        createPanaderiaTable()
    }

    private fun createPanaderiaTable() {
            dataSource.connection.use { connection ->
                val statement = connection.createStatement()
            val createTableSQL = """
                CREATE TABLE IF NOT EXISTS panaderias (
                    id INTEGER PRIMARY KEY,
                    nombre TEXT,
                    ubicacion TEXT,
                    esCafeteria INTEGER,
                    arriendo REAL
                );
            """
            statement.executeUpdate(createTableSQL)
        }
    }

    fun existsPanaderia(id: Int): Boolean {
        dataSource.connection.use { connection ->
            val selectSQL = "SELECT COUNT(*) FROM panaderias WHERE id = ?"
            connection.prepareStatement(selectSQL).use { preparedStatement ->
                preparedStatement.setInt(1, id)
                val resultSet = preparedStatement.executeQuery()
                return resultSet.getInt(1) > 0
            }
        }
    }

    fun create(panaderia: Panaderia) {
        if (existsPanaderia(panaderia.id)) {
            println("ERROR!!!! El ID de la panadería ya está en uso.")
            return
        }
        dataSource.connection.use { connection ->
            val insertSQL = """
                INSERT INTO panaderias(id, nombre, ubicacion, esCafeteria, arriendo)
                VALUES (?, ?, ?, ?, ?);
            """
            connection.prepareStatement(insertSQL).use { preparedStatement ->
                preparedStatement.setInt(1, panaderia.id)
                preparedStatement.setString(2, panaderia.nombre)
                preparedStatement.setString(3, panaderia.ubicacion)
                preparedStatement.setInt(4, if (panaderia.esCafeteria) 1 else 0)
                preparedStatement.setDouble(5, panaderia.arriendo)
                preparedStatement.executeUpdate()
            }
        }
        println("Panadería creada correctamente.")
    }

    fun readAll(): List<Panaderia> {
        val panaderias = mutableListOf<Panaderia>()
        dataSource.connection.use { connection ->
            val selectSQL = "SELECT * FROM panaderias"
            connection.prepareStatement(selectSQL).use { preparedStatement ->
                val resultSet = preparedStatement.executeQuery()
                while (resultSet.next()) {
                    panaderias.add(
                        Panaderia(
                            resultSet.getInt("id"),
                            resultSet.getString("nombre"),
                            resultSet.getString("ubicacion"),
                            resultSet.getInt("esCafeteria") == 1,
                            resultSet.getDouble("arriendo")
                        )
                    )
                }
            }
        }
        return panaderias
    }

    fun update(panaderia: Panaderia) {
        dataSource.connection.use { connection ->
            val updateSQL = """
                UPDATE panaderias
                SET nombre = ?, ubicacion = ?, esCafeteria = ?, arriendo = ?
                WHERE id = ?;
            """
            connection.prepareStatement(updateSQL).use { preparedStatement ->
                preparedStatement.setString(1, panaderia.nombre)
                preparedStatement.setString(2, panaderia.ubicacion)
                preparedStatement.setInt(3, if (panaderia.esCafeteria) 1 else 0)
                preparedStatement.setDouble(4, panaderia.arriendo)
                preparedStatement.setInt(5, panaderia.id)
                preparedStatement.executeUpdate()
            }
        }
        println("Panadería actualizada correctamente.")
    }

    fun delete(id: Int) {
        dataSource.connection.use { connection ->
            val deleteSQL = "DELETE FROM panaderias WHERE id = ?"
            connection.prepareStatement(deleteSQL).use { preparedStatement ->
                preparedStatement.setInt(1, id)
                preparedStatement.executeUpdate()
            }
        }
        println("Panadería eliminada correctamente.")
    }
}

class PanManager {
    private val dataSource: SQLiteDataSource

    init {
        dataSource = SQLiteDataSource()
        dataSource.url = "jdbc:sqlite:panaderias.db"
        createPanTable()
    }

    private fun createPanTable() {
        dataSource.connection.use { connection ->
            val statement = connection.createStatement()
            val createTableSQL = """
                CREATE TABLE IF NOT EXISTS panes (
                    id INTEGER PRIMARY KEY,
                    idPanaderia INTEGER,
                    nombre TEXT,
                    origen TEXT,
                    esDulce INTEGER,
                    precio REAL,
                    stock INTEGER
                );
            """
            statement.executeUpdate(createTableSQL)
        }
    }

    fun existsPan(id: Int): Boolean {
        dataSource.connection.use { connection ->
            val selectSQL = "SELECT COUNT(*) FROM panes WHERE id = ?"
            connection.prepareStatement(selectSQL).use { preparedStatement ->
                preparedStatement.setInt(1, id)
                val resultSet = preparedStatement.executeQuery()
                return resultSet.getInt(1) > 0
            }
        }
    }

    fun existsPanaderia(id: Int, panaderiaManager: PanaderiaManager): Boolean {
        return panaderiaManager.existsPanaderia(id)
    }

    fun create(pan: Pan, panaderiaManager: PanaderiaManager) {
        if (!existsPanaderia(pan.idPanaderia, panaderiaManager)) {
            println("ERROR!!!! La panadería con el ID ${pan.idPanaderia} no existe.")
            return
        }
        if (existsPan(pan.id)) {
            println("ERROR!!!! El ID del pan ya está en uso.")
            return
        }
        dataSource.connection.use { connection ->
            val insertSQL = """
                INSERT INTO panes(id, idPanaderia, nombre, origen, esDulce, precio, stock)
                VALUES (?, ?, ?, ?, ?, ?, ?);
            """
            connection.prepareStatement(insertSQL).use { preparedStatement ->
                preparedStatement.setInt(1, pan.id)
                preparedStatement.setInt(2, pan.idPanaderia)
                preparedStatement.setString(3, pan.nombre)
                preparedStatement.setString(4, pan.origen)
                preparedStatement.setInt(5, if (pan.esDulce) 1 else 0)
                preparedStatement.setDouble(6, pan.precio)
                preparedStatement.setInt(7, pan.stock)
                preparedStatement.executeUpdate()
            }
        }
        println("Pan creado correctamente.")
    }


    fun readAll(): List<Pan> {
        val panes = mutableListOf<Pan>()
        dataSource.connection.use { connection ->
            val selectSQL = "SELECT * FROM panes"
            connection.prepareStatement(selectSQL).use { preparedStatement ->
                val resultSet = preparedStatement.executeQuery()
                while (resultSet.next()) {
                    panes.add(
                        Pan(
                            resultSet.getInt("id"),
                            resultSet.getInt("idPanaderia"),
                            resultSet.getString("nombre"),
                            resultSet.getString("origen"),
                            resultSet.getInt("esDulce") == 1,
                            resultSet.getDouble("precio"),
                            resultSet.getInt("stock")
                        )
                    )
                }
            }
        }
        return panes
    }

    fun update(pan: Pan) {
        dataSource.connection.use { connection ->
            val updateSQL = """
                UPDATE panes
                SET idPanaderia = ?, nombre = ?, origen = ?, esDulce = ?, precio = ?, stock = ?
                WHERE id = ?;
            """
            connection.prepareStatement(updateSQL).use { preparedStatement ->
                preparedStatement.setInt(1, pan.idPanaderia)
                preparedStatement.setString(2, pan.nombre)
                preparedStatement.setString(3, pan.origen)
                preparedStatement.setInt(4, if (pan.esDulce) 1 else 0)
                preparedStatement.setDouble(5, pan.precio)
                preparedStatement.setInt(6, pan.stock)
                preparedStatement.setInt(7, pan.id)
                preparedStatement.executeUpdate()
            }
        }
        println("Pan actualizado correctamente.")
    }

    fun delete(id: Int) {
        dataSource.connection.use { connection ->
            val deleteSQL = "DELETE FROM panes WHERE id = ?"
            connection.prepareStatement(deleteSQL).use { preparedStatement ->
                preparedStatement.setInt(1, id)
                preparedStatement.executeUpdate()
            }
        }
        println("Pan eliminado correctamente.")
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
                println("==================================")
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

            }
            2 -> {
                println("==================================")
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
                println("==================================")
                print("Ingrese el ID de la panadería que desea actualizar:")
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
                } else {
                    println("No se encontró una panadería con el ID especificado.")
                }
            }
            4 -> {
                println("==================================")
                print("Ingrese el ID de la panadería que desea eliminar:")
                val id = readLine()?.toIntOrNull() ?: continue
                panaderiaManager.delete(id)
            }
            5 -> {
                println("==================================")
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
                panManager.create(pan, panaderiaManager)

            }
            6 -> {
                println("==================================")
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
                println("==================================")
                print("Ingrese el ID del pan que desea actualizar:")
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
                } else {
                    println("No se encontró un pan con el ID especificado.")
                }
            }
            8 -> {
                println("==================================")
                print("Ingrese el ID del pan que desea eliminar:")
                val id = readLine()?.toIntOrNull() ?: continue
                panManager.delete(id)
            }
            9 -> {
                println("==================================")
                println("Saliendo del programa...")
                return
            }
            else -> {
                println("==================================")
                println("Opción no válida.")
            }
        }

        println()
    }
}
