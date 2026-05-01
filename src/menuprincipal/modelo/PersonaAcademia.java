
package menuprincipal.modelo;


public abstract class PersonaAcademia {
    protected String nombre;
    protected String legajo;
    

    public PersonaAcademia(String nombre, String legajo) {
        this.nombre = nombre;
        this.legajo = legajo;
    }

    
    
    // Getters con validación
    public String getNombre() {
        if (nombre == null || nombre.trim().isEmpty()) {
            return "Nombre no especificado";
        }
        return nombre;
    }

    public String getLegajo() {
        if (legajo == null) {
            throw new IllegalStateException("El legajo no puede ser nulo.");
        }
        return legajo;
    }

    
    // Método abstracto a ser implementado por Estudiante
    public abstract void mostrarResumen();
}