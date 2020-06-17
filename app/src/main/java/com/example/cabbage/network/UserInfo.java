package com.example.cabbage.network;

import java.util.List;

/**
 * Author: xiemugan
 * Date: 2020/6/6
 * Description:
 **/
public class UserInfo {
    private int code;
    private String message;
    private Data data;
    private class Data {
        private User user;
        private Token token;
        private class User {
            private int id;
            private int userId;
            private String username;
            private String password;
            private String nickname;
            private String headImgUrl;
            private String phone;
            private String telephone;
            private String email;
            private String birthday;
            private int sex;
            private int status;
        }
        private class Token {
            private String token;
            private long loginTime;
        }
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getId() {
        return data.user.id;
    }

    public int getUserId() {
        return data.user.userId;
    }

    public String getUsername() {
        return data.user.username;
    }

    public String getPassword() {
        return data.user.password;
    }

    public String getNickname() {
        return data.user.nickname;
    }

    public String getHeadImgUrl() {
        return data.user.headImgUrl;
    }

    public String getPhone() {
        return data.user.phone;
    }

    public String getTelephone() {
        return data.user.telephone;
    }

    public String getEmail() {
        return data.user.email;
    }

    public String getBirthday() {
        return data.user.birthday;
    }

    public int getSex() {
        return data.user.sex;
    }

    public int getStatus() {
        return data.user.status;
    }

    public String getToken() {
        return data.token.token;
    }

    public long getLoginTime() {
        return data.token.loginTime;
    }
}
