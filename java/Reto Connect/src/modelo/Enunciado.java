package modelo;

public class Enunciado {

    private int id;
    private String descripcion;
    private Nivel dificultad;
    private boolean disponible;
    private String ruta;

    public Enunciado() {
        this.id = 0;
        this.descripcion = "";
        this.dificultad = Nivel.NONE;
        this.disponible = false;
        this.ruta = "";
    }

    public Enunciado(int id, String descripcion, Nivel dificultad, boolean disponible, String ruta) {
        this.id = id;
        this.descripcion = descripcion;
        this.dificultad = dificultad;
        this.disponible = disponible;
        this.ruta = ruta;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Nivel getDificultad() {
        return dificultad;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public String getRuta() {
        return ruta;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setDificultad(Nivel dificultad) {
        this.dificultad = dificultad;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    @Override
    public String toString() {
        return "Enunciado{id=" + id + ", descripcion='" + descripcion + "', dificultad=" + dificultad + ", disponible=" + disponible + ", ruta='" + ruta + "'}";
    }
}
