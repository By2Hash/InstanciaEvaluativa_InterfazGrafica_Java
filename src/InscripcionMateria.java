package menuprincipal;

import java.util.ArrayList;

public class InscripcionMateria implements Evaluable {
    private Materia materia;
    private int totalClases;
    private int clasesAsistidas;
    private ArrayList<Double> notas;

    public InscripcionMateria(Materia materia) {
        this.materia = materia;
        this.totalClases = 0;
        this.clasesAsistidas = 0;
        this.notas = new ArrayList<>();
    }

    public Materia getMateria() { return materia; }
    public int getTotalClases() { return totalClases; }
    public int getClasesAsistidas() { return clasesAsistidas; }
    public ArrayList<Double> getNotas() { return notas; }

    public void registrarAsistencia(boolean presente) {
        totalClases++;
        if (presente) clasesAsistidas++;
    }

    public void agregarNota(double nota) {
        if (nota < 0 || nota > 10)
            throw new IllegalArgumentException("La nota debe estar entre 0 y 10.");
        if (notas.size() >= 5)
            throw new IllegalStateException("No se pueden agregar más de 5 notas.");
        notas.add(nota);
    }

    public double getPorcentajeAsistencia() {
        if (totalClases == 0) return 100.0;
        return (clasesAsistidas * 100.0) / totalClases;
    }

    @Override
    public String getCondicion() {
        return getPorcentajeAsistencia() >= 75 ? "Regular" : "Libre";
    }

    @Override
    public double getPromedio() {
        if (notas.isEmpty()) return 0;
        double suma = 0;
        for (double n : notas) suma += n;
        return suma / notas.size();
    }

    @Override
    public boolean estaAprobada() {
        return getPromedio() >= 6 && getCondicion().equals("Regular");
    }
}