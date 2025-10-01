package modelo;

import java.sql.Date;
import java.sql.*;
import java.time.LocalDate;
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

    final String SQL_MOSTRAR_ENUNCIADOS = "SELECT * FROM ENUNCIADO";
    final String SQL_EXAMEN_ENUNCIADO = "SELECT * FROM CONVOCATORIA_EXAMEN WHERE ID_ENUNCIADO = ?";
    final String SQLENUNCIADO = "SELECT * FROM ENUNCIADO WHERE ID_ENUNCIADO = (SELECT ID_ENUNCIADO FROM ASIGNAR WHERE ID_UNIDAD =?)";
    final String SQLMOSTRARSESIONES = "SELECT * FROM CONVOCATORIA_EXAMEN C JOIN ENUNCIADO E ON C.ID_ENUNCIADO=E.ID_ENUNCIADO WHERE E.ID_ENUNCIADO = ?";
    final String SQLADDUD_DIDAC = "INSERT INTO UNIDAD_DIDACTICA (ACRONIMO, TITULO, EVALUACION, DESCRIPCION) VALUES (?,?,?,?)";
    final String SQLADDCONVOCATORIA_EXAM = "INSERT INTO CONVOCATORIA_EXAMEN (CONVOCATORIA, DESCRIPCION, FECHA, CURSO, ID_ENUNCIADO)  VALUES (?,?,?,?,?)";
    final String SQLADDENUN = "INSERT INTO ENUNCIADO (DESCRIPCION, NIVEL, DISPONIBLE, RUTA) VALUES (?,?,?,?)";
    final String SQLSEARCHENUNS = "SELECT * FROM ENUNCIADO";
    final String SQLSEARCHENUNID = "SELECT * FROM ENUNCIADO WHERE ID_ENUNCIADO=?";
    final String SQLCREARENUNCIADO = "INSERT INTO ENUNCIADO VALUES (?, ? ,?, ?, ?)";

    final String MODCONVOCATORIA = "UPDATE CONVOCATORIA_EXAMEN SET ID_ENUNCIADO = ? WHERE ID_CONVOCATORIA_EXAMEN = ?";

    // Para la conexi n utilizamos un fichero de configuaraci n, config que
    // guardamos en el paquete control:
    public ImplementacionBD() {
        this.configFile = ResourceBundle.getBundle("configClase");
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

    public HashMap<Integer, Enunciado> getStatements() {
        ResultSet rs;
        HashMap<Integer, Enunciado> enunciados = new HashMap<>();

        openConnection();

        try {
            stmt = con.prepareStatement(SQL_MOSTRAR_ENUNCIADOS);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Enunciado enun = new Enunciado();
                enun.setId(rs.getInt("id_enunciado"));
                enun.setDescripcion(rs.getString("descripcion"));
                enun.setDificultad(Nivel.valueOf(rs.getString("nivel")));
                enun.setDisponible(rs.getBoolean("disponible"));
                enun.setRuta(rs.getString("ruta"));
                enunciados.put(enun.getId(), enun);
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return enunciados;
    }

    public HashMap<Integer, Enunciado> getEnunciadosSesion(int sesionElegida) {
        ResultSet rs = null;
        Enunciado enunciado;
        HashMap<Integer, Enunciado> enunciados = new HashMap<>();

        this.openConnection();

        try {

            stmt = con.prepareStatement(SQLENUNCIADO);
            stmt.setInt(1, sesionElegida);
            rs = stmt.executeQuery();

            while (rs.next()) {
                enunciado = new Enunciado();
                enunciado.setId(rs.getInt("id_enunciado"));
                enunciado.setDescripcion(rs.getString("descripcion"));
                String dificultadStr = rs.getString("nivel");
                if (dificultadStr != null) {
                    Nivel dificultad = Nivel.valueOf(dificultadStr.toUpperCase());
                    enunciado.setDificultad(dificultad);
                }
                enunciado.setDisponible(rs.getBoolean("disponible"));
                enunciado.setRuta(rs.getString("ruta"));

                enunciados.put(enunciado.getId(), enunciado);
            }

            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return enunciados;
    }

    @Override
    public HashMap<Integer, ConvocatoriaExamen> getExams(int statementId) {
        ConvocatoriaExamen ce = null;
        ResultSet rs = null;
        HashMap<Integer, ConvocatoriaExamen> est = new HashMap<>();

        this.openConnection();

        try {
            stmt = con.prepareStatement(SQLMOSTRARSESIONES);
            stmt.setInt(1, statementId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                ce = new ConvocatoriaExamen();
                ce.setId(rs.getInt("ID_CONVOCATORIA_EXAMEN"));
                ce.setConvocatoria(rs.getString("CONVOCATORIA"));
                ce.setCurso(rs.getString("CURSO"));
                ce.setFecha(rs.getDate("FECHA").toLocalDate());
                ce.setDescripcion(rs.getString("DESCRIPCION"));
                ce.setEnunciado(rs.getInt("ID_ENUNCIADO"));

                est.put(ce.getId(), ce);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error de SQL.");
        }
        return est;
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
            stmt = con.prepareStatement(SQLADDCONVOCATORIA_EXAM);
            stmt.setString(1, cE.getConvocatoria());
            stmt.setString(2, cE.getDescripcion());
            stmt.setDate(3, Date.valueOf(cE.getFecha()));
            stmt.setString(4, cE.getCurso());
            stmt.setInt(5, cE.getEnunciado());
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

    //Insertar un enunciado
    public boolean crearEnunciado(Enunciado enunciado) {

        boolean ok = false;
        this.openConnection();
        try {
            // Preparamos la sentencia stmt con la conexion y sentencia sql correspondiente
            stmt = con.prepareStatement(SQLCREARENUNCIADO);
            stmt.setString(1, enunciado.getDescripcion());
            stmt.setString(2, enunciado.getDificultad().toString()); //castear a string para introducirlo en la base de datos
            stmt.setBoolean(3, enunciado.isDisponible());
            stmt.setString(4, enunciado.getRuta());

            if (stmt.executeUpdate() > 0) {
                ok = true;
            }

            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al verificar credenciales: " + e.getMessage());
        }
        return ok;

    }

    public boolean modConvocatoriaExamen(int Encunciado, int Convocatoria) {
        boolean modificado = false;
        this.openConnection();
        try {
            stmt = con.prepareStatement(MODCONVOCATORIA);
            stmt.setInt(1, Encunciado);
            stmt.setInt(2, Convocatoria);
            if (stmt.executeUpdate() > 0) {
                modificado = true;
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error de SQL.");
        }
        return modificado;
    }

}
