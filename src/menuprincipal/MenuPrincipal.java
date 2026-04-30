package menuprincipal;

import java.util.Scanner;

public class MenuPrincipal {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;

        do 
        {
            mostrarMenuPrincipal();
            opcion = leerEnteroValido();

            switch (opcion) 
            {
                case 1:
                    verPerfilEstudiante();
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
            mostrarMenuPrincipal();
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); // limpia el buffer
        return valor;
    }

    // -------------------------------------------------------
    //  Opción 1 – Ver perfil del estudiante
    // -------------------------------------------------------
    static void verPerfilEstudiante() {
        System.out.println("\n--- VER PERFIL DEL ESTUDIANTE ---");
        System.out.print("Ingrese el nombre del estudiante: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el ID del estudiante: ");
        String id = scanner.nextLine();

        System.out.println("\n Perfil del estudiante:");
        System.out.println("   Nombre : " + nombre);
        System.out.println("   ID     : " + id);
        System.out.println("---------------------------------\n");
    }

    // -------------------------------------------------------
    //  Opción 2 – Submenú de gestión de materias
    // -------------------------------------------------------
    static void menuGestionMaterias() {
        int subOpcion;

        do {
            System.out.println("\n--- GESTION DE MATERIAS ---");
            System.out.println("  1. Agregar materia");
            System.out.println("  2. Eliminar materia");
            System.out.println("  3. Listar materias");
            System.out.println("  4. Volver al menu principal");
            System.out.print("Seleccione una opcion: ");

            subOpcion = leerEnteroSubMenu();

            switch (subOpcion) {
                case 1:
                    System.out.print("Ingrese el nombre de la materia a agregar: ");
                    String agregar = scanner.nextLine();
                    System.out.println(" Materia '" + agregar + "' agregada correctamente.\n");
                    break;
                case 2:
                    System.out.print("Ingrese el nombre de la materia a eliminar: ");
                    String eliminar = scanner.nextLine();
                    System.out.println(" Materia '" + eliminar + "' eliminada correctamente.\n");
                    break;
                case 3:
                    System.out.println(" Lista de materias registradas:");
                    System.out.println("   (No hay materias cargadas aun)\n");
                    break;
                case 4:
                    System.out.println("  Volviendo al menu principal...\n");
                    break;
                default:
                    System.out.println("\n  Opcion invalida. Ingrese una opcion del 1 al 4.\n");
            }

        } while (subOpcion != 4);
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

    // -------------------------------------------------------
    //  Opción 3 – Registrar asistencia
    // -------------------------------------------------------
    static void registrarAsistencia() {
        System.out.println("\n--- REGISTRAR ASISTENCIA ---");
        System.out.print("Ingrese el nombre del estudiante: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese la materia: ");
        String materia = scanner.nextLine();
        System.out.print("¿Asistio? (S/N): ");
        String asistio = scanner.nextLine();

        String estado = asistio.equalsIgnoreCase("S") ? " Presente" : " Ausente";
        System.out.println("\nAsistencia registrada:");
        System.out.println("   Estudiante : " + nombre);
        System.out.println("   Materia    : " + materia);
        System.out.println("   Estado     : " + estado);
        System.out.println("----------------------------\n");
    }

    // -------------------------------------------------------
    //  Opción 4 – Registrar calificación
    // -------------------------------------------------------
    static void registrarCalificacion() {
        System.out.println("\n--- REGISTRAR CALIFICACION ---");
        System.out.print("Ingrese el nombre del estudiante: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese la materia: ");
        String materia = scanner.nextLine();
        System.out.print("Ingrese la calificacion (0-10): ");

        double calificacion = -1;
        while (calificacion < 0 || calificacion > 10) {
            while (!scanner.hasNextDouble()) {
                System.out.println("  Ingrese un numero valido: ");
                scanner.next();
            }
            calificacion = scanner.nextDouble();
            scanner.nextLine();
            if (calificacion < 0 || calificacion > 10) {
                System.out.println("  La calificacion debe estar entre 0 y 10. Intente de nuevo: ");
            }
        }

        System.out.println("\nCalificacion registrada:");
        System.out.println("   Estudiante    : " + nombre);
        System.out.println("   Materia       : " + materia);
        System.out.println("   Calificacion  : " + calificacion);
        System.out.println("------------------------------\n");
    }

    // -------------------------------------------------------
    //  Opción 5 – Ver reportes
    // -------------------------------------------------------
    static void verReportes() {
        System.out.println("\n--- VER REPORTES ---");
        System.out.println(" Resumen del sistema:");
        System.out.println("   Total de estudiantes registrados : 0");
        System.out.println("   Total de materias                : 0");
        System.out.println("   Total de asistencias registradas : 0");
        System.out.println("   Total de calificaciones          : 0");
        System.out.println("   (Implemente la logica de datos para ver valores reales)");
        System.out.println("--------------------\n");
    }
}