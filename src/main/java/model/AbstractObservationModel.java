package model;

public abstract class AbstractObservationModel {

	protected String observationSystem;
	protected String observationCode;
	private String observationID;
	private String patientID;

	public AbstractObservationModel() {
		super();
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

	public String getObservationID() {
	    return observationID;
	}

	public void setObservationID(String observationID) {
	    this.observationID = observationID;
	}

	public String getPatientID() {
	    return patientID;
	}

	public void setPatientID(String patientID) {
	    this.patientID = patientID;
	}

}