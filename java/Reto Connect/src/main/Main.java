package main;

import java.time.LocalDate;
import java.time.format.*;
import java.time.temporal.*;
import java.util.*;

import modelo.*;
import controller.LoginController;
import utilidades.Utilidades;

public class Main {

    public static int mostrarMenu() {

        return Utilidades.leerInt("1. Introducir Unidad Didactica y Convocatoria examen \n"
                + "2. Crear Enunciado  añadiendo unidades didacticas\n3. Mostrar Enunciado de una unidad expecifica \n"
                + "4. Consultar en que sesion se ha utilizado un enunciado \n5. Ver documento asociado ha un enunciado \n6. Asignar un enunciado ha una sesion \n7. Salir\n"
                + "Introduce una opcion: ", 1, 7);
    }
    
        public static void addUd_ConvExam(LoginController cont) {
        System.out.println("1. Unidad Didactica.\n2. Convocatoria Examen");
        int menu = Utilidades.leerInt(1, 2);
        switch (menu) {
            case 1:
                UnidadDidactica uD = new UnidadDidactica();
                String acr,
                 title,
                 eval,
                 desc;
                System.out.println("Introduce el acronimo (40 caracteres max):");
                do {
                    acr = Utilidades.introducirCadena();
                    if (acr.length() > 40) {
                        System.out.println("El acronimo no puede ser mas largo que 40 caracteres.");
                    }
                } while (acr.length() > 40);

                System.out.println("Introduce el titulo (40 caracteres max):");
                do {
                    title = Utilidades.introducirCadena();
                    if (title.length() > 40) {
                        System.out.println("El titulo no puede ser mas largo que 40 caracteres.");
                    }
                } while (title.length() > 40);

                System.out.println("Introduce la evaluacion (40 caracteres max):");
                do {
                    eval = Utilidades.introducirCadena();
                    if (eval.length() > 40) {
                        System.out.println("La evaluacion no puede ser mas largo que 40 caracteres.");
                    }
                } while (eval.length() > 40);

                System.out.println("Introduce la descripcion:");
                desc = Utilidades.introducirCadena();

                uD.setAcronimo(acr);
                uD.setTitulo(title);
                uD.setEvaluacion(eval);
                uD.setDescripcion(desc);
                cont.addUd_Didactica(uD);
                System.out.println("La unidad didactica ha sido añadida correctamente.");
                break;

            case 2:
                String conv,
                 desc2,
                 date,
                 curso;
                int id = 0;
                boolean error,
                 found;
                ConvocatoriaExamen cE = new ConvocatoriaExamen();
                Map<Integer, Enunciado> enuns = cont.searchEnuns();
                for (Enunciado en : enuns.values()) {
                    System.out.println(en.toString());
                }
                System.out.println("Introduce el id de un enunciado:");
                do {
                    id = Utilidades.leerInt();
                    found = cont.searchEnunID(id);
                    if (!found) {
                        System.out.println("El enunciado no se ha podido encontrar.");
                    }
                } while (!found);

                System.out.println("Introduce la convocatoria (40 caracteres max):");
                do {
                    conv = Utilidades.introducirCadena();
                    if (conv.length() > 40) {
                        System.out.println("La convocatoria no puede ser mas largo que 40 caracteres.");
                    }
                } while (conv.length() > 40);

                System.out.println("Introduce la descripcion:");
                desc2 = Utilidades.introducirCadena();

                do {
                    System.out.println("Introduce una fecha (Formato de fechas: AAAA-MM-DD):");
                    date = Utilidades.introducirCadena();
                    error = dateFormatErrorCheck(date);
                } while (error);

                System.out.println("Introduce el curso (100 caracteres max):");
                do {
                    curso = Utilidades.introducirCadena();
                    if (curso.length() > 100) {
                        System.out.println("El curso no puede ser mas largo que 100 caracteres.");
                    }
                } while (curso.length() > 100);

                cE.setId(id);
                cE.setConvocatoria(conv);
                cE.setDescripcion(desc2);
                cE.setFecha(LocalDate.parse(date));
                cE.setCurso(curso);
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

    public static void addEnum(LoginController cont) {
        String desc, levelCheck, availCheck, route;
        Nivel level = Nivel.NONE;
        boolean error, avail = false;
        Enunciado enun = new Enunciado();

        System.out.println("Introduce la descripcion:");
        desc = Utilidades.introducirCadena();
        do {
            error = false;
            try {
                System.out.println("Introduce el nivel (Alta, Media, Baja):");
                levelCheck = Utilidades.introducirCadena();
                switch (levelCheck) {
                    case "Alta":
                        level = Nivel.ALTA;
                        break;

                    case "Media":
                        level = Nivel.MEDIA;
                        break;

                    case "Baja":
                        level = Nivel.BAJA;
                        break;

                    default:
                        throw new IllegalArgumentException("El valor introducido es invalido.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                error = true;
            }
        } while (error);

        System.out.println("Esta disponible el enunciado? (Si/No)");
        availCheck = Utilidades.introducirCadena("Si", "No");
        if (availCheck.equalsIgnoreCase("Si")) {
            avail = true;
        } else if (availCheck.equalsIgnoreCase("No")) {
            avail = false;
        }

        System.out.println("Introduce la ruta del archivo relacionado (100 caracteres max):");
        do {
            route = Utilidades.introducirCadena();
            if (route.length() > 100) {
                System.out.println("La ruta no puede ser mas largo que 100 caracteres.");
            }
        } while (route.length() > 100);

        enun.setDescripcion(desc);
        enun.setDificultad(level);
        enun.setDisponible(avail);
        enun.setRuta(route);
        cont.addEnun(enun);
        System.out.println("El enunciado ha sido añadido correctamente.");
    }

    public static void mostrarDocumEnun(ImplementacionBD im) {
        HashMap<Integer, Enunciado> enuns = new HashMap<>();
        enuns = im.mostrarEnunciados();
        int idEnun = -1;
        for (Enunciado enun : enuns.values()) {
            System.out.println(enun.toString());
        }
        
        do{
            System.out.println("ID del enunciado que quieres: ");
            idEnun = Utilidades.leerInt();
            if(!enuns.containsKey(idEnun)){
                System.out.println("Id invalido.");
            }
        }while(!enuns.containsKey(idEnun));
        
        System.out.println(enuns.get(idEnun).toString());
    }

    public static HashMap<Integer, Enunciado> mostrarEnunciadosSesion(ImplementacionBD im) {
        int sesionElegida;
        System.out.println("Sobre que sesion quieres buscar el enunciado?");
        sesionElegida = Utilidades.leerInt();

        HashMap<Integer, Enunciado> enunciados = new HashMap<>();
        
        enunciados = im.getEnunciadosSesion(sesionElegida);
        for (Enunciado enunciado : enunciados.values()) {
            System.out.println(enunciado);

        }

        return enunciados;
    }
    
    public static void mostrarConvocatorias(ImplementacionBD im) {
        int id = 0;
        HashMap<Integer, ConvocatoriaExamen> mapaConvocatoria = new HashMap<>();
        System.out.println("Introduzca el id del Enunciado:");
        id = utilidades.Utilidades.leerInt();
        mapaConvocatoria = im.mostrarConvocatorias(id);
        if (mapaConvocatoria.isEmpty()) {
            System.out.println("EL enunciado introducido no existe");
        } else {
            for (ConvocatoriaExamen convocatoria : mapaConvocatoria.values()) {
                System.out.println(convocatoria);
            }
        }
    }
    
    public static void modConvocatotriaExamen(ImplementacionBD im){
        boolean comprobar=false;
        int enunciado=0;
        int convocatoriaExamen=0;
        
        System.out.println("Introduzca el id de la convocatiria ha editar: ");
        convocatoriaExamen= Utilidades.leerInt();
         System.out.println("Introduzca el id del enunciado ha asignar: ");
         enunciado=Utilidades.leerInt();
         
         comprobar=im.modConvocatoriaExamen(enunciado, convocatoriaExamen);
         
         if(!comprobar){
             System.out.println("No existe ninguna Convocatoria con ese id.");
         }else{
             System.out.println("Se ha modificado correctamente.");
         }
    }

    public static void main(String[] args) {
        LoginController cont = new LoginController();
        // TODO Auto-generated method stub
        int opcion;
        
        do {
            opcion = mostrarMenu();
            ImplementacionBD im = new ImplementacionBD();

            switch (opcion) {
                case 1:
                    addUd_ConvExam(cont);
                    break;
                    
                case 2:
                    addEnum(cont);
                    break;
                    
                case 3:
                    mostrarEnunciadosSesion(im);
                    break;
                    
                case 4:
                    mostrarConvocatorias(im);
                    break;
                    
                case 5:
                    mostrarDocumEnun(im);
                    break;
                    
                case 6:
                    modConvocatotriaExamen(im);
                    break;
                case 7:
                    System.out.println("Adios");
                    break;
            }
        } while (opcion != 7);
    }
}
