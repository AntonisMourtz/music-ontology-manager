package org.university.ontologyapp.service;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class CreateOntologyService {

    public static void ontologyExistCheck(){
        System.out.println("Enter the name of the Ontology: (without extension)");
        Scanner scanner = new Scanner(System.in);

        String userInput = scanner.nextLine();
        String ontologyPath= "ontologies/";
        File file = new File(ontologyPath + userInput + ".owl");

        if(file.exists() && file.isFile()){
            System.out.println("The ontology with the name '" + userInput+ ".owl' already exist!\n");
        }
        else if(userInput.trim().isEmpty()) {
            System.out.println("Please enter a non-empty value.");
        }
        else {
           newOntology(userInput);
        }
    }

    private static void newOntology(String userInput){

        String[] classNames = {
                "Person", "Band", "Musical_Instruments", "Song", "Recording",
                "Album", "Studio", "Venue", "Label", "Year"
        };

        Map<String, List<String>> classes = new LinkedHashMap<>();

        for (String name : classNames){
            classes.put(name, new ArrayList<>());
        }

        int counter =1;
        System.out.println("The Classes: ");
        for (String name : classNames){

            if (name.equals("Person") || name.equals("Band")){
                System.out.println(counter + ") Artists -> " + name);
            }
            else if(name.equals("Studio") || name.equals("Venue")) {
                System.out.println(counter + ") Artists -> " + name);
            }
            else{
                System.out.println(counter+") "+ name);
            }
            counter++;
        }

        System.out.println("\n--Instructions--\n ");
        System.out.println("-> Υou can put as many names as you want for each class");
        System.out.println("-> When you finish entering names, enter 'STOP'");
        System.out.println("-> Do not leave spaces between words. Put '_' instead of a space(e.g. John_Lennon)\n\n");

        Scanner scanner  = new Scanner(System.in);

        for (String name : classNames){

            System.out.println("Enter the individuals of the class: " + name + " (type STOP to finish)");
            while (true){
                String individualInput = scanner.nextLine();
                if (individualInput.equalsIgnoreCase("STOP")){
                    break;
                }
                if(individualInput.trim().contains(" ")) { //checks if there is a gap
                    System.out.println("Please do not leave spaces between words. Put '_' instead of a space(e.g. John_Lennon)");
                    continue;
                }
                if (individualInput.trim().isEmpty()) {
                    System.out.println("Please enter a non-empty value.");
                    continue;
                }

                classes.get(name).add(individualInput);

            }
        }

        System.out.println("\n\n\n\n");
        System.out.println("Ontology Summary: \n");

        for (String name : classNames) {
            System.out.println(name + ":");
            for (String individual : classes.get(name)) {
                System.out.println("  - " + individual);
            }
        }

        //We make the RDF model
        Model model = ModelFactory.createDefaultModel();
        //We make the ontology model
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, model);


        //We make the Classes
        OntClass artistsClass = ontModel.createClass("http://example.org/Artists");
        OntClass personClass = ontModel.createClass("http://example.org/Person");
        OntClass bandClass = ontModel.createClass("http://example.org/Band");
        OntClass musicalInstrumentsClass = ontModel.createClass("http://example.org/Musical_Instraments");
        OntClass songClass = ontModel.createClass("http://example.org/Song");
        OntClass recordingClass = ontModel.createClass("http://example.org/Recording");
        OntClass albumClass = ontModel.createClass("http://example.org/Album");
        OntClass placeClass = ontModel.createClass("http://example.org/Place");
        OntClass studioClass = ontModel.createClass("http://example.org/Studio");
        OntClass venueClass = ontModel.createClass("http://example.org/Venue");
        OntClass labelClass = ontModel.createClass("http://example.org/Label");
        OntClass yearClass = ontModel.createClass("http://example.org/Year");

        //We make the subclasses
        artistsClass.addSubClass(personClass);
        artistsClass.addSubClass(bandClass);

        placeClass.addSubClass(studioClass);
        placeClass.addSubClass(venueClass);

        // Connect names with classes in ontology
        for (Map.Entry<String, List<String>> entry : classes.entrySet()) {
            String className = entry.getKey();
            List<String> names = entry.getValue();

            OntClass ontClass = null;

            switch (className) {
                case "Person":
                    ontClass = personClass;
                    break;
                case "Band":
                    ontClass = bandClass;
                    break;
                case "Musical_Instruments":
                    ontClass = musicalInstrumentsClass;
                    break;
                case "Song":
                    ontClass = songClass;
                    break;
                case "Recording":
                    ontClass = recordingClass;
                    break;
                case "Album":
                    ontClass = albumClass;
                    break;
                case "Studio":
                    ontClass = studioClass;
                    break;
                case "Venue":
                    ontClass = venueClass;
                    break;
                case "Label":
                    ontClass = labelClass;
                    break;
                case "Year":
                    ontClass = yearClass;
                    break;
            }

            if (ontClass != null) {
                for (String name : names) {
                    String individualURI = "http://example.org/" + name;
                    ontModel.createIndividual(individualURI, ontClass);
                }
            }
        }

        //We save the ontology
        try (FileOutputStream outputStream = new FileOutputStream("ontologies/" + userInput +".owl")) {
            ontModel.write(outputStream, "RDF/XML-ABBREV");
            System.out.println("Ontology saved successfully!!!");
        } catch (IOException e) {
            System.out.println("Error saving ontology: " + e.getMessage());
            System.out.println("Error: " + e.getMessage());
        }
    }
}
