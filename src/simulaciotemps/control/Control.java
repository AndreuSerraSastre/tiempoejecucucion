/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulaciotemps.control;

import simulaciotemps.MeuError;
import simulaciotemps.SimulacioTemps;
import simulaciotemps.PerEsdeveniments;

/**
 *
 * @author mascport
 */
public class Control extends Thread implements PerEsdeveniments {

    private final SimulacioTemps prog;
    private int tipus = 0;
    private boolean seguir;

    public Control(SimulacioTemps p) {
        prog = p;
    }

    @Override
    public void run() {
        seguir = true;
        prog.getModel().calcularTotal();
        for (int i = 0; i < prog.getModel().nlength(); i++) {
            prog.getModel().setTemps(i, executar(prog.getModel().getn()[i]));
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
        int i = 0;
        while (i++ < n && seguir) {
            prog.getModel().añadirCalculo();
            int j = 0;
            while (j++ < n && seguir) {
                esperar(10);
            }
        }
    }

    private void nnormal(int n) {
        int i = 0;
        while (i++ < n && seguir) {
            prog.getModel().añadirCalculo();
            esperar(10);
        }
    }

    private void logaritmica(int n) {
        int i = 0;
        while (i++ < n && seguir) {
            prog.getModel().añadirCalculo();
            int j = 0;
            while (j++ < (int) Math.log(i) && seguir) {
                esperar(10);
            }
        }
    }

    private void esperar(long m) {
        try {
            Thread.sleep(m, 0);
        } catch (InterruptedException e) {
            MeuError.informaError(e);
        }
    }

    @Override
    public void notificar(String s) {
        if (s.startsWith("Parar")) {
            if (prog != null) {
                seguir = false;
                prog.getModel().notificar(s);
            }
        } else if (s.startsWith("n^2")) {
            tipus = 1;
            this.start();
        } else if (s.startsWith("n")) {
            tipus = 0;
            this.start();
        } else if (s.startsWith("log(n)")) {
            tipus = 2;
            this.start();
        }
    }
}
