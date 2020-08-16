package io.izzel.netzach.data.entity;

import com.google.common.io.ByteStreams;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ItemRecord {

    public static final String CREATE_TABLE =
        "CREATE TABLE IF NOT EXISTS %s\n" +
            "(\n" +
            "    id          INT PRIMARY KEY AUTO_INCREMENT,\n" +
            "    shop        VARCHAR(32) NOT NULL,\n" +
            "    item        MEDIUMBLOB  NOT NULL,\n" +
            "    stock       INT         NOT NULL,\n" +
            "    owner       VARCHAR(36) NOT NULL,\n" +
            "    create_time BIGINT(19)  NOT NULL,\n" +
            "    stock_time  BIGINT(19)  NOT NULL,\n" +
            "    expire      BIGINT(19)  NOT NULL,\n" +
            "    info        TEXT        NOT NULL,\n" +
            "    INDEX (shop)\n" +
            ");";

    private int id;
    private String shop;
    private byte[] item;
    private int stock;
    private UUID owner;
    private long createTime;
    private long stockTime;
    private long expire;
    private String info;

    public int id() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String shop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public byte[] item() {
        return item;
    }

    public void setItem(byte[] item) {
        this.item = item;
    }

    public int stock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public UUID owner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public long createTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long stockTime() {
        return stockTime;
    }

    public void setStockTime(long stockTime) {
        this.stockTime = stockTime;
    }

    public long expire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public String info() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void read(ResultSet resultSet, int i) throws SQLException, IOException {
        this.id = resultSet.getInt(i++);
        this.shop = resultSet.getString(i++);
        this.item = ByteStreams.toByteArray(resultSet.getBlob(i++).getBinaryStream());
        this.stock = resultSet.getInt(i++);
        this.owner = UUID.fromString(resultSet.getString(i++));
        this.createTime = resultSet.getLong(i++);
        this.stockTime = resultSet.getLong(i++);
        this.expire = resultSet.getLong(i++);
        this.info = resultSet.getString(i);
    }

    public void write(PreparedStatement statement, int i) throws SQLException {
        statement.setInt(i++, this.id);
        statement.setString(i++, this.shop);
        statement.setBlob(i++, new ByteArrayInputStream(this.item));
        statement.setInt(i++, this.stock);
        statement.setString(i++, this.owner.toString());
        statement.setLong(i++, this.createTime);
        statement.setLong(i++, this.stockTime);
        statement.setLong(i++, this.expire);
        statement.setString(i, this.info);
    }
}
