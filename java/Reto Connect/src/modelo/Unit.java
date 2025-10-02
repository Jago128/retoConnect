package modelo;

public class Unit {

    private int id;
    private String acronym;
    private String title;
    private String evaluation;
    private String description;

    public Unit() {
    }

    public Unit(int id, String acronym, String title, String evaluation, String description) {
        this.id = id;
        this.acronym = acronym;
        this.title = title;
        this.evaluation = evaluation;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Unit{" + "id=" + id + ", acronym=" + acronym + ", title=" + title + ", evaluation=" + evaluation + ", description=" + description + '}';
    }
}
