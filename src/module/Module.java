/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author witek
 */
public class Module {

    private final List<User> users;

    private final List<Operation> operations;

    private int currentOperationPointer;

    public Module() {
        this.users = new LinkedList();
        this.operations = new LinkedList();
        currentOperationPointer = -1;
    }

    public String preformOperation(String name, String[] args) {
        Operation toPerform;
        switch (name) {
            case "addUser":
                toPerform = new AddUser(args);
                break;
            case "delUser":
                toPerform = new DelUser(args);
                break;
            case "printAll":
                toPerform = new PrintAll(args);
                break;
            case "searchByName":
                toPerform = new SearchByName(args);
                break;
            case "undo":
                toPerform = new Undo(args);
                break;
            case "redo":
                toPerform = new Redo(args);
                break;
            case "operationReadingError":
                toPerform = new Error(new String[]{"No such operation"});
                break;
            default:
                toPerform = new Error(new String[]{"Error occured"});
                break;
        }
        String outCode = toPerform.execute();
        if (!toPerform.isSpecial()) {
            /*
            Nowa operacja jest dodawana za ostatnią wykonaną i niecofniętą operację 
            niespecjalną do historii operacji.
            
            Operacje cofnięte nie są usuwane, a pozostają na końcu listy.
            Można do nich dojść wykonaniami redo. 
             */
            currentOperationPointer++;
            operations.add(currentOperationPointer, toPerform);
        }
        return outCode;
    }

    /*
    Klasa operacji. Każda operacja jest jej podklasą
     */
    private abstract class Operation {

        /*
        Argumenty przekazane do operacji
         */
        protected final String[] args;

        /*
        Przypisanie argumentów operacji
         */
        public Operation(String[] args) {
            this.args = Arrays.copyOf(args, args.length);
        }

        /*
        Metoda wykonania operacji
         */
        public abstract String execute();

        /*
        Metoda cofnięcia operacji
         */
        public abstract String revert();

        /*
        Informuje, czy operacja jest operacją specjalną - operacje specjalne nie 
        są zapisywane w historii
         */
        public boolean isSpecial() {
            return false;
        }
    }

    /*
    Klasa operacji specjalnych - nie będą dodawane do listy operacji
     */
    private abstract class SpecialOperation extends Operation {

        public SpecialOperation(String[] args) {
            super(args);
        }

        @Override
        public String revert() {
            return "Cannot revert special operations";
        }

        @Override
        public boolean isSpecial() {
            return true;
        }
    }

    /*
    Klasa operacji dodawania użytkownika
     */
    private class AddUser extends Operation {

        public AddUser(String[] args) {
            super(args);
        }

        @Override
        public String execute() {
            users.add(new User(args));
            return "OK";
        }

        @Override
        public String revert() {
            /*
            Usuwamy z bazy urzytkownika o danych paramatrach
             */
            users.remove(users.remove(users.lastIndexOf(new User(args))));
            return "OK";
        }

        public int getArgsNumber() {
            return User.getNumberOfArgs();
        }
    }

    /*
    Klasa operacji usuwania użytkownika - usuwa najnowszego użytkownia o
    podanych parametrach
     */
    private class DelUser extends Operation {

        private int removeIndex;

        public DelUser(String[] args) {
            super(args);
            removeIndex = -1;
        }

        @Override
        public String execute() {
            /*
            Sprawdzenie istnienia użytkownika i usunięcie go jeśli istnieje
             */
            removeIndex = users.lastIndexOf(new User(args));
            if (removeIndex == -1) {
                return "No such user";
            } else {
                users.remove(removeIndex);
                return "OK";
            }
        }

        @Override
        public String revert() {
            /*
            Sprawdzenie, czy użytkownik został kiedyś realnie usunięty
             */
            if (removeIndex == -1) {
                return "User never existed";
            } else {
                users.add(Math.min(users.size(), removeIndex), new User(args));
                return "OK";
            }
        }
    }

    /*
    Klasa operacji wypisywania wszystkiego
     */
    private class PrintAll extends SpecialOperation {

        public PrintAll(String[] args) {
            super(args);
        }

        @Override
        public String execute() {
            String out = "Users in list:\n";
            out = users.stream().map((user) -> user + "\n").reduce(out, String::concat);
            return out;
        }

        @Override
        public String revert() {
            return "What has been seen cannot be unseen.";
        }

    }

    /*
    Klasa szukania numerów telefonów użytkowników o danym imieniu i nazwisku
     */
    private class SearchByName extends Operation {

        public SearchByName(String[] args) {
            super(args);
        }

        private boolean nameAndSurnameFits(String name, String surname, User user) {
            return name.equals(user.getName()) && surname.equals(user.getSurname());
        }

        @Override
        public String execute() {
            String name = args[0];
            String surname = args[1];
            String introdution = "Phone numbers of user " + name + " " + surname + " :\n";
            /*
            Przejście po liście i dopisanie numerów telefonów użytkowników z danym imieniem i nazwiskiem do listy
             */
            String out
                    = users.stream().filter((user)
                            -> nameAndSurnameFits(name, surname, user)).
                            map((user)
                                    -> user.getTelephone() + "\n").
                            reduce("", String::concat);
            return introdution + out;
        }

        @Override
        public String revert() {
            return "What has been seen cannot be unseen.";
        }

    }

    /*
    Klasa operacji cofania operacji
     */
    private class Undo extends SpecialOperation {

        public Undo(String[] args) {
            super(args);
        }

        @Override
        public String execute() {
            String outCode;
            if (currentOperationPointer < 0) {
                outCode = "There is not any previous operation";
            } else {
                outCode = operations.get(currentOperationPointer).revert();
                currentOperationPointer--;
            }
            return outCode;
        }
    }

    /*
    Klasa operacji przywracania cofniętej operacji
     */
    private class Redo extends SpecialOperation {

        public Redo(String[] args) {
            super(args);
        }

        @Override
        public String execute() {
            String outCode;
            if (currentOperationPointer + 1 >= operations.size()) {
                outCode = "There is not any next operation";
            } else {
                currentOperationPointer++;
                outCode = operations.get(currentOperationPointer).execute();
            }
            return outCode;
        }
    }

    /*
    Klasa operacji błędów
     */
    private class Error extends SpecialOperation {

        public Error(String[] args) {
            super(args);
        }

        @Override
        public String execute() {
            return "Error: " + args[0];
        }
    }
}
