POST Patient: 
- call createPatientRest 
- give the patient's informations as parameters (prename, lastname, gender)
 
POST Observation (categorical): 
- call createObservationtCategoricalRest 
- give the informations about the observation as parameters (observationSystem, observationCode, patientID, valueSystem, valueCode)

POST Observation (numerical): 
- call createObservationtNumericalRest 
- give the informations about the observation as parameters (observationSystem, observationCode, patientID, value, valueUnit)

GET all patients: http://localhost:8080/Patient 
GET Patient by ID: 
GET Patient by Identifier