package modelo;

import java.util.Map;

public interface InterfazDAO {
    public boolean addUd_Didactica(UnidadDidactica uD);
    
    public boolean addConvExam(ConvocatoriaExamen cE);
    
    public boolean addEnun(Enunciado enun);
    
    public Map<Integer, Enunciado> searchEnuns();
    
    public boolean searchEnunID(int id);
    
    public Map<Integer, Enunciado> mostrarEnunciados();
    
    public  Map<Integer, Enunciado> getEnunciadosSesion(int sesionElegida);
    
    public Map<Integer, ConvocatoriaExamen> mostrarConvocatorias(int idEnunciado);
    
    public boolean modConvocatoriaExamen(int enunciado, int convocatoria);
}
