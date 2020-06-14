package pt.iscte.es2.covid_graph_spread;

import java.io.File;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class CovidGraphSpreadServiceTest {


	@Test
	void testConstructor() {
		CovidGraphSpreadService covidGraphSpreadService = new CovidGraphSpreadService();
		Assert.assertNotNull(covidGraphSpreadService);
	}
//
//	@Test
//	void testDeleteLocalRepository() {
//		CovidGraphSpreadService covidGraphSpreadService = new CovidGraphSpreadService();
//		Assert.assertNotNull(covidGraphSpreadService);
//
//		// Apaga repositorio local
//		String localRepositoryDirectory = "src/java/resources/repository";
//		File localRepositoryPath = new File(localRepositoryDirectory);
//		covidGraphSpreadService.deleteLocalRepository(localRepositoryPath);
//		Assert.assertTrue(localRepositoryPath.exists());
//		Assert.assertEquals(0, localRepositoryPath.listFiles().length);
//		
//	}
//
//
//	@Test
//	void testCloneRemoteRepository() {
//		CovidGraphSpreadService covidGraphSpreadService = new CovidGraphSpreadService();
//		Assert.assertNotNull(covidGraphSpreadService);
//
//		// Apaga repositorio local
//		String localRepositoryDirectory = "src/java/resources/repository";
//		File localRepositoryPath = new File(localRepositoryDirectory);
//		covidGraphSpreadService.deleteLocalRepository(localRepositoryPath);
//		Assert.assertFalse(localRepositoryPath.exists());
//
//		// Faz clone do repositorio remoto
//		String remoteRepositoryURL = "https://github.com/vbasto-iscte/ESII1920";
//		Git git = covidGraphSpreadService.cloneRemoteRepository(remoteRepositoryURL, localRepositoryPath);
//		Assert.assertNotNull(git);
//	}
//
//	@Test
//	void testGetFileChanges() {
//		CovidGraphSpreadService covidGraphSpreadService = new CovidGraphSpreadService();
//		Assert.assertNotNull(covidGraphSpreadService);
//
//		// Apaga repositorio local
//		String localRepositoryDirectory = "src/java/resources/repository";
//		File localRepositoryPath = new File(localRepositoryDirectory);
//		covidGraphSpreadService.deleteLocalRepository(localRepositoryPath);
//		Assert.assertFalse(localRepositoryPath.exists());
//
//		// Faz clone do repositorio remoto
//		String remoteRepositoryURL = "https://github.com/vbasto-iscte/ESII1920";
//		Git git = covidGraphSpreadService.cloneRemoteRepository(remoteRepositoryURL, localRepositoryPath);
//		Assert.assertNotNull(git);
//
//		// Le commits das varias tags que tem o ficheiro covid19spreading.rdf
//		String filename = "covid19spreading.rdf";
//		List<Data> dataList = covidGraphSpreadService.getFileChanges(git, filename);
//		git.close();
//		
//		Assert.assertNotNull(dataList);
//		Assert.assertTrue(dataList.size() >= 0);
//	}


	@Test
	void testGenerateHTML() {
		
		CovidGraphSpreadService covidGraphSpreadService = new CovidGraphSpreadService();
		Assert.assertNotNull(covidGraphSpreadService);

		// Apaga repositorio local
		String localRepositoryDirectory = "src/java/resources/repository";
		File localRepositoryPath = new File(localRepositoryDirectory);
		covidGraphSpreadService.deleteLocalRepository(localRepositoryPath);
		Assert.assertFalse(localRepositoryPath.exists());

		// Faz clone do repositorio remoto
		String remoteRepositoryURL = "https://github.com/vbasto-iscte/ESII1920";
		Git git = covidGraphSpreadService.cloneRemoteRepository(remoteRepositoryURL, localRepositoryPath);
		Assert.assertNotNull(git);

		// Le commits das varias tags que tem o ficheiro covid19spreading.rdf
		String filename = "covid19spreading.rdf";
		List<Data> dataList = covidGraphSpreadService.getFileChanges(git, filename);
		git.close();
		
		Assert.assertNotNull(dataList);
		String templateHtmlFilePath = "src/java/resources/html/template.html";
		String html = covidGraphSpreadService.generateHTML(templateHtmlFilePath, dataList);
		Assert.assertNotNull(html);
	}
}
