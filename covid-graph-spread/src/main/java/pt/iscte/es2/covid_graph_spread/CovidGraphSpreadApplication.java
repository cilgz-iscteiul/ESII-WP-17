package pt.iscte.es2.covid_graph_spread;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Catarina Lopes Garcez
 *
 */

@SpringBootApplication
@RestController
public class CovidGraphSpreadApplication {
	
	/**
	 * Instancia da classe CovidGraphSpreadService
	 */
	private CovidGraphSpreadService covidGraphSpreadService;

	/**
	 * Cria uma nova instancia da classe CovidGraphSpreadApplication
	 */
	public CovidGraphSpreadApplication() {
		covidGraphSpreadService = new CovidGraphSpreadService();
	}

	/**
	 * Funcao que permite remover o repositorio local, clonar o repositorio remoto, procurar as versoes de um determinado ficheiro e gerar uma pagina html
	 * 
	 * @return html final a mostrar no browser
	 */
	@RequestMapping(value = "/covid-graph-spread", method = RequestMethod.GET,  produces = "text/html")
	public String getCovidGraphSpreadTable() {

		// Apaga repositorio local
		String localRepositoryDirectory = "src/java/resources/repository";
		File localRepositoryPath = new File(localRepositoryDirectory);
		covidGraphSpreadService.deleteLocalRepository(localRepositoryPath);
 
		// Faz clone do repositorio remoto
		String remoteRepositoryURL = "https://github.com/vbasto-iscte/ESII1920";
		Git git = covidGraphSpreadService.cloneRemoteRepository(remoteRepositoryURL, localRepositoryPath);

		if(git != null) {
			// Le commits das varias tags que tem o ficheiro covid19spreading.rdf
			String filename = "covid19spreading.rdf";
			List<Data> dataList = covidGraphSpreadService.getFileChanges(git, filename);
			git.close();

			String templateHtmlFilePath = "src/java/resources/html/template.html";
			String html = covidGraphSpreadService.generateHTML(templateHtmlFilePath, dataList);
			return html;

		} else {
			return "Git is null...";
		}
	}
	
	/**
	 * Main do programa
	 * 
	 * @param args - argumentos iniciais (Nao usados)
	 */
	public static void main(String[] args) {
		//SpringApplication.run(CovidGraphSpreadApplication.class, args);
		 SpringApplication app = new SpringApplication(CovidGraphSpreadApplication.class);
	        app.setDefaultProperties(Collections.singletonMap("server.port", "8081"));
	        app.run(args);
	        
	}
	
}