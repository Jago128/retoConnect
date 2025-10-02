package modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

public final class ImplementsDB implements InterfaceDAO {
    
    private static ImplementsDB instance;

    private Connection con;
    private PreparedStatement stmt;

    private ResourceBundle configFile;
    private String driverDB;
    private String urlDB;
    private String userDB;
    private String passwordDB;

    final String SQL_GET_STATEMENTS = "SELECT * FROM STATEMENT";
    final String SQL_GET_SESSIONS = "SELECT * FROM EXAM_SESSION";
    final String SQL_GET_UNITS = "SELECT * FROM UNIT";
    final String SQL_ASSIGN_STATEMENT_UNIT = "INSERT INTO ASSIGN (ID_UNIT, ID_STATEMENT) VALUES (?,?)";
    final String SQL_EXAM_STATEMENT = "SELECT * FROM EXAM_SESSION WHERE ID_STATEMENT = ?";
    final String SQL_GET_STATEMENTS_SESSION = "SELECT * FROM STATEMENT S JOIN ASSIGN A ON S.ID_STATEMENT = A.ID_STATEMENT WHERE A.ID_UNIT = ?;";
    final String SQL_GET_SESSIONS_STATEMENT = "SELECT * FROM EXAM_SESSION E JOIN STATEMENT S ON E.ID_STATEMENT=S.ID_STATEMENT WHERE S.ID_STATEMENT = ?";
    final String SQL_ADD_UNIT = "INSERT INTO UNIT (ACRONYM, TITLE, EVALUATION, DESCRIPTION) VALUES (?,?,?,?)";
    final String SQL_ADD_SESSION = "INSERT INTO EXAM_SESSION (SESSION, DESCRIPTION, DATE, COURSE, ID_STATEMENT)  VALUES (?,?,?,?,?)";
    final String SQL_ADD_STATEMENT = "INSERT INTO STATEMENT (DESCRIPTION, DIFFICULTY, AVAILABLE, ROUTE) VALUES (?,?,?,?)";
    final String SQL_MODIFY_SESSION = "UPDATE EXAM_SESSION SET ID_STATEMENT = ? WHERE ID_EXAM_SESSION = ?";
    final String SQL_GET_LAST_STATEMENT = "SELECT MAX(ID_STATEMENT) as last_id FROM STATEMENT";
    
    public static ImplementsDB getInstance(){
        if(instance == null){
            instance = new ImplementsDB();
        }
        return instance;
    }

    public ImplementsDB() {
        this.configFile = ResourceBundle.getBundle("configClase");
        this.driverDB = this.configFile.getString("Driver");
        this.urlDB = this.configFile.getString("Conn");
        this.userDB = this.configFile.getString("DBUser");
        this.passwordDB = this.configFile.getString("DBPass");
    }

