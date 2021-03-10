/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulaciotemps.model;

import simulaciotemps.MeuError;
import simulaciotemps.SimulacioTemps;
import simulaciotemps.PerEsdeveniments;

/**
 *
 * @author mascport
 */
public class Model implements PerEsdeveniments {

    private SimulacioTemps prog;
    private int[] n = {5, 10, 15, 20, 25};
    private int x;
    private int y;
    private double grau;
    private final int radi = 50;
    private int vel;
    private int masa;
    private long[] temps = new long[n.length];
    private int tipus = 0;

    public Model(SimulacioTemps p) {
        prog = p;
        x = y = 0;
        grau = 0.0;
        vel = 7;
        masa = 7;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public long[] getTemps() {
        return temps;
    }

    private void incGrau() {
        for (int i = 0; i < n.length; i++) {
            temps[i] = executar(n[i]);
        }
    }

    private long executar(int n) {
        long temps = System.nanoTime();
        switch (tipus) {
            case 0:
                nnormal(n);
                break;
            case 1:
                quadratica(n);
                break;
            case 2:
                break;
        }
        temps = System.nanoTime() - temps;
        System.out.println("Per executar " + n + " he tardat "
                + temps + " ns.");
        return temps;
    }

    private void quadratica(int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                esperar(10);
            }
        }
    }

    private void nnormal(int n) {
        for (int i = 0; i < n; i++) {
            esperar(10);
        }
    }

    private void esperar(int n) {
        try {
            Thread.sleep(n);
        } catch (InterruptedException e) {
            MeuError.informaError(e);
        }
    }

    public int getRadi() {
        return radi;
    }

    public double getGrau() {
        return grau;
    }

    public int getVel() {
        return vel;
    }

    public void setVel(int v) {
        vel = v;
    }

    public int getMasa() {
        return masa;
    }

    @Override
    public void notificar(String s) {
        if (s.startsWith("IncGrau")) {
            this.incGrau();
        } else if (s.startsWith("n")) {
            tipus = 0;
        } else if (s.startsWith("n^2")) {
            tipus = 1;
        } else if (s.startsWith("log(n)")) {
            tipus = 2;
        }
    }
}
