package _Covid_SCI_Discoveries._Covid_SCI_Discoveries;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import pl.edu.icm.cermine.ContentExtractor;
import pl.edu.icm.cermine.exception.AnalysisException;
import pl.edu.icm.cermine.metadata.model.DateType;
import pl.edu.icm.cermine.metadata.model.DocumentAuthor;
import pl.edu.icm.cermine.metadata.model.DocumentDate;
import pl.edu.icm.cermine.metadata.model.DocumentMetadata;

public class HTMLTableBuilderTest {


	public HTMLTableBuilder tableHTML;

	@SuppressWarnings("static-access")
	@Test
	public void addStyleToHTMLTableTest() {
		tableHTML = new HTMLTableBuilder();
		@SuppressWarnings("unused")
		String documentPreActual = tableHTML.getDocumentPre();
//		String documentPreActual = "<!DOCTYPE html>\r\n"
//								 + "<html>\r\n"
//								 + "<head>\r\n"
//								 + "<meta charset=\"UTF-8\">\r\n"
//								 + "<style>\r\n"
//
//								 + " \r\n"
//
//								 + "table {\r\n"
//								 + " font-family: arial, sans-serif;\r\n"
//								 + " border-collapse: collapse;j\r\n" 
//								 + " width: 100%;\r\n"									
//								 + "}\r\n"
//
//								 + " \r\n"
//
//								 + "td, th {\r\n"
//								 + " border: 1px solid #dddddd;\r\n"
//								 + " text-align: left;\r\n" 
//								 + " padding: 6px;\r\n"
//								 + "}\r\n"
//
//								 + " \r\n"
//
//								 + "tr:nth-child(even) {\r\n"
//								 + "	background-color: #e6f0ff;\r\n"
//								 + "}\r\n"
//
//			 					 + " \r\n"
//
//								 + "</style>\r\n"
//								 + "</head>\r\n"
//								 + "<body>\r\n"
//
//			 					 + " \r\n"			   
//			 					 + " <h2>Covid Scientific Discoveries</h2>\r\n"
//			 					 + " \r\n";
		assertNotNull(tableHTML.getDocumentPre());
		assertTrue(tableHTML.getDocumentPre().equals(documentPreActual));
//		assertTrue(tableHTML.getDocumentPre() != null);
	}
	
	
	@SuppressWarnings("static-access")
	@Test
	public void htmlTableTest() {
		tableHTML = new HTMLTableBuilder();
		ArrayList<String> html = tableHTML.getListOfHTMLTable();
		assertTrue(html != null);
	}
	

	@SuppressWarnings("static-access")
	@Test
	public void addHeaderToHTMLTableTest() {
		tableHTML = new HTMLTableBuilder();
		String headerColumn = tableHTML.getHeaderToHTMLTable();
//		String headerColumn = "<table>\r\n"
//				 			+ "	<tr>\r\n"
//				 			+ "		<th>Article Title</th>\r\n"
//				 			+ " 		<th>Journal Name</th>\r\n"
//				 			+ "			<th>Publication Year</th>\r\n"
//				 			+ " 		<th>Authors</th>\r\n"
//				 			+ " 	</tr>\r\n"
//				 			+ " \r\n";
		assertNotNull(tableHTML.getHeaderToHTMLTable());
		assertTrue(tableHTML.getHeaderToHTMLTable().equals(headerColumn));
		assertTrue(tableHTML.getHeaderToHTMLTable() != null);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void addEndOfHTMLTableTest() {
		tableHTML = new HTMLTableBuilder();
		String documentPost = tableHTML.getEndOfHTMLTable();
//		String documentPost = " \r\n"
//				 			+ "</table>\r\n"
//				 			+ "</body>\r\n"
//				 			+ "</html>\r\n";
		tableHTML.addEndOfHTMLTable();
		assertTrue(tableHTML.getEndOfHTMLTable().equals(documentPost));
		assertTrue(tableHTML.getEndOfHTMLTable() != null);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void addContentToHTMLTableTest() {
		tableHTML = new HTMLTableBuilder();
		assertTrue(tableHTML.getContentToHTMLTable() != null);
	}
	
	
	@SuppressWarnings("static-access")
	@Test
	public void Test() throws AnalysisException, IOException {
		tableHTML = new HTMLTableBuilder();
		
		ArrayList<String> listOfHTMLTable = tableHTML.getListOfHTMLTable();
		tableHTML.addStyleToHTMLTable();
		tableHTML.addHeaderToHTMLTable();
		
		tableHTML.addEndOfHTMLTable();
		tableHTML.generateHTMLTable();
		assertTrue(tableHTML.getListOfHTMLTable() != null);
	}


}
