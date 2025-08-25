package org.university.ontologyapp.service;

import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.*;
import org.university.ontologyapp.ui.ConsoleMenu;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class ProcessingService {

    static String ontologyName;
    static String ontologyPath= "ontologies/";

    public static void ontologyChoice(){
        System.out.println("Enter the name of the Ontology: (without extension)");
        Scanner scanner = new Scanner(System.in);

        ontologyName = scanner.nextLine();
        File file = new File(ontologyPath + ontologyName + ".owl");

        if(file.exists() && file.isFile()){
            ConsoleMenu.processingMenu();
        }
        else {
            System.out.println("The file doesn't exist.");
        }
    }

    public static void ontologyTriples(){

        Scanner scanner = new Scanner(System.in);
        Map<Integer, String> objectProperty = new LinkedHashMap<>();

        objectProperty.put(1,"memberOf");
        objectProperty.put(2,"uses");
        objectProperty.put(3,"hasSong");
        objectProperty.put(4,"writerOf");
        objectProperty.put(5,"hasRecording");
        objectProperty.put(6,"hasAlbum");
        objectProperty.put(7,"producerOf");
        objectProperty.put(8,"recorded");
        objectProperty.put(9,"singerOf");
        objectProperty.put(10,"hasLabel");
        objectProperty.put(11,"released");

        while(true){
            System.out.println("--Let's make the Triples--\n");
            System.out.println("Enter the first individual:");
            String individual1 = scanner.nextLine();
            System.out.println("Enter the second individual:");
            String individual2 = scanner.nextLine();
            System.out.println("\nThe Object Properties: (e.g. 5 for hasRecording)");

            for (int key=1; key<=11; key++){
                System.out.println(key + ") " + objectProperty.get(key));
            }

            Scanner scannerObject = new Scanner(System.in);
            int selectedObjectProperty = 0;

            while (true) {
                try {
                    System.out.println("Choose a number: (e.g. 5 for hasRecording)");
                    selectedObjectProperty = scannerObject.nextInt();

                    if (selectedObjectProperty >= 1 && selectedObjectProperty <= 11) {
                        break;
                    } else {
                        System.out.println("The number must be between 1 and 11. Try again!");
                    }

                } catch (Exception e) {
                    System.out.println("Invalid input! Please enter a number.");
                    scannerObject.next(); // we clean the invalid input
                }
            }

            String ontologyFile = ontologyPath + ontologyName +".owl";
            OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
            ontModel.read(ontologyFile);

            //We make objectProperties
            ObjectProperty memberOf = ontModel.createObjectProperty("http://example.org/Member_of");
            ObjectProperty uses = ontModel.createObjectProperty("http://example.org/Uses");
            ObjectProperty hasSong = ontModel.createObjectProperty("http://example.org/Has_song");
            ObjectProperty writerOf = ontModel.createObjectProperty("http://example.org/Writer_of");
            ObjectProperty hasRecording = ontModel.createObjectProperty("http://example.org/Has_recording");
            ObjectProperty hasAlbum = ontModel.createObjectProperty("http://example.org/Has_album");
            ObjectProperty producerOf = ontModel.createObjectProperty("http://example.org/Producer_of");
            ObjectProperty singerOf = ontModel.createObjectProperty("http://example.org/Singer_of");
            ObjectProperty hasLabel = ontModel.createObjectProperty("http://example.org/Has_label");
            ObjectProperty recorded = ontModel.createObjectProperty("http://example.org/Recorded");
            ObjectProperty released = ontModel.createObjectProperty("http://example.org/Released");

            String ind1="http://example.org/"+individual1;
            String ind2="http://example.org/"+individual2;
            Individual name1 = ontModel.getIndividual(ind1);
            Individual name2 = ontModel.getIndividual(ind2);


            // Checking existence as Individuals in ontology
            if (containsIndividual(ontModel, ind1) && containsIndividual(ontModel, ind2)) {
                // Adding Object Property between Individuals
                if (selectedObjectProperty==1){
                    ontModel.add(name1, memberOf, name2);
                } else if (selectedObjectProperty==2) {
                    ontModel.add(name1, uses, name2);
                }else if (selectedObjectProperty==3) {
                    ontModel.add(name1, hasSong, name2);
                }else if (selectedObjectProperty==4) {
                    ontModel.add(name1, writerOf, name2);
                }else if (selectedObjectProperty==5) {
                    ontModel.add(name1, hasRecording, name2);
                }else if (selectedObjectProperty==6) {
                    ontModel.add(name1, hasAlbum, name2);
                }else if (selectedObjectProperty==7) {
                    ontModel.add(name1, producerOf, name2);
                }else if (selectedObjectProperty==8) {
                    ontModel.add(name1, recorded, name2);
                }else if (selectedObjectProperty==9) {
                    ontModel.add(name1, singerOf, name2);
                }else if (selectedObjectProperty==10) {
                    ontModel.add(name1, hasLabel, name2);
                }else if (selectedObjectProperty==11) {
                    ontModel.add(name1, released, name2);
                }

                System.out.println("The connection was successful.");

                //save the ontology
                try (FileOutputStream outputStream = new FileOutputStream(ontologyPath + ontologyName +".owl")) {
                    ontModel.write(outputStream, "RDF/XML-ABBREV");
                    System.out.println("OWL model saved successfully.");
                } catch (IOException e) {
                    System.out.println("Error: " + e.getMessage());
                }

            }else {

                System.out.println("One or both names do not exist in the ontology as Individuals.");
            }
            System.out.println("Do you want to continue?[Y/N]");
            String userAnswer = scanner.nextLine();
            if(userAnswer.equalsIgnoreCase("N")){
                break;
            }

        }
    }

    private static boolean containsIndividual(OntModel ontModel, String name) {
        Individual individual = ontModel.getIndividual(name);
        return individual != null;
    }

    public static void addIndividual(){

        Scanner scanner = new Scanner(System.in);
        String classURI;
        String individual;
        System.out.println("Enter the name of the class:");

        classURI = scanner.nextLine();
        while (classURI.trim().isEmpty() || classURI.trim().contains(" ")) {
            System.out.println("Please try again!");
            classURI = scanner.nextLine();
        }

        classURI ="http://example.org/"+classURI;

        System.out.println("Enter the name of the new Individual:");
        String individualURI = scanner.nextLine();


        while (individualURI.trim().isEmpty() || individualURI.contains(" ")) {
            System.out.println("Please try again! No spaces allowed, use '_' instead (e.g. John_Lennon)");
            individualURI = scanner.nextLine();
        }
        individualURI = "http://example.org/" + individualURI;

        individual = individualURI;

        String ontologyFile = ontologyPath + ontologyName +".owl";
        OntModel ontModel = ModelFactory.createOntologyModel();
        ontModel.read(ontologyFile);


        // Finding the class in OntModel
        OntClass targetClass = ontModel.getOntClass(classURI);

        // We check if the class exists
        if (targetClass != null) {

            // Creation of new Individual, and add in the class
            Resource newIndividual = ontModel.createIndividual(individual, targetClass);
            System.out.println("New Individual added to the class.");

            System.out.println("OWL model saved successfully.");

        } else {
            System.out.println("Class not found in the ontology.");
        }

        try (FileOutputStream outputStream = new FileOutputStream(ontologyPath + ontologyName +".owl")) {
            ontModel.write(outputStream, "RDF/XML-ABBREV");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void removeIndividual(){
        String individual;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the name of the Individual to be removed:");
        individual = scanner.nextLine();

        String ontologyFile = ontologyPath + ontologyName + ".owl";
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
        ontModel.read(ontologyFile);


        individual="http://example.org/" + individual;

        // Finding Individual in OntModel
        Individual individualToRemove = ontModel.getIndividual(individual);

        //We check if individual exists
        if (individualToRemove != null) {

            // We find all triples where are SUBJECT
            StmtIterator stmtIteratorSubject = ontModel.listStatements(individualToRemove, null, (RDFNode) null);

            List<Statement> toRemove = new ArrayList<>();
            while (stmtIteratorSubject.hasNext()) {
                toRemove.add(stmtIteratorSubject.next());
            }

            // We find all triples where are OBJECT
            StmtIterator stmtIteratorObject = ontModel.listStatements(null, null, individualToRemove);

            while (stmtIteratorObject.hasNext()) {
                toRemove.add(stmtIteratorObject.next());
            }

            // Σβήνουμε όλα τα statements
            for (Statement stmt : toRemove) {
                ontModel.remove(stmt);
            }

            System.out.println("Individual " + individualToRemove.getLocalName() + " was removed successfully.");

        } else {
            System.out.println("Individual not found in the ontology.");
        }

        try (FileOutputStream outputStream = new FileOutputStream(ontologyPath + ontologyName + ".owl")) {
            ontModel.write(outputStream, "RDF/XML-ABBREV");
            System.out.println("OWL model saved successfully.");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

