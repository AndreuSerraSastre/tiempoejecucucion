/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulaciotemps.control;

import simulaciotemps.SimulacioTemps;
import simulaciotemps.PerEsdeveniments;

/**
 *
 * @author mascport
 */
public class Control extends Thread implements PerEsdeveniments {

    private final SimulacioTemps prog;

    public Control(SimulacioTemps p) {
        prog = p;
    }

    @Override
    public void run() {
            prog.getModel().notificar("IncGrau");
    }

    @Override
    public void notificar(String s) {
        System.out.println(s);
        if (s.startsWith("Parar")) {
            //seguir = false;
        } else if (s.startsWith("Arrancar")) {
            this.start();
        }
        else if (s.startsWith("n")) {
            prog.getModel().notificar("n");
        }
        else if (s.startsWith("n^2")) {
            prog.getModel().notificar("n^2");
        }
        else if (s.startsWith("log(n)")) {
            prog.getModel().notificar("log(n)");
        }
    }
}
