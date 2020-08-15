package io.izzel.netzach.config;

import java.util.HashMap;
import java.util.Map;

public class DatabaseSpec {

    private String tablePrefix = "netzach_";
    private String jdbcUrl;
    private Map<String, String> properties = new HashMap<>();
    private Map<String, String> dataSourceProperties = new HashMap<>();

    public String tablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public String jdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public Map<String, String> properties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public Map<String, String> dataSourceProperties() {
        return dataSourceProperties;
    }

    public void setDataSourceProperties(Map<String, String> dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }
}
