package modelo;

import java.time.LocalDate;

public class ConvocatoriaExamen {
    private String convocatoria;
    private String descripcion;
    private LocalDate fecha;
    private String curso;
    
    
    public ConvocatoriaExamen(){
        this.convocatoria="";
        this.descripcion="";
        this.fecha= LocalDate.now();
        this.curso="";
    }
        public ConvocatoriaExamen(String convocatoria, String descripcion, LocalDate fecha, String curso){
        this.convocatoria=convocatoria;
        this.descripcion=descripcion;
        this.fecha= fecha;
        this.curso=curso;
    }
    public String getConvocatoria(){
        return convocatoria;
    }
    public void setConvocatoria(String convocatoria){
        this.convocatoria=convocatoria;
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

    @Override
    public String toString() {
        return "ConvocatoriaExamen{" + "convocatoria=" + convocatoria + ", descripcion=" + descripcion + ", fecha=" + fecha + ", curso=" + curso + '}';
    }
    
}
