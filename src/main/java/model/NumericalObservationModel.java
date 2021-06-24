package model;

public class NumericalObservationModel extends AbstractObservationModel {

    private double numericalValue;
    private String unit;

    public NumericalObservationModel(String observationSystem, String observationCode, double numericalValue, String unit) {
        this.observationSystem = observationSystem;
        this.observationCode = observationCode;
        this.numericalValue = numericalValue;
        this.unit = unit;
    }

    public double getValue() {
        return numericalValue;
    }

    public void setValue(double numericalValue) {
        this.numericalValue = numericalValue;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}