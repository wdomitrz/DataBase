/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textGUI;

import controler.Controler;
import java.util.Scanner;
import module.Module;

/**
 *
 * @author witek
 */
public class TextGUI {

    private final Controler controler;
    private final Scanner scanner;

    private static final String MENU = ""
            + " _____________________________________ \n"
            + "| Available operations:               |\n"
            + "| 1 - Add User                        |\n"
            + "|     parameters:                     |\n"
            + "|   + User name                       |\n"
            + "|   + User surname                    |\n"
            + "|   + User phone number               |\n"
            + "| 2 - Delete User                     |\n"
            + "|     parameters:                     |\n"
            + "|   + User name                       |\n"
            + "|   + User surname                    |\n"
            + "|   + User phone number               |\n"
            + "| 3 - Print whole database            |\n"
            + "|     no parameters                   |\n"
            + "| 4 - Search User by name and surname |\n"
            + "|     parameters:                     |\n"
            + "|   + User name                       |\n"
            + "|   + User surname                    |\n"
            + "| 5 - Undo operation                  |\n"
            + "|     no parameters                   |\n"
            + "| 6 - Redo operation                  |\n"
            + "|     no parameters                   |\n"
            + "| 7 - Exit programme                  |\n"
            + "|_____________________________________|\n";

    public TextGUI(Module base, Scanner scanner) {
        this.controler = new Controler(base);
        this.scanner = scanner;
    }

    public void run() {
        while (true) {
            System.out.println(MENU);
            int operation = scanner.nextInt();
            String operationName;
            String out;
            switch (operation) {
                case 1:
                    operationName = "addUser";
                    out = controler.operation(operationName, readArgs(operationName));
                    break;
                case 2:
                    operationName = "delUser";
                    out = controler.operation(operationName, readArgs(operationName));
                    break;
                case 3:
                    operationName = "printAll";
                    out = controler.operation(operationName, readArgs(operationName));
                    break;
                case 4:
                    operationName = "searchByName";
                    out = controler.operation(operationName, readArgs(operationName));
                    break;
                case 5:
                    operationName = "undo";
                    out = controler.operation(operationName, readArgs(operationName));
                    break;
                case 6:
                    operationName = "redo";
                    out = controler.operation(operationName, readArgs(operationName));
                    break;
                case 7:
                    return;
                default:
                    operationName = "operationReadingError";
                    out = controler.operation(operationName, readArgs(operationName));
                    break;
            }
            System.out.println(out);
        }
    }

    private String[] readArgs(String operationName) {
        String[] args = new String[controler.argsNumber(operationName)];
        for (int i = 0; i < args.length; i++) {
            args[i] = scanner.next();
        }
        return args;
    }
}
