package model;

public class Observation {

    private String observationSystem;
    private String observationCode;
    private double numericalValue;
    private String unit;
    private String valueSystem;
    private String valueCode;
    private String observationID;

    public Observation(String observationSystem, String observationCode, double numericalValue, String unit) {
        this.observationID = observationID;
        this.observationSystem = observationSystem;
        this.observationCode = observationCode;
        this.numericalValue = numericalValue;
        this.unit = unit;
    }

    public Observation(String observationSystem, String observationCode, String valueSystem, String valueCode) {
        this.observationID = observationID;
        this.observationSystem = observationSystem;
        this.observationCode = observationCode;
        this.valueSystem = valueSystem;
        this.valueCode = valueCode;
    }

    public String getObservationSystem() {
        return observationSystem;
    }

    public void setObservationSystem(String observationSystem) {
        this.observationSystem = observationSystem;
    }

    public String getObservationCode() {
        return observationCode;
    }

    public void setObservationCode(String observationCode) {
        this.observationCode = observationCode;
    }

    public double getNumericalValue() {
        return numericalValue;
    }

    public void setNumericalValue(double numericalValue) {
        this.numericalValue = numericalValue;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getValueSystem() {
        return valueSystem;
    }

    public void setValueSystem(String valueSystem) {
        this.valueSystem = valueSystem;
    }

    public String getValueCode() {
        return valueCode;
    }

    public void setValueCode(String valueCode) {
        this.valueCode = valueCode;
    }

    public String getObservationID() {
        return observationID;
    }

    public void setObservationID(String observationID) {
        this.observationID = observationID;
    }
}