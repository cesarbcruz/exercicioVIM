/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cesar;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 *
 * @author cesar
 */
@Stateless
@Remote(ExemploEJBInterface.class)
public class ExemploEJBStatefull implements ExemploEJBInterface {

    @EJB
    ProgressEJBInterface progress;
            
    
    @Override
    public void metodoA(int size) {
        List<String> lista = new ArrayList<String>();
        for (int i = 0; i < size; i++) {
            lista.add("item:" + i);
            progress.setProgressMap("metodoA", i);
        }
    }
}
