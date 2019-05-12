package com.mycompany.myapp.databaseCommunication;

import com.google.gson.*;
import com.mycompany.myapp.web.rest.AnalytesOfInterestResource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

/**
 * This class receives requests from desktop application, processes them, sends/gets data from database and returns
 * data to the desktop application.
 */
@RestController
public class DatabaseCommunicator {
    private final Logger log = LoggerFactory.getLogger(AnalytesOfInterestResource.class);
    private ArrayList<Integer> analyteIdQueue = new ArrayList<>();
    private ArrayList<Integer> analyteValueQueue = new ArrayList<>();
    private ArrayList<Integer> bgeIdQueue = new ArrayList<>();
    private ArrayList<Integer> bgeValueQueue = new ArrayList<>();
    private int matrix_id;
    private String bgeUnit;
    private String analyteUnit;
    private int method_id;
    private String nameOfTest;
    private int user_id;
    private int test_id;

    /**
     * @return All analytes to the desktop application.
     */
    @GetMapping("/getAnalytes")
    public String getAnalytes() {
        return getter("analytes");
    }

    /**
     * @return All bge-s to the desktop application.
     */
    @GetMapping("/getBges")
    public String getBges() {
        return getter("bges");
    }

    /**
     * @return All matrixes to the desktop application.
     */
    @GetMapping("/getMatrixes")
    public String getMatrixes() {
        return getter("matrixes");
    }

    /**
     * Method receives all the data from desktop application and stores it in database.
     * @param string Received data from desktop application.
     */
    @GetMapping(path = "/postTest")
    public void postTest(@RequestHeader("Data") String string) {
        byte[] decodedBytes = Base64.getDecoder().decode(string);
        String decodedString = new String(decodedBytes);
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson( decodedString, JsonObject.class);
        putAnalytes(jsonObject);
        putMatrixes(jsonObject);
        putBges(jsonObject);
        putMethod(jsonObject);
        putAnalyteMeasurements(jsonObject);
        putBgeMeasurements(jsonObject);
        putTest(jsonObject);
        putMeasurements(jsonObject);
    }