    private void openConnection() {
        try {
            con = DriverManager.getConnection(urlDB, this.userDB, this.passwordDB);
        } catch (SQLException e) {
            System.out.println("Failed to open database.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public HashMap<Integer, Statement> getStatements() {
        ResultSet rs;
        HashMap<Integer, Statement> statements = new HashMap<>();

        openConnection();

        try {
            stmt = con.prepareStatement(SQL_GET_STATEMENTS);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Statement statement = new Statement();
                statement.setId(rs.getInt("id_statement"));
                statement.setDescription(rs.getString("description"));
                statement.setDifficulty(Difficulty.valueOf(rs.getString("difficulty")));
                statement.setAvailable(rs.getBoolean("available"));
                statement.setRoute(rs.getString("route"));
                statements.put(statement.getId(), statement);
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return statements;
    }

    public HashMap<Integer, Session> getSessions() {
        ResultSet rs;
        HashMap<Integer, Session> sessions = new HashMap<>();

        openConnection();

        try {
            stmt = con.prepareStatement(SQL_GET_SESSIONS);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Session session = new Session();
                session.setId(rs.getInt("ID_EXAM_SESSION"));
                session.setSession(rs.getString("SESSION"));
                session.setDescription(rs.getString("DESCRIPTION"));
                java.sql.Date dateSQL = rs.getDate("DATE");
                if (dateSQL != null) {
                    session.setDate(dateSQL.toLocalDate());
                }
                session.setCourse(rs.getString("COURSE"));
                session.setStatement(rs.getInt("ID_STATEMENT"));

                sessions.put(session.getId(), session);
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sessions;
    }

    @Override
    public HashMap<Integer, Unit> getUnits() {
        ResultSet rs;
        HashMap<Integer, Unit> units = new HashMap<>();

        openConnection();

        try {
            stmt = con.prepareStatement(SQL_GET_UNITS);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Unit unit = new Unit();
                unit.setId(rs.getInt("ID_UNIT"));
                unit.setAcronym(rs.getString("ACRONYM"));
                unit.setTitle(rs.getString("TITLE"));
                unit.setEvaluation(rs.getString("EVALUATION"));
                unit.setDescription(rs.getString("DESCRIPTION"));

                units.put(unit.getId(), unit);
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return units;
    }

    @Override
    public boolean addStatement(int idUnit, int idStat) {
        boolean register = false;

        this.openConnection();
        try {
            stmt = con.prepareStatement(SQL_ASSIGN_STATEMENT_UNIT);
            stmt.setInt(1, idUnit);
            stmt.setInt(2, idStat);
            if (stmt.executeUpdate() > 0) {
                register = true;
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return register;
    }

    @Override
    public int getLastStatementId() {
        this.openConnection();
        int lastId = 0;

        try {
            stmt = con.prepareStatement(SQL_GET_LAST_STATEMENT);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                lastId = rs.getInt("last_id");
            }

            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lastId;
    }

    public HashMap<Integer, Statement> getStatementsSession(int sessionId) {
        ResultSet rs = null;
        Statement statement;
        HashMap<Integer, Statement> statements = new HashMap<>();

        this.openConnection();

        try {
            stmt = con.prepareStatement(SQL_GET_STATEMENTS_SESSION);
            stmt.setInt(1, sessionId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                statement = new Statement();
                statement.setId(rs.getInt("id_statement"));
                statement.setDescription(rs.getString("description"));
                String difficultyStr = rs.getString("difficulty");
                if (difficultyStr != null) {
                    Difficulty difficulty = Difficulty.valueOf(difficultyStr.toUpperCase());
                    statement.setDifficulty(difficulty);
                }
                statement.setAvailable(rs.getBoolean("available"));
                statement.setRoute(rs.getString("route"));

                statements.put(statement.getId(), statement);
            }

            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statements;
    }

    @Override
    public HashMap<Integer, Session> getSessionsStatement(int statementId) {
        Session session = null;
        ResultSet rs = null;
        HashMap<Integer, Session> sessions = new HashMap<>();

        this.openConnection();

        try {
            stmt = con.prepareStatement(SQL_GET_SESSIONS_STATEMENT);
            stmt.setInt(1, statementId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                session = new Session();
                session.setId(rs.getInt("ID_EXAM_SESSION"));
                session.setSession(rs.getString("SESSION"));
                session.setCourse(rs.getString("COURSE"));
                session.setDate(rs.getDate("DATE").toLocalDate());
                session.setDescription(rs.getString("DESCRIPTION"));
                session.setStatement(rs.getInt("ID_STATEMENT"));

                sessions.put(session.getId(), session);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("An error occurred.");
        }
        return sessions;
    }

    @Override
    public boolean addUnit(Unit unit) {
        boolean register = false;

        this.openConnection();
        try {
            stmt = con.prepareStatement(SQL_ADD_UNIT);
            stmt.setString(1, unit.getAcronym());
            stmt.setString(2, unit.getTitle());
            stmt.setString(3, unit.getEvaluation());
            stmt.setString(4, unit.getDescription());
            if (stmt.executeUpdate() > 0) {
                register = true;
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return register;
    }

    @Override
    public boolean addSession(Session session) {
        boolean register = false;

        this.openConnection();
        try {
            stmt = con.prepareStatement(SQL_ADD_SESSION);
            stmt.setString(1, session.getSession());
            stmt.setString(2, session.getDescription());
            stmt.setDate(3, Date.valueOf(session.getDate()));
            stmt.setString(4, session.getCourse());
            stmt.setInt(5, session.getStatement());
            if (stmt.executeUpdate() > 0) {
                register = true;
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return register;
    }

    @Override
    public boolean addStatement(Statement statement) {
        boolean register = false;

        this.openConnection();
        try {
            stmt = con.prepareStatement(SQL_ADD_STATEMENT);
            stmt.setString(1, statement.getDescription());
            stmt.setString(2, statement.getDifficulty().toString().toUpperCase());
            stmt.setBoolean(3, statement.isAvailable());
            stmt.setString(4, statement.getRoute());
            if (stmt.executeUpdate() > 0) {
                register = true;
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return register;
    }

    @Override
    public boolean modifySession(int idStat, int idExam) {
        boolean valid = false;
        this.openConnection();
        try {
            stmt = con.prepareStatement(SQL_MODIFY_SESSION);
            stmt.setInt(1, idStat);
            stmt.setInt(2, idExam);
            if (stmt.executeUpdate() > 0) {
                valid = true;
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("An error occurred.");
        }
        return valid;
    }

}
