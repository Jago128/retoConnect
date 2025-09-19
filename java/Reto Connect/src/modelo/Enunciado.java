package modelo;

public class Enunciado {
    private int id;
    private String descripcion;
    private Dificultad dificultad;
    private boolean disponible;
    private String ruta;
            
    
    public Enunciado(int id, String descripcion, Dificultad dificultad, boolean disponible, String ruta) {
        this.id = id;
        this.descripcion = descripcion;
        this.dificultad = dificultad;
        this.disponible = disponible;
        this.ruta = ruta;
    }
    
<<<<<<< Updated upstream
    public Enunciado() {
        this.id = 0;
        this.descripcion = "";
        this.dificultad = null;
        this.disponible = false;
        this.ruta = "";
    }

=======
>>>>>>> Stashed changes
    public Enunciado() {
        this.id = 0;
        this.descripcion = "";
        this.dificultad = null;
        this.disponible = false;
        this.ruta = "";
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Dificultad getDificultad() {
        return dificultad;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public String getRuta() {
        return ruta;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setDificultad(Dificultad dificultad) {
        this.dificultad = dificultad;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    // toString
    @Override
    public String toString() {
        return "Enunciado{id=" + id + ", descripcion='" + descripcion + "', dificultad=" + dificultad + ", disponible=" + disponible + ", ruta='" + ruta + "'}";
    }
    
}