    /**
     * @return All methods to the desktop application.
     */
    @GetMapping(path = "/getMethods")
    public String getMethods() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        ArrayList<LabTest> methods = new ArrayList<>();
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                ResultSet methodSet = statement.executeQuery("SELECT * FROM methods;");
                while (methodSet.next()) {
                    LabTest labTest = new LabTest();
                    labTest.setNameOfTest(""); // Not important for method.
                    labTest.setNameOfUser(""); // Not important for method.
                    labTest.setUserClass(""); // Not important for method.
                    labTest.setNameOfMethod(methodSet.getString(2));
                    int matrix_id = methodSet.getInt(3);
                    Statement matrixStatement = connection.createStatement();
                    ResultSet matrixSet = matrixStatement.executeQuery("SELECT matrix_name FROM matrixes WHERE matrix_id = " + matrix_id + ";");
                    matrixSet.next();
                    labTest.setMatrix(matrixSet.getString(1));
                    int inner = methodSet.getInt(4);
                    int outer = methodSet.getInt(5);
                    labTest.setCapillary(inner + "/" + outer + " μm");
                    int total = methodSet.getInt(6);
                    labTest.setCapillaryTotalLength(total + " cm");
                    int effective = methodSet.getInt(7);
                    labTest.setCapillaryEffectiveLength(effective + " cm");
                    int frequency = methodSet.getInt(12);
                    String frequencyUnit = " kHz";
                    if (frequency > 900) {
                        frequency = frequency/1000;
                        frequencyUnit = " MHz";
                    }
                    labTest.setFrequency(frequency+frequencyUnit);
                    String injectionMethod = methodSet.getString(8);
                    labTest.setInjectionMethod(injectionMethod);
                    if (injectionMethod.equals("Pressure")) {
                        labTest.setInjectionChoice("Difference");
                    } else if (injectionMethod.equals("Vacuum")) {
                        labTest.setInjectionChoice("Difference");
                    } else {
                        labTest.setInjectionChoice("Voltage");
                    }
                    labTest.setInjectionChoiceValue(String.valueOf(methodSet.getInt(9)));
                    labTest.setInjectionChoiceUnit(methodSet.getString(10));
                    labTest.setInjectionTime(methodSet.getInt(11) + " s");
                    labTest.setCurrent("-15 µA"); // Actually this is not important.
                    labTest.setHvValue(methodSet.getInt(13) + " %");
                    int method_id = methodSet.getInt(1);

                    Statement analyteMeasurementStatement = connection.createStatement();
                    ResultSet analyteMeasurementsSet = analyteMeasurementStatement.executeQuery("SELECT * FROM analyte_measurements WHERE method_id = " + method_id + ";");
                    ObservableList<Analyte> analytes = FXCollections.observableArrayList();
                    String analyteUnit = "";
                    while (analyteMeasurementsSet.next()) {
                        int analyte_id = analyteMeasurementsSet.getInt(4);
                        Statement analyteStatement = connection.createStatement();
                        ResultSet analyteSet = analyteStatement.executeQuery("SELECT analyte_name FROM analytes WHERE analyte_id = "+ analyte_id + ";");
                        analyteSet.next();
                        String analyteName = analyteSet.getString(1);
                        int analyte_amount = analyteMeasurementsSet.getInt(5);
                        analyteUnit = analyteMeasurementsSet.getString(6);
                        Analyte analyte = new Analyte(analyteName, String.valueOf(analyte_amount));
                        analytes.add(analyte);
                    }
                    labTest.setAnalytes(analytes);
                    labTest.setAnalyteUnit(analyteUnit);

                    Statement bgeMeasurementStatement = connection.createStatement();
                    ResultSet bgeMeasurementsSet = bgeMeasurementStatement.executeQuery("SELECT * FROM bge_measurements WHERE method_id = " + method_id + ";");
                    ObservableList<Analyte> bges = FXCollections.observableArrayList();
                    String bgeUnit = "";
                    while (bgeMeasurementsSet.next()) {
                        int bge_id = bgeMeasurementsSet.getInt(4);
                        Statement bgeStatement = connection.createStatement();
                        ResultSet bgeSet = bgeStatement.executeQuery("SELECT bge_name FROM bges WHERE bge_id = "+ bge_id + ";");
                        bgeSet.next();
                        String bgeName = bgeSet.getString(1);
                        int bge_amount = bgeMeasurementsSet.getInt(5);
                        bgeUnit = bgeMeasurementsSet.getString(6);
                        Analyte bge = new Analyte(bgeName, String.valueOf(bge_amount));
                        bges.add(bge);
                    }
                    labTest.setBge(bges);
                    labTest.setBgeUnit(bgeUnit);

                    labTest.setDescription(methodSet.getString(14));
                    labTest.setTestTime("00:00:23:231"); // Not important for method.
                    labTest.setTestData(new ArrayList()); // Not important for method.
                    methods.add(labTest);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(methods);
    }

