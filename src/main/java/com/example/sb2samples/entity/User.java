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
    private Long ids2;
    private String usernames;
    private String password;

    public Long getId() {
        return ids2;
    }

    public void setId(Long id) {
        this.ids2 = id;
    }

    public String getUsername() {
        return usernames;
    }

    public void setUsername(String username) {
        this.usernames = username;
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
        this.usernames = username;
        this.password = password;
    }

    public User(Long id, String username, String password) {
        this.ids2 = id;
        this.usernames = username;
        this.password = password;
    }

}
