package modelo;

import java.time.LocalDate;

public class Session {

    private int id;
    private String session;
    private String description;
    private LocalDate date;
    private String course;
    private int statement;

    public Session() {
    }

    public Session(int id, String session, String description, LocalDate date, String course, int statement) {
        this.id = id;
        this.session = session;
        this.description = description;
        this.date = date;
        this.course = course;
        this.statement = statement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getStatement() {
        return statement;
    }

    public void setStatement(int statement) {
        this.statement = statement;
    }

    @Override
    public String toString() {
        return "Session{" + "id=" + id + ", session=" + session + ", description=" + description + ", date=" + date + ", course=" + course + ", statement=" + statement + '}';
    }
}
