package _Covid_SCI_Discoveries._Covid_SCI_Discoveries;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import pl.edu.icm.cermine.ContentExtractor;
import pl.edu.icm.cermine.exception.AnalysisException;
import pl.edu.icm.cermine.metadata.model.DateType;
import pl.edu.icm.cermine.metadata.model.DocumentAuthor;
import pl.edu.icm.cermine.metadata.model.DocumentDate;
import pl.edu.icm.cermine.metadata.model.DocumentMetadata;

/**
 * A Classe HTMLTableBuilder consiste em desenvolver uma aplicação designada por covid-sci-discoveries.
 * Esta aplicação irá aceder ao repositório designado por Scientific Articles, onde este irá ter uma lista de ficheiros PDF com artigos científicos. 
 * Depois do acesso ter sido feito com sucesso, a aplicação irá extrair o metadata de cada um dos ficheiros PDF que se encontram no repositório.
 * Para cada artigo científico irá ser feita a extração de metadata, nomeadamente: 
 * 	- o título do artigo;
 *  - o nome do jornal;
 *  - o ano de publicação;
 *  - os autores de cada artigo.
 * Posteriormente irá ser criada uma Tabela HTML com 4 colunas (Article Title, Journal Name, Publication Year e Authors) com o metadata extraído dos ficheiros que
 * se encontram no repositório.
 * Na coluna referente ao título do artigo (Article Title) da tabela HTML, cada linha terá que apontar para um hyperlink, que ao clicar no titulo do artigo irá
 * permitir ao visitante abrir e visualizar o ficheiro PDF.
 *  
 * NOTA: para que a extração do metada de documentos PDF fosse possível foi necessário recorrer ao projeto CERMINE (https://github.com/CeON/CERMINE).
 *     
 * @author Beatriz Gomes - 82195
 * @version 1.0
 */

public class HTMLTableBuilder {
	
	/**
	 * Lista do conteúdo que permite a criação da tabela HTML.
	 */
	public static ArrayList<String> listOfHTMLTable;
	
	/**
	 * Variável com o conteúdo extraído dos artigos científicos, referente ao título do artigo.
	 */
	public static String article_title;
	/**
	 * Variável com o conteúdo extraído dos artigos científicos, referente ao hyperlink do artigo, que posteriormente irá ser adicionado na tabela HTML.
	 */
	public static String hyperlink_to_pdf;
	/**
	 * Variável com o conteúdo extraído dos artigos científicos, referente ao nome do jornal, que posteriormente irá ser adicionado na tabela HTML.
	 */
	public static String journal_name;
	/**
	 * Variável com o conteúdo extraído dos artigos científicos, referente ao ano de publicação, que posteriormente irá ser adicionado na tabela HTML.
	 */
	public static String pub_year;
	/**
	 * Variável com o conteúdo extraído dos artigos científicos, referente aos autores do artigo.
	 */
	public static String author;
	/**
	 * Variável com o conteúdo extraído dos artigos científicos, referente aos autores do artigo, que posteriormente irá ser adicionado na tabela HTML.
	 */
	public static String finalAuthor;
	/**
	 * String que permite a criação da tabela HTML e define o seu design.
	 */
	public static String documentPre;
	/**
	 * String que permite definir o nome das colunas das Tabela HTML.
	 */
	public static String headerColumn;
	/**
	 * String que permite terminar a criação da Tabela HTML. 
	 */
	public static String documentPost;
	/**
	 * String com os conteúdos que irão ser inseridos na Tabela HTML. 
	 */
	public static String htmlColumn;
	/**
	 * Lista de ficheiros que vão ser sujeitos à extração de metadata.
	 */
	public static ArrayList<String> listOfFiles;

	
	/**
	 * Este método retorna a um ArrayList de Strings compostas pelo código que permite a criação da tabela HTML.
	 */
	public static ArrayList<String> getListOfHTMLTable() {
		return listOfHTMLTable;
	}
	
	
	/**
	 * Este método permite a criação da Tabela HTML, onde é definido o seu design.
	 */
	public void addStyleToHTMLTable() {
		documentPre = "<!DOCTYPE html>\r\n"
					+ "<html>\r\n"
					+ "<head>\r\n"
					+ "<meta charset=\"UTF-8\">\r\n"
					+ "<style>\r\n"

					+ " \r\n"
		   
					+ "table {\r\n"
					+ "	font-family: arial, sans-serif;\r\n"
					+ " border-collapse: collapse;j\r\n" 
					+ " width: 100%;\r\n"									
					+ "}\r\n"

					+ " \r\n"

					+ "td, th {\r\n"
					+ "	border: 1px solid #dddddd;\r\n"
					+ " text-align: left;\r\n" 
					+ " padding: 6px;\r\n"
					+ "}\r\n"

					+ " \r\n"

					+ "tr:nth-child(even) {\r\n"
					+ "	background-color: #e6f0ff;\r\n"
					+ "}\r\n"
					 
				 	+ " \r\n"

					+ "</style>\r\n"
					+ "</head>\r\n"
					+ "<body>\r\n"
					
				 	+ " \r\n"			   
				 	+ " <h2>Covid Scientific Discoveries</h2>\r\n"
				 	+ " \r\n";
		listOfHTMLTable.add(documentPre);
	}
		
	/**
	 * Este método retorna a String que faz parte da criação da Tabela HTML.
	 */
	public static String getDocumentPre() {
		return documentPre;
	}
	

