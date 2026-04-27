package menuprincipal;

import java.util.ArrayList;
import java.util.Scanner;

public class MenuPrincipal {

    static Scanner scanner = new Scanner(System.in);
    static Estudiante estudiante = null;

    public static void main(String[] args) {
        inicializarEstudiante();
        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = leerEnteroValido();
            switch (opcion) {
                case 1: estudiante.mostrarResumen();     break;
                case 2: menuGestionMaterias();           break;
                case 3: registrarAsistencia();           break;
                case 4: registrarCalificacion();         break;
                case 5: verReportes();                   break;
                case 6: System.out.println("\n👋 Saliendo del sistema. ¡Hasta luego!"); break;
                default: System.out.println("\n⚠️  Opción inválida. Ingrese del 1 al 6.\n");
            }
        } while (opcion != 6);
        scanner.close();
    }

    // ── INICIALIZACIÓN ──────────────────────────────────────────────
    static void inicializarEstudiante() {
        System.out.println("=== BIENVENIDO AL SISTEMA DE AUTOGESTIÓN ESTUDIANTIL ===");
        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese su legajo: ");
        String legajo = scanner.nextLine();
        System.out.print("Ingrese su carrera: ");
        String carrera = scanner.nextLine();
        System.out.print("Ingrese su año de ingreso: ");
        int anio = leerEnteroValido();
        estudiante = new Estudiante(nombre, legajo, carrera, anio);
        System.out.println("\n✅ Perfil creado correctamente. ¡Bienvenido, " + nombre + "!\n");
    }

    // ── MENÚ PRINCIPAL ──────────────────────────────────────────────
    static void mostrarMenuPrincipal() {
        System.out.println("----------------------------------------");
        System.out.println("|    SISTEMA DE AUTOGESTIÓN ESCOLAR    |");
        System.out.println("----------------------------------------");
        System.out.println("|  1. Ver perfil del estudiante        |");
        System.out.println("|  2. Gestión de materias (submenú)    |");
        System.out.println("|  3. Registrar asistencia             |");
        System.out.println("|  4. Registrar calificación           |");
        System.out.println("|  5. Ver reportes                     |");
        System.out.println("|  6. Salir                            |");
        System.out.println("----------------------------------------");
        System.out.print("Seleccione una opción: ");
    }

    // ── GESTIÓN DE MATERIAS ─────────────────────────────────────────
    static void menuGestionMaterias() {
        int subOpcion;
        do {
            System.out.println("\n--- GESTIÓN DE MATERIAS ---");
            System.out.println("  1. Inscribirse a una materia");
            System.out.println("  2. Darse de baja de una materia");
            System.out.println("  3. Listar materias inscriptas");
            System.out.println("  4. Buscar materia por código");
            System.out.println("  5. Buscar materias por cuatrimestre");
            System.out.println("  6. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            subOpcion = leerEnteroSubMenu();
            switch (subOpcion) {
                case 1: inscribirseMateria();            break;
                case 2: darDeBajaMateria();              break;
                case 3: listarMaterias();                break;
                case 4: buscarMateriaPorCodigo();        break;
                case 5: buscarMateriaPorCuatrimestre();  break;
                case 6: System.out.println("  Volviendo al menú principal...\n"); break;
                default: System.out.println("\n  Opción inválida. Ingrese del 1 al 6.\n");
            }
        } while (subOpcion != 6);
    }

    static void inscribirseMateria() {
        System.out.println("\n-- Inscripción a materia --");
        System.out.print("  Nombre de la materia: ");
        String nombre = scanner.nextLine();
        System.out.print("  Código (3-10 caracteres): ");
        String codigo = scanner.nextLine();
        System.out.print("  Cuatrimestre (1 o 2): ");
        int cuatri = leerEnteroSubMenu();
        System.out.print("  Año: ");
        int anio = leerEnteroSubMenu();
        try {
            Materia m = new Materia(nombre, codigo, cuatri, anio);
            estudiante.inscribirse(m);
            System.out.println("  ✅ Inscripto correctamente a " + nombre + "\n");
        } catch (IllegalArgumentException e) {
            System.out.println("  ❌ Error: " + e.getMessage() + "\n");
        }
    }

    static void darDeBajaMateria() {
        System.out.println("\n-- Baja de materia --");
        System.out.print("  Ingrese el código de la materia: ");
        String codigo = scanner.nextLine();
        try {
            estudiante.darDeBaja(codigo);
            System.out.println("  ✅ Baja realizada correctamente.\n");
        } catch (IllegalArgumentException e) {
            System.out.println("  ❌ Error: " + e.getMessage() + "\n");
        }
    }

    static void listarMaterias() {
        ArrayList<InscripcionMateria> materias = estudiante.getMaterias();
        if (materias.isEmpty()) {
            System.out.println("  (No hay materias inscriptas aún)\n");
            return;
        }
        System.out.println("\n-- Materias inscriptas --");
        for (InscripcionMateria ins : materias) {
            System.out.printf("  [%s] %s | %s | Asistencia: %.1f%% | Promedio: %.2f%n",
                    ins.getMateria().getCodigo(),
                    ins.getMateria().getNombre(),
                    ins.getCondicion(),
                    ins.getPorcentajeAsistencia(),
                    ins.getPromedio());
        }
        System.out.println();
    }

    static void buscarMateriaPorCodigo() {
        System.out.print("  Ingrese código a buscar: ");
        String codigo = scanner.nextLine();
        InscripcionMateria ins = estudiante.getInscripcion(codigo);
        if (ins == null) {
            System.out.println("  ❌ No se encontró ninguna materia con ese código.\n");
        } else {
            ins.getMateria().mostrarResumen();
            ins.mostrarEstadoAcademico();
        }
    }

    static void buscarMateriaPorCuatrimestre() {
        System.out.print("  Ingrese cuatrimestre (1 o 2): ");
        int cuatri = leerEnteroSubMenu();
        ArrayList<InscripcionMateria> resultado = estudiante.getInscripcion(cuatri);
        if (resultado.isEmpty()) {
            System.out.println("  No hay materias del " + cuatri + "° cuatrimestre.\n");
        } else {
            System.out.println("  Materias del " + cuatri + "° cuatrimestre:");
            for (InscripcionMateria ins : resultado)
                ins.getMateria().mostrarResumen();
        }
    }

    // ── ASISTENCIA ──────────────────────────────────────────────────
    static void registrarAsistencia() {
        System.out.println("\n--- REGISTRAR ASISTENCIA ---");
        System.out.print("  Código de la materia: ");
        String codigo = scanner.nextLine();
        InscripcionMateria ins = estudiante.getInscripcion(codigo);
        if (ins == null) { System.out.println("  ❌ Materia no encontrada.\n"); return; }
        System.out.print("  ¿Asistió? (S/N): ");
        String resp = scanner.nextLine();
        boolean presente = resp.equalsIgnoreCase("S");
        ins.registrarAsistencia(presente);
        double porcentaje = ins.getPorcentajeAsistencia();
        System.out.printf("  Asistencia registrada. Porcentaje actual: %.1f%%%n", porcentaje);
        if (porcentaje < 75)
            System.out.println("  🚨 ALERTA CRÍTICA: perdiste la regularidad (< 75%)");
        else if (porcentaje < 80)
            System.out.println("  ⚠️  ADVERTENCIA: estás en zona de riesgo (< 80%)");
        System.out.println();
    }

    // ── CALIFICACIONES ──────────────────────────────────────────────
    static void registrarCalificacion() {
        System.out.println("\n--- REGISTRAR CALIFICACIÓN ---");
        System.out.print("  Código de la materia: ");
        String codigo = scanner.nextLine();
        InscripcionMateria ins = estudiante.getInscripcion(codigo);
        if (ins == null) { System.out.println("  ❌ Materia no encontrada.\n"); return; }
        if (ins.getNotas().size() >= 5) {
            System.out.println("  ❌ Ya tiene 5 notas cargadas (máximo permitido).\n"); return;
        }
        System.out.print("  Ingrese la nota (0-10): ");
        double nota = leerDoubleValido(0, 10);
        try {
            ins.agregarNota(nota);
            System.out.printf("  ✅ Nota %.1f %s%n", nota, nota >= 6 ? "✔ Aprobado" : "✘ Desaprobado");
            System.out.printf("  Promedio actual: %.2f%n%n", ins.getPromedio());
        } catch (IllegalArgumentException e) {
            System.out.println("  ❌ " + e.getMessage());
        }
    }

    // ── REPORTES ────────────────────────────────────────────────────
    static void verReportes() {
        System.out.println("\n--- VER REPORTES ---");
        System.out.println("  1. Situación general");
        System.out.println("  2. Materias en riesgo");
        System.out.println("  3. Materias aprobadas");
        System.out.print("Seleccione: ");
        int op = leerEnteroSubMenu();
        switch (op) {
            case 1: reporteSituacionGeneral();  break;
            case 2: reporteMateriasEnRiesgo();  break;
            case 3: reporteMateriasAprobadas(); break;
            default: System.out.println("  Opción inválida.\n");
        }
    }

    static void reporteSituacionGeneral() {
        System.out.println("\n====== SITUACIÓN GENERAL ======");
        int regulares = 0, riesgo = 0, libres = 0;
        for (InscripcionMateria ins : estudiante.getMaterias()) {
            double asist = ins.getPorcentajeAsistencia();
            String estado = ins.estaAprobada() ? "Aprobada" : ins.getCondicion().equals("Libre") ? "Libre" : "En curso";
            System.out.printf("  %-20s | %-8s | Asist: %5.1f%% | Prom: %.2f | %s%n",
                    ins.getMateria().getNombre(), ins.getCondicion(), asist, ins.getPromedio(), estado);
            if (ins.getCondicion().equals("Regular")) {
                if (asist >= 75 && asist <= 85) riesgo++; else regulares++;
            } else libres++;
        }
        System.out.printf("%nPromedio general: %.2f%n", estudiante.getPromedioGeneral());
        System.out.printf("Regulares: %d | En riesgo: %d | Libres: %d%n%n", regulares, riesgo, libres);
    }

    static void reporteMateriasEnRiesgo() {
        ArrayList<InscripcionMateria> criticas = estudiante.getMateriasCriticas();
        if (criticas.isEmpty()) { System.out.println("  No hay materias en riesgo.\n"); return; }
        // Ordenar por asistencia ascendente (bubble sort simple)
        for (int i = 0; i < criticas.size() - 1; i++)
            for (int j = 0; j < criticas.size() - 1 - i; j++)
                if (criticas.get(j).getPorcentajeAsistencia() > criticas.get(j+1).getPorcentajeAsistencia()) {
                    InscripcionMateria tmp = criticas.get(j);
                    criticas.set(j, criticas.get(j+1));
                    criticas.set(j+1, tmp);
                }
        System.out.println("\n====== MATERIAS EN RIESGO ======");
        for (InscripcionMateria ins : criticas)
            System.out.printf("  %-20s | Asistencia: %.1f%%%n",
                    ins.getMateria().getNombre(), ins.getPorcentajeAsistencia());
        System.out.println();
    }

    static void reporteMateriasAprobadas() {
        System.out.println("\n====== MATERIAS APROBADAS ======");
        double max = -1, min = 11, suma = 0;
        int count = 0;
        for (InscripcionMateria ins : estudiante.getMaterias()) {
            if (ins.estaAprobada()) {
                double p = ins.getPromedio();
                System.out.printf("  %-20s | Promedio: %.2f%n", ins.getMateria().getNombre(), p);
                if (p > max) max = p;
                if (p < min) min = p;
                suma += p;
                count++;
            }
        }
        if (count == 0) { System.out.println("  No hay materias aprobadas aún.\n"); return; }
        System.out.printf("%n  Nota máxima: %.2f | Nota mínima: %.2f | Promedio conjunto: %.2f%n%n",
                max, min, suma / count);
    }

    // ── HELPERS ─────────────────────────────────────────────────────
    static int leerEnteroValido() {
        while (!scanner.hasNextInt()) {
            System.out.println("\nEntrada inválida. Ingrese un número.\n");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }

    static int leerEnteroSubMenu() {
        while (!scanner.hasNextInt()) {
            System.out.println("\n  Entrada inválida. Ingrese un número.\n");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }

    static double leerDoubleValido(double min, double max) {
        double valor = -1;
        while (valor < min || valor > max) {
            while (!scanner.hasNextDouble()) {
                System.out.println("  Ingrese un número válido: ");
                scanner.next();
            }
            valor = scanner.nextDouble();
            scanner.nextLine();
            if (valor < min || valor > max)
                System.out.printf("  Debe estar entre %.0f y %.0f. Intente de nuevo: ", min, max);
        }
        return valor;
    }
}