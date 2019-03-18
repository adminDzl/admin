package com.wolves.dto.user;

import java.io.Serializable;

/**
 *
 * @author gf
 * @date 2019/3/5
 */
public class LoginDTO implements Serializable{

    private String telephone;

    private String password;

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "telephone='" + telephone + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
