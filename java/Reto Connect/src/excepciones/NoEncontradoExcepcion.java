/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excepciones;

/**
 *
 * @author kevin
 */
public class NoEncontradoExcepcion extends Exception {

    public NoEncontradoExcepcion() {
        System.out.println("Los datos introducidos no están en la base de datos");
    }
}
