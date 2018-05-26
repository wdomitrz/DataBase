/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import java.util.Map;
import java.util.HashMap;
import module.Module;

/**
 *
 * @author witek
 */
public class Controler {

    private final Module base;
    private static final Map<String, Integer> OPERATION_ARGS = new HashMap<String, Integer>() {
        {
            put("addUser", 3);
            put("delUser", 3);
            put("printAll", 0);
            put("searchByName", 2);
            put("undo", 0);
            put("redo", 0);
            put("operationReadingError", 0);
        }
    };

    public Controler(Module base) {
        this.base = base;
    }

    public String operation(String operation, String[] args) {
        return base.preformOperation(operation, args);
    }

    /*
    Metoda informująca o oczekiwanej liczbie argumentów dla danej operacji
     */
    public int argsNumber(String operatioName) {
        return OPERATION_ARGS.get(operatioName);
    }
}
