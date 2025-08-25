# Music Ontology Manager

This project was developed as part of my undergraduate thesis titled:  
**"Semantic Management of Cultural Knowledge"**.  

It is a Java-based system built with **Apache Jena** for creating, managing, querying, and deleting OWL ontologies.  

---

## 📌 Features  

The system provides a **main menu with four options**:  

1. **Create a new ontology**  
   - Generate a new OWL ontology from scratch (.owl).  

2. **Process an existing ontology**  
   - Open an existing ontology and perform operations through a sub-menu:  
     - Connect **Individuals** (create triples).  
     - Add new **Individuals**.  
     - Remove existing **Individuals**.  

3. **Ontology searching**  
   - Execute SPARQL queries directly on an ontology.  
   - An example ontology (already included in the repository) was created with this system and is available to test SPARQL queries.  

4. **Delete an ontology**  
   - Remove an ontology file from the system.  

---

## 🛠️ Technologies Used  

- **Java**  
- **Apache Jena** (for ontology management and SPARQL querying)  
- Compatible with **Protégé** (you can open the generated ontologies in Protégé for visualization and further editing)  

---

## 📂 Ontology Files
Ontologies are stored in the `ontologies/` folder with the extension `.owl.` Example: `ontologies/example_ontology.owl.`

---

## 🔍 SPARQL Query Examples

Below we provide some example **SPARQL queries** that can be executed directly within the system.  
A sample ontology is already included in the project (example_ontology.owl), so that you can test your queries immediately.  
Of course, you may also create your own ontology and run queries on it.  

### 1. SELECT Queries

**List all songs:**
```sparql
PREFIX j: <http://example.org/>

SELECT ?song
WHERE {
  ?song a j:Song .
}
```

**Find all members of The Beatles:**
```sparql
PREFIX j: <http://example.org/>

SELECT ?member
WHERE {
  ?member a j:Person ;
          j:Member_of <http://example.org/The_Beatles> .
}
```

**Find songs that belong to an album and the label that released them:**
```sparql
PREFIX j: <http://example.org/>

SELECT ?song ?album ?label
WHERE {
  ?song a j:Song ;
        j:Has_recording ?rec .
  ?rec j:Has_album ?album .
  ?album j:Has_label ?label .
}
```

**Find all persons and (if exists) the instrument they use:**
```sparql
PREFIX j: <http://example.org/>

SELECT ?person ?instrument
WHERE {
  ?person a j:Person .
  OPTIONAL { ?person j:Uses ?instrument . }
}
```

**How many songs does each band have?**
```sparql
PREFIX j: <http://example.org/>

SELECT ?band (COUNT(?song) AS ?songsCount)
WHERE {
  ?band a j:Band ;
        j:Has_song ?song .
}
GROUP BY ?band
```

### 2. ASK Queries

**Is Freddie Mercury in the ontology?**
```sparql
PREFIX j: <http://example.org/>

ASK {
  <http://example.org/Freddie_Mercury> a j:Person .
}
```

### 3. CONSTRUCT Queries

**Build a graph of albums and their labels:**
```sparql
PREFIX j: <http://example.org/>

CONSTRUCT {
  ?album j:Has_label ?label .
}
WHERE {
  ?album a j:Album ;
         j:Has_label ?label .
}
```

### 4. DESCRIBE Queries

**Describe an individual (example: The Beatles):**
```sparql
PREFIX j: <http://example.org/>

DESCRIBE <http://example.org/The_Beatles>
```

---

### 👨‍💻 Author
- Antonis Mourtzakis
- antmourtzakis@gmail.com
- [LinkedIn Profile](https://www.linkedin.com/in/antonis-mourtzakis/)
