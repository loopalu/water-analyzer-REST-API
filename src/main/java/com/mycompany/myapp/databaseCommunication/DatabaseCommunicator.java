package com.mycompany.myapp.databaseCommunication;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mycompany.myapp.web.rest.AnalytesOfInterestResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@RestController
public class DatabaseCommunicator {
    private final Logger log = LoggerFactory.getLogger(AnalytesOfInterestResource.class);
    private String databaseAddress = "localhost";
    private ArrayList<Integer> analyteIdQueue = new ArrayList<>();
    private ArrayList<Integer> analyteValueQueue = new ArrayList<>();
    private ArrayList<Integer> bgeIdQueue = new ArrayList<>();
    private ArrayList<Integer> bgeValueQueue = new ArrayList<>();
    private int matrix_id;
    private String bgeUnit;
    private String analyteUnit;
    private int method_id;

    @GetMapping("/getAnalytes")
    public String getAnalytes() {
        return getter("analytes");
    }

    @GetMapping("/getBges")
    public String getBges() {
        return getter("bges");
    }

    @GetMapping("/getMatrixes")
    public String getMatrixes() {
        return getter("matrixes");
    }

    @GetMapping(path = "/postTest")
    public void postTest(@RequestHeader("Data") String string) {
        byte[] decodedBytes = Base64.getDecoder().decode(string);
        String decodedString = new String(decodedBytes);
        System.out.println(decodedString);
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson( decodedString, JsonObject.class);
        putAnalytes(jsonObject);
        putMatrixes(jsonObject);
        putBges(jsonObject);
        putMethods(jsonObject);
//        putAnalyteMeasurements(jsonObject);
//        putBgeMeasurements(jsonObject);
//        putTests(jsonObject);
//        putMeasurements(jsonObject);
    }

