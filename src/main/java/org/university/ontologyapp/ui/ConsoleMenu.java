package org.university.ontologyapp.ui;

import org.university.ontologyapp.service.CreateOntologyService;
import org.university.ontologyapp.service.DeleteService;
import org.university.ontologyapp.service.ProcessingService;
import org.university.ontologyapp.service.SearchingService;

import java.util.Scanner;

public class ConsoleMenu {

    public static void mainMenu(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Music Ontology Manager\n");

        while (true){

            System.out.println("Choose one of the following:");
            System.out.println("1. New Ontology");
            System.out.println("2. Ontology Processing");
            System.out.println("3. Ontology Searching");
            System.out.println("4. Delete Ontology");
            System.out.println("0. Exit");

            System.out.print("Enter the number of your choice: ");
            String choice = scanner.nextLine();

            if(choice.equals("1")){
                spaceMenu();
                CreateOntologyService.ontologyExistCheck();
            }
            else if (choice.equals("2")){
                spaceMenu();
                ProcessingService.ontologyChoice();
            }
            else if (choice.equals("3")){
                spaceMenu();
                SearchingService.ontologyChoice();
            }
            else if (choice.equals("4")){
                spaceMenu();
                DeleteService.checkDecision();
            }
            else if (choice.equals("0")){
                System.exit(0);
            }else {
                spaceMenu();
                System.out.println("[Please enter a number between 0-4]");
            }
            spaceMenu();
        }
    }

    public static void processingMenu(){

        while (true){
            spaceMenu();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Choose an option:");
            System.out.println("1. Make connections");
            System.out.println("2. Add individual");
            System.out.println("3. Remove individual");
            System.out.println("0. Back");

            System.out.print("Enter the number of your choice: ");
            String choice = scanner.nextLine();

            if(choice.equals("1")){
                spaceMenu();
                ProcessingService.ontologyTriples();
            }
            else if (choice.equals("2")){
                spaceMenu();
                ProcessingService.addIndividual();
            }
            else if (choice.equals("3")){
                spaceMenu();
                ProcessingService.removeIndividual();
            }
            else if (choice.equals("0")){
                spaceMenu();
                break;
            }else {
                spaceMenu();
                System.out.println("[Please enter a number between 0-4]");
            }
            spaceMenu();
        }
    }

    private static void spaceMenu(){
        for(int i =0; i<3; i++){
            System.out.println("\n");
        }
    }
}
