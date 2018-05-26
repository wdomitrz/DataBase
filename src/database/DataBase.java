/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.InputStream;
import java.util.Scanner;
import module.Module;
import textGUI.TextGUI;

/**
 *
 * @author witek
 */
public class DataBase {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Module base = new Module();
        InputStream sourceOfInput = System.in;
        TextGUI gui = new TextGUI(base, new Scanner(sourceOfInput));
        gui.run();
    }

}
