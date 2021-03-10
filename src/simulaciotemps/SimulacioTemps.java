/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulaciotemps;

import simulaciotemps.control.Control;
import simulaciotemps.model.Model;
import simulaciotemps.vista.Vista;

/**
 *
 * @author andre
 */
public class SimulacioTemps implements PerEsdeveniments {

    private Model mod;    // Punter al Model del patró
    private Vista vis;    // Punter a la Vista del patró
    private Control con;  // punter al Control del patró

    /*
        Construcció de l'esquema MVC
     */
    private void inicio() {
        mod = new Model(this);
        con = null;
        vis = new Vista("Simulació de tempsos computacionals", this);
        vis.mostrar();
    }

    public static void main(String[] args) {
        (new SimulacioTemps()).inicio();
    }

    /*
        Funció símple de la comunicació per Patró d'esdeveniments
     */
    @Override
    public void notificar(String s) {
        if (s.startsWith("Arrancar")) {
            if (con == null) {
                con = new Control(this);
                con.notificar(s);
            }
        } else if (s.startsWith("Parar")) {
            if (con != null) {
                con.notificar(s);
                con = null;
            }
        } else if (s.startsWith("n")) {
            if (con != null) {
                con.notificar("n");
            }
        } else if (s.startsWith("n^2")) {
            if (con != null) {
                con.notificar("n^2");
            }
        } else if (s.startsWith("log(n)")) {
            if (con != null) {
                con.notificar("log(n)");
            }
        } else if (s.startsWith("Picat:")) {
            s = s.substring(s.indexOf(":") + 1);
            int x = Integer.parseInt(s.substring(0, s.indexOf(",")));
            int y = Integer.parseInt(s.substring(s.indexOf(",") + 1));
            mod.setXY(x, y);
        } else if (s.startsWith("Velocitat:")) {
            int v = Integer.parseInt(s.substring(s.indexOf(":") + 1));
            mod.setVel(v);
        }
    }

    /*
        Mètode public de retorn de la instància del model de dades
     */
    public Model getModel() {
        return mod;
    }
}
