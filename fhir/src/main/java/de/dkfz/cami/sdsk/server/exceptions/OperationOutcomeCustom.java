package de.dkfz.cami.sdsk.server.exceptions;

import ca.uhn.fhir.rest.server.exceptions.InvalidRequestException;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import org.hl7.fhir.r4.model.OperationOutcome;

public class OperationOutcomeCustom {

    public OperationOutcomeCustom(){

    }

    public OperationOutcome customOperationOutcome(OperationOutcome.IssueSeverity issueSeverity, OperationOutcome.IssueType issueType, String message) {
        OperationOutcome operationOutcome = new OperationOutcome();
        operationOutcome.addIssue().setSeverity(issueSeverity).setCode(issueType).setDiagnostics(message);
        return operationOutcome;
    }


    public void throwInvalidRequest(OperationOutcome.IssueSeverity issueSeverity, OperationOutcome.IssueType issueType, String message){
        OperationOutcome operationOutcome = customOperationOutcome(issueSeverity, issueType, message);
        throw new InvalidRequestException(message, operationOutcome);
    }

    public void throwResourceNotFound(OperationOutcome.IssueSeverity issueSeverity, OperationOutcome.IssueType issueType, String message){
        OperationOutcome operationOutcome = customOperationOutcome(issueSeverity, issueType, message);
        throw new ResourceNotFoundException(message, operationOutcome);
    }
}
