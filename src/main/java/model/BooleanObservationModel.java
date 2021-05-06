package model;

import error.IllegalModelStateException;

public class BooleanObservationModel extends CategorialObservationModel {


    public BooleanObservationModel(String observationSystem, String observationCode, boolean value) {
        super(observationSystem, observationCode, value);
    }

    public boolean getValue() {
        if (valueCode.equals("true_value_specification")) {
        	return true;
        } else if (valueCode.equals("false_value_specification")) {
        	return false;
        } else throw new IllegalModelStateException("BooleanObservationModel has a valueCode that cannot be translated to a boolean value: " + this.valueCode);
    }

    public void setValue(boolean value) {
        if (value) {
        	this.valueCode = "true_value_specification";
        } else {
        	this.valueCode = "false_value_specification";
        }
    }
}