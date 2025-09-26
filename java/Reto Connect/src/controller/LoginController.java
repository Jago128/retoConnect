package controller;

import java.util.Map;
import modelo.*;

public class LoginController {
    InterfazDAO dao = new ImplementacionBD();

    public Map<Integer, Enunciado> getEnunciados(int sesionElegida) {
        return dao.getEnunciadosSesion(sesionElegida);
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
}
