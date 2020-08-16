package io.izzel.netzach.data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.util.PropertyElf;
import io.izzel.netzach.Netzach;
import io.izzel.netzach.data.entity.ItemRecord;
import io.izzel.netzach.data.entity.LogRecord;
import io.izzel.taboolib.module.locale.TLocale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DataManager {

    private HikariDataSource dataSource;

    public void open() {
        String jdbcUrl = Netzach.config().database().jdbcUrl();
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setPoolName("Netzach");

        if (jdbcUrl.startsWith("jdbc:h2")) {
            hikariConfig.setDriverClassName("org.h2.Driver");
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
        this.setup();
    }

    private void setup() {
        try (Connection connection = connection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                String.format(ItemRecord.CREATE_TABLE, Netzach.config().database().tablePrefix() + "items"))) {
                statement.execute();
            }
            try (PreparedStatement statement = connection.prepareStatement(
                String.format(LogRecord.CREATE_TABLE, Netzach.config().database().tablePrefix() + "logs"))) {
                statement.execute();
            }
            TLocale.sendToConsole("load.database");
        } catch (Throwable t) {
            TLocale.sendToConsole("load.database-error", t);
        }
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
