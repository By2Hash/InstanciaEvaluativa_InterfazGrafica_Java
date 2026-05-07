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

        estudiante = new Estudiante(nombre, legajo, carrera, anio);
        System.out.println("\n Perfil creado correctamente. ¡Bienvenido, " + nombre + "!\n");
    }

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

    static int leerEnteroValido() {
        while (!scanner.hasNextInt()) {
            System.out.println("\nEntrada invalida. Por favor, ingrese un numero.\n");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
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

        if (estudiante.getMaterias().isEmpty()) {
            System.out.println("  No hay materias inscriptas.  ");
            pausar();
            return;
        }

        System.out.print("  Codigo de la materia: ");
        String codigo = scanner.nextLine().trim();

        InscripcionMateria ins = estudiante.getInscripcion(codigo);
        if (ins == null) {
            System.out.println("  Materia no encontrada.\n");
            pausar();
            return;
        }

        if (ins.getNotas().size() >= 3) {
            System.out.println("  Ya se alcanzó el límite de 3 notas para esta materia.\n");
            mostrarNotasMateria(ins);
            pausar();
            return;
        }

        mostrarNotasMateria(ins);

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

        ins.agregarNota(nota);

        System.out.println();
        if (nota >= 6) {
            System.out.printf("  Parcial/TP APROBADO (%.1f >= 6)%n", nota);
        } else {
            System.out.printf("  Parcial/TP DESAPROBADO (%.1f < 6)%n", nota);
        }

        System.out.println();
        mostrarNotasMateria(ins);
        pausar();
    }

    // -------------------------------------------------------
    //  Muestra notas de una materia
    //  → Usa mostrarEstadoAcademico() de la interfaz Evaluable
    //    para imprimir condición, promedio y estado de aprobación,
    //    en lugar de repetir esa lógica con println/printf sueltos.
    // -------------------------------------------------------
    static void mostrarNotasMateria(InscripcionMateria ins) {
        System.out.println("  ----------------------------------------");
        System.out.println("  Materia  : " + ins.getMateria().getNombre()
                + " [" + ins.getMateria().getCodigo() + "]");

        // Delegamos en el método default de Evaluable
        ins.mostrarEstadoAcademico();

        ArrayList<Double> notas = ins.getNotas();
        if (notas.isEmpty()) {
            System.out.println("  Notas    : (sin notas registradas)");
        } else {
            System.out.print("  Notas    : ");
            for (int i = 0; i < notas.size(); i++) {
                String estado = notas.get(i) >= 6 ? "A" : "D";
                System.out.printf("%.1f(%s)", notas.get(i), estado);
                if (i < notas.size() - 1) System.out.print("  |  ");
            }
            System.out.println();
        }
        System.out.println("  Notas restantes: " + (3 - notas.size()) + " de 3");
        System.out.println("  ----------------------------------------");
    }

    // -------------------------------------------------------
    //  Opción 5 – Ver reportes
    // -------------------------------------------------------
    static void verReportes() {
        int subOpcion;

        do {
            System.out.println("----------------------------------------");
            System.out.println("|           VER REPORTES               |");
            System.out.println("----------------------------------------");
            System.out.println("|  1. Situacion general                |");
            System.out.println("|  2. Materias en riesgo               |");
            System.out.println("|  3. Materias aprobadas               |");
            System.out.println("|  4. Volver al menu principal         |");
            System.out.println("----------------------------------------");
            System.out.print("Seleccione una opcion: ");

            subOpcion = leerEnteroSubMenu();

            switch (subOpcion) {
                case 1: reporteSituacionGeneral(); break;
                case 2: reporteMateriasEnRiesgo();  break;
                case 3: reporteMateriasAprobadas(); break;
                case 4: System.out.println("  Volviendo al menu principal...\n"); break;
                default: System.out.println("\n  Opcion invalida. Ingrese una opcion del 1 al 4.\n");
            }

        } while (subOpcion != 4);
    }

    static void reporteSituacionGeneral() {
        System.out.println("\n-------- SITUACION GENERAL --------");

        ArrayList<InscripcionMateria> materias = estudiante.getMaterias();

        if (materias.isEmpty()) {
            System.out.println("  No hay materias inscriptas.\n");
            pausar();
            return;
        }

        int regulares = 0, enRiesgo = 0, libres = 0;

        for (InscripcionMateria ins : materias) {
            double asistencia = ins.getPorcentajeAsistencia();
            String condicion = ins.getCondicion();
            String estado;

            if (ins.estaAprobada()) {
                estado = "Aprobada";
            } else if (condicion.equals("Libre")) {
                estado = "Libre";
            } else {
                estado = "En curso";
            }

            System.out.printf("  %-20s | %-8s | Asist: %5.1f%% | Prom: %.2f | %s%n",
                    ins.getMateria().getNombre(),
                    condicion,
                    asistencia,
                    ins.getPromedio(),
                    estado);

            if (condicion.equals("Libre")) {
                libres++;
            } else if (asistencia >= 75 && asistencia <= 85) {
                enRiesgo++;
            } else {
                regulares++;
            }
        }

        System.out.println("-----------------------------------");
        System.out.printf("  Promedio general     : %.2f%n", estudiante.getPromedioGeneral());
        System.out.printf("  Materias regulares   : %d%n", regulares);
        System.out.printf("  Materias en riesgo   : %d%n", enRiesgo);
        System.out.printf("  Materias libres      : %d%n", libres);
        System.out.println("-----------------------------------\n");

        pausar();
    }

    static void reporteMateriasEnRiesgo() {
        System.out.println("\n-------- MATERIAS EN RIESGO --------");
        System.out.println("  (Materias con al menos 1 nota y promedio < 4)");

        ArrayList<InscripcionMateria> criticas = estudiante.getMateriasCriticas();

        if (criticas.isEmpty()) {
            System.out.println("  No hay materias en riesgo. ¡Bien!\n");
            pausar();
            return;
        }

        // Ordenar por promedio ascendente (burbuja)
        for (int i = 0; i < criticas.size() - 1; i++) {
            for (int j = 0; j < criticas.size() - 1 - i; j++) {
                if (criticas.get(j).getPromedio() > criticas.get(j + 1).getPromedio()) {
                    InscripcionMateria temp = criticas.get(j);
                    criticas.set(j, criticas.get(j + 1));
                    criticas.set(j + 1, temp);
                }
            }
        }

        for (InscripcionMateria ins : criticas) {
            System.out.printf("  %-20s | Promedio: %.2f | Notas: %d%n",
                    ins.getMateria().getNombre(),
                    ins.getPromedio(),
                    ins.getNotas().size());
        }
        System.out.println("------------------------------------\n");

        pausar();
    }

    static void reporteMateriasAprobadas() {
        System.out.println("\n-------- MATERIAS APROBADAS --------");

        ArrayList<InscripcionMateria> materias = estudiante.getMaterias();
        ArrayList<InscripcionMateria> aprobadas = new ArrayList<>();

        for (InscripcionMateria ins : materias) {
            if (ins.estaAprobada()) {
                aprobadas.add(ins);
            }
        }

        if (aprobadas.isEmpty()) {
            System.out.println("  No hay materias aprobadas aún.\n");
            pausar();
            return;
        }

        double maxProm = -1, minProm = 11, sumaPromedios = 0;

        for (InscripcionMateria ins : aprobadas) {
            double prom = ins.getPromedio();
            System.out.printf("  %-20s | Promedio: %.2f%n",
                    ins.getMateria().getNombre(), prom);

            if (prom > maxProm) maxProm = prom;
            if (prom < minProm) minProm = prom;
            sumaPromedios += prom;
        }

        System.out.println("------------------------------------");
        System.out.printf("  Nota maxima  : %.2f%n", maxProm);
        System.out.printf("  Nota minima  : %.2f%n", minProm);
        System.out.printf("  Promedio gral: %.2f%n", sumaPromedios / aprobadas.size());
        System.out.println("------------------------------------\n");

        pausar();
    }

    static void inscribirseAMateria() {
        System.out.println("\n-- Inscripcion a materia --");

        System.out.print("Nombre de la materia: ");
        String nombre = scanner.nextLine();

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

        boolean duplicado = estudiante.getInscripcion(codigo) != null;
        if (duplicado) {
            System.out.println("  ⚠️  Ya estás inscripto a una materia con ese código.\n");
            return;
        }

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

        Materia materia = new Materia(nombre, codigo, cuatrimestre, anio);
        estudiante.inscribirse(materia, totalClases);

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