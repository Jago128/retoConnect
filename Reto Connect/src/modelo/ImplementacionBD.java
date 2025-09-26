package modelo;

import java.sql.Date;
import java.sql.*;
import java.util.*;

public class ImplementacionBD implements InterfazDAO {

    private Connection con;
    private PreparedStatement stmt;

    @SuppressWarnings("FieldMayBeFinal")
    private ResourceBundle configFile;
    private String driverBD;
    private String urlBD;
    private String userBD;
    private String passwordBD;

    final String SQLADDUD_DIDAC = "INSERT INTO UNIDAD_DIDACTICA (ACRONIMO, TITULO, EVALUACION, DESCRIPCION) VALUES (?,?,?,?)";
    final String SQLADDCONVOCATORIA_EXAM = "INSERT INTO CONVOCATORIA_EXAMEN (CONVOCATORIA, DESCRIPCION, FECHA, CURSO) VALUES (?,?,?,?)";
    final String SQLADDENUN = "INSERT INTO ENUNCIADO (DESCRIPCION, NIVEL, DISPONIBLE, RUTA) VALUES (?,?,?,?)";
    final String SQLSEARCHENUNS = "SELECT * FROM ENUNCIADO";
    final String SQLSEARCHENUNID = "SELECT * FROM ENUNCIADO WHERE ID_ENUNCIADO=?";

    public ImplementacionBD() {
        this.configFile = ResourceBundle.getBundle("modelo.configClase");
        this.driverBD = this.configFile.getString("Driver");
        this.urlBD = this.configFile.getString("Conn");
        this.userBD = this.configFile.getString("DBUser");
        this.passwordBD = this.configFile.getString("DBPass");
    }

    private void openConnection() {
        try {
            con = DriverManager.getConnection(urlBD, this.userBD, this.passwordBD);
        } catch (SQLException e) {
            System.out.println("Error al intentar abrir la BD.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean addUd_Didactica(UnidadDidactica uD) {
        boolean register = false;

        this.openConnection();
        try {
            stmt = con.prepareStatement(SQLADDUD_DIDAC);
            stmt.setString(1, uD.getAcronimo());
            stmt.setString(2, uD.getTitulo());
            stmt.setString(3, uD.getEvaluacion());
            stmt.setString(4, uD.getDescripcion());
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
    public boolean addConvExam(ConvocatoriaExamen cE) {
        boolean register = false;

        this.openConnection();
        try {
            stmt = con.prepareStatement(SQLADDUD_DIDAC);
            stmt.setString(1, cE.getConvocatoria());
            stmt.setString(2, cE.getDescripcion());
            stmt.setDate(3, Date.valueOf(cE.getFecha()));
            stmt.setString(4, cE.getCurso());
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
    public Map<Integer, Enunciado> searchEnuns() {
        Map<Integer, Enunciado> enuns = new TreeMap<>();
        this.openConnection();
        try {
            stmt = con.prepareStatement(SQLSEARCHENUNS);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                enuns.put(rs.getInt("ID_ENUNCIADO"), new Enunciado(rs.getInt("ID_ENUNCIADO"), rs.getString("DESCRIPCION"), 
                        Nivel.valueOf(rs.getString("NIVEL").toUpperCase()), rs.getBoolean("DISPONIBLE"), rs.getString("RUTA")));
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enuns;
    }

    @Override
    public boolean searchEnunID(int id) {
        boolean found = false;

        this.openConnection();
        try {
            stmt = con.prepareStatement(SQLSEARCHENUNID);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                found = true;
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return found;
    }

    @Override
    public boolean addEnun(Enunciado enun) {
        boolean register = false;

        this.openConnection();
        try {
            stmt = con.prepareStatement(SQLADDENUN);
            stmt.setString(1, enun.getDescripcion());
            stmt.setString(2, enun.getDificultad().toString().toUpperCase());
            stmt.setBoolean(3, enun.isDisponible());
            stmt.setString(4, enun.getRuta());
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
}
