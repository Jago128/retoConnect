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
    final String SQLGETMODELS = "INSERT INTO UNIDAD_DIDACTICA VALUES ( ?,?,?,?)";
    final String SQL_MOSTRAR_ENUNCIADOS = "SELECT * FROM ENUNCIADO";
    final String SQLENUNCIADO = "SELECT * FROM ENUNCIADO WHERE ID_ENUNCIADO = (SELECT ID_ENUNCIADO FROM ASIGNAR WHERE ID_UNIDAD =?)";

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
        HashMap<Integer, Enunciado> enunciados=new HashMap<>();
        
        openConnection();
        
        try{
            stmt = con.prepareStatement(SQL_MOSTRAR_ENUNCIADOS);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                Enunciado enun=new Enunciado();
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
        }catch (SQLException e){
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
                                if (dificultadStr != null) 
                                {
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
}
