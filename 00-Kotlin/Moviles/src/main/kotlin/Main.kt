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






//------------------------------------------
//ARREGLOS

//Tipos de arreglos
