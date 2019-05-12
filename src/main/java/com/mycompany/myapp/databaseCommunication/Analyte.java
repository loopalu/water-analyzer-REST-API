package com.mycompany.myapp.databaseCommunication;

import javafx.beans.property.SimpleStringProperty;

/**
 * Analyte/bge that is being used.
 */
public class Analyte {
    private final SimpleStringProperty analyte;
    private final SimpleStringProperty concentration;

    /**
     * @param analyte Name of the analyte.
     * @param concentration Concentration of the analyte.
     */
    public Analyte(String analyte, String concentration) {
        this.analyte = new SimpleStringProperty(analyte);
        this.concentration = new SimpleStringProperty(concentration);
    }

    public String getAnalyte() {
        return analyte.get();
    }

    public void setAnalyte(String analyteString) {
        analyte.set(analyteString);
    }

    public String getConcentration() {
        return concentration.get();
    }

    public void setConcentration(String concentrationString) {
        concentration.set(concentrationString);
    }
}
