package com.miklosova;

import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.RDF;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class RandomRDFGenerator {

    // It takes approx 80 seconds to create 10 000 triples file
    final static String FILE_NAME = "random_sample_1000MTriples.nt";
    final static String RDF_SERIALIZATION = "N-TRIPLE";

    static int NUMBER_OF_TRIPLES = 1000000000;

    // Pool of IRI predicates to choose from
    private static final String[] PREDICATES_POOL = {
            "http://example.org/predicate1",
            "http://example.org/predicate2",
            "http://example.org/predicate3",
            "http://example.org/predicate4",
            "http://example.org/predicate5",
            "http://example.org/predicate6",
            "http://example.org/predicate7",
            "http://example.org/predicate8",
            "http://example.org/predicate9",
            "http://example.org/predicate10",
            "http://example.org/predicate11",
            "http://example.org/predicate12",
            "http://example.org/predicate13",
            "http://example.org/predicate14",
            "http://example.org/predicate15",
            "http://example.org/predicate16",
            "http://example.org/predicate17",
            "http://example.org/predicate18",
            "http://example.org/predicate19",
            "http://example.org/predicate20",
            "http://example.org/predicate21",
            "http://example.org/predicate22",
    };

    public static void createSample(String[] args) {
        // Number of RDF triples to generate
        int numberOfTriples = NUMBER_OF_TRIPLES;

        // Create a model to store the RDF data
        Model model = ModelFactory.createDefaultModel();

        // Namespace for subjects and objects
        String namespace = "http://example.org/resource/";

        // Random number generator
        Random random = new Random();

        // Generate random triples
        for (int i = 0; i < numberOfTriples; i++) {
            // Generate random subject and object URIs
            String subjectURI = namespace + "subject" + random.nextInt(100000);
            String objectURI = namespace + "object" + random.nextInt(100000);

            // Pick a random predicate from the pool
            String predicateURI = PREDICATES_POOL[random.nextInt(PREDICATES_POOL.length)];

            // Create RDF triple
            Resource subject = model.createResource(subjectURI);
            Property predicate = model.createProperty(predicateURI);
            Resource object = model.createResource(objectURI);

            // Add the triple to the model
            model.add(subject, predicate, object);
            // Optional: Save the model to a file in Turtle format

            if(i % 10000 == 0 || i == numberOfTriples){
                System.out.println(i);
                try (FileWriter out = new FileWriter(FILE_NAME, true)) {
                    model.write(out, RDF_SERIALIZATION);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                model = ModelFactory.createDefaultModel();
            }
        }

        // Write the model to the console in Turtle format
        //model.write(System.out, "TURTLE");


    }

    public static void appendToFile(String data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("stream_output.txt", true))) {
            bw.write(data);
            bw.newLine();  // Optional: Add new line
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

