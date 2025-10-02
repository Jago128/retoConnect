package modelo;

import java.util.HashMap;
import java.util.Map;

public interface InterfaceDAO {

    public boolean addUnit(Unit unit);

    public boolean addSession(Session cE);

    public boolean addStatement(Statement enun);
    
    public Map<Integer, Statement> getStatementsSession(int sessionId);
    
    public HashMap<Integer, Session> getSessions();
    
    public HashMap<Integer, Unit> getUnits();
    
    public int getLastStatementId();
    
    public boolean addStatement(int idUnidad, int idEnunciado);
    
    public Map<Integer, Statement> getStatements();

    public Map<Integer, Session> getSessionsStatement(int statementId);

    public boolean modifySession(int enunciado, int convocatoria);
}
