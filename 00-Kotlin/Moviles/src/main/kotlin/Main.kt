import java.util.Date

fun main(args: Array<String>) {
    println("Hello World!")

    //Tipos de variables

    //Inmutables (No se reasignan "=")
    val inmutable: String = "Adrian";
    // inmutalbe = "Vicente" NO SE PUEDE HACER ESTA ACCION

    //Mutables (Reasginar)
    var mutable: String = "Vicente";
    mutable = "Adrian";

    // val > var

    // Duck Typing
    var ejemploVariable = "Hola";
    val edadEjemplo:Int = 15;
    ejemploVariable.trim() //Quita los espacios

    //Variable primitiva
    val nombreProfesor:String = "Adrian Eguez";
    val sueldo:Double = 1.2
    val estadoCivil:Char = 'C'
    val mayorEdad:Boolean = true

    //clase Java
    val fechaNacimiento:Date = Date()

    //SWITCH NO EXISTE, EXISTE WHEN
    val estadoCivilWhen = "C"

    when(estadoCivilWhen){
        ("C") -> {
            println("Casado")
        }
        "S" -> {
            println("Soltero")
        }
        else -> {
            println("No sabemos")
        }
    }

    val coqueteo = if (estadoCivilWhen == "S") "Si" else "No"


    //Como llamar funciones
    calcularSueldo(10.00)
    calcularSueldo(10.00, 12.00, 20.00)
    calcularSueldo(10.00, bonoEspecial = 20.00) //Named Parameter
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00) //Parametros nombrados

    val sumaUno = Suma(1,1)
    val sumaDos = Suma(null,1)
    val sumaTres = Suma(1,null)
    val sumaCuatro = Suma(null,null)

    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()

    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSuma)

    //------------------------------------------
    //ARREGLOS

    //Tipos de arreglos

    //Arreglo estático
    val arregloEstatico: Array<Int> = arrayOf<Int>(1,2,3)
    println(arregloEstatico)

    //Arreglo dinámico
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(1,2,3,4,5,6,7,8,9,10)
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    //Operadores -> Sirven para los arreglos estáticos y dinámicos

    //FOR EACH -> Unit
    //Iterar un arreglo
    val respuestaForEach: Unit = arregloDinamico.forEach{valorActual:Int ->
        println("Valor Actual: ${valorActual}")
    }

    //otra forma
    arregloDinamico.forEach(println(it))//it significa el elemnto del arreglo

    //-----------------------------------------------------------------------
    arregloEstatico
        .forEachIndexed { indice:Int, valorActual:Int ->
            println("Valor ${valorActual} Indice: ${indice}")
        }
    println(respuestaForEach)


    // MAP -> Muta el arreglo
    // 1) Enviamos el nuevo valor de la iteracion
    // 2) Nos devuelve un NUEVO ARREGLO con los valores modificados

    val respuestaMap: List<Double> = arregloDinamico
        .map { valorActual:Int ->
            return@map valorActual.toDouble() + 100.00
        }
    println(respuestaMap)
    val respuestaMapDos = arregloDinamico.map { it + 15 }

    // Filtrer -> FILTRAR EL ARREGLO
    // 1) Devolver una extresion TRUE or FALSE
    // 2) Nuevo arreglo filtrado
    val respuestaFiltrer: List <Int> = arregloDinamico
        .filter { valorActual:Int ->
            val mayoresaCinco: Boolean = valorActual > 5 //Expresion condicion
            return@filter mayoresaCinco
        }
    val respuestaFiltrerDos = arregloEstatico.filter { it <= 5 }
    println(respuestaFiltrer)
    println(respuestaFiltrerDos)


    //OR AND
    // OR -> any (Alguno cumple?)
    // AND -> all (Todos cumplen?)
    println("OR and AND")
    println("OR")
    val respuestaAny: Boolean = arregloDinamico
        .any{valorActual:Int ->
            return@any (valorActual > 5)
        }
    println(respuestaAny) //true

    println("AND")
    val respuestaAll:Boolean = arregloDinamico
        .all { valorActual:Int ->
            return@all (valorActual>5)
        }
    println(respuestaAll)//False

    
    //REDUCE -> Valor acumulado
    //Valor acumulado = 0 (siempre 0 en lenguaje Kotlin)
    //[1,2,3,4,5] -> Sumame todos los valores del arreglo
    //valorIteracion1 = valorEmpieza + 1 = 0 + 1 = 1 -> Iteracion 1
    //valorIteracion2 = ValorIteracion1 + 2 = 1 + 2 = 3 -> Iteracion 2
    //valorIteracion3 = valorIteracion2 + 3 = 3 + 3 = 6 -> Iteracion 3
    //valorIteracion4 = valorIteracion3 + 4 = 6 + 4 = 10 -> Iteracion 4
    //valorIteracion5 = valorIteracion4 + 5 = 10 + 5 = 15 -> Iteracion 5

    val respuestaReduce: Int = arregloDinamico
        .reduce{ //acumulado =0 -> SIEMPRE EMPIEZA EN 0
                acumulado:Int, valorActual: Int ->
            return@reduce(acumulado + valorActual) //->logica negocio
        }
    println(respuestaReduce) //78
}

