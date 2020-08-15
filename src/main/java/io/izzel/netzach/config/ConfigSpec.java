package io.izzel.netzach.config;

public class ConfigSpec {

    private DatabaseSpec database = new DatabaseSpec();

    public DatabaseSpec database() {
        return database;
    }

    public void setDatabase(DatabaseSpec database) {
        this.database = database;
    }
}
