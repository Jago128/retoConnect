package controller;

import java.util.Map;
import modelo.*;

public class LoginController {
    InterfazDAO cont = new ImplementacionBD();
    
    public boolean addUd_Didactica(UnidadDidactica uD) {
        return cont.addUd_Didactica(uD);
    }
    
    public boolean addConvExam(ConvocatoriaExamen cE) {
        return cont.addConvExam(cE);
    }
    
    public Map<Integer, Enunciado> searchEnuns() {
        return cont.searchEnuns();
    }
    
    public boolean searchEnunID(int id) {
        return cont.searchEnunID(id);
    }
    
    public boolean addEnun(Enunciado enun) {
        return cont.addEnun(enun);
    }
}
