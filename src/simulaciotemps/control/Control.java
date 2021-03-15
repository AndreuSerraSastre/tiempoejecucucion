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
    private String notificacion = "";

    public Control(SimulacioTemps p) {
        prog = p;
    }

    @Override
    public void run() {
        prog.getModel().notificar(notificacion);
    }

    @Override
    public void notificar(String s) {
        if (s.startsWith("Parar")) {
            if (prog != null) {
                prog.getModel().notificar(s);
            }
        } else if (s.startsWith("n^2")) {
            notificacion = s;
            this.start();
        } else if (s.startsWith("n")) {
            notificacion = s;
            this.start();
        } else if (s.startsWith("log(n)")) {
            notificacion = s;
            this.start();
        }
    }
}
