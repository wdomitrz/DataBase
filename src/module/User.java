/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module;

import java.util.Objects;

/**
 *
 * @author witek
 */
public class User {

    /*
    Liczba cech użytkownika
     */
    private static final int NUMBER_OF_ARGS = 3;

    public static int getNumberOfArgs() {
        return NUMBER_OF_ARGS;
    }

    private final String name;
    private final String surname;
    private final String telephone;

    public User(String[] args) {
        this.name = args[0];
        this.surname = args[1];
        this.telephone = args[2];
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getTelephone() {
        return telephone;
    }

    @Override
    public String toString() {
        return "User{" + "name=" + name + ", surname=" + surname + ", telephone=" + telephone + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.surname, other.surname)) {
            return false;
        }
        if (!Objects.equals(this.telephone, other.telephone)) {
            return false;
        }
        return true;
    }

}
