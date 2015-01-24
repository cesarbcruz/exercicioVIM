/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cesar;

import javax.ejb.Remote;

/**
 *
 * @author cesar
 */
public interface ProgressEJBInterface {

    public int getProgressMap(java.lang.String chave);

    public void setProgressMap(java.lang.String chave, int value);
    
}
