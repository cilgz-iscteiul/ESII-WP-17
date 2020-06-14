package xml;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class Main {
	
	private String string="";
	
	public void add(String s) {
		this.string =string + s +"\n";
	}
	
	public void buildHTML() throws IOException {
		File f = new File("CovidCriteria.html");
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		bw.write("<html><body><h1>CovidCriteria</h1>");
		bw.write("<textarea cols=90 rows=200>");
		bw.write(string);
		bw.write("</textarea>");
		bw.write("</body></html>");
		bw.close();
		Desktop.getDesktop().browse(f.toURI());		
	}
	
	
   public static void main(String[] args){
	  Main m = new Main ();
      try {	   	      	      	      	  
    	  File workingDirectory = File.createTempFile("git-test", "");
    	  workingDirectory.delete();
    	  
    	  Git.cloneRepository()
    	  .setURI("https://github.com/vbasto-iscte/ESII1920")
    	  .setDirectory(workingDirectory)
    	  .call();	
    	       	  
    	 Repository repo = FileRepositoryBuilder.create(new File(workingDirectory.toString()));	    
    	  
         File inputFile = new File(workingDirectory.toString()+"/covid19spreading.rdf");    	      	  
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.parse(inputFile);
         doc.getDocumentElement().normalize();         
         
         String query = "/RDF/NamedIndividual/@*";
         XPathFactory xpathFactory = XPathFactory.newInstance();
         XPath xpath = xpathFactory.newXPath();
         XPathExpression expr = xpath.compile(query);         
         NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
                  
         
         if(args[0].equals("Lista")) {     
        	 for (int i = 0; i < nl.getLength(); i++) {
        		 m.add(StringUtils.substringAfter(nl.item(i).getNodeValue(), "#"));
        	 }
         }

         if(args[0].contains("and")) {
        	 Transform t1= new Transform (args[0].substring(0, args[0].indexOf("and")));
        	 Transform t2= new Transform (args[0].substring(args[0].indexOf("and")+3));

        	 query = "//*[contains(@about,'"+t1.getRegiao()+"')]/"+t1.getTipo()+"/text()";  
        	 expr = xpath.compile(query);     
        	 m.add("Numero de "+t1.getTipo()+" feitos "+t1.getRegiao()+": "+expr.evaluate(doc, XPathConstants.STRING));
        	 
        	 query = "//*[contains(@about,'"+t2.getRegiao()+"')]/"+t2.getTipo()+"/text()";  
        	 expr = xpath.compile(query);     
        	 m.add("Numero de "+t2.getTipo()+" feitos "+t2.getRegiao()+": "+expr.evaluate(doc, XPathConstants.STRING));
         }
         
         else if(args[0].contains("mais") || args[0].contains("menos")) {
        	 Transform t1=null;
        	 Transform t2=null;
        	 if(args[0].contains("mais") ) {
        		 t1= new Transform (args[0].substring(0, args[0].indexOf("mais")));
        		 t2= new Transform (args[0].substring(args[0].indexOf("mais")+3));
        	 }

        	 if(args[0].contains("menos") ) {
        		 t1= new Transform (args[0].substring(0, args[0].indexOf("menos")));
        		 t2= new Transform (args[0].substring(args[0].indexOf("menos")+3));
        	 }

        	 query = "//*[contains(@about,'"+t1.getRegiao()+"')]/"+t1.getTipo()+"/text()";  
        	 expr = xpath.compile(query);     
        	 int i = Integer.parseInt(expr.evaluate(doc, XPathConstants.STRING).toString());       	 
        	 t1.compare(i,m);
        	 
        	 if(args[0].contains("mais") )
         	 	i = i+Integer.parseInt(expr.evaluate(doc, XPathConstants.STRING).toString());
         	 
         	 if(args[0].contains("menos"))
         		 i = i - Integer.parseInt(expr.evaluate(doc, XPathConstants.STRING).toString());
         	 
         	 m.add("Resultado da preposição 1  = " +i);
        	 
         	 i =0;
         	       	 
        	 query = "//*[contains(@about,'"+t2.getRegiao()+"')]/"+t2.getTipo()+"/text()";  
        	 expr = xpath.compile(query);
        	 int j=  Integer.parseInt(expr.evaluate(doc, XPathConstants.STRING).toString());
        	 t2.compare(j,m);
        	 
        	 if(args[0].contains("mais") )
        	 	i = i+Integer.parseInt(expr.evaluate(doc, XPathConstants.STRING).toString());
        	 
        	 if(args[0].contains("menos"))
        		 i = i - Integer.parseInt(expr.evaluate(doc, XPathConstants.STRING).toString());
        	 
        	 m.add("Resultado da preposição 2  = " +i);
         }        
         else {
        	 Transform t1= new Transform (args[0]);

        	 query = "//*[contains(@about,'"+t1.getRegiao()+"')]/"+t1.getTipo()+"/text()";  
        	 expr = xpath.compile(query);     
        	 m.add("Numero de "+t1.getTipo()+" feitos "+t1.getRegiao()+": "+expr.evaluate(doc, XPathConstants.STRING));
         }
          m.buildHTML();       
	} catch (Exception e) { e.printStackTrace(); }
   }
}
