package io.izzel.netzach.data.entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class LogRecord {

    public static final String CREATE_TABLE =
        "CREATE TABLE IF NOT EXISTS %s\n" +
            "(\n" +
            "    action INT        NOT NULL,\n" +
            "    id     INT        NOT NULL,\n" +
            "    time   BIGINT(19) NOT NULL,\n" +
            "    source VARCHAR(36),\n" +
            "    info   TEXT\n" +
            ");";

    private Action action;
    private int id;
    private long time;
    private UUID source;
    private String info;

    public Action action() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public int id() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long time() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public UUID source() {
        return source;
    }

    public void setSource(UUID source) {
        this.source = source;
    }

    public String info() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void read(ResultSet resultSet, int i) throws SQLException {
        this.action = Action.values()[resultSet.getInt(i++)];
        this.id = resultSet.getInt(i++);
        this.time = resultSet.getLong(i++);
        String uuid = resultSet.getString(i++);
        if (uuid != null) {
            this.source = UUID.fromString(uuid);
        }
        this.info = resultSet.getString(i);
    }

    public void write(PreparedStatement statement, int i) throws SQLException {
        statement.setInt(i++, this.action.ordinal());
        statement.setInt(i++, this.id);
        statement.setLong(i++, this.time);
        if (this.source != null) {
            statement.setString(i, this.source.toString());
        }
        i++;
        if (this.info != null) {
            statement.setString(i, this.info);
        }
    }

    public enum Action {
        CREATE,
        DELETE,
        BUY,
        SELL,
        SETTING
    }
}
