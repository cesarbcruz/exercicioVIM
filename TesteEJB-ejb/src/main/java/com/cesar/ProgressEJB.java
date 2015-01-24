/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cesar;

import java.util.HashMap;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author cesar
 */
@Singleton
@Startup
@Remote(ProgressEJBInterface.class)
public class ProgressEJB implements ProgressEJBInterface{

    private HashMap<String, Integer> progressMap = new HashMap<String, Integer>();

    public int getProgressMap(String chave) {
        if (progressMap.containsKey(chave)) {
            return progressMap.get(chave);
        }
        return 0;
    }

    public void setProgressMap(String chave, int value) {
        this.progressMap.put(chave, value);
    }
}
