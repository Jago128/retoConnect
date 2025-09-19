package controller;

import java.util.Map;
import modelo.Enunciado;
import modelo.ImplementacionBD;
import modelo.InterfazDAO;

public class LoginController {
    InterfazDAO dao = new ImplementacionBD();

    public Map<Integer, Enunciado> mostrarEnunciados() {
        return dao.mostrarEnunciados();
    }
    public Map <Integer, Enunciado> getEnunciados(int sesionElegida) {
		return dao.getEnunciadosSesion(sesionElegida);
	}
}
