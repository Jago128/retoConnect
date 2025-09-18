package main;

import controller.LoginController;

public class main {

    public static int mostrarMenu() {

        return utilidades.Utilidades.leerInt("1. Introducir Unidad Didactica y Convocatoria examen \n"
                + "2. Crear Enunciado  añadiendo unidades didacticas\n3. Mostrar Enunciado de una unidad expecifica \n"
                + "4. Consultar en que sesion se ha utilizado un enunciado \n5. Ver documento asociado ha un enunciado \n6. Asignar un enunciado ha una sesion \n7. Salir\n"
                + "Introduce una opcion: ", 1, 7);
    }

    public static void main(String[] args) {
        LoginController cont = new LoginController();
        // TODO Auto-generated method stub
        int opcion;
        do {
            opcion = mostrarMenu();

            switch (opcion) {
                case 1:

                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;
                case 6:

                    break;
                case 7:

                    break;
                case 8:
                    System.out.println("Adios");
                    break;
            }
        } while (opcion != 8);
    }
}
