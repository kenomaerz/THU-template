package de.dkfz.cami.sdsk.server.api;

import de.dkfz.cami.sdsk.core.model.bfo.BFOQualityClass;
import de.dkfz.cami.sdsk.server.Utils;
import org.eclipse.rdf4j.model.IRI;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class IRIValidator {

    private HashSet<String> qCodes = new HashSet<String>();
    private HashSet<String> qSystems = new HashSet<String>();
    private List<IRI> values = new ArrayList<IRI>();
    private HashSet<String> vSystems = new HashSet<String>();
    private HashSet<String> vCodes = new HashSet<String>();
    private List<BFOQualityClass> qClasses = Utils.ontology.getAllQualityClasses();


    public HashSet<String> getqSystems() {
        for (BFOQualityClass qualityC : qClasses) {
            String qualitySystem = qualityC.getIRI().getNamespace();
            qSystems.add(qualitySystem);
        }
        return qSystems;
    }

    public HashSet<String> getqCodes() {
        for (BFOQualityClass qualityC : qClasses) {
            String qualityCode = qualityC.getIRI().getLocalName();
            qCodes.add(qualityCode);

        }
        return qCodes;
    }

    public List<BFOQualityClass> getqIRIS() {
        System.out.println(qClasses);
        return qClasses;
    }


    public List<IRI> getvIRIS(BFOQualityClass qClass) {
        values = qClass.getPreferredCategoricalValues();
        return values;
    }

    public HashSet<String> getvSystem(BFOQualityClass qClass) {
        values = getvIRIS(qClass);
        for (IRI value : values) {
            String valueSystem = value.getNamespace();
            vSystems.add(valueSystem);
        }
        return vSystems;
    }

    public HashSet<String> getvCodes(BFOQualityClass qClass) {
        values = getvIRIS(qClass);
        for (IRI value : values) {
            String valueCode = value.getLocalName();
            vCodes.add(valueCode);
        }
        return vCodes;
    }

    public boolean codeIsAvailable(String obsCode) {
        getqCodes();
        return qCodes.contains(obsCode);
    }

    public boolean systemIsAvailable(String obsSystem) {
        getqSystems();
        return qSystems.contains(obsSystem);
    }

    public boolean iriIsAvailable(BFOQualityClass iri) {
        getqIRIS();
        return qClasses.contains(iri);
    }

    public boolean vCodeIsAvailable(BFOQualityClass qClass, String code) {
        getvCodes(qClass);
        return vCodes.contains(code);
    }

    public boolean vSystemIsAvailable(BFOQualityClass qClass, String system) {
        getvSystem(qClass);
        return vSystems.contains(system);
    }

    public boolean vIriIsAvailable(BFOQualityClass qClass, IRI iri) {
        getvIRIS(qClass);
        return values.contains(iri);
    }
}