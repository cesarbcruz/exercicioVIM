/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cesar;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author cesar
 */
public class ManipuarArquivo {

    public static String lePropriedade(String dir, String arq, String chave) throws FileNotFoundException, IOException, Exception {


            String prop = "";

            // Se usuario nao informar diretorio, o sistema usara o corrente!
            if (dir.equals("")) {
                dir = System.getProperty("user.dir");
            }

            Properties applicationProps = new Properties();
            String path = dir + "/" + arq;

            FileInputStream in = new FileInputStream(path);
            applicationProps.load(in);
            in.close();

            prop = applicationProps.getProperty(chave);

            if (prop == null) {
                throw new Exception("Chave " + chave + " no arquivo " + path + " não encontrada !");
            }

            return prop;

        

    }
}
