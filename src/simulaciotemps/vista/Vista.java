/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulaciotemps.vista;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import simulaciotemps.SimulacioTemps;
import simulaciotemps.MeuError;
import simulaciotemps.PerEsdeveniments;

/**
 *
 * @author mascport
 */
public class Vista extends JFrame implements ActionListener, PerEsdeveniments, ChangeListener {

    private SimulacioTemps prog;
    private JProgressBar porc;

    public Vista(String s, SimulacioTemps p) {
        super(s);
        prog = p;
        this.getContentPane().setLayout(new BorderLayout());
        JPanel bots = new JPanel();
        JButton boto2 = new JButton("Parar");
        boto2.addActionListener(this);
        bots.add(boto2);
        JButton boto3 = new JButton("n");
        boto3.addActionListener(this);
        bots.add(boto3);
        JButton boto4 = new JButton("n^2");
        boto4.addActionListener(this);
        bots.add(boto4);
        JButton boto5 = new JButton("nlog(n)");
        boto5.addActionListener(this);
        bots.add(boto5);
        this.add(BorderLayout.NORTH, bots);
        PanellDibuix panell = new PanellDibuix(800, 800, prog.getModel(), this);
        this.add(BorderLayout.CENTER, panell);
        porc = new JProgressBar();
        porc.setValue(0);
        this.add(porc, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void mostrar() {
        this.pack();
        this.setVisible(true);
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            MeuError.informaError(e);
        }
        this.revalidate();
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comanda = e.toString();
        int a = comanda.indexOf(",cmd=") + 5;
        comanda = comanda.substring(a, comanda.indexOf(",", a));
        prog.notificar(comanda);
    }

    @Override
    public void notificar(String s) {

    }

    @Override
    public void stateChanged(ChangeEvent e) {
    }

    public void progreso(double prog) {
        porc.setValue((int) prog);
    }
}
