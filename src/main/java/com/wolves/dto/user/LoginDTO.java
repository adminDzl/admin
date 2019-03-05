package com.wolves.dto.user;

/**
 * Created by gf on 2019/3/5.
 */
public class LoginDTO {

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
