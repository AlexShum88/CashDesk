package my.project.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class User implements Serializable {
    private String login;
    private String password;
    private String role;

    public User() {
    }

    public User(String login, String password, String role) {
        this.login = login;
        setPassword(password);
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
//
//        var enigma = password.toCharArray();
//        for (int i = 0; i< enigma.length;i++){
//            enigma[i] = (char) (enigma[i]/3);
//        }
//
//        return Arrays.toString(enigma);
        return this.password;

    }

    public void setPassword(String password) {
//        var enigma = password.toCharArray();
//        for (int i = 0; i< enigma.length;i++){
//            enigma[i] = (char) (enigma[i]*3);
//        }
//        System.out.println(enigma.toString());
//        this.password = Arrays.toString(enigma);
        this.password = password;

    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!login.equals(user.login)) return false;
        return role.equals(user.role);
    }

    @Override
    public int hashCode() {
        int result = login.hashCode();
        result = 31 * result + role.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
