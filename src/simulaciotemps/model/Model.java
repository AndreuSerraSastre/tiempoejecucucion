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

    private final SimulacioTemps prog;
    private final int[] n = {5, 10, 15, 20, 25};
    private final int radi = 7;
    private final long[] temps = new long[n.length];
    private int tipus = 0;
    private double porcentaje = 0;
    private int totalporcentaje = 0;
    private int contadorporcentaje = 0;

    public Model(SimulacioTemps p) {
        prog = p;
    }

    public long[] getTemps() {
        return temps;
    }

    private void incGrau() {
        calcularTotal();
        for (int i = 0; i < n.length; i++) {
            temps[i] = executar(n[i]);
        }
    }

    private long executar(int n) {
        long tempsfuncio = System.nanoTime();
        switch (tipus) {
            case 0:
                nnormal(n);
                break;
            case 1:
                quadratica(n);
                break;
            case 2:
                logaritmica(n);
                break;
        }
        tempsfuncio = System.nanoTime() - tempsfuncio;
        System.out.println("Per executar " + n + " he tardat "
                + tempsfuncio + " ns.");
        return tempsfuncio;
    }

    private void quadratica(int n) {
        for (int i = 1; i <= n; i++) {
            añadirCalculo();
            for (int j = 1; j <= n; j++) {
                esperar(10);
            }
        }
    }

    private void nnormal(int n) {
        for (int i = 1; i <= n; i++) {
            añadirCalculo();
            esperar(10);
        }
    }

    private void logaritmica(int n) {
        for (int i = 1; i <= n; i++) {
            añadirCalculo();
            int t2 = (int) Math.log(i);
            for (int j = 1; j <= t2; j++) {
                esperar(10);
            }
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

    @Override
    public void notificar(String s) {
        if (s.startsWith("IncGrau")) {
            this.incGrau();
        } else if (s.startsWith("n^2")) {
            tipus = 1;
            this.incGrau();
        } else if (s.startsWith("n")) {
            tipus = 0;
            this.incGrau();
        } else if (s.startsWith("log(n)")) {
            tipus = 2;
            this.incGrau();
        } else if (s.startsWith("Parar")) {
            reiniciarDatos();
        }
    }

    private void calcularTotal() {
        for (int i = 0; i < n.length; i++) {
            totalporcentaje += n[i];
        }
    }

    private void añadirCalculo() {
        porcentaje = ++contadorporcentaje * 1.0 / totalporcentaje * 100;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    private void reiniciarDatos() {
        for (int i = 0; i < n.length; i++) {
            temps[i] = 0;
        }
        porcentaje = 0;
        totalporcentaje = 0;
        contadorporcentaje = 0;
    }
}
