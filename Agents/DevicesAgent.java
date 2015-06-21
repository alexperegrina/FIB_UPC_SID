
   

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import java.util.Scanner;
import jade.core.behaviours.CyclicBehaviour;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.shared.JenaException;
import java.io.FileOutputStream;
import java.util.Iterator;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;

public class DevicesAgent extends Agent {
    Scanner keyboard = new Scanner(System.in);
    String NS = "http://www.semanticweb.org/adriàabella/ontologies/2015/4/untitled-ontology-7#";
    OntModel model;
            
    public class WaitInstructions extends CyclicBehaviour
    {
        public void output(String idPacient, String contingut)
        {
            String[] parts = contingut.split(":");
            contingut = parts[1];
            String name  = parts[0];
            ArrayList<String> avisos = new ArrayList <String>();
            
            String QueryString = 
            "PREFIX :<http://www.semanticweb.org/adriàabella/ontologies/2015/4/untitled-ontology-7#>" +
            "SELECT ?nombreAparato\n" +
            "WHERE {\n" +   
            "?paciente a :Paciente.\n" +
            "?paciente :Nombre_persona ?nombrePaciente.\n" +
<<<<<<< HEAD
            "FILTER regex(?nombrePaciente, ?np). \n" +
=======
>>>>>>> 0c235fa6e20badcd0fcdb49371c75e67e7b02615
            "?paciente :Padece ?dolencia.\n" +
            "?dolencia :Incompatible ?medioDolencia.\n" +      
            "?paciente :Tiene_acceso ?aparato.\n" +
            "?aparato :Utiliza ?medioAparato.\n" +
<<<<<<< HEAD
            "FILTER (?medioDolencia != ?medioAparato).\n" +
            "?aparato :Nombre_aparato ?nombreAparato.\n" +
=======
            "?aparato :Nombre_aparato ?nombreAparato.\n" +
            "FILTER regex(?nombrePaciente, ?np). \n" +
            "FILTER (?medioDolencia != ?medioAparato).\n" +        
>>>>>>> 0c235fa6e20badcd0fcdb49371c75e67e7b02615
            "}\n"+ "";
            
            ParameterizedSparqlString str = new ParameterizedSparqlString(QueryString);
            str.setLiteral("np", name);
            Query query = QueryFactory.create(str.toString());
            QueryExecution qe2 = QueryExecutionFactory.create(query, model);
            ResultSet results =  qe2.execSelect();
            if (results.hasNext()) {
                QuerySolution row = results.nextSolution();
                System.out.println("Avis a pacient " + name + " mitjançant " + row.getLiteral("nombreAparato").getString()
            + " amb contingut " + contingut);    
            } else {
                System.out.println("Ningun aparell disponible");
            }

            qe2.close();
            
        }
        
        public void action()
        {
            ACLMessage msg = myAgent.receive();
            if(msg != null)
            {
                String s = msg.getContent();
                String command = s.substring(0, Math.min(s.length(), 6));
                String content = s.substring(Math.min(s.length(), 6),s.length());
                //El missatge s'hauria de parsejar(showd:name:action_description)
                //Hotfix
                if (command.equals("showd:")) {
                    output("idPaient",content);  
                } else {
                    System.out.println("Can't process the message");
                }
                /**
                switch (command) {
                    case "showd:":  output("idPaient",content);
                        break;
            
                    default: System.out.println("Can't process the message");
                        break;
                }*/
                
            }
            else block();
        
        }
        
    }
    protected void setup() {
        model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        
        try {  
<<<<<<< HEAD
            model.read("file:/home/adria/SID/projectRDF.owl", "RDF/XML");
=======
           model.read("file:/home/carlos/Documentos/sid/proyecto/projectRDF.owl", "RDF/XML");
>>>>>>> 0c235fa6e20badcd0fcdb49371c75e67e7b02615
        }
        catch (JenaException je) {        
           System.out.println("ERROR");
           je.printStackTrace();
           System.exit(0);
        }  
    
        WaitInstructions b = new WaitInstructions();
        this.addBehaviour(b);
        
        System.out.println("Interface Agent Ready");
    
    }     

}
