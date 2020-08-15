package io.izzel.netzach.data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.util.PropertyElf;
import io.izzel.netzach.Netzach;
import io.izzel.taboolib.module.locale.TLocale;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataManager {

    private HikariDataSource dataSource;

    public void open() {
        String jdbcUrl = Netzach.config().database().jdbcUrl();
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setPoolName("Netzach");

        if (jdbcUrl.startsWith("jdbc:sqlite")) {
            hikariConfig.setDriverClassName("org.sqlite.JDBC");
        } else if (jdbcUrl.startsWith("jdbc:mysql")) {
            hikariConfig.setDriverClassName("com.mysql.jdbc.Driver");
        }

        Properties properties = new Properties();
        properties.putAll(Netzach.config().database().properties());
        PropertyElf.setTargetFromProperties(hikariConfig, properties);

        Properties dataSourceProp = new Properties();
        dataSourceProp.putAll(Netzach.config().database().dataSourceProperties());
        hikariConfig.setDataSourceProperties(dataSourceProp);
        this.dataSource = new HikariDataSource(hikariConfig);
        TLocale.sendToConsole("load.database");
    }

    public void close() {
        if (this.dataSource != null) {
            this.dataSource.close();
            this.dataSource = null;
        }
    }

    public Connection connection() throws SQLException {
        return this.dataSource.getConnection();
    }
}
