package com.garud.retail.constant;


public enum Role {

    ADMIN("1","ADMIN"),
    STUDENT("2" ,"STUDENT");

    private final String id;
    private final String role;

    Role(String id, String role) {
        this.id=id;
        this.role=role;
    }

    public String getId() {
        return id;
    }

    public String getRole() {
        return role;
    }
}
