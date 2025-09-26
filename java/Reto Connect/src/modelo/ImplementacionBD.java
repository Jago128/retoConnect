package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class ImplementacionBD implements InterfazDAO {

    // Atributos
    private Connection con;
    private PreparedStatement stmt;

    // Los siguientes atributos se utilizan para recoger los valores del fich de
    // configuraci n
    private ResourceBundle configFile;
    private String driverBD;
    private String urlBD;
    private String userBD;
    private String passwordBD;

    // dej
    final String SQLGETMODELS = "INSERT INTO UNIDAD_DIDACTICA VALUES ( ?,?,?,?)"; //ESTO HAY QUE QUITAAAAAAAAAAAAAAR!!!
    final String SQL_MOSTRAR_ENUNCIADOS = "SELECT * FROM ENUNCIADO";
    final String SQLENUNCIADO = "SELECT * FROM ENUNCIADO WHERE ID_ENUNCIADO = (SELECT ID_ENUNCIADO FROM ASIGNAR WHERE ID_UNIDAD =?)";
    final String SQLMOSTRARSESIONES = "SELECT * FROM CONVOCATORIA_EXAMEN C JOIN ENUNCIADO E ON C.ID_ENUNCIADO=E.ID_ENUNCIADO WHERE E.ID_ENUNCIADO = ?";
    final String SQLCREARENUNCIADO = "INSERT INTO ENUNCIADO VALUES (?, ? ,?, ?, ?)";

    final String MODCONVOCATORIA = "UPDATE CONVOCATORIA_EXAMEN SET ID_ENUNCIADO = ?, WHERE ID_CONVOCATORIA_EXAMEN = ?";

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
            System.out.println("Error al intentar abrir la BD");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HashMap<Integer, Enunciado> mostrarEnunciados() {
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
                enun.setDificultad(Dificultad.valueOf(rs.getString("nivel")));
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
                enunciado.setDescripcion(rs.getString("descripcion"));
                String dificultadStr = rs.getString("nivel");
                if (dificultadStr != null) {
                    Dificultad dificultad = Dificultad.valueOf(dificultadStr.toUpperCase());
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
    public HashMap<Integer, ConvocatoriaExamen> mostrarConvocatorias(int idEnunciado) {
        ConvocatoriaExamen ce = null;
        ResultSet rs = null;
        HashMap<Integer, ConvocatoriaExamen> est = new HashMap<>();

        this.openConnection();

        try {
            stmt = con.prepareStatement(SQLMOSTRARSESIONES);
            stmt.setInt(1, idEnunciado);
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
    
    //Insertar un enunciado
	public boolean crearEnunciado(Enunciado enunciado) {

		// TODO Auto-generated method stub
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