	/**
	 * Este método permite definir o nome das colunas das Tabela HTML.
	 */
	public void addHeaderToHTMLTable() {
		headerColumn = "<table>\r\n"
					 + "	<tr>\r\n"
					 + "		<th>Article Title</th>\r\n"
					 + " 		<th>Journal Name</th>\r\n"
					 + "		<th>Publication Year</th>\r\n"
					 + " 		<th>Authors</th>\r\n"
					 + " 	</tr>\r\n"
					 + " \r\n";
		listOfHTMLTable.add(headerColumn);
	}
	
	/**
	 * Este método retorna a String que faz parte da criação da Tabela HTML.
	 */
	public String getHeaderToHTMLTable() {
		return headerColumn;
	}
	
	
	/**
	 * Este método permite terminar a criação da Tabela HTML. 
	 */
	public void addEndOfHTMLTable() {
		documentPost = " \r\n"
					 + "</table>\r\n"
					 + "</body>\r\n"
					 + "</html>\r\n";
		listOfHTMLTable.add(documentPost);
	}
	
	/**
	 * Este método retorna a String que faz parte da criação da Tabela HTML.
	 */
	public static String getEndOfHTMLTable() {
		return documentPost;
	}
	
	
	/**
	 * Este método permite adicionar conteúdo à tabela HTML, este conteúdo é referente ao título do artigo, ao nome do jornal, ao ano de publicação e por fim aos 
	 * nomes dos autores de cada artigo científico.
	 */
	public void addContentToHTMLTable(String hyperlink_to_pdf, String journal_name, String pub_year, String finalAuthor) {
		htmlColumn = "<tr>\r\n"
			  	   + "<td>" + hyperlink_to_pdf + "</td>\r\n"
			   	   + "<td>" + journal_name + "</td>\r\n"
			  	   + "<td>" + pub_year + "</td>\r\n"
			  	   + "<td>" + finalAuthor + "</td>\r\n"
			   	   + "</tr>\r\n";
		listOfHTMLTable.add(htmlColumn);
	}
	
	/**
	 * Este método retorna a String que faz parte da criação da Tabela HTML.
	 */
	public static String getContentToHTMLTable() {
		return htmlColumn;
	}
	
	
	/**
	 * Este método permite vizualizar a Tabela HTML no Browser.
	 */
	public void generateHTMLTable() {
		File f = new File("Covid_SCI_Discoveries.html");	
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
			for (String html : listOfHTMLTable) {
				bw.write(html);
				System.out.println(html);
			}
			Desktop.getDesktop().browse(f.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
//	public void remoteConnection() throws IOException {
//
//		//Ligação Remota
//		String URL  = "http://localhost:8081/wordpress/wp-content/uploads/repo_items/";
//		//Declarar a lista para os ficheiros PDF serem adicionados
//		listOfFiles = new ArrayList<String>();
//		//Detetar se os ficheiros terminam com .pdf ou .PDF
//		String regex = "^.*\\.(pdf|PDF)$";
//		Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
//		//Irá fazer automáticamente o download da pagina http que contém PDFs e converte-os em documentos
//		org.jsoup.nodes.Document doc = Jsoup.connect(URL).get(); 
//		//Seleciona os atributos <a href="">, assim como os ficheiros pdf que estão nesses elementos, também irá selecionar todos os elementos <a href="">
//		Elements links = doc.select("a[href]");
//
//		for(org.jsoup.nodes.Element conteudo : links) {
//			String relHref = conteudo.attr("href");
//			Matcher matcher = pattern.matcher(relHref);
//			while(matcher.find()) {
//				listOfFiles.add(relHref);
//				System.out.println(relHref);
//			}
//		}
//	}
		
	
	/**
	* Este método faz com que a aplicação covid-sci-discoveries seja executada.
	* 
	* @throws AnalysisException sempre que uma query de análise falha, geralmente isto acontece devido ao facto da query em questão ser inválida.
	* @throws IOException sempre que uma operação de input ou output falha ou é interpretada.
	*/ 
	public static void main(String[] args) throws AnalysisException, IOException {
		HTMLTableBuilder htmlBuilder = new HTMLTableBuilder();
			
		//Ligação Local		
		ClassLoader classLoader = HTMLTableBuilder.class.getClassLoader();
		URL resource = classLoader.getResource("ScientificArticles");
		File f = new File(resource.getFile());
		File[] listOfFiles = f.listFiles();
			
		
		listOfHTMLTable = new ArrayList<String>();
		htmlBuilder.addStyleToHTMLTable();
		htmlBuilder.addHeaderToHTMLTable();

			
		for (File file : listOfFiles) {

			ContentExtractor extractor = new ContentExtractor();
			InputStream inputStream = new FileInputStream(file);
			extractor.setPDF(inputStream);
			DocumentMetadata result = extractor.getMetadata();		

			
			article_title = result.getTitle();
			hyperlink_to_pdf = "<a target=\"_blank\" href=" + file.getAbsolutePath() + ">" + article_title + "</a>\r\n";
			journal_name = result.getJournal();				
			DocumentDate doc_year = result.getDate(DateType.PUBLISHED);
			pub_year = doc_year.getYear();	
			
			
			ArrayList<String> listofAuthors = new ArrayList<String>();			
			@SuppressWarnings("unused")
			List<DocumentAuthor> authors = result.getAuthors();
			for(DocumentAuthor a : authors) {
				author = a.getName();
				listofAuthors.add(author);
				finalAuthor = listofAuthors.toString()
						.replace("[", "")
						.replace("]", "")
						.replace(",", " ; ")
						.trim();
			}
			htmlBuilder.addContentToHTMLTable(hyperlink_to_pdf, journal_name, pub_year, finalAuthor);			
		}
		htmlBuilder.addEndOfHTMLTable();
		htmlBuilder.generateHTMLTable();
	}

}