// void -> Unit
fun imprimirNombre(nombre: String):Unit{
    println("Nombre : ${nombre}")
}

fun calcularSueldo(
    sueldo: Double, //Requerido
    tasa: Double = 12.00, //Opcional (defecto)
    bonoEspecial:Double? = null, //Opcion null -> nulleable
):Double{
    // Int -> Int? (nulleable)
    // String -> String? (nulleable)
    // Date -> Date? (nulleable)
    if(bonoEspecial == null){
        return sueldo * (100/tasa)
    }else{
        return sueldo * (100/tasa) + bonoEspecial
    }
}

abstract class NumerosJava{ //Clase java
    protected val numeroUno:Int
    private val numeroDos:Int
    constructor(
        uno:Int,
        dos:Int
    ){//Boque de codigo del contructor
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}

abstract class Numeros(//Contructor PRIMARIO
    //Ejemplo
    //uno:Int, (Parametro - Sin modificador de acceso)
    //public var uno:Int, //Propiedad publica de la clase (numeros.uno)
    //var uno: Int, //Propiedad de la case - Por defecto es PUBLIC
    protected val numeroUno: Int, //Porpiedad de la clase Protected - numero.numeroUno
    protected val numeroDos: Int //Porpiedad de la clase Protected - numero.numeroDos
){
    //var cedula:string = "" (public es por defecto)
    //private valorCalculado:Int = 0 (private)
    init {
        this.numeroUno; this.numeroDos; //this es opcional
        //numeroUno; numeroDos; //Sin el "this"
        println("Inicializado")
    }

}

class Suma(//Constructor primario suma
    uno: Int, //Parametro
    dos: Int //Parametro
):Numeros(uno,dos){ //<- Constructor del padre
    init { //Bloque constructor primario, "Init" SOLO VA EN EL CONSTRUCTOR PRIMARIO
        this.numeroUno; numeroUno;
        this.numeroDos; numeroDos;
        //Los this pertenecen al padre, los que no contiene this
        //pertenecen al hijo
    }

    constructor(//Segundo constructor
        uno:Int?, //parametros
        dos:Int //Parametros
    ):this( //Llamada al constructor primario
        if (uno == null) 0 else uno,
        dos
        //Usamos el this para llamar a la clase padre, al consturctor primario
    ) {
        numeroUno; //Si necesitamos bloque de codigo lo usamos
    }

    constructor( //Tercer constructor
        uno: Int,
        dos: Int?
    ):this(
        uno,
        if(dos == null) 0 else dos
    )//Si no necesitamos un bloque de codio "{}" lo omitimos

    constructor(//Cuarto constructor
        uno: Int?,
        dos: Int?
    ):this(
        if (uno == null) 0 else uno,
        if(dos == null) 0 else dos
    )

    public fun sumar():Int{ // public es por defecto, tambien se puede usar private o protected
        val total = numeroUno + numeroDos;
        agregarHistorial(total)
        return total;
    }

    companion object{//Atributos y métodos "compartidos" - Es parecido al static
        //entre las instancias
        val pi = 3.14
        fun elevarAlCuadrado(num: Int):Int{
            return num * num
        }
        val historialSuma = arrayListOf<Int>()
        fun agregarHistorial(valorNuevaSuma:Int){
            historialSuma.add(valorNuevaSuma)
        }
    }
}







