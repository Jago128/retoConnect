package controller;

import java.util.Map;
import modelo.*;

public class Controller {

    InterfazDAO dao = new ImplementacionBD();

    public Map<Integer, Enunciado> getEnunciadosSesion(int sesionElegida) {
        return dao.getEnunciadosSesion(sesionElegida);
    }
    
    public Map<Integer, Enunciado> getStatements() {
        return dao.getStatements();
    }

    public boolean addUd_Didactica(UnidadDidactica uD) {
        return dao.addUd_Didactica(uD);
    }

    public boolean addConvExam(ConvocatoriaExamen cE) {
        return dao.addConvExam(cE);
    }

    public Map<Integer, Enunciado> searchEnuns() {
        return dao.searchEnuns();
    }

    public boolean searchEnunID(int id) {
        return dao.searchEnunID(id);
    }

    public boolean addEnun(Enunciado enun) {
        return dao.addEnun(enun);
    }

    public Map<Integer, ConvocatoriaExamen> getExams(int statementId) {
        return dao.getExams(statementId);
    }

    public Map<Integer, ConvocatoriaExamen> mostrarTodasConvocatorias() {
        return dao.mostrarTodasConvocatorias();
    }
    
    public Map<Integer, UnidadDidactica> mostrarTodasUnidades(){
         return dao.mostrarTodasUnidades();
    }
    
    public int obtenerUltimoIdEnunciado(){
        return dao.obtenerUltimoIdEnunciado();
    }
    
    public boolean insert_asigment(int idUnidad, int idEnunciado){
         return dao.insert_asigment(idUnidad, idEnunciado);
    }

    public boolean modConvocatoriaExamen(int encunciado, int convocatoria) {
        return dao.modConvocatoriaExamen(encunciado, convocatoria);

    }
}
