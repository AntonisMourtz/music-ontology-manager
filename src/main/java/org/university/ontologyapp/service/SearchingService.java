package org.university.ontologyapp.service;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import java.io.File;
import java.util.Scanner;

public class SearchingService {

    public static void ontologyChoice(){
        System.out.println("Enter the name of the Ontology: (without extension)");
        Scanner scanner = new Scanner(System.in);

        String ontologyName = scanner.nextLine();
        File file = new File("ontologies/"+ ontologyName + ".owl");

        if(file.exists() && file.isFile()){
            ontologySearch();
        }
        else {
            System.out.println("The file doesn't exist.");
        }
    }
    private static void ontologySearch(){

        //We create an empty RDF model
        Model model = ModelFactory.createDefaultModel();

        //We read the ontology from the file
        model.read("ontologies/example_ontology.owl");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Write your SPARQL query. When you're done, write a line with just 'END':");

        //We use StringBuilder to select multiple SPARQL query lines
        StringBuilder queryBuilder = new StringBuilder();


        //Endless loop to read multiple lines from the user
        while (true) {
            String line = scanner.nextLine();
            if (line.equalsIgnoreCase("END")) {
                break;
            }
            queryBuilder.append(line).append("\n"); // We add the line in query and a newline
        }

        //We convert the StringBuilder to a String that includes the query
        String sparqlQuery = queryBuilder.toString();

        try {
            // We make the Query object from String
            Query query = QueryFactory.create(sparqlQuery);

            // We run the query in RDF model
            try (QueryExecution userQuery = QueryExecutionFactory.create(query, model)) {

                    //Check if the query is of type SELECT
                if (query.isSelectType()) {
                    ResultSet results = userQuery.execSelect(); // run the SELECT query
                    ResultSetFormatter.out(System.out, results, query); // Print rhe results

                    //Check if the query is of type ASK
                } else if (query.isAskType()) {
                    boolean result = userQuery.execAsk();
                    System.out.println("Result ASK: " + result);

                    //Check if the query is of type CONSTRUCT
                } else if (query.isConstructType()) {
                    Model resultModel = userQuery.execConstruct();
                    resultModel.write(System.out, "TTL"); // run the result (Turtle format)

                    //Check if the query is of type DESCRIBE
                } else if (query.isDescribeType()) {
                    Model resultModel = userQuery.execDescribe();
                    resultModel.write(System.out, "TTL");
                }
            }

        } catch (Exception e) {
            System.out.println("Error in SPARQL query: " + e.getMessage() + "\n");
        }
    }
}
