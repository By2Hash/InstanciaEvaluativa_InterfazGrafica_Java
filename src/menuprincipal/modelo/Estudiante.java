
package menuprincipal.modelo;

// IMPORTANTE: Importar las interfaces que están en el otro paquete
import menuprincipal.interfaces.Consultable;
import java.util.ArrayList;

public class Estudiante extends PersonaAcademia implements Consultable {
    
    private String carrera;
    private int anioIngreso;
    private ArrayList<InscripcionMateria> materias;

    public Estudiante(String nombre, String legajo, String carrera, int anioIngreso) {
        
        super(nombre, legajo);
        this.carrera = carrera;
        this.anioIngreso = anioIngreso;
        this.materias = new ArrayList<>();
    }

    
    // --- Métodos de Gestión ---

    public void inscribirse(InscripcionMateria ins) {
        this.materias.add(ins);
    }

    public void darDeBaja(String codigo) {
        // Remueve si el código coincide (usando una expresión lambda o iterador)
        boolean removido = materias.removeIf(ins -> ins.getMateria().getCodigo().equals(codigo));
        if (!removido) {
            throw new IllegalArgumentException("La materia con código " + codigo + " no existe.");
        }
    }

    
    // --- Implementación de Consultable ---

    @Override
    public InscripcionMateria getInscripcion(String codigoMateria) {
        for (InscripcionMateria ins : materias) {
            if (ins.getMateria().getCodigo().equalsIgnoreCase(codigoMateria)) {
                return ins;
            }
        }
        return null;
    }

    // Sobrecarga para buscar por cuatrimestre (requerido por tu MenuPrincipal)
    public ArrayList<InscripcionMateria> getInscripcion(int cuatrimestre) {
        ArrayList<InscripcionMateria> filtradas = new ArrayList<>();
        for (InscripcionMateria ins : materias) {
            if (ins.getMateria().getCuatrimestre() == cuatrimestre) {
                filtradas.add(ins);
            }
        }
        return filtradas;
    }

    @Override
    public double getPromedioGeneral() {
        if (materias.isEmpty()) return 0.0;
        double suma = 0;
        for (InscripcionMateria ins : materias) {
            suma += ins.getPromedio();
        }
        return suma / materias.size();
    }

    @Override
    public ArrayList<InscripcionMateria> getMateriasCriticas() {
        ArrayList<InscripcionMateria> criticas = new ArrayList<>();
        for (InscripcionMateria ins : materias) {
            double asist = ins.getPorcentajeAsistencia();
            // Criterio: Asistencia entre 75% y 85%
            if (asist >= 75 && asist <= 85) {
                criticas.add(ins);
            }
        }
        return criticas;
    }

    // --- Implementación de PersonaAcademica ---

    @Override
    public void mostrarResumen() {
        System.out.println("\n----------------------------------------");
        System.out.println("   PERFIL ACADÉMICO DEL ESTUDIANTE");
        System.out.println("----------------------------------------");
        System.out.println("Nombre:   " + getNombre());
        System.out.println("Legajo:   " + getLegajo());
        System.out.println("Carrera:  " + carrera);
        System.out.println("Ingreso:  " + anioIngreso);
        System.out.println("Materias: " + materias.size());
        System.out.printf("Promedio: %.2f%n", getPromedioGeneral());
        System.out.println("----------------------------------------\n");
    }

    public ArrayList<InscripcionMateria> getMaterias() {
        return materias;
    }
}