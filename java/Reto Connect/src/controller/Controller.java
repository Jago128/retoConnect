package controller;

import java.util.Map;
import modelo.*;

public class Controller {

    private ImplementsDB dao = ImplementsDB.getInstance();
    
    
    
    public Map<Integer, Statement> getStatementsSession(int sessionId) {
        return dao.getStatementsSession(sessionId);
    }
    
    public Map<Integer, Statement> getStatements() {
        return dao.getStatements();
    }

    public boolean addUnit(Unit unit) {
        return dao.addUnit(unit);
    }

    public boolean addSession(Session cE) {
        return dao.addSession(cE);
    }

    public boolean addStatement(Statement statement) {
        return dao.addStatement(statement);
    }

    public Map<Integer, Session> getSessionsStatement(int statementId) {
        return dao.getSessionsStatement(statementId);
    }

    public Map<Integer, Session> getSessions() {
        return dao.getSessions();
    }
    
    public Map<Integer, Unit> getUnits(){
         return dao.getUnits();
    }
    
    public int getLastStatementId(){
        return dao.getLastStatementId();
    }
    
    public boolean addStatement(int idUnidad, int idEnunciado){
         return dao.addStatement(idUnidad, idEnunciado);
    }

    public boolean modifySession(int encunciado, int convocatoria) {
        return dao.modifySession(encunciado, convocatoria);

    }
}
