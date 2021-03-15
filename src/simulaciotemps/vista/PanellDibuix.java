/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulaciotemps.vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import simulaciotemps.MeuError;
import simulaciotemps.model.Model;

/**
 *
 * @author mascport
 */
public class PanellDibuix extends JPanel implements MouseListener {

    private final int w;
    private final int h;
    private final Model mod;
    private final Vista vis;
    protected final int FPS = 24;  // 24 frames per segon
    private final ProcesPintat procpin;
    private BufferedImage bima;

    public PanellDibuix(int x, int y, Model m, Vista v) {
        w = x;
        h = y;
        mod = m;
        vis = v;
        bima = null;
        this.addMouseListener(this);
        this.setPreferredSize(new Dimension(w, h));
        procpin = new ProcesPintat(this);
        procpin.start();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void repaint() {
        if (this.getGraphics() != null) {
            paint(this.getGraphics());
        }
    }

    @Override
    public void paint(Graphics gr) {
        if (bima == null) {
            if (this.getWidth() > 0) {
                bima = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
                bima.getGraphics().setColor(Color.white);
                bima.getGraphics().fillRect(0, 0, bima.getWidth(), bima.getHeight());
            }
        }
        gr.drawImage(bima, 0, 0, this);
        gr.setColor(Color.red);
        int iter = 20;
        long[] temps = mod.getTemps();
        try {
            for (int i = 0; i < temps.length; i++) {
                if (temps[i] != 0) {
                    int x = iter;
                    int y = h - (int) (temps[i] / (temps[0] / 20));
                    gr.fillOval(x, y, mod.getRadi(), mod.getRadi());

                    Graphics2D g2d = (Graphics2D) gr;
                    if (i != 0) {
                        g2d.drawLine(iter - 16, h - (int) (temps[i - 1] / (temps[0] / 20)), x + 2, y);
                    } else {
                        g2d.drawLine(0, h, x + 2, y);
                    }

                    iter += 20;
                }
                vis.progreso(mod.getPorcentaje());
            }
        } catch (Exception ex) {
            //division entre cero
        }
    }
}

class ProcesPintat extends Thread {

    private final PanellDibuix pan;

    public ProcesPintat(PanellDibuix pd) {
        pan = pd;
    }

    @Override
    public void run() {
        long temps = System.nanoTime();
        long tram = 1000000000L / pan.FPS;
        while (true) {
            if ((System.nanoTime() - temps) > tram) {
                pan.repaint();
                temps = System.nanoTime();
                espera((long) (tram / 2000000));
            }
        }
    }

    private void espera(long t) {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            MeuError.informaError(e);
        }
    }
}
