package main;

import java.time.LocalDate;
import java.time.format.*;
import java.time.temporal.*;
import java.util.*;

import modelo.*;
import controller.Controller;
import exceptions.NotFoundException;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import utilidades.Utilidades;

public class Main {

    public static int showMenu() {

        return Utilidades.leerInt("1. Add a unit or session \n"
                + "2. Create statement and link to unit \n3. Show statements from a certain unit \n"
                + "4. Show sessions from a certain statement \n5. Open document related to a certain statement \n6. Assign a statement to a session \n7. Leave\n"
                + "Choose an option: ", 1, 7);
    }

    public static void addUnit_Session(Controller cont) {
        System.out.println("1. Unit\n2. Session");
        int menu = Utilidades.leerInt(1, 2);
        switch (menu) {
            case 1:
                Unit unit = new Unit();
                String acr,
                 title,
                 eval,
                 desc;
                System.out.println("Acronym (40 characters max):");
                do {
                    acr = Utilidades.introducirCadena();
                    if (acr.length() > 40) {
                        System.out.println("The acronym can't be longer than 40 characters.");
                    }
                } while (acr.length() > 40);

                System.out.println("Title (40 characters max):");
                do {
                    title = Utilidades.introducirCadena();
                    if (title.length() > 40) {
                        System.out.println("The title can't be longer than 40 characters.");
                    }
                } while (title.length() > 40);

                System.out.println("Evaluation number/name (40 characters max):");
                do {
                    eval = Utilidades.introducirCadena();
                    if (eval.length() > 40) {
                        System.out.println("The evaluation field can't be longer than 40 characters.");
                    }
                } while (eval.length() > 40);

                System.out.println("Description:");
                desc = Utilidades.introducirCadena();

                unit.setAcronym(acr);
                unit.setTitle(title);
                unit.setEvaluation(eval);
                unit.setDescription(desc);
                cont.addUnit(unit);
                System.out.println("The unit has been added correctly.");
                break;

            case 2:
                String session,
                 desc2,
                 date,
                 course;
                int idStatement = -1;
                boolean error,
                 valid = false;
                Session cE = new Session();
                HashMap<Integer, Statement> statements = new HashMap<>(cont.getStatements());

                do {
                    try {
                        showStatements(statements);

                        System.out.println("Choose a statement ID: ");
                        idStatement = Utilidades.leerInt();

                        if (!statements.containsKey(idStatement)) {
                            throw new NotFoundException("Statement not found.");
                        }

                        valid = true;
                    } catch (NotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                } while (!valid);

                System.out.println("Session (40 caracteres max):");
                do {
                    session = Utilidades.introducirCadena();
                    if (session.length() > 40) {
                        System.out.println("Session can not be longer than 40 characters.");
                    }
                } while (session.length() > 40);

                System.out.println("Description:");
                desc2 = Utilidades.introducirCadena();

                do {
                    System.out.println("Date (Date format: AAAA-MM-DD):");
                    date = Utilidades.introducirCadena();
                    error = dateFormatErrorCheck(date);
                } while (error);

                System.out.println("Course (100 caracteres max):");
                do {
                    course = Utilidades.introducirCadena();
                    if (course.length() > 100) {
                        System.out.println("Course can not be longer than 100 characters.");
                    }
                } while (course.length() > 100);

                cE.setSession(session);
                cE.setDescription(desc2);
                cE.setStatement(idStatement);
                cE.setDate(LocalDate.parse(date));
                cE.setCourse(course);
                cont.addSession(cE);
                System.out.println("The session has been added correctly.");
                break;
        }
    }

    public static boolean dateFormatErrorCheck(String date) {
        DateTimeFormatter format = new DateTimeFormatterBuilder().appendValue(ChronoField.YEAR, 4).appendLiteral('-')
                .appendValue(ChronoField.MONTH_OF_YEAR).appendLiteral('-').appendValue(ChronoField.DAY_OF_MONTH)
                .toFormatter();
        try {
            if (!date.isEmpty()) {
                LocalDate.parse(date, format);
            }
        } catch (DateTimeParseException e) {
            return true;
        }
        return false;
    }

    public static void addStatement(Controller cont) {
        String desc, levelCheck, availCheck, route;
        int idSes=0, idUnit=0, idStat;
        Difficulty level = Difficulty.NONE;
        boolean error, avail = false, valid = false;
        Statement enun = new Statement();
        HashMap<Integer, Session> sessions = new HashMap<>(cont.getSessions());
        HashMap<Integer, Unit> units = new HashMap<>(cont.getUnits());

        do {
            try {
                showSessions(sessions);

                System.out.println("Which session will this statement belong to?");
                idSes = Utilidades.leerInt();

                if (!sessions.containsKey(idSes)) {
                    throw new NotFoundException("Session not found.");
                }

                valid = true;
            } catch (NotFoundException e) {
                System.out.println(e.getMessage());
            }
        } while (!valid);
        
        valid = false;
        
        do {
            try {
                showUnits(units);

                System.out.println("Which unit will this statement belong to?");
                idUnit = Utilidades.leerInt();

                if (!units.containsKey(idUnit)) {
                    throw new NotFoundException("Unit not found.");
                }

                valid = true;
            } catch (NotFoundException e) {
                System.out.println(e.getMessage());
            }
        } while (!valid);

        System.out.println("Description:");
        desc = Utilidades.introducirCadena();
        do {
            error = false;
            try {
                System.out.println("Difficulty (High, Medium, Low):");
                levelCheck = Utilidades.introducirCadena();
                switch (levelCheck) {
                    case "High":
                        level = Difficulty.HIGH;
                        break;

                    case "Medium":
                        level = Difficulty.MEDIUM;
                        break;

                    case "Low":
                        level = Difficulty.LOW;
                        break;

                    default:
                        throw new IllegalArgumentException("Invalid difficulty.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                error = true;
            }
        } while (error);

        System.out.println("Is this statement available? (y/n)");
        availCheck = Utilidades.introducirCadena("y", "n");
        if (availCheck.equalsIgnoreCase("y")) {
            avail = true;
        } else if (availCheck.equalsIgnoreCase("n")) {
            avail = false;
        }

        System.out.println("Document route (ig. ./assets/example.jpg):");
        do {
            route = Utilidades.introducirCadena();
            if (route.length() > 100) {
                System.out.println("The route can not be longer than 100 characters.");
            }
        } while (route.length() > 100);

        enun.setDescription(desc);
        enun.setDifficulty(level);
        enun.setAvailable(avail);
        enun.setRoute(route);
        cont.addStatement(enun);
        idStat = cont.getLastStatementId();
        cont.modifySession(idStat, idSes);
        cont.addStatement(idUnit, idStat);

        System.out.println("The statement has been added and linked correctly.");
    }

    public static void showStatementDoc(Controller cont) {
        HashMap<Integer, Statement> statements = new HashMap<>(cont.getStatements());
        Statement chosenStatement;
        int idStatement = -1;
        boolean valid = false;

        do {
            try {
                showStatements(statements);

                System.out.println("Choose a statement ID: ");
                idStatement = Utilidades.leerInt();

                if (!statements.containsKey(idStatement)) {
                    throw new NotFoundException("Statement not found.");
                }

                valid = true;
            } catch (NotFoundException e) {
                System.out.println(e.getMessage());
            }
        } while (!valid);

        chosenStatement = statements.get(idStatement);

        File document = new File(chosenStatement.getRoute());

        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().open(document);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void showStatementsUnit(Controller cont) {
        int chosenUnit;
        boolean valid = false;

        HashMap<Integer, Unit> units;
        units = new HashMap<>(cont.getUnits());

        do {
            try {
                showUnits(units);

                System.out.println("Which unit do you want to get statements from?");
                chosenUnit = Utilidades.leerInt();

                if (!units.containsKey(chosenUnit)) {
                    throw new NotFoundException("Session not found.");
                }

                HashMap<Integer, Statement> enunciados = new HashMap<>(cont.getStatementsSession(chosenUnit));

                for (Statement enunciado : enunciados.values()) {
                    System.out.println(enunciado);
                }

                valid = true;
            } catch (NotFoundException e) {
                System.out.println(e.getMessage());
            }
        } while (!valid);
    }

    public static void showStatementSessions(Controller cont) {
        int idStatement = -1;
        boolean valid = false;
        HashMap<Integer, Session> sessions;
        HashMap<Integer, Statement> statements = new HashMap<>(cont.getStatements());

        do {
            try {
                showStatements(statements);

                System.out.println("Choose a statement ID: ");
                idStatement = Utilidades.leerInt();

                if (!statements.containsKey(idStatement)) {
                    throw new NotFoundException("Statement not found.");
                }

                valid = true;
            } catch (NotFoundException e) {
                System.out.println(e.getMessage());
            }
        } while (!valid);

        sessions = new HashMap<>(cont.getSessionsStatement(idStatement));
        showSessions(sessions);
    }

    public static void modifySession(Controller cont) {
        boolean valid = false;
        int idSes = 0;
        int idStat = 0;
        HashMap<Integer, Session> sessions = new HashMap<>(cont.getSessions());
        HashMap<Integer, Statement> statements = new HashMap<>(cont.getStatements());
        
        do {
            try {
                showSessions(sessions);

                System.out.println("Choose a session ID: ");
                idSes = Utilidades.leerInt();

                if (!sessions.containsKey(idSes)) {
                    throw new NotFoundException("Session not found.");
                }

                valid = true;
            } catch (NotFoundException e) {
                System.out.println(e.getMessage());
            }
        } while (!valid);
        
        valid = false;
        
        do {
            try {
                showStatements(statements);

                System.out.println("Choose a statement ID: ");
                idStat = Utilidades.leerInt();

                if (!statements.containsKey(idStat)) {
                    throw new NotFoundException("Session not found.");
                }

                valid = true;
            } catch (NotFoundException e) {
                System.out.println(e.getMessage());
            }
        } while (!valid);

        valid = cont.modifySession(idStat, idSes);

        if (!valid) {
            System.out.println("Error during modification.");
        } else {
            System.out.println("Modification done correctly.");
        }
    }

    public static void showStatements(HashMap<Integer, Statement> statements) {
        for (Statement statement : statements.values()) {
            System.out.println(statement.toString());
        }
    }

    public static void showUnits(HashMap<Integer, Unit> units) {
        for (Unit unit : units.values()) {
            System.out.println(unit.toString());
        }
    }

    public static void showSessions(HashMap<Integer, Session> sessions) {
        for (Session session : sessions.values()) {
            System.out.println(session.toString());
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Controller cont = new Controller();
        int option;

        do {
            option= showMenu();

            switch (option) {
                case 1:
                    addUnit_Session(cont);
                    break;

                case 2:
                    addStatement(cont);
                    break;

                case 3:
                    showStatementsUnit(cont);
                    break;

                case 4:
                    showStatementSessions(cont);
                    break;

                case 5:
                    showStatementDoc(cont);
                    break;

                case 6:
                    modifySession(cont);
                    break;
                case 7:
                    System.out.println("Goodbye!");
                    break;
            }
        } while (option != 7);
    }
}
