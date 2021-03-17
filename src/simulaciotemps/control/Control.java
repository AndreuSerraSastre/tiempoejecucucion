/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulaciotemps.control;

import java.util.concurrent.TimeUnit;
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
        int max = prog.getModel().nlength() + (prog.getModel().getExecucio() * prog.getModel().nlength());
        imprimirCabecera();
        for (int i = prog.getModel().getExecucio() * prog.getModel().nlength(); i < max; i++) {
            if (seguir) {
                prog.getModel().setTemps(i, executar(prog.getModel().getn()[i % prog.getModel().nlength()]));
            }
        }
        prog.getModel().setExecucio((prog.getModel().getExecucio() + 1) % prog.getModel().getNmetodos());
        prog.notificar("Parar");
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
        System.out.println("Per executar " + n + " iteracions ha tardat "
                + formatNano(tempsfuncio) + " s.");
        if (!seguir) {
            return 0;
        }
        return tempsfuncio;
    }

    private String formatNano(long temps) {
        long segundos = TimeUnit.SECONDS.convert(temps, TimeUnit.NANOSECONDS);
        long milisegundos = TimeUnit.MILLISECONDS.convert(temps - (segundos * 1000 * 1000 * 1000), TimeUnit.NANOSECONDS);

        return segundos + "." + milisegundos;
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

    private int log2(int n) {
        int result = (int) (Math.log(n) / Math.log(2));
        return result;
    }

    private void logaritmica(int n) {
        int i = 0;
        while (i++ < n && seguir) {
            prog.getModel().añadirCalculo();
            int j = 0;
            while (j++ < log2(n) && seguir) {
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
                System.out.println("La execució s'ha turat amb èxit.");
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

    private void imprimirCabecera() {
        switch (tipus) {
            case 0:
                System.out.println("--- Execució de n: ---");
                break;
            case 1:
                System.out.println("--- Execució de n^2: ---");
                break;
            case 2:
                System.out.println("--- Execució de n·log(n): ---");
                break;
        }
    }
}
