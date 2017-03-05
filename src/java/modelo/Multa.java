/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author daniel
 */
public class Multa {
    private int id;
    private double valor;

    public int getId() {
        return id;
    }

    public double getValor() {
        return valor;
    }

    public Multa(int id, double valor) {
        this.id = id;
        this.valor = valor;
    }
}
