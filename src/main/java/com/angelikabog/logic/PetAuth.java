package com.angelikabog.logic;

public class PetAuth {
    private String name;
    private String type;
    private int age;
    private String user;
    private String password;

    public PetAuth(String name, String type, int age) {
        this.name = name;
        this.type = type;
        this.age = age;
    }

    public PetAuth(String name, String type, int age, String user, String password) {
        this.name = name;
        this.type = type;
        this.age = age;
        this.user = user;
        this.password = password;
    }

    public PetAuth() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
