package main;

import controller.LoginController;
import java.util.HashMap;
import modelo.ConvocatoriaExamen;
import modelo.Enunciado;
import modelo.ImplementacionBD;
import utilidades.Utilidades;

public class main {

    public static int mostrarMenu() {

        return Utilidades.leerInt("1. Introducir Unidad Didactica y Convocatoria examen \n"
                + "2. Crear Enunciado  añadiendo unidades didacticas\n3. Mostrar Enunciado de una unidad expecifica \n"
                + "4. Consultar en que sesion se ha utilizado un enunciado \n5. Ver documento asociado ha un enunciado \n6. Asignar un enunciado ha una sesion \n7. Salir\n"
                + "Introduce una opcion: ", 1, 7);
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

    public static void main(String[] args) {
        LoginController cont = new LoginController();
        // TODO Auto-generated method stub
        int opcion;
        
        do {
            opcion = mostrarMenu();
            ImplementacionBD im = new ImplementacionBD();

            switch (opcion) {
                case 1:

                    break;
                case 2:

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

                    break;
                case 7:
                    System.out.println("Adios");
                    break;
            }
        } while (opcion != 7);
    }
}
