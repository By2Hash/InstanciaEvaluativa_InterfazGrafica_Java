package menuprincipal;
import menuprincipal.modelo.Estudiante;
import menuprincipal.modelo.Materia;
import menuprincipal.modelo.InscripcionMateria;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuPrincipal {

    static Scanner scanner = new Scanner(System.in);
    static Estudiante estudiante = null;


    public static void main(String[] args) {

        inicializarEstudiante();
        int opcion;

        do 
        {
            mostrarMenuPrincipal();
            opcion = leerEnteroValido();

            switch (opcion) 
            {
                case 1:
                    estudiante.mostrarResumen();
                    pausar();
                    break;
                case 2:
                    menuGestionMaterias();
                    break;
                case 3:
                    registrarAsistencia();
                    break;
                case 4:
                    registrarCalificacion();
                    break;
                case 5:
                    verReportes();
                    break;
                case 6:
                    System.out.println("\n👋 Saliendo del sistema. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("\n⚠️  Opción invalida. Por favor, ingrese una opción del 1 al 6.\n");
            }

        } while (opcion != 6);

        scanner.close();
    }

    // ── INICIALIZACION ──────────────────────────────────────────────
    // Metodo para capturar los datos basicos del estudiante al iniciar el programa
    static void inicializarEstudiante() {

        System.out.println("----- BIENVENIDO AL SISTEMA DE AUTOGESTION ESTUDIANTIL -----");
        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese su legajo: ");
        String legajo = scanner.nextLine();
        System.out.print("Ingrese su carrera: ");
        String carrera = scanner.nextLine();
        System.out.print("Ingrese su año de ingreso: ");
        int anio = leerEnteroValido();

        // Se instancia la clase Estudiante con los datos provistos
        estudiante = new Estudiante(nombre, legajo, carrera, anio);
        System.out.println("\n Perfil creado correctamente. ¡Bienvenido, " + nombre + "!\n");
    }
    // -------------------------------------------------------
    //  Muestra el menú principal
    // -------------------------------------------------------
    static void mostrarMenuPrincipal()  {
        System.out.println("----------------------------------------");
        System.out.println("|       SISTEMA DE GESTION ESCOLAR     |");
        System.out.println("----------------------------------------");
        System.out.println("|  1. Ver perfil del estudiante        |");
        System.out.println("|  2. Gestion de materias (submenu)    |");
        System.out.println("|  3. Registrar asistencia             |");
        System.out.println("|  4. Registrar calificacion           |");
        System.out.println("|  5. Ver reportes                     |");
        System.out.println("|  6. Salir                            |");
        System.out.println("----------------------------------------");
        System.out.print("Seleccione una opcion: ");
    }

    // -------------------------------------------------------
    //  Lee un entero; si el usuario escribe texto, no crashea
    // -------------------------------------------------------
    static int leerEnteroValido() {
        while (!scanner.hasNextInt()) {
            System.out.println("\nEntrada invalida. Por favor, ingrese un numero.\n");
            scanner.next(); // descarta el token no numérico

        }
        int valor = scanner.nextInt();
        scanner.nextLine(); // limpia el buffer
        return valor;
    }



    // -------------------------------------------------------
    //  Opción 2 – Submenú de gestión de materias
    // -------------------------------------------------------
    static void menuGestionMaterias() {
        int subOpcion;

        do {
            System.out.println("----------------------------------------");
            System.out.println("|          GESTION DE MATERIAS         |");
            System.out.println("----------------------------------------");
            System.out.println("|  1. Inscribirse a una materia        |");
            System.out.println("|  2. Darse de baja de una materia     |");
            System.out.println("|  3. Listar materias inscriptas       |");
            System.out.println("|  4. Buscar materia                   |");
            System.out.println("|  5. Volver al menu principal         |");
            System.out.println("----------------------------------------");
            System.out.print("Seleccione una opcion: ");

            subOpcion = leerEnteroSubMenu();

            switch (subOpcion) {
                case 1:
                    inscribirseAMateria();
                    break;
                case 2:
                    darseDeBaja();
                    break;
                case 3:
                    listarMaterias();
                    break;
                case 4:
                    buscarMateria();
                    break;
                case 5:
                    System.out.println("  Volviendo al menu principal...\n");
                    break;
                default:
                    System.out.println("\n  Opcion invalida. Ingrese una opcion del 1 al 5.\n");
            }

        } while (subOpcion != 5);
    }

    // Lee entero para el submenú sin redibujar el menú principal
    static int leerEnteroSubMenu() {
        while (!scanner.hasNextInt()) {
            System.out.println("\n  Entrada invalida. Por favor, ingrese un numero.\n");
            scanner.next();
            System.out.print("Seleccione una opción: ");
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }

    // ── ASISTENCIA ──────────────────────────────────────────────────
    // Permite marcar si el alumno asistio o no a una clase de una materia puntual
    static void registrarAsistencia() {


        System.out.println("\n------- REGISTRAR ASISTENCIA ---------");
        System.out.print("  Codigo de la materia: ");
        String codigo = scanner.nextLine().trim();
        InscripcionMateria ins = estudiante.getInscripcion(codigo);
        if (ins == null) { System.out.println(" Materia no encontrada.\n"); return; }

        System.out.print("  ¿Asistio? (S/N): ");
        String resp = scanner.nextLine();
        boolean presente = resp.equalsIgnoreCase("S");

        ins.registrarAsistencia(presente);
        double porcentaje = ins.getPorcentajeAsistencia();
        System.out.printf("  Asistencia registrada. Porcentaje actual: %.1f%%%n", porcentaje);

        // Lógica de alertas segun el porcentaje de asistencia
        if (porcentaje < 75)
            System.out.println(" ALERTA CRITICA: perdiste la regularidad (< 75%)");
        else if (porcentaje < 80)
            System.out.println(" ADVERTENCIA: estas en zona de riesgo (< 80%)");
        System.out.println();

        pausar();
    }



    // -------------------------------------------------------
    //  Opción 4 – Registrar calificación
    // -------------------------------------------------------
    static void registrarCalificacion() {
        System.out.println("-------- REGISTRAR CALIFICACION ----------");

        // Verificar que haya materias
        if (estudiante.getMaterias().isEmpty()) {
            System.out.println("  No hay materias inscriptas.  ");
            pausar();
            return;
        }

        // Punto 1: buscar la materia inscripta por código
        System.out.print("  Codigo de la materia: ");
        String codigo = scanner.nextLine().trim();

        InscripcionMateria ins = estudiante.getInscripcion(codigo);
        if (ins == null) {
            System.out.println("  Materia no encontrada.\n");
            pausar();
            return;
        }

        // Punto 4: no permitir más de 5 notas
        if (ins.getNotas().size() >= 5) {
            System.out.println("  Ya se alcanzó el límite de 5 notas para esta materia.\n");
            mostrarNotasMateria(ins);
            pausar();
            return;
        }

        // Mostrar notas actuales antes de agregar
        mostrarNotasMateria(ins);

        // Punto 1: pedir nota con validación de rango 0-10
        double nota = -1;
        boolean entradaValida = false;
        while (!entradaValida) {
            System.out.print("  Ingrese la nota (0-10): ");
            if (scanner.hasNextDouble()) {
                nota = scanner.nextDouble();
                scanner.nextLine();
                if (nota >= 0 && nota <= 10) {
                    entradaValida = true;
                } else {
                    System.out.println("  La nota debe estar entre 0 y 10. Intente de nuevo.");
                }
            } else {
                System.out.println("  Entrada invalida. Ingrese un numero.");
                scanner.nextLine();
            }
        }

        // Agregar la nota al modelo
        ins.agregarNota(nota);

        // Punto 3: indicar si ese parcial/TP aprobó o no
        System.out.println();
        if (nota >= 6) {
            System.out.printf("  Parcial/TP APROBADO (%.1f >= 6)%n", nota);
        } else {
            System.out.printf("  Parcial/TP DESAPROBADO (%.1f < 6)%n", nota);
        }

        // Punto 2: mostrar todas las notas con el promedio actualizado
        System.out.println();
        mostrarNotasMateria(ins);
        pausar();
    }


    static void mostrarNotasMateria(InscripcionMateria ins) {
        System.out.println("  ----------------------------------------");
        System.out.println("  Materia  : " + ins.getMateria().getNombre()
                + " [" + ins.getMateria().getCodigo() + "]");
        System.out.println("  Condicion: " + ins.getCondicion());

        ArrayList<Double> notas = ins.getNotas();
        if (notas.isEmpty()) {
            System.out.println("  Notas    : (sin notas registradas)");
        } else {
            System.out.print("  Notas    : ");
            for (int i = 0; i < notas.size(); i++) {
                String estado = notas.get(i) >= 6 ? "A" : "D"; // Aprobado / Desaprobado
                System.out.printf("%.1f(%s)", notas.get(i), estado);
                if (i < notas.size() - 1) System.out.print("  |  ");
            }
            System.out.println();
            System.out.printf("  Promedio : %.2f%n", ins.getPromedio());
            System.out.println("  Estado   : " + (ins.estaAprobada() ? "APROBADA" : "No aprobada"));
        }
        System.out.println("  Notas restantes: " + (5 - notas.size()) + " de 5");
        System.out.println("  ----------------------------------------");
    }

    // -------------------------------------------------------
    //  Opción 5 – Ver reportes
    // -------------------------------------------------------

    static void verReportes() {

        System.out.println("------------- VER REPORTES -------------");
        System.out.println("           Resumen del sistema:         ");
        System.out.println("   Total de estudiantes registrados :  0");
        System.out.println("   Total de materias                :  0");
        System.out.println("   Total de asistencias registradas :  0");
        System.out.println("   Total de calificaciones          :  0");
        System.out.println("----------------------------------------");
        pausar();
    }
    static void inscribirseAMateria() {
        System.out.println("\n-- Inscripcion a materia --");

        System.out.print("Nombre de la materia: ");
        String nombre = scanner.nextLine();

        // Validación del código
        String codigo = "";
        boolean codigoValido = false;

        while (!codigoValido) {
            System.out.print("Codigo (3-10 caracteres): ");
            codigo = scanner.nextLine();

            if (codigo.length() < 3 || codigo.length() > 10) {
                System.out.println("  ⚠️  El codigo debe tener entre 3 y 10 caracteres.");
            } else {
                codigoValido = true;
            }
        }

        // Verificar que no exista ya esa inscripción
        String codigoFinal = codigo;
        boolean duplicado = estudiante.getInscripcion(codigo) != null;
        if (duplicado) {
            System.out.println("  ⚠️  Ya estás inscripto a una materia con ese código.\n");
            return;
        }

        // Validación del cuatrimestre
        int cuatrimestre = 0;
        while (cuatrimestre != 1 && cuatrimestre != 2) {
            System.out.print("Cuatrimestre (1 o 2): ");
            cuatrimestre = leerEnteroSubMenu();
            if (cuatrimestre != 1 && cuatrimestre != 2) {
                System.out.println("  ⚠️  El cuatrimestre debe ser 1 o 2.");
            }
        }

        System.out.print("Año: ");
        int anio = leerEnteroSubMenu();

        System.out.print("Total de clases de la cursada: ");
        int totalClases = leerEnteroSubMenu();

        // Crear objetos y agregar a la lista
        Materia materia = new Materia(nombre, codigo, cuatrimestre, anio);
        estudiante.inscribirse(materia);


        System.out.println("  ✅ Inscripción a '" + nombre + "' registrada correctamente.\n");
        pausar();
    }

    static void darseDeBaja() {
        System.out.println("\n-- Baja de materia --");


        if (estudiante.getMaterias().isEmpty()) {
            System.out.println("  No hay materias inscriptas.\n");
            return;
        }

        System.out.print("Ingrese el codigo de la materia a eliminar: ");
        String codigo = scanner.nextLine();

        InscripcionMateria aEliminar = null;
        for (InscripcionMateria ins : estudiante.getMaterias()) {
            if (ins.getMateria().getCodigo().equalsIgnoreCase(codigo)) {
                aEliminar = ins;
                break;
            }
        }

        if (aEliminar == null) {
            System.out.println("  ⚠️  No se encontró una materia con ese código.\n");
        } else {
           estudiante.darDeBaja(aEliminar.getMateria().getCodigo());
            System.out.println("  ✅ Materia '" + aEliminar.getMateria().getNombre() + "' eliminada correctamente.\n");
        }
        pausar();
    }

    // -------------------------------------------------------
//  Listar todas las materias inscriptas
// -------------------------------------------------------
    static void listarMaterias() {
        System.out.println("\n-- Materias inscriptas --");

        if (estudiante.getMaterias().isEmpty()) {
            System.out.println("  No hay materias inscriptas.\n");
            return;
        }

        ArrayList<InscripcionMateria> lista = estudiante.getMaterias();
        for (int i = 0; i < lista.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + lista.get(i).toString());
        }
        System.out.println();

        pausar();
    }

    // -------------------------------------------------------
//  Buscar materia por código o nombre parcial
// -------------------------------------------------------
    static void buscarMateria() {
        System.out.println("\n-- Buscar materia --");

        if (estudiante.getMaterias().isEmpty()) {
            System.out.println("  No hay materias inscriptas.\n");
            return;
        }

        System.out.print("Ingrese codigo o nombre (búsqueda parcial): ");
        String busqueda = scanner.nextLine().toLowerCase();

        ArrayList<InscripcionMateria> resultados = new ArrayList<>();
        for (InscripcionMateria ins : estudiante.getMaterias()) {
            String nombreM = ins.getMateria().getNombre().toLowerCase();
            String codigoM = ins.getMateria().getCodigo().toLowerCase();
            if (nombreM.contains(busqueda) || codigoM.contains(busqueda)) {
                resultados.add(ins);
            }
        }

        if (resultados.isEmpty()) {
            System.out.println("  ⚠️  No se encontraron materias con ese criterio.\n");
        } else {
            System.out.println("  Resultados encontrados: " + resultados.size());
            for (InscripcionMateria ins : resultados) {
                System.out.println("  → " + ins.toString());
            }
            System.out.println();
        }

        pausar();
    }

    static void pausar() {
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }

}