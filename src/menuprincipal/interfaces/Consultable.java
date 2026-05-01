
package menuprincipal.interfaces;

import menuprincipal.modelo.InscripcionMateria;
import java.util.ArrayList;

public interface Consultable {
    
    InscripcionMateria getInscripcion(String codigoMateria);
    ArrayList<InscripcionMateria> getMateriasCriticas();
    double getPromedioGeneral();
    
}