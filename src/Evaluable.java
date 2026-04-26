package menuprincipal;

public interface Evaluable {
    String getCondicion();
    double getPromedio();
    boolean estaAprobada();

    default void mostrarEstadoAcademico() {
        System.out.printf("  Condición: %s | Promedio: %.2f%n", getCondicion(), getPromedio());
    }
}