    /**
     * Adds measurements to the database.
     * @param jsonObject Data in the form of JsonObject.
     */
    private void putMeasurements(JsonObject jsonObject) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        PreparedStatement preparedStatement = null;
        JsonArray testData = jsonObject.getAsJsonArray("testData");
        try {
            for (int i = 0; i < testData.size(); i++) {
                preparedStatement = connection.prepareStatement("INSERT INTO measurements (test_id, measurement) VALUES (?,?)");
                preparedStatement.setInt(1,test_id);
                preparedStatement.setInt(2,Integer.parseInt(String.valueOf(testData.get(i))));
                int someInt = preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds all test data to the database.
     * @param jsonObject Data in the form of JsonObject.
     */
    private void putTest(JsonObject jsonObject) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        Statement statement = null;
        String user_name = String.valueOf(jsonObject.get("nameOfUser")).replace('"', ' ').trim();
        String testDuration = String.valueOf(jsonObject.get("testTime")).replace('"', ' ').trim();
        int hours = Integer.parseInt(testDuration.split(":")[0]);
        int minutes = Integer.parseInt(testDuration.split(":")[1]);
        int secunds = Integer.parseInt(testDuration.split(":")[2]);
        int duration = hours * 3600 + minutes * 60 + secunds;
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery("select user_id from users where user_name = '" + user_name + "';");
            if (set.next()) {
                user_id = set.getInt(1);
            }
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO tests (user_id, method_id, " +
                "test_time, test_duration) VALUES (?,?,?,?);");
            preparedStatement.setInt(1,user_id);
            preparedStatement.setInt(2,method_id);
            preparedStatement.setString(3,nameOfTest);
            preparedStatement.setInt(4,duration);
            int someInt = preparedStatement.executeUpdate();
            ResultSet idSet = statement.executeQuery("select test_id from tests where test_time = '" + nameOfTest + "';");
            idSet.next();
            test_id = idSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds bge-s and their concentrations to the database.
     * @param jsonObject Data in the form of JsonObject.
     */
    private void putBgeMeasurements(JsonObject jsonObject) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        nameOfTest = String.valueOf(jsonObject.get("nameOfTest")).replace('"', ' ').trim();
        try {
            for (int i = 0; i < bgeIdQueue.size(); i++) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO bge_measurements (test_time, method_id, bge_id, bge_amount, bge_unit) VALUES (?,?,?,?,?);");
                preparedStatement.setString(1, nameOfTest);
                preparedStatement.setInt(2, method_id);
                preparedStatement.setInt(3, bgeIdQueue.get(i));
                preparedStatement.setInt(4, bgeValueQueue.get(i));
                preparedStatement.setString(5, bgeUnit);
                int someInt = preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds analytes and their concentrations to the database.
     * @param jsonObject Data in the form of JsonObject.
     */
    private void putAnalyteMeasurements(JsonObject jsonObject) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        nameOfTest = String.valueOf(jsonObject.get("nameOfTest")).replace('"', ' ').trim();
        try {
            for (int i = 0; i < analyteIdQueue.size(); i++) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO analyte_measurements (test_time, method_id, analyte_id, analyte_amount, analyte_unit) VALUES (?,?,?,?,?);");
                preparedStatement.setString(1, nameOfTest);
                preparedStatement.setInt(2, method_id);
                preparedStatement.setInt(3, analyteIdQueue.get(i));
                preparedStatement.setInt(4, analyteValueQueue.get(i));
                preparedStatement.setString(5, analyteUnit);
                int someInt = preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return The names of methods.
     */
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

    /**
     * Adds method to the database.
     * @param jsonObject Data in the form of JsonObject.
     */
    private void putMethod(JsonObject jsonObject) {
        ArrayList<String> currentMethods = getMethodNames();
        String methodName = String.valueOf(jsonObject.get("nameOfMethod")).replace('"', ' ').trim();
        if (currentMethods.contains(methodName)) {
            method_id = getIdOfElement("methods", methodName);
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
            String testDescription = String.valueOf(jsonObject.get("description")).replace('"', ' ').trim();
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            if (connection != null) {
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO methods (method_name, matrix_id, capillary_inner, capillary_outer, " +
                        "capillary_total, capillary_effective, injection_method, injection_amount, injection_unit, " +
                        "injection_duration, frequency, hv_value, method_description) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);");
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
                    preparedStatement.setString(13,testDescription);
                    int i = preparedStatement.executeUpdate();
                    Statement statement = connection.createStatement();
                    ResultSet set = statement.executeQuery("select method_id from methods where method_name = '" + methodName + "';");
                    set.next();
                    method_id = set.getInt(1);
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

    /**
     * Adds bge-s to the database.
     * @param jsonObject Data in the form of JsonObject.
     */
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

    /**
     * Adds matrix to the database.
     * @param jsonObject Data in the form of JsonObject.
     */
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

    /**
     * Adds analytes to the database.
     * @param jsonObject Data in the form of JsonObject.
     */
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

    /**
     * Adds an object (can be of different class) to the database and returns its id.
     * @param tableName Name of table in database.
     * @param tableElement Object to be added.
     * @return Id of element.
     */
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

    /**
     * Returns the if of object when it is already in the database.
     * @param tableName Name of table in database.
     * @param tableElement Object that is in database.
     * @return Id of element.
     */
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

    /**
     * @return All users to the desktop application.
     */
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

    /**
     * Returns all objects of given table.
     * @param string Name of the table.
     * @return All objects of the table.
     */
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
}
