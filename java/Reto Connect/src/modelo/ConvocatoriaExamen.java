package modelo;

import java.time.LocalDate;

public class ConvocatoriaExamen {

    private int id;
    private String convocatoria;
    private String descripcion;
    private LocalDate fecha;
    private String curso;
    private int enunciado;
    
    
    public ConvocatoriaExamen(){
        this.convocatoria="";
        this.descripcion="";
        this.fecha= LocalDate.now();
        this.curso="";
        this.enunciado=0;
    }
        public ConvocatoriaExamen(int id, String convocatoria, String descripcion, LocalDate fecha, String curso, int enunciado){
        this.id=id;
        this.convocatoria=convocatoria;
        this.descripcion=descripcion;
        this.fecha= fecha;
        this.curso=curso;
        this.enunciado=enunciado;
    }
        

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConvocatoria() {
        return convocatoria;
    }

    public void setConvocatoria(String convocatoria) {
        this.convocatoria = convocatoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getCurso() {
        return curso;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public int getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(int enunciado) {
        this.enunciado = enunciado;
    }

    @Override
    public String toString() {
        return "ConvocatoriaExamen{" + "id=" + id + ", convocatoria=" + convocatoria + ", descripcion=" + descripcion + ", fecha=" + fecha + ", curso=" + curso + ", enunciado=" + enunciado + '}';
    }

    
}
