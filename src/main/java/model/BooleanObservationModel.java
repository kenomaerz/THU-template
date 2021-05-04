package model;

public class BooleanObservationModel extends AbstractObservationModel {

    private String valueSystem;
    private String valueCode;


    public BooleanObservationModel(String observationSystem, String observationCode, String valueSystem, String valueCode) {
        this.observationSystem = observationSystem;
        this.observationCode = observationCode;
        this.valueSystem = valueSystem;
        this.valueCode = valueCode;
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
}