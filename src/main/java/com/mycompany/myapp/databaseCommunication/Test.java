package com.mycompany.myapp.databaseCommunication;

import com.google.gson.Gson;
import com.mycompany.myapp.web.rest.AnalytesOfInterestResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

@RestController
public class Test {
    private final Logger log = LoggerFactory.getLogger(AnalytesOfInterestResource.class);
    private String databaseAddress = "localhost";

    @GetMapping("/getOperators")
    public String getOperators() {
        log.debug("REST request to get all operators");
        ArrayList<String> lines = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                ResultSet set = statement.executeQuery("SELECT * FROM operaatorid;");
                while (set.next()) {
                    lines.add(set.getString(2)+ " " + set.getString(3));
                }
                return new Gson().toJson(lines);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            return "Database is not available right now!";
        }
        return "Works";
    }

    public boolean isDatabaseUp() {
        boolean ret = false;
        try {
            Socket s = new Socket(databaseAddress,5432);
            ret = true;
            s.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return ret;
    }
}
