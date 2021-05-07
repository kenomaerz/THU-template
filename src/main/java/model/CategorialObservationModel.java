package model;

public class CategorialObservationModel extends AbstractObservationModel {

    protected String valueSystem;
    protected String valueCode;
  
    
    
    public CategorialObservationModel(String observationSystem, String observationCode, String valueSystem, String valueCode) {
        this.observationSystem = observationSystem;
        this.observationCode = observationCode;
        this.valueSystem = valueSystem;
        this.valueCode = valueCode;
    }
    
    /**
     * This constructor is protected and intend for use with BooleanObservationModels only
     */
    protected CategorialObservationModel(String observationSystem, String observationCode, boolean value) {
    	this.observationSystem = observationSystem;
        this.observationCode = observationCode;
        this.valueSystem = "http://sfb125.de/ontology/factorontology/";
        if (value) {
        	this.valueCode = "true_value_specification";
        } else {
        	this.valueCode = "false_value_specification";
        }
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