    private ArrayList<String> getMethodNames() {
        log.debug("REST request to get all methods");
        ArrayList<String> lines = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                ResultSet set = statement.executeQuery("SELECT method_name FROM methods;");
                while (set.next()) {
                    String methodName = set.getString(1);
                    lines.add(methodName);
                }
                return lines;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            return lines;
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private void putMethods(JsonObject jsonObject) {
        ArrayList<String> currentMethods = getMethodNames();
        String methodName = String.valueOf(jsonObject.get("nameOfMethod")).replace('"', ' ').trim();
        if (currentMethods.contains(methodName)) {
            method_id = getIdOfElement("methods", methodName);
            System.out.println(method_id);
        } else {
            int innerCapillary = Integer.parseInt(String.valueOf(jsonObject.get("capillary")).replace('"', ' ').trim().split(" ")[0].split("/")[0]);
            int outerCapillary = Integer.parseInt(String.valueOf(jsonObject.get("capillary")).replace('"', ' ').trim().split(" ")[0].split("/")[1]);
            int totalCapillary = Integer.parseInt(String.valueOf(jsonObject.get("capillaryTotalLength")).replace('"', ' ').trim().split(" ")[0]);
            int effectiveCapillary = Integer.parseInt(String.valueOf(jsonObject.get("capillaryEffectiveLength")).replace('"', ' ').trim().split(" ")[0]);
            String injectionMethod = String.valueOf(jsonObject.get("injectionMethod")).replace('"', ' ').trim();
            int injectionAmount = Integer.parseInt(String.valueOf(jsonObject.get("injectionChoiceValue")).replace('"', ' ').trim());
            String injectionUnit = String.valueOf(jsonObject.get("injectionChoiceUnit")).replace('"', ' ').trim();
            int injectionDuration = Integer.parseInt(String.valueOf(jsonObject.get("injectionTime")).replace('"', ' ').trim().split(" ")[0]);
            String frequencyUnit = String.valueOf(jsonObject.get("frequency")).replace('"', ' ').trim().split(" ")[1];
            int frequency = Integer.parseInt(String.valueOf(jsonObject.get("frequency")).replace('"', ' ').trim().split(" ")[0]);
            if (frequencyUnit.equals("MHz")) {
                frequency *= 1000;
            }
            int hvValue = Integer.parseInt(String.valueOf(jsonObject.get("hvValue")).replace('"', ' ').trim().split(" ")[0]);
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            if (connection != null) {
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO methods (method_name, matrix_id, capillary_inner, capillary_outer, " +
                        "capillary_total, capillary_effective, injection_method, injection_amount, injection_unit, " +
                        "injection_duration, frequency, hv_value) VALUES (?,?,?,?,?,?,?,?,?,?,?,?);");
                    preparedStatement.setString(1, methodName);
                    preparedStatement.setInt(2, matrix_id);
                    preparedStatement.setInt(3, innerCapillary);
                    preparedStatement.setInt(4, outerCapillary);
                    preparedStatement.setInt(5, totalCapillary);
                    preparedStatement.setInt(6, effectiveCapillary);
                    preparedStatement.setString(7, injectionMethod);
                    preparedStatement.setInt(8, injectionAmount);
                    preparedStatement.setString(9, injectionUnit);
                    preparedStatement.setInt(10, injectionDuration);
                    preparedStatement.setInt(11, frequency);
                    preparedStatement.setInt(12, hvValue);
                    int i = preparedStatement.executeUpdate();
                    Statement statement = connection.createStatement();
                    ResultSet set = statement.executeQuery("select method_id from methods where method_name = '" + methodName + "';");
                    set.next();
                    method_id = set.getInt(1);
                    System.out.println(method_id);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void putBges(JsonObject jsonObject) {
        String currentBgesString = getBges().replace('"', ' ').replace('[', ' ').replace(']', ' ');
        List<String> currentBges = Arrays.asList(currentBgesString.trim().split(" , "));
        JsonElement bgeJson = jsonObject.get("bge");
        bgeUnit = String.valueOf(jsonObject.get("bgeUnit")).replace('"', ' ').trim();
        JsonArray jsonArray = bgeJson.getAsJsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject subObject = jsonArray.get(i).getAsJsonObject();
            String bgeName = String.valueOf(subObject.get("analyte").getAsJsonObject().get("value")).replace('"', ' ').trim();
            Integer bgeValue = Integer.valueOf(String.valueOf(subObject.get("concentration").getAsJsonObject().get("value")).replace('"', ' ').trim());
            if (currentBges.contains(bgeName)) {
                Integer id = getIdOfElement("bges", bgeName);
                bgeIdQueue.add(id);
            } else {
                Integer id = putElement("bges", bgeName);
                bgeIdQueue.add(id);
            }
            bgeValueQueue.add(bgeValue);
        }
    }

    private void putMatrixes(JsonObject jsonObject) {
        String currentAnalytesString = getMatrixes().replace('"', ' ').replace('[', ' ').replace(']', ' ');
        List<String> currentMatrixes = Arrays.asList(currentAnalytesString.trim().split(" , "));
        JsonElement analyteJson = jsonObject.get("matrix");
        String matrix = String.valueOf(analyteJson).replace('"', ' ').trim();
        if (currentMatrixes.contains(matrix)) {
            matrix_id = getIdOfElement("matrixes", matrix);
        } else {
            matrix_id = putElement("matrixes", matrix);
        }
    }

    private void putAnalytes(JsonObject jsonObject) {
        String currentAnalytesString = getAnalytes().replace('"', ' ').replace('[', ' ').replace(']', ' ');
        List<String> currentAnalytes = Arrays.asList(currentAnalytesString.trim().split(" , "));
        JsonElement analyteJson = jsonObject.get("analytes");
        analyteUnit = String.valueOf(jsonObject.get("analyteUnit")).replace('"', ' ').trim();
        JsonArray jsonArray = analyteJson.getAsJsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject subObject = jsonArray.get(i).getAsJsonObject();
            String analyteName = String.valueOf(subObject.get("analyte").getAsJsonObject().get("value")).replace('"', ' ').trim();
            Integer analyteValue = Integer.valueOf(String.valueOf(subObject.get("concentration").getAsJsonObject().get("value")).replace('"', ' ').trim());
            if (currentAnalytes.contains(analyteName)) {
                Integer id = getIdOfElement("analytes", analyteName);
                analyteIdQueue.add(id);
            } else {
                Integer id = putElement("analytes", analyteName);
                analyteIdQueue.add(id);
            }
            analyteValueQueue.add(analyteValue);
        }
    }

    private int putElement(String tableName, String tableElement) {
        String subString = "";
        if (tableName.equals("matrixes")) {
            subString = tableName.substring(0, (tableName.length() - 2));
        } else {
            subString = tableName.substring(0, (tableName.length() - 1));
        }
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        Integer value = null;
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                statement.executeUpdate("INSERT INTO " + tableName + " (" + subString + "_name)" + "VALUES ('" + tableElement + "')");
                ResultSet set = statement.executeQuery("select " + subString + "_id from " + tableName+ " where " + subString + "_name = '" + tableElement + "';");
                set.next();
                value = set.getInt(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return value;
    }

    private Integer getIdOfElement(String tableName, String tableElement) {
        String subString = "";
        if (tableName.equals("matrixes")) {
            subString = tableName.substring(0, (tableName.length() - 2));
        } else {
            subString = tableName.substring(0, (tableName.length() - 1));
        }
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        Integer value = null;
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                ResultSet set = statement.executeQuery("select " + subString + "_id from " + tableName+ " where " + subString + "_name = '" + tableElement + "';");
                set.next();
                value = set.getInt(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return value;
    }

    @GetMapping("/getUsers")
    public String getUsers() {
        log.debug("REST request to get all users");
        ArrayList<User> lines = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                ResultSet set = statement.executeQuery("SELECT * FROM users;");
                while (set.next()) {
                    User user = new User();
                    user.setName(set.getString(2));
                    user.setUserClass(set.getInt(3));
                    lines.add(user);
                }
                return new Gson().toJson(lines);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            return "Database is not available right now!";
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Works";
    }

    private String getter(String string) {
        log.debug("REST request to get all " + string);
        ArrayList<String> lines = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                ResultSet set = statement.executeQuery("SELECT * FROM " + string + ";");
                while (set.next()) {
                    lines.add(set.getString(2));
                }
                return new Gson().toJson(lines);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            return "Database is not available right now!";
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
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
