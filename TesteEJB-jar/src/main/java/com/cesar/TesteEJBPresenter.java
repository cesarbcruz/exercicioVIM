/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cesar;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cesar
 */
public class TesteEJBPresenter {

    private TesteEJBGUI view;
    private final ExemploEJBInterface ejb;
    private final ProgressEJBInterface progress;
    private final int size = 999999;
    private int currentProgress;

    public TesteEJBPresenter(TesteEJBGUI view) throws Exception {
        this.view = view;
        ejb = (ExemploEJBInterface) ConexaoJBoss.getInstance().lookupRemote(ExemploEJBStatefull.class, ExemploEJBInterface.class);
        progress = (ProgressEJBInterface) ConexaoJBoss.getInstance().lookupRemote(ProgressEJB.class, ProgressEJBInterface.class);
    }

    private void otimizarVM(){
        System.gc();
    }
    
    protected void iniciar() {
        otimizarVM();
        new Thread(new Runnable() {

            @Override
            public void run() {
                view.getjButton1().setEnabled(false);
                view.getjButton2().setEnabled(false);
                ejb.metodoA(size);
                try {
                    otimizarVM();
                    finalize();
                } catch (Throwable ex) {
                    Logger.getLogger(TesteEJBPresenter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            protected void finalize() throws Throwable {
                view.getjButton1().setEnabled(true);
                view.getjButton2().setEnabled(true);
                super.finalize();
            }
        }).start();

        exibirProgresso();
    }

    private void exibirProgresso() {
        view.getjProgressBar1().setMaximum(size);
        TimerTask tasknew = new TimerTask() {

            @Override
            public void run() {
                try {
                    currentProgress = progress.getProgressMap("metodoA");
                    if (currentProgress > 0 && view.getjProgressBar1().getValue() < view.getjProgressBar1().getMaximum()) {
                        view.getjProgressBar1().setValue(currentProgress);
                    } else if (view.getjProgressBar1().getMaximum() == view.getjProgressBar1().getValue()) {
                        try {
                            finalize();
                        } catch (Throwable ex) {
                            Logger.getLogger(TesteEJBPresenter.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            protected void finalize() throws Throwable {

                super.finalize();
            }
        };
        new Timer().schedule(tasknew, 500, 500);
    }
}
