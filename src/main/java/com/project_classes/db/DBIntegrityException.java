package com.project_classes.db;

public class DBIntegrityException extends RuntimeException{
    

    public DBIntegrityException(String msg) {
        super(msg);
    }
}
