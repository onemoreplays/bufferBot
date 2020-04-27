package me.inao.discordbot.util;

import lombok.Getter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


@Getter
public class SQLite {

    public Connection openConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:db.sqlite");
        } catch (Exception e) {
            new ExceptionCatcher(e);
        }
        return null;
    }

    public void execute(PreparedStatement stmt) {
        try {
            stmt.execute();
            stmt.getConnection().close();
            stmt.close();
        } catch (Exception e) {
            new ExceptionCatcher(e);
        }
    }

    public ResultSet getResults(PreparedStatement stmt) {
        try {
            return stmt.executeQuery();
        } catch (Exception e) {
            new ExceptionCatcher(e);
        }
        return null;
    }
}
