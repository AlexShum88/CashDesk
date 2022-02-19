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
    private Integer id;


    public User() {
    }

    public User(String login, String password, String role, Integer id) {
        this.login = login;
        setPassword(password);
        this.role = role;
        this.id = id;
    }

    public User(String login, String password, String role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String incriptPassword() {
        var sub1 = password.replace('[', 'a');
        var sub2 = sub1.replace(']', 'e');
        var sub3 = sub2.replace('{', 'o');
        var sub4 = sub3.replace('$', 's');
        return sub4;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        var sub1 = password.replace('a', '[');
        var sub2 = sub1.replace('e', ']');
        var sub3 = sub2.replace('o', '{');
        var sub4 = sub3.replace('s', '$');
        this.password = sub4;

    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
