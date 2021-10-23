package com.example.sb2samples.entity;

import lombok.Data;

import java.io.Serializable;
/**
* @author lironghong
* @date  12:33 2020/7/28
* email itlironghong@foxmail.com
* description 
*/
@Data
public class User implements Serializable {

    private static final long serialVersionUID = -281034684366346160L;
    private Long id;
    private String username;
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

}
