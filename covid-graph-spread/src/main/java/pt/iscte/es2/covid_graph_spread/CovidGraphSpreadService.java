package pt.iscte.es2.covid_graph_spread;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevWalk;

/**
 * 
 * @author Catarina Lopes Garcez
 *
 */
public class CovidGraphSpreadService {

	/**
	 * Cria uma nova instancia da classe CovidGraphSpreadService
	 */
	public CovidGraphSpreadService() {}


	/**
	 * Procedimento que permite remover recursivamente todos os ficheiros e pastas dentro de um diretorio
	 * 
	 * @param localRepositoryPath - raiz do diretorio que qual se pretendem remover todos os ficheiros e pastas
	 */
	public void deleteLocalRepository(File localRepositoryPath) {
		// Remove o repositorio local, caso exista
		if(localRepositoryPath.exists()) {
			deleteDir(localRepositoryPath);
		}
		localRepositoryPath.delete();
	}

	private void deleteDir(File dir) {
		File[] files = dir.listFiles();
		if(files != null) {
			for (File file : files) {
				deleteDir(file);
			}
		}
		dir.delete();
	}


	/**
	 * Funcao que permite clonar um repositorio remoto para a sua versao local
	 * 
	 * @param remoteRepositoryURL - URL do repositorio remoto
	 * @param localRepositoryPath - Diretoria do repositorio local
	 * @return instancia da classe Git com o repositorio clonado
	 */
	public Git cloneRemoteRepository(String remoteRepositoryURL, File localRepositoryPath) {
		Git git = null;

		try {
			git = Git.cloneRepository()
					.setURI(remoteRepositoryURL)
					.setDirectory(localRepositoryPath)
					.setCloneAllBranches(true)
					.call();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return git;
	}


	/**
	 * Funcao que permite encontrar todas as versoes de um determinado ficheiro associado a tags
	 * 
	 * @param git - repositorio local onde se pretende procurar
	 * @param filename - nome do ficheiro que se pretende procurar
	 * @return lista com dados relativos a todas as ocorrencias encontradas
	 */
	public List<Data> getFileChanges(Git git, String filename)  {
		List<Data> dataList = new ArrayList<Data>();

		try {
			Repository repository = git.getRepository();

			RevWalk revWalk = new RevWalk(repository);

			List<Ref> call = git.tagList().call();
			for (Ref ref : call) {
				System.out.println("----------------");
				System.out.println("Tag: " + ref + " " + ref.getName() + " " + ref.getObjectId().getName());

				String tagFullName = ref.getName();

				RevObject any = revWalk.parseAny(ref.getObjectId());
				if (any != null) {

					if (any instanceof RevCommit) {
						System.out.println("RevCommit");
						RevCommit rc = (RevCommit) any;

						PersonIdent authorIdent = rc.getAuthorIdent();
						Date authorDate = authorIdent.getWhen();

						String baseLink = "http://visualdataweb.de/webvowl/#iri=https://github.com/vbasto-iscte/ESII1920/raw";
						String tagName = tagFullName.substring(tagFullName.lastIndexOf("/") + 1);
						String link = baseLink + "/" + tagName + "/" + filename;

						Data data = new Data(filename, tagName, rc.getFullMessage(), link, authorDate);
						System.out.println(data);
						dataList.add(data);
					}
					any = revWalk.next();
				}
			}

			revWalk.close();
			revWalk.dispose();

		} catch(Exception e) {
			e.printStackTrace();
		}

		return dataList;
	}


	/**
	 * Funcao que permite ler o conteudo de um template html e criar N linhas na sua tabela
	 * 
	 * @param templateHtmlFilePath - template html usado
	 * @param dataList - Lista com N entradas de dados que serao adicionados a tabela
	 * @return string contendo a versao html final com a tabela e as N linhas de dados
	 */
	public String generateHTML(String templateHtmlFilePath, List<Data> dataList) {
		StringBuilder sb = new StringBuilder();
		try {
			Scanner scanner = new Scanner(new File(templateHtmlFilePath));

			while(scanner.hasNextLine()) {
				String templateLine = scanner.nextLine();
				System.out.println(templateLine);
				if(templateLine.contains("[[DATA]]")) {

					for (Data data : dataList) {
						sb.append("\t\t<tr>\n");
						sb.append("\t\t\t<td>" + data.getDate() + "</td>\n");
						sb.append("\t\t\t<td>" + data.getFileName() + "</td>\n");
						sb.append("\t\t\t<td>" + data.getFileTag() + "</td>\n");
						sb.append("\t\t\t<td>" + data.getTagDescription() + "</td>\n");
						sb.append("\t\t\t<td>\n");
						sb.append("\t\t\t<a href=\"" + data.getSpreadVisualizationLink() + "\"> link </a>\n");
						sb.append("\t\t\t</td>\n");
						sb.append("\t\t</tr>\n");
					}

				} else {
					sb.append(templateLine);
				}
			}

			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}
}
