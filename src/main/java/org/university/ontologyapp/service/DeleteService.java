package org.university.ontologyapp.service;

import java.io.File;
import java.util.Scanner;

public class DeleteService {

    public static void checkDecision(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Are you sure about your Decision? [Y/N]" );
        String userDecision = scanner.nextLine();
        if(userDecision.equalsIgnoreCase("Y")){
            deleteOntology();
        }
    }
    public static void deleteOntology(){

        System.out.println("Enter the name of the Ontology that you want to delete: (without extension)");
        Scanner scanner = new Scanner(System.in);
        String ontologyName = scanner.nextLine();

        if(ontologyName.equalsIgnoreCase("example_ontology")){
            System.out.println("You can't delete the example_ontology.owl");
        }else {
            File file = new File("ontologies/" + ontologyName + ".owl");

            if(file.exists()) {
                if(file.delete()) {
                    System.out.println("The ontology deleted successfully.");
                } else {
                    System.out.println("Deletion failed.");
                }
            } else {
                System.out.println("Τhe file doesn't exist.");
            }
        }
    }
}
