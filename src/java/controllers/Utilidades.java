/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

/**
 *
 * @author daniel
 */
public class Utilidades {
    public static String toFirstUppercase(String n) {
        String valor = n.charAt(0) + "";
        valor = valor.toUpperCase();
        valor += n.substring(1).toLowerCase();
        return valor;
    }
}
