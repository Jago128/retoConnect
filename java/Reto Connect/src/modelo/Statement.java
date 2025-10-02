package modelo;

public class Statement {

    private int id;
    private String description;
    private Difficulty difficulty;
    private boolean available;
    private String route;

    public Statement() {
    }

    public Statement(int id, String description, Difficulty difficulty, boolean available, String route) {
        this.id = id;
        this.description = description;
        this.difficulty = difficulty;
        this.available = available;
        this.route = route;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return "Statement{" + "id=" + id + ", description=" + description + ", difficulty=" + difficulty + ", available=" + available + ", route=" + route + '}';
    }
}
