/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulaciotemps.model;

import simulaciotemps.SimulacioTemps;
import simulaciotemps.PerEsdeveniments;

/**
 *
 * @author mascport
 */
public class Model implements PerEsdeveniments {

    private final SimulacioTemps prog;
    private final int nmetodos = 3; //Numero de metodos utilizados
    private final int[] n = {2, 4, 8, 16, 32};
    private final int radi = 7;
    private final long[] temps = new long[n.length * nmetodos];
    private double porcentaje = 0;
    private int totalporcentaje = 0;
    private int contadorporcentaje = 0;
    private int execucio = 0;

    public Model(SimulacioTemps p) {
        prog = p;
    }

    public int getExecucio() {
        return execucio;
    }

    public void setExecucio(int execucio) {
        this.execucio = execucio;
    }

    public int getNmetodos() {
        return nmetodos;
    }

    public long[] getTemps() {
        return temps;
    }

    public int getLastTemps() {
        for (int i = 0; i < temps.length; i++) {
            if (temps[i] == 0) {
                return i;
            }
        }
        return 0;
    }

    public int nlength() {
        return n.length;
    }

    public int[] getn() {
        return n;
    }

    public void setTemps(int i, long temp) {
        temps[i] = temp;
    }

    public void calcularTotal() {
        calcularTotalPrivate();
    }

    public int getRadi() {
        return radi;
    }

    @Override
    public void notificar(String s) {
        if (s.startsWith("Parar")) {
            reiniciarDatos();
        }
    }

    private void calcularTotalPrivate() {
        for (int i = 0; i < n.length; i++) {
            totalporcentaje += n[i];
        }
    }

    public void añadirCalculo() {
        porcentaje = ++contadorporcentaje * 1.0 / totalporcentaje * 100;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    private void reiniciarDatos() {
        porcentaje = 0;
        totalporcentaje = 0;
        contadorporcentaje = 0;
    }
}
